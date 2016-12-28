package com.dds.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dds.test.service.AppService;

public class DDSTest {
	private  ApplicationContext context = null;
  @Test
  public void test() throws Exception {
	  Session session = new Session();
	  session.setDsid("licpdb");
	  AppService service = context.getBean(AppService.class);
	  try {
		List<String> list = service.insertAndGet("aaaaaa", session);
		showList(list);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
  }
  @Test
  public void test2() throws Exception {
	  Session session = new Session();
	  session.setDsid("licpdb");
	  AppService service = context.getBean(AppService.class);
	  try {
		service.insertAndThrowSomeException("bbbbb", session);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
  }
  private void showList(List<String> list){
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
  }
  
  @BeforeClass
  public void beforeClass() {
	 context = new ClassPathXmlApplicationContext("applicationContext.xml");
	  
  }

}

package com.dds.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dds.test.Session;
import com.dds.test.dao.UserOperatorDAO;
import com.dds.test.service.AppService;
@Service
@Transactional
public class AppServiceImple implements AppService {
	@Resource
	private UserOperatorDAO userOperatorDAO = null;

	@Override
	public void insertText(String text, Session session) throws Exception {
		userOperatorDAO.insertText(text);
	}
	@Override
	public List<String> getAllText(Session session) throws Exception {
		return userOperatorDAO.queryAllTexts();
	}
	@Override
	public void insertAndThrowSomeException(String text,Session session) throws Exception {
		userOperatorDAO.insertText(text);
		List<String> list = getAllText(session);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		throw new RuntimeException("some error");
		
	}
	@Override
	public List<String> insertAndGet(String text, Session session) throws Exception {
		insertText(text, session);
		return getAllText(session);
		
	}

}

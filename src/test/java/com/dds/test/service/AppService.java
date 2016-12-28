package com.dds.test.service;

import java.util.List;

import com.dds.impl.advice.Dsid;
import com.dds.test.Session;

public interface AppService {
	public void insertText(String text, @Dsid("dsid")Session session) throws Exception;
	public List<String> getAllText(@Dsid("dsid")Session session) throws Exception;
	public void insertAndThrowSomeException(String text, @Dsid("dsid")Session session) throws Exception;
	public List<String> insertAndGet(String text, @Dsid("dsid") Session session) throws Exception;
	
}

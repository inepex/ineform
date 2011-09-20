package com.inepex.example.ineForm.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.dao.ContactTypeDao;
import com.inepex.example.ineForm.entity.kvo.ContactTypeHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactTypeHandlerFactory.ContactTypeHandler;
import com.inepex.example.ineForm.entity.mapper.ContactTypeMapper;

@Singleton
@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	
	@Inject
	ContactTypeDao contactTypeDAO;
	
	@Inject
	ContactTypeMapper contactTypeMapper;
	
	@Inject
	ContactTypeHandlerFactory contactTypeHandlerFactory;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactTypeHandler kvo = contactTypeHandlerFactory.createHandler();
		kvo.setTypeName("type2");
		ContactType entity = new ContactType();
		contactTypeDAO.persistTrans(contactTypeMapper.kvoToEntity(kvo.getAssistedObject(), entity));
		
		resp.getWriter().println("Ready");
	}
}

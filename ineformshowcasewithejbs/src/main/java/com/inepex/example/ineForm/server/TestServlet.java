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
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.mapper.ContactTypeMapper;

@Singleton
@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	
	@Inject
	ContactTypeDao contactTypeDAO;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactTypeKVO kvo = new ContactTypeKVO();
		kvo.setTypeName("type2");
		ContactType entity = new ContactType();
		contactTypeDAO.persist(new ContactTypeMapper().kvoToEntity(kvo, entity));
		
		resp.getWriter().println("Ready");
	}
}

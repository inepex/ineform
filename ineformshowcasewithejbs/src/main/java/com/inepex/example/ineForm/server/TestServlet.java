package com.inepex.example.ineForm.server;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.dao.ContactTypeDao;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.mapper.ContactTypeMapper;

@WebServlet(urlPatterns={"/TestServlet"})
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303942304654917315L;
	
	@EJB ContactTypeDao contactTypeDAO;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		ContactTypeKVO kvo = new ContactTypeKVO();
		kvo.setTypeName("type2");
		ContactType entity = new ContactType();
		contactTypeDAO.persist(new ContactTypeMapper().kvoToEntity(kvo, entity));
		
		resp.getWriter().println("Ready");
	}
}

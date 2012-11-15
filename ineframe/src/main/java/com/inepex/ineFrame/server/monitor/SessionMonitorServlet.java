package com.inepex.ineFrame.server.monitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@SuppressWarnings("serial")
public class SessionMonitorServlet extends HttpServlet implements HttpSessionListener {
	
	private static List<HttpSession> sessions = new ArrayList<HttpSession>();
	private static Long numberOfSessions = 0L;
	
	@Inject
	public SessionMonitorServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() throws ServletException {
		System.out.println("started");
		super.init();
	}

	@Override	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().println("Number of active sessions: " + numberOfSessions);
		List<HttpSession> copy;
		synchronized (sessions) {
			copy = new ArrayList<HttpSession>(sessions);
		}
		for (HttpSession sess : copy){
			
			Long approximateSizeInByte = 0L;
			Enumeration<String> attributeNames = sess.getAttributeNames();
			while (attributeNames.hasMoreElements()){
				String name = attributeNames.nextElement();
				if (sess.getAttribute(name) instanceof IsSerializable || sess.getAttribute(name) instanceof Serializable){
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutput out = new ObjectOutputStream(bos);
					
					out.writeObject(sess.getAttribute(name));
					byte[] bytes = bos.toByteArray();
					approximateSizeInByte += bytes.length;
				}

			}		
			resp.getWriter().println("Size of " + sess.getId() + ": " + approximateSizeInByte);
			
		}
		
		
	}


	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		synchronized (numberOfSessions) {
			numberOfSessions++;
		}
		synchronized (sessions) {
			sessions.add(arg0.getSession());			
		}

	}


	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		synchronized (numberOfSessions) {
			numberOfSessions--;
		}
		synchronized (sessions) {
			sessions.remove(arg0.getSession());			
		}
	}
}

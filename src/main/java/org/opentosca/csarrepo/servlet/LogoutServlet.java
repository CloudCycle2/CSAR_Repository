package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login servlet for the Csar repository
 * 
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@SuppressWarnings("serial")
@WebServlet(LogoutServlet.PATH)
public class LogoutServlet extends AbstractServlet {

	public static final String PATH = "/logout";

	public LogoutServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (null != request.getSession()) {
			request.getSession().invalidate();
		}

		this.redirect(request, response, LoginServlet.PATH);
	}

}

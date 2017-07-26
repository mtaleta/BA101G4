package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tools.HttpUtils;

public class AdminAuthFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		// 【從 session 判斷此user是否登入過】
		Object adminVO = session.getAttribute("ADMIN");
		String uri = request.getRequestURI();

		boolean isValid = false;
		if (adminVO == null) {
			isValid = false;
		}
		if (uri.indexOf("/login_Admin.jsp") != -1 || uri.indexOf("/addAdmin.jsp") != -1
				|| uri.indexOf("/listOneAdmin.jsp") != -1) {
			isValid = true;
		} else if (request.getSession().getAttribute("ADMIN") != null) {
			isValid = true;
		} else if (uri.indexOf("/admin.do") != -1) {
			String action = request.getParameter("action");
			if ("Login_Admin".equals(action) || "insert".equals(action)) {
				isValid = true;
			}
		}

		if (isValid) {
			chain.doFilter(req, res);
		} else {
			session.setAttribute("location", uri);
			HttpUtils.redirect("/backend/admin/login_Admin.jsp", request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

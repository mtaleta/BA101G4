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

public class StoreAuthFilter implements Filter {

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
		Object storeVO = session.getAttribute("STORE");
		String uri = request.getRequestURI();
		
		boolean isValid = false;
		if (storeVO == null) {
			isValid = false;
		}
		if (uri.indexOf("/login_Store.jsp") != -1 || uri.indexOf("/addStore.jsp") != -1|| uri.indexOf("/forgetPasswrod.jsp") != -1) {
			isValid = true;
		} else if (request.getSession().getAttribute("STORE") != null) {
			isValid = true;
		} else if (uri.indexOf("/store.do") != -1) {
			String action = request.getParameter("action");
			if ("Login_Store".equals(action) || "insert".equals(action)|| "STORE_AUTHENTICATION".equals(action)|| "STORE_FORGET_PASSWORD".equals(action)) {
				isValid = true;
			}
		}

		if (isValid) {
			chain.doFilter(req, res);
		} else {
			session.setAttribute("location", uri);
			HttpUtils.redirect("/frontend/store/login_Store.jsp", request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

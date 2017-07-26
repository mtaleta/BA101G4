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

public class MemberAuthFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 【取得 session】
		HttpSession session = request.getSession();
		// 【從 session 判斷此user是否登入過】
		Object memberVO = session.getAttribute("MEMBER");
		String uri = request.getRequestURI();

		boolean isValid = false;
		if (memberVO == null) {
			isValid = false;
		}
		if (uri.indexOf("/login_Member.jsp") != -1 || uri.indexOf("/addMember.jsp") != -1
				|| uri.indexOf("/forgetPasswrod.jsp") != -1) {
			isValid = true;
		} else if (request.getSession().getAttribute("MEMBER") != null) {
			isValid = true;
		} else if (uri.indexOf("/member.do") != -1) {
			String action = request.getParameter("action");
			if ("Login_Member".equals(action) || "insert".equals(action) || "MEM_AUTHENTICATION".equals(action)
					|| "MEM_FORGET_PASSWORD".equals(action)) {
				isValid = true;
			}
		}

		if (isValid) {
			chain.doFilter(req, res);
		} else {
			session.setAttribute("location", uri);
			HttpUtils.redirect("/frontend/member/login_Member.jsp", request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

package com.tools;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.model.AdminVO;
import com.member.model.MemberVO;
import com.store.model.StoreVO;

public class HttpUtils {

	private final static String ROOT_FRONTEND = "/frontend";
	private final static String ROOT_BACKEND = "/backend";
	private final static String SESSION_MEMBER = "MEMBER";
	private final static String SESSION_STORE = "STORE";
	private final static String SESSION_ADMIN = "ADMIN";

	public static void registerSessionMember(HttpServletRequest request, MemberVO vo) {
		request.getSession().setAttribute(SESSION_MEMBER, vo);
	}

	public static void registerSessionStore(HttpServletRequest request, StoreVO vo) {
		request.getSession().setAttribute(SESSION_STORE, vo);
	}

	public static void registerSessionAdmin(HttpServletRequest request, AdminVO vo) {
		request.getSession().setAttribute(SESSION_ADMIN, vo);
	}

	public static void clearSessionMember(HttpServletRequest request) {
		invalidateSession(request, SESSION_MEMBER);
	}

	public static void clearSessionStore(HttpServletRequest request) {
		invalidateSession(request, SESSION_STORE);
	}

	public static void clearSessionAdmin(HttpServletRequest request) {
		invalidateSession(request, SESSION_ADMIN);
	}

	private static void invalidateSession(HttpServletRequest request, String sessionKey) {
		request.getSession().removeAttribute(sessionKey);
		request.getSession().invalidate();
	}

	public static String getParameter(HttpServletRequest request, String key) {
		return request.getParameter(key);
	}

	public static void forward(String url, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		RequestDispatcher failureView = req.getRequestDispatcher(url);
		failureView.forward(req, res);
	}

	public static void redirect(String url, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.sendRedirect(req.getContextPath() + url);
		return;
	}

	public static String getSmartUrl(String path, HttpServletRequest req) {
		String from = "";
		if (isFrontend(req)) {
			from = ROOT_FRONTEND;
		} else if (isBackend(req)) {
			from = ROOT_BACKEND;
		}
		return from + path;
	}

	public static void smartForward(String path, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		forward(getSmartUrl(path, req), req, res);
	}

	public static boolean isFrontend(HttpServletRequest req) {
		return getReferer(req).indexOf(ROOT_FRONTEND) != -1;
	}

	public static boolean isBackend(HttpServletRequest req) {
		return getReferer(req).indexOf(ROOT_BACKEND) != -1;
	}

	private static String getReferer(HttpServletRequest req) {
		return req.getRequestURI();
	}

}

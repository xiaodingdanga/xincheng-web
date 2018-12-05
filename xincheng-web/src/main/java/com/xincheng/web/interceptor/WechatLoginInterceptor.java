package com.xincheng.web.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xincheng.service.system.MenuService;

public class WechatLoginInterceptor implements HandlerInterceptor {

	Logger log = LogManager.getLogger(WechatLoginInterceptor.class.getName());

	@Resource
	private MenuService menuService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info(request.getRequestURI());
		HttpSession session = request.getSession();
		Object openid = session.getAttribute("openid");
		Object customerid = session.getAttribute("customerid");
		Object appid = session.getAttribute("appid");
		if (openid == null || "".equals(openid.toString().trim()) || customerid == null
				|| "".equals(customerid.toString().trim()) || appid == null || "".equals(appid.toString().trim())) {// 没有openid
			// 测试
			session.setAttribute("openid", "12");
			session.setAttribute("customerid", "0");
			session.setAttribute("appid", "wx40c6858f072b7182");
			session.setAttribute("customername", "测试账号0");
			session.setAttribute("id", "1");
			session.setAttribute("name", "骨灰级");
			return true;
			// throw new OpenIdNullException();
		} else {// 有openid
			return true;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
	}
}

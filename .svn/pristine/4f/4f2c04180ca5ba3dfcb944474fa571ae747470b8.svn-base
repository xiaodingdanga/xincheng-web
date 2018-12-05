package com.xincheng.web.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/homepage")
public class HomePageController extends BaseController{
	Logger log = LogManager.getLogger(HomePageController.class.getName());
	/**
	 * 首页页面数据加载
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request,Model modal){
		Map<String,Object> user=(Map<String,Object>)request.getSession().getAttribute("user");
		return new ModelAndView("modules/homepage/main");
	}
	/**
	 * 系统消息自己能查看的
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/sysinfo")
	public ModelAndView sysinfo(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/homepage/sysinfo");
	}
}

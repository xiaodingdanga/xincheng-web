package com.xincheng.web.controller.system;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.xincheng.service.system.MemberService;
import com.xincheng.service.system.RechargeService;
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/recharge")
public class RechargeController extends BaseController{
	Logger log = LogManager.getLogger(RechargeController.class.getName());
	
	@Resource
	private RechargeService rechargeService;
	/**
	 * 管理首页
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/recharge/main");
	}
	/**	
	 * 获取商品信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/getDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String getDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=rechargeService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 充值页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpServletRequest request,Model modal){
		Map<String,Object> paramap = this.getParameter();
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/recharge/edit");
	}
	/**
	 * 充值
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/save")
	public @ResponseBody void save(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		rechargeService.save(paramap);
	}
}

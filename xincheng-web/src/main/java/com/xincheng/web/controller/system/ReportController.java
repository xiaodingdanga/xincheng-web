package com.xincheng.web.controller.system;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.xincheng.service.system.MemberService;
import com.xincheng.service.system.ReportService;
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/report")
public class ReportController extends BaseController{
	Logger log = LogManager.getLogger(ReportController.class.getName());
	
	@Resource
	private ReportService reportService;
	/**
	 * 消费明细
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/xfmxreport")
	public ModelAndView xfmxreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/xfmxreport");
	}
	/**	
	 * 获取消费明细信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/xfmxgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String xfmxgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.xfmxgetDataList(paramap);
		return data;
	}
	
	/**
	 *消费统计
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/xftjreport")
	public ModelAndView xftjreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/xftjreport");
	}
	/**	
	 * 获取消费统计信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/xftjgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String xftjgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.xftjgetDataList(paramap);
		return data;
	}
	
	/**
	 * 充值明细
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/czmxreport")
	public ModelAndView czmxreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/czmxreport");
	}
	/**	
	 * 获取充值明细信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/czmxgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String czmxgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.czmxgetDataList(paramap);
		return data;
	}
	/**
	 *充值统计
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/cztjreport")
	public ModelAndView cztjreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/cztjreport");
	}
	/**	
	 * 获取充值统计信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/cztjgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String cztjgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.cztjgetDataList(paramap);
		return data;
	}
	
	/**
	 * 退卡明细
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/tkmxreport")
	public ModelAndView tkmxreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/tkmxreport");
	}
	/**	
	 * 获取退卡明细信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/tkmxgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String tkmxgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.tkmxgetDataList(paramap);
		return data;
	}
	/**
	 *退卡统计
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/tktjreport")
	public ModelAndView tktjreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/tktjreport");
	}
	/**	
	 * 获取退卡统计信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/tktjgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String tktjgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.tktjgetDataList(paramap);
		return data;
	}
	
	/**
	 * 佣金明细
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/yjmxreport")
	public ModelAndView yjmxreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/yjmxreport");
	}
	/**	
	 * 获取佣金明细信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/yjmxgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String yjmxgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.yjmxgetDataList(paramap);
		return data;
	}
	/**
	 *佣金统计
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/yjtjreport")
	public ModelAndView yjtjreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/yjtjreport");
	}
	/**	
	 * 获取佣金统计信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/yjtjgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String yjtjgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.yjtjgetDataList(paramap);
		return data;
	}
	
	/**
	 * 佣金提现记录
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/yjtxjlreport")
	public ModelAndView yjtxjlreport(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/report/yjtxjlreport");
	}
	/**	
	 * 获取佣金提现记录信息
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/yjtxjlgetDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String yjtxjlgetDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=reportService.yjtxjlgetDataList(paramap);
		return data;
	}
}

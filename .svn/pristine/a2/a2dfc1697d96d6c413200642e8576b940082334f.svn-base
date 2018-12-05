package com.xincheng.web.controller.system;

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

import com.xincheng.service.system.OilService;
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/oil")
public class OilController extends BaseController{
	Logger log = LogManager.getLogger(OilController.class.getName());
	@Resource
	private OilService oilService;
	/**
	 * 石油管理首页
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/system/oil/main");
	}
	/**
	 * 根据条件获取石油数据
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/getDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String getDataList(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String data=oilService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 石油添加页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/system/oil/edit");
	}
	/**
	 * 验证名称是否重复
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/checkname")
	public @ResponseBody String checkname(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String data="";
		if (!oilService.checkName(paramap)) {
			data="true";
		}else {
			data="false";
		}
		return data;
	}
	/**
	 * 石油修改页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		Map<String,Object> oilmap=oilService.findById(id);
		modal.addAttribute("valmap",oilmap);
		return new ModelAndView("modules/system/oil/edit");
	}
	/**
	 * 石油保存--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/save")
	public @ResponseBody void save(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		if(id.equals("")){//保存
			oilService.save(paramap);
		}else{//更新
			oilService.update(paramap);
		}
	}
	/**
	 * 石油信息-删除
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody void delete(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		oilService.delete(id);
	}
}

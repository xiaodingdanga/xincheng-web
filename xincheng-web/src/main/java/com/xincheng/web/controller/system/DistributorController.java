package com.xincheng.web.controller.system;

import java.util.List;
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

import com.xincheng.service.system.DistributorService;
import com.xincheng.service.system.MemberService;
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/distributor")
public class DistributorController extends BaseController{
	Logger log = LogManager.getLogger(DistributorController.class.getName());
	
	@Resource
	private DistributorService distributorService;
	@Resource
	private MemberService memberService;
	
	/**
	 * 分销员管理首页
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/system/distributor/main");
	}
	/**
	 * 根据条件获取分销员数据
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/getDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String getDataList(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String data=distributorService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 分销员添加页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/system/distributor/edit");
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
		if (!distributorService.checkName(paramap)) {
			data="true";
		}else {
			data="false";
		}
		return data;
	}
	/**
	 * 分销员修改页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		Map<String,Object> dismap=distributorService.findById(id);
		modal.addAttribute("valmap",dismap);
		return new ModelAndView("modules/system/distributor/edit");
	}
	/**
	 * 分销员保存--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/save")
	public @ResponseBody void save(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		paramap.put("isdel", 0);
		if(id.equals("")){//保存
			distributorService.save(paramap);
		}else{//更新
			distributorService.update(paramap);
		}
	}
	/**
	 * 分销员信息-删除
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody void delete(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		Map<String, Object> dismap =distributorService.findById(id);
		dismap.put("isdel", 1);
		distributorService.update(dismap);
		List<Map<String, Object>>memberlist=memberService.findBydisid(id);
		for (int i = 0; i < memberlist.size(); i++) {
			String idstr=memberlist.get(i).get("id").toString();
			Map<String, Object> membermap=memberService.findById(idstr);
			membermap.put("disid", "");
			memberService.update(membermap);
//			String ids[]=idstr.split(",");
//			memberService.batchDelete(ids);
		}
//		distributorService.delete(id);
	}
	/**
	 * 佣金提现
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/distributorwithdrawtoedit")
	public ModelAndView distributorwithdraw(HttpServletRequest request,Model modal){
		Map<String,Object> paramap = this.getParameter();
		modal.addAttribute("valmap", paramap);
		return new ModelAndView("modules/system/distributor/distributorwithdraw");
	}
	/**
	 * 提现金额保存
	 */
	@RequestMapping(value="/distributorwithdrawsave")
	public @ResponseBody String distributorwithdrawsave(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		return distributorService.distributorwithdrawsave(paramap);
	}
}

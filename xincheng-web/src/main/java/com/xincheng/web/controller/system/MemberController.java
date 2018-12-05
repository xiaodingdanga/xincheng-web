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
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/member")
public class MemberController extends BaseController{
	Logger log = LogManager.getLogger(RoleController.class.getName());
	
	@Resource
	private MemberService memberService;
	/**
	 * 管理首页
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/member/main");
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
		String data=memberService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 添加会员卡页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpServletRequest request,Model modal){
		Map<String,Object> valmap=memberService.findLastSerialnum();
		Map<String,Object> paramap = new HashMap<String,Object>();
		if(valmap!=null){
			String serialnum = valmap.get("serialnum").toString();
			int next = Integer.parseInt(serialnum)+1;
			int length = String.valueOf(next).length();
			String newserialnum ="";
			for(int i=0;i<5-length;i++){
				newserialnum+="0";
			}
			newserialnum+=next;
			paramap.put("serialnum", newserialnum);
		}else{
			paramap.put("serialnum","00001");
		}
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/member/edit");
	}
	/**
	 * 验证商品名称是否重复
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/checkserialnum")
	public @ResponseBody String checkserialnum(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		boolean ret=!memberService.checkserialnum(paramap);
		if(ret) {
			return "true";
		}else {
			return "false";
		}
	}
	/**
	 * 会员卡保存--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/save")
	public @ResponseBody void save(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String id =paramap.get("id").toString();
		if(id.equals("")){//保存
			memberService.save(paramap);
		}else{//更新
			memberService.update(paramap);
		}
	}
	/**	
	 * 下一个
	 * @param request
	 * @param modal
	 * @return
	 */	
	@RequestMapping(value="/next",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String next(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		memberService.save(paramap);
		Map<String,Object> valmap=memberService.findLastSerialnum();
		String newserialnum ="";
		if(valmap!=null){
			String serialnum = valmap.get("serialnum").toString();
			int next = Integer.parseInt(serialnum)+1;
			int length = String.valueOf(next).length();
			for(int i=0;i<5-length;i++){
				newserialnum+="0";
			}
			newserialnum+=next;
		}else{
			newserialnum="00001";
		}
		JSONObject obj = new JSONObject();
		obj.put("serialnum", newserialnum);
		return obj.toJSONString();
	}
	/**
	 * 商品信息修改页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		paramap=memberService.findById(id);
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/member/edit");
	}
	/**
	 * 启用/停用
	 */
	@RequestMapping(value="/isuse")
	public @ResponseBody void isuse(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id = paramap.get("id").toString();
		Map<String,Object> membermap = memberService.findById(id);
		String isuse = paramap.get("isuse").toString();
		membermap.put("isuse", isuse);
		memberService.update(membermap);
	}
	/**
	 * 加载分销商
	 */
	@RequestMapping(value="/loaddis",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String loaddis(HttpServletRequest request,Model modal){
		String data= memberService.loaddis();
		return data;
	}
}

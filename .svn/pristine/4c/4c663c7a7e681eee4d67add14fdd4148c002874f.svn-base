 package com.xincheng.web.controller.system;

import java.util.List;	
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.poifs.storage.ListManagedBlock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.service.system.CustomerService;
import com.xincheng.service.system.MenuService;
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/tree")
public class TreeController extends BaseController{
	Logger log = LogManager.getLogger(TreeController.class.getName());
	@Resource
	private MenuService menuService;
	@Resource
	private CustomerService customerService;
	
	/**
	 * 菜单树--点击触发事件
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/menutree")
	public ModelAndView menutree(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/tree/menutree");
	}
	/**
	 * 获取菜单
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/menudata",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String menudata(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String pid=paramap.get("pid").toString();
		List<Map<String,Object>> list=menuService.findByPid(pid);
		JSONArray data=new JSONArray();
		for(Map<String,Object> map:list){
			JSONObject obj=new JSONObject();
			obj.put("id",map.get("id").toString());
			obj.put("pid",map.get("pid").toString());
			obj.put("name",map.get("name").toString());
			int childnum=Integer.parseInt(map.get("childnum")!=null?map.get("childnum").toString():"0");
			if(childnum==0){
				obj.put("isParent",false);
			}else{
				obj.put("isParent",true);
			}
			data.add(obj);
		}
		return data.toJSONString();
	}
	/**
	 * 油站树--点击触发事件
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/customertree")
	public ModelAndView customertree(HttpServletRequest request,Model modal){
		return new ModelAndView("modules/tree/customertree");
	}
	/**
	 * 获取商户
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/customerdata",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String customerdata(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String pid=paramap.get("pid").toString();
//		List<Map<String,Object>> list=menuService.findByPid(pid);
		List<Map<String,Object>> list = customerService.findAll();
		JSONArray data=new JSONArray();
		for(Map<String,Object> map:list){
			JSONObject obj=new JSONObject();
			obj.put("id",map.get("id").toString());
			obj.put("pid",map.get("pid").toString());
			obj.put("name",map.get("name").toString());
			int childnum=Integer.parseInt(map.get("childnum")!=null?map.get("childnum").toString():"0");
			if(childnum==0){
				obj.put("isParent",false);
			}else{
				obj.put("isParent",true);
			}
			data.add(obj);
		}
		return data.toJSONString();
	}
}
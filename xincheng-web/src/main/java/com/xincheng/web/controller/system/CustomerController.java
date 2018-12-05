package com.xincheng.web.controller.system;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.service.system.CustomerService;
import com.xincheng.service.system.IPService;
import com.xincheng.service.system.OilPriceService;
import com.xincheng.service.system.OilService;
import com.xincheng.web.controller.BaseController;

@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController{
	Logger log = LogManager.getLogger(CustomerController.class.getName());
	@Resource
	private CustomerService customerService;
	@Resource
	private IPService ipService;
	@Resource
	private OilService oilService;
	@Resource
	private OilPriceService oilPriceService;
	
	/**
	 * 加油站管理首页
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request,Model modal){
		String company=(String)request.getSession().getAttribute("company");
		JSONArray node=new JSONArray();
		JSONObject obj=new JSONObject();
		obj.put("id","0");
		obj.put("pid","");
		obj.put("name",company);
		obj.put("isParent",true);
		node.add(obj);
		modal.addAttribute("node", node);
		return new ModelAndView("modules/customer/main");
	}
	/**
	 * 根据条件获取加油张账户
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/getDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String getDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=customerService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 加油张账户添加页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/customer/add");
	}
	/**
	 * 验证账号是否重复
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/checkname",produces={"text/json;charset=UTF-8"})
	public @ResponseBody boolean checkname(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		return customerService.checkName(paramap);
	}
	/**
	 * 加油张账户保存--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/save",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void save(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		if(id.equals("")){//保存
			customerService.save(paramap);
		}else{//更新
			customerService.update(paramap);
		}
	}
	/**
	 * 用户修改页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String userid=paramap.get("id").toString();
		Map<String,Object> usermap=customerService.findById(userid);
		modal.addAttribute("valmap",usermap);
		return new ModelAndView("modules/customer/add");
	}
	/**
	 * 用户-批量删除
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/batchdelete",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void batchdelete(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String idstr=paramap.get("ids").toString();
		String ids[]=idstr.split(",");
		customerService.batchDelete(ids);
	}
	/**
	 * 加油张账户-删除
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/delete",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void delete(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		customerService.delete(id);
	}
	/**
	 * 绑定ip页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/iplook")
	public ModelAndView iplook(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String customerid=paramap.get("id").toString();
		Map<String,Object> customermap=customerService.findById(customerid);
		modal.addAttribute("valmap",customermap);
		return new ModelAndView("modules/customer/iplook");
	}
	/**
	 * 根据条件获取加油站绑定的ip
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/getIPDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String getIPDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=ipService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 添加ip页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/iptoadd")
	public ModelAndView iptoadd(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/customer/iptoadd");
	}
	/**
	 * 加油站ip保存--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/ipsave",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void ipsave(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		ipService.save(paramap);
	}
	/**
	 * 加油站ip-删除
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/ipdelete",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void ipdelete(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		ipService.delete(id);
	}
	/**
	 * 添加销售油价和有类型页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/oiltoadd")
	public ModelAndView oiltoadd(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String customerids=paramap.get("customerids").toString();
		String[] customer=customerids.split(",");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<customer.length;i++){
			Map<String,Object> map=customerService.findById(customer[i]);
			sb.append((map.get("name")==null?"":map.get("name").toString())+",");
		}
		paramap.put("customernames", sb.toString());
		modal.addAttribute("valmap",paramap);
		List<Map<String,Object>> oillist=oilService.findAll();
		modal.addAttribute("oillist",oillist);
		return new ModelAndView("modules/customer/oiltoadd");
	}
	/**
	 * 添加销售油价和有类型页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/oiltoedit")
	public ModelAndView oiltoedit(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String customerid=paramap.get("customerid").toString();
		Map<String,Object> map=customerService.findById(customerid);
		List<Map<String, Object>> oilType=customerService.findOilTypeByCustomerId(customerid);
		List<String> oiltypelist = new ArrayList<String>();
		for(Map<String, Object> oilTypemap:oilType){
			oiltypelist.add(oilTypemap.get("oilid").toString());
		}
		modal.addAttribute("oiltypelist",oiltypelist);
		
		paramap.put("customername", null==map.get("name")?"":map.get("name").toString());
		modal.addAttribute("valmap",paramap);
		
		List<Map<String,Object>> oillist=oilService.findAll();
		modal.addAttribute("oillist",oillist);
		return new ModelAndView("modules/customer/oiltoedit");
	}
	/**
	 * 加油站油类型保存--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/oilsave",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void oilsave(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		customerService.batchOilSave(paramap);
	}
	/**
	 * 添加销售油价
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/oilpricetoedit")
	public ModelAndView oilpricetoedit(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String customerid=paramap.get("customerid").toString();
		Map<String,Object> customermap=customerService.findById(customerid);
		modal.addAttribute("customermap",customermap);
		modal.addAttribute("valmap",paramap);
		return new ModelAndView("modules/customer/oilpriceedit");
	}
//	/**
//	 * 加油站油类型保存--更新
//	 * @param request
//	 * @param modal
//	 */
//	@RequestMapping(value="/oilpricesave",produces={"text/json;charset=UTF-8"})
//	public @ResponseBody void oilpricesave(HttpServletRequest request,Model modal){
//		//获取参数
//		Map<String,Object> paramap=this.getParameter();
//		customerService.batchOilPriceSave(paramap);
//	}
	/**
	 * 根据条件获取加油站油类型
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/getOilPriceDataList",produces={"text/json;charset=UTF-8"})
	public @ResponseBody String getOilPriceDataList(HttpServletRequest request,Model modal){
		Map<String,Object> paramap=this.getParameter();
		String data=oilPriceService.findPageByParam(paramap);
		return data;
	}
	/**
	 * 添加销售油价和有类型页面
	 * @param request
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="/pricetoadd")
	public ModelAndView pricetoadd(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		String id=paramap.get("id").toString();
		modal.addAttribute("valmap",paramap);
		Map<String,Object> pricemap=oilPriceService.findById(id);
		modal.addAttribute("pricemap",pricemap);
		return new ModelAndView("modules/customer/pricetoadd");
	}
	/**
	 * 加油站油价格--更新
	 * @param request
	 * @param modal
	 */
	@RequestMapping(value="/pricesave",produces={"text/json;charset=UTF-8"})
	public @ResponseBody void pricesave(HttpServletRequest request,Model modal){
		//获取参数
		Map<String,Object> paramap=this.getParameter();
		oilPriceService.save(paramap);
	}
}

package com.xincheng.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.common.MD5Util;
import com.xincheng.common.util;
import com.xincheng.dao.CustomerDao;
import com.xincheng.dao.DistributionDao;
import com.xincheng.dao.OilPriceDao;
import com.xincheng.dao.UlrDao;
import com.xincheng.dao.UserDao;
import com.xincheng.dao.WithdrawDao;
import com.xincheng.service.system.CustomerService;

@Service
@Transactional(rollbackFor=Exception.class)
public class CustomerServiceImpl implements CustomerService{
	Logger log = LogManager.getLogger(CustomerServiceImpl.class.getName());
	@Resource
	private CustomerDao customerDao;
	@Resource
	private  OilPriceDao oilPriceDao;
	
	public String findPageByParam(Map<String,Object> paramap){
		//当前每页行数
		int pageSize=Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize",pageSize);
		//当前页数
		int page=Integer.parseInt(paramap.get("page").toString());
		//总记录数 
		int totalRecord=customerDao.getCountByParam(paramap);
		//总页数
		int totalPage=0;
		//总数%每页数=余数：等于0----否则----总数%每页数=余数：不等于0
		if(totalRecord%pageSize==0){
			//总数/每页数=总页数
			totalPage=totalRecord/pageSize;
		}else{
			//总数/每页数+1=总页数
			totalPage=totalRecord/pageSize+1;
		}
		//开始记录数  
		int index=(page-1)*pageSize;
		paramap.put("start",index);
		JSONObject pagejson=new JSONObject();
		//根据条件查询
		List<Map<String,Object>> list=customerDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);//总页数
		pagejson.put("page", page);//当前页数
		pagejson.put("records", totalRecord);//总记录数
		JSONArray listjson=new JSONArray();
		for(Map<String,Object> customermap:list){//id,name,tel,address,remark,isdel,isIP,sort
			JSONObject obj=new JSONObject();
			String customerid=customermap.get("id").toString();
			obj.put("id",customerid);
			obj.put("name",customermap.get("name").toString());
			obj.put("tel",customermap.get("tel")!=null?customermap.get("tel").toString():"");
			obj.put("address",customermap.get("address")!=null?customermap.get("address").toString():"");
			obj.put("remark",customermap.get("remark")!=null?customermap.get("remark").toString():"");
			obj.put("isIP",customermap.get("isIP")!=null?customermap.get("isIP").toString():"");
			listjson.add(obj);
		}
		pagejson.put("rows",listjson);//记录列表
		return pagejson.toJSONString();
	}
	/**
	 * 验证账号是否重复
	 * @param paramap
	 * @return
	 */
	public boolean checkName(Map<String,Object> paramap){
		return customerDao.checkName(paramap);
	}
	/**
	 * 保存
	 * @param paramap
	 */
	public void save(Map<String,Object> paramap){//id,name,tel,address,remark,isdel,isIP,sort
		//保存的ID
		String id=util.getUuid();
		paramap.put("id",id);
		paramap.put("isdel","0");
		paramap.put("pid","0");
		paramap.put("childnum","0");
		int sort=customerDao.getMaxSort();
		paramap.put("sort",sort+5);
		customerDao.save(paramap);
	}
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> findById(String id){
		return customerDao.findById(id);
	}
	/**
	 * 根据账号获取用户信息
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> getByName(String account){
		return customerDao.getByName(account);
	}
	/**
	 * 更新
	 * @param paramap
	 */
	public void update(Map<String,Object> paramap){//id,name,tel,address,remark,isdel,isIP,sort
		String customerid=paramap.get("id").toString();
		Map<String,Object> customermap=customerDao.findById(customerid);
		customermap.put("name", paramap.get("name").toString());
		customermap.put("tel", paramap.get("tel").toString());
		customermap.put("address", paramap.get("address").toString());
		customermap.put("remark", paramap.get("remark").toString());
		customermap.put("isdel", "0");
		customermap.put("isIP", paramap.get("isIP").toString());
		//更新用户
		customerDao.update(customermap);
	}
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void batchDelete(String ids[]){
		//删除用户
		customerDao.batchDelete(ids);
	}
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void delete(String id){
		//删除用户
		customerDao.delete(id);
	}
	@Override
	public List<Map<String, Object>> findIPByCustomerId(String customerid) {
		return customerDao.findIPByCustomerId(customerid);
	}
	@Override
	public void oilSave(Map<String, Object> paramap) {
		
	}
	@Override
	public void batchOilSave(Map<String, Object> paramap) {
		String customerid=paramap.get("customerid").toString();
		List<Map<String, Object>> oiltype=this.findOilTypeByCustomerId(customerid);
		StringBuffer sb=new StringBuffer();
		for(Map<String, Object> map:oiltype){
			sb.append(map.get("oilid").toString());
		}
		String oilids=paramap.get("oilids")==null?"":paramap.get("oilids").toString();
		List<Map<String,Object>> addoiltypelist=new ArrayList<Map<String,Object>>();
		if(!oilids.equals("")){
			String[] oilid=oilids.split(",");
				for(int i=0;i<oilid.length;i++){//新增
					if(!sb.toString().contains(oilid[i])){
						Map<String,Object> addtypemap=new HashMap<String,Object>();
						String id=util.getUuid();
						addtypemap.put("id", id);
						addtypemap.put("customerid", customerid);
						addtypemap.put("oilid", oilid[i]);
						addtypemap.put("state", "0");
						addoiltypelist.add(addtypemap);
					}
				}
			if(addoiltypelist!=null&&addoiltypelist.size()>0){
				oilPriceDao.batchOilSave(addoiltypelist);
			}
			for(Map<String, Object> map:oiltype){//不启用有类型
				String oldoilid =map.get("oilid").toString();
				if(!oilids.contains(oldoilid)){
					map.put("state", "1");
					oilPriceDao.update(map);
				}
			}
		}
	}
	@Override
	public List<Map<String, Object>> findOilTypeByCustomerId(String customerid) {
		return oilPriceDao.findOilTypeByCustomerId(customerid);
	}
	@Override
	public void batchOilPriceSave(Map<String, Object> paramap) {
//		String customerid=paramap.get("customerid").toString();
//		String ids=paramap.get("ids")==null?"":paramap.get("ids").toString();
//		String prices=paramap.get("prices")==null?"":paramap.get("prices").toString();
//		if(!("".equals(ids))&&!("".equals(prices))){
//			String[] id=ids.split(",");
//			String[] price=prices.split(",");
//			if(id.length==price.length){
//				for(int i=0;i<id.length;i++){
//					Map<String,Object> cormap=oilPriceDao.findById(id[i]);
//					1
//				}
//				
//			}
//		}
	}
	@Override
	public List<Map<String, Object>> findAll() {
		return customerDao.findAll();
	}
}

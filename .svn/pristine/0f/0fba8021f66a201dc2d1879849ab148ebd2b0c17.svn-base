package com.xincheng.service.system.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.common.util;
import com.xincheng.dao.IPDao;
import com.xincheng.service.system.IPService;

@Service
@Transactional(rollbackFor=Exception.class)
public class IPServiceImpl implements IPService{
	Logger log = LogManager.getLogger(IPServiceImpl.class.getName());
	@Resource
	private IPDao ipDao;
	
	public String findPageByParam(Map<String,Object> paramap){
		//当前每页行数
		int pageSize=Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize",pageSize);
		//当前页数
		int page=Integer.parseInt(paramap.get("page").toString());
		//总记录数 
		int totalRecord=ipDao.getCountByParam(paramap);
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
		List<Map<String,Object>> list=ipDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);//总页数
		pagejson.put("page", page);//当前页数
		pagejson.put("records", totalRecord);//总记录数
		JSONArray listjson=new JSONArray();
		for(Map<String,Object> ipmap:list){
			JSONObject obj=new JSONObject();
			String ipid=ipmap.get("id").toString();
			obj.put("id",ipid);
			obj.put("ip",ipmap.get("ip")!=null?ipmap.get("ip").toString():"");
			obj.put("customername",ipmap.get("customername")!=null?ipmap.get("customername").toString():"");
			obj.put("customerid",ipmap.get("customerid")!=null?ipmap.get("customerid").toString():"");
			listjson.add(obj);
		}
		pagejson.put("rows",listjson);//记录列表
		return pagejson.toJSONString();
	}
	/**
	 * 保存
	 * @param paramap
	 */
	public void save(Map<String,Object> paramap){
		//保存的ID
		String id=util.getUuid();
		paramap.put("id",id);
		ipDao.save(paramap);
	}
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> findById(String id){
		return ipDao.findById(id);
	}
	/**
	 * 更新
	 * @param paramap
	 */
	public void update(Map<String,Object> paramap){
		String ipid=paramap.get("id").toString();
		Map<String,Object> usermap=ipDao.findById(ipid);
		usermap.put("ip", paramap.get("ip").toString());
		//更新用户
		ipDao.update(usermap);
	}
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void batchDelete(String ids[]){
		//删除用户
		ipDao.batchDelete(ids);
	}
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void delete(String id){
		//删除用户
		ipDao.delete(id);
	}
}

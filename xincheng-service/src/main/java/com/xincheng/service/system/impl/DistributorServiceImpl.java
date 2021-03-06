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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.common.util;
import com.xincheng.dao.DistributionDao;
import com.xincheng.dao.DistributorDao;
import com.xincheng.dao.WithdrawDao;
import com.xincheng.service.system.DistributorService;

@Service
@Transactional(rollbackFor = Exception.class)
public class DistributorServiceImpl implements DistributorService {

	Logger log = LogManager.getLogger(DistributorServiceImpl.class.getName());

	@Resource
	private DistributorDao distributorDao;
	@Resource 
	private WithdrawDao withdrawDao;
	@Resource
	private DistributionDao distributionDao;

	@Override
	public String findPageByParam(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = distributorDao.getCountByParam(paramap);
		// 总页数
		int totalPage = 0;
		// 总数%每页数=余数：等于0----否则----总数%每页数=余数：不等于0
		if (totalRecord % pageSize == 0) {
			// 总数/每页数=总页数
			totalPage = totalRecord / pageSize;
		} else {
			// 总数/每页数+1=总页数
			totalPage = totalRecord / pageSize + 1;
		}
		// 开始记录数
		int index = (page - 1) * pageSize;
		paramap.put("start", index);
		JSONObject pagejson = new JSONObject();
		// 根据条件查询
		List<Map<String, Object>> list = distributorDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> distributormap : list) {
			JSONObject obj = new JSONObject();
			String disid=distributormap.get("id").toString();
			float money=getmoney(disid);
			obj.put("id", disid);
			obj.put("name", distributormap.get("name") != null ? distributormap.get("name").toString() : "");
			obj.put("tel", distributormap.get("tel") != null ? distributormap.get("tel").toString() : "");
			obj.put("money",money);//distributormap.get("money") != null ? distributormap.get("money").toString() : ""
			obj.put("address", distributormap.get("address") != null ? distributormap.get("address").toString() : "");
			obj.put("remark", distributormap.get("name") != null ? distributormap.get("remark").toString() : "");
			obj.put("sex", distributormap.get("sex") != null ? distributormap.get("sex").toString() : "");
			obj.put("isdel", distributormap.get("isdel") != null ? distributormap.get("isdel").toString() : "");
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}

	@Override
	public boolean checkName(Map<String, Object> paramap) {
		return distributorDao.checkName(paramap);
	}

	@Override
	public void save(Map<String, Object> paramap) {
		// 保存的ID
		String id = util.getUuid();
		paramap.put("id", id);
		paramap.put("money", 0);
		distributorDao.save(paramap);
	}

	@Override
	public void update(Map<String, Object> paramap) {
		String id=paramap.get("id").toString();
		Map<String,Object> distributormap=distributorDao.findById(id);
		distributormap.put("name",paramap.get("name")!=null? paramap.get("name").toString():"");
		distributormap.put("tel", paramap.get("tel").toString());
		distributormap.put("sex",paramap.get("sex")!=null? paramap.get("sex").toString():"");
		distributormap.put("money",0);
		distributormap.put("address",paramap.get("address")!=null? paramap.get("address").toString():"");
		distributormap.put("remark",paramap.get("remark")!=null? paramap.get("remark").toString():"");
		distributormap.put("isdel", paramap.get("isdel").toString());
		//更新用户
		distributorDao.update(distributormap);
	}

	@Override
	public Map<String, Object> findById(String id) {
		return distributorDao.findById(id);
	}

	@Override
	public void delete(String id) {
		distributorDao.delete(id);
	}

	@Override
	public @ResponseBody String distributorwithdrawsave(Map<String, Object> paramap) {
		String data="";
		String id = util.getUuid();
		String disid = paramap.get("id").toString();
		Map<String, Object> dismap=distributorDao.findById(disid);
//		float oldmoney=Float.parseFloat(dismap.get("money").toString());
		float oldmoney=getmoney(disid);
		float money = Float.parseFloat(paramap.get("price").toString());
		if (oldmoney >= money) {
			float newmoney = (float) (oldmoney - money);
			paramap.put("id", id);
			paramap.put("disid", disid);
			paramap.put("oldmoney", oldmoney);
			paramap.put("money", money);
			paramap.put("newmoney", newmoney);
			paramap.put("createdata", util.datetostring(new Date(), "yyyy-MM-dd HH:mm:ss"));
			withdrawDao.save(paramap);
			dismap.put("money", newmoney);
			distributorDao.update(dismap);
			data="0";
		}else {
			data="1";
		}
		return data;
	}
	/**
	 * 获取佣金
	 */
	public float getmoney(String disid){
		float money=0;
		float tixianmoney=0;
		Map<String,Object> dismap  = new HashMap<String,Object>();
		Map<String,Object> widthsmap  = new HashMap<String,Object>();
		dismap.put("disid", disid);
		widthsmap.put("disid", disid);
		List<Map<String,Object>> dislist = new ArrayList<Map<String,Object>>();
		dislist = distributionDao.findcountmoney(dismap);//查询提成总金额
		if(dislist.size()!=0){
			List<Map<String,Object>> withlist = withdrawDao.findcountmoney(widthsmap);
			if(withlist.size()!=0){
				widthsmap = withlist.get(0);
				tixianmoney = Float.parseFloat(widthsmap.get("money").toString());//提现金额
			}
			dismap = dislist.get(0);
			money = Float.parseFloat(dismap.get("money").toString());//提成总金额
			money = money-tixianmoney;
		}
		return money;
	}

}

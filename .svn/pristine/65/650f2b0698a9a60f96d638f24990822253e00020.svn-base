package com.xincheng.service.system.impl;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.common.util;
import com.xincheng.dao.MemberDao;
import com.xincheng.dao.RechargeDao;
import com.xincheng.service.system.MemberService;
import com.xincheng.service.system.RechargeService;

@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeServiceImpl implements RechargeService {

	Logger log = LogManager.getLogger(RechargeServiceImpl.class.getName());

	@Resource
	private RechargeDao rechargeDao;
	@Resource
	private MemberDao memberDao;

	public String findPageByParam(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = rechargeDao.getCountByParam(paramap);
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
		List<Map<String, Object>> list = rechargeDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> membermap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == membermap.get("id") ? "" : membermap.get("id").toString());
			obj.put("serialnum", null == membermap.get("serialnum") ? "" : membermap.get("serialnum").toString());
			obj.put("chipnum", null == membermap.get("chipnum") ? "" : membermap.get("chipnum").toString());
			obj.put("state", null == membermap.get("state") ? "" : membermap.get("state").toString());
			obj.put("name", null == membermap.get("name") ? "" : membermap.get("name").toString());
			obj.put("tel", null == membermap.get("tel") ? "" : membermap.get("tel").toString());
			obj.put("cardtype", null == membermap.get("cardtype") ? "" : membermap.get("cardtype").toString());
			obj.put("money", null == membermap.get("money") ? "" : membermap.get("money").toString());
			obj.put("isuse", null == membermap.get("isuse") ? "" : membermap.get("isuse").toString());
			obj.put("createdate", null == membermap.get("createdate") ? "" : membermap.get("createdate").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	/**
	 * 保存
	 * @param paramap
	 */
	public void save(Map<String, Object> paramap) {
		String memberid = paramap.get("id").toString();
		Map<String,Object> membermap  =memberDao.findById(memberid);
		float money = Float.parseFloat(membermap.get("money").toString());
		float price = Float.parseFloat(paramap.get("price").toString());
		float almoney = money+price;
		membermap.put("money",almoney);
		memberDao.update(membermap);
		String id = util.getUuid();
		Map<String,Object> rechargemap = new HashMap<String,Object>();
		rechargemap.put("id",id);
		rechargemap.put("memberid",memberid);
		rechargemap.put("money",money);
		rechargemap.put("price",price);
		rechargemap.put("almoney",almoney);
		rechargemap.put("type","0");
		rechargemap.put("createdate",util.datetostring(new Date(),"yyyy-MM-dd HH:mm:ss"));
		rechargeDao.save(rechargemap);
	}
}

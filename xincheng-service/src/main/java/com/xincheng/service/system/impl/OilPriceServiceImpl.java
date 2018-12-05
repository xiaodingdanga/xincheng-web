package com.xincheng.service.system.impl;

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
import com.xincheng.common.util;
import com.xincheng.dao.OilPriceDao;
import com.xincheng.service.system.OilPriceService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OilPriceServiceImpl implements OilPriceService {

	Logger log = LogManager.getLogger(OilPriceServiceImpl.class.getName());

	@Resource
	private OilPriceDao oilPriceDao;

	@Override
	public String findPageByParam(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = oilPriceDao.getCountByParam(paramap);
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
		List<Map<String, Object>> list = oilPriceDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> oilmap : list) {//id,oilid,customerid,outprice,inprice,state,createdate,customername,oilname
			JSONObject obj = new JSONObject();
			obj.put("id", oilmap.get("id").toString());
			obj.put("oilid", oilmap.get("oilid") != null ? oilmap.get("oilid").toString() : "");
			obj.put("customerid", oilmap.get("customerid") != null ? oilmap.get("customerid").toString() : "");
			obj.put("outprice", oilmap.get("outprice") != null ? oilmap.get("outprice").toString() : "");
			obj.put("inprice", oilmap.get("inprice") != null ? oilmap.get("inprice").toString() : "");
			obj.put("createdate", oilmap.get("createdate") != null ? oilmap.get("createdate").toString() : "");
			obj.put("customername", oilmap.get("customername") != null ? oilmap.get("customername").toString() : "");
			obj.put("oilname", oilmap.get("oilname") != null ? oilmap.get("oilname").toString() : "");
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	@Override
	public void save(Map<String, Object> paramap) {
		//之前的价格设置无效
		paramap.put("state", "1");
		oilPriceDao.update(paramap);
		//保存
		String oldid=paramap.get("id").toString();
		Map<String,Object> pricemap= oilPriceDao.findById(oldid);
		pricemap.put("id", util.getUuid());
		pricemap.put("outprice", paramap.get("outprice").toString());
		pricemap.put("inprice",paramap.get("inprice").toString());
		pricemap.put("state", "0");
		pricemap.put("createdate", util.datetostring(new Date(), "yyyy-MM-dd HH-mm-ss"));
		oilPriceDao.save(pricemap);
	}

	@Override
	public void update(Map<String, Object> paramap) {
	}

	@Override
	public Map<String, Object> findById(String id) {
		return oilPriceDao.findById(id);
	}

}

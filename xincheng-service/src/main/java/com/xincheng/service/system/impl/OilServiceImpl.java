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
import com.xincheng.dao.OilDao;
import com.xincheng.service.system.OilService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OilServiceImpl implements OilService {

	Logger log = LogManager.getLogger(OilServiceImpl.class.getName());

	@Resource
	private OilDao oilDao;

	@Override
	public String findPageByParam(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = oilDao.getCountByParam(paramap);
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
		List<Map<String, Object>> list = oilDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> oilmap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", oilmap.get("id").toString());
			obj.put("name", oilmap.get("name") != null ? oilmap.get("name").toString() : "");
			obj.put("disprice", oilmap.get("disprice") != null ? oilmap.get("disprice").toString() : "");
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}

	@Override
	public boolean checkName(Map<String, Object> paramap) {
		return oilDao.checkName(paramap);
	}

	@Override
	public void save(Map<String, Object> paramap) {
		// 保存的ID
		String id = util.getUuid();
		paramap.put("id", id);
		oilDao.save(paramap);
	}

	@Override
	public void update(Map<String, Object> paramap) {
		String id=paramap.get("id").toString();
		Map<String,Object> usermap=oilDao.findById(id);
		usermap.put("name", paramap.get("name").toString());
		usermap.put("disprice", paramap.get("disprice").toString());
		//更新用户
		oilDao.update(usermap);
	}

	@Override
	public Map<String, Object> findById(String id) {
		return oilDao.findById(id);
	}

	@Override
	public void delete(String id) {
		oilDao.delete(id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return oilDao.findAll();
	}
}

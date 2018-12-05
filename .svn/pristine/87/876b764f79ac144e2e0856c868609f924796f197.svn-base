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
import com.xincheng.dao.ConsumptionDao;
import com.xincheng.dao.DistributionDao;
import com.xincheng.dao.MemberDao;
import com.xincheng.dao.RechargeDao;
import com.xincheng.dao.UserDao;
import com.xincheng.dao.WithdrawDao;
import com.xincheng.service.system.MemberService;
import com.xincheng.service.system.ReportService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

	Logger log = LogManager.getLogger(ReportServiceImpl.class.getName());

	@Resource
	private ConsumptionDao consumptionDao;
	@Resource
	private RechargeDao rechargeDao;
	@Resource
	private DistributionDao distributionDao;
	@Resource
	private WithdrawDao withdrawDao;
	/**
	 * 消费明细
	 */
	public String xfmxgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = consumptionDao.getxfmxCountByParam(paramap);
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
		List<Map<String, Object>> list = consumptionDao.findxfmxPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> consumptionmap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == consumptionmap.get("id") ? "" : consumptionmap.get("id").toString());
			obj.put("memberid", null == consumptionmap.get("memberid") ? "" : consumptionmap.get("memberid").toString());
			obj.put("price", null == consumptionmap.get("price") ? "" : consumptionmap.get("price").toString());
			obj.put("realprice", null == consumptionmap.get("realprice") ? "" : consumptionmap.get("realprice").toString());
			obj.put("oldmoney", null == consumptionmap.get("oldmoney") ? "" : consumptionmap.get("oldmoney").toString());
			obj.put("newmoney", null == consumptionmap.get("newmoney") ? "" : consumptionmap.get("newmoney").toString());
			obj.put("inprice", null == consumptionmap.get("inprice") ? "" : consumptionmap.get("inprice").toString());
			obj.put("outprice", null == consumptionmap.get("outprice") ? "" : consumptionmap.get("outprice").toString());
			obj.put("createdate", null == consumptionmap.get("createdate") ? "" : consumptionmap.get("createdate").toString());
			obj.put("serialnum", null == consumptionmap.get("serialnum") ? "" : consumptionmap.get("serialnum").toString());
			obj.put("name", null == consumptionmap.get("name") ? "" : consumptionmap.get("name").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	/**
	 * 消费统计
	 */
	public String xftjgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
				int pageSize = Integer.parseInt(paramap.get("rows").toString());
				paramap.put("pageSize", pageSize);
				// 当前页数
				int page = Integer.parseInt(paramap.get("page").toString());
				// 总记录数
				int totalRecord = consumptionDao.getxftjCountByParam(paramap);
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
				List<Map<String, Object>> list = consumptionDao.findxftjPageByParam(paramap);
				pagejson.put("total", totalPage);// 总页数
				pagejson.put("page", page);// 当前页数
				pagejson.put("records", totalRecord);// 总记录数
				JSONArray listjson = new JSONArray();
				for (Map<String, Object> consumptionmap : list) {
					JSONObject obj = new JSONObject();
					obj.put("id", null == consumptionmap.get("id") ? "" : consumptionmap.get("id").toString());
					obj.put("inprice", null == consumptionmap.get("inprice") ? "" : consumptionmap.get("inprice").toString());
					obj.put("outprice", null == consumptionmap.get("outprice") ? "" : consumptionmap.get("outprice").toString());
					obj.put("price", null == consumptionmap.get("price") ? "" : consumptionmap.get("price").toString());
					obj.put("realprice", null == consumptionmap.get("realprice") ? "" : consumptionmap.get("realprice").toString());
					listjson.add(obj);
				}
				pagejson.put("rows", listjson);// 记录列表
				return pagejson.toJSONString();
	}

	/**
	 * 充值明细
	 */
	public String czmxgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = rechargeDao.getczmxCountByParam(paramap);
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
		List<Map<String, Object>> list = rechargeDao.findczmxPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> rechargemap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == rechargemap.get("id") ? "" : rechargemap.get("id").toString());
			obj.put("username", null == rechargemap.get("username") ? "" : rechargemap.get("username").toString());
			obj.put("serialnum", null == rechargemap.get("serialnum") ? "" : rechargemap.get("serialnum").toString());
			obj.put("money", null == rechargemap.get("money") ? "" : rechargemap.get("money").toString());
			obj.put("price", null == rechargemap.get("price") ? "" : rechargemap.get("price").toString());
			obj.put("almoney", null == rechargemap.get("almoney") ? "" : rechargemap.get("almoney").toString());
			obj.put("type", null == rechargemap.get("type") ? "" : rechargemap.get("type").toString());
			obj.put("createdate", null == rechargemap.get("createdate") ? "" : rechargemap.get("createdate").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	/**
	 * 充值统计
	 */
	public String cztjgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = rechargeDao.getcztjCountByParam(paramap);
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
		List<Map<String, Object>> list = rechargeDao.findcztjPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> rechargemap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == rechargemap.get("id") ? "" : rechargemap.get("id").toString());
			obj.put("name", null == rechargemap.get("name") ? "" : rechargemap.get("name").toString());
			obj.put("serialnum", null == rechargemap.get("serialnum") ? "" : rechargemap.get("serialnum").toString());
			obj.put("money", null == rechargemap.get("money") ? "" : rechargemap.get("money").toString());
			obj.put("price", null == rechargemap.get("price") ? "" : rechargemap.get("price").toString());
			obj.put("almoney", null == rechargemap.get("almoney") ? "" : rechargemap.get("almoney").toString());
			obj.put("createdate", null == rechargemap.get("createdate") ? "" : rechargemap.get("createdate").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	
	/**
	 * 退卡明细
	 */
	public String tkmxgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = rechargeDao.gettkmxCountByParam(paramap);
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
		List<Map<String, Object>> list = rechargeDao.findtkmxPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> rechargemap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == rechargemap.get("id") ? "" : rechargemap.get("id").toString());
			obj.put("name", null == rechargemap.get("name") ? "" : rechargemap.get("name").toString());
			obj.put("serialnum", null == rechargemap.get("serialnum") ? "" : rechargemap.get("serialnum").toString());
			obj.put("money", null == rechargemap.get("money") ? "" : rechargemap.get("money").toString());
			obj.put("price", null == rechargemap.get("price") ? "" : rechargemap.get("price").toString());
			obj.put("almoney", null == rechargemap.get("almoney") ? "" : rechargemap.get("almoney").toString());
			obj.put("type", null == rechargemap.get("type") ? "" : rechargemap.get("type").toString());
			obj.put("createdate", null == rechargemap.get("createdate") ? "" : rechargemap.get("createdate").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	/**
	 * 退卡统计
	 */
	public String tktjgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = rechargeDao.gettktjCountByParam(paramap);
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
		List<Map<String, Object>> list = rechargeDao.findtktjPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> rechargemap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == rechargemap.get("id") ? "" : rechargemap.get("id").toString());
			obj.put("name", null == rechargemap.get("name") ? "" : rechargemap.get("name").toString());
			obj.put("serialnum", null == rechargemap.get("serialnum") ? "" : rechargemap.get("serialnum").toString());
			obj.put("money", null == rechargemap.get("money") ? "" : rechargemap.get("money").toString());
			obj.put("price", null == rechargemap.get("price") ? "" : rechargemap.get("price").toString());
			obj.put("almoney", null == rechargemap.get("almoney") ? "" : rechargemap.get("almoney").toString());
			obj.put("createdate", null == rechargemap.get("createdate") ? "" : rechargemap.get("createdate").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
	
	/**
	 * 佣金明细
	 */
	public String yjmxgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
				int pageSize = Integer.parseInt(paramap.get("rows").toString());
				paramap.put("pageSize", pageSize);
				// 当前页数
				int page = Integer.parseInt(paramap.get("page").toString());
				// 总记录数
				int totalRecord = distributionDao.getyjmxCountByParam(paramap);
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
				List<Map<String, Object>> list = distributionDao.findyjmxPageByParam(paramap);
				pagejson.put("total", totalPage);// 总页数
				pagejson.put("page", page);// 当前页数
				pagejson.put("records", totalRecord);// 总记录数
				JSONArray listjson = new JSONArray();
				for (Map<String, Object> distributionmap : list) {
					JSONObject obj = new JSONObject();
					obj.put("id", null == distributionmap.get("id") ? "" : distributionmap.get("id").toString());
					obj.put("userid", null == distributionmap.get("userid") ? "" : distributionmap.get("userid").toString());
					obj.put("username", null == distributionmap.get("username") ? "" : distributionmap.get("username").toString());
					obj.put("volume", null == distributionmap.get("volume") ? "" : distributionmap.get("volume").toString());
					obj.put("commission", null == distributionmap.get("commission") ? "" : distributionmap.get("commission").toString());
					obj.put("money", null == distributionmap.get("money") ? "" : distributionmap.get("money").toString());
					obj.put("createdate", null == distributionmap.get("createdate") ? "" : distributionmap.get("createdate").toString());
					listjson.add(obj);
				}
				pagejson.put("rows", listjson);// 记录列表
				return pagejson.toJSONString();
	}
	/**
	 * 佣金统计
	 */
	public String yjtjgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
				int pageSize = Integer.parseInt(paramap.get("rows").toString());
				paramap.put("pageSize", pageSize);
				// 当前页数
				int page = Integer.parseInt(paramap.get("page").toString());
				// 总记录数
				int totalRecord = distributionDao.getyjtjCountByParam(paramap);
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
				List<Map<String, Object>> list = distributionDao.findyjtjPageByParam(paramap);
				pagejson.put("total", totalPage);// 总页数
				pagejson.put("page", page);// 当前页数
				pagejson.put("records", totalRecord);// 总记录数
				JSONArray listjson = new JSONArray();
				for (Map<String, Object> distributionmap : list) {
					JSONObject obj = new JSONObject();
					obj.put("id", null == distributionmap.get("id") ? "" : distributionmap.get("id").toString());
					obj.put("username", null == distributionmap.get("username") ? "" : distributionmap.get("username").toString());
					obj.put("money", null == distributionmap.get("money") ? "" : distributionmap.get("money").toString());
					listjson.add(obj);
				}
				pagejson.put("rows", listjson);// 记录列表
				return pagejson.toJSONString();
	}
	/**
	 * 佣金提现记录
	 */
	public String yjtxjlgetDataList(Map<String, Object> paramap) {
		// 当前每页行数
		int pageSize = Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize", pageSize);
		// 当前页数
		int page = Integer.parseInt(paramap.get("page").toString());
		// 总记录数
		int totalRecord = withdrawDao.getyjtxjlCountByParam(paramap);
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
		List<Map<String, Object>> list = withdrawDao.findyjtxjlPageByParam(paramap);
		pagejson.put("total", totalPage);// 总页数
		pagejson.put("page", page);// 当前页数
		pagejson.put("records", totalRecord);// 总记录数
		JSONArray listjson = new JSONArray();
		for (Map<String, Object> withdrawmap : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", null == withdrawmap.get("id") ? "" : withdrawmap.get("id").toString());
			obj.put("username", null == withdrawmap.get("username") ? "" :withdrawmap.get("username").toString());
			obj.put("money", null == withdrawmap.get("money") ? "" : withdrawmap.get("money").toString());
			obj.put("createdate", null == withdrawmap.get("createdate") ? "" : withdrawmap.get("createdate").toString());
			listjson.add(obj);
		}
		pagejson.put("rows", listjson);// 记录列表
		return pagejson.toJSONString();
	}
}

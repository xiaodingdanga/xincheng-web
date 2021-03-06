package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xincheng.basedao.BaseDao;

public interface DistributionDao extends BaseDao {
	/**
	 * 报表
	 */
	public List<Map<String,Object>> findcountmoney(Map<String, Object> paramap);
	/**
	 * 佣金明细
	 * @param paramap
	 * @return
	 */
	public int getyjmxCountByParam(Map<String,Object> paramap);
	/**
	 * 佣金明细
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findyjmxPageByParam(Map<String,Object> paramap);
	/**
	 * 佣金统计
	 * @param paramap
	 * @return
	 */
	public int getyjtjCountByParam(Map<String,Object> paramap);
	/**
	 *佣金统计
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findyjtjPageByParam(Map<String,Object> paramap);
}

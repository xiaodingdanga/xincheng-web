package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xincheng.basedao.BaseDao;

public interface ConsumptionDao extends BaseDao {
	/**
	 * 保存分销记录
	 */
	public void savedis(Map<String,Object> paramap);
	/**
	 * 消费明细
	 * @param paramap
	 * @return
	 */
	public int getxfmxCountByParam(Map<String,Object> paramap);
	/**
	 * 消费明细
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findxfmxPageByParam(Map<String,Object> paramap);
	/**
	 * 消费统计
	 * @param paramap
	 * @return
	 */
	public int getxftjCountByParam(Map<String,Object> paramap);
	/**
	 * 消费统计
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findxftjPageByParam(Map<String,Object> paramap);
	/**
	 * 根据paramap查询油站拥有的油
	 */
	public List<Map<String,Object>> findoilbyparamap(Map<String,Object> paramap);
}

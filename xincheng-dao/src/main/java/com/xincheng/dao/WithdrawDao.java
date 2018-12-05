package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xincheng.basedao.BaseDao;

public interface WithdrawDao extends BaseDao {
	/**
	 * 报表
	 */
	public List<Map<String,Object>> findcountmoney(Map<String,Object> paramap);
	/**
	 * 佣金提现记录
	 * @param paramap
	 * @return
	 */
	public int getyjtxjlCountByParam(Map<String,Object> paramap);
	/**
	 * 佣金提现记录
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findyjtxjlPageByParam(Map<String,Object> paramap);
}

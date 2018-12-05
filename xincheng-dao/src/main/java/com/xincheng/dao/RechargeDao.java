package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xincheng.basedao.BaseDao;

public interface RechargeDao extends BaseDao {
	/**
	 * 充值明细
	 * @param paramap
	 * @return
	 */
	public int getczmxCountByParam(Map<String,Object> paramap);
	/**
	 * 充值明细
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findczmxPageByParam(Map<String,Object> paramap);
	/**
	 * 充值统计
	 * @param paramap
	 * @return
	 */
	public int getcztjCountByParam(Map<String,Object> paramap);
	/**
	 *充值统计
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findcztjPageByParam(Map<String,Object> paramap);
	
	/**
	 *  退卡明细
	 * @param paramap
	 * @return
	 */
	public int gettkmxCountByParam(Map<String,Object> paramap);
	/**
	 *  退卡明细
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findtkmxPageByParam(Map<String,Object> paramap);
	/**
	 * 退卡统计
	 * @param paramap
	 * @return
	 */
	public int gettktjCountByParam(Map<String,Object> paramap);
	/**
	 * 退卡统计
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findtktjPageByParam(Map<String,Object> paramap);
}

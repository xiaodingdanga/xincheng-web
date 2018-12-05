package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xincheng.basedao.BaseDao;
@Repository
public interface OilPriceDao extends BaseDao {
	/**
	 * 获取排序最大值
	 * @return
	 */
	public int getMaxSort();
	/**
	 * 根据账号获取用户信息
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> getByName(@Param(value="name") String name);
	/**
	 * 根据customerid数组查询加油站绑定的ip
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> findIPByCustomerId(@Param(value="customerid") String customerid);
	/**
	 * 保存加油站的有类型
	 * @param paramap
	 */
	public void oilSave(Map<String,Object> paramap);
	/**
	 * 批量保存加油站的有类型
	 * @param datalist
	 */
	public void batchOilSave(List<Map<String,Object>> datalist);
	/**
	 * 根据customerid数组查询加油站类型
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> findOilTypeByCustomerId(String customerid);
	/**
	 * 批量保存加油站的有类型
	 * @param datalist
	 */
	public void batchOilPriceSave(List<Map<String,Object>> datalist);
	/**
	 * 根据条件更ixn加油站的有类型
	 * @param datalist
	 */
	public void updateByOilidAndCustomeerid(Map<String,Object> paramap);
	
}

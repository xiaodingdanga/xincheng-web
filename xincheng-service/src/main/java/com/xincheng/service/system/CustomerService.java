package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 加油张管理
 *
 */
public interface CustomerService{
	/**
	 * 根据账号获取用户信息
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> getByName(String name);
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> findById(@Param(value="id") String id);
	/**
	 * 分页查询
	 * @param paramap
	 * @return
	 */
	public String findPageByParam(Map<String,Object> paramap);
	/**
	 * 验证账号是否重复
	 * @param paramap
	 * @return
	 */
	public boolean checkName(Map<String,Object> paramap);
	/**
	 * 保存数据
	 * @param paramap
	 */
	public void save(Map<String,Object> paramap);
	/**
	 * 修改数据
	 * @param paramap
	 */
	public void update(Map<String,Object> paramap);
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void batchDelete(String ids[]);
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void delete(String id);
	/**
	 * 根据customerid数组查询加油站绑定的ip
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> findIPByCustomerId(String customerid);
	/**
	 * 保存油类型
	 * @param paramap
	 */
	public void oilSave(Map<String,Object> paramap);
	/**
	 * 批量保存油类型
	 * @param paramap
	 */
	public void batchOilSave(Map<String,Object> paramap);
	/**
	 * 批量保存油类型
	 * @param paramap
	 */
	public void batchOilPriceSave(Map<String,Object> paramap);
	/**
	 * 根据customerid数组查询加油站类型
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> findOilTypeByCustomerId(String customerid);
	/**
	 * 查询所有
	 * @return
	 */
	public List<Map<String,Object>> findAll();
	
}

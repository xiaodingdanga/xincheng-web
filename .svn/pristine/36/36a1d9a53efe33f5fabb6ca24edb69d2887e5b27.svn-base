package com.xincheng.service.system;

import java.util.Map;

public interface DistributorService {
	/**
	 * 分页查询
	 * @param paramap
	 * @return
	 */
	String findPageByParam(Map<String, Object> paramap);
	/**
	 * 验证名称是否重复
	 * @param paramap
	 * @return
	 */
	boolean checkName(Map<String, Object> paramap);
	/**
	 * 保存数据
	 * @param paramap
	 */
	void save(Map<String, Object> paramap);
	/**
	 * 修改数据
	 * @param paramap
	 */
	void update(Map<String, Object> paramap);
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	Map<String, Object> findById(String id);
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	void delete(String id);
	/**
	 * 提现金额保存
	 * @return 
	 */
	String distributorwithdrawsave(Map<String, Object> paramap);

}

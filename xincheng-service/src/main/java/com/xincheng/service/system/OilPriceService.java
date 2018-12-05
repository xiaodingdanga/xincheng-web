package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

public interface OilPriceService {
	/**
	 * 分页查询
	 * @param paramap
	 * @return
	 */
	String findPageByParam(Map<String, Object> paramap);
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

}

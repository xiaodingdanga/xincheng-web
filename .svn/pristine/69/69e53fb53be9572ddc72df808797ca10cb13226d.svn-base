package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IPService{
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> findById(@Param(value="id") String id);
	/**
	 * 分页查询IP
	 * @param paramap
	 * @return
	 */
	public String findPageByParam(Map<String,Object> paramap);
	/**
	 * 保存数据IP
	 * @param paramap
	 */
	public void save(Map<String,Object> paramap);
	/**
	 * 修改数据IP
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
}

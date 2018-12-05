package com.xincheng.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xincheng.basedao.BaseDao;

@Repository
public interface UserDao extends BaseDao{
	/**
	 * 获取排序最大值
	 * @return
	 */
	public int getMaxSort();
	/**
	 * 重置密码
	 * @param paramap
	 */
	public void resetPassword(Map<String,Object> paramap);
	/**
	 * 根据账号获取用户信息
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> getByAccount(@Param(value="account") String account);
	/**
	 * 根据用户ID数组查询用户集合
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> findByUserIds(String userid[]);
}

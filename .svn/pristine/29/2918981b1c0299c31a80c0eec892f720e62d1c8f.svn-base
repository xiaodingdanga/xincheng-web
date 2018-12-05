package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xincheng.basedao.BaseDao;

@Repository
public interface RoleDao extends BaseDao{
	/**
	 * 获取排序最大值
	 * @return
	 */
	public int getMaxSort(Map<String,Object> paramap);
	/**
	 * 根据用户ID查询所有角色
	 * @param rolemap
	 * @return
	 */
	public List<Map<String,Object>> findRoleByUserid(@Param(value="userid")String userid);
	/**
	 * 根据des查询role
	 * @param des
	 * @return
	 */
	public Map<String,Object> findByDes(@Param(value="des")String des);
	
}

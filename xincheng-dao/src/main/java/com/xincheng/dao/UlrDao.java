package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xincheng.basedao.BaseDao;

@Repository
public interface UlrDao extends BaseDao{
	/**
	 * 根据角色ID删除用户角色关联--批量删除
	 * @param paramap
	 */
	public void deleteByRoleIds(String roleid[]);
	/**
	 * 根据角色ID获取关联用户
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findUsersByRoleid(@Param(value="roleid") String roleid);
	/**
	 * 根据用户id删除--批量删除
	 * @param ids
	 */
	public void deleteByUserIds(String ids[]);
}

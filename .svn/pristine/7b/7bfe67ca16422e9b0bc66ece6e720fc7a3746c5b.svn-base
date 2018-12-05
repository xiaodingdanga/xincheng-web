package com.xincheng.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xincheng.basedao.BaseDao;

@Repository
public interface PermDao extends BaseDao{
	/**
	 * 根据角色ID删除权限--批量删除
	 * @return
	 */
	public void deleteByRoleIds(String roleids[]);
	/**
	 * 根据角色ID获取权限
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findByRoleId(@Param(value="roleid") String roleid);
	/**
	 * 根据菜单ID删除权限
	 */
	public void deleteByMenuId(@Param(value="menuid") String menuid);
}

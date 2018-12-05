package com.xincheng.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xincheng.basedao.BaseDao;

@Repository
public interface ButtonDao extends BaseDao{
	/**
	 * 获取排序最大值
	 * @return
	 */
	public int getMaxSort(@Param(value="menuid") String menuid);
	/**
	 * 根据菜单ID删除按钮
	 */
	public void deleteByMenuId(@Param(value="menuid") String menuid);
}

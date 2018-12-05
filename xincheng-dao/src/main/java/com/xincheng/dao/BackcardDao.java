package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xincheng.basedao.BaseDao;

public interface BackcardDao extends BaseDao {
	/**
	 * 查询最后一条充值记录
	 */
	public Map<String,Object> findLastMap(Map<String,Object> paramap);
}

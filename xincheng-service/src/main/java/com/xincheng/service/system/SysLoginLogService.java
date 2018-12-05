package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

public interface SysLoginLogService{
	/**
	 * 根据条件分页查询
	 * @param paramap
	 * @return
	 */
	public String findPageByParam(Map<String,Object> paramap);
	/**
	 * 添加
	 * @param paramap
	 */
	public void save(Map<String,Object> paramap);
	/**
	 * 根据customerid查找ip
	 */
	public List<Map<String,Object>> findiplistbyparamap(Map<String,Object> paramap);
}

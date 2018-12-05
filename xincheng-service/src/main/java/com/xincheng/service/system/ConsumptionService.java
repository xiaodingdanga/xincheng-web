package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

public interface ConsumptionService {
	/**
	 * 读取卡信息
	 */
	public Map<String,Object> readCardInfo(Map<String,Object> paramap);
	/**
	 * 下订单
	 */
	public String saveorder(Map<String,Object> paramap);
	/**
	 * 根据customerid查询油站拥有的油
	 */
	public List<Map<String,Object>> findoilbyparamap(Map<String,Object> paramap);
}

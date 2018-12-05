package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

public interface ReportService {
	/**
	 * 销售明细
	 * @param paramap
	 * @return
	 */
	public String xfmxgetDataList(Map<String,Object> paramap);
	/**
	 * 销售统计
	 * @param paramap
	 * @return
	 */
	public String xftjgetDataList(Map<String,Object> paramap);
	/**
	 * 充值明细
	 * @param paramap
	 * @return
	 */
	public String czmxgetDataList(Map<String,Object> paramap);
	/**
	 * 充值统计
	 * @param paramap
	 * @return
	 */
	public String cztjgetDataList(Map<String,Object> paramap);
	
	/**
	 * 退卡明细
	 * @param paramap
	 * @return
	 */
	public String tkmxgetDataList(Map<String,Object> paramap);
	/**
	 * 退卡统计
	 * @param paramap
	 * @return
	 */
	public String tktjgetDataList(Map<String,Object> paramap);

	/**
	 *佣金明细
	 * @param paramap
	 * @return
	 */
	public String yjmxgetDataList(Map<String,Object> paramap);
	/**
	 * 佣金统计
	 * @param paramap
	 * @return
	 */
	public String yjtjgetDataList(Map<String,Object> paramap);
	/**
	 * 佣金提现记录 
	 * @param paramap
	 * @return
	 */
	public String yjtxjlgetDataList(Map<String,Object> paramap);
}

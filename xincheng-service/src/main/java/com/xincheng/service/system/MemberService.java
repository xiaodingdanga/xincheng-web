package com.xincheng.service.system;

import java.util.List;
import java.util.Map;

public interface MemberService {
	
	public String findPageByParam(Map<String,Object> paramap);
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> findById(String id);
	/**
	 * 验证会员卡号是否重复
	 * @param paramap
	 * @return
	 */
	public boolean checkserialnum(Map<String,Object> paramap);
	/**
	 * 查找最后一次保存的会员卡号
	 */
	public Map<String,Object> findLastSerialnum();
	/**
	 * 保存
	 * @param paramap
	 */
	public void save(Map<String,Object> paramap);
	/**
	 * 更新
	 * @param paramap
	 */
	public void update(Map<String, Object> paramap);
	/**
	 * 加载分销商
	 */
	public String loaddis();
	/**
	 * 根据disid查询实例
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findBydisid(String id);
	/**
	 * 根据id批量删除
	 * @param paramap
	 * @return
	 */
	public void batchDelete(String[] ids);
}

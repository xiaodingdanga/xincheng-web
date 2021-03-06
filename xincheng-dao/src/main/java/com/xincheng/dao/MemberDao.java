package com.xincheng.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xincheng.basedao.BaseDao;

public interface MemberDao extends BaseDao {
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
	 * 根据chipnum查找数据
	 */
	public Map<String,Object> findByChipnum(Map<String,Object> paramap);
	/**
	 * 根据disid查找数据
	 */
	public List<Map<String, Object>> findBydisid(String id);
}

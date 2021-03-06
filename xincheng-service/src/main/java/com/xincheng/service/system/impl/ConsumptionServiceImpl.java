package com.xincheng.service.system.impl;

import java.util.List;
import java.util.Map;
import java.awt.BufferCapabilities.FlipContents;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.common.util;
import com.xincheng.dao.BackcardDao;
import com.xincheng.dao.ConsumptionDao;
import com.xincheng.dao.DistributorDao;
import com.xincheng.dao.MemberDao;
import com.xincheng.dao.RechargeDao;
import com.xincheng.dao.SysLoginLogDao;
import com.xincheng.dao.UserDao;
import com.xincheng.service.system.BackcardService;
import com.xincheng.service.system.ConsumptionService;
import com.xincheng.service.system.MemberService;
import com.xincheng.service.system.RechargeService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConsumptionServiceImpl implements ConsumptionService {

	Logger log = LogManager.getLogger(ConsumptionServiceImpl.class.getName());

	@Resource
	private ConsumptionDao consumptionDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private UserDao userDao;
	@Resource
	private SysLoginLogDao SysUserLogAccessDao;
	@Resource
	private DistributorDao distributorDao;
	/**
	 * 读取卡信息
	 */
	public Map<String, Object> readCardInfo(Map<String, Object> paramap) {
		Map<String,Object>  map = memberDao.findByChipnum(paramap);
		return map;
	}
	/**
	 * 下订单
	 */
	public String saveorder(Map<String, Object> paramap) {
		JSONObject returnobj = new JSONObject();
		Map<String,Object>  map = memberDao.findByChipnum(paramap);
		String isIP = paramap.get("isIP").toString();
		if(isIP.equals("1")){
			String ip = paramap.get("ip").toString();
			List<Map<String,Object>> ipList = SysUserLogAccessDao.findiplistbyparamap(paramap);
			if(ipList.size()!=0){
				Map<String,Object> ipMap = new HashMap<String,Object>();
				for(Map<String,Object> getipmap:ipList){
					ipMap.put(getipmap.get("ip").toString(),"");
				}
				if(!ipMap.containsKey(ip)){
					returnobj.put("code","此登录IP不可消费！");
					returnobj.put("state","6");
					return returnobj.toJSONString();
				}
			}
		}
		if(map==null){
			returnobj.put("code","无此卡信息！");
			returnobj.put("state","0");
			return returnobj.toJSONString();
		}
		String state =map.get("state").toString();
		if(state.equals("1")){
			returnobj.put("code","此卡未绑定，不可消费！");
			returnobj.put("state","1");
			return returnobj.toJSONString();
		}
		String isuse = map.get("isuse").toString();
		if(isuse.equals("1")){
			returnobj.put("code","次卡已被停用，不可消费！");
			returnobj.put("state","2");
			return returnobj.toJSONString();
		}
		float oldmoney  = Float.parseFloat(map.get("money").toString());//余额
		String cardtype  = map.get("cardtype").toString();
		if(cardtype.equals("1")){
			if(oldmoney<0){
				returnobj.put("code","此信用卡已超过透支额度！");
				returnobj.put("state","3");
				return returnobj.toJSONString();
			}
		}
		String id = util.getUuid();
		String memberid = map.get("id").toString();
		String userid = paramap.get("userid").toString();
		String customerid = paramap.get("customerid").toString();
		String oilid = paramap.get("oilid").toString();
		float price = Float.parseFloat(paramap.get("price").toString());//消费金额
		List<Map<String,Object>> oillist = consumptionDao.findoilbyparamap(paramap);
		Map<String,Object> maplist = oillist.get(0);
		float disprice = Float.parseFloat(maplist.get("disprice").toString());
		float inprice =  Float.parseFloat(maplist.get("inprice")==null?"0":maplist.get("inprice").toString());
		float outprice =  Float.parseFloat(maplist.get("outprice")==null?"0":maplist.get("outprice").toString());
		if(inprice==0||outprice==0){
			returnobj.put("code","未设置价格！");
			returnobj.put("state","4");
			return returnobj.toJSONString();
		}
		float volume = price/outprice;
		float realprice = volume*inprice;
		float newmoney = oldmoney-realprice;
		if(cardtype.equals("0")){
			if(newmoney<0){
				returnobj.put("code","卡内余额不足！");
				returnobj.put("state","5");
				return returnobj.toJSONString();
			}
		}
		realprice = (float)Math.round(realprice*100)/100;
		newmoney = (float)Math.round(newmoney*100)/100;
		//保存消费记录
		String date = util.datetostring(new Date(),"yyyy-MM-dd HH:mm:ss");
		Map<String,Object> consumptionmap = new HashMap<String,Object>();
		consumptionmap.put("id", id);
		consumptionmap.put("memberid",memberid);
		consumptionmap.put("price", price);
		consumptionmap.put("realprice", realprice);
		consumptionmap.put("oldmoney", oldmoney);
		consumptionmap.put("newmoney", newmoney);
		consumptionmap.put("inprice", inprice);
		consumptionmap.put("outprice", outprice);
		consumptionmap.put("createdate", date);
		consumptionmap.put("userid", userid);
		consumptionmap.put("customerid", customerid);
		consumptionmap.put("oilid", oilid);
		consumptionDao.save(consumptionmap);
		//更改会员卡余额
		map.put("money", newmoney);
		memberDao.update(map);
		String disid = map.get("disid")==null?"":map.get("disid").toString();
		if(!disid.equals("")&&disid!=null){
			//保存分销记录
			float tichengmoney = disprice*volume;
			String distributionid = util.getUuid();
			Map<String,Object> distributionmap = new HashMap<String,Object>();
			distributionmap.put("id", distributionid);
			distributionmap.put("disid", disid);
			distributionmap.put("consumption", id);
			distributionmap.put("volume", volume);
			distributionmap.put("commission", disprice);
			distributionmap.put("money", tichengmoney);
			distributionmap.put("createdate", date);
			consumptionDao.savedis(distributionmap);
		}
		//发送短信
		String tel = map.get("tel").toString();
		String strrealprice = String.valueOf(realprice);
		String strnewMoney = String.valueOf(newmoney);
		util.sendSms(tel, strrealprice, strnewMoney);
		returnobj.put("code","购买成功！");
		returnobj.put("state","6");
		return returnobj.toJSONString();
	}
	/**
	 * 根据customerid查询油站拥有的油
	 */
	public List<Map<String, Object>> findoilbyparamap(Map<String, Object> paramap) {
		return consumptionDao.findoilbyparamap(paramap);
	}
}

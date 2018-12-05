package com.xincheng.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xincheng.common.MD5Util;
import com.xincheng.common.util;
import com.xincheng.dao.DistributionDao;
import com.xincheng.dao.SysUserFaceDao;
import com.xincheng.dao.UlrDao;
import com.xincheng.dao.UserDao;
import com.xincheng.dao.WithdrawDao;
import com.xincheng.service.system.UserFaceService;
import com.xincheng.service.system.UserService;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService{
	Logger log = LogManager.getLogger(UserServiceImpl.class.getName());
	@Resource
	private UserDao usersDao;
	@Resource
	private UlrDao ulrDao;
	@Resource
	private DistributionDao distributionDao;
	@Resource
	private WithdrawDao withdrawDao;
	@Resource
	private UserFaceService userFaceService;
	
	public String findPageByParam(Map<String,Object> paramap){
		//当前每页行数
		int pageSize=Integer.parseInt(paramap.get("rows").toString());
		paramap.put("pageSize",pageSize);
		//当前页数
		int page=Integer.parseInt(paramap.get("page").toString());
		//总记录数 
		int totalRecord=usersDao.getCountByParam(paramap);
		//总页数
		int totalPage=0;
		//总数%每页数=余数：等于0----否则----总数%每页数=余数：不等于0
		if(totalRecord%pageSize==0){
			//总数/每页数=总页数
			totalPage=totalRecord/pageSize;
		}else{
			//总数/每页数+1=总页数
			totalPage=totalRecord/pageSize+1;
		}
		//开始记录数  
		int index=(page-1)*pageSize;
		paramap.put("start",index);
		JSONObject pagejson=new JSONObject();
		//根据条件查询
		List<Map<String,Object>> list=usersDao.findPageByParam(paramap);
		pagejson.put("total", totalPage);//总页数
		pagejson.put("page", page);//当前页数
		pagejson.put("records", totalRecord);//总记录数
		JSONArray listjson=new JSONArray();
		for(Map<String,Object> usermap:list){
			JSONObject obj=new JSONObject();
			String userid=usermap.get("id").toString();
			obj.put("id",userid);
			obj.put("account",usermap.get("account").toString());
			obj.put("name",usermap.get("name").toString());
			obj.put("tel",usermap.get("tel")!=null?usermap.get("tel").toString():"");
			obj.put("email",usermap.get("email")!=null?usermap.get("email").toString():"");
			obj.put("customername",usermap.get("customername")!=null?usermap.get("customername").toString():"");
			listjson.add(obj);
		}
		pagejson.put("rows",listjson);//记录列表
		return pagejson.toJSONString();
	}

	/**
	 * 验证账号是否重复
	 * @param paramap
	 * @return
	 */
	public boolean checkName(Map<String,Object> paramap){
		return usersDao.checkName(paramap);
	}
	/**
	 * 保存
	 * @param paramap
	 */
	public String save(Map<String,Object> paramap){
		//保存的ID
		String id=util.getUuid();
		String password=MD5Util.getMD5(paramap.get("password").toString());
		paramap.put("id",id);
		paramap.put("password",password);
		paramap.put("isdel","");
		paramap.put("des","");
		int sort=usersDao.getMaxSort();
		paramap.put("sort",sort+5);
		String facedate=paramap.get("facedate")==null?"":paramap.get("facedate").toString();
		if(StringUtils.isNotBlank(facedate)){
			String string = userFaceService.addFace(id, facedate, "xincheng");
			
			if("成功".equals(string)){
				usersDao.save(paramap);
			}else{
				
			}
			return string;
		}else{
			usersDao.save(paramap);
			return "成功";
		}
	}
	/**
	 * 根据ID查询实例
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> findById(String id){
		return usersDao.findById(id);
	}
	/**
	 * 根据账号获取用户信息
	 * @param paramap
	 * @return
	 */
	public Map<String,Object> getByAccount(String account){
		return usersDao.getByAccount(account);
	}
	/**
	 * 重置密码
	 * @param paramap
	 */
	public void resetPassword(Map<String,Object> paramap){
		usersDao.resetPassword(paramap);
	}
	/**
	 * 更新
	 * @param paramap
	 */
	public String update(Map<String,Object> paramap){
		String userid=paramap.get("id").toString();
		Map<String,Object> usermap=usersDao.findById(userid);
		usermap.put("account", paramap.get("account").toString());
		usermap.put("name", paramap.get("name").toString());
		usermap.put("tel", paramap.get("tel").toString());
		usermap.put("email", paramap.get("email").toString());
		usermap.put("customerid", paramap.get("customerid").toString());
		String password=paramap.get("password")!=null?paramap.get("password").toString():"";
		if(!password.equals("")){
			password=MD5Util.getMD5(password);
			usermap.put("password",password);
		}
		//更新用户
		usersDao.update(usermap);
		Map<String, Object> userFace = userFaceService.findByUserId(userid);
		if(userFace!=null){//录过人脸信息。只进行更新操作
			String facedate=paramap.get("facedate")==null?"":paramap.get("facedate").toString();
			if(StringUtils.isNotBlank(facedate)){
				String string = userFaceService.updateFace(userid, facedate, "xincheng");
				return string;
			}
			return "成功";
		}else{//未录过人脸信息。进行添加操作
			
			String facedate=paramap.get("facedate")==null?"":paramap.get("facedate").toString();
			if(StringUtils.isNotBlank(facedate)){
				String string = userFaceService.addFace(userid, facedate, "xincheng");
				return string;
			}
		}
		return "成功";
	}
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void batchDelete(String ids[]){
		//删除用户
		usersDao.batchDelete(ids);
		//删除用户相关角色
		ulrDao.deleteByUserIds(ids);
	}
	/**
	 * 根据用户ID删除
	 * @param paramap
	 * @return
	 */
	public void delete(String id){
		Map<String, Object> userFace = userFaceService.findByUserId(id);
		if(userFace!=null){
			String facetoken = (String) userFace.get("facetoken");
			userFaceService.deleteFace(id, facetoken, "xincheng");
		}
		//删除用户
		usersDao.delete(id);
		//删除用户相关角色
		ulrDao.deleteByUserIds(id.split(","));
	}
	/**
	 * 根据用户ID数组查询用户集合
	 * @param userid
	 * @return
	 */
	public List<Map<String,Object>> findByUserIds(String userid[]){
		return usersDao.findByUserIds(userid);
	}
	/**
	 * 用户查询
	 * @param paramap
	 * @return
	 */
	public List<Map<String,Object>> findAllByParam(Map<String,Object> paramap){
		return usersDao.findAllByParam(paramap);
	}
	/**
	 * 获取所有用户
	 * @return
	 */
	public Map<String,Object> getUsers(){
		Map<String,Object> alluser=new HashMap<String,Object>();
		List<Map<String,Object>> alllist=usersDao.findAllByParam(new HashMap<String,Object>());
		for(Map<String,Object> map:alllist){
			String account=map.get("account").toString();
			String id=map.get("id").toString();
			alluser.put(account, id);
		}
		return alluser;
	}
	/**
	 * 批量导入
	 */
	public void importdata(List<String[]> datalist){
		int sort=usersDao.getMaxSort();
		List<Map<String,Object>> userlist=new ArrayList<Map<String,Object>>();
		for(Object[] obj:datalist){
			String account=(String)obj[0];//账号
			String name=(String)obj[1];//姓名
			String tel=(String)obj[3];//电话
			String email=(String)obj[4];//EMAIL
			String userid=util.getUuid();//ID
			String password=MD5Util.getMD5("123456");//密码
			//用户信息
			Map<String,Object> usermap=new HashMap<String,Object>();
			usermap.put("id",userid);
			usermap.put("password",password);
			usermap.put("account",account);
			usermap.put("name",name);
			usermap.put("tel",tel);
			usermap.put("email",email);
			usermap.put("isdel","");
			sort=sort+5;
			usermap.put("sort",sort);
			userlist.add(usermap);
		}
		//用户批量保存
		usersDao.batchSave(userlist);
	}
}

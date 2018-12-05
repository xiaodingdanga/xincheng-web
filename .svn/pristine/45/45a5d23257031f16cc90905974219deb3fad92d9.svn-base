package com.xincheng.service.system.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.aip.face.AipFace;
import com.xincheng.common.FaceCodeConversion;
import com.xincheng.common.util;
import com.xincheng.dao.SysUserFaceDao;
import com.xincheng.service.system.UserFaceService;
@Service
@Transactional(rollbackFor = Exception.class)
public class UserFaceServiceImpl implements UserFaceService {
	@Value("#{configProperties.app_id}")   
	public  String app_id;
	@Value("#{configProperties.api_key}")   
	public  String api_key;
	@Value("#{configProperties.secret_key}")   
	public  String secret_key;
	
	/*private  AipFace client = new AipFace(app_id, api_key, secret_key);*/
	
	
	@Resource
	private SysUserFaceDao sysUserFaceDao;
	
	@Override
	public String addFace(String userId, String facedate, String goupid) {
		// 初始化一个AipFace
        AipFace client = new AipFace(app_id, api_key, secret_key);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

		int indexOf = facedate.lastIndexOf(",");
		String imageDate ="";
		if(indexOf!=-1){
		imageDate = facedate.substring(indexOf+1);
		}
        // 调用接口
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        String image =imageDate;
        String imageType = "BASE64";
        String groupId = goupid;
        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
        //TODO 根据响应结果判定是否存入数据库
        /*{正确
        	  "result": {
        	    "face_token": "ceda60d73dad06c75c4dcddeb3cde8ad",
        	    "location": {
        	      "top": 95.3408432,
        	      "left": 136.5170593,
        	      "rotation": 0,
        	      "width": 80,
        	      "height": 90
        	    }
        	  },
        	  "log_id": 8435001259975,
        	  "error_msg": "SUCCESS",
        	  "cached": 0,
        	  "error_code": 0,
        	  "timestamp": 1538034578
        	}*/
        
         /* {错误
        	  "result": null,
        	  "log_id": 525150015500,
        	  "error_msg": "face is already exist",
        	  "cached": 0,
        	  "error_code": 223105,
        	  "timestamp": 1538034711
        	}*/
        Integer error_code = (Integer) res.get("error_code");
        if(error_code==0){
        	JSONObject result = (JSONObject) res.get("result");
        	String face_token = (String) result.get("face_token");
        	String id=util.getUuid();
        	Map<String, Object> saveMap=new HashMap<>();
        	saveMap.put("id",id);
        	saveMap.put("userid",userId);
        	saveMap.put("facedate",facedate);
        	saveMap.put("groupid",goupid);
        	saveMap.put("facetoken",face_token);
        	sysUserFaceDao.save(saveMap);
        	return "成功";
        }
		return FaceCodeConversion.codeToMessage(error_code);
	}

	@Override
	public String updateFace(String userId, String facedate, String goupid) {
		// 初始化一个AipFace
        AipFace client = new AipFace(app_id, api_key, secret_key);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        int indexOf = facedate.lastIndexOf(",");
		String imageDate ="";
		if(indexOf!=-1){
		imageDate = facedate.substring(indexOf+1);
		}

		 // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("user_info", "user's info");
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    String image =imageDate;
	    String imageType = "BASE64";
	    // 人脸更新
	    JSONObject res = client.updateUser(image, imageType, goupid, userId, options);
	    
	    //TODO 根据响应结果判定是否更改数据库
	   /* {成功
	    	  "result": {
	    	    "face_token": "4f8ea400cff9b3c2970a5351f2cee108",
	    	    "location": {
	    	      "top": 35.543293,
	    	      "left": 152.2142334,
	    	      "rotation": 0,
	    	      "width": 81,
	    	      "height": 71
	    	    }
	    	  },
	    	  "log_id": 2515991018494,
	    	  "error_msg": "SUCCESS",
	    	  "cached": 0,
	    	  "error_code": 0,
	    	  "timestamp": 1536734934
	    	}*/
	   /* {失败
	    	  "result": null,
	    	  "log_id": 1579941016545,
	    	  "error_msg": "user is not exist",
	    	  "cached": 0,
	    	  "error_code": 223103,
	    	  "timestamp": 1538037075
	    	}*/
	    Integer error_code = (Integer) res.get("error_code");
        if(error_code==0){
        	JSONObject result = (JSONObject) res.get("result");
        	String face_token = (String) result.get("face_token");
        	Map<String, Object> updateMap=new HashMap<>();
        	updateMap.put("userid", userId);
        	updateMap.put("facedate", facedate);
        	updateMap.put("facetoken", face_token);
        	sysUserFaceDao.updateByUserId(updateMap);
        	return "成功";
        }
		return FaceCodeConversion.codeToMessage(error_code);
	}

	@Override
	public JSONObject findFace(String facedate,String goupid) {
		// 初始化一个AipFace
        AipFace client = new AipFace(app_id, api_key, secret_key);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

		
		 // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    /*options.put("user_id", "user1");*/
	    options.put("max_user_num", "3");
	    String image =facedate;
	    String imageType = "BASE64";
	    String groupIdList = goupid;
	    // 人脸搜索
	    JSONObject res = client.search(image, imageType, groupIdList, options);
	   
		return res;
	}

	@Override
	public String deleteFace(String userId, String facetoken, String goupid) {
		// 初始化一个AipFace
        AipFace client = new AipFace(app_id, api_key, secret_key);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

		
		// 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    // 人脸删除
	    JSONObject res = client.faceDelete(userId, goupid, facetoken, options);
	   /* {成功
	    	  "result": null,
	    	  "log_id": 9965001657945,
	    	  "error_msg": "SUCCESS",
	    	  "cached": 0,
	    	  "error_code": 0,
	    	  "timestamp": 1538038681
	    	}*/
	    /*{ 失败
	    	  "result": null,
	    	  "log_id": 5520194798455,
	    	  "error_msg": "face is not exist",
	    	  "cached": 0,
	    	  "error_code": 223106,
	    	  "timestamp": 1538038714
	    	}*/
	    Integer error_code = (Integer) res.get("error_code");
        if(error_code==0){
	    	sysUserFaceDao.deleteByUserId(userId);
	    	return "删除成功";
	    }
		return "删除失败";
	}

	@Override
	public Map<String, Object> findByUserId(String userid) {
		
		return sysUserFaceDao.findByUserId(userid);
	}

}

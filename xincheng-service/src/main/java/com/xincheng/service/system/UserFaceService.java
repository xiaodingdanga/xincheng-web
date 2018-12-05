package com.xincheng.service.system;

import java.util.Map;

import org.json.JSONObject;

public interface UserFaceService {
	String addFace(String userId,String facedate,String goupid);
	String updateFace(String userId,String facedate,String goupid);
	JSONObject findFace(String facedate,String goupid);
	String deleteFace(String userId,String facetoken,String goupid);
	Map<String, Object> findByUserId(String userid);
	
}

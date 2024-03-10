package com.yskinfy.webemail.controller;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.yskinfy.webemail.VO.AddLoginVO;
import com.yskinfy.webemail.repo.ZenovelLoginRepo;
import com.yskinfy.webemail.util.ZenovelLoginUtil;

@RestController
@RequestMapping({"/login"})
public class LoginController {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired ZenovelLoginRepo zenovelLoginRepo;
	
	@PostMapping("/addLogin")
	public Map<String, Object> addLogin(@RequestBody AddLoginVO request)
	{
		Map<String,Object> response=new HashMap();
		try {
			logger.info("New Signup!!");
			logger.info("Request Body!!"+new Gson().toJson(request));
			
			Date date=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String todaysDate=formatter.format(date);
			logger.info("Today's Date::"+todaysDate);
			
			String userId="USER-"+UUID.randomUUID().toString();
			logger.info("User Id::"+userId);
			
			ZenovelLoginUtil newUser=new ZenovelLoginUtil();
			
			newUser.setUserId(userId);
			newUser.setUserName(request.getUsername());
			newUser.setPassword(request.getPassword());
			newUser.setFullName(request.getFullName());
			newUser.setEmail(request.getEmail());
			newUser.setContactNbr(request.getContactNbr());
			newUser.setRole(request.getRole());
			newUser.setLastLoggedIn(null);
			newUser.setCreatedOn(todaysDate);
			newUser.setToken(null);
			newUser.setIsTokenValid(null);
			newUser.setIsNewUser("Y");
			newUser.setUnsuccessfulLogins("0");
			
			logger.info("New User::"+new Gson().toJson(newUser));
			
			zenovelLoginRepo.save(newUser);
			
			response.put("statusCode", "200");
			response.put("statusDescq", "SUCCESS");
			response.put("description", "User Created for "+request.getFullName()+" with UserID:"+userId);
			return response;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			response.put("statusCode", "500");
			response.put("statusDescq", "FAILURE");
			response.put("description", "Cannot create User!!");
			return response;
		}
		
	}
}

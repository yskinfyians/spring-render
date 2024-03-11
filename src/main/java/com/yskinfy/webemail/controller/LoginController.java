package com.yskinfy.webemail.controller;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.yskinfy.webemail.VO.AddLoginVO;
import com.yskinfy.webemail.VO.LoginVO;
import com.yskinfy.webemail.repo.ZenovelLoginRepo;
import com.yskinfy.webemail.util.ZenovelLoginUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping({"/login"})
public class LoginController {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired ZenovelLoginRepo zenovelLoginRepo;
	public static final String SECRET_KEY="ZenovelLoginSecretKeyForTokenGenerationLoginValidations";
	
	@Value("${unsuccessful_login_threshold}")
	private int LOGIN_THRESHOLD;
	
	@PostMapping("/newLogin")
	public Map<String, Object> newLogin(@RequestBody LoginVO request)
	{
		Map<String,Object> response=new HashMap();
		try {
			logger.info("login Request for username!!"+request.getUsername());
			List<ZenovelLoginUtil> user=zenovelLoginRepo.findUsername(request.getUsername());
			
			Date date=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String todaysDate=formatter.format(date);
			logger.info("Today's Date::"+todaysDate);
			
			if(user.isEmpty())
			{
				logger.info("Username Not present in db!");
				response.put("statusCode", "500");
				response.put("statusDesc", "FAILURE");
				response.put("description", "UserName Doesnt exist!!");
				return response;
			}
			if(Integer.parseInt(user.get(0).getUnsuccessfulLogins())>=LOGIN_THRESHOLD)
			{
				logger.info("Login Threshold:"+LOGIN_THRESHOLD);
				logger.info("Unsuccessful Login Count > Login Threshold!"+user.get(0).getUnsuccessfulLogins());
				response.put("statusCode", "500");
				response.put("statusDescs", "FAILURE");
				response.put("description", "Account Locked!!");
				return response;
			}
			
			String password=convertBase64ToNormal(request.getEncodedPassword());
			logger.debug(password);
			if(BCrypt.checkpw(password, request.getBcryptPassword()))
			{
				logger.info("Passwords Matched!!");
				zenovelLoginRepo.unsuccessfulLogins("0",request.getUsername());
				logger.info("Password Matched..Logged in successfully!");
				
				String token=generateJWTToken(request.getUsername());
				
				logger.info("token:"+token);
				
				List<String> tokens=new ArrayList();
				JSONArray jsonArray=new JSONArray(user.get(0).getToken());
				for(int i=0;i<jsonArray.length();i++)
				{
					tokens.add(jsonArray.getString(i));
				}
				tokens.add(token);
				
				zenovelLoginRepo.updateToken(new Gson().toJson(tokens),request.getUsername());
				zenovelLoginRepo.updateLoggedIn(todaysDate,request.getUsername());
				 logger.info("Successful Login!");
				response.put("statusCode", "200");
				response.put("statusDesc", "SUCCESS");
				response.put("description", "Successsfully Logged in!!");
				return response;
			}
			else {
				logger.info("Password Didnt Match!!");
				int unsuccessfulCount=Integer.parseInt(user.get(0).getUnsuccessfulLogins());
				unsuccessfulCount++;
				logger.info("Unsuccesssful Count::"+unsuccessfulCount);
				zenovelLoginRepo.unsuccessfulLogins(Integer.toString(unsuccessfulCount),request.getUsername());
				
				if(unsuccessfulCount>=LOGIN_THRESHOLD)
				{
					logger.info("Count excessive than threshold.."+unsuccessfulCount);
					response.put("statusCode", "500");
					response.put("statusDescs", "FAILURE");
					response.put("description", "Account Locked!!");
					return response;
				}
				response.put("statusCode", "500");
				response.put("statusDesc", "FAILURE");
				response.put("description", "Wrong Password!!");
				return response;
			}
			 
		}catch(Exception e)
		{
			e.printStackTrace();
			response.put("statusCode", "500");
			response.put("statusDescs", "FAILURE");
			response.put("description", "Error in login!!");
			return response;
		}
		
	}
	
	private String generateJWTToken(String username)
	{
		Date expiration=new Date(System.currentTimeMillis()+3600000);
		logger.info("expiration:"+expiration);
		return Jwts.builder().setSubject(username).setExpiration(expiration).signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
	}
	
	private String convertBase64ToNormal(String base64EncodedString)
	{
		byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        String decodedString = new String(decodedBytes);
        return decodedString;
	}
	
	@PostMapping("/signup")
	public Map<String, Object> addLogin(@RequestBody AddLoginVO request)
	{
		Map<String,Object> response=new HashMap();
		try {
			logger.info("New Signup!!");
			logger.info("Request Body!!"+new Gson().toJson(request));
			
			logger.info("Check if username Exists!!");
			List<ZenovelLoginUtil> user=zenovelLoginRepo.findUsername(request.getUsername());
			
			if(!user.isEmpty())
			{
				logger.info("Username already Exists");
				response.put("statusCode", "500");
				response.put("statusDescq", "FAILURE");
				response.put("description", "UserName already exists!!");
				return response;
			}
			
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
			newUser.setToken("[]");
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

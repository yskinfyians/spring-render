package com.yskinfy.webemail.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/sum"})
public class EmailController {

	@GetMapping("/getSum")
	public Map<String, Object> getSum(@RequestParam String number1,@RequestParam String number2,@RequestParam String operation,@RequestParam String preference)
	{
		String result="";
		Map<String,Object> response=new HashMap();
		try {
			if(operation.equalsIgnoreCase("ADD"))
			{
				if(preference.equalsIgnoreCase("number1"))
				{
					result=Integer.toString(Integer.parseInt(number1)+Integer.parseInt(number2));
				}
				else
				{
					result=Integer.toString(Integer.parseInt(number1)+Integer.parseInt(number2));
				}
			}
			if(operation.equalsIgnoreCase("SUBSTRACT"))
			{
				if(preference.equalsIgnoreCase("number1"))
				{
					result=Integer.toString(Integer.parseInt(number1)-Integer.parseInt(number2));
				}
				else
				{
					result=Integer.toString(Integer.parseInt(number2)-Integer.parseInt(number1));
				}
			}
			if(operation.equalsIgnoreCase("MULTIPLY"))
			{
				if(preference.equalsIgnoreCase("number1"))
				{
					result=Integer.toString(Integer.parseInt(number1)*Integer.parseInt(number2));
				}
				else
				{
					result=Integer.toString(Integer.parseInt(number1)*Integer.parseInt(number2));
				}
			}
			if(operation.equalsIgnoreCase("DIVIDE"))
			{
				if(preference.equalsIgnoreCase("number1"))
				{
					result=Integer.toString(Integer.parseInt(number1)/Integer.parseInt(number2));
				}
				else
				{
					result=Integer.toString(Integer.parseInt(number2)/Integer.parseInt(number1));
				}
			}
				
			response.put("StatusCode", "200");
			response.put("StatusDesc", "SUCCESS");
			response.put("Result", result);
			return response;
			
		}
		catch(Exception e)
		{
			response.put("StatusCode", "500");
			response.put("StatusDesc", "ERROR");
			response.put("Result", result);
			return response;
		}
		
	}
}

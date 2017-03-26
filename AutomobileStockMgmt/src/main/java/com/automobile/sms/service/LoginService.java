package com.automobile.sms.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.LoginDetails;
import com.automobile.sms.service.impl.LoginServiceImpl;
import com.google.gson.Gson;

@Path("/login")
public class LoginService {
	private static Logger logger = LoggerFactory.getLogger(LoginService.class);
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyLogin(LoginDetails loginDetails) {
		Gson gSon = new Gson();
		logger.debug("login verify request :"+gSon.toJson(loginDetails));
		
		LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
		APIResponse<String> apiResponse = loginServiceImpl.verifyLogin(loginDetails);
		logger.debug("login verify response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();
		
	}
	
}
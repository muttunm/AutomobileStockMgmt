package com.automobile.sms.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerDetails;
import com.automobile.sms.service.impl.ApplicationRenewalServiceImpl;
import com.google.gson.Gson;

@Path("/AppRenewal/")
public class ApplicationRenewalService {
	private static Logger logger = LoggerFactory.getLogger(ApplicationRenewalService.class);

	@GET
	@Path("/getRenewalDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRenewalDetails() {

		ApplicationRenewalServiceImpl serviceImpl = new ApplicationRenewalServiceImpl();
		APIResponse<String> apiResponse = serviceImpl.getRenewalDetails();
		Gson gSon = new Gson();
		logger.debug("getRenewalDetails  response:" + gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
}

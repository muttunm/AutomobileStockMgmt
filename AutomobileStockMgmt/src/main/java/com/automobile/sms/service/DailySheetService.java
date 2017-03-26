package com.automobile.sms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DailySheetDetails;
import com.automobile.sms.service.impl.DailySheetServiceImpl;
import com.google.gson.Gson;


@Path("/dailySheet/")
public class DailySheetService {

	private static Logger logger = LoggerFactory.getLogger(DailySheetService.class);
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerDailySheet(List<DailySheetDetails> dailySheetDetailsList) {
		Gson gSon = new Gson();
		logger.debug("RegisterCustomer Request :"+gSon.toJson(dailySheetDetailsList));
		DailySheetServiceImpl dailySheetServiceImpl = new DailySheetServiceImpl();
		APIResponse<String> apiResponse = dailySheetServiceImpl
				.registerDailySheet(dailySheetDetailsList);
		logger.debug("RegisterCustomer Response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	@GET
	@Path("/getTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDailySheet(@QueryParam("Date") String date) {

		DailySheetServiceImpl customerServiceImpl = new DailySheetServiceImpl();
		APIResponse<List<DailySheetDetails>> apiResponse = customerServiceImpl.getDailySheet(date);
		Gson gSon = new Gson();
		logger.debug("GetCustomer Response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

}

package com.automobile.sms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerDetails;
import com.automobile.sms.service.impl.DealerServiceImpl;
import com.google.gson.Gson;

@Path("/dealer/")
public class DealerService {
	private static Logger logger = LoggerFactory.getLogger(DealerService.class);
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerDealer(DealerDetails dealerDetails) {
		Gson gSon = new Gson();
		logger.debug("dealer register :"+gSon.toJson(dealerDetails));
		
		DealerServiceImpl dealerServiceImpl = new DealerServiceImpl();
		APIResponse<String> apiResponse = dealerServiceImpl
				.registerDealer(dealerDetails);
		logger.debug("dealer register response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	@GET
	@Path("/getDealers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDealers() {

		DealerServiceImpl dealerServiceImpl = new DealerServiceImpl();
		APIResponse<List<DealerDetails>> apiResponse = dealerServiceImpl.getDealers();
		Gson gSon = new Gson();
		logger.debug("dealer getDealers response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDealer(DealerDetails dealerDetails) {
		Gson gSon = new Gson();
		logger.debug("dealer update request:"+gSon.toJson(dealerDetails));
		
		DealerServiceImpl dealerServiceImpl = new DealerServiceImpl();
		APIResponse<List<DealerDetails>> apiResponse = dealerServiceImpl
				.updateDealer(dealerDetails);
		logger.debug("dealer update response:"+gSon.toJson(apiResponse));
		
		return Response.status(201).entity(apiResponse).build();

	}
}

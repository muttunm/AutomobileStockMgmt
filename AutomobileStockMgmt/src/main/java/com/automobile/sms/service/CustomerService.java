package com.automobile.sms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerDetails;
import com.automobile.sms.service.impl.CustomerServiceImpl;
import com.google.gson.Gson;

@Path("/customer/")
public class CustomerService {
	private static Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@POST
	@Path("/register")
	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCustomer(CustomerDetails customerDetails) {
		Gson gSon = new Gson();
		logger.debug("RegisterCustomer Request :"+gSon.toJson(customerDetails));
		CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
		APIResponse<String> apiResponse = customerServiceImpl
				.registerCustomer(customerDetails);
		logger.debug("RegisterCustomer Response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	@GET
	@Path("/getCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers(@QueryParam("filter") String filter) {

		CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
		APIResponse<List<CustomerDetails>> apiResponse = customerServiceImpl.getCustomers(filter);
		Gson gSon = new Gson();
		logger.debug("GetCustomer Response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(CustomerDetails customerDetails) {
		Gson gSon = new Gson();
		logger.debug("UpdateCustomer Request :"+gSon.toJson(customerDetails));
		
		CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
		APIResponse<String> apiResponse = customerServiceImpl
				.updateCustomer(customerDetails);
		return Response.status(201).entity(apiResponse).build();

	}

	@GET
	@Path("/getCustomersById")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@QueryParam("customerId") String customerId) {

		CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
		APIResponse<List<CustomerDetails>> apiResponse = customerServiceImpl.getCustomerById(customerId);
		Gson gSon = new Gson();
		logger.debug("GetCustomer Response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
}

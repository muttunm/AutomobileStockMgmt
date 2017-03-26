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
import com.automobile.sms.model.CustomerTransactionDetails;
import com.automobile.sms.service.impl.CustomerTransactionServiceImpl;
import com.google.gson.Gson;

@Path("/customertransaction/")
public class CustomerTransactionService {
	private static Logger logger = LoggerFactory
			.getLogger(CustomerTransactionService.class);

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCustomer(
			CustomerTransactionDetails customerTransactionDetails) {
		Gson gSon = new Gson();
		logger.debug("customertransaction create :"
				+ gSon.toJson(customerTransactionDetails));
		CustomerTransactionServiceImpl customerTransactionServiceImpl = new CustomerTransactionServiceImpl();
		APIResponse<CustomerTransactionDetails> apiResponse = customerTransactionServiceImpl
				.createCustomerTransaction(customerTransactionDetails);
		logger.debug("customertransaction create :" + gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();
	}

	@GET
	@Path("/getTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerTransactions(
			@QueryParam("fromDate") String fromDate,
			@QueryParam("toDate") String toDate,
			@QueryParam("customerId") long customerId,
			@QueryParam("transactionStatus") String transactionStatus) {

		CustomerTransactionServiceImpl customerTransactionServiceImpl = new CustomerTransactionServiceImpl();
		APIResponse<List<CustomerTransactionDetails>> apiResponse = customerTransactionServiceImpl
				.getCustomerTransactions(fromDate, toDate, customerId,
						transactionStatus);
		Gson gSon = new Gson();
		logger.debug("customertransaction getTransactions :"
				+ gSon.toJson(apiResponse));

		return Response.status(201).entity(apiResponse).build();
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(
			CustomerTransactionDetails customerTransactionDetails) {

		CustomerTransactionServiceImpl customerTransactionServiceImpl = new CustomerTransactionServiceImpl();
		APIResponse<CustomerTransactionDetails> apiResponse = customerTransactionServiceImpl
				.updateCustomerTransaction(customerTransactionDetails);
		return Response.status(201).entity(apiResponse).build();

	}

}

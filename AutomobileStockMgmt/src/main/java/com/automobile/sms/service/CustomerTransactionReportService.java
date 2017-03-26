package com.automobile.sms.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerTransactionReportDetails;
import com.automobile.sms.service.impl.CustomerTransactionReportServiceImpl;
import com.google.gson.Gson;

@Path("/customertransactionreport/")
public class CustomerTransactionReportService {

	private static Logger logger = LoggerFactory
			.getLogger(CustomerTransactionReportService.class);

	/*@POST
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
	}*/

	@GET
	@Path("/getTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerTransactionsReport(
			@QueryParam("fromDate") String fromDate,
			@QueryParam("toDate") String toDate,
			@QueryParam("filter") String filter) {

		CustomerTransactionReportServiceImpl customerTransactionReportServiceImpl = new CustomerTransactionReportServiceImpl();
		APIResponse<List<CustomerTransactionReportDetails>> apiResponse = customerTransactionReportServiceImpl
				.getCustomerTransactionsReport(fromDate, toDate, filter);
		Gson gSon = new Gson();
		logger.debug("customertransaction getTransactions :"
				+ gSon.toJson(apiResponse));

		return Response.status(201).entity(apiResponse).build();
	}

	/*@PUT
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

*/
}

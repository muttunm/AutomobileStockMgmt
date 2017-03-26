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
import com.automobile.sms.model.DealerTransactionReportDetails;
import com.automobile.sms.service.impl.DealerTransactionReportServiceImpl;
import com.google.gson.Gson;

@Path("/dealertransactionreport/")
public class DealerTransactionReportService {

	private static Logger logger = LoggerFactory
			.getLogger(DealerTransactionReportService.class);

	@GET
	@Path("/getTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDealerTransactionsReport(
			@QueryParam("fromDate") String fromDate,
			@QueryParam("toDate") String toDate) {

		DealerTransactionReportServiceImpl dealerTransactionReportServiceImpl = new DealerTransactionReportServiceImpl();
		APIResponse<List<DealerTransactionReportDetails>> apiResponse = dealerTransactionReportServiceImpl
				.getDealerTransactionsReport(fromDate, toDate);
		Gson gSon = new Gson();
		logger.debug("dealertransaction getTransactions :"
				+ gSon.toJson(apiResponse));

		return Response.status(201).entity(apiResponse).build();
	}

	/*@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDealer(
			DealerTransactionDetails dealerTransactionDetails) {

		DealerTransactionServiceImpl dealerTransactionServiceImpl = new DealerTransactionServiceImpl();
		APIResponse<DealerTransactionDetails> apiResponse = dealerTransactionServiceImpl
				.updateDealerTransaction(dealerTransactionDetails);
		return Response.status(201).entity(apiResponse).build();

	}

*/
}

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
import com.automobile.sms.model.DealerTransactionDetails;
import com.automobile.sms.service.impl.DealerServiceImpl;
import com.automobile.sms.service.impl.DealerTransactionServiceImpl;
import com.google.gson.Gson;

@Path("/dealertransaction/")
public class DealerTransactionService {
	private static Logger logger = LoggerFactory.getLogger(DealerTransactionService.class);

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerDealer(DealerTransactionDetails dealerTransactionDetails) {
		Gson gSon = new Gson();
		logger.debug("dealertransaction create request :"+gSon.toJson(dealerTransactionDetails));
		
		DealerTransactionServiceImpl dealerTransactionServiceImpl = new DealerTransactionServiceImpl();
		APIResponse<DealerTransactionDetails> apiResponse = dealerTransactionServiceImpl
				.createDealerTransaction(dealerTransactionDetails);
		logger.debug("dealertransaction create response :"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	@GET
	@Path("/getTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDealers(@QueryParam("fromDate") String fromDate,@QueryParam("toDate") String toDate,@QueryParam("dealerId") long dealerId,@QueryParam("transactionStatus") String transactionStatus) {

		DealerTransactionServiceImpl dealerServiceImpl = new DealerTransactionServiceImpl();
		APIResponse<List<DealerTransactionDetails>> apiResponse = dealerServiceImpl.getDealerTransactions(fromDate,toDate,dealerId,transactionStatus);
		Gson gSon = new Gson();
		logger.debug("dealertransaction getTransactions response :"+gSon.toJson(apiResponse));
		
		return Response.status(201).entity(apiResponse).build();

	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDealer(DealerTransactionDetails dealerTransactionDetails) {

		DealerTransactionServiceImpl dealerTransactionServiceImpl = new DealerTransactionServiceImpl();
		APIResponse<DealerTransactionDetails> apiResponse = dealerTransactionServiceImpl.updateDealerTransaction(dealerTransactionDetails);
		return Response.status(201).entity(apiResponse).build();

	}

}

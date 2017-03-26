package com.automobile.sms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.Stock;
import com.automobile.sms.service.impl.StockServiceImpl;
import com.google.gson.Gson;

@Path("/stock")
public class StockService {
	private static Logger logger = LoggerFactory.getLogger(StockService.class);
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerStock(Stock stock) {
		Gson gSon = new Gson();
		logger.debug("stock register request:"+gSon.toJson(stock));
		StockServiceImpl stockimpl = new StockServiceImpl();
		APIResponse<String> apiResponse = stockimpl.registerStock(stock);
		logger.debug("stock register response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();
	}

	@GET
	@Path("/getStocks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStocks(@QueryParam("filter")String filter) {

		StockServiceImpl stockimpl = new StockServiceImpl();
		APIResponse<List<Stock>> apiResponse = stockimpl.getStocks(filter);
		Gson gSon = new Gson();
		logger.debug("getstocks respones:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStocks(Stock stocks) {
		Gson gSon = new Gson();
		logger.debug("StockService update request:"+gSon.toJson(stocks));
		
		StockServiceImpl stockImpl = new StockServiceImpl();
		APIResponse<String> apiResponse = stockImpl.updateStocks(stocks);
		logger.debug("StockService update response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
	
	
	@PUT
	@Path("/updateStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStockStatus(Stock stocks) {
		Gson gSon = new Gson();
		logger.debug("StockService update request:"+gSon.toJson(stocks));
		
		StockServiceImpl stockImpl = new StockServiceImpl();
		APIResponse<String> apiResponse = stockImpl.updateStockStatus(stocks);
		logger.debug("StockService update response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
	@DELETE
	@Path("/delete/{stockId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStocks(@PathParam("stockId")  long stockId) {
		Gson gSon = new Gson();
		logger.debug("StockService delete request:"+stockId);
		
		StockServiceImpl stockImpl = new StockServiceImpl();
		APIResponse<String> apiResponse = stockImpl.deleteStocks(stockId);
		logger.debug("StockService delete response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
}

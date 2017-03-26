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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.StockItem;
import com.automobile.sms.service.impl.StockItemServiceImpl;
import com.google.gson.Gson;

@Path("/stockItem")
public class StockItemService {
	private static Logger logger = LoggerFactory.getLogger(StockItemService.class);
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerStockItem(StockItem stockItem) {
		Gson gSon = new Gson();
		logger.debug("stockItem register request:"+gSon.toJson(stockItem));
		
		StockItemServiceImpl stockimpl = new StockItemServiceImpl();
		APIResponse<String> apiResponse = stockimpl.registerStockItems(stockItem);
		logger.debug("stockItem register response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}

	
	
	@GET
	@Path("/getStockItems/{stockId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStocks(@PathParam("stockId") long stockItemId) {
		Gson gSon = new Gson();
		logger.debug("stockItem register request:"+stockItemId);
		
		StockItemServiceImpl stockimpl = new StockItemServiceImpl();
		APIResponse<List<StockItem>> apiResponse = stockimpl.getStockItems(stockItemId);
		logger.debug("stockItem register request:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStockItems(StockItem stockItem) {
		Gson gSon = new Gson();
		logger.debug("stockItem update request:"+gSon.toJson(stockItem));
		StockItemServiceImpl stockImpl = new StockItemServiceImpl();
		APIResponse<String> apiResponse = stockImpl.updateStockItems(stockItem);
		logger.debug("stockItem update response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
	
	@DELETE
	@Path("/delete/{stockItemId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStockItems(@PathParam("stockItemId") long stockItemId) {

		StockItemServiceImpl stockImpl = new StockItemServiceImpl();
		APIResponse<String> apiResponse = stockImpl.deleteStockItems(stockItemId);
		return Response.status(201).entity(apiResponse).build();

	}
	
	@PUT
	@Path("/updateStockItemStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStockItemStatus(StockItem stockItem) {
		Gson gSon = new Gson();
		logger.debug("stockItem update request:"+gSon.toJson(stockItem));
		StockItemServiceImpl stockImpl = new StockItemServiceImpl();
		APIResponse<String> apiResponse = stockImpl.updateStockItemStatus(stockItem);
		logger.debug("stockItem update response:"+gSon.toJson(apiResponse));
		return Response.status(201).entity(apiResponse).build();

	}
	
	public static void main(String[] args) {
		StockItemService impl=new StockItemService();
		impl.getStocks(3);
	}
}

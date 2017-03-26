package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.StockItemDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.StockItem;

public class StockItemServiceImpl {

	public APIResponse<String> registerStockItems(StockItem stockItem) {
		StockItemDaoImpl stockData = new StockItemDaoImpl();
		APIResponse<String> apiRespons = stockData.registerStockItems(stockItem);
		return apiRespons;

	}

	public APIResponse<List<StockItem>> getStockItems(long stockItemId) {
		StockItemDaoImpl stockData = new StockItemDaoImpl();
		APIResponse<List<StockItem>> apiRespons = stockData
				.getStockItems(stockItemId);
		return apiRespons;

	}

	public APIResponse<String> updateStockItems(StockItem stockItems) {
		StockItemDaoImpl stockDao = new StockItemDaoImpl();
		APIResponse<String> apiRespons = stockDao
				.updateStockItems(stockItems);
		return apiRespons;

	}
	
	
	public APIResponse<String> deleteStockItems(long stockItemId) {
		StockItemDaoImpl stockDao = new StockItemDaoImpl();
		APIResponse<String> apiRespons = stockDao
				.deleteStockItems(stockItemId);
		return apiRespons;

	}
	
	
	

	public APIResponse<String> updateStockItemStatus(StockItem stockItems) {
		StockItemDaoImpl stockDao = new StockItemDaoImpl();
		APIResponse<String> apiRespons = stockDao
				.updateStockItemStatus(stockItems);
		return apiRespons;

	}
}

package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.StockDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.Stock;

public class StockServiceImpl {

	public APIResponse<String> registerStock(Stock stock) {
		StockDaoImpl stockData = new StockDaoImpl();
		APIResponse<String> apiRespons = stockData.registerStock(stock);
		return apiRespons;

	}

	public APIResponse<List<Stock>> getStocks(String filter) {
		StockDaoImpl stockData = new StockDaoImpl();
		APIResponse<List<Stock>> apiRespons = stockData
				.getStocks(filter);
		return apiRespons;

	}

	public APIResponse<String> updateStocks(Stock stock) {
		StockDaoImpl stockDao = new StockDaoImpl();
		APIResponse<String> apiRespons = stockDao
				.updateStocks(stock);
		return apiRespons;

	}
	
	public APIResponse<String> updateStockStatus(Stock stock) {
		StockDaoImpl stockDao = new StockDaoImpl();
		APIResponse<String> apiRespons = stockDao
				.updateStockStatus(stock);
		return apiRespons;

	}
	
	public APIResponse<String> deleteStocks(long stockId) {
		StockDaoImpl stockDao = new StockDaoImpl();
		APIResponse<String> apiRespons = stockDao
				.deleteStocks(stockId);
		return apiRespons;

	}
}

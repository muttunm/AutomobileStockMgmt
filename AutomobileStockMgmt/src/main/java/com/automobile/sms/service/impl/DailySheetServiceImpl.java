package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.DailySheetDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DailySheetDetails;

public class DailySheetServiceImpl {

	
	public APIResponse<String> registerDailySheet(List<DailySheetDetails> dailySheetDetails) {
		DailySheetDaoImpl dailySheetDaoImpl = new DailySheetDaoImpl();
		APIResponse<String> apiRespons = dailySheetDaoImpl.registerDailySheet(dailySheetDetails);
		return apiRespons;

	}
	
	public APIResponse<List<DailySheetDetails>> getDailySheet(String date) {
		DailySheetDaoImpl dailySheetDaoImpl = new DailySheetDaoImpl();
		APIResponse<List<DailySheetDetails>> apiRespons = dailySheetDaoImpl.getDailySheet(date);
		return apiRespons;

	}
}

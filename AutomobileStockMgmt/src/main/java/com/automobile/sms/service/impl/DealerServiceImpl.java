package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.DealerDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerDetails;

public class DealerServiceImpl {

	public APIResponse<String> registerDealer(DealerDetails dealerDetails) {
		DealerDaoImpl dealerData = new DealerDaoImpl();
		APIResponse<String> apiRespons = dealerData.registerDealer(dealerDetails);
		return apiRespons;

	}

	public APIResponse<List<DealerDetails>> getDealers() {
		DealerDaoImpl dealerData = new DealerDaoImpl();
		APIResponse<List<DealerDetails>> apiRespons = dealerData.getDealers();
		return apiRespons;

	}
	 
	public APIResponse<List<DealerDetails>> updateDealer(DealerDetails dealerDetails){
		DealerDaoImpl dealerDao = new DealerDaoImpl();
		APIResponse<List<DealerDetails>> apiRespons = dealerDao.updateDealer(dealerDetails);
		return apiRespons;
		
	}
}

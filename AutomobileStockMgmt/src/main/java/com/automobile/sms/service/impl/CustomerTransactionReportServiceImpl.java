package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.CustomerTransactionReportDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerTransactionReportDetails;

public class CustomerTransactionReportServiceImpl {
	public APIResponse<List<CustomerTransactionReportDetails>> getCustomerTransactionsReport(String fromDate,String toDate, String filter) {
		CustomerTransactionReportDaoImpl customerTransactionReportDao = new CustomerTransactionReportDaoImpl();
		APIResponse<List<CustomerTransactionReportDetails>> apiResponse = customerTransactionReportDao.getCustomerTransactionsReport(fromDate,toDate, filter);
		return apiResponse;
	}

}

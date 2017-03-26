package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.DealerTransactionReportDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerTransactionReportDetails;

public class DealerTransactionReportServiceImpl {
	public APIResponse<List<DealerTransactionReportDetails>> getDealerTransactionsReport(String fromDate,String toDate) {
		DealerTransactionReportDaoImpl customerTransactionReportDao = new DealerTransactionReportDaoImpl();
		APIResponse<List<DealerTransactionReportDetails>> apiResponse = customerTransactionReportDao.getDealerTransactionsReport(fromDate,toDate);
		return apiResponse;
	}

}

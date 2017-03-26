package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.DealerTransactionDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerTransactionDetails;

public class DealerTransactionServiceImpl {
	public APIResponse<DealerTransactionDetails> createDealerTransaction(DealerTransactionDetails dealerTransactionDetails) {
		DealerTransactionDaoImpl dealerTransactionDao = new DealerTransactionDaoImpl();
		APIResponse<DealerTransactionDetails> apiResponse = dealerTransactionDao.createTransaction(dealerTransactionDetails);
		return apiResponse;

	}
	public APIResponse<List<DealerTransactionDetails>> getDealerTransactions(String fromDate,String toDate,long dealerId,String transactionStatus) {
		DealerTransactionDaoImpl dealerTransactionDao = new DealerTransactionDaoImpl();
		APIResponse<List<DealerTransactionDetails>> apiResponse = dealerTransactionDao.getDealerTransactions(fromDate,toDate,dealerId,transactionStatus);
		return apiResponse;

	}
	public APIResponse<DealerTransactionDetails> updateDealerTransaction(DealerTransactionDetails dealerTransactionDetails) {
		DealerTransactionDaoImpl dealerTransactionDao = new DealerTransactionDaoImpl();
		APIResponse<DealerTransactionDetails> apiResponse = dealerTransactionDao.updateDealerTransaction(dealerTransactionDetails);
		return apiResponse;

	}
}

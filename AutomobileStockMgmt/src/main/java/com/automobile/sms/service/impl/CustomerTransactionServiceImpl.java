package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.CustomerDaoImpl;
import com.automobile.sms.dao.CustomerTransactionDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerTransactionDetails;

public class CustomerTransactionServiceImpl {
	public APIResponse<CustomerTransactionDetails> createCustomerTransaction(CustomerTransactionDetails customerTransactionDetails) {
		if(customerTransactionDetails.getIsGuestUser() && !(customerTransactionDetails.getCustomerId()>0)){
			CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
			customerDaoImpl.registerCustomer(customerTransactionDetails.getCustomerDetails());
			customerTransactionDetails.setCustomerId(customerDaoImpl.getMaxCustomerId());
		}
		CustomerTransactionDaoImpl customerTransactionDao = new CustomerTransactionDaoImpl();
		APIResponse<CustomerTransactionDetails> apiResponse = customerTransactionDao.createTransaction(customerTransactionDetails);
		return apiResponse;
	}
	public APIResponse<List<CustomerTransactionDetails>> getCustomerTransactions(String fromDate,String toDate,long customerId,String transactionStatus) {
		CustomerTransactionDaoImpl customerTransactionDao = new CustomerTransactionDaoImpl();
		APIResponse<List<CustomerTransactionDetails>> apiResponse = customerTransactionDao.getCustomerTransactions(fromDate,toDate,customerId,transactionStatus);
		return apiResponse;
	}
	public APIResponse<CustomerTransactionDetails> updateCustomerTransaction(CustomerTransactionDetails customerTransactionDetails) {
		CustomerTransactionDaoImpl customerTransactionDao = new CustomerTransactionDaoImpl();
		APIResponse<CustomerTransactionDetails> apiResponse = customerTransactionDao.updateCustomerTransaction(customerTransactionDetails);
		return apiResponse;
	}
	
}

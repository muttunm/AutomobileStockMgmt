package com.automobile.sms.service.impl;

import java.util.List;

import com.automobile.sms.dao.CustomerDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerDetails;

public class CustomerServiceImpl {

	public APIResponse<String> registerCustomer(CustomerDetails customerDetails) {
		CustomerDaoImpl customerData = new CustomerDaoImpl();
		APIResponse<String> apiRespons = customerData.registerCustomer(customerDetails);
		return apiRespons;

	}

	
	public APIResponse<List<CustomerDetails>> getCustomerById(String id) {
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		APIResponse<List<CustomerDetails>> apiRespons = customerDao.getCustomerById(id);
		return apiRespons;

	}
	public APIResponse<List<CustomerDetails>> getCustomers(String filter) {
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		APIResponse<List<CustomerDetails>> apiRespons = customerDao.getCustomers(filter);
		return apiRespons;

	}
	 
	public APIResponse<String> updateCustomer(CustomerDetails customerDetails){
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		APIResponse<String> apiRespons = customerDao.updateCustomer(customerDetails);
		return apiRespons;
		
	}
}

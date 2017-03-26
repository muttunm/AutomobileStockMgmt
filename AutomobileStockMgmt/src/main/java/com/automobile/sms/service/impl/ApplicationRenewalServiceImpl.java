package com.automobile.sms.service.impl;

import com.automobile.sms.dao.ApplicationRenewalServiceDao;
import com.automobile.sms.model.APIResponse;

public class ApplicationRenewalServiceImpl {

	public APIResponse<String> getRenewalDetails() {
		ApplicationRenewalServiceDao renewalData = new ApplicationRenewalServiceDao();
		APIResponse<String> apiRespons = renewalData.getRenewalDetails();
		return apiRespons;

	}
}

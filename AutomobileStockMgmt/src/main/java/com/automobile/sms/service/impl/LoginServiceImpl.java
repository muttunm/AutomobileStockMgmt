package com.automobile.sms.service.impl;

import com.automobile.sms.dao.LoginDaoImpl;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.LoginDetails;

public class LoginServiceImpl {

	public APIResponse<String> verifyLogin(LoginDetails loginDetails) {
		LoginDaoImpl loginDaoImpl = new LoginDaoImpl();
		APIResponse<String> response= loginDaoImpl.verifyUserLogin(loginDetails);
		return response;

	}

}
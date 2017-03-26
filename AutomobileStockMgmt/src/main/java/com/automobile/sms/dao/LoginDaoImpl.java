package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.LoginDetails;
import com.automobile.sms.util.JdbcConnectionUtil;

public class LoginDaoImpl {

	public APIResponse<String> verifyUserLogin(LoginDetails loginDetails) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<String> apiResponse = new APIResponse<String>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT user_name, password, user_type FROM login_info where user_name='"
						+ loginDetails.getUserName()
						+ "' and password='"
						+ loginDetails.getPassword()
						+ "' and user_type='"
						+ loginDetails.getUserType() + "'";
				ResultSet rs = stmt.executeQuery(sql);

				// STEP 5: Extract data from result set
				if (null != rs && rs.next()) {
					apiResponse.setStatus(true);
					// STEP 6: Clean-up environment
					rs.close();
				} else {
					apiResponse.setStatus(false);
					error.setErrorMessage("Invalid Username or Password");
					apiResponse.setError(error);
				}
				stmt.close();
				conn.close();
			} else {
				apiResponse.setStatus(false);
				error.setErrorMessage("Databse Connection Failed");
				apiResponse.setError(error);
			}
		} catch (Exception e) {
			apiResponse.setStatus(false);
			if (e.getMessage() != null)
				error.setErrorMessage(e.getMessage());
			else if (e.getCause() != null)
				error.setErrorMessage(e.getCause().getLocalizedMessage());
			apiResponse.setError(error);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO:
			}
		}
		return apiResponse;
	}
}

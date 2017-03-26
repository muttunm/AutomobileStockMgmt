package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.util.JdbcConnectionUtil;

public class ApplicationRenewalServiceDao {

	public APIResponse<String> getRenewalDetails() {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<String> apiResponse = new APIResponse<String>();
		APIError error = new APIError();
		String res = null;
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select  application_renewal_date from "
						+ "application_expiry_details";
				ResultSet rs = stmt.executeQuery(sql);

				if (null != rs) {
					while (rs.next()) {
						res = rs.getString("application_renewal_date");

					}

					Date date1 = new Date();

					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");

					Date date2 = formatter.parse(res);

					if (date1.compareTo(date2) <= 0) {
						apiResponse.setStatus(true);

					} else {
						apiResponse.setStatus(false);
					}

					rs.close();
				} else {
					apiResponse.setStatus(false);
					error.setErrorMessage("invalid request");
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

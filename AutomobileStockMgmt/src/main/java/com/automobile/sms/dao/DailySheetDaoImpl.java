package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DailySheetDetails;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.google.gson.Gson;

public class DailySheetDaoImpl {

	public APIResponse<String> registerDailySheet(List<DailySheetDetails> dailySheetDetailsList) {
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<String> apiResponse = new APIResponse<String>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				String sql;
				for(DailySheetDetails dailySheetDetails:dailySheetDetailsList){
				sql = "insert into daily_sheet  values('"
						+ dailySheetDetails.getParticulars()
						+ "','"
						+ dailySheetDetails.getQuantity()
						+ "','"
						+ dailySheetDetails.getBillNo()
						+ "','"
						+ dailySheetDetails.getAmount()
						+ "','"
						+ dailySheetDetails.getDateOfExpenditure().substring(0, 10) 
						+ "','"
						+ dailySheetDetails.getExpenditureType()+ "')";
				stmt.addBatch(sql);
				//
				//stmt.execute(sql);
				}
				stmt.executeBatch();
				conn.commit();
				apiResponse.setStatus(true);
				stmt.close();
				conn.close();
			} else {
				apiResponse.setStatus(false);
				error.setErrorMessage("Database Connection Failed");
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
	
	
	public APIResponse<List<DailySheetDetails>> getDailySheet(String date) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<DailySheetDetails>> apiResponse = new APIResponse<List<DailySheetDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select * from daily_sheet where date_of_expenditure='"
						+ date.substring(0, 10)  + "'";
				ResultSet rs = stmt.executeQuery(sql);
				List<DailySheetDetails> dailySheetDetails = new ArrayList<DailySheetDetails>();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						DailySheetDetails details = new DailySheetDetails();
						details.setAmount(rs.getString("amount"));
						details.setBillNo(rs
								.getString("bill_no"));
						details.setDateOfExpenditure(rs
								.getString("date_of_expenditure"));
						details.setExpenditureType(rs
								.getString("expenditure_type"));
						details.setParticulars(rs
								.getString("perticulers"));
						details.setQuantity(rs
								.getString("quantity"));
						

						dailySheetDetails.add(details);

					}
					apiResponse.setDataModel(dailySheetDetails);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ dailySheetDetails.size());

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
				error.setErrorMessage("Database Connection Failed");
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
	
	public static void main(String[] args) {
		
		
		DailySheetDetails d=new DailySheetDetails();
		DailySheetDetails d2=new DailySheetDetails();
		d.setAmount("100");
		d.setBillNo("100");
		d.setDateOfExpenditure("2012-10-02");
		d.setExpenditureType("dailyt");
		d.setParticulars("chay choda");
		d.setQuantity("1");
		
		d2.setAmount("100");
		d2.setBillNo("100");
		d2.setDateOfExpenditure("2012-10-02");
		d2.setExpenditureType("dailyt");
		d2.setParticulars("chay choda");
		d2.setQuantity("1");
		
		DailySheetDaoImpl dd=new DailySheetDaoImpl();
		List<DailySheetDetails> list = new ArrayList<DailySheetDetails>();
		//Gson gSon = new Gson();
		//System.out.println(gSon.toJson(d));
		list.add(d);
		list.add(d2);
		dd.registerDailySheet(list);
		//dd.getDailySheet("2012-10-02");
	}
}

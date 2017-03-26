package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerDetails;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.google.gson.Gson;

public class CustomerDaoImpl {

	public APIResponse<String> registerCustomer(CustomerDetails customerDetails) {
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
				sql = "insert into customer_info(cust_title,cust_firstname, cust_lastname,cust_addres,cust_place,"
						+ "cust_vehicleno,cust_vehicletype,cust_mobile,cust_emailid,cust_imgurl,cust_reg_date,is_guest_user)"
						+ " values('"
						+ customerDetails.getCustomerTitle()
						+ "','"
						+ customerDetails.getCustomerFirstName()
						+ "','"
						+ customerDetails.getCustomerLastName()
						+ "','"
						+ customerDetails.getCustomerAddress()
						+ "','"
						+ customerDetails.getCustomerPlace()
						+ "','"
						+ customerDetails.getCustomerVehicleNum()
						+ "','"
						+ customerDetails.getCustomerVehicleType()
						+ "','"
						+ customerDetails.getCustomerMobile()
						+ "','"
						+ customerDetails.getCustomerEmailId()
						+ "','"
						+ customerDetails.getCustomerImageUrl()
						+ "','"
						+ customerDetails.getCustomerRegisteredDate()
								.substring(0, 10)
						+ "',"
						+ customerDetails.getIsGuestUser() + ")";
				stmt.execute(sql);
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

	public APIResponse<List<CustomerDetails>> getCustomers(String filter) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<CustomerDetails>> apiResponse = new APIResponse<List<CustomerDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql = null;
				if (filter == null || filter.equalsIgnoreCase("")
						|| filter.equalsIgnoreCase("all")) {
					sql = "select * from customer_info";
				} else if (filter.equalsIgnoreCase("customer")) {
					sql = "select * from customer_info where is_guest_user=false";
				} else {
					sql = "select * from customer_info where is_guest_user=true";
				}
				ResultSet rs = stmt.executeQuery(sql);
				List<CustomerDetails> customerDetailsresList = new ArrayList<CustomerDetails>();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						CustomerDetails customerDetailsres = new CustomerDetails();
						customerDetailsres.setCustomerId(rs.getLong("cust_id"));
						customerDetailsres.setCustomerTitle(rs
								.getString("cust_title"));
						customerDetailsres.setCustomerFirstName(rs
								.getString("cust_firstname"));
						customerDetailsres.setCustomerLastName(rs
								.getString("cust_lastname"));
						customerDetailsres.setCustomerAddress(rs
								.getString("cust_addres"));
						customerDetailsres.setCustomerPlace(rs
								.getString("cust_place"));
						customerDetailsres.setCustomerVehicleNum(rs
								.getString("cust_vehicleno"));
						customerDetailsres.setCustomerVehicleType(rs
								.getString("cust_vehicletype"));
						customerDetailsres.setCustomerMobile(rs
								.getString("cust_mobile"));
						customerDetailsres.setCustomerEmailId(rs
								.getString("cust_emailid"));
						customerDetailsres.setCustomerImageUrl(rs
								.getString("cust_imgurl"));
						customerDetailsres.setCustomerRegisteredDate(rs
								.getString("cust_reg_date"));
						customerDetailsres.setIsGuestUser(rs
								.getBoolean("is_guest_user"));
						customerDetailsresList.add(customerDetailsres);

					}
					apiResponse.setDataModel(customerDetailsresList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ customerDetailsresList.size());

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

	public APIResponse<String> updateCustomer(CustomerDetails customerDetails) {
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
				sql = " update  customer_info" + " set cust_title='"
						+ customerDetails.getCustomerTitle()
						+ "',"
						+ "cust_firstname='"
						+ customerDetails.getCustomerFirstName()
						+ "',"
						+ " cust_lastname='"
						+ customerDetails.getCustomerLastName()
						+ "',"
						+ " cust_addres='"
						+ customerDetails.getCustomerAddress()
						+ "',"
						+ " cust_place='"
						+ customerDetails.getCustomerPlace()
						+ "',"
						+ " cust_vehicleno='"
						+ customerDetails.getCustomerVehicleNum()
						+ "',"
						+ " cust_vehicletype='"
						+ customerDetails.getCustomerVehicleType()
						+ "',"
						+ " cust_mobile='"
						+ customerDetails.getCustomerMobile()
						+ "',"
						+ "cust_emailid='"
						+ customerDetails.getCustomerEmailId()
						+ "',"
						+ "cust_imgurl='"
						+ customerDetails.getCustomerImageUrl()
						+ "',"
						+ "cust_reg_date='"
						+ customerDetails.getCustomerRegisteredDate()
								.substring(0, 10) + "' where cust_id='"
						+ customerDetails.getCustomerId() + "'";
				stmt.executeUpdate(sql);
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

	public CustomerDetails getCustomerById(long customerId) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select * from customer_info where cust_id='"
						+ customerId + "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (null != rs) {
					while (rs.next()) {
						CustomerDetails customerDetails = new CustomerDetails();
						customerDetails.setCustomerId(rs.getLong("cust_id"));
						customerDetails.setCustomerTitle(rs
								.getString("cust_title"));
						customerDetails.setCustomerFirstName(rs
								.getString("cust_firstname"));
						customerDetails.setCustomerLastName(rs
								.getString("cust_lastname"));
						customerDetails.setCustomerAddress(rs
								.getString("cust_addres"));
						customerDetails.setCustomerPlace(rs
								.getString("cust_place"));
						customerDetails.setCustomerVehicleNum(rs
								.getString("cust_vehicleno"));
						customerDetails.setCustomerVehicleType(rs
								.getString("cust_vehicletype"));
						customerDetails.setCustomerMobile(rs
								.getString("cust_mobile"));
						customerDetails.setCustomerEmailId(rs
								.getString("cust_emailid"));
						customerDetails.setCustomerImageUrl(rs
								.getString("cust_imgurl"));
						customerDetails.setCustomerRegisteredDate(rs
								.getString("cust_reg_date"));
						customerDetails.setIsGuestUser(rs
								.getBoolean("is_guest_user"));
						return customerDetails;
					}
				}
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO:
			}
		}
		return null;
	}

	public Long getMaxCustomerId() {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select * from customer_info where cust_id=(select MAX(cust_id) from customer_info)";
				ResultSet rs = stmt.executeQuery(sql);
				if (null != rs) {
					while (rs.next()) {
						return rs.getLong("cust_id");
					}
				}
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO:
			}
		}
		return null;
	}

	public APIResponse<List<CustomerDetails>> getCustomerById(String id) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<CustomerDetails>> apiResponse = new APIResponse<List<CustomerDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql = null;
				
					sql = "select * from customer_info where cust_id='"+id+"'";
		
					
				ResultSet rs = stmt.executeQuery(sql);
				List<CustomerDetails> customerDetailsresList = new ArrayList<CustomerDetails>();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						CustomerDetails customerDetailsres = new CustomerDetails();
						customerDetailsres.setCustomerId(rs.getLong("cust_id"));
						customerDetailsres.setCustomerTitle(rs
								.getString("cust_title"));
						customerDetailsres.setCustomerFirstName(rs
								.getString("cust_firstname"));
						customerDetailsres.setCustomerLastName(rs
								.getString("cust_lastname"));
						customerDetailsres.setCustomerAddress(rs
								.getString("cust_addres"));
						customerDetailsres.setCustomerPlace(rs
								.getString("cust_place"));
						customerDetailsres.setCustomerVehicleNum(rs
								.getString("cust_vehicleno"));
						customerDetailsres.setCustomerVehicleType(rs
								.getString("cust_vehicletype"));
						customerDetailsres.setCustomerMobile(rs
								.getString("cust_mobile"));
						customerDetailsres.setCustomerEmailId(rs
								.getString("cust_emailid"));
						customerDetailsres.setCustomerImageUrl(rs
								.getString("cust_imgurl"));
						customerDetailsres.setCustomerRegisteredDate(rs
								.getString("cust_reg_date"));
						customerDetailsres.setIsGuestUser(rs
								.getBoolean("is_guest_user"));
						customerDetailsresList.add(customerDetailsres);

					}
					apiResponse.setDataModel(customerDetailsresList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ customerDetailsresList.size());

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
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setCustomerAddress("Bangalore");
		customerDetails.setCustomerMobile("9901084213");
		customerDetails.setCustomerEmailId("myemail@mail.com");
		customerDetails.setCustomerFirstName("Satish");
		customerDetails.setCustomerImageUrl("http://ImageUrl.com");
		customerDetails.setCustomerLastName("Kodadur");
		customerDetails.setCustomerPlace("Bangalore");
		customerDetails.setCustomerVehicleNum("4567891");
		customerDetails.setCustomerVehicleType("Car");
		customerDetails.setCustomerTitle("MyTitle");

		customerDetails.setCustomerRegisteredDate("0001-01-01T00:00:00Z");
		Gson gson = new Gson();
		System.out.println(gson.toJson(customerDetails));
		
		// customerDetails.setCustomerId(1);
		
		  CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
		  customerDaoImpl.getCustomerById("2");
		 
	 customerDaoImpl.updateCustomer(customerDetails);
	}
}

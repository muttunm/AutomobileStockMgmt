package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerDetails;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.google.gson.Gson;

public class DealerDaoImpl {

	public APIResponse<String> registerDealer(DealerDetails dealerDetails) {
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
				sql = "insert into dealer_info(dealer_title,dealer_firstname, dealer_lastname,dealer_addres,dealer_place,"
						+ "dealer_tinno,dealer_mobile,dealer_emailid,dealer_imgurl,dealer_reg_date)"
						+ " values('"
						+ dealerDetails.getTitle()
						+ "','"
						+ dealerDetails.getFirstName()
						+ "','"
						+ dealerDetails.getLastName()
						+ "','"
						+ dealerDetails.getAddress()
						+ "'"
						+ ",'"
						+ dealerDetails.getPlace()
						+ "','"
						+ dealerDetails.getTinNumber()
						+ "','"
						+ dealerDetails.getContactNumber()
						+ "'"
						+ ",'"
						+ dealerDetails.getEmailId()
						+ "','"
						+ dealerDetails.getImgUrl()
						+ "'"
						+ ",'"
						+ dealerDetails.getRegisteredDate().substring(0, 10)
						+ "')";
				stmt.execute(sql);
				apiResponse.setStatus(true);
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

	public APIResponse<List<DealerDetails>> getDealers() {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<DealerDetails>> apiResponse = new APIResponse<List<DealerDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select  dealer_id,dealer_title,dealer_firstname, dealer_lastname,dealer_addres,dealer_place,"
						+ "dealer_tinno,dealer_mobile,dealer_emailid,dealer_imgurl,dealer_reg_date from "
						+ "dealer_info";
				ResultSet rs = stmt.executeQuery(sql);
				List<DealerDetails> dealerDetailsresList = new ArrayList<DealerDetails>();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						DealerDetails dealerDetailsres = new DealerDetails();
						dealerDetailsres.setId(rs.getLong("dealer_id"));
						dealerDetailsres.setTitle(rs.getString("dealer_title"));
						dealerDetailsres.setFirstName(rs
								.getString("dealer_firstname"));
						dealerDetailsres.setLastName(rs
								.getString("dealer_lastname"));
						dealerDetailsres.setAddress(rs
								.getString("dealer_addres"));
						dealerDetailsres.setPlace(rs.getString("dealer_place"));
						dealerDetailsres.setTinNumber(rs
								.getString("dealer_tinno"));
						dealerDetailsres.setContactNumber(rs
								.getString("dealer_mobile"));
						dealerDetailsres.setEmailId(rs
								.getString("dealer_emailid"));
						dealerDetailsres.setImgUrl(rs
								.getString("dealer_imgurl"));
						dealerDetailsres.setRegisteredDate(rs
								.getString("dealer_reg_date"));

						dealerDetailsresList.add(dealerDetailsres);

					}
					apiResponse.setDataModel(dealerDetailsresList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ dealerDetailsresList.size());

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

	public APIResponse<List<DealerDetails>> updateDealer(
			DealerDetails dealerDetails) {
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<DealerDetails>> apiResponse = new APIResponse<List<DealerDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = " update  dealer_info" + " set dealer_title='"
						+ dealerDetails.getTitle() + "',"
						+ "dealer_firstname='" + dealerDetails.getFirstName()
						+ "'," + " dealer_lastname='"
						+ dealerDetails.getLastName() + "',"
						+ " dealer_addres='" + dealerDetails.getAddress()
						+ "'," + " dealer_place='" + dealerDetails.getPlace()
						+ "'," + " dealer_tinno='"
						+ dealerDetails.getTinNumber() + "',"
						+ " dealer_mobile='" + dealerDetails.getContactNumber()
						+ "'," + "dealer_emailid='"
						+ dealerDetails.getEmailId() + "'," + "dealer_imgurl='"
						+ dealerDetails.getImgUrl() + "',"
						+ "dealer_reg_date='"
						+ dealerDetails.getRegisteredDate().substring(0, 10)
						+ "' where dealer_id='" + dealerDetails.getId() + "'";
				stmt.executeUpdate(sql);
				apiResponse.setStatus(true);
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

	public DealerDetails getDealerById(long dealerId) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select * from dealer_info where dealer_id='" + dealerId
						+ "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (null != rs) {
					while (rs.next()) {
						DealerDetails dealerDetails = new DealerDetails();
						dealerDetails.setId(rs.getLong("dealer_id"));
						dealerDetails.setTitle(rs.getString("dealer_title"));
						dealerDetails.setFirstName(rs
								.getString("dealer_firstname"));
						dealerDetails.setLastName(rs
								.getString("dealer_lastname"));
						dealerDetails.setAddress(rs.getString("dealer_addres"));
						dealerDetails.setPlace(rs.getString("dealer_place"));
						dealerDetails.setContactNumber(rs
								.getString("dealer_mobile"));
						dealerDetails
								.setEmailId(rs.getString("dealer_emailid"));
						dealerDetails.setImgUrl(rs.getString("dealer_imgurl"));
						dealerDetails.setRegisteredDate(rs
								.getString("dealer_reg_date"));
						return dealerDetails;
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

	public static void main(String[] args) {
		DealerDetails dealerDetails = new DealerDetails();
		dealerDetails.setAddress("Bangalore");
		dealerDetails.setContactNumber("9901084213");
		dealerDetails.setEmailId("myemail@mail.com");
		dealerDetails.setFirstName("Muttu");
		dealerDetails.setImgUrl("http://ImageUrl.com");
		dealerDetails.setLastName("Kodadur");
		dealerDetails.setPlace("Bangalore");
		dealerDetails.setTinNumber("4567891");
		dealerDetails.setTitle("MyTitle");

		dealerDetails.setRegisteredDate("0001-01-01T00:00:00Z");
		// dealerDetails.setId(1);
		Gson gs = new Gson();
		System.out.println(gs.toJson(dealerDetails));
		// DealerDaoImpl dealerDaoImpl = new DealerDaoImpl();
		// dealerDaoImpl.registerDealer(dealerDetails);
		// dealerDaoImpl.updateDealer(dealerDetails);
	}
}

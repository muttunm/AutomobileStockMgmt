package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.Stock;
import com.automobile.sms.util.JdbcConnectionUtil;

public class StockDaoImpl {

	public APIResponse<String> registerStock(Stock stock) {
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
				sql = "insert into stocks(stock_id, stock_name, status)" + " values('"
						+ stock.getStockId() + "','" + stock.getStockName()
						+ "','active')";
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
			if(e.getMessage() != null)
				error.setErrorMessage(e.getMessage());
				else if(e.getCause()!= null)
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

	public APIResponse<List<Stock>> getStocks(String filter) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<Stock>> apiResponse = new APIResponse<List<Stock>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql=null;
				if(filter == null || filter.equalsIgnoreCase("") || filter.equalsIgnoreCase("all")){
					sql = "select stock_id,stock_name,status from stocks";
				}
				else if(filter.equalsIgnoreCase("active")){
					sql = "select stock_id,stock_name,status from stocks where status='active'";
				}
				else if(filter.equalsIgnoreCase("inactive")){
					sql = "select stock_id,stock_name,status from stocks where status='inactive'";
				}
				ResultSet rs = stmt.executeQuery(sql);
				List<Stock> stockList = new ArrayList<Stock>();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						Stock stocks = new Stock();
						stocks.setStockId(rs.getLong("stock_id"));
						stocks.setStockName(rs.getString("stock_name"));
						stocks.setStockStatus(rs.getString("status"));
						stockList.add(stocks);

					}
					apiResponse.setDataModel(stockList);
					apiResponse.setStatus(true);
					System.out.println("list size " + stockList.size());

					rs.close();
				} else {
					apiResponse.setStatus(false);
					error.setErrorMessage("Stock retrival error");
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
			if(e.getMessage() != null)
				error.setErrorMessage(e.getMessage());
				else if(e.getCause()!= null)
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

	public APIResponse<String> updateStocks(Stock stock) {
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
				sql = " update  stocks" + " set stock_name='"
						+ stock.getStockName() + "' where stock_id='"
						+ stock.getStockId() + "'";
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

	public APIResponse<String> deleteStocks(long stockId) {
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
				
				sql = " update  stocks set status='inactive' where stock_id='"
						+ stockId + "'";
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
	
	public APIResponse<String> updateStockStatus(Stock stock) {
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
				sql = " update  stocks" + " set status='active' where stock_id='"
						+ stock.getStockId() + "'";
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


	public static void main(String[] args) {
		Stock stock = new Stock();
		stock.setStockName("satish");
		stock.setStockId(1);

		StockDaoImpl customerDaoImpl = new StockDaoImpl();
		// customerDaoImpl.registerCustomer(customerDetails);
		// customerDaoImpl.updateCustomer(customerDetails);
		// customerDaoImpl.deleteStocks(1);
		customerDaoImpl.registerStock(stock);
		customerDaoImpl.updateStockStatus(stock);
	}

}

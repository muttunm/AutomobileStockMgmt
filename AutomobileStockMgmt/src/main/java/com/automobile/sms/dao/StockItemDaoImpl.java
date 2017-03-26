package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.StockItem;
import com.automobile.sms.util.JdbcConnectionUtil;

public class StockItemDaoImpl {

	public APIResponse<String> registerStockItems(StockItem stockItem) {
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
				sql = "insert into stock_item(stock_item_id,stock_item_name,quantity,unit_type,stock_id,unit_price,status)"
						+ " values('"
						+ stockItem.getStockItemId()
						+ "','"
						+ stockItem.getStockItemName()
						+ "','"
						+ stockItem.getQuantity()
						+ "','"
						+ stockItem.getUnitType()
						+ "','"
						+ stockItem.getStockId()
						+ "','"
						+ stockItem.getUnitPrice() + "',"
								+ "'active')";
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
		}
		finally{
			   try{
			   if(conn != null)
				   conn.close();
			   }
			   catch(Exception e){
				   //TODO:
			   }
		   }
		return apiResponse;
	}

	public APIResponse<List<StockItem>> getStockItems(long stockItemId) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<StockItem>> apiResponse = new APIResponse<List<StockItem>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			 conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select  stock_item_id,stock_item_name,quantity,unit_type,stock_id,unit_price,status from stock_item"
						+  " where stock_id='"
						+ stockItemId+ "'";
				ResultSet rs = stmt.executeQuery(sql);
				List<StockItem> stockList = new ArrayList<StockItem>();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						StockItem stocks = new StockItem();
						stocks.setStockItemId(rs.getLong("stock_item_id"));
						stocks.setStockItemName(rs.getString("stock_item_name"));
						stocks.setQuantity(rs.getString("quantity"));
						stocks.setUnitType(rs.getString("unit_type"));
						stocks.setStockId(rs.getLong("stock_id"));
						stocks.setUnitPrice(rs.getDouble("unit_price"));
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
			if (e.getMessage() != null)
				error.setErrorMessage(e.getMessage());
			else if (e.getCause() != null)
				error.setErrorMessage(e.getCause().getLocalizedMessage());
			apiResponse.setError(error);
		}
		finally{
			   try{
			   if(conn != null)
				   conn.close();
			   }
			   catch(Exception e){
				   //TODO:
			   }
		   }
		return apiResponse;
	}

	public APIResponse<String> updateStockItems(StockItem stockItem) {
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
				sql = " update  stock_item" + " set stock_item_name='"
						+stockItem.getStockItemName()
						+ "',"
						+ "quantity='"
						+ stockItem.getQuantity()
						+ "',"
						+ " unit_type='"
						+ stockItem.getUnitType()
						+ "',"
						+ " stock_id='"
						+ stockItem.getStockId()
						+ "',"
						+ " unit_price='"
						+ stockItem.getUnitPrice()
						 + "' where stock_item_id='"
						+ stockItem.getStockItemId() + "'";
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
		}
		finally{
			   try{
			   if(conn != null)
				   conn.close();
			   }
			   catch(Exception e){
				   //TODO:
			   }
		   }
		return apiResponse;
	}
	
	
	public APIResponse<String> updateStockItemStatus(StockItem stockItem) {
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
				sql = " update  stock_item" + " set status='active' where stock_item_id='"
						+ stockItem.getStockItemId() + "'";
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
		}
		finally{
			   try{
			   if(conn != null)
				   conn.close();
			   }
			   catch(Exception e){
				   //TODO:
			   }
		   }
		return apiResponse;
	}
	public APIResponse<String> deleteStockItems(long stockItemId) {
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
				sql = " update  stock_item set status='inactive' where stock_item_id='"
						+ stockItemId + "'";
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
		}
		 finally{
			   try{
			   if(conn != null)
				   conn.close();
			   }
			   catch(Exception e){
				   //TODO:
			   }
		   }
		return apiResponse;
	}

	public List<StockItem> getStockItemsList(String stockItemIds) {
		// Code to access db table
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		List<StockItem> stockList = new ArrayList<StockItem>();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = "select  stock_item_id,stock_item_name,quantity,unit_type,stock_id,unit_price from stock_item"
						+  " where stock_item_id in ("
						+ stockItemIds+ ")";
				ResultSet rs = stmt.executeQuery(sql);
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						StockItem stocks = new StockItem();
						stocks.setStockItemId(rs.getLong("stock_item_id"));
						stocks.setStockItemName(rs.getString("stock_item_name"));
						stocks.setQuantity(rs.getString("quantity"));
						stocks.setUnitType(rs.getString("unit_type"));
						stocks.setStockId(rs.getLong("stock_id"));
						stocks.setUnitPrice(rs.getLong("unit_price"));

						stockList.add(stocks);

					}
					System.out.println("list size " + stockList.size());

					rs.close();
				} else {
					//TODO:
				}
				stmt.close();
				conn.close();
			} else {
				/*apiResponse.setStatus(false);
				error.setErrorMessage("Database Connection Failed");
				apiResponse.setError(error);*/
			}
		} catch (Exception e) {
			/*apiResponse.setStatus(false);
			error.setErrorMessage(e.getMessage());
			apiResponse.setError(error);*/
		}
   finally{
	   try{
	   if(conn != null)
		   conn.close();
	   }
	   catch(Exception e){
		   //TODO:
	   }
   }
		return stockList;
	}
	
	public static void main(String[] args) {
		StockItem customerDetails = new StockItem();
		customerDetails.setStockItemName("Muttu");
		customerDetails.setQuantity("0");
		customerDetails.setUnitType("gg");
		customerDetails.setStockId(1);
		customerDetails.setUnitPrice(11);
		customerDetails.setStockItemId(12);

		StockItemDaoImpl customerDaoImpl = new StockItemDaoImpl();
	//	customerDaoImpl.getStockItems(3);
	customerDaoImpl.registerStockItems(customerDetails);
		// customerDaoImpl.updateStockItems(customerDetails);
	}

}

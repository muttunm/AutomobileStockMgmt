package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerTransactionDetails;
import com.automobile.sms.model.DealerTransactionReportDetails;
import com.automobile.sms.model.StockItem;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.automobile.sms.util.TransactionUtil;

public class DealerTransactionReportDaoImpl {
	public APIResponse<List<DealerTransactionReportDetails>> getDealerTransactionsReport(
			String fromDate, String toDate) {
		// Code to access db table
		// java.sql.Date fromSqlDate = new java.sql.Date(fromDate.getTime());
		// java.sql.Date toSqlDate = new java.sql.Date(toDate.getTime());
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<DealerTransactionReportDetails>> apiResponse = new APIResponse<List<DealerTransactionReportDetails>>();
		List<DealerTransactionReportDetails> dealerTransactionReportDetailsList = new ArrayList<DealerTransactionReportDetails>();
		APIError error = new APIError();
		Map<Long,List<DealerTransactionDetails>> dealerTransactionMap = new HashMap<Long,List<DealerTransactionDetails>>();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
					sql = "select * from Dealer_transaction"
							+ " where trans_date>='"
							+ fromDate.substring(0, 10) + "' and trans_date<='"
							+ toDate.substring(0, 10) + "'";
				ResultSet rs = stmt.executeQuery(sql);
				TransactionUtil transactionUtil = new TransactionUtil();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						DealerTransactionDetails dealerTransactionDetailsres = new DealerTransactionDetails();
						dealerTransactionDetailsres.setDealerId(String.valueOf(rs
								.getLong("dealer_id")));
						dealerTransactionDetailsres.setDueAmount(String.valueOf(rs
								.getDouble("due_amount")));
						dealerTransactionDetailsres.setInvoiceNumber(rs
								.getString("invoice_no"));
						dealerTransactionDetailsres.setInvoiceUrl(rs
								.getString("invoice_url"));
						dealerTransactionDetailsres.setPaidAmount(String.valueOf(rs
								.getDouble("paid_amount")));
						dealerTransactionDetailsres
								.setSeperatedStockItemQuantities(rs
										.getString("stock_item_quantities"));
						dealerTransactionDetailsres.setTotalAmount(String.valueOf(rs
								.getDouble("total_amount")));
						dealerTransactionDetailsres.setTransactionDate(String
								.valueOf(rs.getDate("trans_date")));
						dealerTransactionDetailsres.setTransactionId(String.valueOf(rs
								.getLong("trans_id")));
						dealerTransactionDetailsres.setTransactionStatus(rs
								.getString("trans_status"));
						List<StockItem> storedStockItems = transactionUtil
								.getStockItemDetails(rs
										.getString("stock_item_ids"));
						dealerTransactionDetailsres
						.setSeperatedStockItemPrices(rs
								.getString("stock_item_prices"));
						String[] stockQuantities = dealerTransactionDetailsres
								.getSeperatedStockItemQuantities().split(",");
						String[] stockPrices = dealerTransactionDetailsres
								.getSeperatedStockItemPrices().split(",");
						for (int i = 0; i < stockQuantities.length; i++) {
							storedStockItems.get(i).setQuantity(
									stockQuantities[i]);
							storedStockItems.get(i).setUnitPrice(
									Double.valueOf(stockPrices[i]));
						}
						dealerTransactionDetailsres
								.setStockItems(storedStockItems);
						
						if(null != dealerTransactionMap.get(Long.valueOf(dealerTransactionDetailsres.getDealerId()))){
							List<DealerTransactionDetails> dealerTransactionList = dealerTransactionMap.get(Long.valueOf(dealerTransactionDetailsres.getDealerId()));
							dealerTransactionList.add(dealerTransactionDetailsres);
							dealerTransactionMap.put(Long.valueOf(dealerTransactionDetailsres.getDealerId()), dealerTransactionList);
						}
						else{
							List<DealerTransactionDetails> dealerTransactionList = new ArrayList<DealerTransactionDetails>();
							dealerTransactionList.add(dealerTransactionDetailsres);
							dealerTransactionMap.put(Long.valueOf(dealerTransactionDetailsres.getDealerId()), dealerTransactionList);
						}
					}
					DealerDaoImpl dealerDaoImpl = new DealerDaoImpl();
					
					for (Map.Entry<Long, List<DealerTransactionDetails>> entry : dealerTransactionMap.entrySet()) {
						DealerTransactionReportDetails dealerTransactionReportDetails = new DealerTransactionReportDetails();
						dealerTransactionReportDetails.setDetails(dealerDaoImpl.getDealerById(entry.getKey()));
						dealerTransactionReportDetails.setTransactions(entry.getValue());
						dealerTransactionReportDetailsList.add(dealerTransactionReportDetails);
					}
					
					apiResponse.setDataModel(dealerTransactionReportDetailsList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ dealerTransactionReportDetailsList.size());

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

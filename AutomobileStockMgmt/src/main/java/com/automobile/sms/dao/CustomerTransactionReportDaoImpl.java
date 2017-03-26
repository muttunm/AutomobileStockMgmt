package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerTransactionDetails;
import com.automobile.sms.model.CustomerTransactionReportDetails;
import com.automobile.sms.model.StockItem;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.automobile.sms.util.TransactionUtil;

public class CustomerTransactionReportDaoImpl {
	private static Logger logger = LoggerFactory
			.getLogger(CustomerTransactionReportDaoImpl.class);
	public APIResponse<List<CustomerTransactionReportDetails>> getCustomerTransactionsReport(
			String fromDate, String toDate, String filter) {
		logger.debug("CustomerTransactionReportDaoImpl : filter value is :"+filter);
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<CustomerTransactionReportDetails>> apiResponse = new APIResponse<List<CustomerTransactionReportDetails>>();
		List<CustomerTransactionReportDetails> customerTransactionReportDetailsList = new ArrayList<CustomerTransactionReportDetails>();
		APIError error = new APIError();
		Map<Long,List<CustomerTransactionDetails>> customerTransactionMap = new HashMap<Long,List<CustomerTransactionDetails>>();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				if (filter == null || filter.equalsIgnoreCase("") || filter.equalsIgnoreCase("all")) {
					sql = "select * from Customer_transaction"
							+ " where trans_date>='"
							+ fromDate.substring(0, 10) + "' and trans_date<='"
							+ toDate.substring(0, 10) + "'";
				}
				else if(filter.equalsIgnoreCase("customer")){
					sql = "select * from Customer_transaction"
							+ " where trans_date>='"
							+ fromDate.substring(0, 10) + "' and trans_date<='"
							+ toDate.substring(0, 10) + "'"
							+ " and is_guest_user=false";
				
				}
				else{
					sql = "select * from Customer_transaction"
							+ " where trans_date>='"
							+ fromDate.substring(0, 10) + "' and trans_date<='"
							+ toDate.substring(0, 10) + "'"
							+ " and is_guest_user=true";
				}
				System.out.println("executed SQl Statement :"+sql);
				ResultSet rs = stmt.executeQuery(sql);
				TransactionUtil transactionUtil = new TransactionUtil();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						CustomerTransactionDetails customerTransactionDetailsres = new CustomerTransactionDetails();
						customerTransactionDetailsres.setCustomerId(rs
								.getLong("cust_id"));
						customerTransactionDetailsres.setDueAmount(rs
								.getDouble("due_amount"));
						customerTransactionDetailsres.setInvoiceNumber(rs
								.getString("invoice_no"));
						customerTransactionDetailsres.setInvoiceUrl(rs
								.getString("invoice_url"));
						customerTransactionDetailsres.setPaidAmount(rs
								.getDouble("paid_amount"));
						customerTransactionDetailsres
								.setSeperatedStockItemQuantities(rs
										.getString("stock_item_quantities"));
						customerTransactionDetailsres.setTotalAmount(rs
								.getDouble("total_amount"));
						customerTransactionDetailsres.setTransactionDate(String
								.valueOf(rs.getDate("trans_date")));
						customerTransactionDetailsres.setTransactionId(rs
								.getLong("trans_id"));
						customerTransactionDetailsres.setTransactionStatus(rs
								.getString("trans_status"));
						customerTransactionDetailsres.setVat(rs
								.getDouble("vat"));
						customerTransactionDetailsres.setDiscount(rs
								.getDouble("discount_rate"));
						List<StockItem> storedStockItems = transactionUtil
								.getStockItemDetails(rs
										.getString("stock_item_ids"));
						String[] stockQuantities = customerTransactionDetailsres
								.getSeperatedStockItemQuantities().split(",");
						for (int i = 0; i < stockQuantities.length; i++) {
							storedStockItems.get(i).setQuantity(
									stockQuantities[i]);
						}
						customerTransactionDetailsres
								.setStockItems(storedStockItems);
						
						if(null != customerTransactionMap.get(customerTransactionDetailsres.getCustomerId())){
							List<CustomerTransactionDetails> customerTransactionList = customerTransactionMap.get(customerTransactionDetailsres.getCustomerId());
							customerTransactionList.add(customerTransactionDetailsres);
							customerTransactionMap.put(Long.valueOf(customerTransactionDetailsres.getCustomerId()), customerTransactionList);
						}
						else{
							List<CustomerTransactionDetails> customerTransactionList = new ArrayList<CustomerTransactionDetails>();
							customerTransactionList.add(customerTransactionDetailsres);
							customerTransactionMap.put(Long.valueOf(customerTransactionDetailsres.getCustomerId()), customerTransactionList);
						}
					}
					CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
					
					for (Map.Entry<Long, List<CustomerTransactionDetails>> entry : customerTransactionMap.entrySet()) {
						CustomerTransactionReportDetails customerTransactionReportDetails = new CustomerTransactionReportDetails();
						customerTransactionReportDetails.setDetails(customerDaoImpl.getCustomerById(entry.getKey()));
						customerTransactionReportDetails.setTransactions(entry.getValue());
						customerTransactionReportDetailsList.add(customerTransactionReportDetails);
					}
					
					apiResponse.setDataModel(customerTransactionReportDetailsList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ customerTransactionReportDetailsList.size());

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
	public static void main(String[] args) {
		CustomerTransactionReportDaoImpl customerTransactionReportDaoImpl = new CustomerTransactionReportDaoImpl();
		customerTransactionReportDaoImpl.getCustomerTransactionsReport("2016-01-31T00:00:00Z", "2016-01-31T00:00:00Z", "Guest");
		
	}
}

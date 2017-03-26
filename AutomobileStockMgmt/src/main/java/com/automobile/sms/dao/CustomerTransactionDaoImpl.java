package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.CustomerTransactionDetails;
import com.automobile.sms.model.StockItem;
import com.automobile.sms.service.CustomerService;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.automobile.sms.util.TransactionUtil;
import com.google.gson.Gson;

public class CustomerTransactionDaoImpl {
	private static Logger logger = LoggerFactory.getLogger(CustomerTransactionDaoImpl.class);
	public APIResponse<CustomerTransactionDetails> createTransaction(
			CustomerTransactionDetails customerTransactionDetails) {
		if (Double.valueOf(customerTransactionDetails.getDueAmount()) == 0.0) {
			customerTransactionDetails.setTransactionStatus("Paid");
		} else {
			customerTransactionDetails.setTransactionStatus("UnPaid");
		}
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<CustomerTransactionDetails> apiResponse = new APIResponse<CustomerTransactionDetails>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				conn.setAutoCommit(false);
				String invoiceNo = getInvoiceSequence();

				// stmt = conn.createStatement();
				String insertTransactionsql = "insert into Customer_transaction(cust_id,trans_date,invoice_no,invoice_url,total_amount,"
						+ "paid_amount,due_amount,trans_status,vat,discount_rate,stock_item_ids,stock_item_quantities,stock_item_prices,is_guest_user)"
						+ " values('"
						+ customerTransactionDetails.getCustomerId()
						+ "','"
						+ new java.sql.Date(new java.util.Date().getTime())
						+ "','"
						+ invoiceNo
						+ "','"
						+ customerTransactionDetails.getInvoiceUrl()
						+ "'"
						+ ",'"
						+ customerTransactionDetails.getGrandTotalAmount()
						+ "','"
						+ customerTransactionDetails.getPaidAmount()
						+ "','"
						+ customerTransactionDetails.getDueAmount()
						+ "'"
						+ ",'"
						+ customerTransactionDetails.getTransactionStatus()
						+ "','"
						+ customerTransactionDetails.getVat()
						+ "','"

						+ customerTransactionDetails.getDiscount()
						+ "','"
						+ customerTransactionDetails.getSeperatedStockItemIds()
						+ "'"
						+ ",'"
						+ customerTransactionDetails
								.getSeperatedStockItemQuantities() + "','"
								+ customerTransactionDetails
								.getSeperatedStockItemPrices()
						+ "',"
						+ customerTransactionDetails.getIsGuestUser()+")";
				stmt.addBatch(insertTransactionsql);
				TransactionUtil transactionUtil = new TransactionUtil();
				List<StockItem> storedStockItems = transactionUtil
						.getStockItemDetails(customerTransactionDetails
								.getSeperatedStockItemIds());
				List<StockItem> updatedStockItems = transactionUtil
						.updateCustomerStockItems(customerTransactionDetails
								.getSeperatedStockItemIds(),
								customerTransactionDetails
										.getSeperatedStockItemQuantities(),
								storedStockItems);

				for (StockItem stockItem : updatedStockItems) {
					stmt.addBatch(" update  stock_item" + " set quantity='"
							+ stockItem.getQuantity()
							+ "' where stock_item_id='"
							+ stockItem.getStockItemId() + "'");
				}

				stmt.executeBatch();
				String sql = "select * from Customer_transaction where Trans_id=(select max(Trans_id) from Customer_transaction)";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs != null) {
					while (rs.next()) {
						customerTransactionDetails.setTransactionId(rs
							.getLong("Trans_id"));
						System.out
								.println("Max transactionid :"
										+ customerTransactionDetails
												.getTransactionId());
						customerTransactionDetails.setTransactionDate(rs
								.getString("trans_date"));
						customerTransactionDetails.setInvoiceNumber(rs
								.getString("invoice_no"));
						customerTransactionDetails.setTransactionStatus(rs
								.getString("trans_status"));
					}
				}

				conn.commit();
				apiResponse.setStatus(true);
				apiResponse.setDataModel(customerTransactionDetails);
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

	public APIResponse<List<CustomerTransactionDetails>> getCustomerTransactions(
			String fromDate, String toDate, long customerId,
			String transactionStatus) {
		logger.debug("Transaction status: "+transactionStatus);
		// Code to access db table
		// java.sql.Date fromSqlDate = new java.sql.Date(fromDate.getTime());
		// java.sql.Date toSqlDate = new java.sql.Date(toDate.getTime());
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<CustomerTransactionDetails>> apiResponse = new APIResponse<List<CustomerTransactionDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				if (transactionStatus.equalsIgnoreCase("all")) {
					sql = "select * from Customer_transaction"
							+ " where trans_date>='"
							+ fromDate.substring(0, 10) + "' and trans_date<='"
							+ toDate.substring(0, 10) + "' and cust_id="
							+ customerId + "";
				} else {
					sql = "select * from Customer_transaction"
							+ " where trans_date>='"
							+ fromDate.substring(0, 10) + "' and trans_date<='"
							+ toDate.substring(0, 10) + "' and trans_status='"
							+ transactionStatus + "' and cust_id=" + customerId
							+ "";
				}
				logger.debug("GetCustomer Transactions sql: "+sql);
				ResultSet rs = stmt.executeQuery(sql);
				List<CustomerTransactionDetails> customerTransactionDetailsresList = new ArrayList<CustomerTransactionDetails>();
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
						customerTransactionDetailsres
						.setSeperatedStockItemPrices(rs
								.getString("stock_item_prices"));
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
						String[] stockPrices = customerTransactionDetailsres
								.getSeperatedStockItemPrices().split(",");
						logger.debug("GetCustomer Transactions Stock items size "+storedStockItems.size()+" stockQuantities size "+stockQuantities.length+ " stockPrices size"+stockPrices.length);
						for (int i = 0; i < stockQuantities.length; i++) {
							storedStockItems.get(i).setQuantity(
									stockQuantities[i]);
							storedStockItems.get(i).setUnitPrice(
									Double.valueOf(stockPrices[i]));
						}
						customerTransactionDetailsres
								.setStockItems(storedStockItems);
						customerTransactionDetailsresList
								.add(customerTransactionDetailsres);

					}
					apiResponse.setDataModel(customerTransactionDetailsresList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ customerTransactionDetailsresList.size());

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
			logger.debug("GetCustomer Transactions Exception : "+e.getMessage());
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

	public APIResponse<CustomerTransactionDetails> updateCustomerTransaction(
			CustomerTransactionDetails customerTransactionDetails) {
		if (Double.valueOf(customerTransactionDetails.getDueAmount()) == 0.0) {
			customerTransactionDetails.setTransactionStatus("Paid");
		} else {
			customerTransactionDetails.setTransactionStatus("UnPaid");
		}
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<CustomerTransactionDetails> apiResponse = new APIResponse<CustomerTransactionDetails>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = " update  Customer_transaction set paid_amount='"
						+ customerTransactionDetails.getPaidAmount()
						+ "',due_amount='"
						+ customerTransactionDetails.getDueAmount()
						+ "',trans_status='"
						+ customerTransactionDetails.getTransactionStatus()
						+ "' where Trans_id='"
						+ customerTransactionDetails.getTransactionId() + "'";
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

	public String getInvoiceSequence() {
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		//APIResponse<CustomerTransactionDetails> apiResponse = new APIResponse<CustomerTransactionDetails>();
		//APIError error = new APIError();
		Connection conn = null;
		long invoiceId = 0;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				conn.setAutoCommit(false);
			}
			String sql1 = "UPDATE sequence SET id=LAST_INSERT_ID(id+1)";
			stmt.executeUpdate(sql1);
			conn.commit();
			String sql2 = "SELECT id from sequence";
			ResultSet rs2 = stmt.executeQuery(sql2);

			if (rs2 != null) {
				while (rs2.next()) {
					invoiceId = rs2.getLong("id");
				}

			}
		} catch (Exception e) {
			// TODO:
		}

		return String.valueOf(invoiceId);
	}

	public static void main(String[] args) {
		CustomerTransactionDetails customerTransactionDetails = new CustomerTransactionDetails();
		customerTransactionDetails.setCustomerId(1L);
		customerTransactionDetails.setDueAmount(0.0);
		customerTransactionDetails.setInvoiceNumber("Number1");
		customerTransactionDetails.setInvoiceUrl("Number1Url");
		customerTransactionDetails.setPaidAmount(100.0);
		customerTransactionDetails.setSeperatedStockItemIds("12");
		customerTransactionDetails.setSeperatedStockItemQuantities("1");
		customerTransactionDetails.setTotalAmount(100.0);
		// customerTransactionDetails.setTransactionDate(new Date());

		// customerTransactionDetails.setTransactionId("");
		customerTransactionDetails.setTransactionStatus("Paid");
		Gson gson = new Gson();
		System.out.println(gson.toJson(customerTransactionDetails));
		CustomerTransactionDaoImpl customerTrasanctionDaoImpl = new CustomerTransactionDaoImpl();
		customerTrasanctionDaoImpl.createTransaction(customerTransactionDetails);
	//	customerTrasanctionDaoImpl.getCustomerTransactions("2015-12-17","2016-03-17",1,"all");
		//fromDate=2015-12-17&toDate=2016-03-17&transactionStatus=all&customerId=1
		//System.out.println(customerTrasanctionDaoImpl.getInvoiceSequence());
	}

}

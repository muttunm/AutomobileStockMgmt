package com.automobile.sms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.model.APIError;
import com.automobile.sms.model.APIResponse;
import com.automobile.sms.model.DealerTransactionDetails;
import com.automobile.sms.model.StockItem;
import com.automobile.sms.util.JdbcConnectionUtil;
import com.automobile.sms.util.TransactionUtil;
import com.google.gson.Gson;

public class DealerTransactionDaoImpl {

	public APIResponse<DealerTransactionDetails> createTransaction(
			DealerTransactionDetails dealerTransactionDetails) {
		if (Double.valueOf(dealerTransactionDetails.getDueAmount()) == 0.0) {
			dealerTransactionDetails.setTransactionStatus("Paid");
		} else {
			dealerTransactionDetails.setTransactionStatus("UnPaid");
		}
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<DealerTransactionDetails> apiResponse = new APIResponse<DealerTransactionDetails>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				conn.setAutoCommit(false);
				// stmt = conn.createStatement();
				String insertTransactionsql = "insert into Dealer_transaction(dealer_id,trans_date, invoice_no,invoice_url,total_amount,"
						+ "paid_amount,due_amount,trans_status,stock_item_ids,stock_item_quantities,stock_item_prices,vat,discount)"
						+ " values('"
						+ Long.valueOf(dealerTransactionDetails.getDealerId())
						+ "','"
						+ new java.sql.Date(new java.util.Date().getTime())
						+ "','"
						+ dealerTransactionDetails.getInvoiceNumber()
						+ "','"
						+ dealerTransactionDetails.getInvoiceUrl()
						+ "'"
						+ ",'"
						+ Double.valueOf(dealerTransactionDetails
								.getGrandTotalAmount())
						+ "','"
						+ Double.valueOf(dealerTransactionDetails
								.getPaidAmount())
						+ "','"
						+ Double.valueOf(dealerTransactionDetails
								.getDueAmount())
						+ "'"
						+ ",'"
						+ dealerTransactionDetails.getTransactionStatus()
						+ "','"
						+ dealerTransactionDetails.getSeperatedStockItemIds()
						+ "'"
						+ ",'"
						+ dealerTransactionDetails
								.getSeperatedStockItemQuantities()
						+ "','"
						+ dealerTransactionDetails
								.getSeperatedStockItemPrices()
						+ "','"
						+ dealerTransactionDetails.getVat()
						+ "','"
						+ dealerTransactionDetails.getDiscount() + "')";
				stmt.addBatch(insertTransactionsql);
				TransactionUtil transactionUtil = new TransactionUtil();
				List<StockItem> storedStockItems = transactionUtil
						.getStockItemDetails(dealerTransactionDetails
								.getSeperatedStockItemIds());
				List<StockItem> updatedStockItems = transactionUtil
						.updateDealerStockItems(dealerTransactionDetails
								.getSeperatedStockItemIds(),
								dealerTransactionDetails
										.getSeperatedStockItemQuantities(),
								storedStockItems);
				for (StockItem stockItem : updatedStockItems) {
					stmt.addBatch(" update  stock_item" + " set quantity='"
							+ stockItem.getQuantity()
							+ "' where stock_item_id='"
							+ stockItem.getStockItemId() + "'");
				}

				stmt.executeBatch();
				String sql = "select Trans_id from Dealer_transaction where Trans_id=(select max(Trans_id) from Dealer_transaction)";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs != null) {
					while (rs.next()) {
						dealerTransactionDetails.setTransactionId(String
								.valueOf(rs.getLong("Trans_id")));
						System.out.println("Max transactionid :"
								+ dealerTransactionDetails.getTransactionId());
					}
				}

				conn.commit();
				apiResponse.setStatus(true);
				apiResponse.setDataModel(dealerTransactionDetails);
				stmt.close();
				conn.close();
			} else {
				apiResponse.setStatus(false);
				error.setErrorMessage("Databaase Connection Failed");
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

	public APIResponse<List<DealerTransactionDetails>> getDealerTransactions(
			String fromDate, String toDate, long dealerId,
			String transactionStatus) {
		// Code to access db table
		// java.sql.Date fromSqlDate = new java.sql.Date(fromDate.getTime());
		// java.sql.Date toSqlDate = new java.sql.Date(toDate.getTime());
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<List<DealerTransactionDetails>> apiResponse = new APIResponse<List<DealerTransactionDetails>>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				if (transactionStatus.equalsIgnoreCase("all")
						|| transactionStatus.equalsIgnoreCase("")) {
					if (dealerId != 0) {
						sql = "select * from Dealer_transaction"
								+ " where trans_date>='"
								+ fromDate.substring(0, 10)
								+ "' and trans_date<='"
								+ toDate.substring(0, 10) + "' and dealer_id="
								+ dealerId + "";
					} else {
						sql = "select * from Dealer_transaction"
								+ " where trans_date>='"
								+ fromDate.substring(0, 10)
								+ "' and trans_date<='"
								+ toDate.substring(0, 10) + "'";
					}
				} else {
					if (dealerId != 0) {
						sql = "select * from Dealer_transaction"
								+ " where trans_date>='"
								+ fromDate.substring(0, 10)
								+ "' and trans_date<='"
								+ toDate.substring(0, 10)
								+ "' and trans_status='" + transactionStatus
								+ "' and dealer_id=" + dealerId + "";
					} else {
						sql = "select * from Dealer_transaction"
								+ " where trans_date>='"
								+ fromDate.substring(0, 10)
								+ "' and trans_date<='"
								+ toDate.substring(0, 10)
								+ "' and trans_status='" + transactionStatus
								+ "'";
					}
				}

				ResultSet rs = stmt.executeQuery(sql);
				List<DealerTransactionDetails> dealerTransactionDetailsresList = new ArrayList<DealerTransactionDetails>();
				TransactionUtil transactionUtil = new TransactionUtil();
				// STEP 5: Extract data from result set
				if (null != rs) {
					while (rs.next()) {
						DealerTransactionDetails dealerTransactionDetailsres = new DealerTransactionDetails();
						dealerTransactionDetailsres.setDealerId(String
								.valueOf(rs.getLong("dealer_id")));
						dealerTransactionDetailsres.setDueAmount(String
								.valueOf(rs.getDouble("due_amount")));
						dealerTransactionDetailsres.setInvoiceNumber(rs
								.getString("invoice_no"));
						dealerTransactionDetailsres.setInvoiceUrl(rs
								.getString("invoice_url"));
						dealerTransactionDetailsres.setPaidAmount(String
								.valueOf(rs.getDouble("paid_amount")));
						dealerTransactionDetailsres
								.setSeperatedStockItemQuantities(rs
										.getString("stock_item_quantities"));
						dealerTransactionDetailsres
								.setSeperatedStockItemPrices(rs
										.getString("stock_item_prices"));
						dealerTransactionDetailsres.setTotalAmount(String
								.valueOf(rs.getDouble("total_amount")));
						dealerTransactionDetailsres.setTransactionDate(String
								.valueOf(rs.getDate("trans_date")));
						dealerTransactionDetailsres.setTransactionId(String
								.valueOf(rs.getLong("trans_id")));
						
						dealerTransactionDetailsres.setTransactionStatus(rs
								.getString("trans_status"));
						dealerTransactionDetailsres.setDiscount(rs
								.getDouble("discount"));
						dealerTransactionDetailsres.setVat(rs
								.getDouble("vat"));
						List<StockItem> storedStockItems = transactionUtil
								.getStockItemDetails(rs
										.getString("stock_item_ids"));
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
						dealerTransactionDetailsresList
								.add(dealerTransactionDetailsres);

					}
					apiResponse.setDataModel(dealerTransactionDetailsresList);
					apiResponse.setStatus(true);
					System.out.println("list size "
							+ dealerTransactionDetailsresList.size());

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

	public APIResponse<DealerTransactionDetails> updateDealerTransaction(
			DealerTransactionDetails dealerTransactionDetails) {
		if (Double.valueOf(dealerTransactionDetails.getDueAmount()) == 0.0) {
			dealerTransactionDetails.setTransactionStatus("Paid");
		} else {
			dealerTransactionDetails.setTransactionStatus("UnPaid");
		}
		JdbcConnectionUtil jdbcConnectionUtil = new JdbcConnectionUtil();
		APIResponse<DealerTransactionDetails> apiResponse = new APIResponse<DealerTransactionDetails>();
		APIError error = new APIError();
		Connection conn = null;
		try {
			Statement stmt = null;
			conn = jdbcConnectionUtil.getJdbcConnection();
			if (null != conn) {
				stmt = conn.createStatement();
				String sql;
				sql = " update  Dealer_transaction set paid_amount='"
						+ dealerTransactionDetails.getPaidAmount() + "',"
						+ "due_amount='"
						+ dealerTransactionDetails.getDueAmount() + "',"
						+ " trans_status='"
						+ dealerTransactionDetails.getTransactionStatus()
						+ "' where trans_id="
						+ dealerTransactionDetails.getTransactionId() + "";
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
		DealerTransactionDetails dealerTransactionDetails = new DealerTransactionDetails();
		dealerTransactionDetails.setDealerId("1");
		dealerTransactionDetails.setDueAmount("0.0");
		dealerTransactionDetails.setInvoiceNumber("Number1");
		dealerTransactionDetails.setInvoiceUrl("Number1Url");
		dealerTransactionDetails.setPaidAmount("100.0");
		dealerTransactionDetails.setSeperatedStockItemIds("12");
		dealerTransactionDetails.setSeperatedStockItemQuantities("1");
		dealerTransactionDetails.setTotalAmount("100.0");
		// dealerTransactionDetails.setTransactionDate(new Date());

		// dealerTransactionDetails.setTransactionId("");
		dealerTransactionDetails.setTransactionStatus("Paid");
		Gson gson = new Gson();
		System.out.println(gson.toJson(dealerTransactionDetails));
		DealerTransactionDaoImpl dealerTrasanctionDaoImpl = new DealerTransactionDaoImpl();
		// dealerTrasanctionDaoImpl.createTransaction(dealerTransactionDetails);
		dealerTrasanctionDaoImpl.createTransaction(dealerTransactionDetails);
	}

}

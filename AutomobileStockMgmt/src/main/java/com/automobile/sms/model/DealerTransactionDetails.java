package com.automobile.sms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DealerTransactionDetails {
	
	private String transactionId;
	private String dealerId;
	private String transactionDate;
	private String invoiceNumber;
	private String invoiceUrl;
	private String totalAmount;
	private String paidAmount;
	private String dueAmount;
	private String transactionStatus;
	private String seperatedStockItemIds;
	private String seperatedStockItemQuantities;
	private String seperatedStockItemPrices;
	private List<StockItem> stockItems;
	private double grandTotalAmount;
	private double vat;
	private double discount;
	
	
	
	public String getSeperatedStockItemPrices() {
		return seperatedStockItemPrices;
	}

	public void setSeperatedStockItemPrices(String seperatedStockItemPrices) {
		this.seperatedStockItemPrices = seperatedStockItemPrices;
	}

	public double getGrandTotalAmount() {
		return grandTotalAmount;
	}

	public void setGrandTotalAmount(double grandTotalAmount) {
		this.grandTotalAmount = grandTotalAmount;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public List<StockItem> getStockItems() {
		return stockItems;
	}

	public void setStockItems(List<StockItem> stockItems) {
		this.stockItems = stockItems;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setInvoiceUrl(String invoiceUrl) {
		this.invoiceUrl = invoiceUrl;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setSeperatedStockItemIds(String seperatedStockItemIds) {
		this.seperatedStockItemIds = seperatedStockItemIds;
	}

	public void setSeperatedStockItemQuantities(String seperatedStockItemQuantities) {
		this.seperatedStockItemQuantities = seperatedStockItemQuantities;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getInvoiceUrl() {
		return invoiceUrl;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public String getDueAmount() {
		return dueAmount;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public String getSeperatedStockItemIds() {
		return seperatedStockItemIds;
	}

	public String getSeperatedStockItemQuantities() {
		return seperatedStockItemQuantities;
	}

}

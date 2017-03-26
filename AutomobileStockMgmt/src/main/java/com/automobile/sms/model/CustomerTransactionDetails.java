package com.automobile.sms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTransactionDetails {

	private long transactionId;
	private long customerId;
	private String transactionDate;
	private String invoiceNumber;
	private String invoiceUrl;
	private double totalAmount;
	private double paidAmount;
	private double dueAmount;
	private double grandTotalAmount;
	private String transactionStatus;
	private String seperatedStockItemIds;
	private String seperatedStockItemQuantities;
	private String seperatedStockItemPrices;
	private double vat;
	private double discount;
	private List<StockItem> stockItems;
	private CustomerDetails customerDetails;
	private Boolean isGuestUser;
	

	public String getSeperatedStockItemPrices() {
		return seperatedStockItemPrices;
	}

	public void setSeperatedStockItemPrices(String seperatedStockItemPrices) {
		this.seperatedStockItemPrices = seperatedStockItemPrices;
	}
	
	public Boolean getIsGuestUser() {
		return isGuestUser;
	}

	public void setIsGuestUser(Boolean isGuestUser) {
		this.isGuestUser = isGuestUser;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceUrl() {
		return invoiceUrl;
	}

	public void setInvoiceUrl(String invoiceUrl) {
		this.invoiceUrl = invoiceUrl;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public double getGrandTotalAmount() {
		return grandTotalAmount;
	}

	public void setGrandTotalAmount(double grandTotalAmount) {
		this.grandTotalAmount = grandTotalAmount;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getSeperatedStockItemIds() {
		return seperatedStockItemIds;
	}

	public void setSeperatedStockItemIds(String seperatedStockItemIds) {
		this.seperatedStockItemIds = seperatedStockItemIds;
	}

	public String getSeperatedStockItemQuantities() {
		return seperatedStockItemQuantities;
	}

	public void setSeperatedStockItemQuantities(
			String seperatedStockItemQuantities) {
		this.seperatedStockItemQuantities = seperatedStockItemQuantities;
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

}

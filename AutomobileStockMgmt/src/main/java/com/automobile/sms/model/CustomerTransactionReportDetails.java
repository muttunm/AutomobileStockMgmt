package com.automobile.sms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomerTransactionReportDetails {
	private CustomerDetails details;
	private List<CustomerTransactionDetails> transactions;
	public CustomerDetails getDetails() {
		return details;
	}
	public void setDetails(CustomerDetails details) {
		this.details = details;
	}
	public List<CustomerTransactionDetails> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<CustomerTransactionDetails> transactions) {
		this.transactions = transactions;
	}


}

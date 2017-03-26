package com.automobile.sms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DealerTransactionReportDetails {
	private DealerDetails details;
	private List<DealerTransactionDetails> transactions;

	public DealerDetails getDetails() {
		return details;
	}

	public void setDetails(DealerDetails details) {
		this.details = details;
	}

	public List<DealerTransactionDetails> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<DealerTransactionDetails> transactions) {
		this.transactions = transactions;
	}

}

package com.automobile.sms.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomerDetails {

	private long customerId;
	private String customerTitle;
	private String customerFirstName;
	private String customerLastName;
	private String customerAddress;
	private String customerPlace;
	private String customerVehicleNum;
	private String customerVehicleType;
	private String customerMobile;
	private String customerEmailId;
	private String customerImageUrl;
	private String customerRegisteredDate;
	private Boolean isGuestUser;
	

	public Boolean getIsGuestUser() {
		return isGuestUser;
	}

	public void setIsGuestUser(Boolean isGuestUser) {
		this.isGuestUser = isGuestUser;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerTitle() {
		return customerTitle;
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPlace() {
		return customerPlace;
	}

	public void setCustomerPlace(String customerPlace) {
		this.customerPlace = customerPlace;
	}

	public String getCustomerVehicleNum() {
		return customerVehicleNum;
	}

	public void setCustomerVehicleNum(String customerVehicleNum) {
		this.customerVehicleNum = customerVehicleNum;
	}

	public String getCustomerVehicleType() {
		return customerVehicleType;
	}

	public void setCustomerVehicleType(String customerVehicleType) {
		this.customerVehicleType = customerVehicleType;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public String getCustomerImageUrl() {
		return customerImageUrl;
	}

	public void setCustomerImageUrl(String customerImageUrl) {
		this.customerImageUrl = customerImageUrl;
	}

	public String getCustomerRegisteredDate() {
		return customerRegisteredDate;
	}

	public void setCustomerRegisteredDate(String customerRegisteredDate) {
		this.customerRegisteredDate = customerRegisteredDate;
	}

}

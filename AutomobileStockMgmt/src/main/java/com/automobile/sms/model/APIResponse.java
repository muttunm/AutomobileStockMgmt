package com.automobile.sms.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties
public class APIResponse<T> {
	private boolean status;
	private APIError error;
	private T dataModel;

	public boolean getStatus() {
		return status;
	}

	public T getDataModel() {
		return dataModel;
	}

	public void setDataModel(T dataModel) {
		this.dataModel = dataModel;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public APIError getError() {
		return error;
	}

	public void setError(APIError error) {
		this.error = error;
	}
}

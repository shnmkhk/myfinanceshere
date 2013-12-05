package org.rabbit.services.dwr.vo;

public class BaseResponseWrapper {

	private String errorMessage = "";
	
	private String statusMessage = "";

	private boolean errored = false;

	private int recordsChanged = 0;

	private String responseAsString = "";

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isErrored() {
		return errored;
	}

	public void setErrored(boolean errored) {
		this.errored = errored;
	}

	public int getRecordsChanged() {
		return recordsChanged;
	}

	public void setRecordsChanged(int recordsChanged) {
		this.recordsChanged = recordsChanged;
	}

	public String getResponseAsString() {
		return responseAsString;
	}

	public void setResponseAsString(String responseAsString) {
		this.responseAsString = responseAsString;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}

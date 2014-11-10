package org.rabbit.wrappers;

import java.util.Map;
import org.rabbit.model.Sheet;

public class EntryStatusWrapper extends BaseWrapper {
	private String				redirectURI;
	private Map<String, String>	callbackParamsMap;
	private Sheet				associatedSheet;
	private boolean				errored;

	public String getRedirectURI() {
		return this.redirectURI;
	}

	public void setRedirectURI(String paramString) {
		this.redirectURI = paramString;
	}

	public Map<String, String> getCallbackParamsMap() {
		return this.callbackParamsMap;
	}

	public void setCallbackParamsMap(Map<String, String> paramMap) {
		this.callbackParamsMap = paramMap;
	}

	public Sheet getAssociatedSheet() {
		return this.associatedSheet;
	}

	public void setAssociatedSheet(Sheet paramSheet) {
		this.associatedSheet = paramSheet;
	}

	public boolean isErrored() {
		return this.errored;
	}

	public void setErrored(boolean paramBoolean) {
		this.errored = paramBoolean;
	}
}

/*
 * Location:
 * /home/shnmkhktr/expense-yes-managed/WEB-INF/classes/expense-yes-managed.jar
 * Qualified Name: org.rabbit.wrappers.EntryStatusWrapper Java Class Version: 6
 * (50.0) JD-Core Version: 0.5.3
 */
package org.rabbit.services.dwr.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SheetResponseWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5963862850951458372L;

	private SheetVO uniqueSheet;
	
	private Map<String, List<SheetVO>> sheetListMap;
	
	private List<SheetVO> sheetList;
	
	private String errorMessage = "";
	
	private boolean errored = false;
	
	private int recordsChanged = 0;
	
	private String responseAsString = "";
	
	
	public Map<String, List<SheetVO>> getSheetListMap() {
		return sheetListMap;
	}

	public void setSheetListMap(Map<String, List<SheetVO>> sheetListMap) {
		this.sheetListMap = sheetListMap;
	}

	public String getResponseAsString() {
		return responseAsString;
	}

	public void setResponseAsString(String responseAsString) {
		this.responseAsString = responseAsString;
	}

	public int getRecordsChanged() {
		return recordsChanged;
	}

	public void setRecordsChanged(int recordsChanged) {
		this.recordsChanged = recordsChanged;
	}

	public SheetVO getUniqueSheet() {
		return uniqueSheet;
	}

	public void setUniqueSheet(SheetVO uniqueSheet) {
		this.uniqueSheet = uniqueSheet;
	}

	public List<SheetVO> getSheetList() {
		return sheetList;
	}

	public void setSheetList(List<SheetVO> sheetList) {
		this.sheetList = sheetList;
	}

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
}

package org.rabbit.services.dwr.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SheetResponseWrapper extends BaseResponseWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 5963862850951458372L;

	private SheetVO						uniqueSheet;

	private Map<String, List<SheetVO>>	sheetListMap;

	private List<SheetVO>				sheetList;

	public Map<String, List<SheetVO>> getSheetListMap() {
		return sheetListMap;
	}

	public void setSheetListMap(Map<String, List<SheetVO>> sheetListMap) {
		this.sheetListMap = sheetListMap;
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
}

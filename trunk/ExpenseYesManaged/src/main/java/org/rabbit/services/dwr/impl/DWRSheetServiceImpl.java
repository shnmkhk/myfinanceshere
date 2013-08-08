/**
 * 
 */
package org.rabbit.services.dwr.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.SheetService;
import org.rabbit.services.dwr.DWRSheetService;
import org.rabbit.services.dwr.vo.SheetVO;
import org.rabbit.services.impl.SheetServiceImpl;

/**
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: Aug 3, 2013
 */
public class DWRSheetServiceImpl implements DWRSheetService {
	
	private SheetService sheetService = SheetServiceImpl.getInstance();
	
	public SheetVO createNew(String userId, int month, int year) throws SheetAlreadyExistsException {
		Sheet sheet = sheetService.addNewSheet(userId, month, year);
		return (SheetVO) sheet.getVO();
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.SheetService#getAllSheets(java.lang.String)
	 */
	public List<SheetVO> fetchAll(String userId) {
		List<Sheet> allSheets = sheetService.getAllSheets(userId);
		List<SheetVO> sheetVOsList = getVOsListFromEntities(allSheets);
		
		return sheetVOsList;
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.SheetService#getAllSheetsMap(java.lang.String)
	 */
	public Map<String, List<SheetVO>> getAllSheetsMap(String userId) {
		Map<Integer, List<Sheet>> allSheetsMap = sheetService.getAllSheetsMap(userId);
		Map<String, List<SheetVO>> allSheetsForView = new LinkedHashMap<String, List<SheetVO>>();
		for (Map.Entry<Integer, List<Sheet>> entry: allSheetsMap.entrySet()) {
			allSheetsForView.put(String.valueOf(entry.getKey()), getVOsListFromEntities(entry.getValue()));
		}
		System.out.println("allSheetsForView: " + allSheetsForView);
		return allSheetsForView;
	}
	
	public String getAllSheetMapHTML() {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getRemoteUser();
		System.out.println("UserId: " + userId);
		Map<Integer, List<Sheet>> allSheetsMap = sheetService.getAllSheetsMap(userId);
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<Integer, List<Sheet>> entry: allSheetsMap.entrySet()) {
			sb.append("<li class=\"header-two\">").append(entry.getKey()).append("</li>");
			sb.append("<li>");
			sb.append("<ul class=\"calendar-icon-ul\">");
			List<Sheet> entryList = entry.getValue();
			for (Sheet sheet: entryList) {
				sb.append("<li class=\"calendar-icon-li\">");
				sb.append("<a class=\"no-underline\" href=\"/ea/").append(sheet.getKeyStr()).append("/#content\">");
				sb.append("<div class=\"sheet-container cursor-pointer\">");
				sb.append("<div class=\"sheet-container-month\">").append(sheet.getShortMonthStr()).append("</div>");
				sb.append("<div class=\"sheet-container-year\">").append(sheet.getYear()).append("</div>");
				sb.append("</div>");
				sb.append("</a>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</li>");
			sb.append("<hr/>");
		}
		/*
		<li class="header-two"><c:out value='${sheetEntry.key}' /></li>
		<li>
			<ul class="calendar-icon-ul">
				<c:forEach var="sheetObj" items="${sheetEntry.value}">
					<li class="calendar-icon-li">
						<a class="no-underline" href='/ea/<c:out value="${sheetObj.keyStr }"/>#content'>
							<div class="sheet-container cursor-pointer">
								<div class="sheet-container-month">
									<c:out value="${sheetObj.shortMonthStr}" />
								</div>
								<div class="sheet-container-year">
									<c:out value="${sheetObj.year}" />
								</div>
							</div>
						</a>
					</li>
				</c:forEach>
			</ul>
		</li>
		<hr />
		*/
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.SheetService#getSheet(java.lang.String, int, int)
	 */
	public SheetVO getSheet(String userId, int month, int year)
			throws SheetNotFoundException {
		return (SheetVO) sheetService.getSheet(userId, month, year).getVO();
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.SheetService#getSheet(java.lang.String, java.lang.String)
	 */
	public SheetVO getSheet(String userId, String sheetKeyStr)
			throws SheetNotFoundException {
		return (SheetVO) sheetService.getSheet(userId, sheetKeyStr).getVO();
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.SheetService#deleteSheet(java.lang.String, int, int)
	 */
	public void deleteSheet(String userId, int month, int year)
			throws SheetNotFoundException {
		sheetService.deleteSheet(userId, month, year);
	}
	
	/**
	 * @param allSheets
	 * @return
	 */
	protected List<SheetVO> getVOsListFromEntities(List<Sheet> allSheets) {
		List<SheetVO> sheetVOsList = new ArrayList<SheetVO>(allSheets.size());
		for (Sheet sheet: allSheets) {
			sheetVOsList.add((SheetVO) sheet.getVO());
		}
		System.out.println("sheetVOsList: " + sheetVOsList);
		return sheetVOsList;
	}
}
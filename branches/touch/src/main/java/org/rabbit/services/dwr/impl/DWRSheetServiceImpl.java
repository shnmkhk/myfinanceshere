/**
 * 
 */
package org.rabbit.services.dwr.impl;

import java.security.Principal;
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
import org.rabbit.services.dwr.vo.SheetResponseWrapper;
import org.rabbit.services.dwr.vo.SheetVO;
import org.rabbit.services.impl.SheetServiceImpl;


/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: Aug 3, 2013
 */
public class DWRSheetServiceImpl implements DWRSheetService {

	public static final int INTERFACE_PLAIN = 0;
	public static final int INTERFACE_AJAX = 1;
	
	private SheetService sheetService = SheetServiceImpl.getInstance();

	public SheetResponseWrapper createNew(int month, int year)
			throws SheetAlreadyExistsException {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();
		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		try {
			Sheet sheet = sheetService.addNewSheet(userId, month, year);
			sheetResponseWrapper.setUniqueSheet((SheetVO) sheet.getVO());
		} catch (SheetAlreadyExistsException sheetAlreadyExistsException) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(sheetAlreadyExistsException
					.getMessage());
		} catch (IllegalArgumentException argumentException) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(argumentException
					.getMessage());
		}
		return sheetResponseWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.SheetService#getAllSheets(java.lang.String)
	 */
	public SheetResponseWrapper fetchAll() {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();
		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		try {
			List<Sheet> allSheets = sheetService.getAllSheets(userId);
			List<SheetVO> sheetVOsList = getVOsListFromEntities(allSheets);

			sheetResponseWrapper.setSheetList(sheetVOsList);
		} catch (Exception e) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(e.getMessage());
		}
		return sheetResponseWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.SheetService#getAllSheetsMap(java.lang.String)
	 */
	public SheetResponseWrapper getAllSheetsMap() {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();
		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		try {
			Map<Integer, List<Sheet>> allSheetsMap = sheetService
					.getAllSheetsMap(userId);
			Map<String, List<SheetVO>> allSheetsForView = new LinkedHashMap<String, List<SheetVO>>();
			for (Map.Entry<Integer, List<Sheet>> entry : allSheetsMap
					.entrySet()) {
				allSheetsForView.put(String.valueOf(entry.getKey()),
						getVOsListFromEntities(entry.getValue()));
			}
			sheetResponseWrapper.setSheetListMap(allSheetsForView);
		} catch (Exception e) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(e.getMessage());
		}
		return sheetResponseWrapper;
	}

	public SheetResponseWrapper getAllSheetMapHTML(int interfaceId) {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		Principal userPrincipal = request.getUserPrincipal();
		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		if (userPrincipal == null) { 
			return sheetResponseWrapper;
		}
		String userId = userPrincipal.getName();
		try {
			Map<Integer, List<Sheet>> allSheetsMap = sheetService
					.getAllSheetsMap(userId);
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<Integer, List<Sheet>> entry : allSheetsMap
					.entrySet()) {
				sb.append("<li class=\"header-two\">").append(entry.getKey())
						.append("</li>");
				sb.append("<li>");
				sb.append("<ul class=\"calendar-icon-ul\">");
				List<Sheet> entryList = entry.getValue();
				for (Sheet sheet : entryList) {
					sb.append("<li class=\"calendar-icon-li\">");
					if (INTERFACE_AJAX == interfaceId) {
						sb.append("<a class=\"no-underline\" href=\"javascript:void(0);\" onclick=\"showListEntriesPage('/ajax/ea/").append(sheet.getKeyStr()).append("/#content')\">");
					} else {
						sb.append("<a class=\"no-underline\" href=\"/ea/").append(sheet.getKeyStr()).append("/#content\">");
					} 
					sb.append("<div class=\"sheet-container cursor-pointer\">");
					sb.append("<div class=\"sheet-container-month\">")
							.append(sheet.getShortMonthStr()).append("</div>");
					sb.append("<div class=\"sheet-container-year\">")
							.append(sheet.getYear()).append("</div>");
					sb.append("</div>");
					sb.append("</a>");
					sb.append("</li>");
				}
				sb.append("</ul>");
				sb.append("</li>");
				sb.append("<hr/>");
			}

			sheetResponseWrapper.setResponseAsString(sb.toString());
		} catch (Exception e) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(e.getMessage());
		}
		return sheetResponseWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.SheetService#getSheet(java.lang.String, int,
	 * int)
	 */
	public SheetResponseWrapper getSheet(int month, int year)
			throws SheetNotFoundException {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();

		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		try {
			sheetResponseWrapper.setUniqueSheet((SheetVO) sheetService
					.getSheet(userId, month, year).getVO());
		} catch (SheetNotFoundException e) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(e.getMessage());
		}

		return sheetResponseWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.SheetService#getSheet(java.lang.String,
	 * java.lang.String)
	 */
	public SheetResponseWrapper getSheet(String sheetKeyStr)
			throws SheetNotFoundException {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();

		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		try {
			sheetResponseWrapper.setUniqueSheet((SheetVO) sheetService
					.getSheet(userId, sheetKeyStr).getVO());
		} catch (SheetNotFoundException e) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(e.getMessage());
		}
		return sheetResponseWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.SheetService#deleteSheet(java.lang.String, int,
	 * int)
	 */
	public SheetResponseWrapper deleteSheet(int month, int year)
			throws SheetNotFoundException {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();

		SheetResponseWrapper sheetResponseWrapper = new SheetResponseWrapper();
		try {
			sheetService.deleteSheet(userId, month, year);
			sheetResponseWrapper.setRecordsChanged(1);
		} catch (SheetNotFoundException e) {
			sheetResponseWrapper.setErrored(true);
			sheetResponseWrapper.setErrorMessage(e.getMessage());
		}
		return sheetResponseWrapper;
	}

	/**
	 * @param allSheets
	 * @return
	 */
	protected List<SheetVO> getVOsListFromEntities(List<Sheet> allSheets) {
		List<SheetVO> sheetVOsList = new ArrayList<SheetVO>(allSheets.size());
		for (Sheet sheet : allSheets) {
			sheetVOsList.add((SheetVO) sheet.getVO());
		}
		return sheetVOsList;
	}
}
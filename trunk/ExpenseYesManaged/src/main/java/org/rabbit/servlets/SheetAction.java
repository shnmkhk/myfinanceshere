package org.rabbit.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.model.Sheet;
import org.rabbit.services.SheetService;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.RequestUtil;

public class SheetAction extends HttpServlet {
	
	private static final long serialVersionUID = -8801600974223631863L;
	
	private static SheetService sheetService = SheetServiceImpl.getInstance();

	private void unloadMessages(HttpServletRequest request){
		request.getSession().removeAttribute("INFO_MESSAGE");
		request.getSession().removeAttribute("ERROR_MESSAGE");
		request.getSession().removeAttribute("INPUT_MONTH");
		request.getSession().removeAttribute("INPUT_YEAR");
	}
	private void refreshAllSheetsIntoServletContext(HttpServletRequest request) {
		List<Sheet> allSheets = (List<Sheet>) sheetService.getAllSheets();
		request.getSession().setAttribute("allSheets", allSheets);			
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException, ServletException {
		unloadMessages(request);
		refreshAllSheetsIntoServletContext(request);
		
		String baseHref = (String) request.getSession().getServletContext().getAttribute("baseHref");
		if (ObjectUtils.isNullOrEmpty(baseHref)) {
			baseHref = RequestUtil.EMPTY_STR;
		}
		resp.sendRedirect(baseHref + "/list/ls.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		unloadMessages(request);
		String baseHref = (String) request.getSession().getServletContext().getAttribute("baseHref");
		if (ObjectUtils.isNullOrEmpty(baseHref)) {
			baseHref = RequestUtil.EMPTY_STR;
		}
		
		String submit = RequestUtil.getStringValue(request, "submit");
		if (RequestUtil.EMPTY_STR.equals(submit) || RequestUtil.CANCEL_STR.equalsIgnoreCase(submit)) {
			response.sendRedirect(baseHref + "/sa");
			return;
		}

		int month = RequestUtil.getIntValue(request, "month", NumUtil.MINUS_ONE);
		int year = RequestUtil.getIntValue(request, "year", NumUtil.MINUS_ONE);
		
		try {
			sheetService.addNewSheet(month, year);
			request.getSession().setAttribute("INFO_MESSAGE", String.format("Added a new sheet for month - <b>%d</b> and year - <b>%d</b>", month, year));
			request.getSession().setAttribute("SHEET_KEY_ID", ObjectUtils.getSheetKeyId(month, year));
			
			refreshAllSheetsIntoServletContext(request);
			response.sendRedirect(baseHref + "/list/ls.jsp");
			return;
		} catch (SheetAlreadyExistsException e) {
			System.err.println(e.getMessage());
			request.getSession().setAttribute("ERROR_MESSAGE", String.format("Sheet already exists with month - <b>%d</b> and year - <b>%d</b>", month, year));
			
		} catch (IllegalArgumentException ie) {
			System.err.println(ie.getMessage());
			request.getSession().setAttribute("ERROR_MESSAGE", ie.getMessage());
		}
		
		if (NumUtil.MINUS_ONE != month){
			request.getSession().setAttribute("INPUT_MONTH", month);
		} 
		if (NumUtil.MINUS_ONE != year) {
			request.getSession().setAttribute("INPUT_YEAR", year);
		}
		
		response.sendRedirect(baseHref + "/as.jsp");
	}
}
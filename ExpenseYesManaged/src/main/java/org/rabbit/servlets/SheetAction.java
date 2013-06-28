package org.rabbit.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.RequestUtil;

public class SheetAction extends BaseServlet {
	
	private static final long serialVersionUID = -8801600974223631863L;
	
	protected void unloadMessages(HttpServletRequest request) {
		super.unloadMessages(request);
		request.getSession().removeAttribute("INPUT_MONTH");
		request.getSession().removeAttribute("INPUT_YEAR");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestUtil.refreshAllSheetsIntoSession(request);
		String baseHref = handleCancelAndReturnBaseHref(request, response);
		response.sendRedirect(baseHref + "/list/ls.jsp#content");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String baseHref = handleCancelAndReturnBaseHref(request, response);
		String submit = RequestUtil.getStringValue(request, "submit");
		if (RequestUtil.EMPTY_STR.equals(submit) || RequestUtil.CANCEL_STR.equalsIgnoreCase(submit)) {
			response.sendRedirect(baseHref + "/sa#content");
			return;
		}

		int month = RequestUtil.getIntValue(request, "month", NumUtil.MINUS_ONE);
		int year = RequestUtil.getIntValue(request, "year", NumUtil.MINUS_ONE);
		
		try {
			sheetService.addNewSheet(month, year);
			request.getSession().setAttribute("INFO_MESSAGE", String.format("Added a new sheet for month - <b>%d</b> and year - <b>%d</b>", month, year));
			request.getSession().setAttribute("SHEET_KEY_ID", ObjectUtils.getSheetKeyId(month, year));
			
			RequestUtil.refreshAllSheetsIntoSession(request);
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
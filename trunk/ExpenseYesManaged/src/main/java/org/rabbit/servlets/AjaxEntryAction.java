package org.rabbit.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.common.Constants;
import org.rabbit.common.EntryCategory;
import org.rabbit.common.Month;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.RequestUtil;
import org.rabbit.wrappers.EntryStatusWrapper;

import com.google.appengine.api.datastore.KeyFactory;

public class AjaxEntryAction extends BaseServlet {

	private static final long serialVersionUID = -9092072935123090022L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (!doAuthCheck(request, response)) {
			return;
		}
		String baseHref = handleCancelAndReturnBaseHref(request, response);
		String[] uriFragments = request.getRequestURI().substring(1).split("/");
		System.out.println("uriFragments.length: " + uriFragments.length);
		if (uriFragments.length == 2) {
			retrieveEntriesBasedOnSheetKeyInSession(request, response, baseHref);
		} else if (uriFragments.length == 3) {
			retrieveEntriesBasedOnGivenSheetKey(request, response, baseHref,
					uriFragments);
		} else if (uriFragments.length > 3) {
			deleteSpecificEntryBasedOnGivenSheetKeyAndEntrySeqNumber(request,
					response, baseHref, uriFragments);
		} else {
			request.getSession().setAttribute("ERROR_MESSAGE", String.format("Invalid number of arguments in the RequestedURI %s",request.getRequestURI()));
			response.sendRedirect(baseHref + "/error.jsp#content");
			return;
		}
	}

	private void deleteSpecificEntryBasedOnGivenSheetKeyAndEntrySeqNumber(
			HttpServletRequest request, HttpServletResponse response,
			String baseHref, String[] uriFragments) throws IOException {
		// Delete specific entry
		String monthYrFragment = ObjectUtils.getStrValue(uriFragments[2]);
		String fullSheetKey = request.getUserPrincipal().getName() + "_"
				+ monthYrFragment;
		int[] monthYrFragmentsIntArr = ObjectUtils
				.getMonthYr(uriFragments[2]);
		int entrySequence = ObjectUtils.getIntValue(uriFragments[3],
				NumUtil.MINUS_ONE);
		if (entrySequence != NumUtil.MINUS_ONE) {
			try {
				boolean recordDeleted = entryService.deleteEntry(KeyFactory
						.createKey(Sheet.class.getSimpleName(),
								fullSheetKey), entrySequence);
				if (recordDeleted) {
					RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(
							request, sheetService.getSheet(request
									.getUserPrincipal().getName(),
									monthYrFragment));
					request.getSession()
							.setAttribute(
									"INFO_MESSAGE",
									String.format(
											"Entry deleted successfully with sheetKey [%s %d] and sequence %d",
											Month.shortMonthArr[monthYrFragmentsIntArr[0] - 1],
											monthYrFragmentsIntArr[1],
											entrySequence));
				} else {
					request.getSession()
							.setAttribute(
									"ERROR_MESSAGE",
									String.format(
											"Entry deletion failed with sheetKey [%s %d] and sequence %d",
											Month.shortMonthArr[monthYrFragmentsIntArr[0] - 1],
											monthYrFragmentsIntArr[1],
											entrySequence));
				}
				response.sendRedirect(baseHref + "/ajax/list/le.jsp#content");
			} catch (EntryNotFoundException e) {
				request.getSession()
						.setAttribute(
								"ERROR_MESSAGE",
								String.format(
										"Entry not found with sheet key [%s %d] and sequence %d",
										Month.shortMonthArr[monthYrFragmentsIntArr[0] - 1],
										monthYrFragmentsIntArr[1],
										entrySequence));
				response.sendRedirect(baseHref + "/error.jsp#content");
				e.printStackTrace();
			} catch (SheetNotFoundException e) {
				request.getSession()
						.setAttribute(
								"ERROR_MESSAGE",
								String.format(
										"Sheet not found with sheet key [%s %d]",
										Month.shortMonthArr[monthYrFragmentsIntArr[0] - 1],
										monthYrFragmentsIntArr[1]));
				response.sendRedirect(baseHref + "/error.jsp#content");
				e.printStackTrace();
			}
		}
	}

	private void retrieveEntriesBasedOnGivenSheetKey(
			HttpServletRequest request, HttpServletResponse response,
			String baseHref, String[] uriFragments) throws IOException {
		List<Entry> listOfEntries;
		// Listing entries
		// It is only sheet key.
		String monthYrKeyFragment = uriFragments[2];
		String fullSheetKeyId = request.getUserPrincipal().getName() + "_"
				+ uriFragments[2];
		int[] monthYr = ObjectUtils.getMonthYr(uriFragments[2]);
		try {
			listOfEntries = entryService.getEntries(KeyFactory.createKey(
					Sheet.class.getSimpleName(), fullSheetKeyId));
		} catch (SheetNotFoundException e) {
			System.err.println(e.getMessage());
			request.getSession()
					.setAttribute(
							"ERROR_MESSAGE",
							String.format(
									"Sheet doesn't exist with month - <b>%d</b> and year - <b>%d</b>",
									monthYr[0], monthYr[1]));
			RequestUtil.refreshAllSheetsIntoSession(request);
			response.sendRedirect(baseHref + "/list/ls.jsp#content");
			return;
		}

		Sheet sheet;
		try {
			sheet = sheetService.getSheet(request.getUserPrincipal()
					.getName(), monthYrKeyFragment);
			RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(request,
					sheet);
			request.getSession().setAttribute(Constants.SHEET_KEY_ID,
					monthYrKeyFragment);
			request.getSession().setAttribute(
					Constants.SHEET_MONTH_YR_ARRAY,
					new String[] { sheet.getShortMonthStr(),
							String.valueOf(sheet.getYear()) });
		} catch (SheetNotFoundException e) {
			e.printStackTrace();
		}
		response.sendRedirect(baseHref + "/ajax/list/le.jsp#content");
	}

	private void retrieveEntriesBasedOnSheetKeyInSession(
			HttpServletRequest request, HttpServletResponse response,
			String baseHref) throws IOException {
		String sheetKeyId = (String) request.getSession().getAttribute(
				Constants.SHEET_KEY_ID);
		if (ObjectUtils.isNotNullAndNotEmpty(sheetKeyId)) {
			try {
				Sheet sheet = sheetService.getSheet(request
						.getUserPrincipal().getName(), sheetKeyId);
				RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(
						request, sheet);
			} catch (SheetNotFoundException e) {
				e.printStackTrace();
			}
			response.sendRedirect(baseHref + "/ajax/list/le.jsp#content");
		} else {
			// No sheet key id found in the session, so redirecting it to
			// list sheets
			request.getSession().setAttribute("ERROR_MESSAGE",
					String.format("Please select a sheet"));
			response.sendRedirect(baseHref + "/sa/#content");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (!(doAuthCheck(request, response)))
			return;
		String str1 = handleCancelAndReturnBaseHref(request,
				response);
		String str2 = RequestUtil.getStringValue(request,
				"submit");
		if (("".equals(str2)) || ("Cancel".equalsIgnoreCase(str2))) {
			response
					.sendRedirect(str1 + "/ajax/list/le.jsp#content");
			return;
		}
		HashMap localHashMap = new HashMap();
		Enumeration localEnumeration = request
				.getParameterNames();
		while (localEnumeration.hasMoreElements()) {
			String localObject = ObjectUtils.getStrValue(localEnumeration
					.nextElement());
			localHashMap.put(localObject,
					request.getParameter((String) localObject));
		}
		if (ObjectUtils.isNullOrEmpty(localHashMap.get("sid")))
			localHashMap.put("sid", request.getSession()
					.getAttribute("SHEET_KEY_ID"));
		EntryStatusWrapper localObject = this.entryService.addMultipleEntries(
				request.getUserPrincipal().getName(),
				localHashMap);
		try {
			RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(
					request,
					((EntryStatusWrapper) localObject).getAssociatedSheet());
			response.sendRedirect(String.format(
					"%s/ajax/list/le.jsp?sid=%s#content", new Object[] {
							str1,
							((EntryStatusWrapper) localObject)
									.getAssociatedSheet().getKeyStr() }));
			if (((EntryStatusWrapper) localObject).isErrored())
				request.getSession().setAttribute(
						"ERROR_MESSAGE",
						((EntryStatusWrapper) localObject).getErrorMessage());
			else
				request.getSession().setAttribute(
						"INFO_MESSAGE",
						((EntryStatusWrapper) localObject).getStatusMessage());
			response.sendRedirect(String
					.format("%s%s",
							new Object[] {
									str1,
									((EntryStatusWrapper) localObject)
											.getRedirectURI() }));
			return;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			System.err.println(localIllegalArgumentException.getMessage());
			request.getSession().setAttribute("ERROR_MESSAGE",
					localIllegalArgumentException.getMessage());
			response.sendRedirect(String.format(
					"%s/mae.jsp?sid=%s#content", new Object[] {
							str1,
							((EntryStatusWrapper) localObject)
									.getAssociatedSheet().getKeyStr() }));
			return;
		} catch (SheetNotFoundException localSheetNotFoundException) {
			localSheetNotFoundException.printStackTrace();
			request.getSession().setAttribute("ERROR_MESSAGE",
					localSheetNotFoundException.getMessage());
			response.sendRedirect(String.format(
					"%s/mae.jsp?sid=%s#content", new Object[] {
							str1,
							((EntryStatusWrapper) localObject)
									.getAssociatedSheet().getKeyStr() }));
		}
	}
}
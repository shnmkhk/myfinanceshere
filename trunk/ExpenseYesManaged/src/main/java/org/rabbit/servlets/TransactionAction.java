package org.rabbit.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.model.Transaction;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.RequestUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class TransactionAction extends BaseServlet {

	private static final long serialVersionUID = -2061169867104019807L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (!doAuthCheck(request, response)){
			return;
		}
		List<Transaction> results = (List<Transaction>) transactionService
				.getAllTransactions();
		request.setAttribute("transactionResults", results);

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("/list/lt.jsp");
		requestDispatcher.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		boolean skip = RequestUtil.getBoolValue(request, "skip", false);
		if (!skip) {
			int entryIx = RequestUtil.getIntValue(request, "entryIx",
					NumUtil.MINUS_ONE);
			long sheetId = RequestUtil.getLongValue(request, "sheetId",
					NumUtil.MINUS_ONE);

			if (NumUtil.MINUS_ONE == entryIx || NumUtil.MINUS_ONE == sheetId) {
				response.getWriter()
						.println(
								"<div style='color: red;'>Invalid arguments<br/>Expected sheet identifier along with entry sequence number</div>");
				return;
			}
			Key parentSheetKey = KeyFactory.createKey(
					Sheet.class.getSimpleName(), sheetId);
			try {
				Entry entry = entryService.getEntryBySheetAndIndex(
						parentSheetKey, entryIx);
				transactionService.addNewTransaction(RequestUtil
						.getStringValue(request, "description"), RequestUtil
						.getDoubleValue(request, "openingBalance", -1),
						RequestUtil.getDoubleValue(request,
								"transactionAmount", -1), entry);
			} catch (EntryNotFoundException e) {
				e.printStackTrace();
			} catch (SheetNotFoundException e) {
				e.printStackTrace();
			}
		}
		response.getWriter().println(
				"<a href='/myfinances'>Click to see the listing</a>");
	}
}

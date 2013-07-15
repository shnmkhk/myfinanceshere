/**
 * 
 */
package org.rabbit.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.services.EntryService;
import org.rabbit.services.SheetService;
import org.rabbit.services.TransactionService;
import org.rabbit.services.impl.EntryServiceImpl;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.services.impl.TransactionServiceImpl;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.RequestUtil;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 28-Jun-2013
 */
public class BaseServlet extends HttpServlet {
	
    protected TransactionService transactionService = TransactionServiceImpl.getInstance();
    protected EntryService entryService = EntryServiceImpl.getInstance();
    protected SheetService sheetService = SheetServiceImpl.getInstance();

	protected void unloadMessages(HttpServletRequest request){
		request.getSession().removeAttribute("INFO_MESSAGE");
		request.getSession().removeAttribute("ERROR_MESSAGE");
	}
	
	public boolean doAuthCheck(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
        String thisURL = request.getRequestURI();
        response.setContentType("text/html");
        if (request.getUserPrincipal() != null) {            
            return true;
        } else {
            response.sendRedirect("/");
            return false;
        }
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected String handleCancelAndReturnBaseHref(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		unloadMessages(request);
		String baseHref = (String) request.getSession().getServletContext().getAttribute("baseHref");
		if (ObjectUtils.isNullOrEmpty(baseHref)) {
			baseHref = RequestUtil.EMPTY_STR;
		}
		
		return baseHref;
	}
}

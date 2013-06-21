package org.rabbit.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.SheetService;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.RequestUtil;

public class SheetAction extends HttpServlet {

             private static final long serialVersionUID = -8801600974223631863L;

             private static SheetService sheetService = SheetServiceImpl.getInstance();

             public void doGet(HttpServletRequest req, HttpServletResponse resp)
                                             throws IOException, ServletException {

                             List<Sheet> allSheets = (List<Sheet>) sheetService.getAllSheets();
                             req.getSession().setAttribute("allSheets", allSheets);

                             resp.sendRedirect("/list/ls.jsp");
             }

             public void doPost(HttpServletRequest request, HttpServletResponse response)
                                             throws IOException {

                             String submit = RequestUtil.getStringValue(request, "submit");
                             if (RequestUtil.EMPTY_STR.equals(submit) || RequestUtil.CANCEL_STR.equalsIgnoreCase(submit)) {
                                             response.sendRedirect("/sa");
                                             return;
                             }
                             response.setContentType("text/html");
                             PrintWriter out = response.getWriter();

                             String sheetKeyId = (String) request.getSession().getAttribute("SHEET_KEY_ID");
                             Sheet sheet = null;

                             boolean sheetAlreadyExists = false;
                             if (ObjectUtils.isNullOrEmpty(sheetKeyId)){
                                             String[] monthYrArr = ObjectUtils.getMonthYr(sheetKeyId);
                                             if (ObjectUtils.isNotNullAndNotEmpty(monthYrArr) && monthYrArr.length == 2){
                                                             int month = ObjectUtils.getIntValue(monthYrArr[0], NumUtil.MINUS_ONE);
                                                             int year = ObjectUtils.getIntValue(monthYrArr[1], NumUtil.MINUS_ONE);

                                                             if (NumUtil.MINUS_ONE != month && NumUtil.MINUS_ONE != year){
                                                                             try {
                                                                                             sheet = sheetService.getSheet(month, year);
                                                                                             if (ObjectUtils.isNotNullAndNotEmpty(sheet)){
                                                                                                             sheetAlreadyExists = true;
                                                                                             }
                                                                             } catch (SheetNotFoundException e) {
                                                                                             // Ignore if sheet doesn't exist in the db.
                                                                                             sheetAlreadyExists = false;
                                                                             }
                                                             }
                                             }
                             }


                             if (!sheetAlreadyExists) {

                                             int month = RequestUtil.getIntValue(request, "month", NumUtil.MINUS_ONE);
                                             int year = RequestUtil.getIntValue(request, "year", NumUtil.MINUS_ONE);

                                             try {
                                                             sheet = sheetService.addNewSheet(month, year);
                                                             out.println(String.format("A new sheet is created with month - %d and year - %d</div>", month, year));
                                                             out.println("<br/>");
                                                             out.println("<a href='/sa'>Click here</a> to see all the available sheets");

                                                             request.getSession().setAttribute("SHEET_KEY_ID", ObjectUtils.getSheetKeyId(month, year));
                                             } catch (SheetAlreadyExistsException e) {
                                                             System.err.println(e.getMessage());
                                                             out.println(String.format("<div style='color: red;'>Sheet already exists with month - %d and year - %d</div>", month, year));
                                             }
                             }

                             out.println("<a href='/sa'>Sheet List</a>");
                             out.println("&nbsp;|&nbsp;");
                             out.println("<a href='/as.jsp'>Add a sheet</a>");
             }
}
<%@page import="java.util.List"%>
<%@page import="org.rabbit.model.Sheet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ include file="../common/header.jsp" %>
<title>Rabbit Computing :: Available sheets</title>
<body>
             <display:table name="${sessionScope.allSheets}" class="Mars" >
               <display:column property="month" title="Month" />
               <display:column property="year" title="Year" />
               <display:column property="createdOn" title="Creation date" />
             </display:table>

             <a href="/as.jsp">Add a(nother) sheet</a>&nbsp;|&nbsp;<a href="/sa">Reload</a>
</body>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<div style="text-align: right; float: right; width: 100%; padding: 5px 0px 10px;" id="ajxDspId">
	<display:table name="${sessionScope.entriesOfSelectedSheet}"
		class="Mars" requestURI="/ajax/display.le.jsp#content"
		id="entryRow" decorator="org.rabbit.decorators.EntryDecorator"
		style="text-align: right;" pagesize="${param.pageSize}" requestURIcontext="true">
		<display:column property="icons" title="#"
			class="align-right"
			style="width: 5%" />
		<display:column property="hyperlink" title="Detail"
			class="align-right"
			style="width: 75%"/>
		<display:column property="amount" title="Amount"
			class="align-right ${entryRow.styleClass}"
			format="{0,number,#,###.00}" style="width: 15%"/>
	</display:table>
	<c:if test="${not empty sessionScope.entriesTotal}">
		<hr width="50%" style="float: right; text-align: right; width: 50%;" />
		<div style="float: right; text-align: right; width: 100%; padding-bottom: 5px;">
			<div class="sum-of-entries-amounts">
				Total account balance for this month: <label> <fmt:formatNumber
						value="${sessionScope.entriesTotal}" type="currency"
						pattern="#,###.00" />
				</label>
			</div>
		</div>
	</c:if>
</div>
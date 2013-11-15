<! DOCTYPE HTML>
<html>
<title>Home</title>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.10.2.min.js'/>"></script>
<script type='text/javascript' src="<c:url value='/dwr/interface/SheetService.js'/>"></script>
<script type='text/javascript' src="<c:url value='/dwr/interface/EntryService.js'/>"></script>
<script type='text/javascript' src="<c:url value='/scripts/dwr.engine.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/sheet-util.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/displayTagAjax.js'/>"></script>
</head>
<body>
	<%@ include file="common/header.jsp" %>
	<script type="text/javascript">
		var listSheetsLoaded = false;
		var addASheetLoaded = false;
		var listEntriesLoaded = false;
		var addAnEntryLoaded = false;

		function hideAll(){
			$("#list_sheets").hide();
			$("#add_a_sheet").hide();
			$("#list_entries").hide();
			$("#add_entries").hide();
			$("#error_message").hide();
			$("#status_message").hide();
		}
		
		function showSheetListPage(loadAgain){
			if (listSheetsLoaded) {
				hideAll();
				$("#list_sheets").show();
			} else {
				$("body").addClass("loading");
				$("#list_sheets").load("/ajax/sa/?ref=Home#content", function() {
					hideAll();
					$("#list_sheets").show();
					listSheetsLoaded = true;
					$("body").removeClass("loading");
				});
			}
		}

		function showAddASheetPage(){
			if (addASheetLoaded) {
				hideAll();
				$("#add_a_sheet").show();
				clearAddASheetInputs();
				return;
			} else {
				$("body").addClass("loading");
				$("#add_a_sheet").load("/ajax/as.jsp#content", function() {
					hideAll();
					$("#add_a_sheet").show();
					addASheetLoaded = true;
					$("body").removeClass("loading");
					clearAddASheetInputs();
				});
			}
		}

		function showListEntriesPage(urlToLoad, successMsg){
			if (listEntriesLoaded && !urlToLoad) {
				hideAll();
				$("#list_entries").show();
				return;
			} else {
				loadedPrevSheetURI = urlToLoad;
				if (successMsg) {
					$("#info_message").html("<p><span><a href=\"javascript:void()\" onclick=\"$('#info_message').remove()\">X</a></span></p>" + successMsg);
				}
				$("body").addClass("loading");
				$("#list_entries").load(urlToLoad, function() {
					hideAll();
					$("#list_entries").show();
					listEntriesLoaded = true;
					$("body").removeClass("loading");
				});
			}
		}

		function resetAddEntriesFields() {
			$(".entry_field").val("");	
			$(".entry_field_checked").prop("checked", true);
		}
		
		function showAddEntriesPage(loadAgain){
			if (addAnEntryLoaded && !loadAgain) {
				hideAll();
				resetAddEntriesFields();
				$("#add_entries").show();
				return;
			} else {
				$("body").addClass("loading");
				$("#add_entries").load("/ajax/mae.jsp#content", function() {
					hideAll();
					resetAddEntriesFields();
					$("#add_entries").show();
					addAnEntryLoaded = true;
					$("body").removeClass("loading");
				});
			}
		}
		
		function processResponse(response) {
			var noIssues = eval(response.errored) == true ? false : true;
			if (noIssues) {
				$("#error_message_content").html("");
				$("#error_message").hide();
			} else {
				$("#error_message").show();
				$("#error_message_content").html(response.errorMessage);	
			} 
			return noIssues;
		}
		
		
		function addSheet() {
			var givenMonth = $("#month").val();
			var givenYear = $("#year").val();
			$("body").addClass("loading");
			SheetService.createNew(givenMonth, givenYear, function(response){
				listSheetsLoaded = false;
				$("body").removeClass("loading");
				var noIssues = processResponse(response);
				if (noIssues) {
					showSheetListPage();					
				}
			});
		}

		var ENTRY_PROP_AMOUNT = "amount";
		var ENTRY_PROP_SHORTCODE = "shortCode";
		var ENTRY_PROP_DESCR = "descr";
		var ENTRY_PROP_TYPE = "type";
		
		function addMultipleEntries() {
			var noOfEntries = eval($("#no-of-entries").val());
			var entriesArr = "{\"entries\":[";
			for (var i = 1; i <= noOfEntries; i++)
			{
				var type = $("[name=\"type_income_" + i + "\"]").val();
				var shortCode = $("#short_code_" + i).val();
				var amount = $("#amount_" + i).val();
								
				var entry = new Entry(type, shortCode, shortCode, amount);
				entriesArr += entry.toString();
				if (i != noOfEntries) {
					entriesArr += ",";	
				}
			}
			entriesArr += "]}";
			
			$("body").addClass("loading");
			EntryService.addMultipleEntries(entriesArr, function(response){
				$("body").removeClass("loading");
				var noIssues = processResponse(response);
				if (noIssues) {
					showListEntriesPage(loadedPrevSheetURI);					
				}
			});
		}
	</script>
	
	
	<div>
		<div id="list_sheets"></div>
		<div id="add_a_sheet"></div>
		<div id="list_entries"></div>
		<div id="add_entries"></div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			showSheetListPage();
		});
	</script>
</body>
</html>

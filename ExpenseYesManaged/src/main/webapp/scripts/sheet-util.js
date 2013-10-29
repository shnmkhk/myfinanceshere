function loadSheets(interfaceId, removeMsg) {
	if (removeMsg) {
		$(".message").remove();
	}

	$("body").addClass("loading");
	SheetService.getAllSheetMapHTML(interfaceId, {
		callback : function(response) {
			var sheetListContainer = $("#sheet-list-container");
			if (sheetListContainer[0]) {
				sheetListContainer.html(response.responseAsString);
				var currentLoc = location.href;
				if (currentLoc.indexOf("#content") == -1) {
					location.href = currentLoc + "#content";
				}
			}
			$("body").removeClass("loading");
		},
		timeout : 10000
	})
}

// Default interface is Plain
var currentInterfaceId = 0;

try {
	// a crossbrowser solution
	$(document).ready(function() {
		dwr.engine.setErrorHandler(function(message) {
			$("body").removeClass("loading");
		});
		loadSheets(currentInterfaceId);
	});
} catch (error) {
}

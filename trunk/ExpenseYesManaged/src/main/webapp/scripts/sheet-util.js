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
var loadedPrevSheetURI = ""; 

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


var ENTRY_PROP_AMOUNT = "amount";
var ENTRY_PROP_SHORTCODE = "shortCode";
var ENTRY_PROP_DESCR = "descr";
var ENTRY_PROP_TYPE = "type";

function Entry(type, descr, shortCode, amount) {
	  this.type = type;
	  this.descr = descr;
	  this.shortCode = shortCode;
	  this.amount = amount;
	  
}

Entry.prototype.toString = function()
{
  return "{" +
  		"\""+ENTRY_PROP_TYPE+"\":\""+this.type+"\"," +
  		"\""+ENTRY_PROP_SHORTCODE+"\":\""+this.shortCode+"\"," +
  		"\""+ENTRY_PROP_DESCR+"\":\""+this.descr+"\"," +
  		"\""+ENTRY_PROP_AMOUNT+"\":\""+this.amount+"\"" +
  				"}";
};

var ENTRY_PROP_SHEET_KEY_STR = "sheet_key_str";
var ENTRY_PROP_SEQUENCE_IX = "seq_ix";


function ToDeleteEntry(sheetKeyStr, sequenceIx) {
	  this.sheetKeyStr = sheetKeyStr;
	  this.sequenceIx = sequenceIx;
}

ToDeleteEntry.prototype.toString = function()
{
  return "{" +
  		"\""+ENTRY_PROP_SHEET_KEY_STR+"\":\""+this.sheetKeyStr+"\"," +
  		"\""+ENTRY_PROP_SEQUENCE_IX+"\":\""+this.sequenceIx+"\"}";
};

function showLoader(linkObj, msg) {
	msg = (msg == undefined || msg == null) ? "" : msg;
	linkObj.innerHTML = "Loading" + msg + "..";
}

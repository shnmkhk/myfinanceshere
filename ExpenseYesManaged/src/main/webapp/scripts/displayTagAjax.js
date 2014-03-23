/*
 * displayTagAjax.js
 *
 * Copyright (c) 2008 Vijay Karthik
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */

function changeLinks()
{
    done=true;
    var linkData, queryArr, action, qryStr;
    selector = 'span.pagelinks>a';
    changeUsingSelector(selector);
    selector = 'table#dispTable>thead>tr>th>a';
    changeUsingSelector(selector);
}

var current_page_uri = "/ajax/display.le.jsp";
function changeUsingSelector(selector)
{
    $(selector).each(function()
    {
        linkData = $(this).attr("href");
        queryArr = linkData.split("?");
        action = queryArr[0];
        qryStr = queryArr[1];
        if (action == undefined || action == "") {
        	action = current_page_uri;
        }
        newStr = "JavaScript:doAjax('"+action+"','"+qryStr+"');";
        $(this).attr("href",newStr);
        $(this).attr("onMouseOver","window.status='Pagination Links have been Ajaxified!!';return false;");
        $(this).attr("onMouseOut","window.status='';return false;");
        $(this).attr("data-ajax", "false");
        current_page_uri = action;
    });    

}

function doAjax(url, data)
{
	$(".message").remove();
	$("body").addClass("loading");
    //if you need additional params to be passed - add to the data variable
    $.ajax
    ({
        url: url,
        data:  data,
        async: false,
        success: function(resp){
            htmlStr=resp;
            $('#ajxDspId').html(htmlStr);
            changeLinks();
            $("body").removeClass("loading");
        }

    });
}
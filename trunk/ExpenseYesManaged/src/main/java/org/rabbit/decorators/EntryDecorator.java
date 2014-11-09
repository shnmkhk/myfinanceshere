/**
 * 
 */
package org.rabbit.decorators;

import org.displaytag.decorator.TableDecorator;
import org.rabbit.model.Entry;
import org.rabbit.shared.ObjectUtils;

import com.google.appengine.labs.repackaged.org.json.JSONException;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 28-Jun-2013
 */
public class EntryDecorator extends TableDecorator {

	private static StringBuffer sb = new StringBuffer();

	public String getHyperlink() throws JSONException {
		sb.setLength(0);
		Entry entry = (Entry) getCurrentRowObject();

		sb.append("<div class=\"entry-label-full\">");
		sb.append("<big>");
		sb.append(entry.getShortCode());
		sb.append("</big>&nbsp;<br/>");
		sb.append(entry.getTypeStr());
		sb.append("</div>");
		
		return sb.toString();
	}

	public String esc(String inputStr) {
		inputStr = inputStr.replaceAll("'", "\\\\'");
		inputStr = inputStr.replaceAll("\"", "\\\\\"");

		return inputStr;
	}
	
	public String getIcons() {
		sb.setLength(0);
		Entry entry = (Entry) getCurrentRowObject();
		String sheetKeyStr = entry.getSheet().getKeyStr();
		int sequenceNumber = entry.getSequenceIndex();

		sb.append("<a href=\"javascript:void(0);\" onclick=\"deleteEntry('");
		sb.append(esc(entry.getShortCode()));
		sb.append("', '");
		sb.append(entry.getAmount());
		sb.append("', '");
		sb.append(esc(entry.getCategory()));
		sb.append("', '");
		sb.append(esc(ObjectUtils.getSimpleDate(entry.getCreatedOn())));
		sb.append("', '");
		sb.append(sheetKeyStr);
		sb.append("', '");
		sb.append(sequenceNumber);
		sb.append("')\" data-ajax=\"false'\"><img src=\"/images/delete-icon.png\" width=\"16\" height=\"16\"/></a>&nbsp;<br/>");

		return sb.toString();
	}

}

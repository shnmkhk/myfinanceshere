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
		String sheetKeyStr = entry.getSheet().getKeyStr();
		int sequenceNumber = entry.getSequenceIndex();
		return "<div class=\"entry-label-full\"><big>"
				+ entry.getShortCode()
				+ "</big>&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteEntry('"+esc(entry.getShortCode())+"', '"+entry.getAmount()+"', '"+esc(entry.getCategory())+"', '"+esc(ObjectUtils.getSimpleDate(entry.getCreatedOn()))+"', '"
				+ sheetKeyStr + "', '" + sequenceNumber
				+ "')\" data-ajax=\"false'\">[Del]</a>&nbsp;<br/>"
				+ entry.getTypeStr() + "</div>";
	}

	public String esc(String inputStr) {
		inputStr = inputStr.replaceAll("'", "\\\\'");
		inputStr = inputStr.replaceAll("\"", "\\\\\"");
	    
		return inputStr;
	}
	
}

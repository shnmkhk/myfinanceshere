/**
 * 
 */
package org.rabbit.decorators;

import org.displaytag.decorator.TableDecorator;
import org.rabbit.model.Entry;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 28-Jun-2013
 */
public class EntryDecorator extends TableDecorator {

	public String getHyperlink() {
		Entry entry = (Entry) getCurrentRowObject();
		String sheetKeyStr = entry.getSheet().getKeyStr();
		int sequenceNumber = entry.getSequenceIndex();
		return entry.getShortCode() + "&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteEntry('"+sheetKeyStr+"', '"+sequenceNumber+"')\" data-ajax=\"false'\">[Del]</a>&nbsp;<br/>" + entry.getTypeStr();
	}
}

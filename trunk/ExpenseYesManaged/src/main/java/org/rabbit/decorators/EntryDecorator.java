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
		return "<a href='/ta/" + sheetKeyStr + "'/" + sequenceNumber + ">"
				+ entry.getSheet().getMonth() + "_"
				+ entry.getSheet().getYear() + "_" + sequenceNumber + "</a>";
	}
}

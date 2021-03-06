/**
 * 
 */
package org.rabbit.decorators;

import org.displaytag.decorator.TableDecorator;
import org.rabbit.model.Sheet;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 28-Jun-2013
 */
public class SheetDecorator extends TableDecorator {

	public String getHyperlink() {
		Sheet sheet = (Sheet) getCurrentRowObject();
		String keyStr = sheet.getKeyStr();

		return "<a href='/ea/" + keyStr + "'><li><div class='sheet-container'><div class='sheet-container-month'>" + sheet.getShortMonthStr() + "</div><div class='sheet-container-year'>" + sheet.getYear() + "</div></div></li>";
	}
}

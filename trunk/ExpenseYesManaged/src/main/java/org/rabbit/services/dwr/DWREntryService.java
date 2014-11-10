package org.rabbit.services.dwr;

import org.rabbit.services.dwr.vo.EntryResponseWrapper;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: Aug 3, 2013
 */
public interface DWREntryService {
	EntryResponseWrapper addMultipleEntries(String entriesInJSON);

	EntryResponseWrapper deleteSelectedEntries(String entriesInJSON);

	EntryResponseWrapper getPiChartDataForGivenSheet(String sheetIdJSON);
}

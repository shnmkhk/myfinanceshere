package org.rabbit.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static PersistenceManagerFactory pmfInstance = null;

	static {
		try {
		 pmfInstance = JDOHelper
					.getPersistenceManagerFactory("transactions-optional");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
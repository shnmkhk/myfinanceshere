package org.rabbit.common;

import javax.jdo.JDOEnhancer;
import javax.jdo.JDOHelper;

public class Enhancer {

	public static void main(String[] args) {
		JDOEnhancer enhancer = JDOHelper.getEnhancer();
		enhancer.setVerbose(true);
		enhancer.addPersistenceUnit("transactions-optional");
		enhancer.enhance();
	}
}

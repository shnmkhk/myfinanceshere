package org.rabbit.shared;

import javax.jdo.JDOEnhancer;
import javax.jdo.JDOHelper;

public class DataStoreJDOInitializer {
             public static final void initialize() {
                             JDOEnhancer enhancer = JDOHelper.getEnhancer();
                             enhancer.setVerbose(true);
                             enhancer.addPersistenceUnit("transactions-optional");
                             enhancer.enhance();
             }

             public static void main(String[] args) {
                             initialize();
             }
}
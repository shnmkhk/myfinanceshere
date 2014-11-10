package org.rabbit.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.rabbit.shared.DataStoreJDOInitializer;

public class InitDataStoreListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		DataStoreJDOInitializer.initialize();
	}

}

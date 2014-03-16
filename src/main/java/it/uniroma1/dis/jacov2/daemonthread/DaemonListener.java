package it.uniroma1.dis.jacov2.daemonthread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DaemonListener implements ServletContextListener {
	
	private DaemonThread daemonThread = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			daemonThread.interrupt();
		} catch (Exception ex) {}
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if ( (daemonThread == null) || (daemonThread.isAlive()) ) {
			daemonThread = new DaemonThread();
			daemonThread.start();
		}
	}
}

package it.uniroma1.dis.jacov2.dao;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum JobsQueue {
	instance;
	
	private BlockingQueue<String> jobsQueue = new LinkedBlockingQueue<String>();
	
	public synchronized BlockingQueue<String> getModel() {
		return jobsQueue;
	}
}

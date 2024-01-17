package com.finago.interview.task;

/**
 * A simple main method as an example.
 */
public class BatchProcessor {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting the Folder Watcher  Service ");
			
		MonitorInFolder monitorInFolder = new MonitorInFolder();
		monitorInFolder.processEvents();
		
		
	}
	
	
	

}

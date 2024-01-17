package com.finago.interview.task;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.finago.interview.fileProcessor.FileProcessor;
import com.finago.interview.fileProcessor.FileUtility;

/**
 * This class is responsible to watch the IN folder and as soon it encounter the
 * xml it will process the xml
 * 
 * @author Ajay Naik
 *
 */
public class MonitorInFolder  {

		static {
		File file = new File(Constants.INFolder);

		if (!file.exists())
			throw new RuntimeException("In-Folder doesn't exists");
	}

	/**
	 * Creates a WatchService and registers the given directory
	 * 
	 * @throws ParserConfigurationException
	 */

	/**
	 * Process all events for the key queued to the watcher.
	 * @throws Exception 
	 */
	public void processEvents() throws Exception {

		String monitorDirectory = Constants.INFolder;
		FileAlterationObserver observer = new FileAlterationObserver(monitorDirectory);

		System.out.println("Start Monitoring In Folder  " + monitorDirectory);
		observer.addListener(new FileAlterationListenerAdaptor() {
			@Override
			public void onDirectoryCreate(File file) {
				System.out.println("New Folder Created:" + file.getName());
			}

			@Override
			public void onDirectoryDelete(File file) {
				System.out.println("Folder Deleted:" + file.getName());
			}

			@Override
			public void onFileCreate(File file) {
				System.out.println("File Created:" + file.getName());
				if (FileUtility.checkXMlFile(file.getName(), "xml")) {
					try {
						FileProcessor.process(Constants.INFolder + file.getName());
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			@Override
			public void onFileChange(File file) {
				// TODO Auto-generated method stub
				System.out.println("File Change:" + file.getName());
			}

			@Override
			public void onFileDelete(File file) {
				System.out.println("File Deleted:" + file.getName());
			}
		});
		/* Set to monitor changes for 500 ms */
		FileAlterationMonitor monitor = new FileAlterationMonitor(500, observer);
		monitor.start();
	}

	

}

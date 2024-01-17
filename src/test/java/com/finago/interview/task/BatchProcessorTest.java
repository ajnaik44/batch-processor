package com.finago.interview.task;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;

import com.finago.interview.fileProcessor.FileProcessor;
import com.finago.interview.fileProcessor.FileUtility;

import junit.framework.TestCase;

/**
 * Unit test for BatchProcessor.
 */
public class BatchProcessorTest extends TestCase {

    /**
     * Something to feed your imagination.
     */
	@Test
	 public void testApp() {
        assertTrue(1 < 2);
    }
	@Test
	public void testFileProcessor() throws ParserConfigurationException
	{
		FileProcessor  fileProcessor = new FileProcessor();
		fileProcessor.process(Constants.INFolder+"90072701.xml");
		File file = new File(Constants.INFolder+"90072657.pdf");
		assertFalse(file.exists());
	}
	@Test
	public  void testFileExtension() {
		
		assertTrue(FileUtility.checkXMlFile("abc.xml", "xml"));
	}

}

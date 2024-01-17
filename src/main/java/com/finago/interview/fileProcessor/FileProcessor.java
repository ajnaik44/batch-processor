package com.finago.interview.fileProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.ParserConfigurationException;

import com.finago.interview.task.Constants;

/**
 * 
 * @author Ajay Naik File Processor Usage : This class will validate the xml
 *         with xsd , read xml if the validation is successful move all xml to
 *         archive location and delete all pdf
 */
public class FileProcessor {
	/**
	 * This class will validate the xml with xsd read xml if the validation is
	 * successful. if validation is successfull perform the logic as given if
	 * validation is not successful move the files to error location
	 * 
	 * 
	 * @throws ParserConfigurationException
	 */

	/**
	 * 
	 * Main method method to process the xml
	 * 
	 * @param xmlString
	 * @throws ParserConfigurationException
	 */
	public static void process(String xmlString) throws ParserConfigurationException {
		if (FileUtility.fileExists(xmlString)) {
			if (new XMLValidator().validate(xmlString, Constants.XSDFILEPATH)) {
				System.out.println("validation is successful and Processing File start " + xmlString);
				XMLReader.readANDProcessXML(xmlString);

				System.out.println("validation is successful and Processing File end  " + xmlString);
			} else {
				try {
					if (FileUtility.fileExists(xmlString)) {
						System.out.println(
								xmlString + " validation is un-successful and moving the file to error location ");
						Files.move(Paths.get(xmlString),
								Paths.get(Constants.XMLERRORFOLDER + Paths.get(xmlString).getFileName().toString()),
								StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Processing fail for xml "+  xmlString);
					e.printStackTrace();
				}
			}
		}else {
			System.out.println(xmlString + " File doesn't exists");
		}

	}

}

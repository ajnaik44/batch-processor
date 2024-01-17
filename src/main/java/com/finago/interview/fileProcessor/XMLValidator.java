package com.finago.interview.fileProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.finago.interview.task.Constants;

/**
 * Thid class will validate the xml with the XSD and gives output as boolean
 * 
 * @author Ajay Naik
 *
 */
public class XMLValidator {
	private boolean valid;

	/**
	 * Inner class for logging of validation
	 * 
	 * @author Ajay Naik
	 *
	 */
	private class ValidatorErrorHandler implements ErrorHandler {
		public void fatalError(SAXParseException e) throws SAXException {
			System.err.printf("Validation error on line %d: %s%n", e.getLineNumber(), e.getLocalizedMessage());
			valid = false;
		}

		public void error(SAXParseException e) throws SAXException {
			System.err.printf("Validation error on line %d: %s%n", e.getLineNumber(), e.getLocalizedMessage());
			valid = false;
		}

		public void warning(SAXParseException e) throws SAXException {
			System.err.printf("Validation warning on line %d: %s%n", e.getLineNumber(), e.getLocalizedMessage());
		}
	}

	/**
	 * This method will validate the xml with xsd and gives whether the xml as a
	 * proper structure as per xsd
	 */
	public boolean validate(String xmlFile, String schemaFile) {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(new FileInputStream(new File(xmlFile)));
			System.out.println("File Path" + getResource(schemaFile));
			Source source = new StreamSource(getResource(schemaFile));
			Schema schema = schemaFactory.newSchema(source);
			Validator validator = schema.newValidator();
			validator.setErrorHandler(new ValidatorErrorHandler());
			valid = true;
			validator.validate(new DOMSource(document));
			System.out.println("XML validation with xsd is successfully for file " + xmlFile);
			return valid;
		} catch (Throwable e) {
			System.out.println("XML validation with xsd is un-successfully for file " + xmlFile);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This methid will give the full path of the xml
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	private String getResource(String filename) throws FileNotFoundException {
		File dir = new File("./");

		return dir.getAbsolutePath() + Constants.XSDFILEPATH.substring(1);
	}
}

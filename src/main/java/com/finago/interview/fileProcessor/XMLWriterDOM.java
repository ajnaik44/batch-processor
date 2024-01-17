package com.finago.interview.fileProcessor;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.finago.interview.bean.ReceiverBean;

/**
 * This class is responsible for writing the xml as per Receiver Bean and as the
 * logic
 * 
 * @author Ajay Naik
 *
 */
public class XMLWriterDOM {
	/**
	 * This class will build the xml and write the xml in the folder Name
	 * 
	 * @param receiverBean
	 * @param folderName
	 */
	public static void buildXML(ReceiverBean receiverBean, String folderName) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element rootElement = doc.createElementNS("", "receiver");
			// append root element to document
			doc.appendChild(rootElement);

			// append first child element to root element
			populateReceiver(rootElement, receiverBean, doc);

			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(
					new File(folderName + receiverBean.getFile().split("\\.")[0] + ".xml"));

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Class will populate the Element of the Document object and gives the
	 * Element as the Node
	 * 
	 * @param receiver_Element
	 * @param receiverBean
	 * @param doc
	 * @return
	 */
	private static Node populateReceiver(Element receiver_Element, ReceiverBean receiverBean, Document doc) {

		// set id attribute

		receiver_Element
				.appendChild(getReceiverElements(doc, receiver_Element, "receiver_id", receiverBean.getReceiverId()));
		// create name element
		receiver_Element
				.appendChild(getReceiverElements(doc, receiver_Element, "first_name", receiverBean.getFirstName()));

		// create age element
		receiver_Element
				.appendChild(getReceiverElements(doc, receiver_Element, "last_name", receiverBean.getLastName()));

		// create role element
		receiver_Element.appendChild(getReceiverElements(doc, receiver_Element, "file", receiverBean.getFile()));

		// create gender element
		receiver_Element.appendChild(getReceiverElements(doc, receiver_Element, "file_md5", receiverBean.getFileMd5()));

		return receiver_Element;
	}

	/**
	 * utility method to create xml node
	 * 
	 * @param doc
	 * @param element
	 * @param name
	 * @param value
	 * @return
	 */

	private static Node getReceiverElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}

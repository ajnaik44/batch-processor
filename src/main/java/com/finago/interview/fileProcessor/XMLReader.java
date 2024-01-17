package com.finago.interview.fileProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.finago.interview.bean.ReceiverBean;
import com.finago.interview.task.Constants;

/**
 * This is the XML reader class
 * 
 * @author Ajay Naik
 *
 */
public class XMLReader {
	/***
	 * This method will take the xml file as the input parameter and process and
	 * generate the output as per logic
	 * 
	 * @param XMLfileName
	 */
	public static void readANDProcessXML(String fileName) {

		try {
			System.out.println("Processing of xml file start " + fileName);
			Document doc = getDoc(fileName);
			NodeList nList = doc.getElementsByTagName(Constants.XMLRECEIVER);

			
			Set<String> pdfCollection = new HashSet<String>();

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					ReceiverBean receiverBean = populateReciverBean(eElement);
					pdfCollection.add(receiverBean.getFile());

					String folder_name = String.valueOf(Integer.valueOf(receiverBean.getReceiverId()) % 100) + "/"
							+ Integer.valueOf(receiverBean.getReceiverId()) + "/";
					System.out.println(" Folder Name  " + folder_name);

					if (FileUtility.fileExists(Constants.INFolder + receiverBean.getFile())) {
						// Get the checksum
						String checksum = FileUtility.getFileChecksum(Constants.INFolder + receiverBean.getFile());

						// see checksum
						System.out.println(checksum);
						if (checksum.equals(receiverBean.getFileMd5())) {
							FileUtility.moveAndBuildXML(Constants.OUTFolder + folder_name,
									Constants.INFolder + receiverBean.getFile(),
									Constants.OUTFolder + folder_name + receiverBean.getFile(), folder_name,
									receiverBean);

						} else {
							FileUtility.moveAndBuildXML(Constants.XMLERRORFOLDER + folder_name,
									Constants.INFolder + receiverBean.getFile(),
									Constants.XMLERRORFOLDER + folder_name + receiverBean.getFile(), folder_name,
									receiverBean);

						}
					} else {
						File file_dir = new File(Constants.XMLERRORFOLDER + folder_name);

						boolean dirCreated = file_dir.mkdirs();
						System.out.println(dirCreated);

						XMLWriterDOM.buildXML(receiverBean, Constants.XMLERRORFOLDER + folder_name + "/");
					}

				}
			}
			for (String pdfString : pdfCollection) {
				File pdfFile = new File(Constants.INFolder + pdfString);
				pdfFile.delete();
				System.out.println("PDF deleted ::  " + pdfString);
			}
			Path sourcePath = Paths.get(fileName);
			Path destinationPath = Paths.get(Constants.XMLARCHIVEFOLDER + sourcePath.getFileName().toString());
			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * This method take the xml as the input parameter and gives Document object
	 * 
	 * @param fileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static Document getDoc(String fileName) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		return doc;
	}

	/**
	 * This method will take Receiver element as the object and gives ReceiverBean
	 * as the object
	 * 
	 * @param eElement
	 * @return
	 */
	private static ReceiverBean populateReciverBean(Element eElement) {
		int receiver_id = Integer
				.valueOf(eElement.getElementsByTagName(Constants.XMLRECEIVER_ID).item(0).getTextContent());
		String first_name = eElement.getElementsByTagName(Constants.XMLRECEIVER_FIRSTNAME).item(0).getTextContent();
		String last_name = eElement.getElementsByTagName(Constants.XMLRECEIVER_LASTNAME).item(0).getTextContent();
		String file = eElement.getElementsByTagName(Constants.XMLRECEIVER_FILE).item(0).getTextContent();
		String file_md5 = eElement.getElementsByTagName(Constants.XMLRECEIVER_FILE_MD5).item(0).getTextContent();
		ReceiverBean receiverBean = new ReceiverBean(String.valueOf(receiver_id), first_name, last_name, file,
				file_md5);
		System.out.println("Receiver Object " + receiverBean.toString());
		return receiverBean;
	}

}

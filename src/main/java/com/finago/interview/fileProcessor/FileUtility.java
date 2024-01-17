package com.finago.interview.fileProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.finago.interview.bean.ReceiverBean;

/**
 * This is the utility class for File service operation like move, file exists
 * 
 * @author Ajay Naik
 *
 */
public class FileUtility {
	public static boolean fileExists(String filePath) {
		File file = new File(filePath);
		System.out.println("checking File Exists for path " + filePath + "  and file exists is " + file.exists());
		return file.exists();
	}

	/**
	 * This method will get the file as the input and give the checksum of the file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getFileChecksum(String file) throws IOException, NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		// Get file input stream for reading the file content
		FileInputStream fis = new FileInputStream(file);

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// Read file data and update in message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		;

		// close the stream; We don't need it now.
		fis.close();

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// return complete hash
		return sb.toString();
	}

	/**
	 * This method will create the output directory as well as create the the xml
	 * and store in directory
	 * 
	 * @param createOutDirectory
	 * @param infolder
	 * @param outfolder
	 * @param folder_name
	 * @param receiverBean
	 * @throws IOException
	 */
	public static void moveAndBuildXML(String createOutDirectory, String infolder, String outfolder, String folder_name,
			ReceiverBean receiverBean) throws IOException {
		File file_dir = new File(createOutDirectory);
		System.out.println(" Folder created" + createOutDirectory + "Move File In Location " + infolder
				+ " Move File Out location" + outfolder + "Folder name " + folder_name);
		file_dir.mkdirs();
		Files.copy(Paths.get(infolder), Paths.get(outfolder), StandardCopyOption.REPLACE_EXISTING);
		XMLWriterDOM.buildXML(receiverBean, createOutDirectory);
	}

	/**
	 * This method takes the file name and extension to be check and give boolean
	 * result as per result
	 * 
	 * @param fileName
	 * @param fileExtensionAsXML
	 * @return
	 */
	public static boolean checkXMlFile(String fileName, String fileExtensionAsXML) {
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i >= 0) {
			extension = fileName.substring(i + 1);
		}
		return fileExtensionAsXML.equalsIgnoreCase(extension);

	}
}

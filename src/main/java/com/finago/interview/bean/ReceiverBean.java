package com.finago.interview.bean;

/**
 * 
 * @author Ajay Naik Receiver bean to populate xml elements
 */
public class ReceiverBean {
	String receiverId;
	String firstName;
	String lastName;
	String file;
	String fileMd5;

	public ReceiverBean() {
		// TODO Auto-generated constructor stub
	}

	public ReceiverBean(String receiverId, String firstName, String lastName, String file, String fileMd5) {
		super();
		this.receiverId = receiverId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.file = file;
		this.fileMd5 = fileMd5;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFile() {
		return file;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Receiver Object ::  " + this.receiverId + " First Name " + this.firstName + " Last Name "
				+ this.lastName + " File " + this.file + "  file MD5 " + this.fileMd5;
	}

}

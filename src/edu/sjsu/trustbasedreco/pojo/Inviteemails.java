package edu.sjsu.trustbasedreco.pojo;

import java.util.ArrayList;
import java.util.List;

public class Inviteemails {
	
	List<String> emailaddress = new ArrayList<String>();
	String senderemail;
	
	public List<String> getEmailaddress() {
		return emailaddress;
	}
	
	public void setEmailaddress(List<String> emailaddress) {
		this.emailaddress = emailaddress;
	}

	public void addEmail(String email) {
		emailaddress.add(email);
	}
	
	public String getSenderemail() {
		return senderemail;
	}
	
	public void setSenderemail(String senderemail) {
		this.senderemail = senderemail;
	}
}

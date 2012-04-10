package br.com.bfmapper.model.xml;

import javax.xml.datatype.XMLGregorianCalendar;

public class Client {
	
	private String xmlName;
	private XMLGregorianCalendar xmlBirthday;
	private String xmlNickname;
	private String xmlEmail;
	private String xmlPhone;
	private String xmlTwitter;
	
	public String getXmlName() {
		return xmlName;
	}
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
	public XMLGregorianCalendar getXmlBirthday() {
		return xmlBirthday;
	}
	public void setXmlBirthday(XMLGregorianCalendar xmlBirthday) {
		this.xmlBirthday = xmlBirthday;
	}
	public String getXmlNickname() {
		return xmlNickname;
	}
	public void setXmlNickname(String xmlNickname) {
		this.xmlNickname = xmlNickname;
	}
	public String getXmlEmail() {
		return xmlEmail;
	}
	public void setXmlEmail(String xmlEmail) {
		this.xmlEmail = xmlEmail;
	}
	public String getXmlPhone() {
		return xmlPhone;
	}
	public void setXmlPhone(String xmlPhone) {
		this.xmlPhone = xmlPhone;
	}
	public String getXmlTwitter() {
		return xmlTwitter;
	}
	public void setXmlTwitter(String xmlTwitter) {
		this.xmlTwitter = xmlTwitter;
	}
 
}

package br.com.bfmapper.model.canonic;

import java.util.Date;

public class CoolClient extends Client {

	private String twitter;
	
	public CoolClient(String name, Date birthday, String twitter) {
		this.setName(name);
		this.setBirthday(birthday);
		this.twitter = twitter;
	}

	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	
}

package br.com.bfmapper.model.canonic;

import java.util.Date;

public abstract class Client {

	private String name;
	private Date birthday;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}

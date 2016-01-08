package com.simple.jsf.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="user")
@SessionScoped
public class User {
	
	
	private String id = "2";
	private String name = "Sudha";
	private String email = "sudha@email.com";
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	private String firstname;
	private String lastname;	
	private String lastsignon;	
	


	//action method to add user
	public String addUser(){
		return "success";
	}
	
	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getLastsignon() {
		return lastsignon;
	}


	public void setLastsignon(String lastsignon) {
		this.lastsignon = lastsignon;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}


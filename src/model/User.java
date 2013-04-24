package model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class User {
	@Element
	private String id;
	
	@Element(name="passwd")
	private String pass;
	
	@Element(name="email")
	private String login;//email
	
	@Element
	private String firstname;
	@Element
	private String lastname;
	

	public User(String id, String login, String pass, String firstname,
			String lastname) {
		super();
		this.id = id;
		this.login = login;
		this.pass = pass;
		this.firstname = firstname;
		this.lastname = lastname;
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

	public User() {
		super();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User(String login, String pass, String id) {
		super();
		this.login = login;
		this.pass = pass;
		this.id = id;
	}
	public User(String login, String pass) {
		super();
		this.login = login;
		this.pass = pass;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "User [login=" + login + ", pass=" + pass + "]";
	}
	@Override
	public boolean equals(Object o) {
		User u2 = (User)o;
		return (login.equals(u2.getLogin()) && pass.equals(u2.getPass()));
	}
	
}

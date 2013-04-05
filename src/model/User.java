package model;

public class User {
	private String login;
	private String pass;
	
	
	
	public User() {
		super();
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

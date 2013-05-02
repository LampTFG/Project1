package model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="prestashop")
public class User {
	
	@Element
	@Path("customer")
	private String id;
	
	@Element(name="passwd")
	@Path("customer")
	private String pass;
	
	@Element(name="imagePath")
	@Path("customer")
	private String imagePath;
	
	@Element
	@Path("customer")
	private String firstname;
	@Element
	@Path("customer")
	private String lastname;
	
	@Element
	@Path("customer")
	private String email;
	

	public User(String id, String pass, String firstname,
			String lastname, String email) {
		super();
		this.id = id;
		this.pass = pass;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
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
		this.email = login;
		this.pass = pass;
		this.id = id;
	}
	public User(String login, String pass) {
		super();
		this.email = login;
		this.pass = pass;
	}
	public String getLogin() {
		return email;
	}
	public void setLogin(String login) {
		this.email = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "User [login=" + email + ", pass=" + pass + "]";
	}
	@Override
	public boolean equals(Object o) {
		User u2 = (User)o;
		return (email.equals(u2.getLogin()) && pass.equals(u2.getPass()));
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
}

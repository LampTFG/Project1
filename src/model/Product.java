package model;

import java.io.Serializable;

public class Product implements Serializable{
	int id;
	float price;
	String shortDesc;
	String longDesc;
	String name;
	
	
	public Product() {
		super();
	}

	public Product(int id, float price, String shortDesc, String longDesc,
			String name) {
		super();
		this.id = id;
		this.price = price;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.name = name;
	}

	public String toString(){
		return "ID: "+this.id+"/nName: "+this.name+"/nShort Description: "+this.shortDesc+
				"/nLong Description: "+this.longDesc+"/nPrice: "+this.price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	
	
	
}

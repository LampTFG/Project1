package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XMLParser2 {

	private static final String ns = null;
	public static final int GET_CUSTOMER_BY_EMAIL = 1;
	public static final int GET_CUSTOMER_BY_ID = 2;
	public static final int GET_PRODUCT_BY_ID = 2;
	
    public List parse(InputStream in, int option) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readPrestashop(parser, option);
        } finally {
            in.close();
        }
    }
	
	private List readPrestashop(XmlPullParser parser, int option) throws XmlPullParserException, IOException {
		List entries = new ArrayList();
		
		parser.require(XmlPullParser.START_TAG, ns, "prestashop");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			System.out.println("XMLParser2 readPrestashop getName: "+ parser.getName());
			if(name == "customers"){//when you resquest a user by email
				entries.add(readCustomers(parser));
			}else if(name == "customer"){//when you request a user by id
				entries.add(readCustomer(parser));
			}else if(name == "product"){//when request a product by id
				entries.add(readProduct(parser));
			}
		}
		return entries;
	}
	//Read product info from XML
	private Product readProduct(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "product");
		String productID=null;
		String productName = null;
		String shortDesc = null;
		String longDesc = null;
		String price = null;
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//System.out.println("readCustomer parser getName: "+ parser.getName());
			if(name.equals("id")){
				productID = readID(parser);
				System.out.println("readCustomer parser getName: "+ parser.getName());
			}else if(name.equals("name")){
				name = readProductName(parser);
			}else if(name.equals("passwd")){
				//password = readPassword(parser);
			}else{
				skip(parser);
			}
		}
		return new Product();
	}
	
	//Read customer data from XML
	private User readCustomer(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "customer");
		String customerID=null;
		String firstname = null;
		String lastname = null;
		String login= null;
		String passwd = null;
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//System.out.println("readCustomer parser getName: "+ parser.getName());
			if(name.equals("id")){
				customerID = readID(parser);
			}else if(name.equals("firstname")){
				login = readFirstName(parser);
			}else if(name.equals("lastname")){
				lastname = readLastName(parser);
			}else if(name.equals("email")){
				login = readEmail(parser);
			}else if(name.equals("passwd")){
				passwd = readPassword(parser);
			}else{
				skip(parser);
			}
		}
		if(customerID.equals(null))
			return null;
		else
			return new User(customerID,login,passwd,firstname,lastname);
	}
	
	private String readID(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "id");
		String id = readText(parser);
		System.out.println("XMLParser2: readID: "+id);
		parser.require(XmlPullParser.END_TAG, ns, "id");
		return id;
	}
	
	private String readProductName(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "name");
		String name = readText(parser);
		System.out.println("readProductName: "+name);
		parser.require(XmlPullParser.END_TAG, ns, "name");
		return name;
	}
	
	private String readLastName(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "lastname");
		String lastname = readText(parser);
		System.out.println("XMLParser2: readLastName: "+lastname);
		parser.require(XmlPullParser.END_TAG, ns, "lastname");
		return lastname;
	}
	
	private String readFirstName(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "firstname");
		String firstname = readText(parser);
		System.out.println("XMLParser2: readFirstName: "+firstname);
		parser.require(XmlPullParser.END_TAG, ns, "firstname");
		return firstname;
	}
	
	private String readPassword(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "passwd");
		String passwd = readText(parser);
		System.out.println("XMLParser2:  readPassword: "+passwd);
		parser.require(XmlPullParser.END_TAG, ns, "passwd");
		return passwd;
	}
	
	private String readEmail(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "email");
		String login = readText(parser);
		System.out.println("XMLParser2 :readEmail: "+login);
		parser.require(XmlPullParser.END_TAG, ns, "email");
		return login;
	}
	
	//Read the xml returned from email,passwd query and return the requested user
	private User readCustomers(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "customers");
		User customer=null;
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("customer")){
				customer = readCustomer(parser);
			}else{
				skip(parser);
			}
		}
		return(customer);
	}
	/*
	//Read the customer Id from ?email= query
	private String readCustomerID(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "customer");
		String tag = parser.getName();
		System.out.println("readCustomerID parser getName: "+ parser.getName());
		String id = parser.getAttributeValue(null, "id");
		System.out.println("readCustomerID parser id: "+ id);
		
		return id;
	}
	*/
	// For the tags, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	//Skips an unwanted tag
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
	
}

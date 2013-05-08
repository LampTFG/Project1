package utils;

import java.io.IOException;
import java.io.InputStream;
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
	
	private static int language =0;
	
    public List<Object> parse(InputStream in, int option) throws XmlPullParserException, IOException {
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
	
	private List<Object> readPrestashop(XmlPullParser parser, int option) throws XmlPullParserException, IOException {
		List<Object> entries = new ArrayList<Object>();
		
		parser.require(XmlPullParser.START_TAG, ns, "prestashop");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			System.out.println("XMLParser2 readPrestashop getName: "+ parser.getName());
			if(name.equals("customers")){//when you resquest a user by email
				entries.add(readCustomers(parser));
			}else if(name.equals("customer")){//when you request a user by id
				entries.add(readCustomer(parser));
			}else if(name.equals("product")){//when request a product by id
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
		String imagePath = null;
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			language=1;
			String name = parser.getName();
			if(name.equals("id")){
				productID = readTag(parser,"id");
			}else if(name.equals("name")){
				productName = readLanguageTag(parser, name);
			}else if(name.equals("description")){
				longDesc = readLanguageTag(parser, name);
			}else if(name.equals("description_short")){
				shortDesc = readLanguageTag(parser, name);
			}else if(name.equals("price")){
				price = readTag(parser,"price");
			}else if(name.equals("associations")){
				imagePath = readSubItem(parser,"images", "image");
			}else{
				skip(parser);
			}
		}
		return new Product(Integer.parseInt(productID),Float.parseFloat(price), shortDesc,longDesc,productName, imagePath);
	}
	
	private String readSubItem(XmlPullParser parser, String ... tag) throws XmlPullParserException, IOException {
		int cont=0;
		String resp = "";
		while(parser.next() != XmlPullParser.END_DOCUMENT && cont<tag.length){
			if(parser.getName()!=null && parser.getName().equals(tag[cont])){
				cont++;
				if(cont==tag.length){
					resp = parser.getAttributeValue(0);
				}
			}
		}
		return resp;
	}

	//Used to read an inner tag named language, it always read the first language
	private String readLanguageTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String prodName = null;
			
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("language") && language == 1){
				prodName = readTag(parser,"language");
				language = 0;
			}else{
				skip(parser);
			}
		}
		return prodName;
	}
			
	//Read customer data from XML
	private User readCustomer(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "customer");
		String customerID=null;
		String firstname = null;
		String lastname = null;
		String passwd = null;
		String email = null;
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			//System.out.println("readCustomer parser getName: "+ parser.getName());
			if(name.equals("id")){
				customerID = readTag(parser,"id");
			}else if(name.equals("firstname")){
				firstname = readTag(parser,"firstname");
			}else if(name.equals("lastname")){
				lastname = readTag(parser, "lastname");
			}else if(name.equals("email")){
				email = readTag(parser, "email");
			}else if(name.equals("passwd")){
				passwd = readTag(parser, "passwd");
			}else{
				skip(parser);
			}
		}
		if(customerID.equals(null))
			return null;
		else
			return new User(customerID,passwd,firstname,lastname, email);
	}
	//Read the tag
	private String readTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String id = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, tag);
		return id;
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
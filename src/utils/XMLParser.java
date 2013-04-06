package utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.User;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

//Extend the class with Default Handler

public class XMLParser extends DefaultHandler {
	ArrayList<User> itemsList = new ArrayList<User>();
	User item;
	String data;
	String type;
	private String tempVal;

	// Create the Constructor
	public XMLParser(String data) {
		itemsList = new ArrayList<User>();

		this.data = data;

	}

	public byte parse() {

		SAXParserFactory spf = null;
		SAXParser sp = null;
		InputStream inputStream = null;

		try {
			inputStream = new ByteArrayInputStream(data.getBytes());
			spf = SAXParserFactory.newInstance();
			if (spf != null) {
				sp = spf.newSAXParser();
				sp.parse(inputStream, this);
			}
		}
		/*
		 * Exceptions need to be handled MalformedURLException
		 * ParserConfigurationException IOException SAXException
		 */

		catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e) {
			}
		}

		if (itemsList != null && itemsList.size() > 0) {
			return 1;
		} else {
			return 0;
		}

	}

	public ArrayList<User> getItemList() {
		return itemsList;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equalsIgnoreCase("customer")) {
			item = new User();
			Log.d("Working", "+++++++++++++++++++++++");
		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempVal = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println(localName +" - "+ tempVal);
		if (localName.equalsIgnoreCase("firstname")) {
			itemsList.add(item);
			Log.d("Working in endelement", "+++++++++++++++++++++++");
			item.setLogin(tempVal);
		}
	}
}
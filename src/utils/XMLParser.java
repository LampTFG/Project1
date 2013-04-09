package utils;

import java.io.ByteArrayInputStream; 
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.User;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {
	User item;
	String data;
	String type;
	private String tempVal;
	private String lookingFor;
	private String resp;

	// Create the Constructor
	public XMLParser(String data, String search) {
		this.data = data;
		this.lookingFor = search;
	}

	public void parse() {
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
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempVal = new String(ch, start, length);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase(lookingFor))
			resp = tempVal;
	}
	
	public String getResp(){
		return resp;
	}
}
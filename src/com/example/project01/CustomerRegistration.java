package com.example.project01;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.CustomerFormRequester;

import model.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CustomerRegistration extends Activity {
	private EditText emailtv;
	private EditText passwdtv;
	private EditText nametv;
	private EditText lastnametv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_registration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_customer_registration, menu);
		return true;
	}
	
	public void RegisterUser(View v){
		emailtv = (EditText) findViewById(R.id.email_cutomer_registration);
		String email = emailtv.getText().toString();
		
		passwdtv = (EditText) findViewById(R.id.passwd_customer_registration);
		String passwd= passwdtv.getText().toString();
		
		nametv= (EditText) findViewById(R.id.first_name_customer_registration);
		String firstname = nametv.getText().toString();
		
		lastnametv = (EditText) findViewById(R.id.last_name_customer_registration);
		String lastname = lastnametv.getText().toString();
		
		User u = new User();
		u.setId("1");
		u.setLogin(email);
		u.setPass(passwd);
		u.setFirstname(firstname);
		u.setLastname(lastname);
		
		File result = new File(getFilesDir().getPath()+"/customer.xml");
		System.out.println(getFilesDir()+"/customer.xml");
		try {
			Serializer serializer = new Persister();
			serializer.write(u, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			File filepath = new File(getFilesDir().getPath()+"/customer.xml");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			
			Node prestashop = doc.getFirstChild();
			Node customer = doc.getElementsByTagName("customer").item(0);
			
			NodeList list = customer.getChildNodes();
			
			for(int i=0;i<list.getLength();i++){
				Node node = list.item(i);
				
				if(node.getNodeName().equals("id")){
					node.setTextContent("5");
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resu =  new StreamResult(new File(filepath.getPath()));
			transformer.transform(source, resu);
			
			new CustomerFormRequester().execute(u);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

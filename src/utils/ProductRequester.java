package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import model.Product;
import model.User;

public class ProductRequester extends AsyncTask<String, Void, Product> {
	
	Product product;

	/*public Product find(int prodID){
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/"+prodID+"?ws_key="+ Vars.wsKey));
		String response = res.getResponse().toString();
		System.out.println("Response: "+ response);
		product = parseAttributes(response);
		
		return product;
	}*/

	@Override
	protected Product doInBackground(String... params) {
		find(params[0]);
		return product;
	}
	
	private void findProduct(String id){
		String filters = "filters=[id]"+id;
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/?ws_key="+ Vars.wsKey+"&"+filters));
		//String response = res.getResponse().toString();
		InputStream i = res.getResponse();
		//user = parseAttributes(response);
		XMLParser2 parser = new XMLParser2();
		try {
			List entries = parser.parse(i, XMLParser2.GET_PRODUCT_BY_ID);
			product = (Product) entries.get(0);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void find(String id){
		findProduct(id);
		System.out.println("Customer Requester: findUser id "+ product.getId()+ "Name: "+product.getName());
	}
	
	private Product parseAttributes(String response){
		Product product = new Product();
		
		XMLParser xml = new XMLParser(response, "id");
		xml.parse();
		product.setId(Integer.parseInt(xml.getResp()));
		
		xml = new XMLParser(response, "name");
		xml.parse();
		product.setName(xml.getResp());
		
		xml = new XMLParser(response, "description_short");
		xml.parse();
		product.setShortDesc(xml.getResp());
		
		xml = new XMLParser(response, "description");
		xml.parse();
		product.setLongDesc(xml.getResp());
		
		xml = new XMLParser(response, "price");
		xml.parse();
		product.setPrice(Float.parseFloat(xml.getResp()));
		
		return product;
	}

}

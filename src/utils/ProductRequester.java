package utils;

import android.os.AsyncTask;
import model.Product;

public class ProductRequester extends AsyncTask<String, Void, Product> {
	
	Product product;

	private Product find(int prodID){
		Response res = new Response(Functions.getProductsUrl(prodID));
		String response = res.getResponse();
		product = parseAttributes(response);
		
		return product;
	}

	@Override
	protected Product doInBackground(String... params) {
		find(Integer.parseInt(params[0]));
		return product;
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

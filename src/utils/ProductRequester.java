package utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import model.Product;

public class ProductRequester extends AsyncTask<String, Void, Product> {

	Activity context;
	int prodID;
	Product product;
	
	public ProductRequester(Activity context) {
		this.context = context;
	}

	public int getProdID() {
		return prodID;
	}

	public void setProdID(int prodID) {
		this.prodID = prodID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product find(){
		System.out.println("ProductRequester: entrou no find");
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/"+prodID+"?ws_key="+ Vars.wsKey));
		String response = res.getResponse();
		product = parseAttributes(response);
		
		return product;
	}
	
	public void find(int id){
		this.prodID = id;
		find();
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

	@Override
	protected Product doInBackground(String... params) {
		find(Integer.parseInt(params[0]));
		return product;
	}
}

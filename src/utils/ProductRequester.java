package utils;

import model.Product;

public class ProductRequester {

	
	public static Product find(int id){
		Product product;
		int prodID = id;
		System.out.println("entrou no find");
		Response res = new Response(Functions.urlConcat("http://"+ Vars.wsServer, 
				Vars.wsProductPath + "/"+prodID+"?ws_key="+ Vars.wsKey));
		String response = res.getResponse();
		System.out.println("Response: "+ response);
		product = parseAttributes(response);
		
		System.out.println("Product: "+product.toString());
		return product;
	}
	
	private static Product parseAttributes(String response){
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
	/*public synchronized void run() {
		Response res = new Response(
				Functions.urlConcat("http://" + Vars.wsServer,
						Vars.wsProductPath + "/3?ws_key=" + Vars.wsKey));
		String response = res.getResponse();
		XMLParser xml = new XMLParser(response, "name");
		xml.parse();
		respText = xml.getResp();
		notifyAll();
	}*/
	
}

package utils;

import model.Product;

public class ProductRequester extends Thread {

	int prodID;
	Product product;
	
	public ProductRequester(int prodID) {
		super();
		this.prodID = prodID;
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
		System.out.println("Response: "+ response);
		product = parseAttributes(response);
		
		System.out.println("Product: "+product.toString());
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
	public void run() {
        this.product=find();
        notifyAll();
    }
	
}

package utils;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import android.os.AsyncTask;
import model.Product;

public class ProductRequester extends AsyncTask<String, Void, Product> {

	Product product;

	@Override
	protected Product doInBackground(String... params) {
		try {
			find(params[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	// Use this method when simple framework is 100%
	private void findProductSimple(String id) throws Exception {
		URL url = new URL(Vars.lampWS+"/products.php?key=" + Vars.wsKey+"&id="+id);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(url.openStream()));
		doc.getDocumentElement().normalize();
		//collecting strings
		String id_product = getJSONTag(doc, "id_product");
		String name = getJSONTag(doc, "name");
		String description_short = getJSONTag(doc, "description_short");
		String description = getJSONTag(doc, "description");
		String id_image = getJSONTag(doc, "id_image");
		String active = getJSONTag(doc, "active");
		String price = getJSONTag(doc, "price");
		//seting product
		product = new Product();
		product.setId(id_product);
		product.setName(name);
		product.setLongDesc(description);
		product.setShortDesc(description_short);
		product.setImagePath(id_image);
		product.setPrice(price);
	}
	
	private void find(String id) throws Exception {
		findProductSimple(id);
	}
	
	private String getJSONTag(Document xml, String tag) {
		return xml.getElementsByTagName(tag).item(0).getTextContent();
	}
}

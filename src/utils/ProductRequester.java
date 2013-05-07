package utils;

import java.io.InputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
	
	private void findProduct(String id) throws Exception{
		String filters = "filter[id]="+id;
		String display = "display=[id,description,description_short,price,name,id_default_image]";
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
		InputStream i = res.getResponse();
		Serializer serializer = new Persister();
		product = serializer.read(Product.class, i, false);
		product.setName("name");
		product.setLongDesc("loing");
		product.setShortDesc("short");
		System.out.println("Product REquester: "+product.toString());
	}

	private void find(String id) throws Exception{
		findProduct(id);
	}

}

package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import model.Product;

public class ProductRequester extends AsyncTask<String, Void, Product> {
	
	Product product;

	@Override
	protected Product doInBackground(String... params) {
		find(params[0]);
		return product;
	}
	
	private void findProduct(String id){
		String filters = "filter[id]="+id;
		String display = "display=full";
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
		InputStream i = res.getResponse();
		XMLParser2 parser = new XMLParser2();
		try {
			List<Object> entries = parser.parse(i, XMLParser2.GET_PRODUCT_BY_ID);
			product = (Product) entries.get(0);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void find(String id){
		findProduct(id);
	}

}

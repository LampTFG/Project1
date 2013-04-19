package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;
import model.Product;
import model.User;

public class ProductRequester extends AsyncTask<String, Void, Product> {
	
	Product product;

	@Override
	protected Product doInBackground(String... params) {
		find(params[0]);
		return product;
	}
	
	private void findProduct(String id){
		System.out.println("Entrou no find PRod");
		String filters = "filter[id]="+id;
		String display = "display=full";
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
		String response = res.getResponse().toString();
		System.out.println("ProductRequester" + "FindProduct: "+response );
		InputStream i = res.getResponse();
		//user = parseAttributes(response);
		XMLParser2 parser = new XMLParser2();
		try {
			List entries = parser.parse(i, XMLParser2.GET_PRODUCT_BY_ID);
			product = (Product) entries.get(0);
			System.out.println("Produto encontrado= "+product.toString());
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
	}

}

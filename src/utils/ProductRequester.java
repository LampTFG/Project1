package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.PersistenceException;
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
		product = new Product();
		String filters = "filter[id]="+id;
		String display = "display=[id,description,description_short,price,name,id_default_image]";
		Response res = new Response(Functions.urlConcat(Vars.wsServer, 
				Vars.wsProductPath + "/?ws_key="+ Vars.wsKey+"&"+filters+"&"+display));
		InputStream in = res.getResponse();
		Serializer serializer = new Persister();
		//Essa parte eh soh para teste, depois de arrumar o simple framework pode tirar
		/*product.setId(1);
		product.setImagePath("path");*/
		product.setName("name");
		product.setLongDesc("long");
		product.setShortDesc("short");
		/*product.setPrice(10);
		 OutputStream output = new OutputStream()
		    {
		        private StringBuilder string = new StringBuilder();
		        @Override
		        public void write(int b) throws IOException {
		            this.string.append((char) b );
		        }

		        //Netbeans IDE automatically overrides this toString()
		        public String toString(){
		            return this.string.toString();
		        }
		    };
		serializer.write(product, output);
		System.out.println("Requester: "+output.toString());*/
		
		try{
			serializer.read(product, in,false);
		}catch(PersistenceException pe){
			System.out.println(pe.getMessage());
		}
		
	}

	private void find(String id) throws Exception{
		findProduct(id);
	}

}

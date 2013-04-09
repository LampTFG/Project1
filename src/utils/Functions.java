package utils;

public class Functions {

	public static String urlConcat(String ... strings ){
		String resp = "";
		for(int i=0;i<strings.length;i++){
			resp += strings[i] + ((strings[i].endsWith("/"))?"":"/");
		}
		return resp;
	}
}

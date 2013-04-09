package utils;

public class Functions {

	public static String urlConcat(String ... strings ){
		String resp = "";
		for(int i=0;i<strings.length-1;i++){
			resp += strings[i] + ((strings[i].endsWith("/"))?"":"/");
		}
		resp += strings[strings.length-1];
		return resp;
	}
}

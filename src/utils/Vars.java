package utils;

//global vars
public class Vars {
	
	//_COOKIE_KEY_ encrypt key for password on remote server
	//the file containing this key is /var/www/store/config/settings.inc.php 
	public static String encryptKey = "8YB4I3RhahSyr4zbUY84ROJzkTBD0p6oAQRhGehI2CgoEInkzG45PtDU";
	
	//ws - WebService
	public static String wsKey = "4OZ72NAAOCD3ZYWYCOENYIBGU3HU3F39";
	
	//For remote access
	public static String wsServer = "http://www.site1366132944.hospedagemdesites.ws/";
	
	//webService
	public static String wsCustomersPath = "store/api/customers";
	public static String wsProductPath = "store/api/products";
	
	//LampWS
	public static String lampWS = wsServer+"store/lampWS";
	
	//editProfile
	public static String editProfile = "http://www.site1366132944.hospedagemdesites.ws/store/index.php?controller=authentication&back=my-account";
	
	//extra fee
	public static float extraFee = 5.0f;
}

package fr.cnam.openopti.fonctions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fonction {

	public Fonction() {
		super();
	}
	
	/**
	 * 
	 * @param data date dont il faut modifier le format
	 * @param actualFormat son format actuel
	 * @param newFormat le nouveau format que l'on souhaite
	 * @return String la date avec le nouveau format
	 * @throws ParseException
	 */
	
	//DEPRECATED UTILISATION DES FONCTIONS MYSQL A LA PLACE
	public static String bddDateFormat(String data, String actualFormat,String newFormat) throws ParseException {
		//String formFormat = "dd/MM/yyyy";
		// bddFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf= new SimpleDateFormat(actualFormat);
		Date d = sdf.parse(data);
		sdf.applyPattern(newFormat);
		return sdf.format(d);
	}
	
	/**
	 * Affiche un nombre limité de caractère d'un string
	 * @param value String valeur à reduire
	 * @param nbChar int nombre de caractère à affiché souhaité
	 * @return String
	 */
	public static String showLessString(String value, int nbChar) {
		return (value != null && value.length() > nbChar)?value.substring(0, (nbChar - 3)) + "...":value;
	}
	
	public static String mdpHash(String mdp) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(
					  mdp.getBytes(StandardCharsets.UTF_8));
			return  bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

	public static String arrondir(String nb,double decimal){
		double nombre = Double.parseDouble(nb);
		double  arr = (double)((int)(nombre * Math.pow(10,decimal) + .5)) / Math.pow(10,decimal);
		return String.valueOf(arr);
	}
}

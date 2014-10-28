package dev;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseRequest {

	/**
	 * convert string from UTF-8 to Unicode
	 * @param utfString
	 * @return unicode string
	 */
	public static String convertUTF8toUnicode(byte[] utfString) {
		
		try {
			String str = new String(utfString, "US-ASCII");
			return str;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	public static JSONObject parseJSON (String json) {
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			return jsonObj;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}

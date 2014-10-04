package dev;

public class AnalyzeRequest {

	private static String mRequest = "map (a: Type1, b: Type2)<Generic: gen1, gen2> -> type";
	private static String name = "";
	private static String generic = "";
	private static String signature = "";
	private static String output = "";
	
	/**
	 * save extracted parameter to global variable
	 * @param request - extractable string
	 * @param pos - extract from 0 to pos
	 * @param global - flag, means which global param changes
	 * @return true if success, false if that parameter full
	 */
	private static boolean extractGlobal (String request, int pos, int global) {
		
		switch (global) {
		case 0:
			{
				if ( name.equals("")) {
					name = request.substring(0, pos);
					return true;
				}
				else {
					System.out.println("ERROR, duplicate name");
					return false;
				}
			}
		case 1:
		{
			if ( generic.equals("")) {
				generic = request.substring(0, pos);
				return true;
			}
			else {
				System.out.println("ERROR, duplicate generic");
				return false;
			}
		}
		case 2:
		{
			if ( signature.equals("")) {
				signature = request.substring(0, pos);
				return true;
			}
			else {
				System.out.println("ERROR, duplicate signature");
				return false;
			}
		}
		case 3:
		{
			if ( output.equals("")) {
				output = request.substring(0, pos);
				return true;
			}
			else {
				System.out.println("ERROR, duplicate output");
				return false;
			}
		}

		default:
			return false;
		}
	}
	
	/**
	 * 
	 * @param request - extractable string
	 * @param posChar - extract from 0 to posChar
	 * @param global - flag
	 * @return tail of request after extracting parameter
	 */
	private static String extract (String request, char posChar, int global) {
		
		String temp;
		int pos;
		
		pos = request.indexOf(posChar) + 1;
		if (!extractGlobal(request, pos, global)) {
			return null;
		}
		temp = request.substring(pos);
		return temp;
	}
	
	/**
	 * 
	 * @param request - extractable string
	 * @param pos - extract from 0 to pos
	 * @param global - flag
	 * @return tail of request after extracting parameter
	 */
	private static String extract (String request, int pos, int global) {
		
		String temp;
		if (!extractGlobal(request, pos, global)) {
			return null;
		}
		temp = request.substring(pos);
		return temp;
	}
	
	/**
	 * removes whitespaces from begin of the string
	 * @param str
	 * @return tail of the str
	 */
	private static String removeWhitespace (String str) {
		int pos = 0;
		while ( pos < str.length() && String.valueOf(str.charAt(pos)).matches("[ \t]") ) {
			pos++;
		}
		return str.substring(pos);
	}
	
	public static void main (String [] args) {
		
		String request = mRequest;
		int pos;
		PrimaryRegexp.type type;
		
		System.out.println("request: " + request);
		while ( !request.matches("[ \t]*")) {
			
			type = PrimaryRegexp.type(request);
			
			if ( type == dev.PrimaryRegexp.type.NAME ) {
				System.out.println("name");	
				request = removeWhitespace(request);
				request = extract(request, ' ', 0);
				if (request == null) break;
				System.out.println("request string = " + request);
			}
			
			if ( type == dev.PrimaryRegexp.type.GENERIC ) {
				System.out.println("generic");
				request = extract(request, '>', 1);
				if (request == null) break;
				System.out.println("request string = " + request);
			}
			
			if ( type == dev.PrimaryRegexp.type.SIGNATURE ) {
				System.out.println("signature");
				request = extract(request, ')', 2);
				if (request == null) break;
				System.out.println("request string = " + request);
			}
			
			if ( type == dev.PrimaryRegexp.type.OUTPUT ) {
				System.out.println("output");
				
				pos = request.indexOf(">") + 1; 
				request = request.substring(pos);
				request = removeWhitespace(request);
				while ( pos < request.length() && String.valueOf(request.charAt(pos)).matches("[a-z]") ) {
					pos++;
				}
				request = extract(request, pos, 3);
				if (request == null) break;
				System.out.println("request string = " + request);
			}
			
			if ( type == dev.PrimaryRegexp.type.PARSE_ERROR ) {
				System.out.println("ERROR");
				break;
			}
		}
		
		System.out.println("");
		System.out.println("name: " + name);
		System.out.println("generic: " + generic);
		System.out.println("signature: " + signature);
		System.out.println("output: " + output);
	}
}

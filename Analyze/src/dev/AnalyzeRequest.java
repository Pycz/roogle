package dev;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import dev.AnalyzeType.Type;

public class AnalyzeRequest {

	private static String mRequest = "-> Type<T>";//"map (a: Type1, b: Type2) <'a, T: G1 + G2> -> String<Type1, Type2>";
	private static String name = "";
	public static LinkedList<LinkedList> genericList = new LinkedList<LinkedList>();
	public static LinkedList lifetimesList = new LinkedList();
	public static LinkedList signatureList = new LinkedList();
	public static Type output = new Type();
	
	/**
	 * clean globals for tests
	 */
	public static void clean() {
		name = "";
		genericList = new LinkedList<LinkedList>();
		lifetimesList = new LinkedList();
		signatureList = new LinkedList();
		output = new Type();
	}
	
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
					//System.out.println("ERROR, duplicate name");
					return false;
				}
			}
		case 1:
		{
			if ( genericList.isEmpty() ) {
				genericList = AnalyzeGeneric.parseGeneric(request.substring(0, pos));
				
				if (genericList == null)
					return false;
				
				lifetimesList = genericList.getFirst();
				genericList = genericList.getLast();
				
				return true;
			}
			else {
				//System.out.println("ERROR, duplicate generic");
				return false;
			}
		}
		case 2:
		{
			if ( signatureList.isEmpty() ) {
				signatureList = AnalyzeSignature.parseSignature(request.substring(0, pos));
				if (signatureList == null) {
					return false;
				}
				return true;
			}
			else {
				//System.out.println("ERROR, duplicate signature");
				return false;
			}
		}
		case 3:
		{
			if ( output.isEmpty()) {
				output = AnalyzeOutput.parseOutput(request.substring(0, pos));
				if (!output.equals("")) {
					return true;
				}
				return false;
			}
			else {
				//System.out.println("ERROR, duplicate output");
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
	
	/**
	 * 
	 * @return function name
	 */
	public static String name() {
		return name;
	}
	
	/**
	 * 
	 * @return output type
	 */
	public static Type output() {
		return output;
	}
	
	public static JSONObject analyze(String request) {
		int pos;
		PrimaryRegexp.type type;
		JSONObject json;
		
		while ( !request.matches("[ \t]*")) {
			
			type = PrimaryRegexp.type(request);
			
			if ( type == dev.PrimaryRegexp.type.NAME ) {
				request = removeWhitespace(request);
				pos = 0;
				while ( pos < request.length() && String.valueOf(request.charAt(pos)).matches(PrimaryRegexp.nameRegex) ) {
					pos++;
				}
				request = extract(request, pos, 0);
				if (request == null) {
					return JSONManager.error();
				}
			}
			
			if ( type == dev.PrimaryRegexp.type.GENERIC ) {
				request = removeWhitespace(request);
				request = extract(request, '>', 1);
				if (request == null) {
					return JSONManager.error();
				}
			}
			
			if ( type == dev.PrimaryRegexp.type.SIGNATURE ) {
				request = removeWhitespace(request);
				request = extract(request, ')', 2);
				if (request == null) {
					return JSONManager.error();
				}
			}
			
			if ( type == dev.PrimaryRegexp.type.OUTPUT ) {
				
				Pattern pattern = Pattern.compile("[ \t]*-[ \t]*>[ \t]*([a-zA-Z0-9_]+)(<.*>)?");
				Matcher matcher = pattern.matcher(request);
				if (matcher.find())
				{
				    request = extract(request, matcher.end(), 3);
				} else {
					return JSONManager.error();
				}

				if (request == null) {
					return JSONManager.error();
				}
			}
			
			if ( type == dev.PrimaryRegexp.type.PARSE_ERROR ) {
				//System.out.println("parse error");
				return json = JSONManager.error();
			}
		}
		
		json = JSONManager.request();
		return json;
	}
	
	public static void main (String [] args) {
		
		String request = /*args[0];*/ mRequest;
		//System.out.println("request: " + request);		
		
		JSONObject obj = analyze(request);
		
//		System.out.println("");
//		System.out.println("formed json:");
		System.out.println(obj);
//		
//		System.out.println("");
//		System.out.println("name: " + name);
//		System.out.println("generic: " + genericList);
//		System.out.println("lifetime: " + lifetimesList);
//		System.out.println("signature: " + signatureList);
//		System.out.println("output: " + output);		
	}
}

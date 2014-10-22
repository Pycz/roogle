package dev;

public class PrimaryRegexp {
	
	public static String nameRegex = "[ \t]*[a-z_0-9]+.*";
	private static String genericRegex = "[ \t]*<.*>.*"; 
	private static String signatureRegex = "[ \t]*[(].*[)].*";
	public static String outputRegex = "[ \t]*-[ \t]*>[ \t]*[a-zA-Z0-9_]*.*";
	
	public enum type {NAME, GENERIC, SIGNATURE, OUTPUT, PARSE_ERROR}
	
	public PrimaryRegexp() {
	}
	
	public static PrimaryRegexp.type type(String request) {
		
		if (request.matches("[ \t]*[a-z]+:.*")) {
			return type.PARSE_ERROR;
		}
		
		if (request.matches(nameRegex)) {
			return type.NAME;
		}
		
		if (request.matches(genericRegex)) {
			return type.GENERIC;
		}
		
		if (request.matches(signatureRegex)) {
			return type.SIGNATURE;
		}
		
		if (request.matches(outputRegex)) {
			return type.OUTPUT;
		}
		
		return type.PARSE_ERROR;
	}
}

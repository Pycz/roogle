package dev;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzeType {
	
 	private static String typeNameRegex = "([A-Z][a-z0-9_]*)+[ \t]*";
	private static String unit = "[ \t]*[(].*[)][ \t]*";
	private static String typeRegex = "([A-Z][a-z0-9_]+)+[ \t]*(<.*>)?"; 
	
	private static boolean typeExpected;
		
	private static int saveTypeName(String str, Type type) {
		Pattern pattern = Pattern.compile(typeNameRegex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find(0)) {
			type.setTypeName(str.substring(0, matcher.end()));
			return matcher.end();
		}
		return 0;
	}
	
	private static boolean saveTypeArr(String str, Type type) {
		
		typeExpected = true;
		
		/* remove < */
		str = str.substring(str.indexOf('<') + 1);
		
		Pattern namePattern = Pattern.compile(typeRegex);		
		Pattern unitPattern = Pattern.compile(unit);	
		Matcher nameMatcher = namePattern.matcher(str);
		Matcher unitMatcher = unitPattern.matcher(str);
		
		ArrayList<Type> list = new ArrayList<Type>();
		
		while (!str.equals(">")) {
			
			if (nameMatcher.find(0)) {
				if (nameMatcher.end() <= str.length()) {
					list.add(new Type(str.substring(0, nameMatcher.end())));
					str = str.substring(nameMatcher.end());
					typeExpected = false;
				}				
			} 
//			else if (unitMatcher.find()) {
//				list.add(new Type(str.substring(0, nameMatcher.end())));
//				str = str.substring(unitMatcher.end());
//				typeExpected = true;
//				
//			} 
			else {
				return false;
			}
			if (str.indexOf(',') != -1) {
				str = str.substring(str.indexOf(',') + 1);
				while(str.indexOf(' ') == 0) {
					str = str.substring(str.indexOf(' ') + 1);
				}
				typeExpected = true;
			}
		}
		
		if (typeExpected)
			return false;
		
		type.setTypeList(list);
		return true;
	}
	
	/**
	 * return empty outputName if error
	 */
	public static Type parseType(String request, Type type) {

		Pattern pattern = Pattern.compile(typeRegex);
		Matcher matcher = pattern.matcher(request);
		int pos = 0;
		
		if (matcher.matches()) {
			pos = saveTypeName(request, type);
			if ( pos > 0 ) {
				request = request.substring(pos);
				/* parse <> */
				if (!request.isEmpty()) {
					if (!saveTypeArr(request, type)){
						type.setTypeName("");
					}
				}
			}			
		}
		else {
			type.setTypeName("");
		}
		return type;
	}	
	
	public static class Type {
		
		private String typeName = "";
		private ArrayList<Type> types = new ArrayList<Type>();
		
		public Type() {};
		public Type(String name) {typeName = name;}
		
		public String getTypeName() {
			return typeName;
		}
		
		public ArrayList<Type> getTypeList() {
			return types;
		}
		
		public ArrayList<String> getTypeListString() {
			ArrayList<String> arr = new ArrayList<String>();
			for (int i = 0; i < types.size(); i++) {
				arr.add(types.get(i).getTypeName());
			}
			return arr;
		}
		
		public void setTypeName(String val) {
			typeName = val;
		}
		
		public void setTypeList(ArrayList<Type> list) {
			types = list;
		}
		
		public boolean isEmpty() {
			if (typeName.isEmpty())
				return true;
			return false;
		}
	}
	
}

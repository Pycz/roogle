package dev;

import java.util.LinkedList;
import java.util.regex.Pattern;

import org.hamcrest.Matcher;


public class AnalyzeGeneric {
	/**
	 * 
	 * @param generic
	 * @return LinkedList - [ [lifetimesList], [[type1, [GenericsList]], [type2, [GenericsList2]] ]], null mean error
	 */
	static Pattern allow = Pattern.compile("[a-zA-Z_0-9]+");
	public static LinkedList<LinkedList> parseGeneric(String generic) {
		LinkedList<LinkedList> result = new LinkedList();
		LinkedList<LinkedList> genericList = new LinkedList();
		LinkedList lifetimes = new LinkedList();
		
		generic = generic.replaceAll(" ", "");
		
		if (generic.contains("+>") || generic.contains("<+")){
			return null;
		}
		
		generic = generic.substring(1, generic.length()-1);
		if (!generic.equals("")){
			String[] genericSplitByComa = generic.split(",");
			for (String s: genericSplitByComa){
				try {
					LinkedList resultFromAnalyzeOneGeneric = AnalyzeOneGenericRequest(s, lifetimes);
					if (resultFromAnalyzeOneGeneric != null){
						genericList.add(resultFromAnalyzeOneGeneric);
					}
				}
				catch(Exception e){
					return null;					
				}
			}
		} else { return null;}
		result.add(lifetimes);
		result.add(genericList);
		return result; 
	}
	
	private static LinkedList AnalyzeOneGenericRequest(String oneGeneric,LinkedList lifetimes) throws Exception{
		LinkedList typeAndGenerics = new LinkedList();
		
		try{
			String[] oneGenericSplitByColon = oneGeneric.split(":");
			String type = oneGenericSplitByColon[0];
			//type = type.replaceAll(" ", "");
			Pattern p = Pattern.compile("[A-Z]");
			String firstLetter = type.substring(0, 1);
			java.util.regex.Matcher isLetter = p.matcher(firstLetter);
			java.util.regex.Matcher isAllow = allow.matcher(type);
			if (!(firstLetter.toUpperCase()).equals(firstLetter) || !isLetter.matches() || !isAllow.matches()) { 
				throw new Exception();
			}
			typeAndGenerics.add(type);
			typeAndGenerics.add(AnalyzeGenericsInOneGenericRequest(oneGenericSplitByColon[1]));
		}
		catch(Exception e){
			//oneGeneric = oneGeneric.replaceAll(" ", "");
			String first = oneGeneric.substring(0, 1);
			String anotherWord = oneGeneric.substring(1, oneGeneric.length());
			Pattern allowLifetime = Pattern.compile("[a-z][a-zA-Z_0-9]*");
			java.util.regex.Matcher isAllow = allowLifetime.matcher(anotherWord);
			if (!first.equals("'") || anotherWord.contains("'") || !isAllow.matches()){
				throw new Exception();
			}
			lifetimes.add(oneGeneric+"'");
			return null;
		}
		return typeAndGenerics;
	}
	
	private static LinkedList AnalyzeGenericsInOneGenericRequest(String generics) throws Exception{
		LinkedList genericsList = new LinkedList();
				
		String[] genericsByPlus = generics.split("\\+");
		for (String generic : genericsByPlus){
			//generic = generic.replaceAll(" ","");
			Pattern p = Pattern.compile("[A-Z]");
			String firstLetter = generic.substring(0, 1);
			java.util.regex.Matcher isLetter = p.matcher(firstLetter);
			java.util.regex.Matcher isAllow = allow.matcher(generic);			
			if (!(firstLetter.toUpperCase()).equals(firstLetter) || (!isLetter.matches()|| !isAllow.matches())) {
				throw new Exception();
			}
			genericsList.add(generic);
		}
		return genericsList;
	}	
}

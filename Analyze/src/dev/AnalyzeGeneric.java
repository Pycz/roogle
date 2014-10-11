package dev;

import java.util.LinkedList;


public class AnalyzeGeneric {
	/**
	 * 
	 * @param generic
	 * @return LinkedList of [type [GenericsList]], null mean error
	 */
	public static LinkedList<LinkedList> parseGeneric(String generic){ 
		LinkedList<LinkedList> genericList = new LinkedList();
		
		generic = generic.substring(1, generic.length()-1);
		if (!generic.equals("")){
			String[] genericSplitByComa = generic.split(",");
			for (String s: genericSplitByComa){
				//if (s.charAt(0) == '`') {}   --- 'a  ask Marsel about it
				try {
					genericList.add(AnalyzeOneGenericRequest(s));
				}
				catch(Exception e){
					System.out.println("exception");
					return null;					
				}
			}
		} else { return null;}
		return genericList; 
	}
	
	private static LinkedList AnalyzeOneGenericRequest(String oneGeneric) throws Exception{
		LinkedList typeAndGenerics = new LinkedList();
		
		String[] oneGenericSplitByColon = oneGeneric.split(":");
		String type = oneGenericSplitByColon[0];
		type = type.replaceAll(" ", "");
		if (!type.toUpperCase().equals(type)) { throw new Exception();}
		typeAndGenerics.add(type);
		typeAndGenerics.add(AnalyzeGenericsInOneGenericRequest(oneGenericSplitByColon[1]));
		return typeAndGenerics;
	}
	
	private static LinkedList AnalyzeGenericsInOneGenericRequest(String generics){
		LinkedList genericsList = new LinkedList();
		
		String[] genericsByPlus = generics.split("\\+");
		for (String generic : genericsByPlus){
			generic = generic.replaceAll(" ","");
			genericsList.add(generic);
		}
		return genericsList;
	}	
}

package dev;

import java.util.LinkedList;


public class AnalyzeGeneric {
	/**
	 * 
	 * @param generic
	 * @return LinkedList of [type [GenericsList]]
	 */
	public static LinkedList<LinkedList> parseGeneric(String generic){ 
		LinkedList<LinkedList> genericList = new LinkedList();
		
		generic = generic.substring(1, generic.length()-1);
		if (!generic.equals("")){
			String[] genericSplitByComa = generic.split(",");
			for (String s: genericSplitByComa){
				genericList.add(AnalyzeOneGenericRequest(s));
			}
		}
		return genericList; 
	}
	
	private static LinkedList AnalyzeOneGenericRequest(String oneGeneric){
		LinkedList typeAndGenerics = new LinkedList();
		
		String[] oneGenericSplitByColon = oneGeneric.split(":");
		String type = oneGenericSplitByColon[0];
		type = type.replaceAll(" ", "");
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

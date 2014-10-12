package dev;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class AnalyzeSignature {
	/**
	 * 
	 * @param signature
	 * @return LinkedList - [[var, Type], [var2, Type2], ...], null mean error 
	 */
	public static LinkedList parseSignature(String signature){
		LinkedList signatureList = new LinkedList();
		
		signature = signature.substring(1, signature.length()-1);
		if (!signature.equals("")){
			String[] signatureSplitByComa = signature.split(",");
			for (String el: signatureSplitByComa){
				try{
					signatureList.add(AnalyzeOneSignatureRequest(el));
				}
				catch(Exception e){
					return null;
				}
			}
		}else {return null;}
		return signatureList;
	}
	
	private static LinkedList AnalyzeOneSignatureRequest(String oneSignature) throws Exception{
		LinkedList varAndType = new LinkedList();
		
		String[] oneSignatureSplitByColon = oneSignature.split(":");
		String var = oneSignatureSplitByColon[0];
		var = var.replaceAll(" ", "");
		String type = oneSignatureSplitByColon[1];
		type = type.replaceAll(" ", "");
		Pattern patternForVar = Pattern.compile("[A-Z]");
		String varFirstSing = var.substring(0, 1);
		java.util.regex.Matcher isLetter = patternForVar.matcher(varFirstSing.toUpperCase());
		if (!(varFirstSing.toLowerCase()).equals(varFirstSing) || !isLetter.matches()){
			throw new Exception();
		}
		varAndType.add(var);
		String typeFirstSing = type.substring(0, 1);
		isLetter = patternForVar.matcher(typeFirstSing.toUpperCase());
		if (!(typeFirstSing.toUpperCase()).equals(typeFirstSing) || !isLetter.matches()){
			throw new Exception();
		}
		varAndType.add(type);
		return varAndType;
	}
}

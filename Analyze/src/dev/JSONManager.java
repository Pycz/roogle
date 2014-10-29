package dev;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONManager {
	
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @return parse error
	 */
	public static JSONObject error () {
		
		JSONObject errorObject = new JSONObject();
		errorObject.put("error", "parse error");
		return errorObject;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * send to search
	 * @return object with extracted parameters from user request
	 */
	public static JSONObject request() {
		
		JSONObject requestJson = new JSONObject();
		
		if (!AnalyzeRequest.name().equals("")) {
			requestJson.put("name", AnalyzeRequest.name());
		}
		if ( !AnalyzeRequest.genericList.isEmpty()) {
			JSONArray genArray = new JSONArray();
			genArray.addAll(AnalyzeRequest.genericList);
			
			requestJson.put("generic", genArray);
		}
		if ( !AnalyzeRequest.lifetimesList.isEmpty()) {
			JSONArray lifetimeArray = new JSONArray();
			lifetimeArray.addAll(AnalyzeRequest.lifetimesList);
			
			requestJson.put("lifetime", lifetimeArray);
		}
		if ( !AnalyzeRequest.signatureList.isEmpty()) {
			JSONArray signatureArray = new JSONArray();
			signatureArray.addAll(AnalyzeRequest.signatureList);
			
			requestJson.put("signature", signatureArray);
		}
		if ( !AnalyzeRequest.output.isEmpty() ) {	
			JSONObject outputObj = new JSONObject();
			outputObj.put("name", AnalyzeRequest.output.getTypeName());
			
			JSONArray outArray = new JSONArray();
			if (!AnalyzeRequest.output.getTypeListString().isEmpty()) {
				outArray.addAll(AnalyzeRequest.output.getTypeListString());
				outputObj.put("types", outArray);
			}
			
			requestJson.put("output", outputObj);
		}
		
		return requestJson;
	}

}

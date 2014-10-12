package dev;

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
		if ( !AnalyzeRequest.genericList.isEmpty() ) {
			JSONArray genArray = new JSONArray();
			genArray.addAll(AnalyzeRequest.genericList);
			
			requestJson.put("generic", genArray);
		}
		if (!AnalyzeRequest.output().equals("")) {	
			requestJson.put("output", AnalyzeRequest.output());
		}
		
		return requestJson;
	}

}

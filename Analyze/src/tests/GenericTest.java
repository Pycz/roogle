package tests;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;
import dev.AnalyzeGeneric;

public class GenericTest {
	
	@Test
	public void emptyGenric(){
		String request = "<>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
	}
	
	@Test
	public void oneGenric(){
		String request = "map<A: Type1>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		//expected.put("error", "parse error");
		assertEquals(expected, actual);
	}
	
}

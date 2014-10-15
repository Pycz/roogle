package tests;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;

public class GenericTest {
	
			
	@Test
	public void emptyGenric(){
		String request = "<>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void sipmeTest() {

		JSONObject actual = AnalyzeRequest.analyze("<G1: Type>");
		JSONObject expected = new JSONObject();
		LinkedList generic = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("G1");
		LinkedList gen = new LinkedList();
		gen.add("Type");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		expected.put("generic", generic);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}

	
	@Test
	public void simpleGeneric(){
		String request = "<A: Type>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
}

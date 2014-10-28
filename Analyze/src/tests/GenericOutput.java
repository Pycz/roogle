package tests;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;
import dev.AnalyzeGeneric;


public class GenericOutput {
	@Test
	public void emptySignature(){
		AnalyzeRequest.clean();
		String request = "<>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void WrongOutput1(){
		AnalyzeRequest.clean();
		String request = "<Type: G1>>Type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void WrongOutput2(){
		AnalyzeRequest.clean();
		String request = "<Type: G1>-Type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void wrongGenericType(){
		AnalyzeRequest.clean();
		String request = "<type: G1>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void wrongGeneric(){
		AnalyzeRequest.clean();
		String request = "<Type: g1>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void wrongGenericType1(){
		AnalyzeRequest.clean();   // error ' in front of Type
		String request = "<'Type: G1>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void wrongLifetime(){
		AnalyzeRequest.clean();
		String request = "<''a,Type: G1>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void wrongLifetime1(){
		AnalyzeRequest.clean();
		String request = "<'a',Type: g1>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void wrongLifetime2(){
		AnalyzeRequest.clean();
		String request = "<'a,',Type: g1>->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	@Test
	public void simpleGeneric(){
		AnalyzeRequest.clean();
		String request = "<Type: G1> -> sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList expectedGenericList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		LinkedList varAndType1 = new LinkedList();
		varAndType.add("Type");
		varAndType1.add("G1");
		varAndType.add(varAndType1);
		expectedGenericList.add(varAndType);
		expected.put("output", "sometype");
		expected.put("generic", expectedGenericList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void outputUpperCase(){
		AnalyzeRequest.clean();
		String request = "<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>->Sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList expectedGenericList = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList varType = new LinkedList();
		LinkedList varType1 = new LinkedList();
		LinkedList varAndType = new LinkedList();
		LinkedList varAndType1 = new LinkedList();
		
		varAndType.add("G1");
		varAndType.add("G2");
		varAndType.add("G3");
		varType.add("Type1");
		varType.add(varAndType);
		
		varAndType1.add("G2");
		varAndType1.add("G4");
		varAndType1.add("G5");
		varType1.add("Type2");
		varType1.add(varAndType1);
		
		expectedGenericList.add(varType);
		expectedGenericList.add(varType1);
		
		lifetime.add("'a'");
		lifetime.add("'b'");
		
		expected.put("output", "Sometype");
		expected.put("generic", expectedGenericList);
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	

}

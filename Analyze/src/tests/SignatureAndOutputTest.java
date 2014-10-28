package tests;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;


public class SignatureAndOutputTest {

	@Test
	public void emptySignature(){
		AnalyzeRequest.clean();
		String request = "()->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void simpleSignature(){
		AnalyzeRequest.clean();
		String request = "(a: Type) -> sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList expectedSignatureList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		varAndType.add("a");
		varAndType.add("Type");
		expectedSignatureList.add(varAndType);
		expected.put("output", "sometype");
		expected.put("signature", expectedSignatureList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void lowCaseTypeSignature(){
		AnalyzeRequest.clean();
		String request = "(a: type)->sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void uppedCaseVarSignature(){
		AnalyzeRequest.clean();
		String request = "(A: Type)->sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void wrongSignature(){
		AnalyzeRequest.clean();
		String request = "(A: type)->sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void twoSignatures(){
		AnalyzeRequest.clean();
		String request = "(a: Type, b:Type2)->sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList expectedSignatureList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		varAndType.add("a");
		varAndType.add("Type");
		expectedSignatureList.add(varAndType);
		varAndType = new LinkedList();
		varAndType.add("b");
		varAndType.add("Type2");
		expectedSignatureList.add(varAndType);
		expected.put("output", "sometype");
		expected.put("signature", expectedSignatureList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void ouputError(){
		AnalyzeRequest.clean();
		String request = "(a: Type, b:Type2)-sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void ouputanotherError(){
		AnalyzeRequest.clean();
		String request = "(a: Type, b:Type2)>sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void outputUpperCase(){
		AnalyzeRequest.clean();
		String request = "(a: Type, b:Type2)->Sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList expectedSignatureList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		varAndType.add("a");
		varAndType.add("Type");
		expectedSignatureList.add(varAndType);
		varAndType = new LinkedList();
		varAndType.add("b");
		varAndType.add("Type2");
		expectedSignatureList.add(varAndType);
		expected.put("output", "Sometype");
		expected.put("signature", expectedSignatureList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
}

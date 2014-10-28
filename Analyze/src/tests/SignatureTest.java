package tests;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeGeneric;
import dev.AnalyzeRequest;
import dev.AnalyzeSignature;
import static org.junit.Assert.*;


public class SignatureTest {

	@Test
	public void emptySignature(){
		AnalyzeRequest.clean();
		String request = "()";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void simpleSignature(){
		AnalyzeRequest.clean();
		String request = "(a: Type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList expectedSignatureList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		varAndType.add("a");
		varAndType.add("Type");
		expectedSignatureList.add(varAndType);
		expected.put("signature", expectedSignatureList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void lowCaseTypeSignature(){
		AnalyzeRequest.clean();
		String request = "(a: type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void uppedCaseVarSignature(){
		AnalyzeRequest.clean();
		String request = "(A: Type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void wrongSignature(){
		AnalyzeRequest.clean();
		String request = "(A: type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void twoSignatures(){
		AnalyzeRequest.clean();
		String request = "(a: Type, b:Type2)";
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
		expected.put("signature", expectedSignatureList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
}

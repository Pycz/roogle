package tests;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeGeneric;
import dev.AnalyzeRequest;
import dev.AnalyzeSignature;
import static org.junit.Assert.*;


public class NameAndSignatureTest {
	
	@Test
	public void wrongName(){
		AnalyzeRequest.clean();
		String request = "Map(a: Type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void wrongSignatureUpVar(){
		AnalyzeRequest.clean();
		String request = "map(A: Type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void wrongSignatureLowType(){
		AnalyzeRequest.clean();
		String request = "map(a: type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void rightNameAndSignature(){
		AnalyzeRequest.clean();
		String request = "map(a: Type)";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("name", "map");
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
	public void twoSignatures(){
		AnalyzeRequest.clean();
		String request = "zip(a: Type, b:Type2)";
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
		expected.put("name", "zip");
		expected.put("signature", expectedSignatureList);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
}

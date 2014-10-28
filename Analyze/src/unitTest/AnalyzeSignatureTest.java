package unitTest;

import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.*;
import dev.AnalyzeSignature;

public class AnalyzeSignatureTest {

	@Test
	public void emptySignature(){
		String request = "()";
		LinkedList expectedSignatureList = null;
		assertEquals(null , AnalyzeSignature.parseSignature(request));
	}
	
	@Test
	public void simpleSignature(){
		String request = "(a: Type)";
		LinkedList expectedSignatureList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		varAndType.add("a");
		varAndType.add("Type");
		expectedSignatureList.add(varAndType);
		assertEquals(expectedSignatureList , AnalyzeSignature.parseSignature(request));
	}
	
	@Test
	public void lowCaseTypeSignature(){
		String request = "(a: type)";
		LinkedList expectedSignatureList = null;
		assertEquals(expectedSignatureList , AnalyzeSignature.parseSignature(request));
	}
	
	@Test
	public void uppedCaseVarSignature(){
		String request = "(A: Type)";
		LinkedList expectedSignatureList = null;
		assertEquals(expectedSignatureList , AnalyzeSignature.parseSignature(request));
	}
	
	@Test
	public void wrongSignature(){
		String request = "(A: type)";
		LinkedList expectedSignatureList = null;
		assertEquals(expectedSignatureList , AnalyzeSignature.parseSignature(request));
	}
	
	@Test
	public void twoSignature(){
		String request = "(a: Type, b:Type2)";
		LinkedList expectedSignatureList = new LinkedList();
		LinkedList varAndType = new LinkedList();
		varAndType.add("a");
		varAndType.add("Type");
		expectedSignatureList.add(varAndType);
		varAndType = new LinkedList();
		varAndType.add("b");
		varAndType.add("Type2");
		expectedSignatureList.add(varAndType);
		assertEquals(expectedSignatureList , AnalyzeSignature.parseSignature(request));
	}
}

package tests;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeGeneric;
import dev.AnalyzeRequest;
import static org.junit.Assert.*;

public class GenericTest {
	
			
	@Test
	public void emptyGenric(){
		AnalyzeRequest.clean();
		String request = "<>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void sipmleTest() {
		AnalyzeRequest.clean();
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
	public void lowCaseType() {
		AnalyzeRequest.clean();
		JSONObject actual = AnalyzeRequest.analyze("<g1: Type>");
		JSONObject expected = new JSONObject();
		LinkedList generic = new LinkedList();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}

	
	@Test
	public void oneTypeTwoGeneric(){
		AnalyzeRequest.clean();
		String request = "<Type: G1 + G2>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList generic = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		expected.put("generic", generic);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void twoTypeThreeGeneric(){
		AnalyzeRequest.clean();
		String request = "<Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList generic = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type1");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		gen.add("G3");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		LinkedList typeAndGen1 = new LinkedList();
		typeAndGen1.add("Type2");
		LinkedList gen1 = new LinkedList();
		gen1.add("G2");
		gen1.add("G4");
		gen1.add("G5");
		typeAndGen1.add(gen1);
		generic.add(typeAndGen1);
		expected.put("generic", generic);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void lifeTime(){
		AnalyzeRequest.clean();
		String request = "<'a>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList lifetime = new LinkedList();
		lifetime.add("'a'");
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}	
	
	
	@Test
	public void morelifeTime(){
		AnalyzeRequest.clean();
		String request = "<'a, 'b>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList lifetime = new LinkedList();
		lifetime.add("'a'");
		lifetime.add("'b'");
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();		
	}
	
	
	public void twoTypeThreeAndLifetimeGeneric(){
		AnalyzeRequest.clean();
		String request = "<'a,Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type1");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		gen.add("G3");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		LinkedList typeAndGen1 = new LinkedList();
		typeAndGen1.add("Type2");
		LinkedList gen1 = new LinkedList();
		gen1.add("G2");
		gen1.add("G4");
		gen1.add("G5");
		typeAndGen1.add(gen1);
		generic.add(typeAndGen1);
		lifetime.add("'a'");
		expected.put("generic", generic);
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
	
	public void twoTypeThreeAndTwoLifetimeGeneric(){
		AnalyzeRequest.clean();
		String request = "<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type1");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		gen.add("G3");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		LinkedList typeAndGen1 = new LinkedList();
		typeAndGen1.add("Type2");
		LinkedList gen1 = new LinkedList();
		gen1.add("G2");
		gen1.add("G4");
		gen1.add("G5");
		typeAndGen1.add(gen1);
		generic.add(typeAndGen1);
		lifetime.add("'a'");
		lifetime.add("'b'");
		expected.put("generic", generic);
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
	}
}

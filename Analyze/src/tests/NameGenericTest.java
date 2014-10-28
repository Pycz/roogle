package tests;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;
import dev.AnalyzeGeneric;

public class NameGenericTest {
	
	@Test
	public void errorEmptyBracers(){
		AnalyzeRequest.clean();
		String request = "map<>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error","parse error");
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}
	@Test
	public void nameGenericTest(){
		AnalyzeRequest.clean();
		String request = "map<A: Tojson>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("name", "map");
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList expectedGenericsList2 = new LinkedList();
		LinkedList generic = new LinkedList();
		
		generic.add("A");
		expectedGenericsList2.add("Tojson");
		generic.add(expectedGenericsList2);
		expectedGenericsList.add(generic);
		
		
		expected.put("generic",expectedGenericsList);
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void WrongNameGenericTest(){
		AnalyzeRequest.clean();
		String request = "Map<A: Tojson>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error","parse error");
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}

	@Test
	public void nameWrongTypeGenericTest(){
		AnalyzeRequest.clean();
		String request = "map<a: Tojson>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error","parse error");
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void nameWrongType2GenericTest(){
		AnalyzeRequest.clean();
		String request = "map<'Al: Tojson>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error","parse error");
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}

	@Test
	public void nameTypeWrongGeneric2Test(){
		AnalyzeRequest.clean();
		String request = "map<A: 'Tojson>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error","parse error");
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}
	
	
	@Test
	public void nameTypeWrongGenericTest(){
		AnalyzeRequest.clean();
		String request = "map<A: tojson>";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("error","parse error");
		assertEquals(expected,actual);
		AnalyzeRequest.clean();
	}
	
	@Test
	public void oneTypeTwoGeneric(){
	AnalyzeRequest.clean();
	String request = "map<Type: G1 + G2>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	expected.put("name", "map");
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
	String request = "map<Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	expected.put("name", "map");
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
	String request = "map<'a>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	LinkedList lifetime = new LinkedList();
	expected.put("name", "map");
	lifetime.add("'a'");
	expected.put("lifetime", lifetime);
	assertEquals(expected, actual);
	AnalyzeRequest.clean();
	}	
	
	@Test
	public void WronglifeTime(){
	AnalyzeRequest.clean();
	String request = "map<''a>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	expected.put("error","parse error");
	assertEquals(expected,actual);
	AnalyzeRequest.clean();
}
	
	@Test
	public void morelifeTime(){
	AnalyzeRequest.clean();
	String request = "map<'a, 'b>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	LinkedList lifetime = new LinkedList();
	expected.put("name", "map");
	lifetime.add("'a'");
	lifetime.add("'b'");
	expected.put("lifetime", lifetime);
	assertEquals(expected, actual);
	AnalyzeRequest.clean();	
	}
	public void twoTypeThreeAndLifetimeGeneric(){
	AnalyzeRequest.clean();
	String request = "map<'a,Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	expected.put("name", "map");
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
	String request = "map<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
	JSONObject actual = AnalyzeRequest.analyze(request);
	JSONObject expected = new JSONObject();
	expected.put("name", "map");
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

package unitTest;

import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.*;
import dev.AnalyzeGeneric;

public class AnalyzeGenericTest {

	@Test
	public void emptyRequest(){
		String request = "<>";
		LinkedList expectedGenericsList = null;
		assertEquals(null , AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void commaInsteadPlus(){
		String request = "< A: G1, G2 >";
		assertEquals(null, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void lowRegisterType(){
		String request = "< a: G1 + G2 >";
		assertEquals(null, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void oneTypeOneGeneric(){
		String request = "< A: G1 >";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("A");
		LinkedList gen = new LinkedList();
		String G1 = "G1";
		G1.replaceAll(" ", "");
		gen.add(G1);
		typeAndGen.add(gen);
		expectedGenericsList.add(typeAndGen);
		
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void oneTypeTwoGeneric(){
		String request = "<A: G1 + G2>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("A");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		typeAndGen.add(gen);
		expectedGenericsList.add(typeAndGen);
		
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void oneTypeThreeGeneric(){
		String request = "<A: G1 + G2+G3>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("A");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		gen.add("G3");
		typeAndGen.add(gen);
		expectedGenericsList.add(typeAndGen);
		
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void twoTypeThreeGeneric(){
		String request = "<A: G1 + G2+G3, B: G2+ G4+G5>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("A");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		gen.add("G3");
		typeAndGen.add(gen);
		expectedGenericsList.add(typeAndGen);
		LinkedList typeAndGen1 = new LinkedList();
		typeAndGen1.add("B");
		LinkedList gen1 = new LinkedList();
		gen1.add("G2");
		gen1.add("G4");
		gen1.add("G5");
		typeAndGen1.add(gen1);
		expectedGenericsList.add(typeAndGen1);
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
}

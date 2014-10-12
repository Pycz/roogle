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
	public void firstSignInTypeNotLetter(){
		String request = "< 1A: G1, G2 >";
		assertEquals(null, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void lowRegisterType(){
		String request = "< a: G1 + G2 >";
		assertEquals(null, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void oneTypeOneGeneric(){
		String request = "< Type: G1 >";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type");
		LinkedList gen = new LinkedList();
		String G1 = "G1";
		G1.replaceAll(" ", "");
		gen.add(G1);
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void oneTypeTwoGeneric(){
		String request = "<Type: G1 + G2>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void oneTypeThreeGeneric(){
		String request = "<Type: G1 + G2+G3>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		typeAndGen.add("Type");
		LinkedList gen = new LinkedList();
		gen.add("G1");
		gen.add("G2");
		gen.add("G3");
		typeAndGen.add(gen);
		generic.add(typeAndGen);
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void twoTypeThreeGeneric(){
		String request = "<Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
		LinkedList expectedGenericsList = new LinkedList();
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
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void lifeTime(){
		String request = "<'a>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		lifetime.add("'a'");
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	@Test
	public void morelifeTime(){
		String request = "<'a, 'b>";
		LinkedList expectedGenericsList = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		lifetime.add("'a'");
		lifetime.add("'b'");
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	public void twoTypeThreeAndLifetimeGeneric(){
		String request = "<'a,Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
		LinkedList expectedGenericsList = new LinkedList();
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
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
	
	public void twoTypeThreeAndFewLifetimeGeneric(){
		String request = "<'a,'b, Type1: G1 + G2+G3, Type2: G2+ G4+G5>";
		LinkedList expectedGenericsList = new LinkedList();
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
		expectedGenericsList.add(lifetime);
		expectedGenericsList.add(generic);
		assertEquals(expectedGenericsList, AnalyzeGeneric.parseGeneric(request));
	}
}

package tests;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;
import dev.AnalyzeGeneric;

public class GenericSignatureOutput {

	@Test
    public void EmptySignature() {
        AnalyzeRequest.clean();
        String request = "()<Type:G>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void EmptyGeneric() {
        AnalyzeRequest.clean();
        String request = "(War:Type)<>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongSignatureVar() {
        AnalyzeRequest.clean();
        String request = "(War:Type)<Type:G>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongSignatureType() {
        AnalyzeRequest.clean();
        String request = "(war:type)<Type:G>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongVarSignature() {
        AnalyzeRequest.clean();
        String request = "('war:Type)<Type:G>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongGeneric() {
        AnalyzeRequest.clean();
        String request = "(War:Type)<Type:'G>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongGenericType() {
        AnalyzeRequest.clean();
        String request = "(War:Type)<'Type:G>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongLifetime() {
        AnalyzeRequest.clean();
        String request = "(q:Type3)<''a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }

	@Test
    public void wrongLifetime1() {
        AnalyzeRequest.clean();
        String request = "(q:Type3)<'a, 'b',Type1: G1 + G2+G3, Type2: G2+ G4+G5>->sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
        
    }
	
	@Test
    public void wrongOutput1() {
        AnalyzeRequest.clean();
        String request = "(q:Type3)<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>>sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
    public void wrongOutput2() {
        AnalyzeRequest.clean();
        String request = "(q:Type3)<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>-sometype";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
	
	@Test
	public void twoTypeThreeAndTwoLifetimeGeneric(){
		AnalyzeRequest.clean();
		String request = "(q:Type3)<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>->sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList signature = new LinkedList();
		LinkedList sig = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		sig.add("q");
		sig.add("Type3");
		signature.add(sig);
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
		expected.put("output","sometype");
		expected.put("signature", signature);
		expected.put("generic", generic);
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
		}

	@Test
	public void twoTypeThreeAndTwoLifetimeGenericUpperCaseOutput(){
		AnalyzeRequest.clean();
		String request = "(q:Type3)<'a, 'b,Type1: G1 + G2+G3, Type2: G2+ G4+G5>->Sometype";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		LinkedList signature = new LinkedList();
		LinkedList sig = new LinkedList();
		LinkedList generic = new LinkedList();
		LinkedList lifetime = new LinkedList();
		LinkedList typeAndGen = new LinkedList();
		sig.add("q");
		sig.add("Type3");
		signature.add(sig);
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
		expected.put("output","Sometype");
		expected.put("signature", signature);
		expected.put("generic", generic);
		expected.put("lifetime", lifetime);
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
		}

	
}

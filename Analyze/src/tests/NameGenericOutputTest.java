/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dev.AnalyzeRequest;
import java.util.LinkedList;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Алсу
 */
public class NameGenericOutputTest {
    @Test
    public void allRight() {
        AnalyzeRequest.clean();
        String request = "map <Type:G> -> Type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("name", "map");
        LinkedList generic = new LinkedList();	
        LinkedList typeAndGen = new LinkedList();
        typeAndGen.add("Type");
        LinkedList gen = new LinkedList();
        gen.add("G");
        typeAndGen.add(gen);
        generic.add(typeAndGen);
        expected.put("generic", generic);
        expected.put("output", "Type");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrongGeneric() {
        AnalyzeRequest.clean();
        String request = "map<Type:G+>->Type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Test
    public void wrongOutput() {
        AnalyzeRequest.clean();
        String request = "map<Type:G>->type'";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Test
    public void wrongOutput2() {
        AnalyzeRequest.clean();
        String request = "map<Type:G>->type->type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Test
    public void wrongName() {
        AnalyzeRequest.clean();
        String request = "mAp<Type:G>->type->type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Test
    public void wrongName1() {
        AnalyzeRequest.clean();
        String request = "map+<Type:G>->type->type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
}

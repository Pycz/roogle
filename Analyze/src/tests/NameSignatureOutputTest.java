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
public class NameSignatureOutputTest {
    @Test
    public void allRight() {
        AnalyzeRequest.clean();
        String request = "map (a:Type) -> Type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("name", "map");
        LinkedList signature = new LinkedList();	
        LinkedList typeAndVar = new LinkedList(); 
        typeAndVar.add("a");
        typeAndVar.add("Type");
        signature.add(typeAndVar);
        expected.put("signature", signature);
        expected.put("output", "Type");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }   
     @Test
     public void right() {
        AnalyzeRequest.clean();
        String request = "map11(a:Type)->type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
         expected.put("name", "map11");
        LinkedList signature = new LinkedList();	
        LinkedList typeAndVar = new LinkedList(); 
        typeAndVar.add("a");
        typeAndVar.add("Type");
        signature.add(typeAndVar);
        expected.put("signature", signature);
         expected.put("output", "type");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrongSignatureAndOutput() {
        AnalyzeRequest.clean();
        String request = "map11(a:Type+)->tYpe";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
     public void wrongOutput() {
        AnalyzeRequest.clean();
        String request = "map(a:Type)->tYpe";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Test
    public void wrongOutput1() {
        AnalyzeRequest.clean();
        String request = "map11(a:Type)->tYPe";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
}

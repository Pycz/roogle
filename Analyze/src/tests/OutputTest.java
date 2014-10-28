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
public class OutputTest {
    @Test
    public void allRight() {
        AnalyzeRequest.clean();
        String request = "-> Type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("output", "Type");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
    @Test
    public void wrong() {
        AnalyzeRequest.clean();
        String request = "-> Type'";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrong1() {
        AnalyzeRequest.clean();
        String request = "-> TyPE";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }    
    
    @Test
    public void wrong2() {
        AnalyzeRequest.clean();
        String request = "-> Type,";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }  
    
    @Test
    public void wrong3() {
        AnalyzeRequest.clean();
        String request = "-> Type,Type";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }   
    


}

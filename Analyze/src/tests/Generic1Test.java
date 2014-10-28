/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dev.AnalyzeRequest;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Алсу
 */
public class Generic1Test {
    @Test
    public void wrong() {
        AnalyzeRequest.clean();
        String request = "<a':>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }  
    
    @Test
    public void wrong1() {
        AnalyzeRequest.clean();
        String request = "<a'>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
    @Ignore
    @Test
    public void wrong2() {
        AnalyzeRequest.clean();
        String request = "<'a,>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
    @Test
    public void wrong3() {
        AnalyzeRequest.clean();
        String request = "<'a,Type:>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
    @Ignore
    @Test
    public void wrong4() {
        AnalyzeRequest.clean();
        String request = "<'a,Type:G,>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
    @Test
    public void wrong5() {
        AnalyzeRequest.clean();
        String request = "<'a,Type:G+>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
    @Ignore
    @Test
    public void wrong6() {
        AnalyzeRequest.clean();
        String request = "<'a,Type:G+G1,>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
}

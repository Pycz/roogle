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
public class TypesTest {  
    @Ignore
    @Test
    public void wrong() {
        AnalyzeRequest.clean();
        String request = "-> Type<T>";
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
        String request = "-> Type<Type,>";
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
        String request = "-> Type<Type,Type,>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();        
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
}

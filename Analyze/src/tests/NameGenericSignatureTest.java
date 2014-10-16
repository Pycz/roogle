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
public class NameGenericSignatureTest { 
    @Ignore
    @Test
    public void wrongSignature1() {
        AnalyzeRequest.clean();
        String request = "map(war:Type)<Type:G+>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrongGeneric() {
        AnalyzeRequest.clean();
        String request = "map(a:Type)<'Alll'>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrongGeneric1() {
        AnalyzeRequest.clean();
        String request = "map(a:Type)<'Alll:G>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrongGeneric2() {
        AnalyzeRequest.clean();
        String request = "map(a:Type)<Type:'G>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    @Ignore
    @Test
    public void wrongSignature() {
        AnalyzeRequest.clean();
        String request = "map(war:Type,)<Type:G>";
        JSONObject actual = AnalyzeRequest.analyze(request);
        JSONObject expected = new JSONObject();
        expected.put("error", "parse error");
        assertEquals(expected, actual);
        AnalyzeRequest.clean();
    }
    
}

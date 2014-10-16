/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dev.AnalyzeRequest;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Алсу
 */
public class NameAndOutputTest {
    @Test
    public void RightOutputRightName(){
        AnalyzeRequest.clean();
		String request = "map->Type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("name", "map");
                expected.put("output", "Type");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
    }
    @Test
    public void RightOutputRightName2(){
        AnalyzeRequest.clean();
		String request = "map_t->Type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("name", "map_t");
                expected.put("output", "Type");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
    }
    @Test
    public void RightOutputRightName1(){
        AnalyzeRequest.clean();
		String request = "map->type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
		expected.put("name", "map");
                expected.put("output", "type");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
    }
    @Test
    public void RightOutputWrongName(){
        AnalyzeRequest.clean();
		String request = "Map ->Type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
                expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
    }
     @Test
    public void RightOutputWrongName1(){
        AnalyzeRequest.clean();
		String request = "MAP ->  Type";
		JSONObject actual = AnalyzeRequest.analyze(request);
		JSONObject expected = new JSONObject();
                expected.put("error", "parse error");
		assertEquals(expected, actual);
		AnalyzeRequest.clean();
    }     
}

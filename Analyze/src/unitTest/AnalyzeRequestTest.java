package unitTest;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.AnalyzeRequest;
import static org.junit.Assert.*;

public class AnalyzeRequestTest {
	
	@Test
	public void WrongNameTest() {

		JSONObject actual = AnalyzeRequest.analyze("Map");
		JSONObject expected = new JSONObject();
		expected.put("error", "parse error");
		assertEquals(expected, actual);
	}
	
	@Test
	public void NameTest() {
		
		JSONObject actual = AnalyzeRequest.analyze("map");
		JSONObject expected = new JSONObject();
		expected.put("name", "map");
		assertEquals(expected, actual);
	}

	
}

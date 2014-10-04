package unitTest;

import org.junit.Test;

import dev.PrimaryRegexp;

import static org.junit.Assert.*;

public class PrimaryRegexpTest {

	@Test
	public void nameTest() {
		PrimaryRegexp mreg = new PrimaryRegexp();
		PrimaryRegexp.type res = PrimaryRegexp.type("map");
		assertEquals(PrimaryRegexp.type.NAME, res);

		res = PrimaryRegexp.type(" 	map");
		assertEquals(PrimaryRegexp.type.NAME, res);
		
		res = PrimaryRegexp.type("map <> ()");
		assertEquals(PrimaryRegexp.type.NAME, res);
		
		res = PrimaryRegexp.type("to_json");
		assertEquals(PrimaryRegexp.type.NAME, res);
		
		res = PrimaryRegexp.type("to_json1");
		assertEquals(PrimaryRegexp.type.NAME, res);
		
		res = PrimaryRegexp.type("ma:p a");
		assertEquals(PrimaryRegexp.type.PARSE_ERROR, res); 
		/*
		 * двоеточие может быть только в генерике и сигнатуре, 
		 * возможно там просто забыли открыть скобку.
		 * хотя вообще это лишнее тут,
		 * пускай этот нейм распознается, а следующий шаблон гарантированно вернет parse_error
		 * потому что нет шаблона, начинающегося с двоеточия
		 */
		
		res = PrimaryRegexp.type("?lalala");
		assertEquals(PrimaryRegexp.type.PARSE_ERROR, res);
	}
	
	@Test
	public void genericTest() {
		PrimaryRegexp mreg = new PrimaryRegexp();
		PrimaryRegexp.type res = PrimaryRegexp.type("<T: Trait>");
		assertEquals(PrimaryRegexp.type.GENERIC, res);
		
		res = PrimaryRegexp.type("<>");
		assertEquals(PrimaryRegexp.type.GENERIC, res);
		
		res = PrimaryRegexp.type("<notgeneric: Trait1 + Trait2");
		assertEquals(PrimaryRegexp.type.PARSE_ERROR, res);
	}
	
	@Test
	public void signatureTest() {
		PrimaryRegexp mreg = new PrimaryRegexp();
		PrimaryRegexp.type res = PrimaryRegexp.type("(a: type1)");
		assertEquals(PrimaryRegexp.type.SIGNATURE, res);
		
		res = PrimaryRegexp.type("()");
		assertEquals(PrimaryRegexp.type.SIGNATURE, res);
		
		res = PrimaryRegexp.type("(a: type1, b: type2");
		assertEquals(PrimaryRegexp.type.PARSE_ERROR, res);
	}
	
	@Test
	public void outputTest() {
		PrimaryRegexp mreg = new PrimaryRegexp();
		PrimaryRegexp.type res = PrimaryRegexp.type("->type");
		assertEquals(PrimaryRegexp.type.OUTPUT, res);
		
		res = PrimaryRegexp.type(" - > type	");
		assertEquals(PrimaryRegexp.type.OUTPUT, res);
		
		res = PrimaryRegexp.type(" -> Type	");
		assertEquals(PrimaryRegexp.type.PARSE_ERROR, res);
	}
}

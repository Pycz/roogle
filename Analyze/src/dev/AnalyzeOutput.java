package dev;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.AnalyzeType.Type;

public class AnalyzeOutput {

	private static String arrow = "[ \t]*-[ \t]*>[ \t]*";
	//private static String lowerCaseTypeName = "[a-z0-9_]+";
	private static String upperCaseTypeName = "([A-Z][a-z0-9_]+)+";
	
	private static String extract (String str, int pos) {
		String tail;
		tail = str.substring(pos);
		return tail;
	}
	
	public static int check(String str, String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return  matcher.end();
		}
		else {
			return 0;
		}
	}
	
	public static Type parseOutput (String output) {
		
		/* сначала выкинуть стрелочку */
		Pattern pattern = Pattern.compile(arrow);
		Matcher matcher = pattern.matcher(output);
		if (matcher.find())
		{
			output = extract(output, matcher.end());
		}

		Type type = new Type();
		type = AnalyzeType.parseType(output, type);
		
//		int pos = check(output, upperCaseTypeName);
//		if ( pos > 0 ) {
//			return output.substring(0, pos);
//		}
		
		return type;
	}
}

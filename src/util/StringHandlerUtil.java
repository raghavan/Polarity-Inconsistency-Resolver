package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandlerUtil {

	public static List<String> getStringBetweenPattern(String text,String startPattern,String endPattern){
			List<Integer> positions = new ArrayList<Integer>();
			List<String> definitions = new ArrayList<String>();
			Pattern p = Pattern.compile(startPattern);
			Matcher m = p.matcher(text);
			while (m.find()) {
			   positions.add(m.start());
			}
			for(Integer pos : positions){
				Pattern endPointPattern = Pattern.compile(endPattern);	
				Matcher endPointMatcher =  endPointPattern.matcher(text.substring(pos+startPattern.length()));			
				if(endPointMatcher.find()){
					int endPoint =  endPointMatcher.start();
					String def = text.substring(pos+startPattern.length(),pos+startPattern.length()+endPoint);
					definitions.add(def);
				}
			}
			return definitions;

	}
	
	public static String makeLeftAlign(String input) {
		int width = 200;
		int pos = 0;
		StringBuilder sb = new StringBuilder();
		
		String[] words = input.split(" ");
		for (String w : words) {
			if (pos + w.length() > width) {
				sb.append('\n');
				pos = 0;
			}
			sb.append(w);
			sb.append(' ');
			pos += w.length() + 1;
			
		}
		return sb.toString();
	}
	
}

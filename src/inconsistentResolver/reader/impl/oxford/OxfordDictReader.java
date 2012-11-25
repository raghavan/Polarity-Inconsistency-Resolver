package inconsistentResolver.reader.impl.oxford;

import java.util.ArrayList;
import java.util.List;

import util.HttpHandlerUtil;
import util.StringHandlerUtil;

import inconsistentResolver.reader.IDictionaryReader;

public class OxfordDictReader implements IDictionaryReader {
	
	public static final String URL = "http://oxforddictionaries.com/definition/american_english/";
	public void printString(String value){
		System.out.println(value);
	}

	@Override
	public List<String> getMeaning(String word) {
		String html = HttpHandlerUtil.getHTML(URL+word);
		List<String> words = StringHandlerUtil.getStringBetweenPattern(html, "<span class=\"definition\">", "</span>");
		List<String> sensesToReturn = new ArrayList<String>();
		for(String str: words){
			if(!str.contains("<a"))
				sensesToReturn.add(str);
		}
		System.out.println("......");
		return sensesToReturn;
	}
}

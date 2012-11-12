package inconsistentResolver.reader.impl.cambridge;

import java.util.List;

import util.HttpHandlerUtil;
import util.StringHandlerUtil;

public class CambridgeApiHandler {

	public static final String CAMBRIGE_DICT_URL = "http://dictionary.cambridge.org/dictionary/american-english/";
	
	private static String findEntryUrlForWord(String word){
		String entryUrl = HttpHandlerUtil.getEntryUrlUsingApi(word);
		return entryUrl;
	}

	private static String getHtmlPageForEntry(String entry){
		String html = HttpHandlerUtil.getHTML(CAMBRIGE_DICT_URL+entry);
		return html;
	}
	
	public static List<String> getDefinitionsForWord(String word){
		String entryUrl = findEntryUrlForWord(word);
		String html = getHtmlPageForEntry(entryUrl);
		List<String> definitions = StringHandlerUtil.getStringBetweenPattern(html,"<span class=\"def\">","<");
		return definitions;
	}
	
}

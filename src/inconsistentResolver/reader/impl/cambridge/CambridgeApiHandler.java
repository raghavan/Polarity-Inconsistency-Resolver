package inconsistentResolver.reader.impl.cambridge;

import java.util.ArrayList;
import java.util.List;

import util.HttpHandlerUtil;
import util.StringHandlerUtil;

public class CambridgeApiHandler {

	public static final String CAMBRIGE_DICT_URL = "http://dictionary.cambridge.org/dictionary/american-english/";
	

	private static String getHtmlPageForEntry(String entry){
		String html = HttpHandlerUtil.getHTML(CAMBRIGE_DICT_URL+entry);
		return html;
	}
	
	public static List<String> getDefinitionsForWord(String word){
		List<String> definitions = new ArrayList<String>();
		/*String entryUrl = findEntryUrlForWord(word);
		String html = getHtmlPageForEntry(entryUrl);
		definitions = StringHandlerUtil.getStringBetweenPattern(html,"<span class=\"def\">","<");*/
		String html = HttpHandlerUtil.getResponse(word);
		definitions =  StringHandlerUtil.getStringBetweenPattern(html, "<def>", "<");
		return definitions;
	}
	
}

package inconsistentResolver.reader.impl.cambridge;

import java.util.ArrayList;
import java.util.List;

import util.HttpHandlerUtil;
import util.StringHandlerUtil;

public class CambridgeApiHandler {

	private static String getHtmlPageForEntry(String entryUrl){
		String html = HttpHandlerUtil.getHTML(entryUrl);
		return html;
	}
	
	public static List<String> getDefinitionsByUrl(String word){
		List<String> definitions = new ArrayList<String>();
		String jsonObject = HttpHandlerUtil.getResponse(word);
		String entryUrl = StringHandlerUtil.getStringBetweenPattern(jsonObject, "<x href=", "\\").get(0);
		String html = getHtmlPageForEntry(entryUrl);
		definitions = StringHandlerUtil.getStringBetweenPattern(html,"<span class=\"def\">","<");
		return definitions;
	}
	
	public static List<String> getDefinitionsFromJson(String word){
		List<String> definitions = new ArrayList<String>();
		String jsonObject = HttpHandlerUtil.getResponse(word);
		definitions =  StringHandlerUtil.getStringBetweenPattern(jsonObject, "<def>", "<");
		return definitions;
	}
	
}

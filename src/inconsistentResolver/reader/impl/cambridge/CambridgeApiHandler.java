package inconsistentResolver.reader.impl.cambridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import propertyHandler.PropertyHandlerImpl;

import util.Constants;
import util.HttpHandlerUtil;
import util.StringHandlerUtil;

public class CambridgeApiHandler {

	private static String getHtmlPageForEntry(String entryUrl){
		String html = HttpHandlerUtil.getHTML(entryUrl);
		return html;
	}
	public static final String URL = "http://dictionary.cambridge.org/dictionary/american-english/";
	public static Map<String,String> cambridgeWordMap = null;
	
	
	public static List<String> getDefinitionsByUrl(String word){
		if(cambridgeWordMap == null){
			cambridgeWordMap = PropertyHandlerImpl.readpropFile(Constants.EXISTING_INCONST_PROP_CAMBRIDGE);
		}
		List<String> definitions = new ArrayList<String>();
		String newWord = cambridgeWordMap.get(word);
		//String jsonObject = HttpHandlerUtil.getResponse(word);
		//String entryUrl = StringHandlerUtil.getStringBetweenPattern(jsonObject, "<x href=", "\\").get(0);
		//String html = getHtmlPageForEntry(entryUrl);
		String html = getHtmlPageForEntry(URL+newWord);
		definitions = StringHandlerUtil.getStringBetweenPattern(html,"<span class=\"def\">","<");
		return definitions;
	}
	
	public static List<String> getDefinitionsFromJson(String word){
		List<String> definitions = new ArrayList<String>();
		String jsonObject = HttpHandlerUtil.getResponse(word);
		System.out.println(jsonObject);
		definitions =  StringHandlerUtil.getStringBetweenPattern(jsonObject, "<def>", "<");
		return definitions;
	}
	
	public static void main(String args[]){
		Map<String,String> map = PropertyHandlerImpl.readpropFile(Constants.EXISTING_INCONST_PROP_CAMBRIDGE);
		for(String key : map.keySet()){
			System.out.println(key+"=");
		}
	}
	
	
	
}

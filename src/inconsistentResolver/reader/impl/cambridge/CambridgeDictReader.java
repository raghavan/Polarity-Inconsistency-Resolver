package inconsistentResolver.reader.impl.cambridge;

import java.util.ArrayList;
import java.util.List;

import inconsistentResolver.reader.IDictionaryReader;

public class CambridgeDictReader implements IDictionaryReader {
	
	public void printString(String value){
		System.out.println(value);
	}

	@Override
	public List<String> getMeaning(String word) {
		List<String> meanings = CambridgeApiHandler.getDefinitionsForWord(word);
		
		//find valid entry to search in api and get it
		//get the html page and get definitions from it
		return meanings;
	}
	
}

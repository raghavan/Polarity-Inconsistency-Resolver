package inconsistentResolver.reader.impl.cambridge;

import inconsistentResolver.reader.IDictionaryReader;

import java.util.List;

public class CambridgeDictReader implements IDictionaryReader {
	
	public void printString(String value){
		System.out.println(value);
	}

	@Override
	public List<String> getMeaning(String word) {
		List<String> meanings = CambridgeApiHandler.getDefinitionsByUrl(word); 
				//CambridgeApiHandler.getDefinitionsFromJson(word);
		return meanings;
	}
	
}

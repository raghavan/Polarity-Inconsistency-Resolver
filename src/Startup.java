import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;
import inconsistentResolver.reader.impl.oxford.OxfordDictReader;
import inconsistentResolver.userInteractor.IUserInteract;
import inconsistentResolver.userInteractor.impl.UserInteractImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import propertyHandler.PropertyHandlerImpl;
import util.Constants;
import util.DictionaryName;

public class Startup {
	public static void main(String args[]) {		
		Map<String,String> updatedWordPolarity = new HashMap<String,String>();
		
		Map<String,String> existingWordPolarity = PropertyHandlerImpl.readpropFile(Constants.fileName);
		existingWordPolarity.putAll(updatedWordPolarity);
		PropertyHandlerImpl.writePropertyFile("UpdatedWordPolarity.properties", existingWordPolarity);
	}
	
	public static void testDictReaders() {
		String word = "large";
		IDictionaryReader idictReader = new OxfordDictReader();
		List<String> meanings = idictReader.getMeaning(word);
		IUserInteract userinteractor = new UserInteractImpl();
		userinteractor.printMeanings(word, DictionaryName.OXFORD_DICTIONARY,
				meanings);

		idictReader = new CambridgeDictReader();
		meanings = idictReader.getMeaning(word);
		userinteractor.printMeanings(word, DictionaryName.CAMBRIDGE_DICTIONARY,
				meanings);
	}

	public static void testMapMerge() {
		final Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("1", "Hello");
		map1.put("2", "There");
		map1.put("3", "fine");
		final Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("2", "Sir");
		map2.put("3", "is");
		map2.put("4", "a");
		map2.put("5", "bird");
		printMap(map1);
		map1.putAll(map2);
		printMap(map1);
	}


	public static void printMap(Map<String, String> mp) {
		Iterator<Entry<String, String>> it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Entry<String, String>) it.next();
			System.out.println(pairs.getKey() + "=" + pairs.getValue());
		}
	}
}

import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;
import inconsistentResolver.reader.impl.oxford.OxfordDictReader;
import inconsistentResolver.userInteractor.IUserInteract;
import inconsistentResolver.userInteractor.impl.UserInteractImpl;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import propertyHandler.PropertyHandlerImpl;
import util.Constants;
import util.DictionaryName;
import util.Polarity;

public class Startup {
	public static void main(String args[]) {

		Map<String, String> inconsistentWordPolarity = PropertyHandlerImpl
				.getInconsistentWords();
		Map<String, String> updatedWordPolarity = new HashMap<String, String>();
		for (String key : inconsistentWordPolarity.keySet()) {
			boolean sensesObtained = useDictReaders(key);
			if (sensesObtained) {
				System.out.println("Inconsistent word /'" + key + "/' with polarity -> /'" + 
								inconsistentWordPolarity.get(key));
				System.out.println(" Update word polarity(Press 1-Positive/2-Negative/3-Neutral)");
				int a = 0;
				while (a == 0) {
					Scanner in = new Scanner(System.in);
					try {
						a = in.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Please enter values between 1 and 3");
						a=0;
					}
					if(!(a>=1&&a<=3)){
						System.out.println("Please enter values between 1 and 3");
						a=0;
					}
				}
				System.out.println("Word's polairty updated as = "+Polarity.fromValue(a)+"\n");
				updatedWordPolarity.put(key, Polarity.fromValue(a));
			}
		}

		Map<String, String> existingWordPolarity = PropertyHandlerImpl
				.readpropFile(Constants.WORD_POLARITY_SRC_FILENAME);
		existingWordPolarity.putAll(updatedWordPolarity);
		PropertyHandlerImpl.writePropertyFile("UpdatedWordPolarity.properties",
				existingWordPolarity);
	}

	public static boolean useDictReaders(String inconsistentWord) {

		boolean sensesObtained = false;
		IDictionaryReader idictReader = new OxfordDictReader();
		List<String> meanings = idictReader.getMeaning(inconsistentWord);
		IUserInteract userinteractor = new UserInteractImpl();
		if (meanings.size() > 0) {
			userinteractor.printMeanings(inconsistentWord,
					DictionaryName.OXFORD_DICTIONARY, meanings);
			sensesObtained = true;
		}
		idictReader = new CambridgeDictReader();
		meanings = idictReader.getMeaning(inconsistentWord);
		if (meanings.size() > 0) {
			userinteractor.printMeanings(inconsistentWord,
					DictionaryName.CAMBRIDGE_DICTIONARY, meanings);
			sensesObtained = true;
		}
		return sensesObtained;
	}

	public static void printMap(Map<String, String> mp) {
		Iterator<Entry<String, String>> it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Entry<String, String>) it.next();
			System.out.println(pairs.getKey() + "=" + pairs.getValue());
		}
	}
}

package inconsistentResolver.userInteractor;

import java.util.List;

import util.DictionaryName;

public interface IUserInteract {
	
	void printMeanings(String word, DictionaryName name , List<String> meanings);
}

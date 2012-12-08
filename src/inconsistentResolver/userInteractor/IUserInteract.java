package inconsistentResolver.userInteractor;

import inconsistentResolver.graphfeed.GraphFeeder;


import java.util.List;

import util.DictionaryName;

public interface IUserInteract {
	
	void printMeanings(String word, DictionaryName name , List<String> meanings);
	public GraphFeeder getMeaningsToGraph(String word, DictionaryName name, List<String> meanings);
}

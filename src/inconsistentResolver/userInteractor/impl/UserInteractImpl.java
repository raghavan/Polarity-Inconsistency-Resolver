package inconsistentResolver.userInteractor.impl;

import java.util.List;

import util.DictionaryName;
import inconsistentResolver.graphfeed.GraphFeeder;
import inconsistentResolver.userInteractor.IUserInteract;

public class UserInteractImpl implements IUserInteract {

	@Override
	public void printMeanings(String word, DictionaryName name, List<String> meanings) {
		System.out.println("Senses from the word '"+word+ "' from "+name.value + " \n");
		for(String sense : meanings){
			System.out.println("--->" + sense);
		}
		System.out.println(".......");
	}
	
	@Override
	public GraphFeeder getMeaningsToGraph(String word, DictionaryName name, List<String> meanings) {
		GraphFeeder graphFeeder = new GraphFeeder();
		graphFeeder.setWord(word);
		graphFeeder.addDictSensee(name, meanings);
		return graphFeeder;
	}

}

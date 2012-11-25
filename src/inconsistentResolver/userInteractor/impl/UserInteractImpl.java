package inconsistentResolver.userInteractor.impl;

import java.util.List;

import util.DictionaryName;
import inconsistentResolver.userInteractor.IUserInteract;

public class UserInteractImpl implements IUserInteract {

	@Override
	public void printMeanings(String word, DictionaryName name, List<String> meanings) {
		System.out.println("Senses from the word "+word+ " from "+name.value);
		int i =1;
		for(String sense : meanings){
			System.out.println(i + ")" + sense);
			i++;
		}
		System.out.println(".......");
	}

}

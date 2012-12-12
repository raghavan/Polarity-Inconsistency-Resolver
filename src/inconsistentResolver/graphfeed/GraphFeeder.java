package inconsistentResolver.graphfeed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DictionaryName;

public class GraphFeeder {
	private String word;
	private String polarity;
	private Map<DictionaryName,List<String>> dictSenses = new HashMap<DictionaryName,List<String>>();

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Map<DictionaryName,List<String>> getDictSenses() {
		return dictSenses;
	}

	public void setDictSenses(Map<DictionaryName,List<String>> dictSenses) {
		this.dictSenses = dictSenses;
	}

	public void addDictSensee(DictionaryName dictName,List<String> senses) {
		if(dictSenses.containsKey(dictName)){
			dictSenses.get(dictName).addAll(senses);
		}else{
			dictSenses.put(dictName, senses);
		}
	}
	
	public void addDictSensee(DictionaryName dictName,String sense) {
		if(dictSenses.containsKey(dictName)){
			dictSenses.get(dictName).add(sense);
		}else{
			List<String> senses = new ArrayList<String>();
			senses.add(sense);
			dictSenses.put(dictName, senses);
		}
	}

	public String getPolarity() {
		String polarityWithCaps = polarity.substring(0, 1).toUpperCase() + polarity.substring(1);
		return polarityWithCaps;
	}

	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}
}

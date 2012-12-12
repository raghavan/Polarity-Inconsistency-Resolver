import inconsistentResolver.graphfeed.GraphFeeder;
import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;
import inconsistentResolver.reader.impl.oxford.OxfordDictReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import propertyHandler.PropertyHandlerImpl;
import util.Constants;
import util.DictionaryName;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Startup {
	public static void main(String args[]) {

		/*
		 * Updated polarities which is the result of user interaction, this will
		 * be finally merged with the existing results to make the final
		 * collated result
		 */
		Map<String, String> updatedWordPolarity = new HashMap<String, String>();

		/* Map of words obtained from existing sat solver results */
		LinkedHashMap<String, String> inconsistentWordPolarity = PropertyHandlerImpl
				.readpropFile(Constants.EXISTING_INCONST_PROP);
		
		/*
		 PropertyHandlerImpl.getInconsistentWords(); -- to get the list of words from a given file
		 PropertyHandlerImpl.writePropertyFile("InconsistentWordPolarity.properties",inconsistentWordPolarity);
		 */

		/* Word net frequencies obtained from wordnet library */
		Map<String, String> wordnetFreqSenses = PropertyHandlerImpl
				.readpropFile(Constants.WORDNET_SENSES_FREQ);

		String[] inconsistentWordPolarityKeyList = (String[]) inconsistentWordPolarity
				.keySet().toArray(new String[0]);

		for (; i < inconsistentWordPolarityKeyList.length;) {
			/*
			 * Since the word polarity obtained has
			 * word:polarity,count_of_polarity_conflicts
			 */
			System.out.println(" i --> " +i);
			Integer polarityConflictCount = Integer
					.parseInt(inconsistentWordPolarity.get(
							inconsistentWordPolarityKeyList[i]).split(",")[1]);
			List<GraphFeeder> graphFeeders = getGraphFeeds(inconsistentWordPolarity,inconsistentWordPolarityKeyList,
					polarityConflictCount, wordnetFreqSenses);

			readGraphFeeds(graphFeeders);//replace this with draw api and this loop should continue everytime next is clicked
		}
		writeUpdatedPolarity(updatedWordPolarity);
	}

	private static void readGraphFeeds(List<GraphFeeder> graphFeeders) {
		for (GraphFeeder graphFeeder : graphFeeders) {
			System.out.println("Word -->" + graphFeeder.getWord()
					+ " no. of senses ="
					+ graphFeeder.getDictSenses().size());
			Map<DictionaryName, List<String>> dictSenses = graphFeeder.getDictSenses();
			for(DictionaryName dict : dictSenses.keySet()){
				System.out.println("Senses from "+dict+" are "+dictSenses.get(dict));
			}
		}
	}

	public static mxGraphComponent addModel(mxGraph graph)
			throws NullPointerException {
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		return graphComponent;
	}

	static int i = 0;

	public static List<GraphFeeder> getGraphFeeds(LinkedHashMap<String, String> inconsistentWordPolarity,String[] inconsistentWordPolarityKeyList, 
			Integer polarityConflictCount,Map<String, String> wordnetFreqSenses) {

		List<GraphFeeder> graphFeeders = new ArrayList<GraphFeeder>();

		/* should iterate until the count_of_polarity_conflicts */
		List<String> innerList = new ArrayList<String>();
		for (int j = i; j < i+polarityConflictCount; j++) {
			String word = inconsistentWordPolarityKeyList[j];
			String polarity = inconsistentWordPolarity.get(
					inconsistentWordPolarityKeyList[j]).split(",")[0];
			System.out.println("For word = " + word + " polarity = "+polarity);
			
			innerList.add(word);
			GraphFeeder graphFeeder = new GraphFeeder();
			graphFeeder.setWord(word);
			graphFeeder.setPolarity(polarity);
			useDictReaders(word,graphFeeder);
			showWordNetFreq(word, wordnetFreqSenses,graphFeeder);
			graphFeeders.add(graphFeeder);
		}
		i += polarityConflictCount;

		return graphFeeders;

	}

	private static void writeUpdatedPolarity(
			Map<String, String> updatedWordPolarity) {
		Map<String, String> existingWordPolarity = PropertyHandlerImpl
				.readpropFile(Constants.WORD_POLARITY_SRC_FILENAME);
		existingWordPolarity.putAll(updatedWordPolarity);
		PropertyHandlerImpl.writePropertyFile("UpdatedWordPolarity.properties",
				existingWordPolarity);
	}

	public static void showWordNetFreq(String word,
			Map<String, String> wordNetFreqSenses,GraphFeeder graphFeeder) {
		List<String> meanings = Arrays.asList(wordNetFreqSenses.get(word)
				.split(","));
		if (meanings != null) {			
			graphFeeder.getDictSenses().put(DictionaryName.WORDNET_DICTIONARY,meanings);
		}
	}

	public static void useDictReaders(String inconsistentWord,GraphFeeder graphFeeder){
		IDictionaryReader idictReader = new OxfordDictReader();
		List<String> meanings = idictReader.getMeaning(inconsistentWord);
		if (meanings.size() > 0) {
			graphFeeder.addDictSensee(DictionaryName.OXFORD_DICTIONARY, meanings);
		}

		idictReader = new CambridgeDictReader();
		meanings = idictReader.getMeaning(inconsistentWord);
		if (meanings.size() > 0) {
			graphFeeder.addDictSensee(DictionaryName.CAMBRIDGE_DICTIONARY, meanings);
		}
	}

	public static void printMap(Map<String, String> mp) {
		Iterator<Entry<String, String>> it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Entry<String, String>) it.next();
			System.out.println(pairs.getKey() + "=" + pairs.getValue());
		}
	}
}

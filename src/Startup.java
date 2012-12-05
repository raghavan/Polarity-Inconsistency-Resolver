import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;
import inconsistentResolver.reader.impl.oxford.OxfordDictReader;
import inconsistentResolver.userInteractor.IUserInteract;
import inconsistentResolver.userInteractor.impl.UserInteractImpl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import propertyHandler.PropertyHandlerImpl;
import util.Constants;
import util.DictionaryName;
import util.Polarity;

import com.mxgraph.examples.swing.ClickHandler;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Startup {
	public static void main(String args[]) {

		ClickHandler frame = null;
		try {
			mxGraphComponent component = addModel(new GraphFeeder());
			frame = new ClickHandler(component);
			// frame.setDefaultCloseOperation(JFrame.ABORT);
			frame.setSize(400, 320);
			frame.setVisible(true);
		} catch (Exception e) {
			frame.setVisible(false);
		}
		
		//doPolaritySupport();
		
	}
	
	public static mxGraphComponent addModel(GraphFeeder feeder){
		final mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			Object next = graph.insertVertex(parent, null, "Next", 10, 200, 30,
					20);
			Object huge = graph.insertVertex(parent, null, "huge", 20, 20, 80,
					30);
			Object enormous = graph.insertVertex(parent, null, "enormous", 240,
					150, 80, 30);
			Object voluminous = graph.insertVertex(parent, null, "voluminous",
					140, 150, 80, 30);
			graph.insertEdge(parent, null, "", next, null);
			graph.insertEdge(parent, null, "Oxford", huge, enormous);
			graph.insertEdge(parent, null, "cambridge", huge, voluminous);
		} finally {
			graph.getModel().endUpdate();
		}
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());

				if (cell != null) {
					System.out.println("cell=" + graph.getLabel(cell));
					if (graph.getLabel(cell).equalsIgnoreCase("next")) {
						throw new NullPointerException();
					}
				}
			}
		});
		return graphComponent;
	}
	
	public static void doPolaritySupport(){

		/*Map of words obtained from existing sat solver results*/
		LinkedHashMap<String, String> inconsistentWordPolarity = PropertyHandlerImpl.readpropFile(Constants.EXISTING_INCONST_PROP);
		//PropertyHandlerImpl.getInconsistentWords(); -- to get the list of words from a given file
		//PropertyHandlerImpl.writePropertyFile("InconsistentWordPolarity.properties",inconsistentWordPolarity);
		
		/*Word net frequencies obtained from wordnet library*/
		Map<String,String> wordnetFreqSenses = PropertyHandlerImpl.readpropFile(Constants.WORDNET_SENSES_FREQ);
		
		/*Updated polarities which is the result of user interaction, 
		 * this will be finally merged with the existing results to make the final collated result*/
		Map<String, String> updatedWordPolarity = new HashMap<String, String>();
		
		String[] inconsistentWordPolarityKeyList = (String[]) inconsistentWordPolarity.keySet().toArray(new String[0]);
		
		int i = 0;
		for (;i<inconsistentWordPolarityKeyList.length;) {
			/*Since the word polarity obtained has word:polarity,count_of_polarity_conflicts*/
			
			Integer polarityConflictCount = Integer.parseInt(inconsistentWordPolarity.get(inconsistentWordPolarityKeyList[i]).split(",")[1]);

			/*should iterate until the count_of_polarity_conflicts*/
			List<String> innerList = new ArrayList<String>(); 
			for(int j=0;j<polarityConflictCount;j++){
				String key = inconsistentWordPolarityKeyList[i];
				innerList.add(key);
				boolean sensesObtained = useDictReaders(key);
				showWordNetFreq(key,wordnetFreqSenses);
				i++;
			}
			//i += polarityConflictCount;
			for(String word : innerList){
					System.out.println("Inconsistent word \'" + word + "\' with polarity -> " + 
									inconsistentWordPolarity.get(word).split(",")[0]);
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
					updatedWordPolarity.put(word, Polarity.fromValue(a));
			}
		}

		Map<String, String> existingWordPolarity = PropertyHandlerImpl
				.readpropFile(Constants.WORD_POLARITY_SRC_FILENAME);
		existingWordPolarity.putAll(updatedWordPolarity);
		PropertyHandlerImpl.writePropertyFile("UpdatedWordPolarity.properties",
				existingWordPolarity);
	}
	
	public static void showWordNetFreq(String word,Map<String,String> wordNetFreqSenses){
		IUserInteract userinteractor = new UserInteractImpl();
		List<String> meanings = Arrays.asList(wordNetFreqSenses.get(word).split(","));
		userinteractor.printMeanings(word,DictionaryName.WORDNET_DICTIONARY,meanings);
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

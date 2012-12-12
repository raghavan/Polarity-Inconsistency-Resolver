import inconsistentResolver.graphfeed.GraphFeeder;
import inconsistentResolver.reader.IDictionaryReader;
import inconsistentResolver.reader.impl.cambridge.CambridgeDictReader;
import inconsistentResolver.reader.impl.oxford.OxfordDictReader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;

import propertyHandler.PropertyHandlerImpl;
import util.Constants;
import util.DictionaryName;
import util.StringHandlerUtil;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class Checker {
	public static int count = 1;
	public static boolean showStartupScreen = true;

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
		// PropertyHandlerImpl.getInconsistentWords(); -- to get the list of
		// words from a given file
		// PropertyHandlerImpl.writePropertyFile(
		// "InconsistentWordPolarity.properties",inconsistentWordPolarity);

		/* Word net frequencies obtained from wordnet library */
		Map<String, String> wordnetFreqSenses = PropertyHandlerImpl
				.readpropFile(Constants.WORDNET_SENSES_FREQ);

		String[] inconsistentWordPolarityKeyList = (String[]) inconsistentWordPolarity
				.keySet().toArray(new String[0]);
		List<List<GraphFeeder>> globalGraphFeeders = new ArrayList<List<GraphFeeder>>();
		for (; i < inconsistentWordPolarityKeyList.length;) {
			/*
			 * Since the word polarity obtained has
			 * word:polarity,count_of_polarity_conflicts
			 */

			Integer polarityConflictCount = Integer
					.parseInt(inconsistentWordPolarity.get(
							inconsistentWordPolarityKeyList[i]).split(",")[1]);
			List<GraphFeeder> graphFeeders = getGraphFeeds(
					inconsistentWordPolarity, inconsistentWordPolarityKeyList,
					polarityConflictCount, wordnetFreqSenses);

			readGraphFeeds(graphFeeders);// replace this with draw api and this
			// loop should continue everytime
			// next is clicked
			globalGraphFeeders.add(graphFeeders);
		}
		mxGraph graph = new mxGraph();
		Object defaultParent = graph.getDefaultParent();
		JFrame frame = new JFrame();
		frame.getContentPane().add(
				drawGraph(graph, defaultParent, globalGraphFeeders,
						globalGraphFeeders.get(0), updatedWordPolarity));
		frame.setSize(1000, 1000);
		frame.setVisible(true);

	}

	public static mxGraphComponent drawGraph(final mxGraph graph,
			final Object defaultParent,
			final List<List<GraphFeeder>> globalGraphFeeders,
			final List<GraphFeeder> graphFeeders,
			final Map<String, String> updatedWordPolarity) {
		final int globalcount = globalGraphFeeders.size();
		if (showStartupScreen) {
			showStartupScreen = false;
			Object vword = graph
					.insertVertex(
							defaultParent,
							null,
							Constants.WELCOME_NOTE + StringHandlerUtil.makeLeftAlign(Constants.WELCOME_INSTRUCTIONS),
							10, 100, 700, 400, "");

			Object start = graph.insertVertex(defaultParent, null, "Start",
					750, 600, 80, 80);
			graph.insertEdge(defaultParent, null, "", vword, null);
			graph.insertEdge(defaultParent, null, "", start, null);
		} else {
			System.out.println("In else");
			int a = 100;
			int b = 200;
			int x1 = 100;
			int pos_y = 10;

			graph.getModel().beginUpdate();
			for (GraphFeeder graphFeeder : graphFeeders) {

				Object vword = graph
						.insertVertex(defaultParent, null, graphFeeder
								.getWord()
								+ ":Polarity=" + graphFeeder.getPolarity(), x1, pos_y,
								200, 100, "");
				x1 = x1 + 300;
				Map<DictionaryName, List<String>> dictSenses = graphFeeder
						.getDictSenses();
				for (DictionaryName dict : dictSenses.keySet()) {
					Object[] allCells = graph.getChildVertices(graph
							.getDefaultParent());
					Object vsense = null;
					Object repeatingCell = null;
					int temp = 0;
					for (Object eachCell : allCells) {
						if (graph.getLabel(eachCell).contains(
								dictSenses.get(dict).get(0))) {
							temp = 1;
							repeatingCell = eachCell;
						}
					}
					String sense = dictSenses.get(dict).toString();
					if (temp == 0) {
						vsense = graph.insertVertex(defaultParent, null,
								sense, a, b, 100, sense.length()+100,
								"whiteSpace=wrap");
						graph.insertEdge(defaultParent, null, dict.value,
								vword, vsense);
						a = a + sense.length()+sense.length()+100;
						b = b + 20;
					} else {
						graph.insertEdge(defaultParent, null, dict.value, vword,
								repeatingCell);
						a = a + sense.length()+100;
						b = b + 20;
					}
				}
			}
			b += 50;
			a += 100;
			x1 += 100;
			if (count != globalcount) {
				Object next = graph.insertVertex(defaultParent, null, "Next",
						750, 600, 80, 80);
				graph.insertEdge(defaultParent, null, "", next, null);
			}
			Object saveFile = graph.insertVertex(defaultParent, null,
					"Save to File", 600, 600, 80, 80);
			graph.insertEdge(defaultParent, null, "", saveFile, null);
		}
		final mxGraphComponent drawGraph = new mxGraphComponent(graph);
		drawGraph.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object cell = drawGraph.getCellAt(e.getX(), e.getY());
				System.out.println("Mouse click in graph component");
				if (cell != null
						&& graph.getLabel(cell)
								.equalsIgnoreCase("Save to File")) {
					writeUpdatedPolarity(updatedWordPolarity);
				}
				if (cell != null
						&& graph.getLabel(cell).equalsIgnoreCase("Start")) {
					graph.removeCells(graph.getChildVertices(graph
							.getDefaultParent()));
					drawGraph(graph, defaultParent, globalGraphFeeders,
							globalGraphFeeders.get(0), updatedWordPolarity);
				}
				if (cell != null
						&& graph.getLabel(cell) != "Next"
						&& !graph.getLabel(cell).equalsIgnoreCase(
								"Save to File")) {
					mxCellState cellState;
					String newLabelPolarity = null;
					System.out.println("cell=" + graph.getLabel(cell));
					if (graph.getLabel(cell).contains("=")) {
						String labelPolarity = graph.getLabel(cell)
								.split(":Polarity\\=")[1];
						String labelWord = graph.getLabel(cell).split(":Polarity\\=")[0];
						
						for (Map.Entry<String, String> eachUpdatedWordPolarity : updatedWordPolarity
								.entrySet()) {
							String updatedWord = eachUpdatedWordPolarity
									.getKey();
							if (labelWord.equalsIgnoreCase(updatedWord)) {
								labelPolarity = eachUpdatedWordPolarity
										.getValue();
							}
						}
						if (labelPolarity.equalsIgnoreCase("positive")) {
							newLabelPolarity = "Negative";
							updatedWordPolarity
									.put(labelWord, newLabelPolarity);
							System.out.println(updatedWordPolarity);
						} else if (labelPolarity.equalsIgnoreCase("negative")) {
							newLabelPolarity = "Neutral";
							updatedWordPolarity
									.put(labelWord, newLabelPolarity);
							System.out.println(updatedWordPolarity);
						} else {
							newLabelPolarity = "Positive";
							updatedWordPolarity
									.put(labelWord, newLabelPolarity);
							System.out.println(updatedWordPolarity);
						}
						cellState = graph.getView().getState(cell);
						cellState.setLabel(labelWord + ":Polarity=" + newLabelPolarity);
					}
				}
				if (cell != null
						&& graph.getLabel(cell).equalsIgnoreCase("Next")
						&& count < globalcount) {
					graph.removeCells(graph.getChildVertices(graph
							.getDefaultParent()));
					drawGraph(graph, defaultParent, globalGraphFeeders,
							globalGraphFeeders.get(count++),
							updatedWordPolarity);
				}
			}
		});
		graph.setAutoSizeCells(true);
		graph.setEdgeLabelsMovable(false);
		graph.setAllowDanglingEdges(false);
		graph.setCellsEditable(false);
		// graph.setCellsMovable(false);
		// graph.setCellsResizable(false);
		graph.setSplitEnabled(false);
		graph.getModel().endUpdate();
		return drawGraph;

	}

	private static void readGraphFeeds(List<GraphFeeder> graphFeeders) {
		for (GraphFeeder graphFeeder : graphFeeders) {
			System.out.println("Word -->" + graphFeeder.getWord()
					+ " no. of senses =" + graphFeeder.getDictSenses().size());
			Map<DictionaryName, List<String>> dictSenses = graphFeeder
					.getDictSenses();
			for (DictionaryName dict : dictSenses.keySet()) {
				System.out.println("Senses from " + dict + " are "
						+ dictSenses.get(dict));
			}
		}
	}

	public static mxGraphComponent addModel(mxGraph graph)
			throws NullPointerException {
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		return graphComponent;
	}

	static int i = 0;

	public static List<GraphFeeder> getGraphFeeds(
			LinkedHashMap<String, String> inconsistentWordPolarity,
			String[] inconsistentWordPolarityKeyList,
			Integer polarityConflictCount, Map<String, String> wordnetFreqSenses) {

		List<GraphFeeder> graphFeeders = new ArrayList<GraphFeeder>();

		/* should iterate until the count_of_polarity_conflicts */
		List<String> innerList = new ArrayList<String>();
		for (int j = i; j < i + polarityConflictCount; j++) {
			String word = inconsistentWordPolarityKeyList[j];
			String polarity = inconsistentWordPolarity.get(
					inconsistentWordPolarityKeyList[j]).split(",")[0];
			System.out
					.println("For word = " + word + " polarity = " + polarity);

			innerList.add(word);
			GraphFeeder graphFeeder = new GraphFeeder();
			graphFeeder.setWord(word);
			graphFeeder.setPolarity(polarity);
			useDictReaders(word, graphFeeder);
			showWordNetFreq(word, wordnetFreqSenses, graphFeeder);
			graphFeeders.add(graphFeeder);
		}
		i += polarityConflictCount;

		return graphFeeders;

	}

	private static void writeUpdatedPolarity(
			Map<String, String> updatedWordPolarity) {
		System.out.println("Writing updated polarity");
		Map<String, String> existingWordPolarity = PropertyHandlerImpl
				.readpropFile(Constants.WORD_POLARITY_SRC_FILENAME);
		existingWordPolarity.putAll(updatedWordPolarity);
		PropertyHandlerImpl.writePropertyFile(Constants.OUTPUT_PROP_FILE     ,
				existingWordPolarity);
	}

	public static void showWordNetFreq(String word,
			Map<String, String> wordNetFreqSenses, GraphFeeder graphFeeder) {
		if (word != null) {
			try {
				List<String> meanings = Arrays.asList(wordNetFreqSenses.get(
						word).split(","));

				if (meanings != null) {
					graphFeeder.getDictSenses().put(
							DictionaryName.WORDNET_DICTIONARY, meanings);
				}
			} catch (NullPointerException e) {

			}
		}
	}

	public static void useDictReaders(String inconsistentWord,
			GraphFeeder graphFeeder) {
		IDictionaryReader idictReader = new OxfordDictReader();
		List<String> meanings = idictReader.getMeaning(inconsistentWord);
		if (meanings.size() > 0) {
			graphFeeder.addDictSensee(DictionaryName.OXFORD_DICTIONARY,
					meanings);
		}

		idictReader = new CambridgeDictReader();
		meanings = idictReader.getMeaning(inconsistentWord);
		if (meanings.size() > 0) {
			graphFeeder.addDictSensee(DictionaryName.CAMBRIDGE_DICTIONARY,
					meanings);
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

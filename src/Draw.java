import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Draw extends JFrame {

	private static final long serialVersionUID = 1L;
	public int count = 0;

	public Draw() {
		super("Graph");
		List<Map<String, Map<String, List<String>>>> totalData = getgraphData();
		mxGraph graph = new mxGraph();
		Object defaultParent = graph.getDefaultParent();
		getContentPane()
				.add(drawComponent(graph, defaultParent, totalData,
						totalData.get(0)));
	}

	public mxGraphComponent drawComponent(final mxGraph graph,
			final Object defaultParent,
			final List<Map<String, Map<String, List<String>>>> totalData,
			Map<String, Map<String, List<String>>> wordSet) {
		int a = 100;
		int b = 200;
		int x1 = 20;
		int pos_y = 10;

		graph.getModel().beginUpdate();
		for (Entry<String, Map<String, List<String>>> wordentry : wordSet
				.entrySet()) {
			String word = wordentry.getKey();
			Object vword = graph.insertVertex(defaultParent, null, word, x1,
					pos_y + 30, 80, 80);
			x1 = x1 + 200;
			for (Entry<String, List<String>> senseentry : wordentry.getValue()
					.entrySet()) {
				String dict = senseentry.getKey();
				Iterator<String> itr = senseentry.getValue().iterator();
				while (itr.hasNext()) {
					String sense = itr.next();
					Object vsense = graph.insertVertex(defaultParent, null,
							sense, a, b, 100, 80);
					graph.insertEdge(defaultParent, null, dict, vword, vsense);
					a = a + 100;
					b = b + 20;
				}
			}
			b += 50;
			a += 100;
			x1 += 100;
		}
		Object next = graph.insertVertex(defaultParent, null, "next", 750, 600,
				80, 80);
		graph.insertEdge(defaultParent, null, "", next, null);
		graph.getModel().endUpdate();
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				System.out.println("Mouse click in graph component");
				if (cell != null)
					System.out.println("cell=" + graph.getLabel(cell));
				if (graph.getLabel(cell).equalsIgnoreCase("next")) {
					graph.removeCells(graph.getChildVertices(graph
							.getDefaultParent()));
					drawComponent(graph, defaultParent, totalData,
							totalData.get(++count));
					System.out.println(count);
				}
			}
		});
		return graphComponent;
	}

	public List<Map<String, Map<String, List<String>>>> getgraphData() {
		String w1 = "huge";
		List<String> w1ox = new ArrayList<String>();
		w1ox.add("extremely large");
		w1ox.add("enormous");
		List<String> w1wn = new ArrayList<String>();
		w1wn.add("great");
		Map<String, List<String>> s1 = new HashMap<String, List<String>>();
		s1.put("Oxford", w1ox);
		s1.put("Wordnet", w1wn);

		String w2 = "immense";
		List<String> w2ox = new ArrayList<String>();
		w2ox.add("extremely large");
		w2ox.add("enormous");
		List<String> w2wn = new ArrayList<String>();
		w2wn.add("great");
		Map<String, List<String>> s2 = new HashMap<String, List<String>>();
		s2.put("Oxford", w2ox);
		s2.put("Wordnet", w2wn);

		String w3 = "vast";
		List<String> w3ox = new ArrayList<String>();
		w3ox.add("extremely large");
		w3ox.add("very great");
		List<String> w3wn = new ArrayList<String>();
		w3wn.add("great");
		Map<String, List<String>> s3 = new HashMap<String, List<String>>();
		s3.put("Oxford", w3ox);
		s3.put("Wordnet", w3wn);

		Map<String, Map<String, List<String>>> data1 = new HashMap<String, Map<String, List<String>>>();
		Map<String, Map<String, List<String>>> data2 = new HashMap<String, Map<String, List<String>>>();
		Map<String, Map<String, List<String>>> data3 = new HashMap<String, Map<String, List<String>>>();
		List<Map<String, Map<String, List<String>>>> totalData = new ArrayList<Map<String, Map<String, List<String>>>>();

		data1.put(w1 + 1, s1);
		data1.put(w2 + 1, s2);
		data1.put(w3 + 1, s3);
		data2.put(w1 + 2, s1);
		data2.put(w2 + 2, s2);
		data2.put(w3 + 2, s3);
		data3.put(w1 + 3, s1);
		data3.put(w2 + 3, s2);
		data3.put(w3 + 3, s3);

		totalData.add(data1);
		totalData.add(data2);
		totalData.add(data3);
		// System.out.println(totalData);
		return totalData;
	}

	public static void main(String args[]) {
		Draw frame = new Draw();
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}
}
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Draw extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Draw(String value1, String value2) {
		super("Graph");
		mxGraph graph = new mxGraph();
		Object defaultParent = graph.getDefaultParent();
		getContentPane().add(
				drawComponent(graph, defaultParent, value1, value2));
	}	
	public mxGraphComponent drawComponent(final mxGraph graph, final Object defaultParent,
			String value1, String value2) {
		graph.getModel().beginUpdate();
		Object v1 = graph.insertVertex(defaultParent, null, value1, 20, 20, 80,
				30);
		Object v2 = graph.insertVertex(defaultParent, null, value2, 240, 150,
				80, 30);
		Object v3 = graph.insertVertex(defaultParent, null, "next", 100, 200,
				30, 20);
		graph.insertEdge(defaultParent, null, "Edge", v1, v2);
		graph.insertEdge(defaultParent, null, "", v3, null);
		graph.getModel().endUpdate();
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				Object cell =graphComponent.getCellAt(e.getX(), e.getY());
				System.out.println("Mouse click in graph component");
				if (cell != null)
					System.out.println("cell=" + graph.getLabel(cell));
				if (graph.getLabel(cell).equalsIgnoreCase("next"))
					drawComponent(graph, defaultParent, "first", "second");		
			} 
		});
		return graphComponent;
	}

	public static void main(String args[]) {
		Draw frame = new Draw("hello", "value2");
		frame.setSize(400, 320);
		frame.setVisible(true);
	}
}
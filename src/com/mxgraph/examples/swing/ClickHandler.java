package com.mxgraph.examples.swing;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;

public class ClickHandler extends JFrame {

	private static final long serialVersionUID = -2764911804288120883L;

	public ClickHandler(final mxGraphComponent graphComponent) throws Exception {
		super("Polarity Inconsistency Supporter");		
		getContentPane().add(graphComponent);
	}

}

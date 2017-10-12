package gui;
import java.awt.*;
import java.awt.event.KeyEvent;


import javax.swing.*;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class practice extends JFrame{
	
	private static final long serialVersionUID = 1L;
	// Private instance variables
	// ......
	
	public practice() {
		// Retrieve the top-level content-pane from JFrame
	    Container cp = getContentPane();
	 
	    // Content-pane sets layout
	    cp.setLayout(new BorderLayout());
	 
	    // Allocate the GUI components
	    // .....
	    
	    /** mxGraphComponent construction **/
	    mxGraph graph = new mxGraph();
	    Object parent = graph.getDefaultParent();

	    graph.getModel().beginUpdate();
	    try{
	    	Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,30);
	    	Object v2 = graph.insertVertex(parent, null, "World!", 20, 220, 80, 30);
	    	graph.insertEdge(parent, null, "Edge", v1, v2);
				
	    	//settings required for flow2code
	    	graph.setAllowDanglingEdges(false);
	    	graph.setCellsResizable(false);
	    	graph.setCellsDeletable(true);
	    	graph.setCellsEditable(false);
	    }finally{
	    	graph.getModel().endUpdate();
	    }
		
	    // Construct mxGraphComponent from mxGraph, mxGraphComponent is an extension of JScrollPane.
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
	     
		/** Menu Bar Construction **/
		// A menu-bar contains menus. A menu contains menu-items (or sub-Menu)
	    JMenuBar menuBar;   // the menu-bar
	    JMenu menu;         // each menu in the menu-bar
	 
	    menuBar = new JMenuBar();
	 
	    // First Menu
	    menu = new JMenu("Menu-A");
	    menu.setMnemonic(KeyEvent.VK_A);  // alt short-cut key
	    menuBar.add(menu);  // the menu-bar adds this menu
	    
	    // Second Menu
	    menu = new JMenu("Menu-B");
	    menu.setMnemonic(KeyEvent.VK_B);  // short-cut key
	    menuBar.add(menu);  // the menu bar adds this menu
	    
	    /** JSplitPane Construction - we add graphComponent on to JSplitPane **/
	    JSplitPane splitPane = new JSplitPane();
	    splitPane.add(graphComponent,JSplitPane.LEFT);
	    splitPane.add(new JTextPane(), JSplitPane.RIGHT);
	      
	    // Content-pane adds components
	    this.setJMenuBar(menuBar);
	    cp.add(splitPane, BorderLayout.CENTER);
	 
	    // Source object adds listener
	    // .....
	 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       // Exit the program when the close-window button clicked
	    setTitle("......");  // "super" JFrame sets title
	    setSize(1000, 600);   // "super" JFrame sets initial size
	    setVisible(true);    // "super" JFrame shows
	}
	
	
	public static void main(String[] args) {
		// Run the GUI construction in the Event-Dispatching thread for thread-safety
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new practice(); // Let the constructor do the job
	         }
	    });
	}
}

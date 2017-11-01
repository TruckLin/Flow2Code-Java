package gui;
import java.awt.*;
import java.awt.event.KeyEvent;


import javax.swing.*;

import org.json.JSONObject;

import graph.object.BlockFD;
import graph.object.BlockFlowDiagram;
import gui.manager.UndoManager;
import saveload.SaveAndLoadManagerFD;

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
	    
	    
	    /** Demo FlowDiagram construction **/
	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON("/FlowDiagramDemo.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON("/FDDemo1.json");
	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON("/info.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON("/info1.json");
	    BlockFD flowDiagram = SaveAndLoadManagerFD.constructBlockFD(myModel, myInfo);
		
	    	// Attach Listeners to the Blocks
	    UndoManager undoManager = new UndoManager();
	    SaveAndLoadManagerFD.attachMouseListenersToBlock(undoManager, flowDiagram);
	    
		/** JScrollPane Construction **/
	    JScrollPane scrollPane = new JScrollPane(flowDiagram);
	    
	    
	    /** JSplitPane Construction - we add scroll pane JSplitPane **/
	    JSplitPane splitPane = new JSplitPane();
	    splitPane.setDividerLocation(700);
	    splitPane.add(scrollPane,JSplitPane.LEFT);
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

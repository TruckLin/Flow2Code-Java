package gui;
import java.awt.*;
import java.awt.event.KeyEvent;


import javax.swing.*;

import org.json.JSONObject;

import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.BlockFlowDiagram;
import saveload.SaveAndLoadManagerFD;

public class Flow2Code extends JFrame{
	
	private static final long serialVersionUID = 1L;
	// Private instance variables
	// ......
	
	public Flow2Code() {
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
	    //Testing
	    //System.out.println("isUndoAvailable():" + undoManager.isUndoAvailable());
	    
		/** JScrollPane Construction **/
	    //ScrollablePanelForFD sp = new ScrollablePanelForFD();
	    //sp.add(flowDiagram);
	    //sp.setSize(flowDiagram);
	    JScrollPane scrollPane = new JScrollPane(flowDiagram);
	    
	    /** Left flowDiagram tool bar **/
	    FlowDiagramToolBar fdToolBar = new FlowDiagramToolBar(undoManager);
	    
	    /** Left Panel Construction **/
	    JPanel leftPanel = new JPanel(new BorderLayout());
	    leftPanel.add(fdToolBar, BorderLayout.NORTH);
	    leftPanel.add(scrollPane, BorderLayout.CENTER);
	    
	    /** Right Panel Construction **/
	    JPanel rightPanel = new JPanel(new BorderLayout());
	    JTextArea codeView = new JTextArea();
	    codeView.setText(myModel.toString(10));
	    codeView.setEditable(false);
	    JScrollPane textScrollPane = new JScrollPane(codeView);
	    rightPanel.add(textScrollPane, BorderLayout.CENTER);
	    
	    /** JSplitPane Construction**/
	    JSplitPane splitPane = new JSplitPane();
	    splitPane.setDividerLocation(700);
	    splitPane.add(leftPanel,JSplitPane.LEFT);
	    splitPane.add(rightPanel, JSplitPane.RIGHT);
	    
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
	            new Flow2Code(); // Let the constructor do the job
	         }
	    });
	}
}

package gui;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import org.json.JSONObject;

import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.BlockFlowDiagram;
import gui.object.CompositeBlockFD;
import strategy.generator.BlockGenerator;
import gui.manager.SaveAndLoadManagerFD;

public class Flow2Code extends JFrame{
	
	private static final long serialVersionUID = 1L;
	// Private instance variables
	
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
	    
	    
	    /** Initialisation of various managers and user interfaces **/
	    UndoManager undoManager = new UndoManager();
	    NameCounterManager nameManager = new NameCounterManager();
	    
	    /** Demo FlowDiagram construction **/
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\FlowDiagramDemo.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\FlowDiagramDemo-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-If.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-If-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-ForLoop.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-ForLoop-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-WhileLoop.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-WhileLoop-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-Declare.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-Declare-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-Assign.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-Assign-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-Input.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-Input-info.json");
//	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-Output.json");
//	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-Output-info.json");
	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-Empty.json");
	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-Empty-info.json");

	    
	    BlockGenerator blockGenerator = new BlockGenerator();
	    CompositeBlockFD flowDiagram =  (CompositeBlockFD)blockGenerator.generate(myModel, myInfo);
	    flowDiagram.setAppropriateBounds();
	    //Testing
	    //System.out.println("isUndoAvailable():" + undoManager.isUndoAvailable());
	    //ArrayList<LineFD> linelist = ((BlockFlowDiagram)flowDiagram).getLineList();
	    //for(LineFD line : linelist) {
	    // 	System.out.println("LineFD :" );
	    //	System.out.println("    Source : " + line.getSource().getModel().getString("Type"));
	    //	System.out.println("    Terminal : " + line.getTerminal().getModel().getString("Type"));
	    //}
	    
	    // Set a common UndoManager for all Blocks.
	    flowDiagram.setUndoManager(undoManager);
	    // Set a common NameCounterManager for all Blocks.
	    flowDiagram.setNameCounterManager(nameManager);
	    
	    // Attach various listeners for blocks
	    flowDiagram.addVariousMouseListeners();
	    
		/** JScrollPane Construction **/
	    ScrollablePanelForFD sp = new ScrollablePanelForFD((BlockFlowDiagram) flowDiagram);
	    JViewport myViewport = new JViewport();
	    myViewport.setView(sp);
	    myViewport.setExtentSize(new Dimension(50,50));
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewport(myViewport);
	    
	    //Testing
	    myViewport.setBackground(Color.yellow);
	    
//	    myViewport.setExtentSize(new Dimension(50,50));
//		System.out.println(myViewport);
	    
	    
	    /** Left flowDiagram tool bar **/
	    FlowDiagramToolBar fdToolBar = new FlowDiagramToolBar(undoManager, sp);
	    
	    
	    /** Left Panel Construction **/
	    JPanel leftPanel = new JPanel(new BorderLayout());
	    leftPanel.add(fdToolBar, BorderLayout.NORTH);
	    leftPanel.add(scrollPane, BorderLayout.CENTER);
	    
	    /** Right Panel Construction **/
	    JPanel rightPanel = new JPanel(new BorderLayout());
	    JTextArea codeView = new JTextArea();
	    codeView.setText(myModel.toString(10));	    
	    TextFetcher txtFetcher = new TextFetcher(myModel,codeView);
	    txtFetcher.start();
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
	
	
	
	/**
	*	This named inner class TextFetcher extends Thread, fetch text from JSONOBject every 0.5 second.
	*/
	class TextFetcher extends Thread {
		JSONObject model;
		JTextArea textArea;
		TextFetcher(JSONObject model, JTextArea textArea){
			this.model = model;
			this.textArea = textArea;
		}
		public void run() {
			try {
				boolean stop = false;
				while(!stop){
					textArea.setText(model.toString(10) + "\n executed.");
					
					Thread.sleep(500);
				}
			}catch (InterruptedException e) {
					System.out.println("Thread interrupted.");
			}
		}
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

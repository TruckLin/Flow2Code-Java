package gui;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import org.json.JSONObject;

import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.BlockFlowDiagram;
import gui.object.CompositeBlockFD;
import strategy.generator.BlockGenerator;
import strategy.generator.ModelGenerator;
import gui.manager.SaveAndLoadManagerFD;

public class Flow2Code extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	// Private instance variables
	private JSONObject FlowDiagramModel = new JSONObject();
	private JSONObject FlowDiagramInfo = new JSONObject();
	private CompositeBlockFD blockFlowDiagram;
	
	private ArrayList<JSONObject> functions = new ArrayList<JSONObject>(); 
	private ArrayList<JSONObject> functionsInfo = new ArrayList<JSONObject>();
	private ArrayList<CompositeBlockFD> blockFunctions = new ArrayList<CompositeBlockFD>();
	
	private UndoManager undoManager;
	private NameCounterManager nameManager;
	
	// Left
	private ScrollablePanelForFD scrollablePanelForFD; 
	private FlowDiagramToolBar fdToolBar;
	
	// Right
	private JTextArea codeView;
	private JToolBar codeToolBar;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu optionMenu;
	
	//I18N fields
	private String language = "en"; // default variable
    private String country = "US";
    private Locale currentLocale;
    private ResourceBundle languageBundle;
	
	// private CodeViewToolBar cvToolBar;
	
	/** Getters and Setters **/
	public JSONObject getFlowDiagramModel() {return this.FlowDiagramModel;}
	public void setFlowDiagramModel(JSONObject model) {this.FlowDiagramModel = model;}
	public JSONObject getFlowDiagramInfo() {return this.FlowDiagramInfo;}
	public void setFlowDiagramInfo(JSONObject info) {this.FlowDiagramInfo = info;}
	public CompositeBlockFD getBlockFlowDiagram() {return this.blockFlowDiagram;}
	public void setBlockFlowDiagram(CompositeBlockFD fd) {this.blockFlowDiagram = fd;}
	public UndoManager getUndoManager() {return this.undoManager;}
	public void setUndoManager(UndoManager undoManager) {this.undoManager = undoManager;}
	public NameCounterManager getNameCounterManager() {return this.nameManager;}
	public void setNameCounterManager(NameCounterManager nameManager) {this.nameManager = nameManager;}
	public ScrollablePanelForFD getScrollablePanelForFD() {return this.scrollablePanelForFD;}
	public JTextArea getCodeTextArea() {return this.codeView;}
	
	public Flow2Code() {
		
		// I18N construction
		this.language = "ch";
		this.country = "CH";
		this.currentLocale = new Locale(language, country);
		this.languageBundle = ResourceBundle.getBundle("LanguageBundle",currentLocale);
		
		//Testing
		//System.out.println(languageBundle.getString("Delete"));
		
		// Retrieve the top-level content-pane from JFrame
	    Container cp = getContentPane();
	 
	    // Content-pane sets layout
	    cp.setLayout(new BorderLayout());
	    
	    // Allocate the GUI components
	    // .....
	    
		/** Menu Bar Construction **/
		// A menu-bar contains menus. A menu contains menu-items (or sub-Menu)
	    this.menuBar = new JMenuBar();
	 
	    // First Menu	
	    fileMenu = new JMenu("File");
	    fileMenu.setMnemonic(KeyEvent.VK_F);  // alt short-cut key
	    this.menuBar.add(fileMenu);  // the menu-bar adds this menu
	    
	    // Second Menu
	    this.optionMenu = new JMenu("Option");
	    this.optionMenu.setMnemonic(KeyEvent.VK_O);  // short-cut key
	    this.menuBar.add(this.optionMenu);  // the menu bar adds this menu
	    
	    /** Initialisation of various managers and user interfaces **/
	    this.undoManager = new UndoManager();
	    this.nameManager = new NameCounterManager();
	    
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
//	    this.FlowDiagramModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON(".\\assets\\Demo-Empty.json");
//	    this.FlowDiagramInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON(".\\assets\\Demo-Empty-info.json");
	    SaveAndLoadManagerFD.loadFlowDiagramFromZippedFile(this.FlowDiagramModel, this.FlowDiagramInfo, 
	    																			".\\assets\\Demo-empty.foo");
	    
	    //ModelGenerator modelGenerator = new ModelGenerator(nameManager);
	    
	    BlockGenerator blockGenerator = new BlockGenerator();
	    //this.FlowDiagramModel = modelGenerator.generate("FlowDiagram");
	    this.blockFlowDiagram = (CompositeBlockFD)blockGenerator.generate(this.FlowDiagramModel, this.FlowDiagramInfo);
	    this.blockFlowDiagram.setAppropriateBounds();
	    
	    //i18n
	    this.blockFlowDiagram.setLanguageBundle(languageBundle);
	    
	    //Testing
	    //System.out.println("isUndoAvailable():" + undoManager.isUndoAvailable());
	    //ArrayList<LineFD> linelist = ((BlockFlowDiagram)flowDiagram).getLineList();
	    //for(LineFD line : linelist) {
	    // 	System.out.println("LineFD :" );
	    //	System.out.println("    Source : " + line.getSource().getModel().getString("Type"));
	    //	System.out.println("    Terminal : " + line.getTerminal().getModel().getString("Type"));
	    //}
	    
	    // Set a common UndoManager for all Blocks.
	    this.blockFlowDiagram.setUndoManager(undoManager);
	    // Set a common NameCounterManager for all Blocks.
	    this.blockFlowDiagram.setNameCounterManager(nameManager);
	    
	    // Attach various listeners for blocks
	    this.blockFlowDiagram.addVariousMouseListeners();
	    
		/** FlowDiagram JScrollPane Construction **/
	    this.scrollablePanelForFD = new ScrollablePanelForFD((BlockFlowDiagram) this.blockFlowDiagram);
	    JViewport myViewport = new JViewport();
	    myViewport.setView(this.scrollablePanelForFD);
	    myViewport.setExtentSize(new Dimension(50,50));
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewport(myViewport);
	    
	    //Testing
	    myViewport.setBackground(Color.white);
	    
//	    myViewport.setExtentSize(new Dimension(50,50));
//		System.out.println(myViewport);
	    
	    
	    /** Left flowDiagram tool bar **/
	    this.fdToolBar = new FlowDiagramToolBar(this);
	    
	    /** Left Panel Construction **/
	    JPanel leftPanel = new JPanel(new BorderLayout());
	    leftPanel.add(this.fdToolBar, BorderLayout.NORTH);
	    leftPanel.add(scrollPane, BorderLayout.CENTER);
	    
	    /** Right Panel Construction **/
	    JPanel rightPanel = new JPanel(new BorderLayout());
	    this.codeToolBar = new CodeViewToolBar(this);
	    codeView = new JTextArea();
	    codeView.setFont(new Font("Courier New", Font.PLAIN,15));
	    codeView.setText(this.FlowDiagramModel.toString(10));
	    //TextFetcher txtFetcher = new TextFetcher(this.FlowDiagramModel,codeView);
	    //txtFetcher.start();
	    codeView.setEditable(true);
	    codeView.setTabSize(4);
	    JScrollPane textScrollPane = new JScrollPane(codeView);
	    rightPanel.add(codeToolBar, BorderLayout.NORTH);
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
	 * Separate initialisation of actual content from other gui component.
	 * Given a new model of flowDiagram, update everything.
	 * **/
	public void updateAllReferences() {
		/** Initialisation of various managers and user interfaces **/
	    this.undoManager = new UndoManager();
	    this.nameManager = new NameCounterManager();
	    
	    updateMenus();
	    updateBlockFlowDiagram();
	    updateBlockFunctions();
	    updateScrollablePanelForFD();
	    updateFlowDiagramToolBar();
	}
	
	private void updateMenus() {}
	private void updateBlockFlowDiagram() {
		BlockGenerator blockGenerator = new BlockGenerator();
		this.blockFlowDiagram = (CompositeBlockFD)blockGenerator.generate(this.FlowDiagramModel, this.FlowDiagramInfo);
		this.blockFlowDiagram.setAppropriateBounds();
		 // Set a common UndoManager for all Blocks.
	    this.blockFlowDiagram.setUndoManager(undoManager);
	    // Set a common NameCounterManager for all Blocks.
	    //System.out.println("Before exclusion : " + nameManager.getCurrentCount());
	    this.blockFlowDiagram.setNameCounterManager(nameManager);
	    nameManager.excludeExistingNames(this.FlowDiagramInfo);
	    
	    //System.out.println("After exclusion : " + nameManager.getCurrentCount());
	    // Attach various listeners for blocks
	    this.blockFlowDiagram.addVariousMouseListeners();
	}
	private void updateBlockFunctions() {}
	private void updateScrollablePanelForFD() {
		this.scrollablePanelForFD.setCompositeBlockFD(this.blockFlowDiagram);
	}
	private void updateFlowDiagramToolBar() {
		this.fdToolBar.setUndoManager(this.undoManager);
		this.fdToolBar.getZoomRatioLabel().setText(this.blockFlowDiagram.getCurrentZoomRatio()*100 + "%");
		//this.fdToolBar.setScrollablePanelForFD(this.scrollablePanelForFD);
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
		
		//Testing
		//list all FontNames
		/*
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for ( int i = 0; i < fonts.length; i++ ){
	      System.out.println(fonts[i]);
	    }
		*/
		
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new Flow2Code(); // Let the constructor do the job
	         }
	    });
	}
}

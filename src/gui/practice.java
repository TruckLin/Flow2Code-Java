package gui;
import java.awt.*;
import java.awt.event.KeyEvent;


import javax.swing.*;

import model.FD.FlowDiagram;
import model.FD.GraphicalInfoFD;

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
	    
	    /** JScrollPane Construction **/
	    FlowDiagram myDiagram =  new FlowDiagram("myDiagram","1","2");
		GraphicalInfoFD myInfo = new GraphicalInfoFD();
		myInfo.addBounds("1", new Rectangle(10,10,100,25));
		myInfo.addBounds("2", new Rectangle(10,100,100,20));
		
		PanelFD panelFD = new PanelFD(myDiagram, myInfo);
		panelFD.setBackground(Color.BLUE);
		panelFD.setSize(400,400);
		System.out.println(panelFD.getSize().toString());
	    
	    JScrollPane scrollPane = new JScrollPane(panelFD);
	    
	    /** JSplitPane Construction - we add scroll pane JSplitPane **/
	    JSplitPane splitPane = new JSplitPane();
	    splitPane.setDividerLocation(300);
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

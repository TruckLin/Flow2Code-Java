package gui;
import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class practice extends JFrame{
	
	private static final long serialVersionUID = 1L;
	// Private instance variables
	// ......
	
	public practice() {
		// Retrieve the top-level content-pane from JFrame
	      Container cp = getContentPane();
	 
	      // Content-pane sets layout
	      cp.setLayout(new FlowLayout());
	 
	      // Allocate the GUI components
	      // .....
	      
	      JScrollPane FDscrollpane = new JScrollPane();
	      FDscrollpane.setPreferredSize(new Dimension(300,300));
	      
	      FDviewport myViewport = new FDviewport();
	      FDscrollpane.setViewport(myViewport);	      
	      
	      //Testing
	      System.out.println(FDscrollpane.getHorizontalScrollBarPolicy() );
	      
	      // Content-pane adds components
	      cp.add(FDscrollpane);
	 
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

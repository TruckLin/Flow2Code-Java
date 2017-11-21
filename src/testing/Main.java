package testing;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import gui.object.BlockEndLOOP;
import gui.object.CompositeBlockFD;

public class Main extends JFrame implements MouseListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPopupMenu popup;
	JMenuItem menuItem;
	JLabel label;
	
	JDialog dialog;
	public Main() {
		// Retrieve the top-level content-pane from JFrame
	    Container cp = getContentPane();
	 
	    // Content-pane sets layout
	    cp.setLayout(null);
	    
	    // Allocate the GUI components
	    // .....
	    BlockEndLOOP endLoop = new BlockEndLOOP();
	    endLoop.setBounds(50,50,25,25);
	    
	    cp.add(endLoop);
	    endLoop.repaint();    
	    // Content-pane adds components
	    
	    //Testing findWichSideFunction of CompositeBlockFD
	    String boundString = "[ left = " + endLoop.getBounds().getMinX();
	    boundString = boundString + ", right = " + endLoop.getBounds().getMaxX();
	    boundString = boundString + ", top = " + endLoop.getBounds().getMinY();
	    boundString = boundString + ", bottom = " + endLoop.getBounds().getMaxY() + "]";
	    

	    
	    
	    
	    // Source object adds listener
	    // .....
	 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("JComponent Test");
	    setSize(500, 450);  // or pack()
	    setVisible(true);
	}
	

	public static void main(String[] args) {
		// Run GUI codes in Event-Dispatching thread for thread-safety
		
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new Main();  // Let the constructor do the job
	         }
	      });
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/** ActionListener interface **/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}

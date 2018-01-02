package testing;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import gui.object.BlockEndLOOP;
import gui.object.CompositeBlockFD;
import gui.object.LineFD;

public class Main extends JFrame implements MouseListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Main() {
		// Retrieve the top-level content-pane from JFrame
	    Container cp = getContentPane();
	 
	    // Content-pane sets layout
	    cp.setLayout(new FlowLayout());
	    
	    // Allocate the GUI components
	    // .....
	    JPanel myPanel = new JPanel() {
	    	@Override
	    	public void paintComponent(Graphics g) {
	    		super.paintComponent(g);
	    		Graphics2D g2 = (Graphics2D)g;
	    		
	    		g2.drawLine(0, 0, 200, 200);
	    	}
	    };
	    myPanel.setPreferredSize(new Dimension(200,200));
	    System.out.println(myPanel.isOpaque());
	    myPanel.setOpaque(false);
	    myPanel.setBackground(new Color(255,0,0,50));
	    myPanel.repaint();
	    cp.add(myPanel);
	    
	    
	    
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

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

public class MainFrame extends JFrame implements MouseListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MainFrame() {
	    
	    // Allocate the GUI components
	    // .....
	    JPanel myPanel = new JPanel(new FlowLayout());
	    myPanel.setPreferredSize(new Dimension(200,200));
	    
	    JCheckBoxMenuItem forBox = new JCheckBoxMenuItem("for");
	    JCheckBoxMenuItem whileBox = new JCheckBoxMenuItem("while");
	    JCheckBoxMenuItem ifBox = new JCheckBoxMenuItem("if");
	    JCheckBoxMenuItem declareBox = new JCheckBoxMenuItem("declare");
	    JCheckBoxMenuItem assignBox = new JCheckBoxMenuItem("assign");
	    
	    JMenuBar menuBar = new JMenuBar();
	    JMenu myMenu = new JMenu("Options");
	    myMenu.add(forBox);
	    myMenu.add(whileBox);
	    myMenu.add(ifBox);
	    myMenu.add(declareBox);
	    myMenu.add(assignBox);

	    menuBar.add(myMenu);
	    myPanel.add(menuBar);
	    
	    
	    this.setContentPane(myPanel);
	    
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
	            new MainFrame();  // Let the constructor do the job
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

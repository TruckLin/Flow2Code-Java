package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FDplane extends JPanel implements Scrollable, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	Component componentBeingDragged;
	Point topLeftToMouse;
	
	// Constructor
	public FDplane() {
		this.setLayout(null);
		this.setBackground(Color.blue);
		this.setPreferredSize(new Dimension(500,500));
		JLabel topleft = new JLabel("top left");
	    JLabel bottomright = new JLabel("bottom right");
	    topleft.setBackground(Color.yellow);
	    this.add(topleft);
	    this.add(bottomright);
	    topleft.setBounds(10, 10, 100, 50);
	    bottomright.setBounds(400, 400, 100, 50);
	    
	    this.addMouseListener(this);
	    this.addMouseMotionListener(this);	
	    
	    
	    
	}

	/**
	 *  Scrollable interface functions.
	 */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 *  MouseMotionListener interface functions.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
        int y = e.getY();
        
        int x2 = (int) (x - topLeftToMouse.getX());
        int y2 = (int) (y - topLeftToMouse.getY());
        this.componentBeingDragged.setLocation(x2, y2);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
        int y = e.getY();
        
        this.componentBeingDragged = this.getComponentAt(x,y);
        Point topLeft = componentBeingDragged.getLocation();
        
        int x2 = (int) (x - topLeft.getX());
        int y2 = (int) (y - topLeft.getY());
        this.topLeftToMouse = new Point( x2, y2);
        
        
        //Testing
        //System.out.println("Mouse left-clicked at ("+x+","+y+").");
        //System.out.println(topLeft.toString());
        System.out.println(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
    	//Testing
        //System.out.println("Mouse released at ("+x+","+y+").");
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

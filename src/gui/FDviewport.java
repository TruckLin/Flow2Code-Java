package gui;
import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class FDviewport extends JViewport{
	FDplane FlowDiagramPlane;
	
	public FDviewport(){
		ViewportListener listener = new ViewportListener();
		this.addMouseListener(listener);
	    this.addMouseListener(listener);
	    this.addMouseMotionListener(listener);
	    this.FlowDiagramPlane = new FDplane();
	    this.setView(this.FlowDiagramPlane);
	}
	
	private class ViewportListener extends MouseInputAdapter {
		
		public void mousePressed(MouseEvent e) {
	        int x = e.getX();
	        int y = e.getY();
	        
	        //Testing
	        System.out.println("Mouse leftclicked at ("+x+","+y+").");
	        
	    }

	    public void mouseDragged(MouseEvent e) {
	    	int x = e.getX();
	        int y = e.getY();
	    	//Testing
	        System.out.println("Mouse moved to ("+x+","+y+").");
	    }

	    public void mouseReleased(MouseEvent e) {
	    	int x = e.getX();
	        int y = e.getY();
	    	//Testing
	        System.out.println("Mouse released at ("+x+","+y+").");
	    }

	}
	
	
	
}

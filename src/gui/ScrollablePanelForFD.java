package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import org.json.JSONObject;

import gui.object.BlockFlowDiagram;
import gui.object.CompositeBlockFD;

public class ScrollablePanelForFD extends JPanel implements Scrollable {
	private CompositeBlockFD flowDiagram;
	
	private PropertyChangeListener listener = e -> {updateSize(); 
											//Testing
											//System.out.println("FlowDiagram update detected.");
											};
	
	public ScrollablePanelForFD(CompositeBlockFD flowDiagram) {
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(500,400));
		
		
		this.flowDiagram = flowDiagram;
		this.flowDiagram.addPropertyChangeListener(listener);
		
		this.add(this.flowDiagram);
		this.updateSize();
	}
	
	public void updateSize() {
		Point topleft = flowDiagram.getLocation();
		int width = (int)flowDiagram.getSize().getWidth();
		int height = (int)flowDiagram.getSize().getHeight();
		/*
		if(topleft.getX() < 0) {
			flowDiagram.setLocation(0,(int)topleft.getY());
		}
		if(topleft.getY() < 0) {
			flowDiagram.setLocation((int)topleft.getY(),0);
		}*/
		int x2 = (int)(topleft.getX() + width);
		int y2 = (int)(topleft.getY() + height);
		
		this.setPreferredSize(new Dimension(x2 + 5,y2 + 5));
		
		this.revalidate();
	}
	/** Getters and Setters **/
	public void setCompositeBlockFD(CompositeBlockFD comp) {
		if(this.flowDiagram != null) {
			this.remove(this.flowDiagram);
			this.flowDiagram.removePropertyChangeListener(listener);
		}
		this.flowDiagram = comp;
		if(this.flowDiagram != null) {
			this.add(this.flowDiagram);
			this.flowDiagram.addPropertyChangeListener(listener);
		}
		
		this.updateSize();
	}
	
	// Zoom function
	public void zoom( double newRatio) {
		this.flowDiagram.zoom(newRatio);
		this.updateSize();
	}
	
	/** Getters and Setter **/
	public double getCurrentZoomRatio() {
		return this.flowDiagram.getCurrentZoomRatio();
	}
	public JSONObject getFlowDiagramModel() {
		return this.flowDiagram.getModel();
	}
	
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

}

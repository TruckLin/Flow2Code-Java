package gui;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import graph.object.*;
import model.FD.FlowDiagram;
import model.FD.GraphicalInfoFD;
import model.object.*;

public class PanelFD extends JPanel implements Scrollable{
	
	private static final long serialVersionUID = 1L;
	
	private FlowDiagram model;
	private GraphicalInfoFD graphicalInfo;
	
	private PropertyChangeListener ListenerFD = e -> PaintFlowDiagram();
	
	/** Constructor **/
	public PanelFD(FlowDiagram fd, GraphicalInfoFD graphicalInfo){
		this.model = fd;
		this.model.addPropertyChangeListener(ListenerFD);
		this.graphicalInfo = graphicalInfo;
		this.graphicalInfo.addPropertyChangeListener(ListenerFD);
	}
	
	/** Paint the FlowDiagram **/
	public void PaintFlowDiagram() {
		for(ComponentFD comp : model.getComponentList()) {
			PaintComponentFD(comp);
		}
	}
	public void PaintComponentFD(ComponentFD comp) {
		if(comp instanceof ComponentSTART){
			comp = (ComponentSTART) comp;
			BlockSTART blockStart = new BlockSTART(comp.getName(), graphicalInfo.getBounds(comp.getName()));
			this.add(blockStart);
		}else if(comp instanceof ComponentEND) {
			comp = (ComponentEND) comp;
			BlockEND blockEnd = new BlockEND(comp.getName(), graphicalInfo.getBounds(comp.getName()));
			this.add(blockEnd);
		}else if(comp instanceof ComponentDECLARE) {
			
		}else if(comp instanceof ComponentASSIGN) {
			
		}else if(comp instanceof ComponentOUTPUT) {
			
		}else if(comp instanceof ComponentINPUT) {
			
		}else if (comp instanceof ComponentWHILE) {
			
		}else if( comp instanceof ComponentFOR) {
			
		}else if (comp instanceof ComponentIF) {
			
		}
	};
	
	
	
	
	/** Create line function 
	*	This function create lines between two BlockFD object.
	**/
	public LineFD createLine(BlockFD b1, BlockFD b2) {
		return new LineFD(b1, b2);
	}
	public void addLine(BlockFD b1, BlockFD b2) {
		this.add(new LineFD(b1, b2));
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
}

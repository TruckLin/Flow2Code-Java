package gui;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import graph.object.*;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import model.FD.FlowDiagram;
import model.FD.GraphicalInfoFD;
import model.object.*;

public class PanelFD extends JPanel implements Scrollable{
	
	private static final long serialVersionUID = 1L;
	
	private FlowDiagram model;
	private GraphicalInfoFD graphicalInfo;
	
	private PropertyChangeListener ListenerFD;
	
	/** Constructor **/
	public PanelFD(FlowDiagram fd){
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(500,500));
		
		this.model = fd;
		this.model.addPropertyChangeListener(ListenerFD);
		
		PaintComponentFD((ComponentFD)fd);
	}
	public PanelFD(FlowDiagram fd, GraphicalInfoFD graphicalInfo){
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.setPreferredSize(new Dimension(500,500));
		
		this.model = fd;
		this.model.addPropertyChangeListener(ListenerFD);
		this.graphicalInfo = graphicalInfo;
		this.graphicalInfo.addPropertyChangeListener(ListenerFD);
		
		PaintComponentFD((ComponentFD)fd);
	}
	
	/** Paint the FlowDiagram **/
	public BlockFD PaintComponentFD(ComponentFD comp){
		if(comp instanceof FlowDiagram) {
			
			int len = ((FlowDiagram)comp).getComponentList().size();
			BlockFD previousBlock = null;
			BlockFD currentBlock = null;
			for (int i = 0; i < len; i++) {
				ComponentFD temp = ((FlowDiagram)comp).getComponentList().get(i);
				currentBlock = PaintComponentFD(temp);
				if(previousBlock != null) {
					addLine((WithOutport)previousBlock, (WithInport)currentBlock);
				}
				previousBlock = currentBlock;
			}
			//Testing
			//System.out.println(len);
			
		}else if(comp instanceof ComponentSTART){
			BlockSTART blockStart = new BlockSTART((ComponentSTART) comp, graphicalInfo.getBounds(comp.getName()));
			this.add(blockStart);
			
			//Testing
			//System.out.println("blockStart is created.");
			
			return blockStart;
			
		}else if(comp instanceof ComponentEND){
			BlockEND blockEnd = new BlockEND((ComponentEND)comp, graphicalInfo.getBounds(comp.getName()));
			this.add(blockEnd);
			
			return blockEnd;
		}else if(comp instanceof ComponentDECLARE) {
			BlockDECLARE blockDeclare = new BlockDECLARE((ComponentDECLARE)comp , graphicalInfo.getBounds(comp.getName()));
			this.add(blockDeclare);
			
			return blockDeclare;
		}else if(comp instanceof ComponentASSIGN) {
			BlockASSIGN blockAssign = new BlockASSIGN((ComponentASSIGN)comp , graphicalInfo.getBounds(comp.getName()));
			this.add(blockAssign);
			
			return blockAssign;
		}else if(comp instanceof ComponentOUTPUT) {
			BlockOUTPUT blockOutput = new BlockOUTPUT((ComponentOUTPUT)comp , graphicalInfo.getBounds(comp.getName()));
			this.add(blockOutput);
			
			return blockOutput;
		}else if(comp instanceof ComponentINPUT) {
			BlockINPUT blockInput = new BlockINPUT((ComponentINPUT)comp , graphicalInfo.getBounds(comp.getName()));
			this.add(blockInput);
			
			return blockInput;
		}else if (comp instanceof ComponentWHILE) {
			BlockWHILE blockWhile = new BlockWHILE( (ComponentWHILE)comp, graphicalInfo.getBounds(comp.getName()) ) ;
			this.add(blockWhile);
			
			int len = ((ComponentWHILE)comp).getComponentList().size();
			BlockFD previousBlock = blockWhile.getBlockStartLoop();
			BlockFD currentBlock = null;
			
			for (int i = 1; i < len; i++) {
				ComponentFD temp = ((ComponentWHILE)comp).getComponentList().get(i);
				currentBlock = PaintComponentFD(temp);

				if(previousBlock != null) {
					
					Point p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
					Point p2 = currentBlock.toContainerCoordinate(((WithInport)currentBlock).getInport());
					
					if(previousBlock instanceof BlockStartLOOP) {
						p1 = blockWhile.toContainerCoordinate(((BlockStartLOOP) previousBlock).getOutport());
					}
					
					addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
				}
				previousBlock = currentBlock;
			}
			
			currentBlock = blockWhile.getBlockStartLoop();
			
			Point p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
			Point p2 = currentBlock.toContainerCoordinate(((WithInport)currentBlock).getInport());
			
			if(previousBlock instanceof BlockStartLOOP) {
				p1 = blockWhile.toContainerCoordinate(((BlockStartLOOP) previousBlock).getOutport());
			}
			if(currentBlock instanceof BlockStartLOOP) {
				p2 = blockWhile.toContainerCoordinate(((BlockStartLOOP) currentBlock).getInport());
			}
			
			addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
			
			return blockWhile;
			
		}else if( comp instanceof ComponentFOR) {
			BlockFOR blockFor = new BlockFOR( (ComponentFOR)comp, graphicalInfo.getBounds(comp.getName()) ) ;
			this.add(blockFor);
			
			int len = ((ComponentFOR)comp).getComponentList().size();
			BlockFD previousBlock = blockFor.getBlockStartLoop();
			BlockFD currentBlock = null;
			
			for (int i = 1; i < len; i++) {
				ComponentFD temp = ((ComponentFOR)comp).getComponentList().get(i);
				currentBlock = PaintComponentFD(temp);

				if(previousBlock != null) {
					
					Point p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
					Point p2 = currentBlock.toContainerCoordinate(((WithInport)currentBlock).getInport());
					
					if(previousBlock instanceof BlockStartLOOP) {
						p1 = blockFor.toContainerCoordinate(((BlockStartLOOP) previousBlock).getOutport());
					}
					
					addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
				}
				previousBlock = currentBlock;
			}
			
			currentBlock = blockFor.getBlockStartLoop();
			
			Point p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
			Point p2 = currentBlock.toContainerCoordinate(((WithInport)currentBlock).getInport());
			
			if(previousBlock instanceof BlockStartLOOP) {
				p1 = blockFor.toContainerCoordinate(((BlockStartLOOP) previousBlock).getOutport());
			}
			if(currentBlock instanceof BlockStartLOOP) {
				p2 = blockFor.toContainerCoordinate(((BlockStartLOOP) currentBlock).getInport());
			}
			addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
			
			return blockFor;
		}else if (comp instanceof ComponentIF) {
			BlockIF blockIf = new BlockIF((ComponentIF) comp);
			this.add(blockIf);
			
			// Temporary
			blockIf.setLocation(100,250);
			
			
			//================================ falseList ======================================================
			ArrayList<ComponentFD> falseList =  ((ComponentIF)comp).getFalseSubDiagram().getComponentList();
			int len1 = falseList.size();
			BlockFD previousBlock = blockIf.getBlockStartFalseIF();
			BlockFD currentBlock = null;
			Point p1;
			Point p2;
			for (int i = 1; i < len1-1; i++) {
				ComponentFD temp = falseList.get(i);
				currentBlock = PaintComponentFD(temp);
				
				if(previousBlock != null) {
					p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
					p2 = currentBlock.toContainerCoordinate(((WithInport)currentBlock).getInport());
					
					if(previousBlock instanceof BlockSTART) {
						p1 = blockIf.getStartFalseOutportpt();
					}
					
					addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
				}
				previousBlock = currentBlock;
			}
			
			currentBlock = blockIf.getBlockEndFalseIF();
			p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
			if(previousBlock instanceof BlockSTART) {
				p1 = blockIf.getStartFalseOutportpt();
			}
			p2 = blockIf.getEndFalseInportpt();
			
			addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
			
			
			//================================ trueList ========================================================
			ArrayList<ComponentFD> trueList =  ((ComponentIF)comp).getTrueSubDiagram().getComponentList();
			int len2 = trueList.size();
			previousBlock = blockIf.getBlockStartTrueIF();
			currentBlock = null;
 
			for (int i = 1; i < len2-1; i++) {
				ComponentFD temp = trueList.get(i);
				currentBlock = PaintComponentFD(temp);
				
				if(previousBlock != null) {
					p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
					p2 = currentBlock.toContainerCoordinate(((WithInport)currentBlock).getInport());
					
					if(previousBlock instanceof BlockSTART) {
						p1 = blockIf.getStartTrueOutportpt();
					}
					
					addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
				}
				previousBlock = currentBlock;
			}
			currentBlock = blockIf.getBlockEndTrueIF();
			p1 = previousBlock.toContainerCoordinate(((WithOutport)previousBlock).getOutport());
			if(previousBlock instanceof BlockSTART) {
				p1 = blockIf.getStartTrueOutportpt();
			}
			p2 = blockIf.getEndTrueInportpt();
			
			addLine((WithOutport)previousBlock, (WithInport)currentBlock, p1, p2);
			
			return blockIf;
			
		}else {
			return null;
		}
		
		return null;
	}
	
	
	/** Create line function 
	*	This function create lines between two BlockFD object.
	**/
	public LineFD createLine(WithOutport b1, WithInport b2) {
		return new LineFD(b1, b2);
	}
	public void addLine(WithOutport b1, WithInport b2) {
		this.add(new LineFD(b1, b2));
	}
	private void addLine(WithOutport b1, WithInport b2, Point p1, Point p2) {
		this.add(new LineFD(b1,b2,p1,p2));
		
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

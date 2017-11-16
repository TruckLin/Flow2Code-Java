package gui.object;

import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.mouselistener.MouseEnterListener;
import gui.interfaces.WithInport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockStartIF extends BlockFD implements WithInport{
	private JLabel displayLabel;
	
	private Point inport;
	
	private BlockStartTrueIF blockStartTrueIF;
	private BlockStartFalseIF blockStartFalseIF;
	
	private PropertyChangeListener StartIFPropertyListener = 
			e -> {Point trueOutport = new Point(BlockStartIF.this.getWidth(),
												Math.round(BlockStartIF.this.getHeight()/2));
				  trueOutport = BlockStartIF.this.toContainerCoordinate(trueOutport);
				  blockStartTrueIF.setLocation( trueOutport );
				  Point falseOutport = new Point( 0 , Math.round(BlockStartIF.this.getHeight()/2));
				  falseOutport = BlockStartIF.this.toContainerCoordinate(falseOutport);
				  blockStartFalseIF.setLocation(falseOutport);
				  
				  //Testing
			/*	  System.out.println("");
				  System.out.println("blockStartIF change detected.");
				  System.out.println("    PropertyName = " + e.getPropertyName());
				 // System.out.println("    Old value = " + e.getOldValue().toString());
				  System.out.println("    New value = " + e.getNewValue().toString());
				  System.out.println(""); */
				 };
	
	public BlockStartIF(JSONObject model){
		super(model);
		
		//Testing
		//System.out.println("\nStart of the constructor of BlockStartIF : ");
		
		
		this.removeAll();
		this.setLayout(null);
		
		// Initialise inport
		this.inport = new Point( Math.round(this.getWidth()/2), 0);
		
		// Set Default bounds
		this.setBounds(0,0,100,25);
		
		// set the Outport panels
		blockStartTrueIF = new BlockStartTrueIF(null);
		blockStartTrueIF.setBlockStartIF(this);
		blockStartFalseIF = new BlockStartFalseIF(null);
		blockStartFalseIF.setBlockStartIF(this);
		Point trueOutport = new Point(this.getWidth(), Math.round(this.getHeight()/2));
		trueOutport = BlockStartIF.this.toContainerCoordinate(trueOutport);
		blockStartTrueIF.setLocation( trueOutport );
		Point falseOutport = new Point( 0 , Math.round(this.getHeight()/2));
		falseOutport = BlockStartIF.this.toContainerCoordinate(falseOutport);
		blockStartFalseIF.setLocation(falseOutport);
		
		// Add a listener that change the border of it's parent when mouse enter.
		MouseEnterListener mouseEnter = new MouseEnterListener(this);
		mouseEnter.setSouldChangeParentBlock(true);
		this.addMouseListener(mouseEnter);
		
		//Testing
/*		System.out.println("startIF.getBounds = " + this.getBounds().toString());
		System.out.println("blockStartTrueIF.getTrueOutport = " + this.getTrueOutport());
		System.out.println("blockStartFalseIF.getFalseOutport = " + this.getFalseOutport()); */
		
		
		
		// Make sure we change outport panels' position when BlockStartIF's propertyChanged.
		this.addPropertyChangeListener(StartIFPropertyListener);
		
		// Temporary
		this.displayLabel = new JLabel("StartIF");
		this.add(this.displayLabel);
		this.displayLabel.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters for ports**/
	public Point getTrueOutport(){
		Point p = blockStartTrueIF.toContainerCoordinate(blockStartTrueIF.getOutport());
		p = new Point( (int)(p.getX()-this.getLocation().getX()), (int)(p.getY()-this.getLocation().getY()));
		return p;
	}
	public void setTrueOutport(Point p){
		blockStartTrueIF.setLocation(p);
	}
	public Point getFalseOutport(){
		Point p = blockStartFalseIF.toContainerCoordinate(blockStartFalseIF.getOutport());
		p = new Point( (int)(p.getX()-this.getLocation().getX()), (int)(p.getY()-this.getLocation().getY()));
		return p;
	}
	public void setFalseOutport(Point p){
		blockStartFalseIF.setLocation(p);
	}
	
	public BlockStartTrueIF getBlockStartTrueIF() {
		return this.blockStartTrueIF;
	}
	public BlockStartFalseIF getBlockStartFalseIF() {
		return this.blockStartFalseIF;
	}
	
	@Override
	public Point getInport() {
		// TODO Auto-generated method stub
		return this.inport;
	}
	@Override
	public void setInport(Point p) {
		// TODO Auto-generated method stub
		this.inport = p;
	}
	
	/**    Getters and Setter for label    **/
	
	public JLabel getDisplayLabel() {
		return this.displayLabel;
	}
	public void setDiaplyLabel(JLabel temp) {
		if(temp != null) {
			this.remove(this.displayLabel);
			this.displayLabel = temp;
			this.add(temp);
		}
	}
	/** override abstract methods**/
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}
	@Override
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
	}
	
	@Override
	protected boolean isCompositeBlockFD() {
		return false;
	}
	
	@Override
	protected boolean shouldAddBlockDrag() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean shouldAddLoopDrag() {
		return true;
	}
	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldAddBlockRightClick() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		// do nothing.
	}
	
	
	
	
	
}

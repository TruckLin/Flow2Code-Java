package gui.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.ForEditDialog;
import gui.editDialog.IfEditDialog;
import gui.manager.UndoManager;
import gui.mouselistener.MouseEnterListener;

public class BlockIF extends OrdinaryCompositeBlockFD{
	private BlockStartIF blockStartIF;
	private BlockEndIF blockEndIF;
	
	private IfEditDialog editDialog;
	private Color bgColor = new Color(204,153,255,80);
	private Color labelColor = new Color(217, 179, 255,255);
	// This listener listen to the change in bounds and move BlockEndLOOP to the correct place.
	private PropertyChangeListener MoveBlockEndLoopListener = 
			e -> {  // Find out the maximum Y coordinate without BlockEndLOOP.
					Component[] compList = BlockIF.this.getComponents();
					double parentMaxY = Double.MIN_VALUE;
					for(Component comp : compList) {
						if(comp == blockEndIF) continue;
						else {
							if(parentMaxY < comp.getBounds().getMaxY())
								parentMaxY = comp.getBounds().getMaxY();
						}
					}
					if( blockEndIF != null && blockEndIF.getBounds().getMaxY() < parentMaxY) {
						int h = BlockIF.this.getHeight() - blockEndIF.getHeight();
						blockEndIF.setLocation((int)blockEndIF.getLocation().getX(),h);
					}
						
					//Testing
					//System.out.println("MoveBlockEndLoopListener triggered.");
					//System.out.println("height = " + h);
				};
	
	public BlockIF(JSONObject model) {
		super(model);

		this.setOpaque(false); // we should always see through this while panel.;
		//Set various default property
		this.setSize(110,110);
		this.setLayout(null);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		//UndoManager undoManager = new UndoManager();
		//DemoMouseListener myListener = new DemoMouseListener(undoManager,this);
		//this.addMouseMotionListener(myListener);
		//this.addMouseListener(myListener);
		
		//Testing for transparent background.
		//this.setOpaque(true);
		//this.setBackground(Color.CYAN);
		
		// Add listener that change the position of BlockEndLOOP, order is important,
		// it needs to be put after setBounds or any setters of the component.
		this.addPropertyChangeListener(MoveBlockEndLoopListener);
	}
	

	
	
	/** Getters and Setters **/
	public BlockStartIF getBlockStartIF() {
		return this.blockStartIF;
	}
	public void setBlockStartIF(BlockStartIF comp) {
		if (this.blockStartIF != null) {
			// disconnect from previous model
			this.blockStartIF.removePropertyChangeListener(listener);
		}
		this.blockStartIF = comp;
		if (this.blockStartIF != null) {
			// connect to new model
			this.blockStartIF.addPropertyChangeListener(listener);
			
			updateBlockContent();
			
			// initialize fields in the UI
			updateInport();
		}
	}
	public BlockEndIF getBlockEndIF() {
		return this.blockEndIF; 
	}
	public void setBlockEndIF(BlockEndIF comp) {
		if (this.blockEndIF != null) {
			// disconnect from previous model
			this.blockEndIF.removePropertyChangeListener(listener);
		}
		this.blockEndIF = comp;
		if (this.blockEndIF != null) {
			// connect to new model
			this.blockEndIF.addPropertyChangeListener(listener);

			// initialize fields in the UI
			updateOutport();
		}
	}
	public String getExpression() {
		return this.getModel().getString("Expression");
	}
	
	/** Utilities **/
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//g2.setColor(bgColor);
		//g2.fillRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
		
		//change font size
		//g2.setFont(new Font("TimesRoman", Font.PLAIN, 18)); 
		//g2.drawString("false", 0, this.getHeight());
		//g2.drawString("true", this.getWidth()-20, this.getHeight());
	}
	@Override
	public void updateInport() {
	//	Point oldPoint = this.getInport();
		if(this.getBlockStartIF() != null) {
			Point p = this.getBlockStartIF().toContainerCoordinate(this.getBlockStartIF().getInport().getPortLocation());
			this.getInport().setPortLocation(p);
		//	this.getPropertyChangeSupport().firePropertyChange("Inport", oldPoint, this.getInport());
		}
	}

	@Override
	public void updateOutport() {
	//	Point oldPoint = this.getOutport();
		if(this.getBlockEndIF() != null) {
			Point p = this.getBlockEndIF().toContainerCoordinate(this.getBlockEndIF().getOutport().getPortLocation());
			this.getOutport().setPortLocation(p);
	//		this.getPropertyChangeSupport().firePropertyChange("Outport", oldPoint, this.getOutport());
		}
	}

	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		String expression = this.getExpression();
		if(expression == "" || expression.isEmpty()) {
			expression = "If(  )";
		}else {
			expression = "If(...)";
		}
		String displayString = (expression);//"If( " + this.getExpression() + " )");
		this.blockStartIF.getBlockLabel().setText(displayString);
		this.blockStartIF.getBlockLabel().setOpaque(false);
		this.blockStartIF.getBlockLabel().setBackground(labelColor);
		this.blockStartIF.adjustLabelSize();
		this.blockStartIF.adjustBlockSizeByLabel();
		this.blockStartIF.adjustLabelLocation();
		
		this.setAppropriateBounds();
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new IfEditDialog(undoManager, this);
		return this.editDialog;
	}
}

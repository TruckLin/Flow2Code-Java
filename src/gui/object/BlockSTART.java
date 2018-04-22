package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockSTART extends BlockFD implements WithOutport{
	private PortFD outport = new PortFD(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()), "bottom");
	private Color bgColor = new Color(166,166,166,255);
	private Color fontColor = new Color(255,255,255,255);
	/** Constructors **/
	public BlockSTART(JSONObject model){
		super(model);
		
		//this.setBounds(0, 0, 25, 25);
		
		this.blockLabel.setText(" ");
		this.adjustBlockSizeByLabel();
		this.adjustLabelSize();
		this.adjustLabelLocation();
		blockLabel.setOpaque(false);
		blockLabel.setForeground(fontColor);
		this.setBackground(null);
		//this.add(blockLabel);
		// Temporary
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
/*		Font myFont = blockLabel.getFont();
		System.out.println("Font Name : " + myFont.getFontName());
		System.out.println("myFont.getName()= " + myFont.getName() );
		System.out.println("Style : " + myFont.getStyle());
		System.out.println("Size : " + myFont.getSize());
		//blockLabel.setFont(new Font(myFont.getFontName(), Font.PLAIN, myFont.getSize())); 
		System.out.println("Label preferred size : " + blockLabel.getPreferredSize());*/
	}
	@Override
	public void adjustBlockSizeByLabel() {
		super.adjustBlockSizeByLabel();
		int minWidth = (int)Math.round(100*this.currentZoomRatio);
		int minHeight = (int)Math.round(25*this.currentZoomRatio);
		
		boolean sizeShouldChange = false;
		
		int newWidth = minWidth;
		int newHeight = minHeight;
		

		this.blockLabel.setMaximumSize(new Dimension(20,30));
		Dimension labelDimension = this.blockLabel.getMaximumSize();
		
		// We need to also deal with the case when text label got shorter. BlockSize needs to shrink.
		int x = (int)this.getLocation().getX();
		int y = (int)this.getLocation().getY();
		this.setBounds(x,y, minWidth , minHeight);
		
		if(labelDimension.getWidth() > minWidth) {
			newWidth = (int)labelDimension.getWidth();
			sizeShouldChange = true;
		}
		if(labelDimension.getHeight() > minHeight){
			newHeight = (int)labelDimension.getHeight();
			sizeShouldChange = true;
		}
		
		/** Mark it always true as supervisor requested.**/
		sizeShouldChange = true;
		newWidth = (int) labelDimension.getWidth();
		newHeight = (int) labelDimension.getHeight();
		
		if(sizeShouldChange) {
			this.setBounds(x,y,
					newWidth + (int)Math.round(10*this.currentZoomRatio),
					newHeight + (int)Math.round(0*this.currentZoomRatio));
		}
	}
	@Override
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0));
	    g2.fillOval(0, 0, this.getWidth(), this.getHeight());
	}
	
	/** Getters and Setters **/

	@Override
	public PortFD getOutport() {
		// TODO Auto-generated method stub
		return this.outport;
	}
	@Override
	public void setOutport(PortFD p) {
		// TODO Auto-generated method stub
		this.outport = p;
	}
	
	/** override abstract methods**/
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}
	
	@Override
	protected void updatePorts() {
		// reset outport
		this.outport.setPortLocation(new Point( Math.round(this.getWidth()/2), this.getHeight()-1) );
	}

	@Override
	protected boolean isCompositeBlockFD() {
		return false;
	}
	
	@Override
	protected boolean shouldAddBlockDrag() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean shouldAddLoopDrag() {
		return false;
	}
	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
	}

	@Override
	protected boolean shouldAddBlockRightClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		
	}
	
}

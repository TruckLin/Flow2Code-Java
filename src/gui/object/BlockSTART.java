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
	
	/** Constructors **/
	public BlockSTART(JSONObject model){
		super(model);
		
		this.setBounds(0, 0, 100, 25);
		
		this.blockLabel.setText("Start");
		this.adjustBlockSizeByLabel();
		this.adjustLabelSize();
		this.adjustLabelLocation();
		this.add(blockLabel);
		
		// Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
/*		Font myFont = blockLabel.getFont();
		System.out.println("Font Name : " + myFont.getFontName());
		System.out.println("myFont.getName()= " + myFont.getName() );
		System.out.println("Style : " + myFont.getStyle());
		System.out.println("Size : " + myFont.getSize());
		//blockLabel.setFont(new Font(myFont.getFontName(), Font.PLAIN, myFont.getSize())); 
		System.out.println("Label preferred size : " + blockLabel.getPreferredSize());*/
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

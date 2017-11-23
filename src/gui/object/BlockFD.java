package gui.object;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.mouselistener.BlockDragListener;
import gui.mouselistener.BlockRightClickListener;
import gui.mouselistener.EndLoopDragListener;
import gui.mouselistener.LoopDragListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class BlockFD extends JPanel{
	
	private JSONObject model;
	protected UndoManager undoManager;
	protected NameCounterManager nameManager;
	
	/** Constructors **/
	public BlockFD(JSONObject model) {
		super();
		this.model = model;
		this.setLayout(null);
		
		// Temporary
		this.setSize(100,25);
		this.setOpaque(false);
		
	}
	
	
	/** Getters and Setters **/
	public JSONObject getModel() {
		return this.model;
	}
	public void setModel(JSONObject model) {
		this.model = model;
	}
	public void setLocation(int x, int y) {
		Point oldValue = this.getLocation();
		super.setLocation(x, y);
		this.firePropertyChange("Location", oldValue, new Point(x,y));
	}
	public void setLocation(Point p) {
		Point oldValue = this.getLocation();
		super.setLocation(p);
		this.firePropertyChange("Location", oldValue, p);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		Rectangle oldValue = this.getBounds();
		super.setBounds(x, y, width, height);
		this.firePropertyChange("Bounds", oldValue, this.getBounds());
	}
	public void setBounds(Rectangle rec) {
		Rectangle oldValue = this.getBounds();
		super.setBounds(rec);
		this.firePropertyChange("Bounds", oldValue, rec);
	}
	
	public UndoManager getUndoManager() {
		return this.undoManager;
	}
	
	public abstract void setUndoManager(UndoManager undoManager);
	
	public NameCounterManager getNameCounterManager() {
		return this.nameManager;
	}
	
	public abstract void setNameCounterManager(NameCounterManager nameManager);
	
	/** Event handling functions **/
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		// this function should be implemented by various block like BlockIF, BlockWHILE and many more.
		return null;
	}
	
	/** Utility functions **/
	public abstract void updateBlockContent() ;
	
	
	public Point toContainerCoordinate(Point coordWRTblock) {
		int x = (int)(this.getLocation().getX() + coordWRTblock.getX());
		int y = (int)(this.getLocation().getY() + coordWRTblock.getY());
		return new Point(x,y);
	}
	
	
	/** Move the block by displacement **/
	public void translateLocation(int dx, int dy) {
		int x = (int)this.getLocation().getX();
		int y = (int)this.getLocation().getY();
		
		x = x + dx;
		y = y + dy;
		
		this.setLocation(x, y);
	}
	
	
	/** Methods that attach listeners to Blocks **/
	public void addVariousMouseListeners() {
		//Testing
		//System.out.println("addVariousMouseListeners() is called by : " + this.getClass());
		
		if(isCompositeBlockFD()) {
			Component[] componentList = this.getComponents();
			for(Component comp:componentList ) {
				if(comp instanceof BlockFD) {
					((BlockFD)comp).addVariousMouseListeners();
				}
			}
		}
		
		if(this.shouldAddBlockDrag()) {
			BlockDragListener lis = new BlockDragListener(this.undoManager, this);
			this.addMouseMotionListener(lis);
			this.addMouseListener(lis);
		}
		
		if(this.shouldAddBlockRightClick()) {
			BlockRightClickListener lis = new BlockRightClickListener(this.undoManager,this);
			this.addMouseListener(lis);
		}
		
		if(this.shouldAddLoopDrag()) {
			LoopDragListener lis = new LoopDragListener(this.undoManager, this);
			this.addMouseMotionListener(lis);
			this.addMouseListener(lis);
		}
		
		if(this.shouldAddEndLoopDrag()) {
			EndLoopDragListener lis = new EndLoopDragListener(this.undoManager, this);
			this.addMouseMotionListener(lis);
			this.addMouseListener(lis);
		}
		
		
		
		// Testing
		//System.out.println("end by : " + this.getClass());
	}
	protected abstract boolean isCompositeBlockFD();
	protected abstract boolean shouldAddBlockDrag();
	protected abstract boolean shouldAddBlockRightClick();
	protected abstract boolean shouldAddLoopDrag();
	protected abstract boolean shouldAddEndLoopDrag();
	
	public abstract boolean isEditable();
	public abstract boolean representCompositeBlock(); // indicate whether a block represents a larger, composite blocks.
													   // BlockStartLOOP and BlockStartIF are the only two block that returns true.	
	
}

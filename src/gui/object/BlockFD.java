package gui.object;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.*;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.mouselistener.BlockDragListener;
import gui.mouselistener.BlockRightClickListener;
import gui.mouselistener.EndLoopDragListener;
import gui.mouselistener.LoopDragListener;

import java.beans.PropertyChangeListener;

public abstract class BlockFD extends JPanel{
	
	private JSONObject model;
	protected UndoManager undoManager;
	protected NameCounterManager nameManager;
	
	protected double currentZoomRatio;
	
	protected JLabel blockLabel = new JLabel("");
	
	protected PropertyChangeListener updatePortsListener = e -> updatePorts();
	
	protected final int textSize = 20;
	
	//I18N
	protected ResourceBundle languageBundle;
	
	/** Constructors **/
	public BlockFD(JSONObject model) {
		super();
		this.model = model;
		this.setLayout(null);
		
		this.setOpaque(false);
		
		this.addPropertyChangeListener(updatePortsListener);
		
		this.currentZoomRatio = 1;
		
		this.blockLabel.setFont(new Font("Courier New", Font.PLAIN, 
									(int)Math.round(this.textSize*this.currentZoomRatio)));
		this.adjustLabelSize();
		this.adjustLabelLocation();
		
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
	
	public JLabel getBlockLabel() {
		return this.blockLabel;
	}
	public void setBlockLabel(JLabel temp) {
		if(temp != null) {
			this.remove(this.blockLabel);
			this.blockLabel = temp;
			this.add(temp);
		}
	}
	public ResourceBundle getLanguageBundle() {
		return this.languageBundle;
	}
	public void setLanguageBundle(ResourceBundle languageBundle) {
		//Testing
		//System.out.println("In setLanguageBundle() : ");
		//System.out.println("    Block Type : " + this.getModel().getString("Type"));
		//System.out.println("    LanguageBundle.toString() = " + languageBundle.toString());
		//System.out.println("    LanguageBundle.getString(\"Delete\") = " + languageBundle.getString("Delete"));
		
		this.languageBundle = languageBundle;
		if(this instanceof CompositeBlockFD) {
			for(Component comp : this.getComponents()) {
				((BlockFD)comp).setLanguageBundle(languageBundle);
			}
		}
		this.updateBlockContent();
	}
	// This method controls which statements should be generated.
	public void setCodeGenForAll(String type, boolean should) {
		//Testing
		//System.out.println("Class = " + this.getClass());
		//System.out.println("setCodeGenForAll is called.");
		//System.out.println("should CodeGen = " + should);
		
		if(this.model == null) {
			//Testing
			//System.out.println("model is null for this block.");
			return;
		}
		String blockType = this.model.getString("Type");
		
		//Testing
		//System.out.println("Type = " + type + " , BlockType = " + blockType);
		if(type.equals(blockType)) {
			this.getModel().put("CodeGen", should);
			//Testing
			//System.out.println("I think we have put CodeGen in.");
		}
		
		// If the current Block is composite, call setCodeGenForAll for the children.
		if(this instanceof CompositeBlockFD) {
			Component[] complist = ((CompositeBlockFD)this).getComponents();
			for(Component comp : complist) {
				if(comp instanceof BlockFD) {
					((BlockFD)comp).setCodeGenForAll(type, should);
				}
			}
		}
		
	}
	
	
	/* Abstract method : updatePorts()
	 * This should be implemented by all BlockFD, either do nothing, or update port location.
	 */
	protected abstract void updatePorts();
	
	/* Abstract method : generateVariableTree()
	 * For each block, return its variable tree representation, it could be VariableLeaf or VariableBranch.
	 * */
	//public abstract VariableTree generateVariableTree();
	
	public abstract void setUndoManager(UndoManager undoManager);
	
	public NameCounterManager getNameCounterManager() {
		return this.nameManager;
	}
	public abstract void setNameCounterManager(NameCounterManager nameManager);
	
	public double getCurrentZoomRatio() {
		return this.currentZoomRatio;
	}
	public void setCurrentZoomRatio(double zr) {
		this.currentZoomRatio = zr;
	}
	
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
	
	public JSONObject getGraphicalInfo() {
		JSONObject loc = new JSONObject();
		loc.put("x", (int)Math.round(this.getLocation().getX()/this.currentZoomRatio));
		loc.put("y", (int)Math.round(this.getLocation().getY()/this.currentZoomRatio));
		JSONObject ans = new JSONObject();
		ans.put(this.getModel().getString("Name"), loc);
		return ans;
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
	
	
	/** Zoom Function **/
	public void zoom(double newRatio) {
		Rectangle oldRec = this.getBounds();
		int x = (int) Math.round(oldRec.getX()*newRatio/currentZoomRatio);
		int y = (int) Math.round(oldRec.getY()*newRatio/currentZoomRatio);
		int width = (int) Math.round(oldRec.getWidth()*newRatio/currentZoomRatio);
		int height = (int) Math.round(oldRec.getHeight()*newRatio/currentZoomRatio);
		Rectangle newRec = new Rectangle(x,y,width,height);
		this.setBounds(newRec);

		if(this.isCompositeBlockFD()) {
			Component[] comps = this.getComponents();
			for(Component comp : comps) {
				((BlockFD)comp).zoom(newRatio);
			}
		}
		
		this.zoomLabel(newRatio);
		// After all the blocks have been zoomed, update currentZoomRatio.
		this.setCurrentZoomRatio(newRatio);
	}
	
	public void zoomLabel(double newRatio) {
		if(!blockLabel.getText().equals("")) {
			Font myFont = this.blockLabel.getFont();
			this.blockLabel.setFont(new Font(myFont.getFontName(), Font.PLAIN, 
										(int)Math.round(myFont.getSize()*newRatio/this.currentZoomRatio)));
			this.adjustLabelSize();
			this.adjustLabelLocation();
		}
		
	}
	
	public void adjustLabelSize() {
		Dimension labelDimension = this.blockLabel.getPreferredSize();
		this.blockLabel.setSize(labelDimension);
	}
	
	// adjustLabelLocation() should always be called after adjustLabelSize() and adjestBlockSize()
	public void adjustLabelLocation() {
		
		int width = this.getWidth();
		int height = this.getHeight();
		Dimension labelDimension = this.blockLabel.getPreferredSize();
		Point newLocation = new Point( (int)Math.round(width/2 - labelDimension.getWidth()/2), 
									   (int)Math.round(height/2 - labelDimension.getHeight()/2));
		
		//Testing
	//	if(this.blockLabel.getText().startsWith("I")) {
	//		System.out.println(this.blockLabel.getText());
	//	}
		
		this.blockLabel.setLocation(newLocation);
	}
	
	public void adjustBlockSizeByLabel() {
		int minWidth = (int)Math.round(100*this.currentZoomRatio);
		int minHeight = (int)Math.round(25*this.currentZoomRatio);
		
		boolean sizeShouldChange = false;
		
		int newWidth = minWidth;
		int newHeight = minHeight;
		
		
		Dimension labelDimension = this.blockLabel.getPreferredSize();
		
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
	

}

package gui.object;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.WhileEditDialog;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.UndoManager;


public class BlockWHILE extends BlockLOOPFD{
	
	private WhileEditDialog editDialog;
	
	public BlockWHILE(JSONObject model){
		super(model);
	}
	
	/** Getters and Setters **/	
	public String getExpression() {
		return this.getModel().getString("Expression");
	}
	
	/** EventHandling functions **/
	@Override
	public void updateBlockContent() {
		String displayString = ("While( " + this.getExpression() + " )");
		this.blockStartLOOP.getBlockLabel().setText(displayString);
		
		this.blockStartLOOP.adjustLabelSize();
		this.blockStartLOOP.adjustBlockSizeByLabel();
		this.blockStartLOOP.adjustLabelLocation();
		
		//Testing
		//System.out.println("blockStartLOOP's label's preferrable size = : " + 
		//						this.blockStartLOOP.getDisplayLabel().getPreferredSize());
		
		// blockStartLOOP may need to change size and location
		// this.blockStartLOOP.setAppropriateBounds();
		
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new WhileEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	/** Utility Functions **/
	
}

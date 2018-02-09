package gui.object;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.InputEditDialog;
import gui.editDialog.OutputEditDialog;
import gui.manager.UndoManager;

public class BlockOUTPUT extends OrdinaryBlockFD{
	
	private OutputEditDialog editDialog;
	
	public BlockOUTPUT(JSONObject model) {
		super(model);

		this.updateBlockContent();
		this.add(blockLabel);
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	/** Getters and Setters **/
	public String getExpression() {
		return this.getModel().getString("Expression");
	}
	
	/** Override the abstract methods **/
	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		this.blockLabel.setText("Output : " + this.getExpression());
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		if(this.getParent() instanceof CompositeBlockFD) {
			((CompositeBlockFD)this.getParent()).setAppropriateBounds();
		}
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new OutputEditDialog(undoManager, this);
		return this.editDialog;
	}
}

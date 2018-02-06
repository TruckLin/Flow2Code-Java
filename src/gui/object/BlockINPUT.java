package gui.object;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.DeclareEditDialog;
import gui.InputEditDialog;
import gui.manager.UndoManager;

public class BlockINPUT extends OrdinaryBlockFD {
	
	private InputEditDialog editDialog;
	
	public BlockINPUT(JSONObject model) {
		super(model);
		
		this.updateBlockContent();
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		this.add(blockLabel);
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	/** Getters and Setters **/
	public String getTargetVariable() {
		return this.getModel().getString("TargetVariable");
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
		this.blockLabel.setText("Input to variable " + this.getTargetVariable());
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		if(this.getParent() instanceof CompositeBlockFD) {
			((CompositeBlockFD)this.getParent()).setAppropriateBounds();
		}
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new InputEditDialog(undoManager, this);
		return this.editDialog;
	}
}

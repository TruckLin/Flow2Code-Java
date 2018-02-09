package gui.editDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockASSIGN;
import gui.object.BlockINPUT;

public class InputEditDialog extends BlockEditDialog{

	private BlockINPUT blockINPUT;
	
	private JLabel variableLabel = new JLabel("Target Variable : ");
	private JTextField variableTF = new JTextField();
	
	public InputEditDialog(UndoManager undoManager, BlockINPUT blockINPUT) {
		super(undoManager);
		this.blockINPUT = blockINPUT;
		this.buildEditDialog();
	}
	
	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("Input editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("InputIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("input help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
		this.editingPanel.setLayout(new FlowLayout());
		
		this.variableTF.setPreferredSize(new Dimension(275,25));
		this.variableTF.setText(this.blockINPUT.getTargetVariable());
		this.editingPanel.add(variableLabel);
		this.editingPanel.add(this.variableTF);
		
	}

	@Override
	protected void writeAdvanceDialog() {
		// TODO Auto-generated method stub
		this.advancedDialog.setText("Input advancedDialog");
		
		this.advancedDialogText = "\n Blah blah blah, \n that that that this this this. Done."; 
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		inputDetail.put("TargetVariable", variableTF.getText());
		this.undoManager.execute(new EditCommand(blockINPUT, inputDetail));
		
		this.dispose(); // careful with this
	}
	

}

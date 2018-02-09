package gui.editDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockOUTPUT;
import gui.object.BlockWHILE;

public class OutputEditDialog extends BlockEditDialog{

	private BlockOUTPUT blockOUTPUT;
	
	// Components for editingPanel
	private JLabel expressionLabel = new JLabel("Expression : ");
	private JTextField expressionTF = new JTextField();
	
	public OutputEditDialog(UndoManager undoManager ,BlockOUTPUT blockOUTPUT) {
		super(undoManager);
		this.blockOUTPUT = blockOUTPUT;
		this.buildEditDialog();
	}
	
	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("Output editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("OutputIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("Output help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
		this.editingPanel.setLayout(new FlowLayout());
		
		this.expressionTF.setPreferredSize(new Dimension(275,25));
		this.expressionTF.setText(this.blockOUTPUT.getExpression());
		this.editingPanel.add(expressionLabel);
		this.editingPanel.add(this.expressionTF);
	}

	@Override
	protected void writeAdvanceDialog() {
		// TODO Auto-generated method stub
		this.advancedDialog.setText("output advancedDialog");
		
		this.advancedDialogText = "\n Blah blah blah, \n that that that this this this. Done."; 
		
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		
		//Testing
		//System.out.println(expressionTF.getText());
		
		// When user enter " in the text field, it is converted into \" automatically.
		inputDetail.put("Expression", expressionTF.getText());
		this.undoManager.execute(new EditCommand(blockOUTPUT, inputDetail));
		
		this.dispose(); // careful with this
	}

}

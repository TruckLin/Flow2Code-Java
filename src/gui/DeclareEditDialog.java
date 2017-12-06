package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockDECLARE;
import gui.object.BlockIF;

public class DeclareEditDialog extends BlockEditDialog{
	
	private BlockDECLARE blockDECLARE;
	
	// Center of editing panel
	private declareTableModel myTableModel;
	private JTable variableTable;
	
	// East of editing panel
	private JPanel eastButtonPanel = new JPanel();
	private JButton addNewVariableBtn = new JButton("Add");
	private JButton removeVariablesBtn = new JButton("remove");
	
	
	
	// Preview
	private JLabel previewLabel = new JLabel("Preview : ");
	
	public DeclareEditDialog(UndoManager undoManager ,BlockDECLARE blockDECLARE) {
		super(undoManager);
		this.blockDECLARE = blockDECLARE;
		this.buildEditDialog();
	}
	
	public void updatePreview() {
	//	ifStatement = "if( " + expressionTextField.getText() + " )";
	//	previewLabel.setText(preview + ifStatement);
	}


	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("Declare editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("DeclareIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("declare help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
		editingPanel.setLayout(new BorderLayout());
		
		// Center Panel
		this.myTableModel = new declareTableModel(this.blockDECLARE.getModel());
		this.variableTable = new JTable(this.myTableModel);
		this.variableTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.variableTable.setFillsViewportHeight(true);
		editingPanel.add( new JScrollPane(this.variableTable), BorderLayout.CENTER);
		
		// East button panel
		this.eastButtonPanel.setLayout(new GridLayout(2,1));
		this.addNewVariableBtn.addActionListener(e -> this.myTableModel.addNewVariable());
		this.eastButtonPanel.add(this.removeVariablesBtn);
		this.eastButtonPanel.add(this.addNewVariableBtn);
		
		editingPanel.add(eastButtonPanel, BorderLayout.EAST);
	}

	@Override
	protected void writeAdvanceDialog() {
		// TODO Auto-generated method stub
		this.advancedDialog.setText("DECLARE advancedDialog");
		
		this.advancedDialogText = "\n Blah blah blah, \n that that that this this this. Done."; 
		
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		
	/*	inputDetail.put("Expression", expressionTextField.getText());
		this.undoManager.execute(new EditCommand(blockIF, inputDetail));
		
		// Set AppropriateBounds for BlockIF, in case the size of 
		// the block has changed.
		this.blockIF.setAppropriateBounds();*/
		
		this.dispose(); // careful with this
	}
}

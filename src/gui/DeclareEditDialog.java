package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockDECLARE;
import gui.object.BlockIF;

public class DeclareEditDialog extends BlockEditDialog{
	
	private BlockDECLARE blockDECLARE;
	
	// Center of editing panel
	private String[] dataTypes = {"Integer", "Real", "Boolean", "String"};
	private declareTableModel myTableModel;
	private JTable variableTable;
	
	// East of editing panel
	private JPanel eastButtonPanel = new JPanel();
	private JButton addNewVariableBtn = new JButton("Add");
	private JButton removeVariablesBtn = new JButton("remove");
	
	// RowSelection listener
	
	
	
	// Preview
	private JLabel previewLabel = new JLabel("Preview : ");
	
	public DeclareEditDialog(UndoManager undoManager ,BlockDECLARE blockDECLARE) {
		super(undoManager);
		this.blockDECLARE = blockDECLARE;
		this.buildEditDialog();
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
        TableColumn dataTypeColumn = this.variableTable.getColumnModel().getColumn(0);
        JComboBox<String> comboBox = new JComboBox<String>(this.dataTypes);
        dataTypeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		editingPanel.add( new JScrollPane(this.variableTable), BorderLayout.CENTER);
		
		// East button panel
		this.eastButtonPanel.setLayout(new GridLayout(2,1));
		this.removeVariablesBtn.addActionListener(e -> {
			int[] selectedRows = this.variableTable.getSelectedRows();
			this.myTableModel.removeVariables(selectedRows);
		});
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
		
		//Testing
	/*	for(ArrayList<Object> variable : this.myTableModel.getData()) {
			String varString = "";
			for(int i = 0; i < 4; i++) {
				varString = varString + variable.get(i) + "    ";
			}
			System.out.println(varString);
		} */
		
		if(this.myTableModel.getData().size() > 0) {
			for(ArrayList<Object> currentVariable : this.myTableModel.getData()) {
				JSONObject tempVar = new JSONObject();
				tempVar.put("DataType", currentVariable.get(0));
				tempVar.put("VariableName", currentVariable.get(1));
				tempVar.put("IsArray", currentVariable.get(2));
				if((boolean)currentVariable.get(2)) {
					tempVar.put("Size", currentVariable.get(3));
				}else {
					tempVar.put("Size", 1);
				}
				inputDetail.append("Variables", tempVar);
			}
		}else {
			inputDetail.put("Variables",new JSONArray() );
		}
		this.undoManager.execute(new EditCommand(blockDECLARE, inputDetail));
		
		this.dispose(); // careful with this
	}
}

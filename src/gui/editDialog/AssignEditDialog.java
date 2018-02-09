package gui.editDialog;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockASSIGN;
import gui.object.BlockDECLARE;

public class AssignEditDialog extends BlockEditDialog{
	
	private BlockASSIGN blockASSIGN;
	
	// Center of editing panel
	private AssignTableModel myTableModel;
	private JTable variableTable;
	
	// East of editing panel
	private JPanel eastButtonPanel = new JPanel();
	private JButton addNewVariableBtn = new JButton("Add");
	private JButton removeVariablesBtn = new JButton("remove");
	
	public AssignEditDialog(UndoManager undoManager, BlockASSIGN blockASSIGN) {
		super(undoManager);
		this.blockASSIGN = blockASSIGN;
		this.buildEditDialog();
	}
	
	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("Assign editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("AssignIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("assign help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
editingPanel.setLayout(new BorderLayout());
		// Center Panel
		this.myTableModel = new AssignTableModel(this.blockASSIGN.getModel());
		this.variableTable = new JTable(this.myTableModel);
		this.variableTable.setPreferredScrollableViewportSize(new Dimension(375, 70));
        this.variableTable.setFillsViewportHeight(true);
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
		this.advancedDialog.setText("ASSIGN advancedDialog");
		
		this.advancedDialogText = "\n Blah blah blah, \n that that that this this this. Done."; 
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		
		if(this.myTableModel.getData().size() > 0) {
			for(ArrayList<Object> currentAssignment : this.myTableModel.getData()) {
				JSONObject tempVar = new JSONObject();
				tempVar.put("TargetVariable", currentAssignment.get(0));
				tempVar.put("Expression", currentAssignment.get(2));
				
				inputDetail.append("Assignments", tempVar);
			}
		}else {
			inputDetail.put("Assignments",new JSONArray() );
		}
		this.undoManager.execute(new EditCommand(blockASSIGN, inputDetail));
		
		this.dispose(); // careful with this
	}

}

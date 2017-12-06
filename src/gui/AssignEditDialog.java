package gui;

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
import gui.object.BlockDECLARE;

public class AssignEditDialog extends BlockEditDialog{
	
	private BlockASSIGN blockASSIGN;
	
	private JLabel variableLabel = new JLabel("Variable : ");
	private JTextField variableTF = new JTextField();
	private JLabel expressionLabel = new JLabel("Expression : ");
	private JTextField expressionTF = new JTextField();
	
	private JLabel assignLabel = new JLabel(" = ");
	
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
		this.editingPanel.setLayout(new BorderLayout());
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.add(this.variableLabel, BorderLayout.WEST);
		northPanel.add(this.expressionLabel, BorderLayout.EAST);
		this.editingPanel.add(northPanel, BorderLayout.NORTH);
		
		this.variableTF.setPreferredSize(new Dimension(100,25));
		this.expressionTF.setPreferredSize(new Dimension(275,25));
		this.variableTF.setText(this.blockASSIGN.getModel().getString("Variable"));
		this.expressionTF.setText(this.blockASSIGN.getModel().getString("Expression"));
		this.editingPanel.add(this.variableTF, BorderLayout.WEST);
		this.editingPanel.add(assignLabel, BorderLayout.CENTER);
		this.editingPanel.add(this.expressionTF, BorderLayout.EAST);
		
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
		inputDetail.put("Variable", variableTF.getText());
		inputDetail.put("Expression", expressionTF.getText());
		this.undoManager.execute(new EditCommand(blockASSIGN, inputDetail));
		
		this.dispose(); // careful with this
	}

}

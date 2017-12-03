package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.BlockWHILE;
import gui.object.CompositeBlockFD;

public class WhileEditDialog extends BlockEditDialog{
	private BlockWHILE blockWHILE;
	
	// Components for editingPanel
	private JLabel expressionLabel = new JLabel("Expression : ");
	private JTextField expressionTextField = new JTextField();
	
	private String preview = "Preview : \n";
	private String whileStatement;
	private JLabel previewLabel = new JLabel();
	
	// CaretListener that dynamically change whileStatement
	private CaretListener listener = e -> updateStatement();
	
	public WhileEditDialog(UndoManager undoManager ,BlockWHILE blockWHILE) {
		super(undoManager);
		this.blockWHILE = blockWHILE;
		this.buildEditDialog();
	}
	
	public void updateStatement() {
		whileStatement = "while( " + expressionTextField.getText() + " )";
		previewLabel.setText(preview + whileStatement);
	}

	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("While editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("WhileIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("while help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
		this.editingPanel.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new FlowLayout());
		
		expressionTextField.setText(this.blockWHILE.getExpression());
		expressionTextField.addCaretListener(listener);
		
		northPanel.add(expressionLabel);
		northPanel.add(expressionTextField);
		
		this.editingPanel.add(northPanel, BorderLayout.NORTH);
	
		whileStatement = "while( " + this.blockWHILE.getExpression() + " )";
		previewLabel.setText(preview + whileStatement);
		this.editingPanel.add(previewLabel, BorderLayout.SOUTH);
	}

	@Override
	protected void writeAdvanceDialog() {
		// TODO Auto-generated method stub
		this.advancedDialog.setText("While advancedDialog");
		
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		inputDetail.put("Expression", expressionTextField.getText());
		this.undoManager.execute(new EditCommand(blockWHILE, inputDetail));
		
		// Set AppropriateBounds for BlockWHILE, in case the size of 
		// the block has changed.
		this.blockWHILE.setAppropriateBounds();
		
		this.dispose(); // careful with this
	}
}

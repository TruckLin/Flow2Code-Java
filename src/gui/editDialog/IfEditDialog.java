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
import gui.object.BlockIF;

public class IfEditDialog extends BlockEditDialog {
	private BlockIF blockIF;
	
	// Components for editingPanel
	private JLabel expressionLabel = new JLabel("Expression : ");
	private JTextField expressionTextField = new JTextField();
	
	private String preview = "Preview : \n";
	private String ifStatement;
	private JLabel previewLabel = new JLabel();
	
	// CaretListener that dynamically change IFStatement
	private CaretListener listener = e -> updatePreview();
	
	public IfEditDialog(UndoManager undoManager ,BlockIF blockIF) {
		super(undoManager);
		this.blockIF = blockIF;
		this.buildEditDialog();
	}
	
	public void updatePreview() {
		ifStatement = "if( " + expressionTextField.getText() + " )";
		previewLabel.setText(preview + ifStatement);
	}

	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("If editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("IfIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("if help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
		this.editingPanel.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new FlowLayout());
		northPanel.setBackground(Color.WHITE);
		expressionTextField.setPreferredSize(new Dimension(100,25));
		expressionTextField.setText(this.blockIF.getExpression());
		expressionTextField.addCaretListener(listener);
		
		northPanel.add(expressionLabel);
		northPanel.add(expressionTextField);

		this.editingPanel.add(northPanel, BorderLayout.NORTH);
	
		ifStatement = "if( " + this.blockIF.getExpression() + " )";
		previewLabel.setText(preview + ifStatement);
		this.editingPanel.add(previewLabel, BorderLayout.SOUTH);
	}

	@Override
	protected void writeAdvanceDialog() {
		// TODO Auto-generated method stub
		this.advancedDialog.setText("IF advancedDialog");
		
		this.advancedDialogText = "\n Blah blah blah, \n that that that this this this. Done."; 
		
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		inputDetail.put("Expression", expressionTextField.getText());
		this.undoManager.execute(new EditCommand(blockIF, inputDetail));
		
		// Set AppropriateBounds for BlockIF, in case the size of 
		// the block has changed.
		this.blockIF.setAppropriateBounds();
		
		this.dispose(); // careful with this
	}
}

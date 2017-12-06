package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import org.json.JSONObject;

import gui.commands.EditCommand;
import gui.manager.UndoManager;
import gui.object.BlockFOR;
import gui.object.BlockWHILE;

public class ForEditDialog extends BlockEditDialog {
	private BlockFOR blockFOR;
	
	// Labels and TextFields
	private JLabel variableLabel = new JLabel("Variable : ");
	private JTextField variableTF = new JTextField();
	private JLabel startValueLabel = new JLabel("Start Value : ");
	private JTextField startValueTF = new JTextField();
	private JLabel endValueLabel = new JLabel("End Value : ");
	private JTextField endValueTF = new JTextField();
	private JLabel directionLabel = new JLabel("Direction : ");
	private JComboBox<String> directionCB = new JComboBox<String>();
	private JLabel stepLabel = new JLabel("Step by : ");
	private JTextField stepTF = new JTextField();
	
	// Preview
	private String preview = "Preview : \n";
	private String forStatement;
	private String initialisation, condition, postprocess;
	private JLabel previewLabel = new JLabel();
	
	// CaretListener that dynamically change whileStatement
	private CaretListener listener = e -> updatePreview();
	private ActionListener comboBoxListener = e -> updatePreview();
	public ForEditDialog(UndoManager undoManager ,BlockFOR blockFOR) {
		super(undoManager);
		this.blockFOR = blockFOR;
		this.buildEditDialog();
	}
	
	private void updatePreview() {
		this.initialisation = variableTF.getText() + " = " + startValueTF.getText();
		this.condition = variableTF.getText() + " <= " + endValueTF.getText();
		this.postprocess = variableTF.getText() + " = " + variableTF.getText();
		if( directionCB.getSelectedItem() != null && ((String)directionCB.getSelectedItem()).equals("Increasing") ) {
			this.postprocess = this.postprocess + " + ";
		}else {
			this.postprocess = this.postprocess + " - ";
		}
		this.postprocess = this.postprocess + this.stepTF.getText();
		
		
		this.forStatement = "for( " +  this.initialisation + "; " 
									+ this.condition + "; "
									+ this.postprocess + " )";
		this.previewLabel.setText(preview + forStatement);
	}
	
	
	@Override
	protected void setDialogTitle() {
		// TODO Auto-generated method stub
		this.setTitle("For Editor");
	}

	@Override
	protected void drawIcon() {
		// TODO Auto-generated method stub
		this.icon.setText("ForIcon");
	}

	@Override
	protected void writeHelpDialog() {
		// TODO Auto-generated method stub
		this.helpDialog.setText("for help dialog");
	}

	@Override
	protected void constructEditingPanel() {
		// TODO Auto-generated method stub
		editingPanel.setLayout(new BorderLayout());
		
		// North panel
		JPanel northPanel = new JPanel(new GridLayout(3,4));
		
		Dimension tempDim = new Dimension(100,25);
		variableTF.setPreferredSize(tempDim);
		variableTF.addCaretListener(listener);
		variableTF.setText(this.blockFOR.getVariable());
		northPanel.add(this.variableLabel);
		northPanel.add(this.variableTF);
		
		startValueTF.setPreferredSize(tempDim);
		startValueTF.addCaretListener(listener);
		startValueTF.setText(this.blockFOR.getStartValue());
		northPanel.add(this.startValueLabel);
		northPanel.add(this.startValueTF);
		
		endValueTF.setPreferredSize(tempDim);
		endValueTF.addCaretListener(listener);
		endValueTF.setText(this.blockFOR.getEndValue());
		northPanel.add(this.endValueLabel);
		northPanel.add(this.endValueTF);
		
		String[] directionModel = {"Increasing", "Decreasing"};
		directionCB = new JComboBox<String>(directionModel);
		directionCB.setPreferredSize(tempDim);
		directionCB.addActionListener(comboBoxListener);
		if(this.blockFOR.getDirection().equals("Increasing")) {
			directionCB.setSelectedItem("Increasing");
		}else {
			directionCB.setSelectedItem("Decreasing");
		}
		northPanel.add(this.directionLabel);
		northPanel.add(this.directionCB);
		
		stepTF.setPreferredSize(tempDim);
		stepTF.addCaretListener(listener);
		stepTF.setText(this.blockFOR.getStepBy());
		northPanel.add(this.stepLabel);
		northPanel.add(this.stepTF);
		
		editingPanel.add(northPanel, BorderLayout.NORTH);
		
		//South
		this.initialisation = this.blockFOR.getVariable() + " = " + this.blockFOR.getStartValue();
		this.condition = this.blockFOR.getVariable() + " <= " + this.blockFOR.getEndValue();
		this.postprocess = this.blockFOR.getVariable() + " = " + this.blockFOR.getVariable();
		if( this.blockFOR.getDirection().equals("Increasing") ) {
			this.postprocess = this.postprocess + " + ";
		}else {
			this.postprocess = this.postprocess + " - ";
		}
		this.postprocess = this.postprocess + this.blockFOR.getStepBy();
		
		
		this.forStatement = "for( " +  this.initialisation + "; " 
									+ this.condition + "; "
									+ this.postprocess + " )";
		this.previewLabel.setText(preview + forStatement);
		editingPanel.add(previewLabel, BorderLayout.SOUTH);
		
	}

	@Override
	protected void writeAdvanceDialog() {
		// TODO Auto-generated method stub
		this.advancedDialog.setText("For advancedDialog");
	}

	@Override
	protected void EditAction() {
		// TODO Auto-generated method stub
		JSONObject inputDetail = new JSONObject();
		inputDetail.put("Variable", this.variableTF.getText());
		inputDetail.put("StartValue", this.startValueTF.getText());
		inputDetail.put("EndValue", this.endValueTF.getText());
		inputDetail.put("Direction", (String)this.directionCB.getSelectedItem());
		inputDetail.put("StepBy", this.stepTF.getText());
		inputDetail.put("Initialisation", this.initialisation);
		inputDetail.put("Condition", this.condition);
		inputDetail.put("PostProcess", this.postprocess);
		
		this.undoManager.execute(new EditCommand(blockFOR, inputDetail));
		
		// Set AppropriateBounds for BlockWHILE, in case the size of 
		// the block has changed.
		this.blockFOR.setAppropriateBounds(); 
		
		this.dispose(); // careful with this
		
	}

}

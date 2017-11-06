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
import gui.object.BlockWHILE;

public class WhileEditDialog extends BlockEditDialog{
	UndoManager undoManager;
	
	BlockWHILE blockWHILE;
	
	// Components in the dialog.
	// north
	JPanel headerPanel = new JPanel(new FlowLayout());
	JLabel whileStatement;
	//center
	JTextField expressionTextArea;
	// south
	JPanel bottomPanel = new JPanel(new BorderLayout());
	JButton btnConfirm;
	JButton btnCancel;
	JLabel warningMessage;
	
	// CaretListener that dynamically change whileStatement
	CaretListener listener = e -> updateStatement();
	
	public WhileEditDialog(UndoManager undoManager ,BlockWHILE blockWHILE) {
		super();
		this.undoManager = undoManager;
		this.blockWHILE = blockWHILE;
		
		// Set title
		this.setTitle("While editor");
		
		// Set up the panel.
		JPanel myContentPanel = new JPanel(new BorderLayout());
		
		// north
		whileStatement = new JLabel("while( " + this.blockWHILE.getExpression() + " )");
		headerPanel.add(whileStatement);
		
		// center
		JPanel centerPanel = new JPanel(new BorderLayout());
		expressionTextArea = new JTextField();
		expressionTextArea.setText(this.blockWHILE.getExpression());
		expressionTextArea.addCaretListener(listener);

		centerPanel.add(new JLabel("Expression : "), BorderLayout.WEST);
		centerPanel.add(expressionTextArea, BorderLayout.CENTER);
		
		// south
		btnConfirm = new JButton("Confirm");
		btnCancel = new JButton("Cancel");
		btnConfirm.addActionListener(e -> {// tell undoManager to perform EditComand
									JSONObject inputDetail = new JSONObject();
									inputDetail.put("Expression", expressionTextArea.getText());
									this.undoManager.execute(new EditCommand(blockWHILE, inputDetail));
									this.dispose(); // careful with this
									});
		
		btnCancel.addActionListener(e -> this.dispose());
		warningMessage = new JLabel("This should be warning message.");
		bottomPanel.add(warningMessage, FlowLayout.LEFT);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnConfirm);
		buttonPanel.add(btnCancel);
		bottomPanel.add(buttonPanel, BorderLayout.EAST);
		
		// Now add all the component onto myContentPanel.
		myContentPanel.add(headerPanel, BorderLayout.NORTH);
		myContentPanel.add(centerPanel, BorderLayout.CENTER);
		myContentPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		// Finally set the content panel
		this.setContentPane(myContentPanel);
		this.pack();
	}
	
	public void updateStatement() {
		whileStatement.setText("while( " + expressionTextArea.getText() + " )");
	}
}

package graph.object;

import java.awt.Color;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.object.ComponentASSIGN;

public class BlockASSIGN extends OrdinaryBlockFD{
	private ComponentASSIGN model;
	private String targetVariable;
	private String inputExpression;
	
	//private PropertyChangeListener listener = e -> update();
	
	public BlockASSIGN(ComponentASSIGN model) {
		super();
		this.model = model;
		
		// Temporary
		this.add(new JLabel("assign"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public BlockASSIGN(ComponentASSIGN model, Rectangle bounds) {
		super();
		this.model = model;
		if(bounds != null) {
			this.setBounds(bounds);
		}
		
		// Temporary
		this.add(new JLabel("assign : "));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters**/
	public String getTargetVariable() {
		return this.targetVariable;
	}
	public String getInputExpression() {
		return this.inputExpression;
	}
	
	/**
	 * update(), should be invoked when anything changed in the model.
	 * */
	/*
	public void update() {
		this.targetVariable = this.model.getTargetVariable();
		this.inputExpression = this.model.getInputExpression();
	}*/

}

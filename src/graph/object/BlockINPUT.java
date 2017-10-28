package graph.object;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.object.ComponentINPUT;

public class BlockINPUT extends OrdinaryBlockFD {
	ComponentINPUT model;
	String expression;
	
	public BlockINPUT(ComponentINPUT model) {
		super();
		// TODO Auto-generated constructor stub
		
		this.model = model;
		
		this.add(new JLabel("INPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	
	public BlockINPUT(ComponentINPUT model, Rectangle bounds) {
		super();
		this.model = model;
		if(bounds != null) {
			this.setBounds(bounds);
		}
		
		// Temporary
		this.add(new JLabel("INPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	/** getters **/
	
	
	/** Setters **/
	
}

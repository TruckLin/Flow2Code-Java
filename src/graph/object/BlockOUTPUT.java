package graph.object;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.object.ComponentOUTPUT;

public class BlockOUTPUT extends OrdinaryBlockFD{
	ComponentOUTPUT model;
	String expression;
	
	public BlockOUTPUT(ComponentOUTPUT model) {
		super();
		this.model = model;
		
		
		//Temporary
		this.add(new JLabel("OUTPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	
	public BlockOUTPUT(ComponentOUTPUT model, Rectangle bounds) {
		super();
		this.model = model;
		if(bounds != null) {
			this.setBounds(bounds);
		}
		
		//Temporary
		this.add(new JLabel("OUTPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	/** getters **/
	
	
	/** Setters **/
}

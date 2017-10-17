package graph.object;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BlockINPUT extends BlockFD {
	public BlockINPUT(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel("INPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	
	public BlockINPUT(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel("INPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	/** getters **/
	
	
	/** Setters **/
	
}

package graph.object;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BlockOUTPUT extends BlockFD{
	
	public BlockOUTPUT(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel("OUTPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	
	public BlockOUTPUT(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel("OUTPUT"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	/** getters **/
	
	
	/** Setters **/
}

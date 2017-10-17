package graph.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class BlockDECLARE extends BlockFD {
	
	public BlockDECLARE(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel(N));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	public BlockDECLARE(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel(N));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	/** getters **/
	
	
	/** Setters **/
	

}

package graph.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BlockWHILE extends BlockFD{
	
	/** Constructors and initialisations **/
	public BlockWHILE(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel("while"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	public BlockWHILE(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel("while"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
	}
	
	
	
	/** Setters **/
	public void setOutPorts() {
		int x = this.getWidth();
		int y = (int)Math.round(this.getHeight()/2);
	}
	
	/** Getters **/
	/*public Point getTrueOutPort() {
		return this.trueOutPort;
	}
	
	public Point getFalseOutPort() {
		return this.falseOutPort;
	}
	public BlockENDIF getBlockENDIF() {
		return this.endBlock;
	}*/
	
	
	/** Utility functions **/
	public ArrayList<BlockFD> buildChildrenList() {

		ArrayList<BlockFD> output = new ArrayList<BlockFD>();
		BlockFD tempBlock = new BlockFD();
		// require modification.
		
		output.add(this.getDirectChild());
		
		
		output.add(tempBlock); // add the final block which will always be BlockENDIF.
		return output;
	}
	
	public BlockFD getDirectChildAfterWhile() {
		// require modification.
		return this.getDirectChild();
	}
}

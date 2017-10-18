package graph.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BlockIF extends BlockFD{
	
	private Point trueOutPort;
	private Point falseOutPort;
	
	private BlockENDIF endBlock;
	
	/** Constructors and initialisations **/
	public BlockIF(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel("if"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.endBlock = new BlockENDIF("");
		
		this.setOutPorts();
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	public BlockIF(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel("if"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.endBlock = new BlockENDIF("");
		
		this.setOutPorts();
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
	}
	
	
	
	/** Setters **/
	public void setOutPorts() {
		int x = this.getWidth();
		int y = (int)Math.round(this.getHeight()/2);
		this.trueOutPort = new Point(x,y);
		this.falseOutPort = new Point(0,y);
	}
	public void setBlockENDIF(BlockENDIF b) {
		this.endBlock = b;
	}
	
	/** Getters **/
	public Point getTrueOutPort() {
		return this.trueOutPort;
	}
	
	public Point getFalseOutPort() {
		return this.falseOutPort;
	}
	public BlockENDIF getBlockENDIF() {
		return this.endBlock;
	}
	
	
	/** Utility functions **/
	public ArrayList<BlockFD> buildChildrenList() {
		
		// Testing
		//System.out.println("In BlockIF.buildChildreList() : ");
		
		ArrayList<BlockFD> output = new ArrayList<BlockFD>();
		BlockFD tempBlock = new BlockFD();
		output.add(this);
		
		for(int i = 0; i < this.lines.size(); i++) {
			LineFD L = (LineFD)this.lines.get(i);	
			
			if(L.isSource(this)) {
				tempBlock = L.getTerminal();
				while(!(tempBlock instanceof BlockENDIF)){
					if(tempBlock instanceof BlockIF) {
						BlockIF temp = (BlockIF)tempBlock;
						output.addAll( temp.buildChildrenList() );
						
						//update tempBlock
						tempBlock = temp.getDirectChildOfEndIf();
					}else if(tempBlock instanceof BlockWHILE) {
						BlockWHILE temp = (BlockWHILE)tempBlock;
						output.addAll( temp.buildChildrenList());
						
						//update tempBlock
						tempBlock = temp.getDirectChildAfterWhile();
					}else {
						// Testing
						//System.out.println("else block is triggered.");
						
						output.add(tempBlock);
						
						tempBlock = tempBlock.getDirectChild();
					}
				}
			}
		}
		output.add(tempBlock); // add the final block which will always be BlockENDIF.
		return output;
	}
	
	public BlockFD getDirectChildOfEndIf() {
		return this.endBlock.getDirectChild();
	}
	
}

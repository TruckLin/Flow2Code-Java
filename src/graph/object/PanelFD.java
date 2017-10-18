package graph.object;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

public class PanelFD extends JPanel implements Scrollable{
	
	private static final long serialVersionUID = 1L;
	
	private BlockFD componentBeingDragged;
	private Point lastClickPoint;
	private Point topLeftToMouse;
	private Boolean isDuringDrag = false;
	
	private JPopupMenu linePopup;
	private JPopupMenu blockPopup;
	
	private LineFD currentLine; // cache the line right clicked. 
	private BlockFD currentBlock; // cache the block right clicked.
	
	private ArrayList<BlockFD> BlockList;
	private ArrayList<LineFD> LineList;
	
	// Constructor
	public PanelFD(){
		this.setLayout(null);
		this.setBackground(Color.blue);
		this.setPreferredSize(new Dimension(500,500));
		
		// Construction of BlockMAIN
		BlockMAIN mainBlock = new BlockMAIN("Main", new Rectangle(50,10,100,25));
		//BlockList.add(mainBlock);
	    this.add(mainBlock);
	    
		// Construction of BlockEND
	    BlockEND endBlock = new BlockEND("End", new Rectangle(50,210,100,25));
	    //endBlock.setLocation(new Point(50,100));
	    //BlockList.add(endBlock);
	    this.add(endBlock);
	    
	    // Testing
	    BlockDECLARE testBlock = new BlockDECLARE("testing", new Rectangle(30,110,100,25));
	    this.add(testBlock);
	    LineFD line1 = createLine(mainBlock,testBlock);
	    this.add(line1);
	    mainBlock.addLine(line1);
	    testBlock.addLine(line1); // add line1 as observer of the blocks.
	    
	    // Construction of LineFD
	    
	    LineFD line2 = createLine(testBlock,endBlock);
	    this.add(line2);
	    testBlock.addLine(line2);
	    endBlock.addLine(line2);
	    
	    // Construct linePopup
	    initLinePopup();
	    initBlockPopup();
	    
	    BlockDragListener blockDragListener = new BlockDragListener(); 
	    this.addMouseListener(blockDragListener);
	    this.addMouseMotionListener(blockDragListener);	
	}
	
	private void initLinePopup() {
		this.linePopup = new JPopupMenu();
		int numRow = 2;
		int numCol = 4;
		linePopup.setLayout(new GridLayout(numRow, numCol));
		
		String[] names = {"declare","userInput","if", "for",
							"assign", "print" , "call" , "while"};
		JMenuItem menuItem;
		
		addBlockMenuItemListener listener = new addBlockMenuItemListener();
		for(String temp : names) {
			menuItem = new JMenuItem(temp);
			menuItem.addActionListener(listener);
			linePopup.add(menuItem);
		}
	}
	
	private void initBlockPopup() {
		this.blockPopup = new JPopupMenu();
		int numRow = 1;
		int numCol = 1;
		blockPopup.setLayout(new GridLayout(numRow, numCol));
		
		String[] names = {"delete"};
		JMenuItem menuItem;
		
		deleteBlockMenuItemListener listener = new deleteBlockMenuItemListener();
		for(String temp : names) {
			menuItem = new JMenuItem(temp);
			menuItem.addActionListener(listener);
			blockPopup.add(menuItem);
		}
	}
	
	
	/** Create line function 
	*	This function create lines between two BlockFD object.
	**/
	public LineFD createLine(BlockFD b1, BlockFD b2) {
		return new LineFD(b1, b2);
	}
	public void addLine(BlockFD b1, BlockFD b2) {
		this.add(new LineFD(b1, b2));
	}

	/**
	 *  Scrollable interface functions.
	 */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	 * Named inner class that control drag action of BlockFDs.
	 * This also controls the right click action of blocks and lines.
	 * */
	public class BlockDragListener extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
			int x = e.getX();
	        int y = e.getY();
	        
	        Component comp = getComponentAt(x,y);
	        if(comp instanceof BlockFD && SwingUtilities.isLeftMouseButton(e)) {
	        	componentBeingDragged = (BlockFD) comp;
	        	PanelFD.this.lastClickPoint = e.getPoint();
	        
	        	Point topLeft = componentBeingDragged.getLocation();
	        	int x2 = (int) (x - topLeft.getX());
	        	int y2 = (int) (y - topLeft.getY());
	        	topLeftToMouse = new Point(x2, y2);
	        	
	        	isDuringDrag = true;
	        	
	        	//Testing
	        	//System.out.println("mousePressed and BlockFD detected.");
	        }
	        
	        //Testing
	        //System.out.println("Mouse left-clicked at ("+x+","+y+").");
	        //System.out.println(topLeft.toString());
	        //System.out.println(e);
			
		}
		
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
	        int y = e.getY();
	        int dx = (int)(x - lastClickPoint.getX());
			int dy = (int)(y - lastClickPoint.getY());
			
			//Testing
			//System.out.println("In mouseDragged(MouseEvent e) : ");
        	//System.out.println("mouseDragged detected at (" + x + "," + y + ").");
        	//System.out.println("Displacement is (" + dx + "," + dy + ").\n");
			
			if(componentBeingDragged instanceof BlockFD && SwingUtilities.isLeftMouseButton(e) && isDuringDrag) {
		        
				if(componentBeingDragged instanceof BlockIF) {
					BlockIF temp = (BlockIF)componentBeingDragged;
					ArrayList<BlockFD> blocklist = temp.buildChildrenList();

					for(int i = 0; i < blocklist.size(); i++) {
						blocklist.get(i).translateLocation(dx, dy);
					}
				}else if(componentBeingDragged instanceof BlockWHILE){
					componentBeingDragged.translateLocation(dx, dy);
				}else {
					componentBeingDragged.translateLocation(dx, dy);
				}
			}
			// update last clickPoint
			lastClickPoint = new Point(x,y);
		}
		
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			isDuringDrag = false;
			
		}
		
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
	        int y = e.getY();
			Component comp = getComponentAt(x,y);
			if( comp instanceof LineFD && SwingUtilities.isRightMouseButton(e)) {
				linePopup.show(e.getComponent(),
	                       e.getX(), e.getY());
				
				currentLine = (LineFD)comp;
			}else if( comp instanceof BlockFD && SwingUtilities.isRightMouseButton(e)) {
				if( !(comp instanceof BlockMAIN || comp instanceof BlockEND) ) {
					blockPopup.show(e.getComponent(),e.getX(), e.getY());
				
					currentBlock = (BlockFD) comp;
				}
				
			}
		}
	}
	
	/** ActionListener that controls the addition of Blocks **/
	public class addBlockMenuItemListener implements ActionListener{
		// function that add ordinary blocks such as assign, declare, output...etc.
		public void addOrdinaryBlock(String blockName) {
			// Step 0, get info from current line.
			BlockFD b1 = currentLine.getSource();
			BlockFD b3 = currentLine.getTerminal();
			Point startpt = currentLine.getStartPoint();
			Point endpt = currentLine.getEndPoint();
			
			Point centre = currentLine.getCentreOnPanel();
						
			// Step 1, remove currentLine from observer list and then delete current line.
			b1.removeLine(currentLine);
			b3.removeLine(currentLine);
			PanelFD.this.remove(currentLine);
						
			// Step 2, create BlockFD object, add to the panel.
				// This is only an example.
						
						
			BlockDECLARE b2 = new BlockDECLARE(blockName, new Rectangle(centre.x, centre.y, 100,25));
			b2.setLocation(centre);
			PanelFD.this.add(b2);
						
				// Testing
				//System.out.println(blockName);
				//JLabel lb = (JLabel)b2.getComponent(0);
				//System.out.println(lb.getText());
					
			// Step 3, adjust the position b1,b2,b3 and possibly update the size of this panel.
						
						
			// Step 4, construct and add two lines that connect three blocks.
			LineFD line1 = new LineFD(b1, b2, startpt, b2.toContainerCoordinate(b2.getInPort()));
			LineFD line2 = new LineFD(b2, b3, b2.toContainerCoordinate(b2.getInPort()), endpt);
			PanelFD.this.add(line1);
			PanelFD.this.add(line2);
			
			// Step 5, add those lines to be the observers of blocks.
			b1.addLine(line1);
			b2.addLine(line1);
			b2.addLine(line2);
			b3.addLine(line2);
						
			// Step 6, repaint PanelFD, do I have to repaint b2?
			PanelFD.this.validate();
			PanelFD.this.repaint();
		}
		
		// function that add if block, which is more complicated that ordinary blocks.
		public void addIfBlock(String blockName) {
			// Step 0, get info from current line.
			BlockFD b1 = currentLine.getSource();
			BlockFD b3 = currentLine.getTerminal();
			Point centre = currentLine.getCentreOnPanel();
			
			Point startpt = currentLine.getStartPoint(); // w.r.t. container coordinate.
			Point endpt = currentLine.getEndPoint(); // w.r.t. container coordinate.
						
			// Step 1, remove currentLine from observer list and then delete current line.
			b1.removeLine(currentLine);
			b3.removeLine(currentLine);
			PanelFD.this.remove(currentLine);
						
			// Step 2, create BlockFD objects,in this case BlockIF and BlockENDIF, add to the panel.
				// This is only an example.
						
						
			BlockIF b2 = new BlockIF(blockName, new Rectangle(centre.x, centre.y, 100,25));
			BlockENDIF b2_5 = new BlockENDIF("endif", new Rectangle(centre.x+50, centre.y+50, 25,25) );
			b2.setBlockENDIF(b2_5);
			
			PanelFD.this.add(b2);
			PanelFD.this.add(b2_5);
				// Testing
				//System.out.println(b2.getBlockENDIF().toString());
					
			// Step 3, adjust the position b1,b2,b3 and possibly update the size of this panel.
						
						
			// Step 4, construct and add 4 lines that connect 4 blocks.
			LineFD line1 = new LineFD(b1, b2, startpt, b2.toContainerCoordinate(b2.getInPort()) );
			LineFD line2 = new LineFD(b2_5,b3, b2_5.toContainerCoordinate(b2_5.getOutPort()), endpt);
			LineFD linetrue = new LineFD(b2,b2_5, 
								b2.toContainerCoordinate(b2.getTrueOutPort()), b2_5.toContainerCoordinate(b2_5.getTrueInPort()));
			LineFD linefalse = new LineFD(b2,b2_5, 
								b2.toContainerCoordinate(b2.getFalseOutPort()), b2_5.toContainerCoordinate(b2_5.getFalseInPort()));
			PanelFD.this.add(line1);
			PanelFD.this.add(line2);
			PanelFD.this.add(linetrue);
			PanelFD.this.add(linefalse);
			
			// Step 5, add those lines to be the observers of blocks.
			b1.addLine(line1);
			b2.addLine(line1);
			b2_5.addLine(line2);
			b3.addLine(line2);
			
			b2.addLine(linetrue);
			b2.addLine(linefalse);
			b2_5.addLine(linetrue);
			b2_5.addLine(linefalse);
						
			// Step 6, repaint PanelFD, do I have to repaint b2?
			PanelFD.this.validate();
			PanelFD.this.repaint();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String blockName = e.getActionCommand();
			
			switch(blockName) {
				case "if":
					addIfBlock(blockName);
					break;
				case "while":
					addOrdinaryBlock(blockName); // need modification.
					break;
				default:
					addOrdinaryBlock(blockName);
					break;
			
			}
		}
	}
	
	/** ActionListener that controls the deletion of Blocks **/
	public class deleteBlockMenuItemListener implements ActionListener{
		
		public void deleteBlock(BlockFD b) {
			// TODO Auto-generated method stub
			//Testing
			System.out.println("In deleteBlock() : ");
			
			BlockFD parent = new BlockFD();
			BlockFD child = new BlockFD();
			Point startpt = new Point();
			Point endpt = new Point();
			
			// step 1, initialise parent, child, startpt, endpt.
			// 		   Remove LineFDs from parent and child's listener list.
			ArrayList<PropertyChangeListener> myLines = currentBlock.getLines();
		
			for(int i = 0; i < myLines.size(); i++){
				LineFD tempLine = (LineFD)myLines.get(i);
			
				if(tempLine.isSource(currentBlock)) {
					child = tempLine.getTerminal();
					child.removeLine(myLines.get(i));
					endpt = tempLine.getEndPoint();
				
				}else if(tempLine.isTerminal(currentBlock)) {
					parent = tempLine.getSource();
					parent.removeLine(myLines.get(i));
					startpt = tempLine.getStartPoint();
					
				}else {}
			
				PanelFD.this.remove(tempLine);
			}
						
						
			// step 2, construct the line connecting the remaining 2 blocks. Add newLine to listener Lists of parent and child.
			LineFD newLine = new LineFD(parent, child, startpt, endpt);
			PanelFD.this.add(newLine);
			parent.addLine(newLine);
			child.addLine(newLine);
						
			// Step 3, remove currentBlock from panel.
			PanelFD.this.remove(currentBlock);
					
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//Testing
			System.out.println("In actionPerformed :");
			
			// step 1, initialise parent, child, startpt, endpt.
			// 		   Remove LineFDs from parent and child's listener list.
			ArrayList<PropertyChangeListener> myLines = currentBlock.getLines();
			if(currentBlock instanceof BlockIF) {
				
				//Testing
				System.out.println("currentBlock is BlockIF.");
				
				
				currentBlock = (BlockIF)currentBlock;
				ArrayList<BlockFD> blocklist = currentBlock.buildChildrenList();
				int len = blocklist.size();
				for(int i = 1; i < len-1 ; i++) {
					
					//Testing
					System.out.println(" i = " + i);
					deleteBlock(blocklist.get(i));
				}
				deleteBlock(blocklist.get(0));
				deleteBlock(blocklist.get(len-1));
				
				
			}else if(currentBlock instanceof BlockWHILE){
				//Testing
				System.out.println("currentBlock is BlockWHILE.");
				
			}else{
				
				deleteBlock(currentBlock);
			}
			
			// Step 4, repaint PanelFD, do I have to repaint b2?
			PanelFD.this.validate();
			PanelFD.this.repaint();
		}
	}
	
}

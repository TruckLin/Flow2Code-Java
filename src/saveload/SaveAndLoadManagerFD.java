package saveload;

import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;

import org.json.*;

import com.tcg.json.JSONUtils;

import gui.BlockPopup;
import gui.LinePopup;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.UndoManager;
import gui.mouselistener.BlockDragListener;
import gui.mouselistener.BlockRightClickListener;
import gui.mouselistener.DoubleClickListener;
import gui.mouselistener.LineRightClickListener;
import gui.mouselistener.LoopDragListener;
import gui.mouselistener.MouseEnterListener;
import gui.object.*;

public abstract class SaveAndLoadManagerFD {
	
	public static JSONObject loadFlowDiagramFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	public static JSONObject loadGraphicalInfoFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	
	public static BlockFD constructBlockFD(JSONObject model, JSONObject graphicalInfo) {
		/** We might need some safety check. **/
		//Testing
		//System.out.println("constructBlockFD is called.");
		//System.out.println("Model : \n" + model.toString());
		//System.out.println("model.getString(\"Type\") = " + model.getString("Type"));
		//System.out.println(model.getString("Type").equals("FlowDiagram"));
		/**************************************/
		if(model.getString("Type").equals("FlowDiagram")) {
			//Testing
			//System.out.println("If section is called.");
			
			
			BlockFlowDiagram myPanel = new BlockFlowDiagram(model);
			JSONArray myMembers = model.getJSONArray("Members");
			
			ArrayList<BlockFD> BlockList = new ArrayList<BlockFD>(); // keeping the list to add lines.
			/* Adding Blocks */
			int length = myMembers.length();
			for(int i = 0; i < length; i++) {
				JSONObject tempObj = myMembers.getJSONObject(i);
				BlockFD tempBlock = constructBlockFD(tempObj, graphicalInfo);
				BlockList.add(tempBlock); // add to the collection of Blocks.
				myPanel.add(tempBlock);
				
			}
			
			/* Connecting Lines */
			for(int i = 0; i < length; i++) {
				// Testing
				//System.out.println("First Block : " + BlockList.get(i).getModel().getString("Name"));
				//System.out.println("Has child : " + BlockList.get(i).getModel().has("Child"));
				if(BlockList.get(i).getModel().has("Child")) {
					String childName = BlockList.get(i).getModel().getString("Child");
					for(int j = 0; j < length; j++) {
						if( childName.equals(BlockList.get(j).getModel().getString("Name")) ) {
							BlockFD b1 = BlockList.get(i);
							BlockFD b2 = BlockList.get(j);
							Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
							Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
							LineFD line = new LineFD(b1,b2,p1,p2);
							myPanel.add(line);
							// Then we might want to register line as the listener of both block.
							
							//Testing
							//System.out.println("In SaveAndLoadManager.constructBlockFD()  FlowDiagram section:");
							//System.out.println("line" + i + " Source: " + line.getSource().toString());
							//System.out.println("line" + i + " Terminal: " + line.getTerminal().toString());
							
							//Finally
							break;
						}
					}
				}
			}
			setGraphicalDetail(myPanel,graphicalInfo);
			return myPanel;
			
		}else if(model.getString("Type").equals("Start")) {
			BlockSTART myPanel = new BlockSTART(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
			
		}else if(model.getString("Type").equals("End")) {
			BlockEND myPanel = new BlockEND(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
			
		}else if(model.getString("Type").equals("Declare")) {
			BlockDECLARE myPanel = new BlockDECLARE(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
		}else if(model.getString("Type").equals("Assign")) {
			BlockASSIGN myPanel = new BlockASSIGN(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
		}else if(model.getString("Type").equals("Output")) {
			BlockOUTPUT myPanel = new BlockOUTPUT(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
		}else if(model.getString("Type").equals("Input")) {
			BlockINPUT myPanel = new BlockINPUT(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
		}else if(model.getString("Type").equals("For")) {
			BlockFOR myPanel = new BlockFOR(model);
			BlockStartLOOP myStartLoop = (BlockStartLOOP)constructBlockFD(model.getJSONObject("StartLoop"), graphicalInfo);
			myPanel.add(myStartLoop);
			myPanel.setBlockStartLOOP(myStartLoop);
			
			ArrayList<BlockFD> BlockList = new ArrayList<BlockFD>(); // keeping the list to add lines.
			JSONArray myMembers = model.getJSONArray("Members");
			int length = myMembers.length();
			for(int i = 0; i < length; i++) {
				JSONObject tempObj = myMembers.getJSONObject(i);
				BlockFD tempBlock = constructBlockFD(tempObj, graphicalInfo);
				BlockList.add(tempBlock); // add to the collection of Blocks.
				myPanel.add(tempBlock);
			}
			
			BlockList.add(myStartLoop); // also add BlockStartLoop into the list.
			
			
			
			/* Connecting Lines */
			for(int i = 0; i < BlockList.size(); i++) {
				// Testing
				//System.out.println("First Block : " + BlockList.get(i).getModel().getString("Name"));
				//System.out.println("Has child : " + BlockList.get(i).getModel().has("Child"));
				
				if(BlockList.get(i).getModel().has("Child")) {
					String childName = BlockList.get(i).getModel().getString("Child");
					for(int j = 0; j < BlockList.size(); j++) {
						if( childName.equals(BlockList.get(j).getModel().getString("Name")) ) {
							
							// Testing
							//System.out.println("Found a match");
							
							BlockFD b1 = BlockList.get(i);
							BlockFD b2 = BlockList.get(j);
							Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
							Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
							LineFD line = new LineFD(b1,b2,p1,p2);
							myPanel.add(line);
							// Then we might want to register line as the listener of both block.
							
							//Finally
							break;
						}
					}
				}
			}
			
			
			setGraphicalDetail(myPanel,graphicalInfo);
			myPanel.setAppropriateBounds();
			return myPanel;
			
		}else if(model.getString("Type").equals("While")) {
			BlockWHILE myPanel = new BlockWHILE(model);
			BlockStartLOOP myStartLoop = (BlockStartLOOP)constructBlockFD(model.getJSONObject("StartLoop"), graphicalInfo);
			myPanel.add(myStartLoop);
			myPanel.setBlockStartLOOP(myStartLoop);
			
			ArrayList<BlockFD> BlockList = new ArrayList<BlockFD>(); // keeping the list to add lines.
			JSONArray myMembers = model.getJSONArray("Members");
			int length = myMembers.length();
			for(int i = 0; i < length; i++) {
				JSONObject tempObj = myMembers.getJSONObject(i);
				BlockFD tempBlock = constructBlockFD(tempObj, graphicalInfo);
				BlockList.add(tempBlock); // add to the collection of Blocks.
				myPanel.add(tempBlock);
			}
			
			BlockList.add(myStartLoop); // also add BlockStartLoop into the list.
			
			
			
			/* Connecting Lines */
			for(int i = 0; i < BlockList.size(); i++) {
				// Testing
				//System.out.println("First Block : " + BlockList.get(i).getModel().getString("Name"));
				//System.out.println("Has child : " + BlockList.get(i).getModel().has("Child"));
				
				if(BlockList.get(i).getModel().has("Child")) {
					String childName = BlockList.get(i).getModel().getString("Child");
					for(int j = 0; j < BlockList.size(); j++) {
						if( childName.equals(BlockList.get(j).getModel().getString("Name")) ) {
							
							// Testing
							//System.out.println("Found a match");
							
							BlockFD b1 = BlockList.get(i);
							BlockFD b2 = BlockList.get(j);
							Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
							Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
							LineFD line = new LineFD(b1,b2,p1,p2);
							myPanel.add(line);
							// Then we might want to register line as the listener of both block.
							
							//Finally
							break;
						}
					}
				}
			}
			
			
			setGraphicalDetail(myPanel,graphicalInfo);
			myPanel.setAppropriateBounds();
			return myPanel;
		}else if(model.getString("Type").equals("StartLoop")) {
			BlockStartLOOP myPanel = new BlockStartLOOP(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
		}else if(model.getString("Type").equals("If")) {
			BlockIF myPanel = new BlockIF(model);
			BlockStartIF startIf = (BlockStartIF)constructBlockFD(model.getJSONObject("StartIf"), graphicalInfo);
			BlockEndIF endIf = (BlockEndIF)constructBlockFD(model.getJSONObject("EndIf"), graphicalInfo);
			myPanel.add(startIf);
			myPanel.add(endIf);
			myPanel.setBlockStartIF(startIf);
			myPanel.setBlockEndIF(endIf);
			
			/** TrueMembers **/
			ArrayList<BlockFD> trueBlockList = new ArrayList<BlockFD>(); // keep a list of Blocks
			JSONArray myMembers = model.getJSONArray("TrueMembers");
			int trueLength = myMembers.length();
			for(int i = 0; i < trueLength; i++){
				JSONObject tempObj = myMembers.getJSONObject(i);
				BlockFD tempBlock = constructBlockFD(tempObj, graphicalInfo);
				trueBlockList.add(tempBlock); // add to the collection of Blocks.
				myPanel.add(tempBlock);
			}
			/** FalseMembers **/
			ArrayList<BlockFD> falseBlockList = new ArrayList<BlockFD>(); // keep a list of Blocks
			myMembers = model.getJSONArray("FalseMembers");
			int falseLength = myMembers.length();
			for(int i = 0; i < falseLength; i++){
				JSONObject tempObj = myMembers.getJSONObject(i);
				BlockFD tempBlock = constructBlockFD(tempObj, graphicalInfo);
				falseBlockList.add(tempBlock); // add to the collection of Blocks.
				myPanel.add(tempBlock);
			}
			
			/* Connecting Lines - trueMembers*/
			
			// Connecting startIf with it's true child.
			if(trueBlockList.size() == 0) {
				Point p1 = startIf.toContainerCoordinate(startIf.getTrueOutport());
				Point p2 = endIf.toContainerCoordinate(endIf.getTrueInport());
				LineFD line = new LineFD(startIf,endIf,p1,p2);
				
				// Stupid check when adding block
				myPanel.setTrueLine(line);
				
				myPanel.add(line);
			}else {
				String childName = startIf.getModel().getString("TrueChild");
				
				//Testing
				//System.out.println("StartIf to true Child.");
				
				for(int i = 0; i < trueBlockList.size(); i++) {
					BlockFD block_i = trueBlockList.get(i);
					if( childName.equals(block_i.getModel().getString("Name")) ) {
						Point p1 = startIf.toContainerCoordinate(startIf.getTrueOutport());
						Point p2 = block_i.toContainerCoordinate(((WithInport)block_i).getInport());
						LineFD line = new LineFD(startIf,block_i,p1,p2);
						myPanel.add(line);
						
						//Testing
						//System.out.println("line 1 : \n" + line.toString());
						
						// Then we might want to register line as the listener of both block.

						//Finally
						break;
					}
				}
				// Connecting lines within true members.
				for(int i = 0; i < trueBlockList.size(); i++) {
					// Testing
					//System.out.println("First Block : " + BlockList.get(i).getModel().getString("Name"));
					//System.out.println("Has child : " + BlockList.get(i).getModel().has("Child"));
					if(trueBlockList.get(i).getModel().has("Child")) {
						childName = trueBlockList.get(i).getModel().getString("Child");
						for(int j = 0; j < trueBlockList.size(); j++) {
							if( childName.equals(trueBlockList.get(j).getModel().getString("Name")) ) {
								BlockFD b1 = trueBlockList.get(i);
								BlockFD b2 = trueBlockList.get(j);
								Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
								Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
								LineFD line = new LineFD(b1,b2,p1,p2);
								myPanel.add(line);
								// Then we might want to register line as the listener of both block.
								
								
								
								//Finally
								break;
							}
						}
					}
				}
				// Connecting the last member to endIf.
				for(int i = 0; i < trueBlockList.size(); i++) {
					BlockFD block_i = trueBlockList.get(i);
					if( block_i.getModel().getString("Child").equals(endIf.getModel().getString("Name")) ) {
						Point p1 = block_i.toContainerCoordinate(((WithOutport)block_i).getOutport());
						Point p2 = endIf.toContainerCoordinate(endIf.getTrueInport());
						LineFD line = new LineFD(block_i,endIf,p1,p2);
						myPanel.add(line);
						
						//Testing
						//System.out.println("line 2 : \n" + line.toString());
						
						// Then we might want to register line as the listener of both block.

						//Finally
						break;
					}
				}
			}
			
			// Connecting startIf with it's false child.
			if(falseBlockList.size() == 0) {
				Point p1 = startIf.toContainerCoordinate(startIf.getFalseOutport());
				Point p2 = endIf.toContainerCoordinate(endIf.getFalseInport());
				LineFD line = new LineFD(startIf,endIf,p1,p2);
				
				// Stupid check when adding block
				myPanel.setFalseLine(line);
				
				myPanel.add(line);
			}else {
				//Testing
				//System.out.println("false else part executed.");
				//System.out.println("falseBlockList.size() = " + falseBlockList.size());
				
				String childName = startIf.getModel().getString("FalseChild");
				for(int i = 0; i < falseBlockList.size(); i++) {
					BlockFD block_i = falseBlockList.get(i);
					
					//Testing
					//System.out.println("childName = " + childName);
					//System.out.println("block_" + i + ".getModel().getString(\"Name\") = " + block_i.getModel().getString("Name"));
					
					if( childName.equals(block_i.getModel().getString("Name")) ) {
						Point p1 = startIf.toContainerCoordinate(startIf.getFalseOutport());
						Point p2 = block_i.toContainerCoordinate(((WithInport)block_i).getInport());
						LineFD line = new LineFD(startIf,block_i,p1,p2);
						myPanel.add(line);
						// Then we might want to register line as the listener of both block.

						//Finally
						break;
					}
				}
				// Connecting lines within false members.
				for(int i = 0; i < falseBlockList.size(); i++) {
					// Testing
					//System.out.println("First Block : " + BlockList.get(i).getModel().getString("Name"));
					//System.out.println("Has child : " + BlockList.get(i).getModel().has("Child"));
					if(falseBlockList.get(i).getModel().has("Child")) {
						childName = falseBlockList.get(i).getModel().getString("Child");
						for(int j = 0; j < falseBlockList.size(); j++) {
							if( childName.equals(falseBlockList.get(j).getModel().getString("Name")) ) {
								BlockFD b1 = falseBlockList.get(i);
								BlockFD b2 = falseBlockList.get(j);
								Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
								Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
								LineFD line = new LineFD(b1,b2,p1,p2);
								myPanel.add(line);
								// Then we might want to register line as the listener of both block.
								
								//Finally
								break;
							}
						}
					}
				}
				// Connecting the last member to endIf.
				for(int i = 0; i < falseBlockList.size(); i++) {
					BlockFD block_i = falseBlockList.get(i);
					if( block_i.getModel().getString("Child").equals(endIf.getModel().getString("Name")) ) {
						Point p1 = block_i.toContainerCoordinate(((WithOutport)block_i).getOutport());
						Point p2 = endIf.toContainerCoordinate(endIf.getFalseInport());
						LineFD line = new LineFD(block_i,endIf,p1,p2);
						myPanel.add(line);
						// Then we might want to register line as the listener of both block.

						//Finally
						break;
					}
				}
			}
			setGraphicalDetail(myPanel, graphicalInfo);
			myPanel.setAppropriateBounds();
			
			// Testing
		/*	Component[] components = myPanel.getComponents();
			for(Component comp : components) {
				if(comp instanceof LineFD) {
					BlockFD Source = ((LineFD)comp).getSource();
					BlockFD Terminal = ((LineFD)comp).getTerminal();
					System.out.println("");
					System.out.println("Line between " + Source.getModel().getString("Name") + " and " + Terminal.getModel().getString("Name"));
					System.out.println("startPoint = " + ((LineFD)comp).getStartPoint() );
					System.out.println("endPoint = " + ((LineFD)comp).getEndPoint() );
					System.out.println("Bounds = " + comp.getBounds());
				}else {
					System.out.println("");
					System.out.println("Block name = " + ((BlockFD)comp).getModel().getString("Name"));
					System.out.println("Bounds = " + ((BlockFD)comp).getBounds());
				}
			}*/
			
			return myPanel;
			
		}else if(model.getString("Type").equals("StartIf")) {
			BlockStartIF myPanel = new BlockStartIF(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
			
		}else if(model.getString("Type").equals("EndIf")) {
			BlockEndIF myPanel = new BlockEndIF(model);
			setGraphicalDetail(myPanel, graphicalInfo);
			return myPanel;
			
		}
		
		
		return null;
	}

	private static void setGraphicalDetail(BlockFD tempBlock, JSONObject graphicalInfo) {
		if(tempBlock.getModel().has("Name")) {
			String myKey = tempBlock.getModel().get("Name").toString();

			//Testing
			//System.out.println("Within setGraphicalDetail() : myKey = " + myKey);
		
			JSONObject info = graphicalInfo.getJSONObject(myKey);
			int x = info.getInt("x");
			int y = info.getInt("y");
		
			tempBlock.setLocation(x,y);
		}
	}

	public static void attachMouseListenersToBlock(UndoManager undoManager,BlockFD currentBlock, BlockPopup blockPopup) {
		//Testing
		//System.out.println(currentBlock.toString() + "\n");
		
		if(currentBlock instanceof BlockFlowDiagram) {
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToBlock(undoManager, (BlockFD)comp, blockPopup);
				}
			}
		}else if(currentBlock instanceof BlockSTART) {
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
			// register a mouse enter listener
			MouseEnterListener mouseEnterListener = new MouseEnterListener(currentBlock);
			currentBlock.addMouseListener(mouseEnterListener);
			
		}else if(currentBlock instanceof BlockEND) {
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
		}else if(currentBlock instanceof BlockDECLARE) {
			//Testing
			//System.out.println("Attach listeners to BlockDECLARE.");
			
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
			// register a double click listener.
			DoubleClickListener doubleClickListener = new DoubleClickListener(currentBlock);
			currentBlock.addMouseListener(doubleClickListener);
			
			// register a block right click listener.
			BlockRightClickListener rightClickListener = new BlockRightClickListener(blockPopup);
			currentBlock.addMouseListener(rightClickListener);
			
		}else if(currentBlock instanceof BlockASSIGN) {
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
			// register a double click listener.
			DoubleClickListener doubleClickListener = new DoubleClickListener(currentBlock);
			currentBlock.addMouseListener(doubleClickListener);
			
			// register a block right click listener.
			BlockRightClickListener rightClickListener = new BlockRightClickListener(blockPopup);
			currentBlock.addMouseListener(rightClickListener);
			
		}else if(currentBlock instanceof BlockOUTPUT) {
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
			// register a double click listener.
			DoubleClickListener doubleClickListener = new DoubleClickListener(currentBlock);
			currentBlock.addMouseListener(doubleClickListener);
			
			// register a block right click listener.
			BlockRightClickListener rightClickListener = new BlockRightClickListener(blockPopup);
			currentBlock.addMouseListener(rightClickListener);
			
		}else if(currentBlock instanceof BlockINPUT) {
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
			// register a double click listener.
			DoubleClickListener doubleClickListener = new DoubleClickListener(currentBlock);
			currentBlock.addMouseListener(doubleClickListener);
			
			// register a block right click listener.
			BlockRightClickListener rightClickListener = new BlockRightClickListener(blockPopup);
			currentBlock.addMouseListener(rightClickListener);

		}else if(currentBlock instanceof BlockFOR) {
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToBlock(undoManager, (BlockFD)comp, blockPopup);
				}
			}
		}else if(currentBlock instanceof BlockWHILE) {
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToBlock(undoManager, (BlockFD)comp, blockPopup);
			
				}
			}
		}else if(currentBlock instanceof BlockStartLOOP) {
			// Testing
			//System.out.println("CurrentBlock is StartLOOP.");
			
			LoopDragListener loopDragListener = new LoopDragListener(undoManager, currentBlock);
			currentBlock.addMouseMotionListener(loopDragListener);
			currentBlock.addMouseListener(loopDragListener);
			
			// register a mouse enter listener
			MouseEnterListener mouseEnterListener = new MouseEnterListener(currentBlock);
			currentBlock.addMouseListener(mouseEnterListener);
			
			// register a double click listener.
			DoubleClickListener doubleClickListener = new DoubleClickListener(currentBlock);
			currentBlock.addMouseListener(doubleClickListener);
			
			// register a block right click listener.
			BlockRightClickListener rightClickListener = new BlockRightClickListener(blockPopup);
			currentBlock.addMouseListener(rightClickListener);
			
		}else if(currentBlock instanceof BlockIF) {
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				// Testing
				//System.out.println(comp.toString());
				//System.out.println("comp instanceof BlockStartIF = " + (comp instanceof BlockStartIF));
				//System.out.println("comp instanceof LineFD = " + (comp instanceof LineFD) + "\n");
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToBlock(undoManager, (BlockFD)comp, blockPopup);
				}
			}
		}else if(currentBlock instanceof BlockStartIF) {
			// Testing
			//System.out.println("CurrentBlock is StartIF.");
			
			LoopDragListener loopDragListener = new LoopDragListener(undoManager, currentBlock);
			currentBlock.addMouseMotionListener(loopDragListener);
			currentBlock.addMouseListener(loopDragListener);
			
			// register a mouse enter listener
			MouseEnterListener mouseEnterListener = new MouseEnterListener(currentBlock);
			currentBlock.addMouseListener(mouseEnterListener);
			
			// register a double click listener.
			DoubleClickListener doubleClickListener = new DoubleClickListener(currentBlock);
			currentBlock.addMouseListener(doubleClickListener);
			
			// register a block right click listener.
			BlockRightClickListener rightClickListener = new BlockRightClickListener(blockPopup);
			currentBlock.addMouseListener(rightClickListener);
			
		}else if(currentBlock instanceof BlockEndIF) {
			BlockDragListener blockDragListener = new BlockDragListener(undoManager,currentBlock);
			currentBlock.addMouseMotionListener(blockDragListener);
			currentBlock.addMouseListener(blockDragListener);
			
		}
	}
	

	public static void attachMouseListenersToLine(LineFD line,LinePopup linePopup) {
		//Testing
		//System.out.println(currentBlock.toString() + "\n");
		
		LineRightClickListener listener = new LineRightClickListener(linePopup);
		line.addMouseListener(listener);
	}
	
	public static void attachMouseListenersToAllLines(BlockFD currentBlock, LinePopup linePopup) {
		//Testing
		//System.out.println(currentBlock.toString() + "\n");
		
		if(currentBlock instanceof BlockFlowDiagram) {
			
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToAllLines((BlockFD)comp, linePopup);
				}else {
					attachMouseListenersToLine((LineFD)comp, linePopup);
				}
			}
		}else if(currentBlock instanceof BlockFOR) {
			
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToAllLines((BlockFD)comp, linePopup);
				}else {
					attachMouseListenersToLine((LineFD)comp, linePopup);
				}
			}
			
		}else if(currentBlock instanceof BlockWHILE) {
			
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToAllLines((BlockFD)comp, linePopup);
				}else {
					attachMouseListenersToLine((LineFD)comp, linePopup);
				}
			}
			
		}else if(currentBlock instanceof BlockIF) {
			
			Component[] compList = currentBlock.getComponents();
			for(Component comp : compList) {
				if(!(comp instanceof LineFD)) {
					attachMouseListenersToAllLines((BlockFD)comp, linePopup);
				}else {
					attachMouseListenersToLine((LineFD)comp, linePopup);
				}
			}
			
		}
	}
	
	
}

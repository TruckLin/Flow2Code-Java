package strategy;

import java.awt.Point;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.object.*;

public class generateBlockIFProcess implements BlockGenerationProcess {
	BlockGenerator processor;
	
	public generateBlockIFProcess(BlockGenerator processor) {
		this.processor = processor;
	}
	
	@Override
	public BlockFD generateBlock(JSONObject model, JSONObject graphicalInfo) {
		BlockIF myPanel = new BlockIF(model);
		BlockStartIF startIf = (BlockStartIF)processor.generate(model.getJSONObject("StartIf"), graphicalInfo);
		BlockEndIF endIf = (BlockEndIF)processor.generate(model.getJSONObject("EndIf"), graphicalInfo);
		myPanel.add(startIf);
		myPanel.add(endIf);
		myPanel.setBlockStartIF(startIf);
		myPanel.setBlockEndIF(endIf);
		
		//Testing
		//System.out.println("\nIn generateBlockIFProcess : ");
		//System.out.println("startIF.getBounds = " + startIf.getBounds().toString());
		//System.out.println("blockStartIF.getTrueOutport = " + startIf.getTrueOutport());
		//System.out.println("blockStartTrueIF.getOutport = " + startIf.getBlockStartTrueIF().getOutport());
		//System.out.println("blockStartTrueIF.getLocation = " + startIf.getBlockStartTrueIF().getLocation());
		//System.out.println("blockStartTrueIF.toContainerCoordiante(blockStartTrueIF.getOutport()) = "
		//			+ startIf.getBlockStartTrueIF().toContainerCoordinate(startIf.getBlockStartTrueIF().getOutport()));
		//System.out.println("blockStartIF.getFalseOutport = " + startIf.getFalseOutport());
		
		/** TrueMembers **/
		ArrayList<BlockFD> trueBlockList = new ArrayList<BlockFD>(); // keep a list of Blocks
		JSONArray myMembers = model.getJSONArray("TrueMembers");
		int trueLength = myMembers.length();
		for(int i = 0; i < trueLength; i++){
			JSONObject tempObj = myMembers.getJSONObject(i);
			BlockFD tempBlock = processor.generate(tempObj, graphicalInfo);
			trueBlockList.add(tempBlock); // add to the collection of Blocks.
			myPanel.add(tempBlock);
		}
		/** FalseMembers **/
		ArrayList<BlockFD> falseBlockList = new ArrayList<BlockFD>(); // keep a list of Blocks
		myMembers = model.getJSONArray("FalseMembers");
		int falseLength = myMembers.length();
		for(int i = 0; i < falseLength; i++){
			JSONObject tempObj = myMembers.getJSONObject(i);
			BlockFD tempBlock = processor.generate(tempObj, graphicalInfo);
			falseBlockList.add(tempBlock); // add to the collection of Blocks.
			myPanel.add(tempBlock);
		}
		
		/* Connecting Lines - trueMembers*/
		
		/** Connecting startIf with it's true child.**/
		if(trueBlockList.size() == 0) {
			// If there is no true members
			PortFD p1 = startIf.getTrueOutport();
			PortFD p2 = endIf.getTrueInport();
			LineFD line = new LineFD(startIf,endIf,p1,p2);
			
			//Testing
			//System.out.println("\nBefore we pass points into line : ");
			//System.out.println("    p1 (startPoint) = " + p1.toString());
			//System.out.println("    p2 (startPoint) = " + p2.toString());
			//System.out.println("");
			//System.out.println("TrueLine Details :");
			//System.out.println("    Source class = " + line.getSource().getClass().toString());
			//System.out.println("    Source bounds = " + line.getSource().getBounds().toString());
			//System.out.println("    Terminal class = " + line.getTerminal().getClass().toString());
			//System.out.println("    Terminal bounds = " + line.getTerminal().getBounds().toString());
			//System.out.println("    StartPoint = " + line.getStartPoint().toString());
			//System.out.println("    EndPoint = " + line.getEndPoint().toString());
			
			myPanel.addLineFD(line);
		}else {
			// If there are at least one true member
			String childName = startIf.getModel().getString("TrueChild");
			
			//Testing
			//System.out.println("StartIf to true Child.");
			
			// Connect blockStartTrueIF with the true child. 
			for(int i = 0; i < trueBlockList.size(); i++) {
				BlockFD block_i = trueBlockList.get(i);
				if( childName.equals(block_i.getModel().getString("Name")) ) {
					PortFD p1 = startIf.getTrueOutport();
					PortFD p2 = ((WithInport)block_i).getInport();
					LineFD line = new LineFD(startIf,block_i,p1,p2);
					
					myPanel.addLineFD(line);
					
					//Testing
					//System.out.println("line 1 : \n" + line.toString());
					
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
							PortFD p1 = ((WithOutport)b1).getOutport();
							PortFD p2 = ((WithInport)b2).getInport();
							LineFD line = new LineFD(b1,b2,p1,p2);
							
							myPanel.addLineFD(line);							
							
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
					PortFD p1 = ((WithOutport)block_i).getOutport();
					PortFD p2 = endIf.getTrueInport();
					LineFD line = new LineFD(block_i,endIf,p1,p2);
					
					myPanel.addLineFD(line);
					
					//Testing
					//System.out.println("line 2 : \n" + line.toString());

					//Finally
					break;
				}
			}
		}
		
		/** Connecting startIf with it's false child. **/
		// If there is no false members
		if(falseBlockList.size() == 0) {
			PortFD p1 = startIf.getFalseOutport();
			PortFD p2 = endIf.getFalseInport();
			LineFD line = new LineFD(startIf,endIf,p1,p2);
			
			myPanel.addLineFD(line);
		}else {
			// If there is at least one false member.
			
			//Testing
			//System.out.println("false else part executed.");
			//System.out.println("falseBlockList.size() = " + falseBlockList.size());
			
			// Connect StartFalseIF with the first child.
			String childName = startIf.getModel().getString("FalseChild");
			for(int i = 0; i < falseBlockList.size(); i++) {
				BlockFD block_i = falseBlockList.get(i);
				
				//Testing
				//System.out.println("childName = " + childName);
				//System.out.println("block_" + i + ".getModel().getString(\"Name\") = " + block_i.getModel().getString("Name"));
				
				if( childName.equals(block_i.getModel().getString("Name")) ) {
					PortFD p1 = startIf.getFalseOutport();
					PortFD p2 = ((WithInport)block_i).getInport();
					LineFD line = new LineFD(startIf,block_i,p1,p2);
					
					myPanel.addLineFD(line);
					
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
						// If there is only one false member, the following if will not be executed.
						if( childName.equals(falseBlockList.get(j).getModel().getString("Name")) ) {
							BlockFD b1 = falseBlockList.get(i);
							BlockFD b2 = falseBlockList.get(j);
							PortFD p1 = ((WithOutport)b1).getOutport();
							PortFD p2 = ((WithInport)b2).getInport();
							LineFD line = new LineFD(b1,b2,p1,p2);
							
							myPanel.addLineFD(line);
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
					PortFD p1 = ((WithOutport)block_i).getOutport();
					PortFD p2 = endIf.getFalseInport();
					LineFD line = new LineFD(block_i,endIf,p1,p2);
					
					myPanel.addLineFD(line);
					// Then we might want to register line as the listener of both block.

					//Finally
					break;
				}
			}
		}
		
		
		
		BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
		
		//Testing
	/*	System.out.println("\nAfter setGraphicalDetail() : ");
		System.out.println("startIF.getBounds = " + startIf.getBounds().toString());
		System.out.println("blockStartIF.getTrueOutport = " + startIf.getTrueOutport());
		System.out.println("blockStartIF.getFalseOutport = " + startIf.getFalseOutport()); */
		
		myPanel.setAppropriateBounds();
		myPanel.repaint();
		
		//Testing
	/*	System.out.println("\nAfter setAppropriateBounds() : ");
		System.out.println("startIF.getBounds = " + startIf.getBounds().toString());
		System.out.println("blockStartIF.getTrueOutport = " + startIf.getTrueOutport());
		System.out.println("blockStartIF.getFalseOutport = " + startIf.getFalseOutport() + "\n"); */
		
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
	}

}

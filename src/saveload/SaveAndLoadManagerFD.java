package saveload;

import java.awt.Point;
import java.util.ArrayList;

import org.json.*;

import com.tcg.json.JSONUtils;

import graph.object.*;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public abstract class SaveAndLoadManagerFD {
	
	public static JSONObject loadFlowDiagramFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	public static JSONObject loadGraphicalInfoFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile("/info.json");
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
							LineFD line = new LineFD((WithOutport)b1,(WithInport)b2,p1,p2);
							myPanel.add(line);
							// Then we might want to register line as the listener of both block.
							
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
			
		}else if(model.getString("Type").equals("Output")) {
			
		}else if(model.getString("Type").equals("Input")) {
			
		}else if(model.getString("Type").equals("For")) {
			BlockFOR myPanel = new BlockFOR(model);
			BlockFD myStartLoop = constructBlockFD(model.getJSONObject("StartLoop"), graphicalInfo);
			myPanel.add(myStartLoop);
			
			JSONArray myMembers = model.getJSONArray("Members");
			int length = myMembers.length();
			for(int i = 0; i < length; i++) {
				JSONObject tempObj = myMembers.getJSONObject(i);
				BlockFD tempBlock = constructBlockFD(tempObj, graphicalInfo);
				myPanel.add(tempBlock);
				
			}
			setGraphicalDetail(myPanel,graphicalInfo);
			myPanel.setAppropriateBounds();
			
			
			return myPanel;
			
		}else if(model.getString("Type").equals("While")) {
			BlockWHILE myPanel = new BlockWHILE(model);
			BlockFD myStartLoop = constructBlockFD(model.getJSONObject("StartLoop"), graphicalInfo);
			myPanel.add(myStartLoop);
			
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
				System.out.println("First Block : " + BlockList.get(i).getModel().getString("Name"));
				System.out.println("Has child : " + BlockList.get(i).getModel().has("Child"));
				if(BlockList.get(i).getModel().has("Child")) {
					String childName = BlockList.get(i).getModel().getString("Child");
					for(int j = 0; j < BlockList.size(); j++) {
						if( childName.equals(BlockList.get(j).getModel().getString("Name")) ) {
							
							// Testing
							System.out.println("Found a match");
							
							BlockFD b1 = BlockList.get(i);
							BlockFD b2 = BlockList.get(j);
							Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
							Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
							LineFD line = new LineFD((WithOutport)b1,(WithInport)b2,p1,p2);
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
			myPanel.setBlockEndIF((BlockEndIF)endIf);
			
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
			/*
			// Connecting startTrue with it's child.
			String childName = startIf.getModel().getString("TrueChild");
			for(int j = 0; j < trueLength; j++) {
				if( childName.equals(trueBlockList.get(j).getModel().getString("Name")) ) {
					BlockFD b2 = trueBlockList.get(j);
					Point p1 = startIf.toContainerCoordinate(((WithOutport)startIf).getOutport());
					Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
					LineFD line = new LineFD((WithOutport)startIf,(WithInport)b2,p1,p2);
					myPanel.add(line);
					// Then we might want to register line as the listener of both block.
					
					//Finally
					break;
				}
			}
			for(int i = 0; i < trueLength; i++) {
				// Testing
				//System.out.println("First Block : " + trueBlockList.get(i).getModel().getString("Name"));
				//System.out.println("Has child : " + trueBlockList.get(i).getModel().has("Child"));
				
				if(BlockList.get(i).getModel().has("Child")) {
					String childName = BlockList.get(i).getModel().getString("Child");
					for(int j = 0; j < length; j++) {
						if( childName.equals(BlockList.get(j).getModel().getString("Name")) ) {
							BlockFD b1 = BlockList.get(i);
							BlockFD b2 = BlockList.get(j);
							Point p1 = b1.toContainerCoordinate(((WithOutport)b1).getOutport());
							Point p2 = b2.toContainerCoordinate(((WithInport)b2).getInport());
							LineFD line = new LineFD((WithOutport)b1,(WithInport)b2,p1,p2);
							myPanel.add(line);
							// Then we might want to register line as the listener of both block.
							
							//Finally
							break;
						}
					}
				}
			}
			*/
			setGraphicalDetail(myPanel, graphicalInfo);
			myPanel.setAppropriateBounds();
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

}

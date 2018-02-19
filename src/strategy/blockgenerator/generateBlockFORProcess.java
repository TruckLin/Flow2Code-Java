package strategy.blockgenerator;

import java.awt.Point;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.object.BlockEndLOOP;
import gui.object.BlockFD;
import gui.object.BlockFOR;
import gui.object.BlockStartLOOP;
import gui.object.LineFD;
import gui.object.PortFD;

public class generateBlockFORProcess implements BlockGenerationProcess {

	BlockGenerator processor;
	
	public generateBlockFORProcess(BlockGenerator processor) {
		this.processor = processor;
	}
	
	
	@Override
	public BlockFD generateBlock(JSONObject model, JSONObject graphicalInfo) {
		BlockFOR myPanel = new BlockFOR(model);
		BlockStartLOOP myStartLoop = (BlockStartLOOP) processor.generate(model.getJSONObject("StartLoop"), graphicalInfo);
		BlockEndLOOP myEndLoop = new BlockEndLOOP();
		myPanel.add(myStartLoop);
		myPanel.add(myEndLoop);
		myPanel.setBlockStartLOOP(myStartLoop);
		myPanel.setBlockEndLOOP(myEndLoop);
		
		myPanel.setExitLine(new LineFD(myStartLoop, myEndLoop,
										myStartLoop.getLoopOutport(),myEndLoop.getInport() ) 
							);
		
		//Testing
		//System.out.println("step1,\nEndLoop's bounds = " + myEndLoop.getBounds().toString());
		
		ArrayList<BlockFD> BlockList = new ArrayList<BlockFD>(); // keeping the list to add lines.
		JSONArray myMembers = model.getJSONArray("Members");
		int length = myMembers.length();
		for(int i = 0; i < length; i++) {
			JSONObject tempObj = myMembers.getJSONObject(i);
			BlockFD tempBlock = processor.generate(tempObj, graphicalInfo);
			BlockList.add(tempBlock); // add to the collection of Blocks.
			myPanel.add(tempBlock);
		}
		
		//Testing
		//System.out.println("step2,\nEndLoop's bounds = " + myEndLoop.getBounds().toString());
		
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
		//Testing
		//System.out.println("Before setGraphicalDetail,\nEndLoop's bounds = " + myEndLoop.getBounds().toString());

		BlockGenerationProcess.setGraphicalDetail(myPanel,graphicalInfo);
		//Testing
		//System.out.println("After setGraphicalDetail, \nEndLoop's bounds = " + myEndLoop.getBounds().toString());
			
		
		myPanel.setAppropriateBounds();
		myPanel.repaint();
		
		//Testing
		//System.out.println("after setAppropriateBounds(), \nEndLoop's bounds = " + myEndLoop.getBounds().toString());
		return myPanel;
	}
}

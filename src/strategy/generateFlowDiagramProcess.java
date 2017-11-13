package strategy;

import java.awt.Point;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.object.BlockFD;
import gui.object.BlockFlowDiagram;
import gui.object.LineFD;

public class generateFlowDiagramProcess implements BlockGenerationProcess{

	BlockGenerator processor;
	
	public generateFlowDiagramProcess(BlockGenerator processor) {
		this.processor = processor;
	}
	
	@Override
	public BlockFD generateBlock(JSONObject model, JSONObject graphicalInfo) {
		//Testing
		//System.out.println("If section is called.");
		
		
		BlockFlowDiagram myPanel = new BlockFlowDiagram(model);
		JSONArray myMembers = model.getJSONArray("Members");
		
		ArrayList<BlockFD> BlockList = new ArrayList<BlockFD>(); // keeping the list to add lines.
		/* Adding Blocks */
		int length = myMembers.length();
		for(int i = 0; i < length; i++) {
			JSONObject tempObj = myMembers.getJSONObject(i);
			BlockFD tempBlock = processor.generate(tempObj, graphicalInfo);
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
						
						myPanel.addLineFD(line);
						
						// Then we might want to register line as the listener of both block.
						
						//Testing
						//System.out.println("In generateFlowDiagramProcess:");
						//System.out.println("line" + i + " Source: " + line.getSource().toString());
						//System.out.println("line" + i + " Terminal: " + line.getTerminal().toString());
						
						//Finally
						break;
					}
				}
			}
		}
		// Generate Line segments for all lines
		myPanel.generateLineSegmentsForAllLines();
		
		BlockGenerationProcess.setGraphicalDetail(myPanel,graphicalInfo);
		myPanel.setAppropriateBounds();
		return myPanel;
	}

}

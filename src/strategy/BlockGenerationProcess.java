package strategy;

import org.json.JSONObject;

import gui.object.BlockFD;

public interface BlockGenerationProcess{
	public BlockFD generateBlock(JSONObject model,JSONObject graphicalInfo);
	
	public static void setGraphicalDetail(BlockFD tempBlock, JSONObject graphicalInfo) {
		if(tempBlock.getModel().has("Name")) {
			String myKey = tempBlock.getModel().get("Name").toString();

			//Testing
			//System.out.println("Within setGraphicalDetail() : myKey = " + myKey);
			if(graphicalInfo!= null && graphicalInfo.has(myKey) ) {
				JSONObject info = graphicalInfo.getJSONObject(myKey);
				int x = info.getInt("x");
				int y = info.getInt("y");
		
				tempBlock.setLocation(x,y);
			}
		}
	}
}

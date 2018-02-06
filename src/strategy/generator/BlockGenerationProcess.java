package strategy.generator;

import org.json.JSONObject;

import gui.object.BlockFD;

public interface BlockGenerationProcess{
	public BlockFD generateBlock(JSONObject model,JSONObject graphicalInfo);
	
	public static void setGraphicalDetail(BlockFD tempBlock, JSONObject graphicalInfo) {
		if(tempBlock.getModel().has("Name")) {
			String myKey = tempBlock.getModel().get("Name").toString();

			//Testing
		/*	if(myKey.equals("While1")) {
				System.out.println("Within setGraphicalDetail() : myKey = " + myKey);
				System.out.println("Bounds before : " + tempBlock.getBounds());
			}*/
			
			if(graphicalInfo!= null && graphicalInfo.has(myKey) ) {
				JSONObject info = graphicalInfo.getJSONObject(myKey);
				int x = info.getInt("x");
				int y = info.getInt("y");
		
				tempBlock.setLocation(x,y);
			}
			
		/*	if(myKey.equals("While1")) {
				System.out.println("Within setGraphicalDetail() : myKey = " + myKey);
				System.out.println("Bounds after : " + tempBlock.getBounds());
			}*/
		}
	}
}

package gui.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.object.BlockFD;
import gui.object.LineFD;

public abstract class CommandUtilityFunctions {
	
	public static void addJSONObjectToParentModel(JSONObject parentModel, JSONObject currentModel, JSONObject sourceModel, JSONObject terminalModel,
													BlockFD parentBlock, LineFD sourceLine) {
		if(parentModel.has("Members")) {
			parentModel.append("Members", currentModel);
			sourceModel.put("Child", currentModel.getString("Name"));
			currentModel.put("Child",terminalModel.getString("Name"));
		}else if(parentModel.getString("Type").equals("If")) {
			// the parent block is BlockIF.
			
			// A test to check whether source and terminal are both in trueMembers.
			boolean sourceInTrueMembers = false;
			boolean terminalInTrueMembers = false;
			JSONArray trueModelList = parentModel.getJSONArray("TrueMembers");
			for(int i = 0; i < trueModelList.length(); i++) {
				String tempName = trueModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(sourceModel.getString("Name"))) sourceInTrueMembers = true;
				if(tempName.equals(terminalModel.getString("Name"))) terminalInTrueMembers = true;
			}
			boolean sourceInFalseMembers = false;
			boolean terminalInFalseMembers = false;
			JSONArray falseModelList = parentModel.getJSONArray("FalseMembers");
			for(int i = 0; i < falseModelList.length(); i++) {
				String tempName = falseModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(sourceModel.getString("Name"))) sourceInFalseMembers = true;
				if(tempName.equals(terminalModel.getString("Name"))) terminalInFalseMembers = true;
			}
			
			if((sourceInTrueMembers && terminalInFalseMembers)) {
				// both in true
				parentModel.append("TrueMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else if((sourceInFalseMembers && terminalInFalseMembers)) {
				// both in false
				parentModel.append("FalseMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else if(sourceInTrueMembers) {
				// only source in true, which means terminal is endIf
				parentModel.append("TrueMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else if (terminalInTrueMembers) {
				// only terminal in true, which means source is startIf
				parentModel.append("TrueMembers", currentModel);
				sourceModel.put("TrueChild", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else if(sourceInFalseMembers) {
				// only source in false, which means terminal is endIf
				parentModel.append("FalseMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else if (terminalInFalseMembers) {
				// only terminal in false, which means source is startIf
				parentModel.append("FalseMembers", currentModel);
				sourceModel.put("FalseChild", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else {
				// both source and terminal not in trueMembers or falseMembers,
				// which means if block is still empty.
				if( sourceLine.getStartPort().getSide().equals("right") ) {
					parentModel.append("TrueMembers", currentModel);
					sourceModel.put("TrueChild", currentModel.getString("Name"));
					currentModel.put("Child",terminalModel.getString("Name"));
				}else if( sourceLine.getEndPort().getSide().equals("left") ) {
					parentModel.append("FalseMembers", currentModel);
					sourceModel.put("FalseChild", currentModel.getString("Name"));
					currentModel.put("Child",terminalModel.getString("Name"));
				}else {
					System.err.println("Something unexpected happened in AddBlockCommand :\nIn redo() \n add block within BlockIF, insert model section");
				}
			}
		}else {
			System.err.println("Parent Model has no key called Members and it's not If.\nIn redo() \n Detail:" + parentModel.toString(10));
		}
	}
	
	public static void removeJSONObjectFromParentModel(JSONObject parentModel, JSONObject currentModel, JSONObject sourceModel, JSONObject terminalModel) {
		// This method also changes the child of sourceModel.
		
		/** Remove the model we inserted into parent model **/
		// Change the child name of sourceModel to terminalModel's name.
		sourceModel.put("Child", terminalModel.getString("Name"));
		// Remove emptyBlock's model from parentModel.
		if(parentModel.has("Members")) {
			JSONArray myArray = parentModel.getJSONArray("Members");
			for(int i = 0; i < myArray.length(); i++) {
				if( myArray.getJSONObject(i).getString("Name").equals(currentModel.getString("Name")) ) {
					myArray.remove(i);
					break;
				}
			}
		}else if(parentModel.getString("Type").equals("If")) {
			// the parent block is BlockIF.
			
			// A test to check if wither source or terminal is in trueMembers. 
			boolean checkFalse = true;
			JSONArray trueModelList = parentModel.getJSONArray("TrueMembers");
			for(int i = 0; i < trueModelList.length(); i++) {
				String tempName = trueModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(currentModel.getString("Name"))) {
					trueModelList.remove(i);
					checkFalse = false;
					break;
				}
			}
			
			if(checkFalse) {
				JSONArray falseModelList = parentModel.getJSONArray("FalseMembers");
				for(int i = 0; i < falseModelList.length(); i++) {
				String tempName = falseModelList.getJSONObject(i).getString("Name");
					if( tempName.equals(currentModel.getString("Name")) ) {
						falseModelList.remove(i);
						break;
					}
				}
			}
		}else {
			System.err.println("Parent Model has no key called Members and it's not If. \n Detail:");
			System.err.println("In CommandUtilityFunctions.removeJSONObjectFromParentModel()");
			System.err.println(parentModel.toString(10));
		}
	}
	
	public static void main(String[] args) {
		//Testing
//		NameCounterManager nameManager = new NameCounterManager();
//		JSONObject model = CommandUtilityFunctions.generateEmptyJSONModel(nameManager, "FlowDiagram");
//		model.append("Members",  CommandUtilityFunctions.generateEmptyJSONModel(nameManager, "If"));
//		System.out.println(model.toString(10));
		
	}
}

package gui.commands;

import java.awt.Point;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.object.BlockASSIGN;
import gui.object.BlockDECLARE;
import gui.object.BlockEND;
import gui.object.BlockEndIF;
import gui.object.BlockFD;
import gui.object.BlockFOR;
import gui.object.BlockFlowDiagram;
import gui.object.BlockIF;
import gui.object.BlockINPUT;
import gui.object.BlockOUTPUT;
import gui.object.BlockSTART;
import gui.object.BlockStartFalseIF;
import gui.object.BlockStartIF;
import gui.object.BlockStartLOOP;
import gui.object.BlockStartTrueIF;
import gui.object.BlockWHILE;
import gui.object.LineFD;

public class CommandUtilityFunctions {
	
	public static void addJSONObjectToParentModel(JSONObject parentModel, JSONObject currentModel, JSONObject sourceModel, JSONObject terminalModel,
													BlockFD parentBlock, LineFD sourceLine) {
		if(parentModel.has("Members")) {
			parentModel.append("Members", currentModel);
			sourceModel.put("Child", currentModel.getString("Name"));
			currentModel.put("Child",terminalModel.getString("Name"));
		}else if(parentModel.getString("Type").equals("If")) {
			// the parent block is BlockIF.
			
			// A test to check if whether source or terminal is in trueMembers.
			boolean inTrueMembers = false;
			JSONArray trueModelList = parentModel.getJSONArray("TrueMembers");
			for(int i = 0; i < trueModelList.length(); i++) {
				String tempName = trueModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(sourceModel.getString("Name")) || tempName.equals(terminalModel.getString("Name"))) {
					inTrueMembers = true;
					break;
				}
			}
			boolean inFalseMembers = false;
			JSONArray falseModelList = parentModel.getJSONArray("FalseMembers");
			for(int i = 0; i < falseModelList.length(); i++) {
				String tempName = falseModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(sourceModel.getString("Name")) || tempName.equals(terminalModel.getString("Name"))) {
					inFalseMembers = true;
					break;
				}
			}
			
			if(inTrueMembers) {
				parentModel.append("TrueMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else if(inFalseMembers){
				parentModel.append("FalseMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else {
				if( sourceLine.getSource() instanceof BlockStartTrueIF ) {
					parentModel.append("TrueMembers", currentModel);
					sourceModel.put("Child", currentModel.getString("Name"));
					currentModel.put("Child",terminalModel.getString("Name"));
				}else if( sourceLine.getSource() instanceof BlockStartFalseIF ) {
					parentModel.append("FalseMembers", currentModel);
					sourceModel.put("Child", currentModel.getString("Name"));
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

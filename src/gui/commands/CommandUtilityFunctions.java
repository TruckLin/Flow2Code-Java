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
import gui.object.BlockStartIF;
import gui.object.BlockStartLOOP;
import gui.object.BlockWHILE;
import gui.object.LineFD;

public class CommandUtilityFunctions {
	
	public static JSONObject generateEmptyJSONModel(NameCounterManager nameManager,String type) {
		if(type.equals("Declare")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Variables",new JSONArray());
			model.put("Child", "");
			
			return model;
		}else if(type.equals("Assign")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Assignments",new JSONArray());
			model.put("Child", "");
			
			return model;
		}else if(type.equals("Output")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Expression","");
			model.put("Child", "");
			
			return model;
		}else if(type.equals("Input")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Variable",new JSONArray());
			model.put("InputExpression", "");
			model.put("Child", "");
			
			return model;
		}else if(type.equals("For")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Initialisation","");
			model.put("Condition", "");
			model.put("Step", "");
			
			JSONObject startLoop = generateEmptyJSONModel(nameManager,"StartLoop");
			
			model.put("StartLoop", startLoop);
			model.put("Members", new JSONArray());
			model.put("Child", "");
			
			return model;
		}else if(type.equals("While")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Expression","");
			
			JSONObject startLoop = generateEmptyJSONModel(nameManager,"StartLoop");
			
			model.put("StartLoop", startLoop);
			model.put("Members", new JSONArray());
			model.put("Child", "");
			
			return model;
		}else if(type.equals("StartLoop")){
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Child", model.getString("Name"));
			
			return model;
		
		}else if(type.equals("If")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Expression", "");
			
			JSONObject startIf = generateEmptyJSONModel(nameManager,"StartIf");
			JSONObject endIf = generateEmptyJSONModel(nameManager,"EndIf");
			startIf.put("TrueChild",endIf.getString("Name"));
			startIf.put("FalseChild", endIf.getString("Name"));
			
			model.put("StartIf", startIf);
			model.put("EndIf", endIf);
			model.put("TrueMembers", new JSONArray());
			model.put("FalseMembers", new JSONArray());
			model.put("Child", "");
			
			return model;
		}else if(type.equals("StartIf")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("TrueChild", "");
			model.put("FalseChild", "");
			
			return model;
		}else if(type.equals("EndIf")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			
			return model;
		}else if(type.equals("Start")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			model.put("Child", "");
			
			return model;
		}else if(type.equals("End")) {
			JSONObject model = new JSONObject();
			model.put("Type", type);
			model.put("Name",nameManager.getAvailableName());
			
			return model;
		}else if(type.equals("FlowDiagram")) {
			JSONObject model = new JSONObject();
			JSONObject START = generateEmptyJSONModel(nameManager,"Start");
			JSONObject END = generateEmptyJSONModel(nameManager,"End");
			model.put("Name",nameManager.getAvailableName());
			model.put("Type", type);
			model.append("Members",START);
			model.append("Members",END);
			
			return model;
		}
		
		return null;
	}
	
	public static BlockFD generateEmptyBlock(NameCounterManager nameManager,String type) {
		
		if(type.equals("Declare")) {
			BlockDECLARE block = new BlockDECLARE(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("Assign")) {
			BlockASSIGN block = new BlockASSIGN(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("Output")) {
			BlockOUTPUT block = new BlockOUTPUT(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("Input")) {
			BlockINPUT block = new BlockINPUT(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("For")) {
			BlockFOR block = new BlockFOR(generateEmptyJSONModel(nameManager,type));
			JSONObject model = block.getModel();
			BlockStartLOOP blockStartLOOP = new BlockStartLOOP(model.getJSONObject("StartLoop"));
			block.add(blockStartLOOP);
			
			// Add loop line
			Point p1 = blockStartLOOP.toContainerCoordinate(blockStartLOOP.getOutport());
			Point p2 = blockStartLOOP.toContainerCoordinate(blockStartLOOP.getInport());
			LineFD line1 = new LineFD(blockStartLOOP, blockStartLOOP,p1,p2 );
			
			//block.add(line1);
			
			block.setBlockStartLOOP(blockStartLOOP);
			block.setAppropriateBounds();
			
			return block;
		}else if(type.equals("While")) {
			BlockWHILE block = new BlockWHILE(generateEmptyJSONModel(nameManager,type));
			JSONObject model = block.getModel();
			BlockStartLOOP blockStartLOOP = new BlockStartLOOP(model.getJSONObject("StartLoop"));
			block.add(blockStartLOOP);
			
			// Add loop line
			Point p1 = blockStartLOOP.toContainerCoordinate(blockStartLOOP.getOutport());
			Point p2 = blockStartLOOP.toContainerCoordinate(blockStartLOOP.getInport());
			LineFD line1 = new LineFD(blockStartLOOP, blockStartLOOP,p1,p2 );
			
			//block.add(line1);
			
			block.setBlockStartLOOP(blockStartLOOP);
			block.setAppropriateBounds();
			
			return block;
		}else if(type.equals("StartLoop")){
			BlockStartLOOP block = new BlockStartLOOP(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("If")) {
			BlockIF block = new BlockIF(generateEmptyJSONModel(nameManager,type));
			JSONObject model = block.getModel();
			BlockStartIF blockStartIF  = new BlockStartIF(model.getJSONObject("StartIf"));
			BlockEndIF blockEndIF = new BlockEndIF(model.getJSONObject("EndIf"));
			
			block.setBlockStartIF(blockStartIF);
			block.setBlockEndIF(blockEndIF);
			
			// Construct the layout of BlockIF.
			block.add(blockStartIF);
			block.add(blockEndIF);
			
			// Add lines
			Point p1 = blockStartIF.toContainerCoordinate(blockStartIF.getTrueOutport());
			Point p2 = blockEndIF.toContainerCoordinate(blockEndIF.getTrueInport());
			LineFD line1 = new LineFD(blockStartIF, blockEndIF,p1,p2 );
			
			//block.add(line1);
			
			// Stupid check when adding block
			block.setTrueLine(line1);
			
			Point p3 = blockStartIF.toContainerCoordinate(blockStartIF.getFalseOutport());
			Point p4 = blockEndIF.toContainerCoordinate(blockEndIF.getFalseInport());
			LineFD line2 = new LineFD(blockStartIF, blockEndIF,p3,p4);
			
			//block.add(line2);
			
			// Stupid check when adding block
			block.setFalseLine(line2);
			
			block.setAppropriateBounds();
			return block;
		}else if(type.equals("StartIf")) {
			BlockStartIF block = new BlockStartIF(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("EndIf")) {
			BlockEndIF block = new BlockEndIF(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("Start")) {
			BlockSTART block = new BlockSTART(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("End")) {
			BlockEND block = new BlockEND(generateEmptyJSONModel(nameManager,type));
			return block;
		}else if(type.equals("FlowDiagram")) {
			BlockFlowDiagram block = new BlockFlowDiagram(generateEmptyJSONModel(nameManager,type));
			return block;
		}
		return null;
	}
	
	public static void addJSONObjectToParentModel(JSONObject parentModel, JSONObject currentModel, JSONObject sourceModel, JSONObject terminalModel,
													BlockFD parentBlock, LineFD sourceLine) {
		if(parentModel.has("Members")) {
			parentModel.append("Members", currentModel);
			sourceModel.put("Child", currentModel.getString("Name"));
			currentModel.put("Child",terminalModel.getString("Name"));
		}else if(parentModel.getString("Type").equals("If")) {
			// the parent block is BlockIF.
			
			// A test to check if wither source or terminal is in trueMembers. 
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
				parentModel.append("TrueMembers", currentModel);
				sourceModel.put("Child", currentModel.getString("Name"));
				currentModel.put("Child",terminalModel.getString("Name"));
			}else {
				if( ((BlockIF)parentBlock).getTrueLine().equals(sourceLine) ) {
					parentModel.append("TrueMembers", currentModel);
					sourceModel.put("Child", currentModel.getString("Name"));
					currentModel.put("Child",terminalModel.getString("Name"));
				}else if( ((BlockIF)parentBlock).getFalseLine().equals(sourceLine) ) {
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
					checkFalse = true;
					break;
				}
			}
			
			if(checkFalse) {
				JSONArray falseModelList = parentModel.getJSONArray("FalseMembers");
				for(int i = 0; i < falseModelList.length(); i++) {
				String tempName = falseModelList.getJSONObject(i).getString("Name");
					if( tempName.equals(currentModel.getString("Name")) ) {
						trueModelList.remove(i);
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
		NameCounterManager nameManager = new NameCounterManager();
		JSONObject model = CommandUtilityFunctions.generateEmptyJSONModel(nameManager, "FlowDiagram");
		model.append("Members",  CommandUtilityFunctions.generateEmptyJSONModel(nameManager, "If"));
		System.out.println(model.toString(10));
		
	}
}

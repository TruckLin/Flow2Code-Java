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
			block.setBlockStartLOOP(blockStartLOOP);
			
			return block;
		}else if(type.equals("While")) {
			BlockWHILE block = new BlockWHILE(generateEmptyJSONModel(nameManager,type));
			JSONObject model = block.getModel();
			BlockStartLOOP blockStartLOOP = new BlockStartLOOP(model.getJSONObject("StartLoop"));
			block.setBlockStartLOOP(blockStartLOOP);
			
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
			block.add(line1);
			Point p3 = blockStartIF.toContainerCoordinate(blockStartIF.getFalseOutport());
			Point p4 = blockEndIF.toContainerCoordinate(blockEndIF.getFalseInport());
			LineFD line2 = new LineFD(blockStartIF, blockEndIF,p3,p4);
			block.add(line2);
			
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
	
	public static void main(String[] args) {
		//Testing
		NameCounterManager nameManager = new NameCounterManager();
		JSONObject model = CommandUtilityFunctions.generateEmptyJSONModel(nameManager, "FlowDiagram");
		model.append("Members",  CommandUtilityFunctions.generateEmptyJSONModel(nameManager, "If"));
		System.out.println(model.toString(10));
		
	}
}

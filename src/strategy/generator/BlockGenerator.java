package strategy.generator;

import java.util.HashMap;

import org.json.JSONObject;

import gui.object.*;


public class BlockGenerator{
	private HashMap<String, BlockGenerationProcess> registry = new HashMap<String, BlockGenerationProcess>();
	
	public BlockGenerator() {
		register("FlowDiagram", new generateFlowDiagramProcess(this));
		register("Start", (model, graphicalInfo) -> {BlockSTART myPanel = new BlockSTART(model);
													BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
													return myPanel;	
												});
		register("End", (model, graphicalInfo) -> {BlockEND myPanel = new BlockEND(model);
													BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
													return myPanel;	
													});
		register("While", new generateBlockWHILEProcess(this));
		register("For", new generateBlockFORProcess(this));
		register("StartLoop", (model, graphicalInfo) -> {BlockStartLOOP myPanel = new BlockStartLOOP(model);
														BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
														return myPanel;	
														});
		register("If", new generateBlockIFProcess(this));
		
		register("StartIf", (model, graphicalInfo) -> {BlockStartIF myPanel = new BlockStartIF(model);
														BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
														return myPanel;
														});
		register("EndIf", (model, graphicalInfo) -> {BlockEndIF myPanel = new BlockEndIF(model);
													BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
													return myPanel;
													});
		register("Declare", (model, graphicalInfo) -> {BlockDECLARE myPanel = new BlockDECLARE(model);
														BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
														return myPanel;	
														});
		register("Assign", (model, graphicalInfo) -> {BlockASSIGN myPanel = new BlockASSIGN(model);
														BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
														return myPanel;	
														});
		register("Output", (model, graphicalInfo) -> {BlockOUTPUT myPanel = new BlockOUTPUT(model);
														BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
														return myPanel;	
														});
		register("Input", (model, graphicalInfo) -> {BlockINPUT myPanel = new BlockINPUT(model);
													BlockGenerationProcess.setGraphicalDetail(myPanel, graphicalInfo);
													return myPanel;	
													});
	}
	
	
	public void register(String blockType, BlockGenerationProcess process) {
		registry.put(blockType, process);
	}

	
	public BlockFD generate(JSONObject model, JSONObject graphicalInfo) {
		//Testing
		//System.out.println("model.getString(\"Type\") = " + model.getString("Type"));
		
		return registry.get(model.getString("Type")).generateBlock(model, graphicalInfo);
	}
	
	public static void main(String[] args) {

		
	}
	
}

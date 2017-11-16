package strategy;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.manager.NameCounterManager;
import gui.object.*;

public class ModelGenerator{
	
	private HashMap<String, ModelGenerationProcess> registry = new HashMap<String, ModelGenerationProcess>();
	private NameCounterManager nameManager;
	
	public ModelGenerator(NameCounterManager nameManager) {
		this.nameManager = nameManager;
		
		register("FlowDiagram",
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Name",nameCounterManager.getAvailableName());
				JSONObject START = modelGenerator.generate("Start");
				JSONObject END = modelGenerator.generate("End");
				START.put("Child", END.getString("Name"));
				
				model.put("Type", "FlowDiagram");
				model.append("Members",START);
				model.append("Members",END);
				return model;
			});
		register("Start", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "Start");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Child", "");
				return model;
			});
		register("End", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "End");
				model.put("Name",nameCounterManager.getAvailableName());
				return model;
			});
		register("While",
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "While");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Expression","");
			
				JSONObject startLoop = modelGenerator.generate("StartLoop");
			
				model.put("StartLoop", startLoop);
				model.put("Members", new JSONArray());
				model.put("Child", "");
				return model;
			});
		register("For",
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "For");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Initialisation","");
				model.put("Condition", "");
				model.put("Step", "");
		
				JSONObject startLoop = modelGenerator.generate("StartLoop");
		
				model.put("StartLoop", startLoop);
				model.put("Members", new JSONArray());
				model.put("Child", "");
				return model;
			});
		register("StartLoop", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "StartLoop");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Child", model.getString("Name"));	
				return model;
			});
		register("If",
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "If");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Expression", "");
		
				JSONObject startIf = modelGenerator.generate("StartIf");
				JSONObject endIf = modelGenerator.generate("EndIf");
				startIf.put("TrueChild",endIf.getString("Name"));
				startIf.put("FalseChild", endIf.getString("Name"));
		
				model.put("StartIf", startIf);
				model.put("EndIf", endIf);
				model.put("TrueMembers", new JSONArray());
				model.put("FalseMembers", new JSONArray());
				model.put("Child", "");
		
				return model;
			});
		
		register("StartIf", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "StartIf");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("TrueChild", "");
				model.put("FalseChild", "");
				return model;
			});
		register("EndIf", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "EndIf");
				model.put("Name",nameCounterManager.getAvailableName());
				return model;
			});
		
		register("Declare", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "Declare");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Variables",new JSONArray());
				model.put("Child", "");
				
				return model;
			});
		register("Assign",
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "Assign");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Assignments",new JSONArray());
				model.put("Child", "");
				
				return model;	
			});
		register("Output", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "Output");
				model.put("Name",nameCounterManager.getAvailableName());
				model.put("Expression","");
				model.put("Child", "");
				
				return model;
			});
		register("Input", 
			(modelGenerator, nameCounterManager) -> {
				JSONObject model = new JSONObject();
				model.put("Type", "Input");
				model.put("Name", nameCounterManager.getAvailableName());
				model.put("Variable",new JSONArray());
				model.put("InputExpression", "");
				model.put("Child", "");
				return model;	
		});
	}
	
	
	public void register(String blockType, ModelGenerationProcess process) {
		registry.put(blockType, process);
	}

	
	public JSONObject generate(String type) {
		//Testing
		//System.out.println("model.getString(\"Type\") = " + model.getString("Type"));
		
		return registry.get(type).generateModel(this,this.nameManager);
	}
	
	public static void main(String[] args) {
		NameCounterManager nameManager = new NameCounterManager();
		ModelGenerator modelGenerator = new ModelGenerator(nameManager);
		JSONObject model = modelGenerator.generate("Input");
		System.out.println(model.toString(10));
	}
	
}

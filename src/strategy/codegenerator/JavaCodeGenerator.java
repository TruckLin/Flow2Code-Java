package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.manager.SaveAndLoadManagerFD;



public class JavaCodeGenerator extends CodeGenerator {
	
	public JavaCodeGenerator() {
		register("FlowDiagram", new FlowDiagramJavaCodeGenProcess(this));
		register("Start", (model, indent) -> {return "";}); // done
		register("End", (model, indent) -> {return "";}); // done
		register("While",(model, indent) -> {
											
											return "";});
		register("For", (model, indent) -> {return "";});
		register("StartLoop", (model, indent) -> {return "";}); // done
		register("If",(model, indent) -> {return "";});
		
		register("StartIf", (model, indent) -> {return "";}); 
		register("EndIf", (model, indent) -> {return "";}); // done
		register("Declare", (model, indent) -> {return "";});
		register("Assign", (model, indent) -> {return "";});
		register("Output", (model, indent) -> {	
												String code = indent + "    " 
															+ "System.out.println( " + model.getString("Expression") + " )"
															+ "\n\n";
												return code;});
		register("Input", (model, indent) -> {return "";});
		
	}
	
	
	public static void main(String[] args) {
		// For testing purpose.
		JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON("/Demo-Output.json");
		JavaCodeGenerator codeGenerator = new JavaCodeGenerator();
		String code = codeGenerator.generate(myModel, "");
		System.out.println(code);
	}
}

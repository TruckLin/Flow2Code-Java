package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.manager.SaveAndLoadManagerFD;
import gui.object.CompositeBlockFD;
import strategy.generator.BlockGenerator;



public class JavaCodeGenerator extends CodeGenerator {
	
	private CompositeBlockFD compositeBlock;
	
	
	public JavaCodeGenerator(CompositeBlockFD composite) {
		
		this.compositeBlock = composite;
		
		register("FlowDiagram", new FlowDiagramJavaCodeGenProcess(this));
		register("Start", (model, indent) -> {return "";}); // done
		register("End", (model, indent) -> {return "";}); // done
		register("While", new WhileJavaCodeGenProcess(this));
		register("For", new ForJavaCodeGenProcess(this));
		register("StartLoop", (model, indent) -> {return "";}); // done
		register("If", new IfJavaCodeGenProcess(this));
		
		register("StartIf", (model, indent) -> {return "";}); 
		register("EndIf", (model, indent) -> {return "";}); // done
		register("Declare", new DeclareJavaCodeGenProcess(this));
		register("Assign", (model, indent) -> {
												String code = "";
												JSONArray assignments = model.getJSONArray("Assignments");
												for(int i = 0; i < assignments.length(); i++) {
													code = code + indent +
															assignments.getJSONObject(i).getString("TargetVariable") + " = " + 
															assignments.getJSONObject(i).getString("Expression") + ";\n";
												}
												code += "\n";
												return code;
											});
		register("Output", (model, indent) -> {	
												String code = indent + 
														"System.out.println( " + model.getString("Expression") 
															+ " );" + "\n\n";
												return code;
											});
		register("Input", (model, indent) -> {return "";});
		
	}
	
	/** Getters and Setters **/
	public CompositeBlockFD getCompositeBlockFD() {
		return this.compositeBlock;
	}
	
	public static void main(String[] args) {
		// For testing purpose.
		JSONObject FlowDiagramModel = new JSONObject();
		JSONObject FlowDiagramInfo = new JSONObject();
		SaveAndLoadManagerFD.loadFlowDiagramFromZippedFile(FlowDiagramModel, FlowDiagramInfo, 
				".\\assets\\Demo-DeclareScope.foo");
		
		CompositeBlockFD blockFlowDiagram;
		BlockGenerator blockGenerator = new BlockGenerator();
	    //this.FlowDiagramModel = modelGenerator.generate("FlowDiagram");
	    blockFlowDiagram = (CompositeBlockFD)blockGenerator.generate(FlowDiagramModel, FlowDiagramInfo);
	    
		JavaCodeGenerator codeGenerator = new JavaCodeGenerator(blockFlowDiagram);
		String code = codeGenerator.generate(FlowDiagramModel, "");
		System.out.println(code);
		
		//Testing
		/*
		int[] myNumbers = new int[5];
		myNumbers[0] = 5;
		myNumbers[1] = 0;
		myNumbers[2] = 5;
		myNumbers[3] = 0;
		myNumbers[4] = 5;
		System.out.println(myNumbers[0]);
		*/
	}
}

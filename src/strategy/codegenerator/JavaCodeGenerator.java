package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.manager.SaveAndLoadManagerFD;
import gui.object.CompositeBlockFD;
import strategy.generator.BlockGenerator;



public class JavaCodeGenerator extends CodeGenerator {
	
	private CompositeBlockFD compositeBlock;
	private ArrayList<JSONObject> variableList = new ArrayList<JSONObject>();
	
	public JavaCodeGenerator(CompositeBlockFD composite) {
		
		this.compositeBlock = composite;
		
		register("FlowDiagram", new FlowDiagramJavaCodeGenProcess(this));
		register("Start", (model, code, indent) -> {return code;}); // done
		register("End", (model, code, indent) -> {return code;}); // done
		register("While", new WhileJavaCodeGenProcess(this));
		register("For", new ForJavaCodeGenProcess(this));
		register("StartLoop", (model, code, indent) -> {return code;}); // done
		register("If", new IfJavaCodeGenProcess(this));
		
		register("StartIf", (model, code, indent) -> {return code;}); 
		register("EndIf", (model, code, indent) -> {return code;}); // done
		register("Declare", new DeclareJavaCodeGenProcess(this));
		register("Assign", (model, code, indent) -> {
												if(!model.getBoolean("CodeGen")) {
													code = code + indent + "// Write your own assignment\n";
													return code;
												}
												JSONArray assignments = model.getJSONArray("Assignments");
												for(int i = 0; i < assignments.length(); i++) {
													code = code + indent +
															assignments.getJSONObject(i).getString("TargetVariable") + " = " + 
															assignments.getJSONObject(i).getString("Expression") + ";\n";
												}
												//code += "\n";
												return code;
											});
		register("Output", (model, code, indent) -> {	
												code = code + indent + 
														"System.out.println( " + model.getString("Expression") 
															+ " );" + "\n";
												return code;
											});
		register("Input", new InputJavaCodeGenProcess(this));
		
	}
	
	/** Getters and Setters **/
	public CompositeBlockFD getCompositeBlockFD() {
		return this.compositeBlock;
	}
	public ArrayList<JSONObject> getVariableList(){
		return this.variableList;
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
		String code = "";
		code = codeGenerator.generate(FlowDiagramModel,code, "");
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

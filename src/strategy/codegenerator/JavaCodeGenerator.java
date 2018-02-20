package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.codeView.TextAreaLeaf;
import gui.codeView.TextBranch;
import gui.codeView.TextLeaf;
import gui.manager.SaveAndLoadManagerFD;
import gui.object.CompositeBlockFD;
import strategy.blockgenerator.BlockGenerator;



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
													code.addTree(new TextLeaf(indent + "//Write your own assignments\n" + indent));
													code.addTree(new TextAreaLeaf());
													code.addTree(new TextLeaf("\n"));
													return code;
												}
												JSONArray assignments = model.getJSONArray("Assignments");
												for(int i = 0; i < assignments.length(); i++) {
													code.addTree(new TextLeaf(indent +
															assignments.getJSONObject(i).getString("TargetVariable") + " = " + 
															assignments.getJSONObject(i).getString("Expression") + ";\n"));
												}
												//code += "\n";
												return code;
											});
		register("Output", (model, code, indent) -> {	
												code.addTree(new TextLeaf(indent + 
														"System.out.println( " + model.getString("Expression") 
															+ " );" + "\n"));
												return code;
											});
		register("Input", new InputJavaCodeGenProcess(this));
		register("Break", (model, code, indent) -> {	
			code.addTree(new TextLeaf(indent + "break;\n"));
			return code;
		});
		
	}
	
	/** Getters and Setters **/
	public CompositeBlockFD getCompositeBlockFD() {
		return this.compositeBlock;
	}
	public ArrayList<JSONObject> getVariableList(){
		return this.variableList;
	}
	
}

package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.codeView.TextBranch;
import gui.codeView.TextFieldLeaf;
import gui.codeView.TextLeaf;

public class IfJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public IfJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public TextBranch generateCode(JSONObject model,TextBranch code, String indent) {
		// TODO Auto-generated method stub
		
		// Setting up
		JSONArray trueMembers = model.getJSONArray("TrueMembers");
		JSONArray falseMembers = model.getJSONArray("FalseMembers");
		int numTrueMembers = trueMembers.length();
		int numFalseMembers = falseMembers.length();
		
		// Check whether we should generate the code.
		if(!model.getBoolean("CodeGen")) {
			code.addTree(new TextLeaf(indent + "// Fill in the content of this if statement.\n"
								+ indent + "if("));
			code.addTree(new TextFieldLeaf());
			code.addTree(new TextLeaf(") {\n"));

		} else {
			code.addTree(new TextLeaf(indent + "if( " + model.getString("Expression") + " ) {\n"));
		}
		// we first find the JSONObject of Type "Start"
		JSONObject currentModel;
		
		// Code generation for True members.
		String targetName = model.getJSONObject("StartIf").getString("TrueChild");
		
		// Get the number of variables declared before we enter the bracket.
		ArrayList<JSONObject> varList = this.codeGenerator.getVariableList();
		int numOfVarStart = varList.size();
		
		while(true) {
			if(model.getJSONObject("EndIf").getString("Name").equals(targetName)) {
				break;
			}
			
			for(int i = 0; i < numTrueMembers; i++) {
				currentModel = trueMembers.getJSONObject(i);
				
				//Testing
				//System.out.print(currentModel.getString("Name"));
				
				if(currentModel.getString("Name").equals(targetName)) {
					code = (TextBranch) this.codeGenerator.generate(currentModel, code, indent + "    ");
					targetName = currentModel.getString("Child");
					break;
				}
			}
		}
		
		// Destroy all variables added in during if statement.
		for(int k = numOfVarStart; k <= (varList.size()-1); k++) {
			varList.remove(k);
		}
		
		// Close up the bracket
		code.addTree(new TextLeaf(indent + "}"));
		if( numFalseMembers == 0 ) {
			code.addTree(new TextLeaf("\n"));
			return code;
		}
		
		// At this point, we know that we have some statement in else{}, numOfVarStart should remain unchanged.
		// Code generation for false members.
		code.addTree(new TextLeaf(" else {\n"));
		targetName = model.getJSONObject("StartIf").getString("FalseChild");
		while(true) {
			if(model.getJSONObject("EndIf").getString("Name").equals(targetName)) {
				break;
			}
			for(int i = 0; i < numFalseMembers; i++) {
				currentModel = falseMembers.getJSONObject(i);
				
				//Testing
				//System.out.print(currentModel.getString("Name"));
				
				if(currentModel.getString("Name").equals(targetName)) {
					code = (TextBranch) this.codeGenerator.generate(currentModel, code, indent + "    ");
					targetName = currentModel.getString("Child");
					break;
				}
			}
		}
		
		// Destroy all variables added in during else statement.
		for(int k = numOfVarStart; k <= (varList.size()-1); k++) {
			varList.remove(k);
		}
		
		// Close up the bracket
		code.addTree(new TextLeaf(indent + "}\n"));
		
		return code;
	}

}

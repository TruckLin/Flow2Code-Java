package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public ForJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public String generateCode(JSONObject model,String code, String indent) {
		// TODO Auto-generated method stub
		

		// Get the number of variables declared before we enter the for loop.
		ArrayList<JSONObject> varList = this.codeGenerator.getVariableList();
		int numOfVarStart = varList.size();
		
		// Setting up
		JSONArray members = model.getJSONArray("Members");
		int numMembers = members.length();
		
		// Check whether we should generate the code.
		if(!model.getBoolean("CodeGen")) {
			code = code + indent + "// Fill in the content of this for loop.\n";
			code = code + indent + "for(        ) {\n";
		} else {
			code = code + indent + "for( " + model.getString("Initialisation") + "; " + model.getString("Condition") 
			+ "; "+ model.getString("PostProcess") + " ) {\n";
		}
		
		// we first find the JSONObject of Type "Start"
		JSONObject currentModel;
		
		// Code generation for True members.
		String targetName = model.getJSONObject("StartLoop").getString("Child");
		while(true) {
			if(model.getJSONObject("StartLoop").getString("Name").equals(targetName)) {
				break;
			}
			
			for(int i = 0; i < numMembers; i++) {
				currentModel = members.getJSONObject(i);
				
				//Testing
				//System.out.print(currentModel.getString("Name"));
				
				if(currentModel.getString("Name").equals(targetName)) {
					code = this.codeGenerator.generate(currentModel, code, indent + "    ");
					targetName = currentModel.getString("Child");
					break;
				}
			}
		}
		
		code = code + indent + "}\n";
		
		// Destroy all variables added in during for loop.
		for(int k = numOfVarStart; k <= (varList.size()-1); k++) {
			varList.remove(k);
		}
		
		return code;
	}

}

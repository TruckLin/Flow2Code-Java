package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class IfJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public IfJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public String generateCode(JSONObject model, String indent) {
		// TODO Auto-generated method stub
		
		// Setting up
		JSONArray trueMembers = model.getJSONArray("TrueMembers");
		JSONArray falseMembers = model.getJSONArray("FalseMembers");
		int numTrueMembers = trueMembers.length();
		int numFalseMembers = falseMembers.length();
		
		String code = "";
		code = code + indent + "if( " + model.getString("Expression") + " ) {\n";
		
		// we first find the JSONObject of Type "Start"
		JSONObject currentModel;
		
		// Code generation for True members.
		String targetName = model.getJSONObject("StartIf").getString("TrueChild");
		while(true) {
			if(model.getJSONObject("EndIf").getString("Name").equals(targetName)) {
				break;
			}
			
			for(int i = 0; i < numTrueMembers; i++) {
				currentModel = trueMembers.getJSONObject(i);
				
				//Testing
				//System.out.print(currentModel.getString("Name"));
				
				if(currentModel.getString("Name").equals(targetName)) {
					String currentCode = this.codeGenerator.generate(currentModel, indent + "    ");
					code += currentCode;
					targetName = currentModel.getString("Child");
					break;
				}
			}
		}
		
		// Code generation for false members.
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
					String currentCode = this.codeGenerator.generate(currentModel, indent + "    ");
					code += currentCode;
					targetName = currentModel.getString("Child");
					break;
				}
			}
		}
		
		
		code = code + indent + "}\n\n";
		
		return code;
	}

}

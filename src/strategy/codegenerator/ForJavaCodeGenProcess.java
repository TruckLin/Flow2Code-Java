package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public ForJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public String generateCode(JSONObject model, String indent) {
		// TODO Auto-generated method stub
		
		// Setting up
		JSONArray members = model.getJSONArray("Members");
		int numMembers = members.length();
		String code = "";
		code = code + indent + "for( " + model.getString("Initialisation") + "; " + model.getString("Condition") 
										+ "; "+ model.getString("PostProcess") + " ) {\n\n";
		
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

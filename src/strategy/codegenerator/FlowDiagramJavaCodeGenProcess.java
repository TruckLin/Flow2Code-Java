package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class FlowDiagramJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public FlowDiagramJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public String generateCode(JSONObject model,String code, String indent) {
		// TODO Auto-generated method stub
		
		// Setting up
		JSONArray members = model.getJSONArray("Members");
		int numMembers = members.length();
		boolean shouldGenerateMore = false;
		
		code = code + indent + "public class FlowCode{\n" + 
					  indent + "    " +"public static void main(String[] args){\n\n";
		
		// we first find the JSONObject of Type "Start"
		JSONObject currentModel;
		String targetName = "";
		for(int i = 0; i < numMembers; i++) {
			currentModel = members.getJSONObject(i);
			if(currentModel.getString("Type").equals("Start")) {
				shouldGenerateMore = true;
				targetName = currentModel.getString("Child");
			}
		}
		while(shouldGenerateMore) {
			for(int i = 0; i < numMembers; i++) {
				currentModel = members.getJSONObject(i);
				
				//Testing
				//System.out.print(currentModel.getString("Name"));
				
				if(currentModel.getString("Name").equals(targetName)) {
					if(currentModel.getString("Type").equals("End")) {
						shouldGenerateMore = false;
					}else {
						code = this.codeGenerator.generate(currentModel,code ,indent + "    " + "    ");
						targetName = currentModel.getString("Child");
					}
				}
			}
		}
		
		
		// Check whether Scanner is used
		if(code.contains("import java.util.Scanner;")) {
			code = code + indent + "    " + "    " +"sc.close();\n";
		}
		
		code = code + indent + "    " + "}\n";
		code = code + indent + "}";
		
		return code;
	}
}

package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.codeView.TextAreaLeaf;
import gui.codeView.TextBranch;
import gui.codeView.TextLeaf;

public class FlowDiagramJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public FlowDiagramJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public TextBranch generateCode(JSONObject model,TextBranch code, String indent) {
		// TODO Auto-generated method stub
		
		// Setting up
		JSONArray members = model.getJSONArray("Members");
		int numMembers = members.length();
		boolean shouldGenerateMore = false;
		
		code.addTree(new TextLeaf(indent + "public class FlowCode{\n" + 
					  indent + "    " +"public static void main(String[] args){\n"));
		
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
						code = (TextBranch) this.codeGenerator.generate(currentModel,code ,indent + "    " + "    ");
						targetName = currentModel.getString("Child");
					}
				}
			}
		}
		if(code.contains("sc.next")) {
			if(code.contains("import java.util.Scanner;")) {
				code.addTree(new TextLeaf(indent + "    " + "    " +"sc.close();\n"));
			}
		}else if (code.contains("import java.util.Scanner;")){
			code.addTree(new TextLeaf(indent+"    " + "    " + "// Process your own input object.\n" + indent + "    " + "    " ));
			code.addTree(new TextAreaLeaf());
			code.addTree(new TextLeaf("\n"));
		}
		// Check whether Scanner is used
		
		
		code.addTree(new TextLeaf(indent + "    " + "}\n" + indent +"}")); // this is always the final leaf.
		
		return code;
	}
}

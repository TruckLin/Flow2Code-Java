package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.codeView.TextAreaLeaf;
import gui.codeView.TextBranch;
import gui.codeView.TextLeaf;

public class DeclareJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	
	public DeclareJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public TextBranch generateCode(JSONObject model,TextBranch code, String indent) {
		// TODO Auto-generated method stub
		
		if(!model.getBoolean("CodeGen")) {
			code.addTree(new TextLeaf(indent + "// Write your own Declaration.\n" + indent));
			code.addTree(new TextAreaLeaf());
			code.addTree(new TextLeaf("\n"));
		}
		
		JSONArray variables = model.getJSONArray("Variables");
		for(int i = 0; i < variables.length(); i = i+1) {
			
			JSONObject currentVar = variables.getJSONObject(i);
			
			codeGenerator.getVariableList().add(currentVar);
			
			String dataType = currentVar.getString("DataType");
			Boolean isArray = currentVar.getBoolean("IsArray");
			String VariableName = currentVar.getString("VariableName");
			int size = currentVar.getInt("Size");

			String dataTypeString = "";
			if(dataType.equals("Integer")) {
				dataTypeString = "int";
			}else if(dataType.equals("Real")) {
				dataTypeString = "double";
			}else if(dataType.equals("Boolean")) {
				dataTypeString = "boolean";
			}else {
				dataTypeString = "String";
			}
			
			
			if(model.getBoolean("CodeGen")) {
				code.addTree(new TextLeaf(indent));
				code.addTree(new TextLeaf(dataTypeString));
				if(isArray) {
					code.addTree(new TextLeaf("[]"));
				}
				
				code.addTree(new TextLeaf(" " + VariableName));
				
				if( !isArray ) {
					code.addTree(new TextLeaf(";\n"));
				}else {
					code.addTree(new TextLeaf(" = new " + dataTypeString +"[" + size + "];\n"));
				}
			}
			
		} // end of for loop.
		
		return code;
	}

}

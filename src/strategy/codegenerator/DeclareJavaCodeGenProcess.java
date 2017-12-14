package strategy.codegenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeclareJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	public DeclareJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}
	
	@Override
	public String generateCode(JSONObject model, String indent) {
		// TODO Auto-generated method stub
		
		String code = "";
		JSONArray variables = model.getJSONArray("Variables");
		for(int i = 0; i < variables.length(); i = i+1) {
			
			JSONObject currentVar = variables.getJSONObject(i);
			String dataType = currentVar.getString("DataType");
			Boolean isArray = currentVar.getBoolean("IsArray");
			String VariableName = currentVar.getString("VariableName");
			int size = currentVar.getInt("Size");
			
			code = code + indent;
			
			String dataTypeString = "";
			if(dataType.equals("Integer")) {
				code = code + "int";
				dataTypeString = "int";
			}else if(dataType.equals("Real")) {
				code = code + "double";
				dataTypeString = "double";
			}else if(dataType.equals("Boolean")) {
				code = code + "boolean";
				dataTypeString = "boolean";
			}else {
				code = code + "String";
				dataTypeString = "String";
			}
			
			if(isArray) {
				code = code + "[]";
			}
			
			code = code + " " + VariableName;
			
			if( !isArray ) {
				code = code + ";\n";
			}else {
				code = code + " = new " + dataTypeString +"[" + size + "];\n";
			}
		} // end of for loop.
		
		code = code + "\n";
		
		return code;
	}

}

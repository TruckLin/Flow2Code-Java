package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONObject;

public class InputJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	
	public InputJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
		
	}
	
	@Override
	public String generateCode(JSONObject model, String CodeSoFar, String indent) {
		// TODO Auto-generated method stub
		
		// Target variable string written down by user.
		String targetVariable = model.getString("TargetVariable");
		
		//Testing
		//System.out.println(targetVariable);
		//System.out.println(model.toString(10));
		
		// Test whether input is referring to an element in an array.
		int indexOfLeftBracket = targetVariable.indexOf('[');
		String targetVariableName = ""; // name after excluding [xxx];
		if(indexOfLeftBracket != -1) {
			targetVariableName = targetVariable.substring(0, indexOfLeftBracket);
		}else {
			targetVariableName = targetVariable;
		}
		
		ArrayList<JSONObject> variableList = this.codeGenerator.getVariableList();
		int refferedVar = -1;
		// Try to find whether the variable is declared.
		// if not, refferedVar remains zero.
		for(int i = 0; i < variableList.size(); i++) {
			if(variableList.get(i).getString("VariableName").equals(targetVariableName)) refferedVar = i;
		}
		
		// if we cannot find the variable.
		String commenting = "";
		if(refferedVar == -1) {
			commenting = "//";
			CodeSoFar = CodeSoFar + "\n" + indent + commenting + targetVariableName +" is either not declared or not visible.\n";
			return CodeSoFar;
		} else {
			// get the variable type and decide which function to call.
			String variableType = variableList.get(refferedVar).getString("DataType");
			String readInStatement = "";
			if(variableType.equals("Integer")) {
				readInStatement = " = "+ "sc.nextInt();\n";
			}else if(variableType.equals("Real")) {
				readInStatement = " = " + "sc.nextDouble();\n";
			}else if(variableType.equals("Boolean")) {
				readInStatement = " = " + "sc.nextBoolean();\n";
			}else if(variableType.equals("String")) {
				readInStatement = " = " + "sc.next();\n";
			}
			
			// Check whether Scanner is imported.
			// if it is, we assume the global variable Scanner sc  is also declared.
			if ( !CodeSoFar.contains("import java.util.Scanner;")) {
				CodeSoFar = "import java.util.Scanner; \n" + CodeSoFar;
				
				int indexOfClass = CodeSoFar.indexOf("args)");
				
				int indexToStart = CodeSoFar.indexOf('{', indexOfClass);
				
				CodeSoFar = CodeSoFar.substring(0, indexToStart+1) + 
											"\n" + "    " + "    " + "Scanner sc = new Scanner(System.in);" + 
											CodeSoFar.substring(indexToStart+1);
				
				//Testing
				//sSystem.out.println("indexOfClass = " + indexOfClass);
				
			}
			
			
			
			CodeSoFar = CodeSoFar + indent + model.getString("TargetVariable") + readInStatement;

			
			return CodeSoFar;
		}
		
		
	}

}

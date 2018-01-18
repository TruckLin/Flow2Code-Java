package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONObject;

public class InputJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	private int numberOfScanner = 1;
	
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
			// get the variable type
			String variableType = variableList.get(refferedVar).getString("DataType");;
			String readInStatement = "";
			String scannerName = "sc" + numberOfScanner;
			this.numberOfScanner ++;
			
			if(variableType.equals("Integer")) {
				readInStatement = " = "+ scannerName + ".nextInt();\n";
			}else if(variableType.equals("Real")) {
				readInStatement = " = " + scannerName + ".nextDouble();\n";
			}else if(variableType.equals("Boolean")) {
				readInStatement = " = " + scannerName + ".nextBoolean();\n";
			}else if(variableType.equals("String")) {
				readInStatement = " = " + scannerName + ".next();\n";
			}
			
			// Check whether Scanner is imported.
			if ( !CodeSoFar.contains("import java.util.Scanner;")) {
				CodeSoFar = "import java.util.Scanner; \n" + CodeSoFar;
			}
			
			
			CodeSoFar = CodeSoFar + indent + commenting +"Scanner " + scannerName + " = new Scanner(System.in);\n";
			
			
			
			CodeSoFar = CodeSoFar + indent + model.getString("TargetVariable") + readInStatement;
			CodeSoFar = CodeSoFar + indent + scannerName + ".close();\n";
			return CodeSoFar;
		}
		
		
	}

}

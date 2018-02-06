package strategy.codegenerator;

import java.util.ArrayList;

import org.json.JSONObject;

import editor.system.testing.TextBranch;
import editor.system.testing.TextLeaf;

public class InputJavaCodeGenProcess implements CodeGenerationProcess{
	private JavaCodeGenerator codeGenerator;
	
	public InputJavaCodeGenProcess(JavaCodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
		
	}
	
	@Override
	public TextBranch generateCode(JSONObject model,TextBranch code, String indent) {
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
		
		//Testing
		//System.out.println("variableList.size() = " + variableList.size());
		
		for(int i = 0; i < variableList.size(); i++) {
			// Testing
			//System.out.println("i'th var name = " + variableList.get(i).getString("VariableName"));
			//System.out.println("TargetVariableName = " + targetVariableName);
			//System.out.println(".equals() = " + variableList.get(i).getString("VariableName").equals(targetVariableName));
			
			
			if(variableList.get(i).getString("VariableName").equals(targetVariableName)) refferedVar = i;
		}
		
		// if we cannot find the variable.
		String commenting = "";
		if(refferedVar == -1) {
			commenting = "//";
			code.addTree(new TextLeaf("\n" + indent + commenting + targetVariableName +" is either not declared or not visible.\n"));
			return code;
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
			if ( !code.contains("import java.util.Scanner;")) {
				code.insertTree(0, new TextLeaf("import java.util.Scanner; \n"));
				int indexOfClass = code.getIndexOfLeafContainString("args)");
				
				if(indexOfClass != -1) {
					code.insertTree(indexOfClass + 1, new TextLeaf("    " + "    " + "Scanner sc = new Scanner(System.in);\n" ));
				}
				//Testing
				//sSystem.out.println("indexOfClass = " + indexOfClass);
				
			}
			code.addTree(new TextLeaf(indent + model.getString("TargetVariable") + readInStatement));
			return code;
		}
		
		
	}

}

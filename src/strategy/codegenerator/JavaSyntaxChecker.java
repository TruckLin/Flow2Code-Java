package strategy.codegenerator;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import gui.object.CompositeBlockFD;

public class JavaSyntaxChecker {
	
	private CompositeBlockFD compositeBlock;
	private JSONObject modelFD;
	
	private VariableBranch variableBranch;
	private ArrayList<String> warningMessages = new ArrayList<String>();
	
	
	public JavaSyntaxChecker(CompositeBlockFD composite) {
		this.compositeBlock = composite;
		this.modelFD = composite.getModel();
		this.variableBranch = new VariableBranch();
	}
	
	/** Getters and Setters **/
	public VariableBranch getVriableBranch() {
		return this.variableBranch;
	}
	public ArrayList<String> getWarningMessages(){
		return this.warningMessages;
	}
	
	//Some static methods that help with syntax check before code generation.
	
	// validate variable name, checks containing [ and ], probably don't want to use this when checking assignment.
	public static boolean variableNameValid(String name) {
		// Characters that should not be used in the name of a variable.
		String[] invalidCharacters = {
				";" , ":" , "\\","+", "-", "*", "/", "@", "#", "!","$", "%", "^", "&", "|", "?" , "," , ".", "`",
				"{", "}", "[", "]", "(", ")", "=", ">", "<", "\"", "\'"," ", "	","\n"
				};
		// words reserved by java.
		String[] invalidNames = {
				"abstract","continue","for","new","switch","assert","default","goto","package","synchronized",
				"boolean","do","if","private","this","break","double","implements","protected","throw",
				"byte","else","import","public","throws","case","enum","instanceof","return","transient",
				"catch","extends","int","short","try","char","final","interface","static","void","class",
				"finally","long","strictfp","volatile","const","float","native","super","while"
		};
		
		for(String str : invalidCharacters) {
			if(name.contains(str)) {
				return false;
			}
		}
		
		for (String str : invalidNames) {
			if(name.equals(str)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean variableNameValid(String name, boolean checkBrackets) {
		// Characters that should not be used in the name of a variable.
		String[] invalidCharacters = {
				";" , ":" , "\\","+", "-", "*", "/", "@", "#", "!","$", "%", "^", "&", "|", "?" , "," , ".", "`",
				"{", "}", "[", "]", "(", ")", "=", ">", "<", "\"", "\'"," ", "	","\n"
				};
		// words reserved by java.
		String[] invalidNames = {
				"abstract","continue","for","new","switch","assert","default","goto","package","synchronized",
				"boolean","do","if","private","this","break","double","implements","protected","throw",
				"byte","else","import","public","throws","case","enum","instanceof","return","transient",
				"catch","extends","int","short","try","char","final","interface","static","void","class",
				"finally","long","strictfp","volatile","const","float","native","super","while"
		};
		
		for(String str : invalidCharacters) {
			if( !checkBrackets && ( str.equals("[") || str.equals("]")) ) {
				continue;
			}
			if(name.contains(str)) {
				
				return false;
			}
		}
		
		for (String str : invalidNames) {
			if(name.equals(str)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean expressionValid(String expression) {
		
		return false;
	}
	
}

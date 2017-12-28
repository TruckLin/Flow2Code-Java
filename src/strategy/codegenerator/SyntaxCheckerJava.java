package strategy.codegenerator;

import java.util.HashMap;

import org.json.JSONObject;

public class SyntaxCheckerJava {
	
	private JSONObject modelFD;
	
	private HashMap<String, Integer> variableScope;
	
	public SyntaxCheckerJava(JSONObject model) {
		this.modelFD = model;
		this.variableScope = new HashMap<String, Integer>();
	}
	
	
	public void assignScope() {
		int scope = 0;
		
		
	}
	
	
	
	//Some static methods that help with syntax check before code generation.
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
	
	public static boolean expressionValid(String expression) {
		
		return false;
	}
	
}

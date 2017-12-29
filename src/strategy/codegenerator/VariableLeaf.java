package strategy.codegenerator;

import org.json.JSONObject;

public class VariableLeaf extends VariableTree{
	private JSONObject variable;
	
	public VariableLeaf(JSONObject var) {
		this.variable = var;
	}
	
	/** Class Functions **/
	public String toString(String indent) {
		String temp = indent + "    ";
		temp = temp + variable.getString("DataType") + " ";
		temp = temp + variable.getString("VariableName") + " ";
		temp = temp + variable.getString("IsArray") + " ";
		temp = temp + variable.getString("Size") + " ";	
		return temp;
	}
	
}

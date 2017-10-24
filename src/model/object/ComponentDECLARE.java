package model.object;

public class ComponentDECLARE extends OrdinaryComponentFD{
	
	private String variableType;
	private String variableName;
	private boolean isArray; 
	private int ArraySize = 1;
	
	public ComponentDECLARE(String name) {
		super(name);
	}
	
	public ComponentDECLARE(String name, String variableType, String variableName, boolean isArray) {
		super(name);
		this.variableType = variableType;
		this.variableName = variableName;
		this.isArray = isArray;
	}
	
	public ComponentDECLARE(String name, String variableType, String variableName, boolean isArray, int ArraySize) {
		super(name);
		this.variableType = variableType;
		this.variableName = variableName;
		this.isArray = isArray;
		this.ArraySize = ArraySize;
	}
	
	/** Getters and Setters **/
	public String getVariableType() {
		return this.variableType;
	}
	public void setVariableType(String type) {
		this.variableType = type;
	}
	public String getVariableName() {
		return this.variableName;
	}
	public void setVariableName(String name) {
		this.variableName = name;
	}
	public boolean getIsArray() {
		return this.isArray;
	}
	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}
	public int getArraySize() {
		return this.ArraySize;
	}
	public void setArraySize(int s) {
		this.ArraySize = s;
	}
	
	
}

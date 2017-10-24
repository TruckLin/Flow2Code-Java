package model.object;

public class ComponentASSIGN extends OrdinaryComponentFD{
	private String targetVariable;
	private String inputExpression;
	
	
	
	public ComponentASSIGN(String name) {
		super(name);
	}
	
	/** Getters and Setters **/
	public String getTargetVariable() {
		return this.targetVariable;
	}
	public void settargetVariable(String var) {
		this.targetVariable = var;
	}
	public String getInputExpression() {
		return this.inputExpression;
	}
	public void setInputExpression(String expression) {
		this.inputExpression = expression;
	}


	
	
}

package model.object;

public class ComponentOUTPUT extends OrdinaryComponentFD{
	
	private String expression;
	
	public ComponentOUTPUT(String name) {
		super(name);
		this.expression = null;
	}
	
	public ComponentOUTPUT(String name, String expression) {
		super(name);
		this.expression = expression;
	}
	
	/**Getters and Setters**/
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
}

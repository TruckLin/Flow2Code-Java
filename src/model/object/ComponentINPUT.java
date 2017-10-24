package model.object;

public class ComponentINPUT extends OrdinaryComponentFD{
	
	private String expression;
	
	public ComponentINPUT(String name) {
		super(name);
		this.expression = null;
	}
	
	public ComponentINPUT(String name, String expression) {
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
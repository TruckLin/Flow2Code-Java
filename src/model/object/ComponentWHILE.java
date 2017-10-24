package model.object;


public class ComponentWHILE extends CompositeComponentFD {
	private ComponentStartLOOP componentStartLOOP;
	private String inputExpression;
	
	public ComponentWHILE(String name, String startLoopName,String expression) {
		super(name);
	
		this.componentStartLOOP = new ComponentStartLOOP(startLoopName);
		
		this.getComponentList().add(componentStartLOOP);
		
		this.inputExpression = expression;
	}
	
	
	/**Getters and Setters**/
	public ComponentStartLOOP getComponentStartLOOP() {
		return this.componentStartLOOP;
	}
	public void setComponentStatLOOP( ComponentStartLOOP comp) {
		this.getComponentList().remove(this.componentStartLOOP);
		this.componentStartLOOP = comp;
		this.getComponentList().add(componentStartLOOP);
		
	}
	
	public String getInputExpression() {
		return this.inputExpression;
	}
	public void setInputExpression(String expression) {
		this.inputExpression = expression;
	}
	
}

package model.object;

public class ComponentFOR extends CompositeComponentFD{
	private ComponentStartLOOP componentStartLOOP;
	private String initialisation;
	private String condition;
	private String in_decrement;
	
	public ComponentFOR(String name, String startLoopName,String initialisation, String condition, String in_decrement) {
		super(name);
		
		this.componentStartLOOP = new ComponentStartLOOP(startLoopName);
		this.componentStartLOOP.setDirectChild(this.componentStartLOOP);
		this.getComponentList().add(componentStartLOOP);
		
		this.initialisation = initialisation;
		this.condition = condition;
		this.in_decrement = in_decrement;
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
	public String getInitialisation() {
		return this.initialisation;
	}
	public void setInitialisation(String initial) {
		this.initialisation = initial;
	}
	public String getCoondition() {
		return this.condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getIn_Decrement() {
		return this.in_decrement;
	}
	public void setIn_Decrement(String in_de) {
		this.in_decrement = in_de;
	}
	
}

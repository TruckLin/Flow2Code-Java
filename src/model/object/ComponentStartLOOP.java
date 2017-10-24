package model.object;

public class ComponentStartLOOP extends OrdinaryComponentFD{
	
	public ComponentStartLOOP(String name) {
		super(name);
		
		this.setDirectChild(this);
		this.setDirectParent(this);
	}
	
}

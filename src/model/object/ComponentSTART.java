package model.object;

import model.interfaces.WithDirectChild;
import model.interfaces.WithDirectParent;

public class ComponentSTART extends ComponentFD implements WithDirectChild{
	private WithDirectParent directChild;
	
	public ComponentSTART(String name) {
		super(name);
	}
	
	/** getters and setters**/
	public WithDirectParent getDirectChild() {
		return directChild;
	}
	
	public void setDirectChild(WithDirectParent comp) {
		this.directChild = comp;
	}
}

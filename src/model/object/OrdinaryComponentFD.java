package model.object;

import model.interfaces.WithDirectChild;
import model.interfaces.WithDirectParent;

public abstract class OrdinaryComponentFD extends ComponentFD implements WithDirectChild, WithDirectParent{
	private WithDirectChild directParent;
	private WithDirectParent directChild;
	
	public OrdinaryComponentFD(String name) {
		super(name);
	}
	
	/** getters and setters**/
	public WithDirectParent getDirectChild() {
		return directChild;
	}
	
	public void setDirectChild(WithDirectParent comp) {
		this.directChild = comp;
	}
	public WithDirectChild getDirectParent() {
		return directParent;
	}
	
	public void setDirectParent(WithDirectChild comp) {
		this.directParent = comp;
	}
}

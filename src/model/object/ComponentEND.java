package model.object;

import model.interfaces.WithDirectChild;
import model.interfaces.WithDirectParent;

public class ComponentEND extends ComponentFD implements WithDirectParent{
	private WithDirectChild directParent;
	
	public ComponentEND(String name) {
		super(name);
	}
	
	/** getters and setters**/
	@Override
	public WithDirectChild getDirectParent() {
		
		return directParent;
	}

	@Override
	public void setDirectParent(WithDirectChild comp) {
		
		this.directParent = comp;
		
	}
	
	

}

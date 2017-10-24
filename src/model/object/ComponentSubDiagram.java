package model.object;

import java.util.ArrayList;

import model.interfaces.WithDirectChild;
import model.interfaces.WithDirectParent;
import model.object.*;


public abstract class ComponentSubDiagram extends ComponentFD{
	private ArrayList<ComponentFD> componentList;
	
	public ComponentSubDiagram(String name) {
		super(name);
		this.componentList = new ArrayList<ComponentFD>();
	}
	
	/**Getters and Setter**/ 
	public ArrayList<ComponentFD> getComponentList(){
		return componentList;
	}
	
	/** Operations **/
	public void addComponentFD(OrdinaryComponentFD comp, WithDirectChild parent, WithDirectParent child) {
		int idxP = this.getComponentList().indexOf(parent);
		int idxC = this.getComponentList().indexOf(child);
		
		// Testing
		//System.out.println("idxP = " + idxP);
		//System.out.println("idxC = " + idxC);
		//System.out.println(parent.getDirectChild().toString());
		
		if(idxC != -1 && idxP != -1 && parent.getDirectChild().equals(child)) {
			child.setDirectParent(comp);
			parent.setDirectChild(comp);
			comp.setDirectParent(parent);
			comp.setDirectChild(child);
			this.getComponentList().add(idxP + 1, comp);
		}else {
			for(ComponentFD i : this.componentList) {
				i.addComponentFD(comp, parent, child);
			}
		}
	}
	public void removeComponentFD(OrdinaryComponentFD comp) {
		if(this.componentList.contains(comp)) {
			WithDirectChild parent = comp.getDirectParent();
			WithDirectParent child = comp.getDirectChild();
		
			parent.setDirectChild(child);
			child.setDirectParent(parent);
			this.componentList.remove(comp);
		}else {
			for(ComponentFD i : this.componentList) {
				i.removeComponentFD(comp);
			}
		}
	}
	
	/** toReadableString(), could aid future debugging. **/
	public String toReadableString(String indent) {
		String temp = indent + this.getName() + " : " + this.getClass() + "\n";
		
		//Testing
		//System.out.println(temp);
		
		for( ComponentFD i : this.getComponentList()) {
			temp = temp + i.toReadableString(indent + "    ");
		}
		return temp;
	}
}

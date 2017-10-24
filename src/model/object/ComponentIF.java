package model.object;

import model.FD.FlowDiagram;

public class ComponentIF extends CompositeComponentFD{
	private FlowDiagram trueSubDiagram;
	private FlowDiagram falseSubDiagram;
	
	public ComponentIF(String name) {
		super(name);
		
		this.trueSubDiagram = new FlowDiagram("TrueSubDiagram","TrueStart", "TrueEnd");
		this.falseSubDiagram = new FlowDiagram("FalseSubDiagram","FalseStart", "FalseEnd");
		
		this.getComponentList().add(this.trueSubDiagram);
		this.getComponentList().add(this.falseSubDiagram);
		
	}
	
	/** Getters and Setters **/
	public FlowDiagram getTrueSubDiagram() {
		return this.trueSubDiagram;
	}
	public FlowDiagram getFalseSubDiagram() {
		return this.falseSubDiagram;
	}
	public ComponentFD getTrueChild() {
		return (ComponentFD)this.getTrueSubDiagram().getComponentSTART().getDirectChild();
	}
	public ComponentFD getFalseChild() {
		return (ComponentFD)this.getFalseSubDiagram().getComponentSTART().getDirectChild();
	}
	
	
	
}

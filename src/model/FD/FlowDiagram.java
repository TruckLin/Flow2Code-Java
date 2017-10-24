package model.FD;

import java.util.ArrayList;

import model.interfaces.WithDirectChild;
import model.interfaces.WithDirectParent;
import model.object.*;

public class FlowDiagram extends ComponentSubDiagram{
	private ComponentSTART componentSTART;
	private ComponentEND componentEND;
	
	public FlowDiagram(String name, String mainName, String endName) {
		super(name);
		
		this.componentSTART = new ComponentSTART(mainName);
		this.componentEND = new ComponentEND(endName);
		
		this.componentSTART.setDirectChild(componentEND);
		this.getComponentList().add(this.componentSTART);
		this.componentEND.setDirectParent(componentSTART);
		this.getComponentList().add(this.componentEND);
	}
	
	/**Getters and Setter**/ 
	public ComponentSTART getComponentSTART() {
		return this.componentSTART;
	}
	public void setComponentSTART(ComponentSTART comp) {
		this.componentSTART = comp;
	}
	public ComponentEND getComponentEND() {
		return this.componentEND;
	}
	public void setComponentEND(ComponentEND comp) {
		this.componentEND = comp;
	}
	
	
	/**Main function**/
	public static void main(String[] args) {
		FlowDiagram myDiagram =  new FlowDiagram("myDiagram","1","2");
		ComponentDECLARE myDeclare = new ComponentDECLARE("3");
		ComponentWHILE myWhile = new ComponentWHILE("while1", "whilestart", "x > 0");
		
		myDiagram.addComponentFD(myWhile, myDiagram.getComponentSTART(), myDiagram.getComponentEND());
		myDiagram.addComponentFD(myDeclare, myWhile.getComponentStartLOOP(), myWhile.getComponentStartLOOP());
		
		ComponentWHILE myWhile2 = new ComponentWHILE("while2", "whilestart2", "x == 0");
		
		myDiagram.addComponentFD(myWhile2, myDeclare, myWhile.getComponentStartLOOP());
		
		
		ComponentIF myIF = new ComponentIF("IF1");
		myDiagram.addComponentFD(myIF, myWhile2.getComponentStartLOOP(), myWhile2.getComponentStartLOOP());
		
		myDiagram.addComponentFD(myDeclare, myIF.getFalseSubDiagram().getComponentSTART(),
				myIF.getFalseSubDiagram().getComponentEND());
		
		
		System.out.println(myDiagram.toReadableString(""));
		
	}
	
	
}

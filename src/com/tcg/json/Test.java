package com.tcg.json;

import org.json.*;

import model.*;

public class Test {

	public static void main(String[] args) {
		
		FlowDiagram myFlowDiagram = new FlowDiagram("/FlowDiagramDemo.json");
		
		
		System.out.println(myFlowDiagram.toString());
		

	}

}

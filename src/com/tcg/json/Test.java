package com.tcg.json;

import org.json.*;

import model.*;
import saveload.SLdemo;

public class Test {

	public static void main(String[] args) {
		
		SLdemo myFlowDiagram = new SLdemo("/FlowDiagramDemo.json");
		
		
		System.out.println(myFlowDiagram.toString());
		

	}

}

package model;

import java.util.ArrayList;

import org.json.JSONObject;

import com.tcg.json.JSONUtils;

public class FlowDiagram {
	
	private ArrayList<OpBlock> BlockList;
	
	// Default constructor.
	public FlowDiagram() {
		this.BlockList = new ArrayList<OpBlock>();
	}
	
	public FlowDiagram(ArrayList<OpBlock> someList) {
		this.BlockList = someList;
	}
	
	// Convert directly from a .json file into FlowDiagram object
	public FlowDiagram(String path) {
		JSONObject[] obj = JSONUtils.getMultipleJSONObjectsFromFile("/FlowDiagramDemo.json");
		
		this.BlockList = new ArrayList<OpBlock>();
		for(int i = 0; i < obj.length ; i++) {	
			OpBlock myBlock = new OpBlock(obj[i]); // This requires some changes if we have many different blocks.
			this.BlockList.add(myBlock);
		}
		
	}
	
	public void addComponent(OpBlock comp) {
		this.BlockList.add(comp);
	}
	
	public void rmComponent(OpBlock comp) {
		this.BlockList.remove(comp);
	}
	
	public String toString() {
		return BlockList.toString();
	}
	
	public static void main(String[] args) {
		

	}

}

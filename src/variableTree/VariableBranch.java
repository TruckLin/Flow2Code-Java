package variableTree;

import java.util.ArrayList;

public class VariableBranch extends VariableTree{
	private ArrayList<VariableTree> list;
	
	public VariableBranch() {
		this.list = new ArrayList<VariableTree>();
	}
	
	public VariableBranch(ArrayList<VariableTree> list) {
		this.list = list;
	}
	
	/** Class Functions **/
	public void addVariable(VariableTree var) {
		this.list.add(var);
	}
	
	// or perhaps you can say "last".
	public VariableBranch getLatestVariableBranch() {
		if(list.size() == 0) {
			return this;
		}
		
		VariableTree temp = list.get(list.size()-1);
		
		if(temp instanceof VariableBranch) {
			return ((VariableBranch) temp).getLatestVariableBranch();
		}else {
			return this;
		}
	}
	
	public VariableLeaf getLatestVariableLeaf() {
		VariableTree temp = list.get(list.size()-1);
		
		if(temp instanceof VariableBranch) {
			return ((VariableBranch) temp).getLatestVariableLeaf();
		}else {
			return (VariableLeaf) temp;
		}
	}
	
	public String toString(String indent) {
		String temp = indent + "    VariableBranch : \n";
		
		for(VariableTree currentTree: list) {
			temp =  temp + currentTree.toString(indent + "    ") + "\n";
			
		}
		return temp;
				
	}
	/** Getters and Setters **/
	
	
}

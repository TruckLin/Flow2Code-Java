package editor.system.testing;

import java.util.ArrayList;
import java.util.HashMap;

public class TextBranch extends TextTree{
	private ArrayList<TextTree> leaves;
//	protected HashMap<String, TextTree> maps; // not sure if we are going to need this.
	
	public TextBranch() {
		this.leaves = new ArrayList<TextTree>();
//		this.maps = new HashMap<String, TextTree>();
	} 
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		
		//Testing
	//	System.out.println("I'm looping all the leaves...");
		
		String txt = "";
		for(TextTree textLeaf : leaves) {
			txt = txt + textLeaf.getText();
			
			//Testing
			//System.out.println("I'm looping all the leaves...");
	//		System.out.println(textLeaf.getText());
		}	
		return txt;
	}
	
	public void addTree(TextTree someTree) {
		this.leaves.add(someTree);
	}
	
	/* Waiting to be completed , not sure if we need this.*/
//	public void addTree(String key, TextTree someTree) {
		// TODO Auto-generated method stub
		
//	}
	
	public void insertTree(int index, TextTree someTree) {
		this.leaves.add(index, someTree);
	}
	/* Waiting to be completed , not sure if we need this.*/
//	public void insertTree(String key, TextTree someTree, int index) {
		// TODO Auto-generated method stub
		
//	}

	public int getIndexOf(TextTree someTree) {
		// TODO Auto-generated method stub
		return leaves.indexOf(someTree);
	}

	public TextTree get(int index) {
		// TODO Auto-generated method stub
		return leaves.get(index);
	}
	
	public ArrayList<TextTree> getLeaves(){
		return this.leaves;
	}

	/* Waiting to be completed , not sure if we need this.*/
//	public TextTree get(String key) {
		// TODO Auto-generated method stub
//		return null;
//	}
}

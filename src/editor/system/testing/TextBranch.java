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
	
	public void insertTree(int index, TextTree someTree) {
		this.leaves.add(index, someTree);
	}

	public int getIndexOf(TextTree someTree) {
		// TODO Auto-generated method stub
		return leaves.indexOf(someTree);
	}
	
	// This function returns the first leaf that contains the given String
	// return -1 if not found.
	public int getIndexOfLeafContainString(String str) {
		int i = 0;
		for(TextTree textLeaf : leaves) {
			if(textLeaf.contains(str)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	//This function returns the first leaf the contains str after certain index k.
	// ***including the k'th leaf***
	// return -1 if not found.
	public int getIndexOfLeafContainString(String str, int k) {
		for( ; k < this.leaves.size(); k++) {
			if(this.leaves.get(k).contains(str)) {
				return k;
			}
		}
		return -1;
	}
	
	public TextTree get(int index) {
		// TODO Auto-generated method stub
		return leaves.get(index);
	}
	
	public ArrayList<TextTree> getLeaves(){
		return this.leaves;
	}
	@Override
	public boolean contains(String str) {
		// TODO Auto-generated method stub
		for(TextTree textLeaf : leaves) {
			if(textLeaf.contains(str)) {
				return true;
			}
		}
		return false;
	}
}

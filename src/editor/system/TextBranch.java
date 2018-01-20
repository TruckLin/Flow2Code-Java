package editor.system;

import java.util.ArrayList;
import java.util.HashMap;

public class TextBranch extends TextTree{
	protected ArrayList<TextTree> leaves;
	protected HashMap<String, TextTree> maps;
	
	public TextBranch() {
		this.leaves = new ArrayList<TextTree>();
		this.maps = new HashMap<String, TextTree>();
	} 
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTree(String key, TextTree someTree) {
		// TODO Auto-generated method stub
		
	}

	public void insertTree(String key, TextTree someTree, int index) {
		// TODO Auto-generated method stub
		
	}

	public int getIndexOf(TextTree someTree) {
		// TODO Auto-generated method stub
		return 0;
	}

	public TextTree get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public TextTree get(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}

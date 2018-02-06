package editor.system.testing;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TextTree {
	
	public abstract String getText();
	
	public abstract boolean contains(String str);
/*	
	getText()
	able to insert text without too much trouble
	able to correspond model to TextTree and possible position in parant tree.
	addTree(TextTree someTree)
	insertTree(TextTree insertion, int index) : put insertion on position index, move other trees to the next position. 
	Perhaps a name to individual trees for a hash property.
	TextTree.get(int index)
	TextTree.get(String key) this should search the whole tree.
*/
	
}

package editor.system.testing;

public class TextLeaf extends TextTree{
	private String text;
	public TextLeaf() {
		super();
		this.text = "";
	}
	
	public TextLeaf(String txt) {
		this.text = txt;
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}

	@Override
	public boolean contains(String str) {
		// TODO Auto-generated method stub
		return this.text.contains(str);
	}

}

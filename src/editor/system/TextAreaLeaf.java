package editor.system;

import javax.swing.JTextArea;

public class TextAreaLeaf extends TextTree{
	
	private JTextArea leafArea;
		
	public TextAreaLeaf() {
		super();
		this.leafArea = new JTextArea();
	}
	
	/** Getters and Setters **/
	public JTextArea getTextArea() {
		return this.leafArea;
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return leafArea.getText();
	}
}

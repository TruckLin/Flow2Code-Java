package editor.system.testing;

import javax.swing.JTextArea;

public class TextAreaLeaf extends TextTree{
	
	private JTextArea leafArea;
	
	private boolean initialised = false;
	// This variable indicated whether the boundary of textArea has been initialised.
		
	public TextAreaLeaf() {
		super();
		this.leafArea = new JTextArea();
	}
	
	/** Getters and Setters **/
	public JTextArea getTextArea() {
		return this.leafArea;
	}
	public boolean getInitialised() {
		return this.initialised;
	}
	public void setInitialised(boolean b) {
		this.initialised = b;
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return leafArea.getText();
	}

	@Override
	public boolean contains(String str) {
		// TODO Auto-generated method stub
		return this.leafArea.getText().contains(str);
	}
}

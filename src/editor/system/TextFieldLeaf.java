package editor.system;

import javax.swing.JTextField;

public class TextFieldLeaf extends TextTree{

	private JTextField myTextField;
	
	private boolean initialised = false;
	// This variable indicated whether the boundary of textfield has been initialised.
	
	private int currentWidth = 0;
	
	public TextFieldLeaf(){
		super();
		
		this.myTextField = new JTextField();
	}
	
	public JTextField getTextField() {
		return this.myTextField;
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.myTextField.getText();
	}
	
	/** Getters and Setters **/
	public boolean getInitialised() {
		return this.initialised;
	}
	public void setInitialised(boolean b) {
		this.initialised = b;
	}
	public int getCurrentWidth() {
		return this.currentWidth;
	}
	public void setCurrentWidth(int cw) {
		this.currentWidth = cw;
	}
	
	
}

package gui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class CustomButton extends JPanel{
	protected ActionListener myAction;
	
	/** Getters and Setters **/
	public ActionListener getMyActionListener() {
		return myAction;
	}
	public void setMyActionListener(ActionListener action) {
		this.myAction = action;
	}
}

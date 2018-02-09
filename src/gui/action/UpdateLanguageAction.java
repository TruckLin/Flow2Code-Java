package gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.mainFrame.Flow2Code;

public class UpdateLanguageAction implements ActionListener{

	private Flow2Code mainFrame;
	
	public UpdateLanguageAction(Flow2Code mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/* What we need to update : 
		 * 1. MenuBar
		 * 2. The Blocks
		 * 3. LinePopup
		 * 4. BlockPopup
		 * 5. 
		 * */
		
		// Update Blocks
		//this.mainFrame.getBlockFlowDiagram().setLanguageBundle(languageBundle);
	}
	
}

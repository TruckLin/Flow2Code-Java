package gui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JDialog;

public class BlockEditDialog extends JDialog{
	
	public BlockEditDialog () {
		super();
		this.setModal(true);
		this.setLocationByPlatform(true);
	}
}

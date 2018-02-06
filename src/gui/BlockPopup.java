package gui;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import gui.commands.DeleteBlockCommand;
import gui.manager.UndoManager;
import gui.object.*;

public class BlockPopup extends JPopupMenu{
	private UndoManager undoManager;
	private BlockFD block;
	private JMenuItem deleteItem = new JMenuItem();
	private JMenuItem editItem = new JMenuItem();
	
	public BlockPopup (UndoManager undoManager, BlockFD block) {
		super();
		this.undoManager = undoManager;
		this.setBlockFD(block);
		
		ResourceBundle languageBundle = block.getLanguageBundle();
		
		//Testing
		//System.out.println("In the constructor of BlockPopup:");
		//System.out.println("    Block type : " + block.getModel().getString("Type"));
		//System.out.println("    LanguageBundle is null : " + (languageBundle == null));
		//System.out.println("    deleteItem is null : " + (deleteItem == null));
	//	System.out.println("    languageBundle.getString(\"Delete\") = " + languageBundle.getString("Delete"));
		
		this.deleteItem.setText( languageBundle.getString("Delete") );
		this.editItem.setText( languageBundle.getString("Edit") );
		
		// Add all the menu items
		this.add(this.deleteItem);
		this.add(this.editItem);
		
	}
	
	/** Getters and Setters **/
	public BlockFD getBlockFD() {
		return this.block;
	}
	public void setBlockFD(BlockFD newBlock) {
		this.block = newBlock;
		
		// remove previous action listeners
		ActionListener[] listenerList = this.deleteItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.deleteItem.removeActionListener(listener);
		}
		listenerList = this.editItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.editItem.removeActionListener(listener);
		}
		
		// enable check for editItem
		editItem.setEnabled(this.block.isEditable());
		
		// Add new action listener to menu items
		this.deleteItem.addActionListener(e -> deleteBlockAction(this.block));
		this.editItem.addActionListener(e -> {
										if(this.block.representCompositeBlock()) {
											showEditDialog(undoManager,(BlockFD)this.block.getParent());
										}else {
											showEditDialog(undoManager,this.block);
										}
										});
	}
	
	public void showEditDialog(UndoManager undoManager,BlockFD block) {
		BlockEditDialog myDialog = block.getBlockEditDialog(undoManager);
		myDialog.setVisible(true);
	}
	
	/** The action that tells undoManager to execute various command **/
	public void deleteBlockAction(BlockFD block) {
		DeleteBlockCommand command = new DeleteBlockCommand(block);
		
		undoManager.execute(command);
	}
	
	/** the checking and popup the dialog **/

	private boolean isLoop(BlockFD block) {
		if(block instanceof BlockStartIF || block instanceof BlockStartLOOP) {
			return true;
		}
		return false;
	}
	
}

package gui;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import gui.commands.AddBlockCommand;
import gui.commands.DeleteBlockCommand;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.BlockASSIGN;
import gui.object.BlockDECLARE;
import gui.object.BlockFD;
import gui.object.BlockIF;
import gui.object.BlockINPUT;
import gui.object.BlockOUTPUT;
import gui.object.BlockStartIF;
import gui.object.BlockStartLOOP;
import gui.object.LineFD;

public class BlockPopup extends JPopupMenu{
	private UndoManager undoManager;
	private BlockFD block;
	private JMenuItem deleteItem = new JMenuItem("Delete");
	private JMenuItem editItem = new JMenuItem("Edit");
	
	public BlockPopup (UndoManager undoManager) {
		super();
		this.undoManager = undoManager;
		
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
		
		// enable chack for editItem
		if(isEditable(this.block)) {
			editItem.setEnabled(true);
		}else {
			editItem.setEnabled(false);
		}
		
		// Add new action listener to menu items
		this.deleteItem.addActionListener(e -> deleteBlockAction(this.block));
		this.editItem.addActionListener(e -> {
										if(isLoop(this.block)) {
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
	private boolean isEditable(BlockFD block) {
		if(block instanceof BlockStartLOOP || block instanceof BlockStartIF || block instanceof BlockOUTPUT
		|| block instanceof BlockINPUT || block instanceof BlockASSIGN || block instanceof BlockDECLARE) {
			return true;
		}
		return false;
	}
	private boolean isLoop(BlockFD block) {
		if(block instanceof BlockStartLOOP || block instanceof BlockIF) {
			return true;
		}
		return false;
	}
	
}

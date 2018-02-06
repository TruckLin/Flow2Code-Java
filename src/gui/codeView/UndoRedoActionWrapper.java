package gui.codeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;

public class UndoRedoActionWrapper implements ActionListener{

	private AbstractAction abstractAction;
	
	private JButton btn;
	
	AbstractDocument temp;
	
	private PropertyChangeListener enabledListener = (e)->{
		this.btn.setEnabled(this.abstractAction.isEnabled());
	};
	
	public UndoRedoActionWrapper(AbstractAction ac, JButton btn){
		this.abstractAction = ac;
		this.btn = btn;
		this.abstractAction.addPropertyChangeListener(enabledListener);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.abstractAction.actionPerformed(e);
	}

}

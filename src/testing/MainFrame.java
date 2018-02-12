package testing;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import gui.codeView.CodeEditPanel;
import gui.codeView.TextAreaLeaf;
import gui.codeView.TextBranch;
import gui.codeView.TextFieldLeaf;
import gui.codeView.TextLeaf;
import gui.codeView.TextTree;
import gui.object.BlockEndLOOP;
import gui.object.CompositeBlockFD;
import gui.object.LineFD;

public class MainFrame extends JFrame implements MouseListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainFrame() {
	    
	    // Allocate the GUI components
	    // .....
	   // Testing TextTree model
	    TextBranch documentTree = new TextBranch();
	    documentTree.addTree(new TextLeaf("public class HelloWorld { \n    public static void main(String[] args){ "));
	    documentTree.addTree(new TextLeaf("\n        System.out.println(\"Hello World!\")\n"));
	    documentTree.addTree(new TextLeaf("        Let's enter something : "));
	    //documentTree.addTree(new TextAreaLeaf());
	    //documentTree.addTree(new TextFieldLeaf());
	    //documentTree.addTree(new TextFieldLeaf());
	    documentTree.addTree(new TextLeaf("Is it working?\n    }\n}") );
	    documentTree.addTree(new TextAreaLeaf());

	    documentTree.addTree(new TextFieldLeaf());
	    
	   // this.setContentPane(new CodeEditPanel(documentTree));
	   
	   
	    //this.setContentPane(new TestingPanel());
	    
	/*    JPanel np = new JPanel(null);
		JTextField tf = new JTextField();
		tf.setBounds(100,100,100,50);
		np.add(tf);
		int initialWidth = 100;
		tf.addCaretListener(e -> {
					int newWidth = tf.getText().length()*8;
					if(newWidth > initialWidth) {
						tf.setBounds(tf.getLocation().x, tf.getLocation().y, newWidth, 50 );
					} else {
						tf.setBounds(tf.getLocation().x, tf.getLocation().y, initialWidth, 50 );
					}
				});
		
		this.setContentPane(np);
		*/
	    
	    // Source object adds listener
	    // .....
	    	
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("JComponent Test");
	    setSize(500, 450);  // or pack()
	    setVisible(true);
	}
	

	public static void main(String[] args) {
		// Run GUI codes in Event-Dispatching thread for thread-safety
		
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new MainFrame();  // Let the constructor do the job
	         }
	      });
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/** ActionListener interface **/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}

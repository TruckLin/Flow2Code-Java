package gui.custom.button;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestFrame extends JFrame{
	public TestFrame() {
		// Retrieve the top-level content-pane from JFrame
	    Container cp = getContentPane();
	 
	    // Content-pane sets layout
	    cp.setLayout(new FlowLayout());
	    
	    // Create source object
	    double time = 200; // in millisecond
	    AddButton addBtn = new AddButton(200,200,time);
	    cp.add(addBtn);
	    DeleteButton deleteBtn = new DeleteButton(100,100, time);
	    cp.add(deleteBtn);

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
	            new TestFrame();  // Let the constructor do the job
	         }
	      });
	}
}

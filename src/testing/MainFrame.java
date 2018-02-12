package testing;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

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
	
	private int x_baseLine = 0;
	private int panelHeight = 100;
	
	private JScrollPane scrollPane;
	
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
	    
	    JPanel panel = new JPanel();
	    
	    panel.setPreferredSize(new Dimension(500, this.panelHeight));
	    panel.add(new JButton("Testing button"));
	    
	    this.scrollPane = new JScrollPane(panel);
	    this.scrollPane.getViewport().setBackground(Color.RED);
	    
	    LineBar lineBar = new LineBar();
	    scrollPane.setRowHeaderView(lineBar);
	    
	    // These two listener inform viewport when to repaint()
	 		scrollPane.getHorizontalScrollBar().addAdjustmentListener(e->{
	 			this.x_baseLine = e.getValue();
	 			lineBar.repaint();
	 		});
	    
	    
	    this.getContentPane().add(scrollPane);
	    
	    // Source object adds listener
	    // .....
	    	
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("JComponent Test");
	    setSize(500, 450);  // or pack()
	    setVisible(true);
	}
	
	private class LineBar extends JComponent{
		
		public LineBar() {
			this.setPreferredSize(new Dimension(100,panelHeight));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g.create();
			
			g2.setColor(Color.YELLOW);
			g2.fillRect(0, 0, LineBar.this.getWidth(), LineBar.this.getHeight());
			
			g2.setColor(Color.black);
			int _x = 25;
			int _y = 20;
			for(int i = 0; i<100; i++) {
				g2.drawString(Integer.toString(i), _x , _y);
				_y += 10;
			}
			
			g2.dispose();
		}
	}
	private class MyLayerUISubclass extends LayerUI<JComponent>{
		@Override
		public void paint(Graphics g, JComponent c) {
			super.paint(g, c);
			
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(new Color(255,0,0));
			g2.fillOval(0, 0, c.getWidth(), c.getHeight());
			
			g2.drawString("oooomg", x_baseLine, 50);
			
			g2.dispose();
		}
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

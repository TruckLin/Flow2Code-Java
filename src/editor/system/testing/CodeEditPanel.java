package editor.system.testing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Scrollable;

public class CodeEditPanel extends JPanel implements Scrollable{

	private Font codeFont;
	private final int textSize = 15;
	private TextBranch textModel;
	
	// These parameters help us keep track of where to put text field and when.
	private int lineHeight;
	private int lineLeading;
	private int lineAscent;
	private int lineDescent;
	private int numLineSoFar;
	private int x_baseLine;
	private int y_baseLine;
	
	public CodeEditPanel(TextBranch textModel){
		//¡@set layout to be null
		this.setLayout(null);
		
		// Decide a font
		this.codeFont = new Font(Font.MONOSPACED, Font.PLAIN, this.textSize );
	
		
		
		// We should be given a textModel
		this.textModel = textModel;
		
		// set up and add certain textField or text area
		
		
	}
	
	/* Find textAreas in textModel and add it to the panel
	 * */
	public void addTextComponentInTextModel(TextTree textTree) {
		if(this.textModel == null) return; // if there is no model, do nothing.
		
		/* Whenever we encounter a TextComponent in the model, we need to calculate
		*  where the top-left corner of the textComponent should be.
		*  In order to do so, we need to have
		*  1. Line height
		*  2. width of each character, (since we are coding , characters should be uniformly sized.)
		*  3. number of current line
		*  4. number of characters in the currentLine so far.
		*  5. How will the width of textArea and TextFiled affect the final result?(if we specify the width and height
		*     of the textComponent, it shouldn't be a problem)
		*/
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		// need to set monospaced font
		g2.setFont(this.codeFont);
		FontMetrics FM = g2.getFontMetrics();
		
		int x = 0;
		int y = FM.getLeading() + FM.getAscent(); // y coordinate of base line of first line.
		
		this.lineLeading = FM.getLeading();
		this.lineAscent = FM.getAscent();
		this.lineDescent = FM.getDescent();
		//this.lineHeight = FM.getLeading() + FM.getAscent() + FM.getDescent();
		this.lineHeight = FM.getHeight();
		this.numLineSoFar = 0;
		this.x_baseLine = 0;
		this.y_baseLine = this.lineLeading + this.lineAscent; // baseline of the first line
		
		//Testing
		//System.out.println("Leading = " + FM.getLeading());
		//System.out.println("Ascent = " + FM.getAscent());
		//System.out.println("Descent = " + FM.getDescent());
		//System.out.println("Height = " + this.lineHeight);
		
		// Remove all component before painting the document
		this.removeAll();
		
		/** Draw all textModel recursively **/
		this.paintTextTree(textModel, g2);
		
		
		
	}
	
	// This function should only be called by paintComponent(Graphics g).
	/*
	 * parameter:
	 * @ TextTree textTree : current model to render
	 * @ Graphics2D g : graphics object used by paintComponent
	 */
	private void paintTextTree(TextTree textTree, Graphics2D g2) {
		FontMetrics FM = g2.getFontMetrics();
		
		if(textTree instanceof TextLeaf) {
			String txt = textTree.getText();	
			char[] charList = txt.toCharArray();
			for(Character letter : charList) {
				if(letter.equals('\n')) {
					this.x_baseLine = 0;
					this.y_baseLine = this.y_baseLine + this.lineHeight;
				} else {
					g2.drawString(letter.toString(), this.x_baseLine, this.y_baseLine);
					int width = FM.stringWidth(letter.toString());
					this.x_baseLine = this.x_baseLine + width;
				}
			}
			
		}else if(textTree instanceof TextAreaLeaf) {
			
			JTextArea ta = ((TextAreaLeaf)textTree).getTextArea();
			ta.setFont(codeFont); //setTextField to be the same Font.
			
			// set width to be 8 white spaces' width, height to be line height.
			int width = FM.stringWidth("        ");
			ta.setBounds(x_baseLine, y_baseLine - this.lineAscent, width, this.lineHeight);
			
			this.x_baseLine += width;
			
			this.add(ta);
			
		}else if(textTree instanceof TextFieldLeaf) {
			JTextField tf = ((TextFieldLeaf)textTree).getTextField();
			int initialWidth = FM.stringWidth("        ");
			if( ! ((TextFieldLeaf)textTree).getInitialised() ) {
				/** This section initialise text field **/
				((TextFieldLeaf)textTree).setInitialised(true);
				tf.setFont(codeFont); //setTextField to be the same Font.
				
				// set width to be 8 white spaces' width, height to be line height.
				tf.setBounds(x_baseLine, y_baseLine - this.lineAscent, initialWidth, this.lineHeight);
				
				this.add(tf);
				// Add caret listener
			//	tf.removeCaretListener(tf.getCaretListeners());
				
				//Testing
				System.out.println("Number of caretListeners : " + tf.getCaretListeners().length);
				
				tf.addCaretListener(e -> {
					//Testing
					System.out.println("listener triggered.");
					
					int newWidth = FM.stringWidth(tf.getText());
					if(newWidth > initialWidth) {
						tf.setBounds(x_baseLine, y_baseLine - this.lineAscent, newWidth, this.lineHeight );
					} else {
						tf.setBounds(x_baseLine, y_baseLine - this.lineAscent, initialWidth, this.lineHeight );
					}
					this.repaint();
				});
				
				//Update x_baseline location
				this.x_baseLine = this.x_baseLine + initialWidth;
				
			} else {
				/** if the TextFieldTree is initialised **/
				// Testing
				System.out.println("textField initialised detected");
				
				this.x_baseLine = this.x_baseLine + ((TextFieldLeaf)textTree).getCurrentWidth();
			}
			
			
		}else if(textTree instanceof TextBranch) {
			for(TextTree currentTree : ((TextBranch)textTree).getLeaves() ) {
				this.paintTextTree(currentTree, g2);
			}
		} else {
			System.err.println("Unknown subclass of TextTree.");
		}
	}
	
	/** Scrollable interface **/
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

}

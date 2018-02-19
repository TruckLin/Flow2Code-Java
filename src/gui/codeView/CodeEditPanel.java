package gui.codeView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.event.CaretListener;

public class CodeEditPanel extends JPanel implements Scrollable{
	
	private int textSize = 15;
	
	// Decide a font, textSize must be set first!!!
	private Font codeFont = new Font(Font.MONOSPACED, Font.PLAIN, this.textSize);
	
	//Some colors that we need
	private final Color backGroundColor = new Color(195,195,195);
	
	private TextBranch textModel = null;
	
	// These parameters help us keep track of where to put text field and when.
	private int lineHeight;
	private int lineLeading;
	private int lineAscent;
	private int lineDescent;

	private int x_baseLine;
	private int y_baseLine;
	
	private int totalNumberOfLine = 0;	

	private int maximum_x;
	private int maximum_y;
	
	// Maybe we can store Font metric here
	private FontMetrics FM;
	
	
	// Listeners that should help to repaint
	private CaretListener textFieldListener = e->{
		// Assume source is always a textField
		if(e.getSource() instanceof JTextField) {
			JTextField tf = (JTextField)e.getSource();
			String txt = tf.getText();
			int newWidth = this.FM.stringWidth(txt);
			int initialWidth = this.FM.stringWidth("        ");
			if(newWidth > (initialWidth-5)) {
				tf.setSize(newWidth+5, this.lineHeight);
			}else {
				tf.setSize(initialWidth, this.lineHeight);
			}
			
			this.addTextComponentInTextModel();
			this.repaint();
			
			//Testing
			//System.out.println("Source is textField.");
			//System.out.println("Bounds = " + tf.getBounds().toString());
		}
	};
	// This listener is responsible for resizing the text area
	private CaretListener textAreaListener = e->{
		if(e.getSource() instanceof JTextArea) {
			JTextArea Ta = (JTextArea)e.getSource();
			FontMetrics FM = Ta.getFontMetrics(Ta.getFont());
			String txt = Ta.getText();
			
			char[] charList = txt.toCharArray();
			int minWidth = FM.stringWidth("        ");
			int maxWidth = 0;
			int maxHeight = FM.getHeight();
			int lineWidth = 0;
			for(Character letter : charList) {
				if(letter.equals('\n')) {
					maxHeight += FM.getHeight();
					if(maxWidth < lineWidth) {
						maxWidth = lineWidth;
					}
					lineWidth = 0;
				} else {
					int charWidth = FM.stringWidth(letter.toString());
					lineWidth += charWidth;
				}
			}
			// Do the last check when we reach the end of charList.
			if(maxWidth < lineWidth) {
				maxWidth = lineWidth;
			}
			lineWidth = 0;
			
			if(maxWidth < minWidth) {
				Ta.setSize(minWidth, maxHeight);
			}else {
				Ta.setSize(maxWidth+5, maxHeight);
			}
			
			this.addTextComponentInTextModel();
			this.repaint();
			//Testing
			//System.out.println("Source is textArea.");
			//System.out.println("Bounds = " + tf.getBounds().toString());
			//System.out.println("maxWidth = " + maxWidth);
			//System.out.println("maxHeight = " + maxHeight);
		}
	};
	
	public CodeEditPanel() {
		//¡@set layout to be null
		this.setLayout(null);
		
		// New a JTextField just to get the FontMetric
		this.FM = new JTextField().getFontMetrics(codeFont);
		this.lineLeading = FM.getLeading();
		this.lineAscent = FM.getAscent();
		this.lineDescent = FM.getDescent();
		this.lineHeight = FM.getHeight();
		this.totalNumberOfLine = 1;
		
		//Testing
		//System.out.println(this.lineNumberBarWidth);
	}

	public void setTextModel(TextBranch textModel) {
		this.totalNumberOfLine = 1;
		
		this.removeAll();
		
		this.textModel = textModel;// replace the model
		
		if(this.textModel!= null) {
			
			this.addTextComponentInTextModel();
			this.repaint();
			
			//Testing
			//System.out.println("this.textModel!=null");
		}
	}
	
	// Adjust size
	public void adjustSize() {		
		this.setPreferredSize(new Dimension(this.maximum_x,this.maximum_y));
		this.revalidate();
		repaint();
	}
	
	/* Find textAreas in textModel and add it to the panel
	 * */
	public void addTextComponentInTextModel() {
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
		this.x_baseLine = 0;
		this.y_baseLine = this.lineLeading + this.lineAscent; // baseline of the first line
		
		this.maximum_x = Integer.MIN_VALUE;
		this.maximum_y = Integer.MIN_VALUE;

		//Testing
		//System.out.println("addTextComponentInTextModel() is called.");
		//System.out.println("Before:");
		//System.out.println("Max x = " + this.maximum_x);
		//System.out.println("Max y = " + this.maximum_y);
		
		// Reset TotalNumberOfLine, we will count it again
		int oldNumLine = this.totalNumberOfLine;
		this.totalNumberOfLine = 1;
		
		this.addTextComponentInTextModel(textModel);
		
		//Testing
		//System.out.println("After:");
		//System.out.println("Max x = " + this.maximum_x);
		//System.out.println("Max y = " + this.maximum_y);
		
		// Set preffered size the reset maximum x and y
		this.adjustSize();
		
		// Fire propertyChangeEvent to inform other component that number of lines has changed.
		this.firePropertyChange("totalNumberOfLine", oldNumLine, this.totalNumberOfLine);
	}
	
	// Do not invoke this method directly.
	public void addTextComponentInTextModel(TextTree textTree) {
		if(this.textModel == null) return; // if there is no model, do nothing.
		
		
		if(textTree instanceof TextLeaf) {
			String txt = textTree.getText();
			char[] charList = txt.toCharArray();
			for(Character letter : charList) {
				if(letter.equals('\n')) {
					this.x_baseLine = 0;
					
					this.y_baseLine = this.y_baseLine + this.lineHeight;
					
					//Update maximum_y
					if(this.maximum_y < (y_baseLine+this.lineDescent)) this.maximum_y = y_baseLine+this.lineDescent;
					
					// Add 1 to totalNumberOfLine
					this.totalNumberOfLine ++;
					
				} else {
					int width = FM.stringWidth(letter.toString());
					this.x_baseLine = this.x_baseLine + width;
					
					//Update maximum_x
					if(this.maximum_x < x_baseLine) this.maximum_x = x_baseLine;
					
				}
			}
			
		}else if(textTree instanceof TextAreaLeaf) {
			
			JTextArea ta = ((TextAreaLeaf)textTree).getTextArea();
			int initialWidth = FM.stringWidth("        ");
			if(! ((TextAreaLeaf)textTree).getInitialised()) {
				((TextAreaLeaf)textTree).setInitialised(true);
				ta.setFont(codeFont); //setTextField to be the same Font.
				
				// set width to be 8 white spaces' width, height to be line height.
				ta.setBounds(x_baseLine, y_baseLine - this.lineAscent, initialWidth, this.lineHeight);
				this.add(ta);
				
				// Add caret listener
				ta.addCaretListener(this.textAreaListener);
				
				//Update maximum_x
				if(this.maximum_x < (x_baseLine + initialWidth)) this.maximum_x = x_baseLine + initialWidth;
				//Update maximum_y
				if(this.maximum_y < (y_baseLine + ta.getHeight() + this.lineDescent)) 
									this.maximum_y = y_baseLine + ta.getHeight() + this.lineDescent;
				
				//Update y_baseLine location and x_baseLine location
				this.x_baseLine += ta.getWidth();
				this.y_baseLine += (ta.getHeight() - this.lineHeight);
				
				// Update totalNumebrOfLine
				this.totalNumberOfLine += (ta.getHeight()/this.lineHeight - 1);
				
			}else {
				/** if the TextAreaLeaf is initialised **/
				ta.setLocation(x_baseLine, y_baseLine-this.lineAscent);
				
				//Update maximum_x
				if(this.maximum_x < (x_baseLine + ta.getWidth())) this.maximum_x = x_baseLine + ta.getWidth();
				//Update maximum_y
				if(this.maximum_y < (y_baseLine + ta.getHeight() + this.lineDescent)) 
									this.maximum_y = y_baseLine + ta.getHeight() + this.lineDescent;
				
				
				//Update y_baseLine location and x_baseLine location
				this.x_baseLine += ta.getWidth();
				this.y_baseLine += (ta.getHeight() - this.lineHeight);
				
				// Update totalNumebrOfLine
				this.totalNumberOfLine += (ta.getHeight()/this.lineHeight - 1);

			}
			
		}else if(textTree instanceof TextFieldLeaf) {
			JTextField tf = ((TextFieldLeaf)textTree).getTextField();
			int initialWidth = FM.stringWidth("        ");
			if( ! ((TextFieldLeaf)textTree).getInitialised() ) {
				/** This section initialise text field **/
				((TextFieldLeaf)textTree).setInitialised(true);
				tf.setFont(codeFont); //setTextField to be the same Font.
				
				// set width to be 8 white spaces' width, height to be line height.
				tf.setLocation(x_baseLine, y_baseLine - this.lineAscent);
				tf.setSize(initialWidth, this.lineHeight);
				
				this.add(tf);
				
				// Add caret listener
				tf.addCaretListener(this.textFieldListener);
				
				//Update x_baseline location
				this.x_baseLine = this.x_baseLine + initialWidth;
				
				//Update maximum_x
				if(this.maximum_x < x_baseLine) this.maximum_x = x_baseLine;
				
			} else {
				/** if the TextFieldTree is initialised **/
				// Testing
			//	System.out.println("textField initialised detected");
			//	tf.setLocation(new Point(x_baseLine, y_baseLine - this.lineAscent));
			//	System.out.println("Current x position = " + this.x_baseLine);
			//	System.out.println("Current y position = " + this.y_baseLine);
			//	System.out.println("Current tf width = " + ((TextFieldLeaf)textTree).getCurrentWidth());
				
				tf.setLocation(x_baseLine, y_baseLine-this.lineAscent);
				this.x_baseLine = this.x_baseLine + ((TextFieldLeaf)textTree).getCurrentWidth();
				
				//Update maximum_x
				if(this.maximum_x < x_baseLine) this.maximum_x = x_baseLine;
				
				//Testing
			//	System.out.println("x position after addition of width = " + this.x_baseLine);
			}
			
			
		}else if(textTree instanceof TextBranch) {
			for(TextTree currentTree : ((TextBranch)textTree).getLeaves() ) {
				this.addTextComponentInTextModel(currentTree);
			}
		} else {
			System.err.println("Unknown subclass of TextTree.");
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		Color fg = g2.getColor();
		g2.setColor(this.backGroundColor);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setColor(fg);
		
		// need to set monospaced font
		g2.setFont(this.codeFont);
		
		
		this.lineLeading = FM.getLeading();
		this.lineAscent = FM.getAscent();
		this.lineDescent = FM.getDescent();
		//this.lineHeight = FM.getLeading() + FM.getAscent() + FM.getDescent();
		this.lineHeight = FM.getHeight();
		this.x_baseLine = 0;
		this.y_baseLine = this.lineLeading + this.lineAscent; // baseline of the first line

		//Testing
		//System.out.println("Leading = " + FM.getLeading());
		//System.out.println("Ascent = " + FM.getAscent());
		//System.out.println("Descent = " + FM.getDescent());
		//System.out.println("Height = " + this.lineHeight);
		
		/** Draw all textModel recursively **/
		this.paintTextTree(textModel, g2);
		
		
		g2.dispose();
	}
	
	// This function should only be called by paintComponent(Graphics g).
	/*
	 * parameter:
	 * @ TextTree textTree : current model to render
	 * @ Graphics2D g : graphics object used by paintComponent
	 */
	private void paintTextTree(TextTree textTree, Graphics2D g2) {
		
		//Testing
		//System.out.println("FM is null = " + FM==null);
		//System.out.println(FM.stringWidth("hohoho"));
		//System.out.println("charWidth : " + FM.charWidth('c'));
		
		if(textTree instanceof TextLeaf) {
			String txt = textTree.getText();	
			char[] charList = txt.toCharArray();
			for(Character letter : charList) {
				if(letter.equals('\n')) {
					this.x_baseLine = 0;
					this.y_baseLine = this.y_baseLine + this.lineHeight;
					
					//Testing
					//System.out.println("x_baseline = " + x_baseLine);
					//System.out.println("y_baseline = " + y_baseLine);
					
				} else {
					g2.drawString(letter.toString(), this.x_baseLine, this.y_baseLine);
					int width = FM.stringWidth(letter.toString());
					this.x_baseLine = this.x_baseLine + width;
					
				}
			}
			
		}else if(textTree instanceof TextAreaLeaf) {
			// only move y_baseLine.
			JTextArea ta = ((TextAreaLeaf)textTree).getTextArea();
		//	this.x_baseLine += ta.getWidth();
			
			//Testing
			//System.out.println("y_baseLine before = " + this.y_baseLine);
			
			int taHeight = ta.getHeight();
			this.y_baseLine += (taHeight - this.getLineHeight() );

			//Update x_baseLine location
			this.x_baseLine = ta.getWidth();
			
			//Testing
			//System.out.println("y_baseLine after = " + this.y_baseLine);
			
		}else if(textTree instanceof TextFieldLeaf) {
			// only move x_baseLine.
			JTextField tf = ((TextFieldLeaf)textTree).getTextField();
			
			//Testing
			//System.out.println("y_baseLine when dealing textField = " + this.y_baseLine);
			
			this.x_baseLine = this.x_baseLine + tf.getWidth();
		}else if(textTree instanceof TextBranch) {
			for(TextTree currentTree : ((TextBranch)textTree).getLeaves() ) {
				this.paintTextTree(currentTree, g2);
			}
		} else {
			System.err.println("Unknown subclass of TextTree.");
		}
	}
	
	/** Getters and Setters **/
	public String getText() {
		return this.textModel.getText();
	}
	
	public Color getBackgroundColor() {
		return this.backGroundColor;
	}
	
	public int getLineHeight() {
		return this.lineHeight;
	}
	public int getLineAscent() {
		return this.lineAscent;
	}
	public int getLineLeading() {
		return this.lineLeading;
	}
	public int getLineDescent() {
		return this.lineDescent;
	}
	public int getTotalNumberOfLine() {
		return this.totalNumberOfLine;
	}
	public FontMetrics getMyFontMetrics() {
		return this.FM;
	}
	public Font getCodeFont() {
		return this.codeFont;
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
		return 50;
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
		return 10;
	}

}


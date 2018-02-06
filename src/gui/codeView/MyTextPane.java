package gui.codeView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class MyTextPane extends JTextPane{
	
	// Colors
	private final Color mySelectionColor = new Color(51,153,255);
	private final Color mySelectionTextColor = Color.white;
	private final Color myCurrentLineColor = new Color(107,224,154,15);
	
	public MyTextPane() {
		super();
		
		this.setEditorKit(new MyVersionOfNumberedEditorKit());
		this.setDocument(new myDefaultDocument());
		this.setMargin(new Insets( 0, MyVersionOfNumberedParagraphView.NUMBERS_WIDTH, 0, 0));
		
		this.setSelectionColor(this.mySelectionColor);
	    this.setSelectedTextColor(this.mySelectionTextColor);
	    //
	}
	
	/** Getters and Setters **/
	
	/** Utility functions **/

	
	/** Override getScrollableTracksViewportWidth 
	 *  to preserve the full width of the text.
	 */
	/*
    public boolean getScrollableTracksViewportWidth() {
        Component parent = getParent();
	    ComponentUI ui = getUI();

	    return parent != null ? (ui.getPreferredSize(this).width <= parent
	        .getSize().width) : true;
	  }
	*/
	/** private class **/
	
	private class myDefaultDocument extends DefaultStyledDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            str = str.replaceAll("\t", "    ");
            super.insertString(offs, str, a);
        }
    }
}

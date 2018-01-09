package gui.codeView;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class CodeViewTextPane extends JTextPane{
	
	// Colors
	private final Color mySelectionColor = new Color(51,153,255);
	private final Color mySelectionTextColor = Color.white;
	
	public CodeViewTextPane() {
		super();
		
		// Setting up
		this.setCaretPosition(0);
		this.setEditorKit(new MyVersionOfNumberedEditorKit());
		this.setDocument(new myDefaultDocument());
		this.setMargin(new Insets( 0, MyVersionOfNumberedParagraphView.NUMBERS_WIDTH, 0, 0));
		
		this.setSelectionColor(this.mySelectionColor);
	    this.setSelectedTextColor(this.mySelectionTextColor);
	    //
	}
	
	/** private class **/
	private class myDefaultDocument extends DefaultStyledDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            str = str.replaceAll("\t", "    ");  // 1 tab is equivalent to 4 white space.
            super.insertString(offs, str, a);
        }
    }

}

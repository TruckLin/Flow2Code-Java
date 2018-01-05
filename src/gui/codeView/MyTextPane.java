package gui.codeView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;

public class MyTextPane extends JTextPane{
	
	private Rectangle caretCoord;
	private int lineHeight;
	private int lineBarWidth;
	
	// Colors
	private final Color mySelectionColor = new Color(51,153,255);
	private final Color mySelectionTextColor = Color.white;
	private final Color myCurrentLineColor = new Color(107,224,154,15);
	
	public MyTextPane() {
		super();
		
		// settings
		this.setCaretPosition(0);
		try {
			this.caretCoord = this.modelToView(this.getCaret().getDot());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setMargin(new Insets(0,0,0,0));
	    
		this.setSelectionColor(this.mySelectionColor);
	    this.setSelectedTextColor(this.mySelectionTextColor);
	    //
	}
	
	/** Getters and Setters **/
	public void setCaretCoord(Rectangle caretCoord) {
		this.caretCoord = caretCoord;
	}
	public Rectangle getCaretCoord() {
		return this.caretCoord;
	}
	
	/** Utility functions **/
	public int getNumebrOfLines() {
		int num = 1;
		if(caretCoord != null) {
			this.lineHeight = caretCoord.height;
		}
		num = (int) (this.getPreferredSize().getHeight()/this.lineHeight);
		return num;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// paint current line indicator.
		if(caretCoord != null) {
			g2.setColor(this.myCurrentLineColor);
			g2.fillRect(0, caretCoord.y, this.getWidth(), caretCoord.height);
		}
		
		// paint line number bar
		//Testing
		System.out.println(this.getNumebrOfLines());
		
		/*
		int numline = this.getNumebrOfLines();
		int maxWidth = 0;
		int h = 0;
		for(int i = 1; i <= numline; i++) {
			String currentline = Integer.toString(i);
			int currentWidth = g2.getFontMetrics().stringWidth(currentline);
			
			if(currentWidth > maxWidth) maxWidth = currentWidth;
			g2.drawString(currentline, 0, h);
			//h = (int) (h + caretCoord.height);
			
		}
		g2.setColor(this.myCurrentLineColor);
		g2.fillRect(0, 0, this.getWidth(), caretCoord.height);
		*/
	}
	
	
	
/*	public static void main(String[] args)  {

	    JTextPane t = new MyTextPane();
	    
	    t.setSelectionColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));
	    Highlighter hilite = new MyHighlighter();
	    t.setHighlighter(hilite);
	    t.setText("Line1\nLine2\nLine3\nLine4\nLine5\n");
	    


	    DefaultHighlightPainter whitePainter = new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
	    DefaultHighlightPainter grayPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);

	    try {
	        Document doc = t.getDocument();
	        String text = doc.getText(0, doc.getLength());
	        int start = 0;
	        int end = 0 ;

	        boolean even = true;

	        //look for newline char, and then toggle between white and gray painters.
	        while ((end = text.indexOf('\n', start)) >= 0) {
	            even = !even;
	            DefaultHighlightPainter painter = even ? grayPainter : whitePainter;
	            hilite.addHighlight(start, end+1, painter);
	            start = end+1;
	        }
	    } catch (BadLocationException e) {
	        e.printStackTrace();
	    }

	    JPanel p = new JPanel(new BorderLayout());      
	    p.add(t, BorderLayout.CENTER);
	    JFrame f = new JFrame();
	    f.add(p);
	    f.setSize(100, 100);
	    f.setVisible(true);
	    
	    //Testing
	    System.out.println(t.getText());
	}*/
}

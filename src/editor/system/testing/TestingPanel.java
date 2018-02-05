package editor.system.testing;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

public class TestingPanel extends JPanel{
	
	private JTextField tf;
	private JTextArea ta;
	private Font codeFont;
	private FontMetrics FM;
	
	private CaretListener textFieldListener = e->{
		// Assume source is always a textField
		if(e.getSource() instanceof JTextField) {
			JTextField tf = (JTextField)e.getSource();
			FontMetrics FM = tf.getFontMetrics(tf.getFont());
			String txt = tf.getText();
			int newWidth = FM.stringWidth(txt);
			int initialWidth = FM.stringWidth("        ");
			if(newWidth > (initialWidth-5)) {
				tf.setSize(newWidth + 5, FM.getHeight());
			}else {
				tf.setSize(initialWidth, FM.getHeight());
			}
			
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
			int minWidth = 100;
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
			//Testing
			//System.out.println("Source is textArea.");
			//System.out.println("Bounds = " + tf.getBounds().toString());
			//System.out.println("maxWidth = " + maxWidth);
			//System.out.println("maxHeight = " + maxHeight);
		}
	};
	
	public TestingPanel() {
		this.setLayout(null);
		
		this.tf = new JTextField();
		this.codeFont = new Font(Font.MONOSPACED, Font.PLAIN, 24 );
		this.FM = tf.getFontMetrics(this.codeFont);
		tf.setLocation(100,50);
		tf.setSize(15, FM.getHeight());
		tf.setFont(codeFont);
		this.tf.addCaretListener(textFieldListener);
		this.add(tf);
		
		this.ta = new JTextArea();
		ta.setLocation(100, 150);
		ta.setSize(15,FM.getHeight());
		ta.setFont(codeFont);
		this.ta.addCaretListener(textAreaListener);
		this.add(ta);
		
		
	}
}

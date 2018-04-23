package gui.codeView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

public class LineNumberBar extends JComponent{

	private CodeEditPanel cep;
	private int lineBarWidth;
	
	private Color backGroundColor = new Color(168,216,185);
	private Color numberColor = Color.BLACK;
	
	
	private PropertyChangeListener lineNumberListener = e->{
		if( e.getPropertyName().equals("totalNumberOfLine") ){
			
			//Testing
			//System.out.println("totalNumberOfLine changed");
			//System.out.println("newValue = " + e.getNewValue());
			
			// Update the size of this Line bar
			this.lineBarWidth = cep.getMyFontMetrics().stringWidth(Integer.toString(cep.getTotalNumberOfLine()));
			this.setPreferredSize(new Dimension(this.lineBarWidth, cep.getHeight()));
			
			this.revalidate();
			this.repaint();
		}
	};
	
	public LineNumberBar(CodeEditPanel cep) {
		this.cep = cep;
		this.lineBarWidth = cep.getMyFontMetrics().stringWidth(Integer.toString(cep.getTotalNumberOfLine()))+8;
		cep.addPropertyChangeListener(this.lineNumberListener);
		this.setPreferredSize(new Dimension(this.lineBarWidth, cep.getHeight()));
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
		// Paint background
		g2.setColor(this.backGroundColor);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Testing
//		System.out.println("lineBarWidth = " + this.lineBarWidth);
//		System.out.println("width = " + this.getWidth());
//		System.out.println("height = " + this.getHeight());
//		int tempWidth = cep.getMyFontMetrics().stringWidth(Integer.toString(50));
//		System.out.println("tempWdth = " + tempWidth);
		
		
		int lineAscent = cep.getLineAscent();
		int lineLeading = cep.getLineLeading();
		int y_baseLine = lineAscent + lineLeading;
		
		
		//Testing
		//System.out.println(cep.getTotalNumberOfLine());
		g2.setFont(cep.getCodeFont());
		g2.setColor(this.numberColor);
		for(int i = 1; i <= cep.getTotalNumberOfLine(); i++) {
			String number = Integer.toString(i);
			int numberWidth = cep.getMyFontMetrics().stringWidth(number);
			g2.drawString(number , this.lineBarWidth/2 - numberWidth/2, y_baseLine);
			y_baseLine += cep.getLineHeight();
		}
		
		g2.dispose();
	}
	
}

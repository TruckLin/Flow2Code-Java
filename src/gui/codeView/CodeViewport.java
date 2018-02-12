package gui.codeView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.JViewport;

public class CodeViewport extends JViewport{

	private CodeEditPanel cep;
	
	private PropertyChangeListener lineNumberListener = e->{
		
	};
	
	public CodeViewport(CodeEditPanel cep) {
		super();
		//this.setView(cep);
		this.cep = cep;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		Point viewPosition = this.getViewPosition();
		Dimension viewSize = cep.getPreferredSize();
		int viewWidth = (int) viewSize.getWidth();
		int viewHeight = (int) viewSize.getHeight();
		int viewPortHeight = this.getHeight();
		int viewPortWidth = this.getWidth();
		
		//Testing
		//System.out.println("ViewPosition = " + viewPosition);
		//System.out.println("ViewRectangle = " + this.getViewRect());
		//System.out.println("ViewSize = " + viewSize);
		//System.out.println("ViewPortSize = " + "[" + viewPortWidth +", "+ viewPortHeight + "]");
		
		int lineBarWidth = cep.getLineNumberBarWidth();
		Color bg = cep.getBackgroundColor();
		Color lineBarColor = cep.getLineNumberBarColor();
		
		if(viewWidth < viewPortWidth && viewHeight < viewPortHeight) {
			// paint background
			g2.setColor(bg);
				//Testing
				g2.setColor(Color.RED);
			g2.fillRect(lineBarWidth, viewHeight, viewPortWidth - lineBarWidth, viewPortHeight - viewHeight);// lower rect
			g2.fillRect(viewWidth, 0, viewPortWidth - viewWidth, viewPortHeight); // right rect
			
			// paint line bar
			g2.setColor(lineBarColor);
				//Testing
				g2.setColor(Color.GREEN);
			g2.fillRect(0, viewHeight, lineBarWidth, viewPortHeight - viewHeight);
			
			
			//Testing
			//System.out.println("1");
		}else if(viewWidth < viewPortWidth && viewHeight >= viewPortHeight) {
			
			// paint background
			g2.setColor(bg);
				//Testing
				g2.setColor(Color.RED);
			g2.fillRect(viewWidth, 0, viewPortWidth - viewWidth, viewPortHeight); // right rect
			
			//Testing
			//System.out.println("2");
			
		}else if(viewWidth >= viewPortWidth && viewHeight < viewPortHeight) {
			int differenceInCoord = viewPosition.x - lineBarWidth + 1;
			if(differenceInCoord <= 0) {// line number bar is visible
				
				// paint background
				g2.setColor(bg);
					//Testing
					g2.setColor(Color.RED);
				g2.fillRect(-differenceInCoord + 1 , viewHeight,
						viewPortWidth + differenceInCoord -1, viewPortHeight - viewHeight);// lower rect
				
				
				// paint line bar
				g2.setColor(lineBarColor);
					//Testing
					g2.setColor(Color.GREEN);
				g2.fillRect(0, viewHeight, -differenceInCoord + 1, viewPortHeight - viewHeight);
				
				
			}else {// differenceInCoord > 0 means line number bar is not visible
				// paint background
				g2.setColor(bg);
					//Testing
					g2.setColor(Color.RED);
				g2.fillRect(0, viewHeight, viewPortWidth, viewPortHeight - viewHeight);// lower rect
			}
			
			//Testing
			//System.out.println("3");
		}else {// do nothing if both >=
			
			//Testing
			//System.out.println("4");
			
		}
		
	}


}

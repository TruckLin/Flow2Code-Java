package gui.custom.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class AddButton extends CustomButton{
	private int minCircleRadius, maxCircleRadius;
	private Point centre;
	
	private double time; // in millisecond
	private double percentage; // from 0 to 1.
	private double step;
	// Time is used to derive step. If we want expansion/shrinkage to complete in 0.5 second which is 500 millisecond,
	// then we need the step to be 1/time*50. 50 us how long the expandThread/shrinkThread would sleep after each repaint().
	
	private Rectangle2D plusRectVer, plusRectHor;
	
	
	private Thread expandThread;
	private Thread shrinkThread;
	private boolean stopExpansionAndShrinkage;
	private boolean mouseInside;
	
	public AddButton(int width, int height, double time) {
		this.setPreferredSize(new Dimension(width, height));
		this.maxCircleRadius = (int)Math.floor(Math.min(width, height)/2.0);
		this.minCircleRadius = (int)Math.floor(this.maxCircleRadius*7.0/8.0);
		this.centre = new Point((int)Math.floor(width/2), (int)Math.floor(height/2.0));
		this.percentage = 0;
		
		this.time = time;
		this.step = 50/this.time;
		
		double portionOfWidth = 5.5/8.0;
		double portionOfHeight = 1.0/8.0;
		int plusWidth = (int)Math.round(this.minCircleRadius * 2 * portionOfWidth);
		int plusHeight = (int)Math.round(this.minCircleRadius * 2 * portionOfHeight );
		
		int xHor = (int)Math.round( this.centre.getX() - plusWidth/2 );
		int yHor = (int)Math.round( this.centre.getY() - plusHeight/2 );
		int xVer = (int)Math.round( this.centre.getX() - plusHeight/2 );
		int yVer = (int)Math.round( this.centre.getY() - plusWidth/2 );
		
		this.plusRectHor = new Rectangle(xHor, yHor, plusWidth,plusHeight);
		this.plusRectVer = new Rectangle(xVer, yVer, plusHeight, plusWidth);
		
		expandThread = new Thread(new Expand());
		shrinkThread = new Thread(new Shrink());
		this.stopExpansionAndShrinkage = false;
		this.mouseInside = false;
		
		this.addMouseListener(new animationListener());
		
		//this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		double currentRadius = (maxCircleRadius - minCircleRadius)*percentage + minCircleRadius;
		
		int x,y,width,height;
		x = (int)(centre.getX() - currentRadius);
		y = (int)(centre.getY() - currentRadius);
		width = (int)Math.round(currentRadius*2);
		height = (int)Math.round(currentRadius*2);
		g2.setColor(Color.green);
		g2.fillOval(x, y, width, height);
		
		g2.setColor(Color.WHITE);
		g2.fill(plusRectHor);
		g2.fill(plusRectVer);
		
		//Testing
		//System.out.println("Horizontal : \n    " + plusRectHor.toString());
		//System.out.println("Vertital : \n    " + plusRectVer.toString());
	}
	
	public class animationListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(expandThread != null && expandThread.isAlive()) {
				expandThread.interrupt();
			}
			stopExpansionAndShrinkage = true;
			percentage = 0;
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(myAction != null) {
				myAction.actionPerformed(null);
			}
			percentage = 1;
			repaint();

			if(!mouseInside) {
				percentage = 0;
				repaint();
			}
			
			stopExpansionAndShrinkage = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(shrinkThread != null && shrinkThread.isAlive()) {
				shrinkThread.interrupt();
			}
			if( !stopExpansionAndShrinkage) {
				expandThread = new Thread(new Expand());
				expandThread.start();
			}
			mouseInside = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(expandThread != null && expandThread.isAlive()) {
				expandThread.interrupt();
			}
			if( !stopExpansionAndShrinkage ) {
				shrinkThread = new Thread(new Shrink());
				shrinkThread.start();
			}
			
			mouseInside = false;
		}
		
	}
	
	private class Expand implements Runnable {
		public void run() {
	        try {
	        	while( percentage < 1) {
	        		percentage += step;
	        		if(percentage > 1) percentage = 1;
	        		repaint();
	        		// Pause for 0.1 seconds
	        		Thread.sleep(50);
	                
	            }
	        } catch (InterruptedException e) {
	          //  System.out.println(Thread.currentThread().getName() + "  interrupted.");
	        }
		}
	}
	
	private class Shrink implements Runnable {
		public void run() {
	        try {
	        	while( percentage > 0) {
	        		percentage -= step;
	        		if(percentage < 0) 	percentage = 0;
	        		repaint();
	        		// Pause for 0.05 seconds
	        		Thread.sleep(50);
	                
	            }
	        } catch (InterruptedException e) {
	         //   System.out.println(Thread.currentThread().getName() + "  interrupted.");
	        }
		}
	}
	
}

package gui.custom.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import javax.imageio.ImageIO;


public class DeleteButton extends CustomButton{
	
	private int minCircleRadius, maxCircleRadius;
	private Point centre;
	
	private BufferedImage lid,container;
	private double minAngle, maxAngle;
	private Point pivot;
	int LidDx1, LidDy1, LidDx2, LidDy2,
			ContainerDx1,ContainerDy1,ContainerDx2,ContainerDy2;
	private double binPercentage = 0; // from 0 to 1.
	
	private double time; // in millisecond
	private double percentage = 0; // from 0 to 1.
	private double step;
	// Time is used to derive step. If we want expansion/shrinkage to complete in 0.5 second which is 500 millisecond,
	// then we need the step to be (1/time)*50. 50 is how long the expandThread/shrinkThread would sleep after each repaint().
	
	private Thread expandThread;
	private Thread shrinkThread;
	private boolean stopExpansionAndShrinkage;
	private boolean mouseInside = false;
	
	public DeleteButton(int width, int height, double time) {
		this.setPreferredSize(new Dimension(width, height));
		this.maxCircleRadius = (int)Math.floor(Math.min(width, height)/2.0);
		this.minCircleRadius = (int)Math.floor(this.maxCircleRadius*7.0/8.0);
		this.centre = new Point((int)Math.floor(width/2), (int)Math.floor(height/2.0));
		
		this.time = time;
		this.step = 50/this.time;
		
		try(FileInputStream lidIn = new FileInputStream(new File("icon/rubbish-bin-lid.png")) ){
			this.lid = ImageIO.read(lidIn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try(FileInputStream containerIn = new FileInputStream(new File("icon/rubbish-bin-container.png"))){
			this.container = ImageIO.read(containerIn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.minAngle = 0;
		this.maxAngle = Math.PI/6;
		double binWidthRatio = 0.5; // With respect to diameter.
		double lidHeightRatio = binWidthRatio*14/54;
		double binHeightRatio = lidHeightRatio + binWidthRatio;
		Point2D offSetRatio = new Point2D.Double(0, 0.2);
		
		LidDx1 = (int)Math.round(this.centre.getX() + offSetRatio.getX()*this.minCircleRadius
															- binWidthRatio*this.minCircleRadius);
		LidDy1 = (int)Math.round(this.centre.getY() + offSetRatio.getY()*this.minCircleRadius
															- binHeightRatio*this.minCircleRadius);
		LidDx2 = (int)Math.round(this.centre.getX() + offSetRatio.getX()*this.minCircleRadius
															+ binWidthRatio*this.minCircleRadius);
		LidDy2 = (int)Math.round(LidDy1 + lidHeightRatio*this.minCircleRadius);
		
		ContainerDx1 = (int)Math.round(this.centre.getX() + offSetRatio.getX()*this.minCircleRadius 
																- binWidthRatio*this.minCircleRadius);
		ContainerDy1 = LidDy2 + 1;
		ContainerDx2 = (int)Math.round(this.centre.getX() + offSetRatio.getX()*this.minCircleRadius 
																+ binWidthRatio*this.minCircleRadius);
		ContainerDy2 = (int)Math.round(this.centre.getY() + offSetRatio.getY()*this.minCircleRadius 
																+ binHeightRatio*this.minCircleRadius);
		
		this.pivot = new Point(LidDx2,LidDy2);
		
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
		g2.setColor(Color.red);
		g2.fillOval(x, y, width, height);
		
		double currentTheta = (maxAngle - minAngle)*binPercentage + minAngle;	
		g2.drawImage(container, ContainerDx1, ContainerDy1, ContainerDx2, ContainerDy2, 
								0, 0, container.getWidth()-1, container.getHeight()-1, Color.RED, null);
		g2.rotate(currentTheta,pivot.getX(), pivot.getY());
		g2.drawImage(lid, LidDx1, LidDy1, LidDx2, LidDy2,
								0, 0, lid.getWidth()-1, lid.getHeight()-1, Color.RED, null);
		
		
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
			binPercentage = 1;
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
				binPercentage = 0;
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
	        		binPercentage += step;
	        		if(percentage > 1) percentage = 1;
	        		if(binPercentage > 1) binPercentage = 1;
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
	        		binPercentage -= step;
	        		if(percentage < 0) 	percentage = 0;
	        		if(binPercentage < 0) binPercentage = 0;
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

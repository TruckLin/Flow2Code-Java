package graph.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BlockFD extends JPanel {
	protected String name;
	protected String input;
	protected ArrayList<BlockFD> children;
	protected ArrayList<PropertyChangeListener> lines;
	
	
	protected int defaultWidth = 100; // default width and height.
	protected int defaultHeight = 25;
	
	protected Point outPort;
	protected Point inPort;
	
	
	/** Constructors **/
	public BlockFD() {
		this.name = "";
		this.children = new ArrayList<BlockFD>();
		this.lines = new ArrayList<PropertyChangeListener>();
		
		this.setSize(defaultWidth, defaultHeight);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setInPort();
		this.setOutPort();
		
	}
	public BlockFD(String N) {
		this.name = N;
		this.children = new ArrayList<BlockFD>();
		this.lines = new ArrayList<PropertyChangeListener>();
		
		this.setSize(defaultWidth, defaultHeight);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setInPort();
		this.setOutPort();
		
	}
	
	public BlockFD(String N, Rectangle rec) {
		this.name = N;
		this.children = new ArrayList<BlockFD>();
		this.lines = new ArrayList<PropertyChangeListener>();
		if(rec == null) {
			rec = new Rectangle(10,10,defaultWidth,defaultHeight); // Default bounds.
		}
		
		this.setBounds(rec);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setInPort();
		this.setOutPort();
	}
	
	
	/** getters **/
	public Point getInPort() {
		// This method allow future modification if we ever want a different outPort position.
		// For now we want outPort to be the bottom middle point of this panel.
		return this.inPort;
		
	}
	public Point getOutPort() {
		// This method allow future modification if we ever want a different outPort position.
		// For now we want outPort to be the bottom middle point of this panel.
		return this.outPort;
		
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<PropertyChangeListener> getLines(){
		return this.lines; 
	}
	
	/** Setters **/
	public void setInPort() {
		// This method allow future modification if we ever want a different inPort position.
		// For now we want inPort to be the top middle point of this panel.
		this.inPort = new Point((int)Math.round(this.getWidth()/2) , 0);
		
	}
	public void setOutPort() {
		// This method allow future modification if we ever want a different outPort position.
		// For now we want outPort to be the bottom middle point of this panel.
		this.outPort = new Point((int)Math.round(this.getWidth()/2) , this.getHeight());
	}
	
	
	/** Event handling functions **/
	public void notifyLines(BlockFD block, String property, Point oldValue, Point newValue) {
		int len = lines.size();
		
		//Testing
		//System.out.println("In BlockFD.notifyLines() : ");
		//System.out.println("Number of lines = " + len);
		for (int i = 0; i < len ; i++ ) {
			
			//Testing
			//System.out.println("	Line " + i + ":");
			
            lines.get(i).propertyChange(new PropertyChangeEvent(block, property, oldValue, newValue));
        }
	}
	public void addLine(PropertyChangeListener ln) {
		// TODO Auto-generated method stub
		this.lines.add(ln);
	}
	public void removeLine(PropertyChangeListener ln) {
		this.lines.remove(ln);
	}
	
	// Notify lines when we set locations
	public void setLocation(Point p1) {
		Point old = this.getLocation();
		super.setLocation(p1);
		notifyLines(this, "Location", old, p1 );
	}
	public void setLocation(int x, int y) {
		Point old = this.getLocation();
		super.setLocation(x,y);
		Point newpt = new Point(x, y);
		
		// Testing
		//System.out.println("In BlockFD.setLocation(int x, int y) : ");
		//System.out.println("old topleft = " + old.toString());
		//System.out.println("new topleft = " + newpt.toString() + "\n");
	
		//notifyLines(this, "Location", old, newpt); 
		/** Important!!! We don't have to call notifyLines as Swing calls it whenever propertyChanges. **/
		
	}
	
	
	/** Utility functions **/
	public void addChild(BlockFD block) {
		this.children.add(block);
	}
	public void removeChild(BlockFD block) {
		this.children.remove(block);
	}
	public Point toContainerCoordinate(Point coordWRTblock) {
		int x = (int)(this.getLocation().getX() + coordWRTblock.getX());
		int y = (int)(this.getLocation().getY() + coordWRTblock.getY());
		return new Point(x,y);
	}
	public void translateLocation(int dx, int dy) {
		int x = (int)(this.getLocation().getX() + dx);
		int y = (int)(this.getLocation().getY() + dy);
		
		// Testing
		//System.out.println("In BlockFD.translateLocation(int dx, int dy) : ");
		//System.out.println("new location = (" + x + "," + y + ")\n");

		this.setLocation(new Point(x, y));
		
	}
	public ArrayList<BlockFD> buildChildrenList() {
		// Children List will include this block itself.
		
		//Testing
		//System.out.println("In BlockFD.buildChildrenList() : ");
		
		ArrayList<BlockFD> output = new ArrayList<BlockFD>();
		BlockFD tempBlock = new BlockFD();
		output.add(this);
		
		for(int i = 0; i < this.lines.size(); i++) {
			LineFD L = (LineFD)this.lines.get(i);	
			if(L.isSource(this)) {
				tempBlock = L.getTerminal();
				output.add(tempBlock);	
			}
		}
		return output;
	}
	
	public BlockFD getDirectChild() {
		BlockFD directChild = new BlockFD();
		for(int i = 0; i < this.lines.size(); i++) {
			LineFD L = (LineFD)this.lines.get(i);	
			if(L.isSource(this)) {
				directChild = L.getTerminal();
			}
		}
		return directChild;
	}
	
	
}

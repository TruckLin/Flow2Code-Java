package graph.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import gui.practice;
public class BlockSTART extends BlockFD{

	// The point where line flows out. Coordinate is with respect to this panel not its container.
	
	
	/** Constructors **/
	public BlockSTART() {
		super("Start");
		
		setOutPort();
		
		this.add(new JLabel("Main"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	public BlockSTART(String N, Rectangle rec) {
		super(N,rec);
		
		setOutPort();
		this.add(new JLabel("Main"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** getters **/
	/*
	public Point getOutPort() {
		// This method allow future modification if we ever want a different outPort position.
		// For now we want outPort to be the bottom middle point of this panel.
		return this.outPort;
		
	}
	*/
	
	
	/** Setters **/
	/*
	public void setOutPort() {
		// This method allow future modification if we ever want a different outPort position.
		// For now we want outPort to be the bottom middle point of this panel.
		this.outPort = new Point((int)Math.round(this.width/2) , this.height);
		
	}
	*/
	
	/** Main function **/
	public static void main(String[] args) {
		
		
	}
	
}

package graph.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class BlockEND extends BlockFD {
	
	private Point inPort;
	// The point where line flows in. Coordinate is with respect to this panel not its container.
	
	
	/** Constructors **/
	public BlockEND() {
		super("End");
		
		setInPort();
		
		this.add(new JLabel("End"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	
	}
	public BlockEND(String N, Rectangle rec) {
		super(N,rec);
		
		setInPort();
		this.add(new JLabel("End"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** getters **/
	/*
	public Point getInPort() {
		// This method allow future modification if we ever want a different outPort position.
		// For now we want outPort to be the bottom middle point of this panel.
		return this.inPort;
		
	}
	*/
	
	/** Setters **/
	/*
	public void setInPort() {
		// This method allow future modification if we ever want a different inPort position.
		// For now we want inPort to be the top middle point of this panel.
		this.inPort = new Point((int)Math.round(this.width/2) , 0);
		
	}
	*/
	
}

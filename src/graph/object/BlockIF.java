package graph.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockIF extends OrdinaryBlockFD{
	BlockStartIF blockStartIF;
	BlockEndIF blockEndIF;
	
	public BlockIF(JSONObject model) {
		super(model);

		this.setOpaque(false); // we should always see through this while panel.
		
		//Temporary
		this.setSize(110,110);
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Finally set the location of ports.
		//this.setPorts();
	}
	
	/** Initialisation **/
	public void setPorts() {
		// Finally we need to set the outport and inport for BlockIF,
		this.setInport(blockStartIF.toContainerCoordinate(blockStartIF.getInport()));
		this.setOutport(blockEndIF.toContainerCoordinate(blockEndIF.getOutport()));
		
		//Testing
		//System.out.println("setPorts() is called : ");
		//System.out.println("Bounds of blockEndIF : " + blockEndIF.getBounds().toString());
		//System.out.println("Outport of blockEndIF : " + blockEndIF.getOutport().toString());
		//System.out.println("Outport of blockIF : " + this.getOutport().toString());
		
		
	}
	
	/** Getters and Setters **/
	public BlockStartIF getBlockStartIF() {
		return this.blockStartIF;
	}
	public void setBlockStartIF(BlockStartIF b) {
		this.blockStartIF = b;
	}
	public BlockEndIF getBlockEndIF() {
		return this.blockEndIF; 
	}
	public void setBlockEndIF(BlockEndIF b) {
		this.blockEndIF = b;
	}
	
	/*
	public BlockSTART getBlockStartFalseIF() {
		return this.blockStartFalseIF;
	}
	public BlockSTART getBlockStartTrueIF() {
		return this.blockStartTrueIF;
	}
	public BlockEND getBlockEndFalseIF() {
		return this.blockEndFalseIF;
	}
	public BlockEND getBlockEndTrueIF() {
		return this.blockEndTrueIF;
	}
	*/
	
	/** Utilities **/
	public void setAppropriateBounds() {
		// This function set approriate size according to it's children.
		// Size that is just big enough to contain all the children.
		super.setAppropriateBounds();
		setPorts();
		
	}
	
	
	// These funstion returns the point in terms of PanelFD's coordinate.
	/*
	public Point getStartFalseOutportpt() {
		return this.blockStartFalseIF.toContainerCoordinate(this.blockStartFalseIF.getOutport());
	}
	public Point getStartTrueOutportpt() {
		return this.blockStartTrueIF.toContainerCoordinate(this.blockStartTrueIF.getOutport());
	}
	public Point getEndFalseInportpt() {
		return this.blockEndFalseIF.toContainerCoordinate(this.blockEndFalseIF.getInport());
	}
	public Point getEndTrueInportpt() {
		return this.blockEndTrueIF.toContainerCoordinate(this.blockEndTrueIF.getInport());
	}
	*/
}

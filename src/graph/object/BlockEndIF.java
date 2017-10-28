package graph.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

public class BlockEndIF extends BlockSTART{
	public BlockEndIF(JSONObject model){
		super(model);
		this.removeAll();
		this.setLayout(null);
		
		// Temporary
		this.setBounds(0,0,25,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Initialise outport
		this.setOutport(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()));
	}
	
	/** Graphics setting **/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (int)this.getLocation().getX();
        int y = (int)this.getLocation().getY();
        int width = this.getWidth();
        int height = this.getHeight();
        
        g.setColor(Color.red);
        g.fillOval(x, y, width, height);
    }
	

}

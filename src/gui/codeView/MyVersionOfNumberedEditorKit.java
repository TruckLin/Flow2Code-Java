package gui.codeView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;


public class MyVersionOfNumberedEditorKit extends StyledEditorKit {
    public ViewFactory getViewFactory() {
    	return new MyVersionOfNumberedViewFactory();
    }
}

class MyVersionOfNumberedViewFactory implements ViewFactory {
    public View create(Element elem) {
        String kind = elem.getName();
        if (kind != null)
            if (kind.equals(AbstractDocument.ContentElementName)) {
                return new LabelView(elem);
            }
            else if (kind.equals(AbstractDocument.
                             ParagraphElementName)) {
//              return new ParagraphView(elem);
                return new MyVersionOfNumberedParagraphView(elem);
            }
            else if (kind.equals(AbstractDocument.
                     SectionElementName)) {
                return new BoxView(elem, View.Y_AXIS);
            }
            else if (kind.equals(StyleConstants.
                     ComponentElementName)) {
                return new ComponentView(elem);
            }
            else if (kind.equals(StyleConstants.IconElementName)) {
                return new IconView(elem);
            }
        // default to text display
        return new LabelView(elem);
    }
}

class MyVersionOfNumberedParagraphView extends ParagraphView {
    public static short NUMBERS_WIDTH = 25;
    public static Color NumberColor = Color.BLACK;
    //public static Color NumberBgColor = new Color(0,185,255,150);
    public static Color NumberBgColor = new Color(255,255,255,100); //Transparent white.
    public static Color SeparationLineColor = Color.LIGHT_GRAY;
    
    public MyVersionOfNumberedParagraphView(Element e) {
        super(e);
    }

    /*
    protected void setInsets(short top, short left, short bottom, short right) {
    		super.setInsets(top, (short)(left+NUMBERS_WIDTH), bottom, right);
    }
    */

    public void paintChild(Graphics g, Rectangle r, int n) {
        super.paintChild(g, r, n);
        int previousLineCount = getPreviousLineCount();
        int numberX = r.x - MyVersionOfNumberedParagraphView.NUMBERS_WIDTH;
        int numberY = r.y + r.height - 5;
        Color old = g.getColor();
        g.setColor(MyVersionOfNumberedParagraphView.NumberColor);
        g.drawString(Integer.toString(previousLineCount + n + 1),
                                      numberX, numberY);
        g.setColor(MyVersionOfNumberedParagraphView.NumberBgColor);
        g.fillRect(numberX, r.y, MyVersionOfNumberedParagraphView.NUMBERS_WIDTH, r.height);
        g.setColor(MyVersionOfNumberedParagraphView.SeparationLineColor);
        g.fillRect(r.x - 1, r.y, 1, r.height);
        g.setColor(old);
    }

    public int getPreviousLineCount() {
        int lineCount = 0;
        View parent = this.getParent();
        int count = parent.getViewCount();
        for (int i = 0; i < count; i++) {
            if (parent.getView(i) == this) {
                break;
            }
            else {
                lineCount += parent.getView(i).getViewCount();
            }
        }
        return lineCount;
    }
}

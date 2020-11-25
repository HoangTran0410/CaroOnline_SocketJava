package Docs.FromInternet.UseJCanvas;

//package dk.ruc.madsr.swing;
//----------------------------------------------------------------------
// This class is written by Mads Rosendahl, University of Roskilde, Denmark
// You may uncomment the first line, but otherwise, please do not change the code.
// If you find errors or have any suggestions contact me at madsr@ruc.dk
//
// version 1.4 : 04-02-00
//----------------------------------------------------------------

import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.renderable.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.*;
import java.awt.image.*;
import java.awt.print.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;

//--------------------------------------------------------------------------
//
/**
 * JCanvas: JComponent that acts as a Graphics object
 */
//
//
public class JCanvas extends JComponent implements Printable {
    // I'm using constants rather than enum to avoid too many class files.

    static final int addRenderingHints = 1, clip1 = 2, draw1 = 3, draw3DRect = 4,
            drawGlyphVector = 5, drawImage1 = 6, drawImage2 = 7, drawRenderableImage = 8,
            drawRenderedImage = 9, drawString1 = 10, drawString2 = 11, drawString3 = 12,
            drawString4 = 13, fill = 14, fill3DRect = 15, rotate1 = 16, rotate2 = 17, scale1 = 18,
            setBackground = 19, setComposite = 20, setPaint = 21, setRenderingHint = 22,
            setRenderingHints = 23, setStroke = 24, setTransform = 25, shear = 26, transform1 = 27,
            translate1 = 28, translate2 = 29, clearRect = 30,
            //clipRect=31,
            copyArea = 32, drawArc = 33, drawBytes = 34, drawChars = 35, drawImage4 = 36,
            drawImage5 = 37, drawImage6 = 38, drawImage7 = 39, drawImage8 = 40, drawImage9 = 41,
            drawLine = 42, drawOval = 43, drawPolygon1 = 44, drawPolygon2 = 45, drawPolyline = 46,
            drawRect = 47, drawRoundRect = 48, fillArc = 49, fillOval = 50, fillPolygon1 = 51,
            fillPolygon2 = 52, fillRect = 53, fillRoundRect = 54, setColor = 57, setFont = 58,
            setPaintMode = 59, setClip1 = 55,//  setClip2=56, 
            setXORMode = 60, clear = 61, opaque = 62, drawOutline = 63;

    /**
     * Create a JCanvas object
     */
    public JCanvas() {
        setFocusable(true);
    }
    //--------------------------------
    //
    // access functions 
    //  functions from Graphics and Graphics2D
    //

    /**
     * [Advanced]
     */
    public void addRenderingHints(Map<?, ?> hints) {
        toBuffer(addRenderingHints, hints);
    }

    /**
     * set clipping shape
     */
    public void clip(Shape s) {
        toBuffer(clip1, s);
    }

    /**
     * draw a shape
     */
    public void draw(Shape s) {
        toBuffer(draw1, s);
    }

    /**
     * draw a 3D rectangle
     */
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
        toBuffer(draw3DRect, mkArg(x, y, width, height, raised));
    }

    /**
     * [Advanced]
     */
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        toBuffer(drawGlyphVector, mkArg(g, x, y));
    }

    /**
     * [Advanced]
     */
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        toBuffer(drawImage1, mkArg(img, op, x, y));
    }

    /**
     * [Advanced]
     */
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        toBuffer(drawImage2, mkArg(img, xform, obs));
        return true;
    }

    /**
     * [Advanced]
     */
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        toBuffer(drawRenderableImage, mkArg(img, xform));
    }

    /**
     * [Advanced]
     */
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        toBuffer(drawRenderedImage, mkArg(img, xform));
    }

    /**
     * [Advanced]
     */
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        toBuffer(drawString1, mkArg(iterator, x, y));
    }

    /**
     * [Advanced]
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        toBuffer(drawString2, mkArg(iterator, x, y));
    }

    /**
     * draw a string
     */
    public void drawString(String s, float x, float y) {
        toBuffer(drawString3, mkArg(s, x, y));
    }

    /**
     * draw a string
     */
    public void drawString(String str, int x, int y) {
        toBuffer(drawString4, mkArg(str, x, y));
    }

    /**
     * draw the outline of characters in a string
     */
    public void drawOutline(String str, int x, int y) {
        toBuffer(drawOutline, mkArg(str, x, y));
    }

    /**
     * fill a shape
     */
    public void fill(Shape s) {
        toBuffer(fill, s);
    }

    /**
     * fill a 3D rectangle
     */
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
        toBuffer(fill3DRect, mkArg(x, y, width, height, raised));
    }

    /**
     * return current background color
     */
    public Color getBackground() {
        return bufferBackground();
    }

    /**
     * return current composite description
     */
    public Composite getComposite() {
        return bufferComposite();
    }

    /**
     * [Advanced]
     */
    public GraphicsConfiguration getDeviceConfiguration() {
        Graphics2D g = getG();
        return g == null ? null : g.getDeviceConfiguration();
    }

    /**
     * [Advanced]
     */
    public FontRenderContext getFontRenderContext() {
        Graphics2D g = getG();
        return g == null ? null : g.getFontRenderContext();
    }

    /**
     * return current <code>Paint</code> object
     */
    public Paint getPaint() {
        return bufferPaint();
    }

    /**
     * [Advanced]
     */
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        Graphics2D g = getG();
        return g == null ? null : g.getRenderingHint(hintKey);
    }

    /**
     * [Advanced]
     */
    public RenderingHints getRenderingHints() {
        Graphics2D g = getG();
        return g == null ? null : g.getRenderingHints();
    }

    public Stroke getStroke() {
        return bufferStroke();
    }

    public AffineTransform getTransform() {
        return bufferTransform();
    }

    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        Graphics2D g = getG();
        return g == null ? false : g.hit(rect, s, onStroke);
    }

    public void rotate(double theta) {
        toBuffer(rotate1, theta);
    }

    public void rotate(double theta, double x, double y) {
        toBuffer(rotate2, mkArg(theta, x, y));
    }

    public void scale(double sx, double sy) {
        toBuffer(scale1, mkArg(sx, sy));
    }

    public void setBackground(Color color) {
        toBuffer(setBackground, color);
        toBuffer(clear, null);
    }

    public void setComposite(Composite comp) {
        toBuffer(setComposite, comp);
    }

    public void setPaint(Paint paint) {
        toBuffer(setPaint, paint);
    }

    /**
     * [Advanced]
     */
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
        toBuffer(setRenderingHint, mkArg(hintKey, hintValue));
    }

    /**
     * [Advanced]
     */
    public void setRenderingHints(Map<?, ?> hints) {
        toBuffer(setRenderingHints, hints);
    }

    public void setStroke(Stroke s) {
        toBuffer(setStroke, s);
    }

    public void setTransform(AffineTransform Tx) {
        toBuffer(setTransform, Tx);
    }

    public void shear(double shx, double shy) {
        toBuffer(shear, mkArg(shx, shy));
    }

    public void transform(AffineTransform Tx) {
        toBuffer(transform1, Tx);
    }

    public void translate(double tx, double ty) {
        toBuffer(translate1, mkArg(tx, ty));
    }

    public void translate(int x, int y) {
        toBuffer(translate2, mkArg(x, y));
    }

    public void clearRect(int x, int y, int width, int height) {
        toBuffer(clearRect, mkArg(x, y, width, height));
    }

    public void clipRect(int x, int y, int width, int height) {
        clip(new Rectangle(x, y, width, height));
    }

    //toBuffer(clipRect,mkArg(x, y, width, height) );}
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        toBuffer(copyArea, mkArg(x, y, width, height, dx, dy));
    }

    /**
     * [Advanced]
     */
    public Graphics create() {
        Graphics2D g = getG();
        return g == null ? null : g.create();
    }

    /**
     * [Advanced]
     */
    public Graphics create(int x, int y, int width, int height) {
        Graphics2D g = getG();
        return g == null ? null : g.create(x, y, width, height);
    }

    /**
     * [Advanced]
     */
    public void dispose() {
        Graphics2D g = getG();
        if (g != null) {
            g.dispose();
        }
    }
    //public void draw3DRect(int x, int y, int width, int height, boolean raised) {
    //  toBuffer(draw3DRect,mkArg(x, y, width, height, raised) );}

    public void drawArc(int x, int y, int width, int height,
            int startAngle, int arcAngle) {
        toBuffer(drawArc, mkArg(x, y, width, height, startAngle, arcAngle));
    }

    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        toBuffer(drawBytes, mkArg(data, offset, length, x, y));
    }

    /**
     * [Advanced]
     */
    public void drawChars(char[] data, int offset, int length, int x, int y) {
        toBuffer(drawChars, mkArg(data, offset, length, x, y));
    }

    public boolean drawImage(Image img, int x, int y, Color bgcolor,
            ImageObserver observer) {
        toBuffer(drawImage4, mkArg(img, x, y, bgcolor, observer));
        return true;
    }

    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        toBuffer(drawImage5, mkArg(img, x, y, observer));
        return true;
    }

    public boolean drawImage(Image img, int x, int y, int width, int height,
            Color bgcolor, ImageObserver observer) {
        toBuffer(drawImage6, mkArg(img, x, y, width, height, bgcolor, observer));
        return true;
    }

    public boolean drawImage(Image img, int x, int y, int width, int height,
            ImageObserver observer) {
        toBuffer(drawImage7, mkArg(img, x, y, width, height, observer));
        return true;
    }

    /**
     * [Advanced]
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1,
            int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        toBuffer(drawImage8, mkArg(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
                bgcolor, observer));
        return true;
    }

    /**
     * [Advanced]
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1,
            int sy1, int sx2, int sy2, ImageObserver observer) {
        toBuffer(drawImage9, mkArg(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
        return true;
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        toBuffer(drawLine, mkArg(x1, y1, x2, y2));
    }

    public void drawOval(int x, int y, int width, int height) {
        toBuffer(drawOval, mkArg(x, y, width, height));
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        toBuffer(drawPolygon1, mkArg(xPoints, yPoints, nPoints));
    }

    public void drawPolygon(Polygon p) {
        toBuffer(drawPolygon2, p);
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        toBuffer(drawPolyline, mkArg(xPoints, yPoints, nPoints));
    }

    public void drawRect(int x, int y, int width, int height) {
        toBuffer(drawRect, mkArg(x, y, width, height));
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth,
            int arcHeight) {
        toBuffer(drawRoundRect, mkArg(x, y, width, height, arcWidth, arcHeight));
    }
    //public void drawString(AttributedCharacterIterator iterator, int x, int y) {
    //  toBuffer(drawString,mkArg(iterator, x, y) );}
    //public void drawString(String str, int x, int y) {
    //  toBuffer(drawString,mkArg(str, x, y) );}
    //public void fill3DRect(int x, int y, int width, int height, boolean raised) {
    // toBuffer(fill3DRect,mkArg(x, y, width, height, raised) );}

    public void fillArc(int x, int y, int width, int height,
            int startAngle, int arcAngle) {
        toBuffer(fillArc, mkArg(x, y, width, height, startAngle, arcAngle));
    }

    public void fillOval(int x, int y, int width, int height) {
        toBuffer(fillOval, mkArg(x, y, width, height));
    }

    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        toBuffer(fillPolygon1, mkArg(xPoints, yPoints, nPoints));
    }

    public void fillPolygon(Polygon p) {
        toBuffer(fillPolygon2, p);
    }

    public void fillRect(int x, int y, int width, int height) {
        toBuffer(fillRect, mkArg(x, y, width, height));
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth,
            int arcHeight) {
        toBuffer(fillRoundRect, mkArg(x, y, width, height, arcWidth, arcHeight));
    }

    /**
     * [Advanced]
     */
    public void finalize() throws Throwable {
        super.finalize();
    }

    public Shape getClip() {
        return bufferClip();
    }

    //Graphics2D g=getG();return g==null?null:g.getClip();}
    public Rectangle getClipBounds() {
        Shape s = bufferClip();
        return s == null ? null : s.getBounds();
    }

    //Graphics2D g=getG();return g==null?null:g.getClipBounds();}
    public Rectangle getClipBounds(Rectangle r) {
        Shape s = bufferClip();
        if (s == null) {
            return null;
        }
        r.setBounds(bufferClip().getBounds());
        return r;
    }

    //Graphics2D g=getG();return g==null?null:g.getClipBounds(r);}
    // deprecated public Rectangle getClipRect() { return getG().getClipRect();}
    public Color getColor() {
        Paint p = getPaint();
        if (p != null && p instanceof Color) {
            return (Color) p;
        }
        return null;
    }

    //Graphics2D g=getG();return g==null?null:g.getColor();}
    public Font getFont() {
        return bufferFont();
    }

    //Font f=(Font)lookBuffer(setFont);if(f==null)return super.getFont();return f;}
    public FontMetrics getFontMetrics() {
        return getFontMetrics(getFont());
    }

    public FontMetrics getFontMetrics(Font f) {
        if (getG() == null) {
            return super.getFontMetrics(f);
        } else {
            return getG().getFontMetrics(f);
        }
    }

    public boolean hitClip(int x, int y, int width, int height) {
        Graphics2D g = getG();
        return g == null ? false : g.hitClip(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        setClip(new Rectangle(x, y, width, height));
    }

    //toBuffer(setClip1,mkArg(x, y, width, height) );}
    public void setClip(Shape clip) {
        if (clip == null) {
            clip = new Rectangle(-1000000, -1000000, 2000000, 2000000);
        }
        toBuffer(setClip1, clip);
    }

    public void setColor(Color c) {
        setPaint(c);
    }//toBuffer(setColor,c );}

    public void setFont(Font font) {
        super.setFont(font);
        toBuffer(setFont, font);
    }

    public void setPaintMode() {
        toBuffer(setPaintMode, null);
    }

    public void setXORMode(Color c1) {
        toBuffer(setXORMode, c1);
    }

    public String toString() {
        return super.toString();
    }
    //public void translate(int x, int y) {toBuffer("translate",mkArg(x, y) );}

    public void clear() {
        toBuffer(clear, null);
    }

    public void setOpaque(boolean b) {
        toBuffer(opaque, b);
    }

    //---------------------------------------------------------
    // state changes
    //
    private Boolean theOpaque = null;
    private Color theBackground = null;
    //private Paint thePaint=null;
    //private Font theFont=null;
    //private Stroke theStroke=null;

    //private Boolean origOpaque=null;
    //private Color origBackground=null;
    private static Paint origPaint = Color.BLACK;
    private static Font origFont = new Font("Dialog", Font.PLAIN, 12);
    private static Stroke origStroke = new BasicStroke(1);

    // also clip, transform, composite,   
    //public boolean isOpaque(){return false;}//theOpaque!=null&&theOpaque;}
    //---------------------------------------------------------
    private void doPaint(Graphics2D g, int s, Object o) {
        // process an operation from the buffer
        //System.out.println(s);
        Object o1 = null, o2 = null, o3 = null, o4 = null, o5 = null, o6 = null, o7 = null, o8 = null,
                o9 = null, o10 = null, o11 = null;
        if (o instanceof Object[]) {
            Object[] a = (Object[]) o;
            if (a.length > 0) {
                o1 = a[0];
            }
            if (a.length > 1) {
                o2 = a[1];
            }
            if (a.length > 2) {
                o3 = a[2];
            }
            if (a.length > 3) {
                o4 = a[3];
            }
            if (a.length > 4) {
                o5 = a[4];
            }
            if (a.length > 5) {
                o6 = a[5];
            }
            if (a.length > 6) {
                o7 = a[6];
            }
            if (a.length > 7) {
                o8 = a[7];
            }
            if (a.length > 8) {
                o9 = a[8];
            }
            if (a.length > 9) {
                o10 = a[9];
            }
            if (a.length > 10) {
                o11 = a[10];
            }
        }
        switch (s) {
            case clear:
                paintBackground(g, theBackground);
                break;
            //public void addRenderingHints(Map<?,?> hints) 
            //{toBuffer("addRenderingHints",hints );}
            case addRenderingHints:
                g.addRenderingHints((Map<?, ?>) o);
                break;
            case clip1:
                g.clip((Shape) o);
                break;
            case draw1:
                g.draw((Shape) o);
                break;
            case draw3DRect:
                g.draw3DRect((Integer) o1, (Integer) o2, (Integer) o3,
                        (Integer) o4, (Boolean) o5);
                break;
            case drawGlyphVector:
                g.drawGlyphVector((GlyphVector) o1, (Float) o2, (Float) o3);
                break;
            case drawImage1:
                g.drawImage((BufferedImage) o1, (BufferedImageOp) o2, (Integer) o3, (Integer) o4);
                break;
            case drawImage2:
                g.drawImage((Image) o1, (AffineTransform) o2, (ImageObserver) o3);
                break;
            case drawRenderableImage:
                g.drawRenderableImage((RenderableImage) o1, (AffineTransform) o2);
                break;
            case drawRenderedImage:
                g.drawRenderedImage((RenderedImage) o1, (AffineTransform) o2);
                break;
            case drawString1:
                g.drawString((AttributedCharacterIterator) o1, (Float) o2, (Float) o3);
                break;
            case drawString2:
                g.drawString((AttributedCharacterIterator) o1, (Integer) o2, (Integer) o3);
                break;
            case drawString3:
                g.drawString((String) o1, (Float) o2, (Float) o3);
                break;
            case drawString4:
                g.drawString((String) o1, (Integer) o2, (Integer) o3);
                break;
            case fill:
                g.fill((Shape) o);
                break;
            case fill3DRect:
                g.fill3DRect((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4, (Boolean) o5);
                break;
            case rotate1:
                g.rotate((Double) o);
                break;
            case rotate2:
                g.rotate((Double) o1, (Double) o2, (Double) o3);
                break;
            case scale1:
                g.scale((Double) o1, (Double) o2);
                break;
            case setBackground:
                g.setBackground((Color) o); //paintBackground(g,(Color)o); /*super.setBackground((Color)o) ;*/
                break;
            case setComposite:
                g.setComposite((Composite) o);
                break;
            case setPaint:
                g.setPaint((Paint) o);
                break;
            case setRenderingHint:
                g.setRenderingHint((RenderingHints.Key) o1, o2);
                break;
            case setRenderingHints:
                g.setRenderingHints((Map<?, ?>) o);
                break;
            case setStroke:
                g.setStroke((Stroke) o);
                break;
            case setTransform:
                g.setTransform(makeTransform(o));
                break;
            case shear:
                g.shear((Double) o1, (Double) o2);
                break;
            case transform1:
                g.transform(makeTransform(o));
                break;
            case translate1:
                g.translate((Double) o1, (Double) o2);
                break;
            case translate2:
                g.translate((Integer) o1, (Integer) o2);
                break;
            case clearRect:
                g.clearRect((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4);
                break;
            case copyArea:
                g.copyArea((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5, (Integer) o6);
                break;
            case drawArc:
                g.drawArc((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5, (Integer) o6);
                break;
            case drawBytes:
                g.drawBytes((byte[]) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5);
                break;
            case drawChars:
                g.drawChars((char[]) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5);
                break;
            case drawImage4:
                g.drawImage((Image) o1, (Integer) o2, (Integer) o3, (Color) o4,
                        (ImageObserver) o5);
                break;
            case drawImage5:
                g.drawImage((Image) o1, (Integer) o2, (Integer) o3,
                        (ImageObserver) o4);
                break;
            case drawImage6:
                g.drawImage((Image) o1, (Integer) o2, (Integer) o3,
                        (Integer) o4, (Integer) o5, (Color) o6, (ImageObserver) o7);
                break;
            case drawImage7:
                g.drawImage((Image) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5, (ImageObserver) o6);
                break;
            case drawImage8:
                g.drawImage((Image) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5, (Integer) o6, (Integer) o7, (Integer) o8, (Integer) o9, (Color) o10,
                        (ImageObserver) o11);
                break;
            case drawImage9:
                g.drawImage((Image) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5, (Integer) o6, (Integer) o7, (Integer) o8, (Integer) o9,
                        (ImageObserver) o10);
                break;
            case drawLine:
                g.drawLine((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4);
                break;
            case drawOval:
                g.drawOval((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4);
                break;
            case drawPolygon1:
                g.drawPolygon((int[]) o1, (int[]) o2, (Integer) o3);
                break;
            case drawPolygon2:
                g.drawPolygon((Polygon) o);
                break;
            case drawPolyline:
                g.drawPolyline((int[]) o1, (int[]) o2, (Integer) o3);
                break;
            case drawRect:
                g.drawRect((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4);
                break;
            case drawRoundRect:
                g.drawRoundRect((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4, (Integer) o5,
                        (Integer) o6);
                break;
            case fillArc:
                g.fillArc((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4,
                        (Integer) o5, (Integer) o6);
                break;
            case fillOval:
                g.fillOval((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4);
                break;
            //{toBuffer("fillPolygon",mkArg(xPoints,  yPoints, nPoints) );}
            case fillPolygon1:
                g.fillPolygon((int[]) o1, (int[]) o2, (Integer) o3);
                break;
            case fillPolygon2:
                g.fillPolygon((Polygon) o);
                break;
            case fillRect:
                g.fillRect((Integer) o1, (Integer) o2, (Integer) o3, (Integer) o4);
                break;
            case fillRoundRect:
                g.fillRoundRect((Integer) o1, (Integer) o2, (Integer) o3,
                        (Integer) o4, (Integer) o5, (Integer) o6);
                break;
            case setClip1:
                g.setClip((Shape) o);
                break;
            case setColor:
                g.setColor((Color) o);
                break;
            case setFont:
                g.setFont((Font) o);
                break;
            case setPaintMode:
                g.setPaintMode();
                break;
            case setXORMode:
                g.setXORMode((Color) o);
                break;
            case opaque:
                super.setOpaque((Boolean) o);
                break;
            case drawOutline: //g.drawString((String)o1, (Integer)o2, (Integer)o3) ;break;
            {
                FontRenderContext frc = g.getFontRenderContext();
                TextLayout tl = new TextLayout((String) o1, g.getFont(), frc);
                Shape s1 = tl.getOutline(null);
                AffineTransform af = g.getTransform();
                g.translate((Integer) o2, (Integer) o3);
                g.draw(s1);
                g.setTransform(af);
            }
            ;
            break;
            default:
                System.out.println("Unknown image operation " + s);
        }
    }

    //---------------------------------------------------------
    private Object mkArg(Object... args) {
        return args;
    }

    private synchronized void toBuffer(int s, Object a) {
        a1.add(s);
        a2.add(a);
        if (s == clear) {
            clearBuffer();
        }
        if (s == opaque) {
            theOpaque = (Boolean) a;
        }
        if (s == setBackground) {
            theBackground = (Color) a;
        }
        if (inBuffer) {
            return;
        }
        if (isSetter(s)) {
            return;
        }
        Graphics g = getGraphics();
        if (g == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(origPaint);
        g2.setFont(origFont);
        g2.setStroke(origStroke);
        for (int i = 0; i < a1.size() - 1; i++) {
            int s1 = a1.get(i);
            Object s2 = a2.get(i);
            if (isSetter(s1)) {
                doPaint(g2, s1, s2);
            }
        }
        doPaint((Graphics2D) g, s, a);
    }

    private Graphics2D getG() {
        return (Graphics2D) getGraphics();
    }

    //---------------------------------------------------------
    private List<Integer> a1 = new ArrayList<Integer>();
    private List<Object> a2 = new ArrayList<Object>();
    private List<Integer> a1x = new ArrayList<Integer>();
    private List<Object> a2x = new ArrayList<Object>();
    private boolean inBuffer = false;

    /**
     * Start double-buffering
     */
    public synchronized void startBuffer() {
        inBuffer = true;
        a1x.clear();
        a2x.clear();
        List<Integer> h1 = a1x;
        List<Object> h2 = a2x;
        a1x = a1;
        a2x = a2;
        a1 = h1;
        a2 = h2;
    }

    /**
     * End double-buffering
     */
    public synchronized void endBuffer() {
        inBuffer = false;
        a1x.clear();
        a2x.clear();
        repaint();
    }

    private synchronized void clearBuffer() {
        Font f = bufferFont();
        Paint p = bufferPaint();
        Stroke s = bufferStroke();
        a1.clear();
        a2.clear();
        if (f != origFont) {
            a1.add(setFont);
            a2.add(f);
        }
        if (p != origPaint) {
            a1.add(setPaint);
            a2.add(p);
        }
        if (s != origStroke) {
            a1.add(setStroke);
            a2.add(s);
        }
        a1.add(clear);
        a2.add(null);
    }

    private synchronized void doBuffer(Graphics2D g2, boolean opq, Rectangle rt) {
        origTransform = g2.getTransform();
        if (opq && rt != null) {
            g2.clearRect(rt.x, rt.y, rt.width, rt.height);
        }
        g2.setPaint(origPaint);
        g2.setFont(origFont);
        g2.setStroke(origStroke);
        if (inBuffer) {//System.out.println("upps");
            for (int i = 0; i < a1x.size(); i++) {
                doPaint(g2, a1x.get(i), a2x.get(i));
            }
            origTransform = null;
            return;
        }
        for (int i = 0; i < a1.size(); i++) {
            doPaint(g2, a1.get(i), a2.get(i));
        }
        origTransform = null;
    }

    private synchronized Object lookBuffer(int s) {
        for (int i = a1.size() - 1; i >= 0; i--) {
            if (a1.get(i) == s) {
                return a2.get(i);
            }
        }
        return null;
    }

    //---------------------------------------------------------
    //
    //
    private AffineTransform origTransform = null;
    private double printScale = 1;

    /**
     * [Internal]
     */
    public void paintComponent(Graphics g) {
        boolean opq = true;
        if (theOpaque != null) {
            opq = theOpaque;
        }
        super.setOpaque(opq);
        //if(theBackground!=null)super.setBackground(theBackground);     
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Rectangle rt = getBounds();
        rt.x = 0;
        rt.y = 0;
        doBuffer(g2, opq, rt);
        chkFPS();
    }

    /**
     * print the canvas
     */
    public void print() {
        print(1.0);
    }

    /**
     * print the canvas scaled with factor <code>scale</code>
     */
    public void print(double scale) {
        printScale = scale;
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * [Internal]
     */
    private void paintBackground(Graphics2D g2, Color theBackground) {
        Color color1 = g2.getColor();
        if (theBackground == null) {
            theBackground = Color.white;
        }
        g2.setColor(theBackground);
        g2.fillRect(0, 0, 30000, 30000);
        g2.setColor(color1);
    }

    /**
     * [Internal]
     */
    public int print(Graphics g, PageFormat pf, int pi)
            throws PrinterException {
        if (pi >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        RepaintManager currentManager = RepaintManager.currentManager(this);
        currentManager.setDoubleBufferingEnabled(false);
        Graphics2D g2 = (Graphics2D) g;
        initState(g2, true);
        g2.translate((int) (pf.getImageableX() + 1), (int) (pf.getImageableY() + 1));
        g2.scale(printScale, printScale);
        doBuffer(g2, true, null);
        currentManager.setDoubleBufferingEnabled(true);
        return Printable.PAGE_EXISTS;
    }

    /**
     * paint the canvas into a image file of given width and height
     */
    public void writeToImage(String s, int w, int h) {
        String ext;
        File f;
        try {
            ext = s.substring(s.lastIndexOf(".") + 1);
            f = new File(s);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        if (!ext.equals("jpg") && !ext.equals("png")) {
            System.out.println("Cannot write to file: Illegal extension " + ext);
            return;
        }
        boolean opq = true;
        if (theOpaque != null) {
            opq = theOpaque;
        }

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setBackground(Color.white);
        g2.setPaint(Color.black);
        g2.setStroke(new BasicStroke(1));
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        doBuffer(g2, true, new Rectangle(0, 0, w, h));
        try {
            ImageIO.write(image, ext, f);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initState(Graphics2D g2, boolean opq) {
    }

    //---------------------------------------------------------
    // operations that change state but do not draw anything
    private static int[] setOp = {
        addRenderingHints, clip1,
        rotate1, rotate2, scale1, setBackground, setComposite,
        setPaint, setRenderingHint, setRenderingHints, setStroke, setTransform,
        shear, transform1, translate1, translate2, //clipRect,
        copyArea, setColor, setFont, setPaintMode, setClip1, // setClip2,
        setXORMode
    };

    private boolean isSetter(int s) {
        for (int s1 : setOp) {
            if (s == s1) {
                return true;
            }
        }
        return false;
    }
    //---------------------------------------------------------

    /**
     * [Internal]
     */
    public synchronized void dumpBuffer() {
        System.out.println("Opaque " + theOpaque);
        //System.out.println("Font "+obj2str(theFont));
        //System.out.println("Paint "+obj2str(thePaint));
        System.out.println("Background " + obj2str(theBackground));
        //System.out.println("Stroke "+obj2str(theStroke));
        for (int i = 0; i < a1.size(); i++) {
            String s1 = totxt(a1.get(i));
            String s2 = obj2str(a2.get(i));
            System.out.println(i + " " + s1 + " " + s2);
        }
    }

    /**
     * [Internal]
     */
    public String obj2str(Object o) {
        if (o == null) {
            return "-null-";
        }
        String s = "";
        if (o instanceof Object[]) {
            Object[] a = (Object[]) o;
            for (Object ox : a) {
                s = s + " " + ox;
            }
        } else if (o instanceof BasicStroke) {
            BasicStroke o1 = (BasicStroke) o;
            s = "BasicStroke(" + o1.getLineWidth() + "," + o1.getDashPhase() + "," + o1.getLineJoin()
                    + "," + o1.getMiterLimit() + "," + o1.getEndCap();

        } else {
            s = "" + o;
        }
        return s;
    }

    private String totxt(int i) {
        switch (i) {
            case 1:
                return "addRenderingHints";
            case 2:
                return "clip1";
            case 3:
                return "draw1";
            case 4:
                return "draw3DRect";
            case 5:
                return "drawGlyphVector";
            case 6:
                return "drawImage1";
            case 7:
                return "drawImage2";
            case 8:
                return "drawRenderableImage";
            case 9:
                return "drawRenderedImage";
            case 10:
                return "drawString1";
            case 11:
                return "drawString2";
            case 12:
                return "drawString3";
            case 13:
                return "drawString4";
            case 14:
                return "fill";
            case 15:
                return "fill3DRect";
            case 16:
                return "rotate1";
            case 17:
                return "rotate2";
            case 18:
                return "scale1";
            case 19:
                return "setBackground";
            case 20:
                return "setComposite";
            case 21:
                return "setPaint";
            case 22:
                return "setRenderingHint";
            case 23:
                return "setRenderingHints";
            case 24:
                return "setStroke";
            case 25:
                return "setTransform";
            case 26:
                return "shear";
            case 27:
                return "transform1";
            case 28:
                return "translate1";
            case 29:
                return "translate2";
            case 30:
                return "clearRect";
            //clipRect=31,
            case 32:
                return "copyArea";
            case 33:
                return "drawArc";
            case 34:
                return "drawBytes";
            case 35:
                return "drawChars";
            case 36:
                return "drawImage4";
            case 37:
                return "drawImage5";
            case 38:
                return "drawImage6";
            case 39:
                return "drawImage7";
            case 40:
                return "drawImage8";
            case 41:
                return "drawImage9";
            case 42:
                return "drawLine";
            case 43:
                return "drawOval";
            case 44:
                return "drawPolygon1";
            case 45:
                return "drawPolygon2";
            case 46:
                return "drawPolyline";
            case 47:
                return "drawRect";
            case 48:
                return "drawRoundRect";
            case 49:
                return "fillArc";
            case 50:
                return "fillOval";
            case 51:
                return "fillPolygon1";
            case 52:
                return "fillPolygon2";
            case 53:
                return "fillRect";
            case 54:
                return "fillRoundRect";
            case 57:
                return "setColor";
            case 55:
                return "setClip";
            case 58:
                return "setFont";
            case 59:
                return "setPaintMode";
            //setClip1=55, setClip2=56,
            case 60:
                return "setXORMode";
            case 61:
                return "clear";
            case 62:
                return "clear";
            case 63:
                return "drawOutline";
            default:
                return "unknown: " + i;
        }
    }
    //---------------------------------------------------------
    private int fpsCount = 0;
    private long fpsTime = 0;
    private int lastFPS = 10;

    private void chkFPS() {
        if (fpsCount == 0) {
            fpsTime = System.currentTimeMillis() / 1000;
            fpsCount++;
            return;
        }
        fpsCount++;
        long time = System.currentTimeMillis() / 1000;
        if (time != fpsTime) {
            lastFPS = fpsCount;
            fpsCount = 1;
            fpsTime = time;
        }
    }

    /**
     * Compute the number of frames displayed per second
     */
    public int getFPS() {
        return lastFPS;
    }
    //---------------------------------------------------------

    private Shape bufferClip() {
        Object s = lookBuffer(clip1);
        if (s instanceof Shape) {
            return (Shape) s;
        }
        return null;
    }
    //---------------------------------------------------------

    private synchronized AffineTransform bufferTransform() {
        AffineTransform r = new AffineTransform();
        for (int i = 0; i < a1.size(); i++) {
            int s1 = a1.get(i);
            Object s2 = a2.get(i);
            Object s3[] = null;
            if (s2 instanceof Object[]) {
                s3 = (Object[]) s2;
            }

            if (s1 == setTransform) {
                r = makeTransform(s2);
            }
            if (s1 == shear) {
                r.shear((Double) s3[0], (Double) s3[1]);
            }
            if (s1 == rotate1) {
                r.rotate((Double) s2);
            }
            if (s1 == rotate2) {
                r.rotate((Double) s3[0], (Double) s3[1], (Double) s3[2]);
            }
            if (s1 == scale1) {
                r.scale((Double) s3[0], (Double) s3[1]);
            }
            if (s1 == translate1) {
                r.translate((Double) s3[0], (Double) s3[1]);
            }
            if (s1 == translate2) {
                r.translate((Integer) s3[0], (Integer) s3[1]);
            }
        }
        return r;
    }

    private AffineTransform makeTransform(Object o) {
        AffineTransform r = (AffineTransform) ((AffineTransform) o).clone();
        //System.out.println("r:"+r);
        if (origTransform != null) {
            AffineTransform r2 = (AffineTransform) origTransform.clone();
            //System.out.println("r2:"+r2);
            r2.concatenate(r);
            r = r2;
        }
        return r;
    }

    private Composite bufferComposite() {
        Composite c = (Composite) lookBuffer(setComposite);
        if (c == null) {
            return AlphaComposite.SrcOver;
        }
        return c;
    }

    private Stroke bufferStroke() {
        Stroke c = (Stroke) lookBuffer(setStroke);
        //if(c==null&&theStroke!=null) return theStroke;
        if (c == null) {
            return origStroke;
        }
        return c;
    }

    private Font bufferFont() {
        Font c = (Font) lookBuffer(setFont);
        //if(c==null&&theFont!=null) return theFont;
        //if(c==null)c=origFont;
        //if(c==null) return new Font("Dialog",Font.PLAIN,12);
        if (c == null) {
            return origFont;
        }
        return c;
    }

    private Paint bufferPaint() {
        Paint c = (Paint) lookBuffer(setPaint);
        //if(c==null&&thePaint!=null) return thePaint;
        //if(c==null) return Color.black;
        if (c == null) {
            return origPaint;
        }
        return c;
    }

    private Color bufferBackground() {
        Color c = (Color) lookBuffer(setBackground);
        if (c == null && theBackground != null) {
            return theBackground;
        }
        if (c == null) {
            return null;//Color.white;
        }
        return c;
    }

    /**
     * draw shape translated to origin <code>(x,y) */
    public void draw(Shape s, int x, int y) {
        AffineTransform af = getTransform();
        translate(x, y);
        draw(s);
        setTransform(af);
    }

    /**
     * fill shape translated to origin <code>(x,y) */
    public void fill(Shape s, int x, int y) {
        AffineTransform af = getTransform();
        translate(x, y);
        fill(s);
        setTransform(af);
    }

    /**
     * draw image translated to origin <code>(x,y) */
    public void draw(BufferedImage im, int x, int y) {
        drawImage(im, null, x, y);
    }

    /**
     * draw image translated to origin <code>(x,y) */
    public void drawImage(BufferedImage im, int x, int y) {
        drawImage(im, null, x, y);
    }

    public void drawScaledImage(BufferedImage im, int x, int y, int w, int h) {
        float scaleX = w * 1.0f / im.getWidth();
        float scaleY = h * 1.0f / im.getHeight();
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleX, scaleY);
        BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        drawImage(im, op, x, y);
    }

    public void drawScaledImage(BufferedImage im, int x, int y, float scale) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scale, scale);
        BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        drawImage(im, op, x, y);
    }

    public void drawDashedLine(int d, int x1, int y1, int x2, int y2) {
        Stroke s = getStroke();
        float w = 1;
        int c = BasicStroke.CAP_BUTT;
        int j = BasicStroke.JOIN_MITER;
        float ml = 0;
        float[] dp = {d, d};
        if (s instanceof BasicStroke) {
            BasicStroke b = (BasicStroke) s;
            w = b.getLineWidth();
            c = b.getEndCap();
            j = b.getLineJoin();
            ml = b.getMiterLimit();

        }
        setStroke(new BasicStroke(w, c, j, ml, dp, 0));
        drawLine(x1, y1, x2, y2);
        setStroke(s);
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
    public static final long serialVersionUID = 42L;
    //------------------------------------------------------------------
    //
    //  Image Operations
    //

    public static BufferedImage loadImage(String s) {
        try {
            return ImageIO.read(new File(s));
        } catch (Exception e) {
            System.out.println("cannot load image " + s);
            return null;
        }
    }

    public static BufferedImage loadImage(URL u) {
        try {
            return ImageIO.read(u);
        } catch (Exception e) {
            System.out.println("cannot load image " + u);
            return null;
        }
    }

    public static BufferedImage scaleImage(BufferedImage bi, int w, int h) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setPaint(Color.white);//getBackground());
        g2.fillRect(0, 0, w, h);
        g2.drawImage(bi, 0, 0, w, h, null);//this);
        g2.dispose();
        return image;
    }

    public static BufferedImage scaleImage(BufferedImage bi, double scale) {
        int w1 = (int) (Math.round(scale * bi.getWidth()));
        int h1 = (int) (Math.round(scale * bi.getHeight()));
        BufferedImage image = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, w1, h1);
        g2.drawImage(bi, 0, 0, w1, h1, null);//this);
        g2.dispose();
        return image;
    }

    public static BufferedImage cropImage(BufferedImage bi, int x, int y, int w, int h) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, w, h);
        g2.drawImage(bi, -x, -y, null);//this);
        g2.dispose();
        return image;
    }

    public static BufferedImage tileImage(BufferedImage im, int width, int height) {
        GraphicsConfiguration gc
                = GraphicsEnvironment.getLocalGraphicsEnvironment().
                        getDefaultScreenDevice().getDefaultConfiguration();
        int transparency = Transparency.OPAQUE;//Transparency.BITMASK;
        BufferedImage compatible = gc.createCompatibleImage(width, height, transparency);
        Graphics2D g = (Graphics2D) compatible.getGraphics();
        g.setPaint(new TexturePaint(im, new Rectangle(0, 0, im.getWidth(), im.getHeight())));
        g.fillRect(0, 0, width, height);
        return compatible;
    }

    public static BufferedImage rotateImage(BufferedImage bi) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        BufferedImage image = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setPaint(Color.white);//getBackground());
        g2.fillRect(0, 0, h, w);
        g2.rotate(90 * Math.PI / 180);
        g2.drawImage(bi, 0, -h, w, h, null);//this);
        g2.dispose();
        return image;
    }

    public static void storeImage(BufferedImage image, String s) {
        String ext;
        File f;
        try {
            ext = s.substring(s.lastIndexOf(".") + 1);
            f = new File(s);
            f.mkdirs();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        if (!ext.equals("gif") && !ext.equals("jpg") && !ext.equals("png")) {
            System.out.println("Cannot write to file: Illegal extension " + ext);
            return;
        }
        try {
            ImageIO.write(image, ext, f);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //---------------------------------------------------------
    // Sound System:
    public static Clip loadClip(String fnm) {
        Clip clip = null;
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                    new File(fnm));
            AudioFormat format = stream.getFormat();

            if ((format.getEncoding() == AudioFormat.Encoding.ULAW)
                    || (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
                AudioFormat newFormat
                        = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                format.getSampleRate(),
                                format.getSampleSizeInBits() * 2,
                                format.getChannels(),
                                format.getFrameSize() * 2,
                                format.getFrameRate(), true);  // big endian
                stream = AudioSystem.getAudioInputStream(newFormat, stream);
                //System.out.println("Converted Audio format: " + newFormat);
                format = newFormat;
            }

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // make sure sound system supports data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Unsupported Clip File: " + fnm);
                return null;
            }
            // get clip line resource
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);    // open the sound file as a clip
            stream.close(); // we're done with the input stream     
            // duration (in secs) of the clip
            double duration = clip.getMicrosecondLength() / 1000000.0;  // new
            if (duration <= 1.0) {
                System.out.println("WARNING. Duration <= 1 sec : " + duration + " secs");
                System.out.println("         The clip in " + fnm
                        + " may not play in J2SE 1.5 -- make it longer");
            }
            //else
            //  System.out.println(fnm + ": Duration: " + duration + " secs");
        } // end of try block
        catch (UnsupportedAudioFileException audioException) {
            System.out.println("Unsupported audio file: " + fnm);
        } catch (LineUnavailableException noLineException) {
            System.out.println("No audio line available for : " + fnm);
        } catch (IOException ioException) {
            System.out.println("Could not read: " + fnm);
        } catch (Exception e) {
            System.out.println("Problem with " + fnm);
        }
        return clip;
    } // end of loadClip()

    public static Clip loadClip(URL url) {
        Clip clip = null;
        String fnm = "" + url;
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                    url);
            AudioFormat format = stream.getFormat();

            if ((format.getEncoding() == AudioFormat.Encoding.ULAW)
                    || (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
                AudioFormat newFormat
                        = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                format.getSampleRate(),
                                format.getSampleSizeInBits() * 2,
                                format.getChannels(),
                                format.getFrameSize() * 2,
                                format.getFrameRate(), true);  // big endian
                stream = AudioSystem.getAudioInputStream(newFormat, stream);
                //System.out.println("Converted Audio format: " + newFormat);
                format = newFormat;
            }

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // make sure sound system supports data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Unsupported Clip File: " + fnm);
                return null;
            }
            // get clip line resource
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);    // open the sound file as a clip
            stream.close(); // we're done with the input stream     
            // duration (in secs) of the clip
            double duration = clip.getMicrosecondLength() / 1000000.0;  // new
            if (duration <= 1.0) {
                System.out.println("WARNING. Duration <= 1 sec : " + duration + " secs");
                System.out.println("         The clip in " + fnm
                        + " may not play in J2SE 1.5 -- make it longer");
            }
            //else
            //  System.out.println(fnm + ": Duration: " + duration + " secs");
        } // end of try block
        catch (UnsupportedAudioFileException audioException) {
            System.out.println("Unsupported audio file: " + fnm);
        } catch (LineUnavailableException noLineException) {
            System.out.println("No audio line available for : " + fnm);
        } catch (IOException ioException) {
            System.out.println("Could not read: " + fnm);
        } catch (Exception e) {
            System.out.println("Problem with " + fnm);
        }
        return clip;
    } // end of loadClip()

    public static void stopClip(Clip clip) {
        if (clip != null) {
            clip.stop();
            //clip.close();
        }
    }

    public static void playClip(Clip clip) {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void loopClip(Clip clip, int n) {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.loop(n);
        }
    }

    public static int clipLength(Clip clip) {
        if (clip != null) {
            return (int) (clip.getMicrosecondLength() / 1000);
        }
        return 0;
    }

}

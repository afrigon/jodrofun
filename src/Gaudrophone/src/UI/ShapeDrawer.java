/*
 * The MIT License
 *
 * Copyright 2017 Alexandre.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package UI;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.TexturePaint;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.util.List;
import Instrument.KeyState;

/**
 *
 * @author Alexandre
 */
public class ShapeDrawer {
    //Construtor
    public ShapeDrawer() {}
    
    //Draw a single shape on the graphic object
    public static void drawShape(Graphics2D g2, DrawableShape shape) {
        try {
            //Variables
            int keyState = shape.getKey().getStates();
            Rectangle2D boundingBox = shape.getShape().getBounds2D();
            
            //Check if it's clicked :
            //  TRUE use sunken appearence (if image is set, use it, otherwise, use the color)
            //  FALSE use idle appearence (same here)
            if((keyState & KeyState.clicked.getValue()) != 0) {
                BufferedImage img = shape.getKey().getShape().getSunkenAppearance().getImage();
                if(img != null)
                    g2.setPaint(new TexturePaint(img, new Rectangle2D.Double(boundingBox.getX(), boundingBox.getY(), img.getWidth(), img.getHeight())));
                else
                    g2.setColor(shape.getKey().getShape().getSunkenAppearance().getColor());
            }
            else {
                BufferedImage img = shape.getKey().getShape().getIdleAppearance().getImage();
                if(img != null)
                    g2.setPaint(new TexturePaint(img, new Rectangle2D.Double(boundingBox.getX(), boundingBox.getY(), img.getWidth(), img.getHeight())));
                else
                    g2.setColor(shape.getKey().getShape().getIdleAppearance().getColor());
            }
            
            //Draw the shape
            g2.fill(shape.getShape());
            
            //Draw each border lines
            drawLines(g2, shape.getLines());
            
            //Draw the key information (name, etc.)
            drawText(g2, shape);
            
            //If the key is hovered, add a white mask
            if((keyState & KeyState.hover.getValue()) != 0) {
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fill(shape.getShape());
            }
        }
        catch (Exception ex) {
            System.out.println("ShapeDrawer.drawShape : " + ex.getMessage());
        }
    }
    
    //Draw mutliple shapes on a size X canvas
    public static void drawShapes(Graphics2D g2, List<DrawableShape> shapes, Rectangle2D canvasSize) {
        try {
            //Get a clip of the size of the canvas (for the search mask)
            Area clip = new Area(canvasSize);
            //True if something is searched (at least one key is valid)
            boolean searching = false;
            
            //Draw all shapes using the drawShape function
            for(DrawableShape s : shapes) {
                drawShape(g2, s);
                //Check if the key have the search flag
                if((s.getKey().getStates() & KeyState.searched.getValue()) != 0) {
                    //Remove the shape from the mask and place searching to TRUE
                    clip.subtract(new Area(s.getShape()));
                    searching = true;
                }
            }
            //Place the black mask if searching
            if(searching) {
                g2.clip(clip);
                g2.setColor(new Color(0, 0, 0, 60));
                g2.fill(canvasSize);
            }
        }
        catch (Exception ex) {
            System.out.println("ShapeDrawer.drawShapes : " + ex.getMessage());
        }
    }
    
    //Draw border lines of a shape
    private static void drawLines(Graphics2D g2, List<DrawableLine> lines) {
        try {
            //First corner of all the shape, and the last corner used
            Point2D corner = null, firstCorner = null;
            //If there is at least three lines, well, we might be able to do something
            if(lines.size() > 2) {
                //VARIABLES
                DrawableLine first = lines.get(0), last = lines.get(lines.size() - 1),
                        current = lines.get(0), next = null;
                
                //For each lines, make a polygon using the first line and a second line using the thickness.
                //Connect it with the previews and next line.
                for(int i = 0; i < lines.size(); ++i) {
                    //Polygon containing the line shape
                    java.awt.Polygon p = new java.awt.Polygon();
                    //Add the first line, directly on the shape
                    p.addPoint((int)current.getLine().getX1(), (int)current.getLine().getY1());
                    p.addPoint((int)current.getLine().getX2(), (int)current.getLine().getY2());
                    
                    //If it's the last, use the past corner and the first one.
                    //No calculation needed
                    if(current == last) {
                        p.addPoint((int)firstCorner.getX(), (int)firstCorner.getY());
                        p.addPoint((int)corner.getX(), (int)corner.getY());
                    }
                    else {
                        next = lines.get(i + 1);
                        //If it's the first line, find the corner with the last one AND the with the next one
                        if(current == first) {
                            Line2D.Double l = getExtendedLine(getLineWithThickness(current.getLine(), current.getThickness()));
                            firstCorner = getIntersection(
                                    l,
                                    getExtendedLine(getLineWithThickness(last.getLine(), last.getThickness())));
                            corner = getIntersection(
                                    l,
                                    getExtendedLine(getLineWithThickness(next.getLine(), next.getThickness())));
                            
                            if(firstCorner == null)
                                throw new NullPointerException("drawLines : firstCorner is undefined. Probably no intersection between the lines in arguments.");
                            if(corner == null)
                                throw new NullPointerException("drawLines : corner is undefined. Probably no intersection between the lines in arguments.");
                            
                            p.addPoint((int)corner.getX(), (int)corner.getY());
                            p.addPoint((int)firstCorner.getX(), (int)firstCorner.getY());
                        }
                        //It's not the last line and not the first, since we have access to the past corner, just find the next corner and draw
                        else {
                            Point2D nextCorner = getIntersection(
                                    getExtendedLine(getLineWithThickness(current.getLine(), current.getThickness())),
                                    getExtendedLine(getLineWithThickness(next.getLine(), next.getThickness())));
                            
                            if(nextCorner == null)
                                throw new NullPointerException("drawLines : nextCorner is undefined. Probably no intersection between the lines in arguments.");

                            p.addPoint((int)nextCorner.getX(), (int)nextCorner.getY());
                            p.addPoint((int)corner.getX(), (int)corner.getY());
                            //Past corner is now the next one. Iterate now.
                            corner = nextCorner;
                        }
                    }
                    
                    //Paint the line using his color
                    g2.setPaint(current.getColor());
                    g2.fill(p);
                    //Current line is the next one. Iterate now.
                    current = next;
                }
            }  
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //Draw the key information (name, etc.)
    private static void drawText(Graphics2D g2, DrawableShape shape) {
        if(g2 == null)
            throw new java.lang.IllegalArgumentException("drawText : g2 is undefined.");
        if(shape == null)
            throw new java.lang.IllegalArgumentException("drawText : shape is undefined.");
        
        //VARIABLES
        int keyState = shape.getKey().getStates();
        Rectangle2D boundingBox = shape.getShape().getBounds2D();
        String text = "";
        
        //Find the flags and if they are set, add the information
        if((keyState & KeyState.displayName.getValue()) != 0)
            text += "\n" +shape.getKey().getName();
        if((keyState & KeyState.displayNote.getValue()) != 0)
            text += "\n" +shape.getKey().getNote();
        if((keyState & KeyState.displayAlteration.getValue()) != 0)
            text += "\n" +shape.getKey().getAlteration();
        if((keyState & KeyState.displayOctave.getValue()) != 0)
            text += "\n" +shape.getKey().getOctave();
        
        //If there is something (at least one flag)
        if(!"".equals(text)) {
            //Create an array using the \n char (not handled by normal drawstring)
            String[] lines = text.split("\n");
            //Font used using a static size (for the time being)
            Font font = new Font("Verdana", Font.PLAIN, 12);
            g2.setFont(font);
            FontRenderContext frc = g2.getFontRenderContext();
            //Get the size of the text (the boundingbox)
            Rectangle2D boundsText = font.getStringBounds(text, frc);
            //Find the position of the first line in the shape (try to center it)
            int posY = (int)(boundingBox.getY() + (boundingBox.getHeight() - boundsText.getHeight() * (lines.length - 1)) / 2);
            g2.setColor(Color.BLACK);
            //Draw the strings
            for (String line : lines) {
                if(!"".equals(line)) {
                    Rectangle2D boundsLine = font.getStringBounds(line, frc);
                    //Find the position of the line on the X axis (to make each line centered)
                    int posX = (int)(boundingBox.getX() + (boundingBox.getWidth() - boundsLine.getWidth()) / 2);
                    g2.drawString(line, posX, posY += g2.getFontMetrics().getHeight());
                }
            }
        }
    }
    
    //Get the line with the thickness applied (second line)
    private static Line2D.Double getLineWithThickness(Line2D.Double line, int thickness) {
        if (line == null) throw new java.lang.IllegalArgumentException("getLineWithThickness : Line argument is null.");
        else if (thickness == 0) return line;
        
        //Angle of the line
        double angle = Math.atan2(
            line.getY2() - line.getY1(),
            line.getX2() - line.getX1());
        //Find the x and y thickness multipliers according to the angle
        double xThickness = angle / Math.toRadians(90),
                yThickness = (Math.abs(angle) - Math.toRadians(90)) / Math.toRadians(90);
        if(xThickness > 1) xThickness = 2 - xThickness;
        if(xThickness < -1) xThickness = -2 + xThickness;
        
        //Create and return a new line using the thickness multipliers
        return new Line2D.Double(
                line.getX1() + Math.ceil(xThickness * thickness),
                line.getY1() + Math.ceil(yThickness * thickness),
                line.getX2() + Math.ceil(xThickness * thickness),
                line.getY2() + Math.ceil(yThickness * thickness));
    }
    
    //Get a close to infinity extended line
    private static Line2D.Double getExtendedLine(Line2D.Double line) {
        if (line == null) throw new java.lang.IllegalArgumentException("getExtendedLine : Line argument is null.");
        
        //Line is vertical, just return a big line
        if (line.getX2() == line.getX1()) {
            return new Line2D.Double(
                line.getX1(),
                -100000,
                line.getX2(),
                100000);
        }
        //Find the linear equation
        double a, b;
        a = (line.getY2() - line.getY1()) / (line.getX2() - line.getX1());
        b = line.getY1() - a * line.getX1();
        
        //Return a new line using the equation and a big x value
        return new Line2D.Double(
        line.getX1() - 10000,
        a * (line.getX1() - 10000) + b,
        line.getX2() + 10000,
        a * (line.getX2() + 10000) + b);
    }
    
    //Find the intersection point of two lines
    private static Point2D getIntersection(Line2D.Double line1, Line2D.Double line2) {
        //They don't collide, well shit.
        if(!line1.intersectsLine(line2)) return null;
        
        //VARIABLES
        double a1, a2, b1, b2, x, y;
        //a = variation
        //b = value of Y when X=0
        a1 = line1.getX2() == line1.getX1() ? 0 : (line1.getY2() - line1.getY1()) / (line1.getX2() - line1.getX1());
        a2 = line2.getX2() == line2.getX1() ? 0 : (line2.getY2() - line2.getY1()) / (line2.getX2() - line2.getX1());
        b1 = line1.getY1() - a1 * line1.getX1();
        b2 = line2.getY1() - a2 * line2.getX1();
        
        //If line 1 is vertical, just find when line2.x = line1.x
        if(line1.getX2() == line1.getX1()) {
            x = line1.getX1();
            y = a2 * x + b2;
        }
        //If line 2 is vertical, just find when line1.x = line2.x
        else if(line2.getX2() == line2.getX1()) {
            x = line2.getX1();
            y = a1 * x + b1;
        }
        //ax+b=mx+c
        //ax-mx=c-b
        //x(a-m)=c-b
        //x=(c-b)/(a-m)
        //therefor, x = b2-b1 / a1-a2
        //To find y, just replace x with in the equation with found value
        //Black magic right there : that's the intersection point
        //I didn't used cross-vectors calculs, we haven't reached that part in class yet...
        else {
            x = (b2 - b1) / (a1 - a2);
            y = a1 * x + b1;
        }
        return new Point2D.Double(x, y);
    }
}
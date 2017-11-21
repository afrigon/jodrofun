/*
 * The MIT License
 *
 * Copyright 2017 frigon.
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

import Manager.CanvasManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.util.List;
import java.lang.Math;

public class Canvas extends javax.swing.JPanel {
    
    CanvasManager manager;
    
    public Canvas(CanvasManager p_manager) {
        
        manager = p_manager;
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                manager.clicked(e.getX(), e.getY());
            }
            
            public void mouseReleased(java.awt.event.MouseEvent e) {
                manager.released(e.getX(), e.getY());
            }
        });
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                manager.dragged(e.getX(), e.getY());
            }
        });
        
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                
            }
            
            public void keyReleased(java.awt.event.KeyEvent e) {
                
            }
        });
        
        /*addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                //Il appelle par défaut invalidate, est-il donc vraiment nécessaire?
            }
        });*/
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //g2 > g en terme de performance
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        
        List<DrawableShape> shapes = manager.getDrawableShapes();
        if (shapes == null) return;
        /*List<DrawableShape> shapes = new java.util.ArrayList<>();
        for(int i = 0; i < 1; ++i) {
            Instrument.Key key = new Instrument.Key(new Music.SynthesizedSound(), new KeyUtils.RectangleKeyShape().generate(), "Test" + i);
            GeneralPath.Double path = new GeneralPath.Double();
            path.moveTo(10+i * 100, 100);
            path.lineTo(110 + i*100, 100);
            path.lineTo(110 + i*100, 200);
            path.lineTo(10+i*100, 200);
            path.closePath();
            DrawableShape shape = new DrawableShape(key);
            getLineWithThickness(new Line2D.Double(10+i*100, 200, 10+i*100, 100), 5);
            shape.setShape(path);
            List<DrawableLine> lines = new java.util.ArrayList<>();
            DrawableLine line = new DrawableLine();
            line.setColor(Color.BLACK);
            line.setThickness(25);
            line.setLine(new Line2D.Double(10+i*100, 100, 110+i*100, 100));
            lines.add(line);
            line = new DrawableLine();
            line.setColor(Color.BLUE);
            line.setThickness(5);
            line.setLine(new Line2D.Double(110+i*100, 100, 110+i*100, 200));
            lines.add(line);
            line = new DrawableLine();
            line.setColor(Color.RED);
            line.setThickness(5);
            line.setLine(new Line2D.Double(110+i*100, 200, 10+i*100, 200));
            lines.add(line);
            line = new DrawableLine();
            line.setColor(Color.ORANGE);
            line.setThickness(5);
            line.setLine(new Line2D.Double(10+i*100, 200, 10+i*100, 100));
            lines.add(line);
            
            shape.setLines(lines);
            shapes.add(shape);
        }*/
        
        for(DrawableShape s : shapes)
        {
            //g2.setClip(s.getShape());
            g2.setPaint(s.getKey().getShape().getIdleAppearance().getColor());
            g2.fill(s.getShape());
            /*for(DrawableLine l : s.getLines()) {
                g2.setPaint(l.getColor());
                g2.setStroke(new java.awt.BasicStroke(l.getThickness(),java.awt.BasicStroke.CAP_BUTT,java.awt.BasicStroke.JOIN_BEVEL));
                g2.draw(l.getLine());
            }*/
            Point2D corner = null, firstCorner = null;
            java.awt.Polygon pClip = new java.awt.Polygon();
            List<DrawableLine> lines = s.getLines();
            if(lines.size() > 1) {
                DrawableLine first = lines.get(0), last = lines.get(lines.size() - 1),
                        current = lines.get(0), next = null;
                for(int i = 0; i < lines.size(); ++i) {
                    java.awt.Polygon p = new java.awt.Polygon();
                    p.addPoint((int)current.getLine().getX1(), (int)current.getLine().getY1());
                    p.addPoint((int)current.getLine().getX2(), (int)current.getLine().getY2());
                    
                    if(current == last) {
                        p.addPoint((int)firstCorner.getX(), (int)firstCorner.getY());
                        p.addPoint((int)corner.getX(), (int)corner.getY());
                    }
                    else {
                        next = lines.get(i + 1);
                        if(current == first) {
                            Line2D.Double l = getExtendedLine(getLineWithThickness(current.getLine(), current.getThickness()));
                            firstCorner = getIntersection(
                                    l,
                                    getExtendedLine(getLineWithThickness(last.getLine(), last.getThickness())));
                            corner = getIntersection(
                                    l,
                                    getExtendedLine(getLineWithThickness(next.getLine(), next.getThickness())));
                            p.addPoint((int)corner.getX(), (int)corner.getY());
                            p.addPoint((int)firstCorner.getX(), (int)firstCorner.getY());
                            
                            pClip.addPoint((int)l.getX1(), (int)l.getY1());
                            pClip.addPoint((int)l.getX2(), (int)l.getY2());
                        }
                        else {
                            Point2D nextCorner = getIntersection(
                                    getExtendedLine(getLineWithThickness(current.getLine(), current.getThickness())),
                                    getExtendedLine(getLineWithThickness(next.getLine(), next.getThickness())));
                            
                            p.addPoint((int)nextCorner.getX(), (int)nextCorner.getY());
                            p.addPoint((int)corner.getX(), (int)corner.getY());
                            
                            corner = nextCorner;
                        }
                    }
                    
                    g2.setPaint(current.getColor());
                    g2.fill(p);
                    current = next;
                }
            }
        }
    }
    
    private Line2D.Double getLineWithThickness(Line2D.Double line, double thickness) {
        if (line == null) return null;
        else if (thickness == 0) return line;
        
        double angle = Math.atan2(
            line.getY2() - line.getY1(),
            line.getX2() - line.getX1());
        double xThickness = angle / Math.toRadians(90),
                yThickness = (Math.abs(angle) - Math.toRadians(90)) / Math.toRadians(90);
        if(xThickness > 1) xThickness = 2 - xThickness;
        if(xThickness < -1) xThickness = -2 + xThickness;
        return new Line2D.Double(
                line.getX1() + Math.ceil(xThickness * thickness),
                line.getY1() + Math.ceil(yThickness * thickness),
                line.getX2() + Math.ceil(xThickness * thickness),
                line.getY2() + Math.ceil(yThickness * thickness));
    }
    
    private Line2D.Double getExtendedLine(Line2D.Double line) {
        if (line.getX2() == line.getX1()) {
            return new Line2D.Double(
                line.getX1(),
                -1000000,
                line.getX2(),
                1000000);
        }
        double a, b;
        a = line.getX2() == line.getX1() ? 0 : (line.getY2() - line.getY1()) / (line.getX2() - line.getX1());
        b = line.getY1() - a * line.getX1();
        
        return new Line2D.Double(
        line.getX1() - 10000,
        a * (line.getX1() - 10000) + b,
        line.getX2() + 10000,
        a * (line.getX2() + 10000) + b);
    }
    
    private Point2D getIntersection(Line2D.Double line1, Line2D.Double line2) {
        if(!line1.intersectsLine(line2)) return null;
        
        double a1, a2, b1, b2, x, y;
        
        a1 = line1.getX2() == line1.getX1() ? 0 : (line1.getY2() - line1.getY1()) / (line1.getX2() - line1.getX1());
        a2 = line2.getX2() == line2.getX1() ? 0 : (line2.getY2() - line2.getY1()) / (line2.getX2() - line2.getX1());
        b1 = line1.getY1() - a1 * line1.getX1();
        b2 = line2.getY1() - a2 * line2.getX1();
        
        if(line1.getX2() == line1.getX1()) {
            x = line1.getX1();
            y = a2 * x + b2;
        }
        else if(line2.getX2() == line2.getX1()) {
            x = line2.getX1();
            y = a1 * x + b1;
        }
        else {
            x = (b2 - b1) / (a1 - a2);
            y = a1 * x + b1;
        }
        return new Point2D.Double(x, y);
    }
}
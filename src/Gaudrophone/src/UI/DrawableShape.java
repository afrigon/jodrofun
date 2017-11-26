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

import Instrument.Key;
import KeyUtils.KeyLine;
import KeyUtils.Vector2;
import Manager.GaudrophoneController;
import java.awt.geom.GeneralPath;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

public class DrawableShape {
    
    private Key key;
    private GeneralPath shape;
    private List<DrawableLine> lines;
    private static List<Ellipse2D> drawableDot = new LinkedList<>();
    
    public DrawableShape (Key key) {
        this.key = key;
        this.lines = new LinkedList<>();
        this.shape = new GeneralPath();
        this.generatePath();
    }
    
    public boolean checkClick(int x, int y) { return shape.contains(x, y); }
    
    public Key getKey() { return this.key; }
    public GeneralPath getShape() { return this.shape; }
    public List<DrawableLine> getLines() { return this.lines; }
    public static List<Ellipse2D> getDot() { return drawableDot; }
    
    public void setShape(GeneralPath p_shape) { this.shape = p_shape; }
    public void setLines(List<DrawableLine> lines) { this.lines = lines; }
    
    public void remakeShape() {
        this.lines = new LinkedList<>();
        this.shape = new GeneralPath();
        this.generatePath();
    }
    
    public void setDots() {
        drawableDot = new LinkedList<>();
        
        int size = 4;
        List<Vector2> points = this.key.getShape().getPoints();
        for(int i = 0; i < points.size(); ++i) {
            KeyUtils.Vector2 v = GaudrophoneController.getController().getCanvasManager().convertWorldToPixel(points.get(i));
            drawableDot.add(new Ellipse2D.Double(v.getX() - size, v.getY() - size, size * 2, size * 2));
        }
    }
    
    private void generatePath() {
        List<Vector2> points = this.key.getShape().getPoints();
        List<KeyLine> keyLines = this.key.getShape().getLines();
        
        if (points.size() > 1) {
            Vector2 first = GaudrophoneController.getController().getCanvasManager().convertWorldToPixel(points.get(0));
            this.shape.moveTo(first.getX(), first.getY());
            for (int i = 1; i < points.size(); i++) {
                Vector2 previous = GaudrophoneController.getController().getCanvasManager().convertWorldToPixel(points.get(i-1));
                Vector2 next = GaudrophoneController.getController().getCanvasManager().convertWorldToPixel(points.get(i));
                
                this.shape.lineTo(next.getX(), next.getY());
                
                DrawableLine dl = new DrawableLine();
                dl.setLine(new Line2D.Double(previous.getX(), previous.getY(), next.getX(), next.getY()));
                dl.setKeyLine(keyLines.get(i-1));
                dl.setThickness(GaudrophoneController.getController().getCanvasManager().convertThicknessToPixel(keyLines.get(i-1).getThickness()));
                this.lines.add(dl);
            }
            
            Vector2 last = GaudrophoneController.getController().getCanvasManager().convertWorldToPixel(points.get(points.size()-1));
            this.shape.lineTo(last.getX(), last.getY());
         
            DrawableLine dl = new DrawableLine();
            dl.setLine(new Line2D.Double(last.getX(), last.getY(), first.getX(), first.getY()));
            dl.setKeyLine(keyLines.get(keyLines.size()-1));
            dl.setThickness(GaudrophoneController.getController().getCanvasManager().convertThicknessToPixel(keyLines.get(keyLines.size()-1).getThickness()));
            this.lines.add(dl);
        }
    }
}

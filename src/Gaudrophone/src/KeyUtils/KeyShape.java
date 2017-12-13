/*
 * The MIT License
 *
 * Copyright 2017 Olivier.
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
package KeyUtils;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.awt.geom.Line2D;

public class KeyShape implements java.io.Serializable {
    private List<Vector2> points = null;
    private List<KeyLine> lines = null;
    private final KeyLine[] crossLines = new KeyLine[4];
    
    private ShapeAppearance idleAppearance = null;
    private ShapeAppearance clickedAppearance = null;
    
    // Constructors
    public KeyShape(List<Vector2> pointsList, Color color) {
        points = pointsList;
        lines = new LinkedList<>();
        for (int i = 0; i < points.size(); i++) {
            lines.add(new KeyLine(0.5, new Color(51, 51, 51)));
        }
        for(int i = 0; i < this.crossLines.length; ++i) {
            this.crossLines[i] = new KeyLine(new Color(255, 255, 255, 0));
        }
        this.idleAppearance = new ShapeAppearance(color);
        this.clickedAppearance = new ShapeAppearance(color.darker());
    }
    
    public KeyShape(List<Vector2> pointsList, Color color, Color clickedColor) {
        points = pointsList;
        lines = new LinkedList<>();
        for (int i = 0; i < points.size(); i++) {
            lines.add(new KeyLine(0.5, new Color(51, 51, 51)));
        }
        for(int i = 0; i < this.crossLines.length; ++i) {
            this.crossLines[i] = new KeyLine(new Color(255, 255, 255, 0));
        }
        this.idleAppearance = new ShapeAppearance(color);
        this.clickedAppearance = new ShapeAppearance(clickedColor);
    }
    
    public void addPoint(int index) {
        if(index >= 0 && index < points.size()) {
            Vector2 p0 = points.get(index++), p1, newP;

            if(index == points.size()) {
                index = 0;
            }
            p1 = points.get(index);

            newP = new Vector2((p0.getX() + p1.getX()) / 2, (p0.getY() + p1.getY()) / 2);
            points.add(index, newP);
            lines.add(index, new KeyLine());
        }
    }
    
    public ShapeAppearance getIdleAppearance() {
        return this.idleAppearance;
    }
    
    public ShapeAppearance getSunkenAppearance() {
        return this.clickedAppearance;
    }
    
    public List<Vector2> getPoints() {
        return this.points;
    }
    
    public List<KeyLine> getLines() {
        return this.lines;
    }
    
    public KeyLine[] getCrossLines() {
        return this.crossLines;
    }
    
    public void setLines(List<KeyLine> lines) {
        this.lines = lines;
    }
    
    public void setCrossLineColor(Color newColor, CrossLine crossLine) {
        this.crossLines[crossLine.getValue()].setColor(newColor);
    }
    
    public void setCrossLineThickness(double newThickness, CrossLine crossLine) {
        this.crossLines[crossLine.getValue()].setThickness(newThickness);
    }
    
    // Methods
    private double getMaxBound(boolean isX) {
        double max = -1;
        for(Vector2 point : points) max = Math.max(max, isX ? point.getX() : point.getY());
        return max;
    }
    private double getMinBound(boolean isX) {
        double min = Double.MAX_VALUE;
        for(Vector2 point : points) min = Math.min(min, isX ? point.getX() : point.getY());
        return min;
    }
    
    public Vector2 getCorner(Corner corner) {
        if (null != corner) {
            switch (corner) {
                case topLeft:
                    return new Vector2(getMinBound(true), getMinBound(false));
                case topCenter:
                    return new Vector2((getMaxBound(true) + getMinBound(true)) / 2, getMinBound(false));
                case topRight:
                    return new Vector2(getMaxBound(true), getMinBound(false));
                case centerLeft:
                    return new Vector2(getMinBound(true), (getMaxBound(false) + getMinBound(false)) / 2);
                case center:
                    return new Vector2((getMaxBound(true) + getMinBound(true)) / 2, (getMaxBound(false) + getMinBound(false)) / 2);
                case centerRight:
                    return new Vector2(getMaxBound(true), (getMaxBound(false) + getMinBound(false)) / 2);
                case bottomLeft:
                    return new Vector2(getMinBound(true), getMaxBound(false));
                case bottomCenter:
                    return new Vector2((getMaxBound(true) + getMinBound(true)) / 2, getMaxBound(false));
                case bottomRight:
                    return new Vector2(getMaxBound(true), getMaxBound(false));
            }
        }
        
        return null;
    }
    
    public Vector2 getSize() {
        Vector2 signedSize = getCorner(Corner.topRight).sub(getCorner(Corner.bottomLeft));
        return new Vector2(Math.abs(signedSize.getX()), Math.abs(signedSize.getY()));
    }
    
    public void translate(Vector2 translation) {
        for(int i = 0; i < points.size(); ++i) {
            Vector2 point = points.get(i);
            points.set(i, point.add(translation));
        }
    }
    
    public void setPosition(Vector2 position, Corner corner) {
        Vector2 actualPosition = getCorner(corner);
        translate(position.sub(actualPosition));
    }
    
    public void rotate(Vector2 anchorPoint, double angle) {
        for (Vector2 point : points) {
            point = point.rotate(anchorPoint, angle);
        }
    }
    
    public Vector2 getDistantPoint(Vector2 vector) {
        Vector2 unitVector = vector.unit();
        double distantPointProduct = points.get(0).dotProduct(unitVector);
        Vector2 distantPoint = points.get(0);
        
        for (Vector2 point : points) {
            double product = point.dotProduct(unitVector);
            if (product > distantPointProduct) {
                distantPointProduct = product;
                distantPoint = point;
            }
        }
        return distantPoint;
    }
    
    public void stretch(Vector2 delta) {
        Vector2 distantPoint = getDistantPoint(delta);
        Vector2 closestPoint = getDistantPoint(delta.negate());
        Vector2 size = distantPoint.sub(closestPoint);
        double scale = delta.length()/size.length();
        Vector2 unitDelta = delta.unit();
        
        for(int i = 0; i < points.size(); ++i) {
            Vector2 point = points.get(i);
            if (point != distantPoint) {
                double product = point.dotProduct(unitDelta);
                points.set(i, point.add(unitDelta.multiply(product * scale)));    
            }
        }
    }
    
    public void setSize(Vector2 newSize, Corner fixCorner) {
        Vector2 position = getCorner(fixCorner);
        
        Vector2 currentSize = getSize();
        double resizeX = newSize.getX() / currentSize.getX();
        double resizeY = newSize.getY() / currentSize.getY();
        
        Vector2 reference = getCorner(Corner.topLeft);
        
        for(int i = 0; i < points.size(); ++i) {
            Vector2 point = points.get(i).sub(reference);
            points.set(i, new Vector2(point.getX() * resizeX + reference.getX(), point.getY() * resizeY + reference.getY()));
        }
        
        setPosition(position, fixCorner);
    }
}

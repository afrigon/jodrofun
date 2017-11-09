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

import java.util.List;

/**
 *
 * @author Olivier
 */
public class KeyShape {
    private List<Vector2> points = null;
    
    private ShapeAppearance idleAppearance = null;
    private ShapeAppearance clickedAppearance = null;
    
    public static enum Corner {
        TopLeft, TopCenter, TopRight,
        CenterLeft, Center, CenterRight, 
        BottomLeft, BottomCenter, BottomRight
    }
    
    // Constructors
    public KeyShape(List<Vector2> pointsList) {
        points = pointsList;
        idleAppearance = new ShapeAppearance();
        clickedAppearance = new ShapeAppearance();
    }
    
    // Methods
    private double getMaxBound(boolean isX) {
        double max = isX ? points.get(0).getX() : points.get(0).getY();
        for (Vector2 point : points) {
            double number = isX ? point.getX() : point.getY();
            if (number > max)
                max = number;
        }
        return max;
    }
    private double getMinBound(boolean isX) {
        double min = isX ? points.get(0).getX() : points.get(0).getY();
        for (Vector2 point : points) {
            double number = isX ? point.getX() : point.getY();
            if (number < min)
                min = number;
        }
        return min;
    }
    
    public Vector2 getCorner(Corner corner) {
        if (null != corner) switch (corner) {
            case TopLeft:
                return new Vector2(getMinBound(true), getMaxBound(false));
            case TopCenter:
                return new Vector2((getMaxBound(true) + getMinBound(true)) / 2, getMaxBound(false));
            case TopRight:
                return new Vector2(getMaxBound(true), getMaxBound(false));
            case CenterLeft:
                return new Vector2(getMinBound(true), (getMaxBound(false) + getMinBound(false)) / 2);
            case Center:
                return new Vector2((getMaxBound(true) + getMinBound(true)) / 2, (getMaxBound(false) + getMinBound(false)) / 2);
            case CenterRight:
                return new Vector2(getMaxBound(true), (getMaxBound(false) + getMinBound(false)) / 2);
            case BottomLeft:
                return new Vector2(getMinBound(true), getMinBound(false));
            case BottomCenter:
                return new Vector2((getMaxBound(true) + getMinBound(true)) / 2, getMinBound(false));
            case BottomRight:
                return new Vector2(getMaxBound(true), getMinBound(false));
            default:
                break; // or throw error ?
        }
        return new Vector2(); // 
    }
    
    public Vector2 getSize() {
        return getCorner(Corner.TopRight).sub(getCorner(Corner.BottomLeft));
    }
    
    public void translate(Vector2 translation) {
        for (Vector2 point : points) {
            point = point.add(translation);
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
    
    public void strech(Vector2 delta) {
        Vector2 distantPoint = getDistantPoint(delta);
        Vector2 closestPoint = getDistantPoint(delta.negate());
        Vector2 size = distantPoint.sub(closestPoint);
        double scale = delta.length()/size.length();
        Vector2 unitDelta = delta.unit();
        
        for (Vector2 point : points) {
            if (point != distantPoint) {
                double product = point.dotProduct(unitDelta);
                point = point.add(unitDelta.multiply(product * scale));
            }
        }
    }
    
}

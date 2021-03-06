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

public class KeyLine implements java.io.Serializable {
    private Color lineColor;
    private double lineThickness;
    private LineShape shape = LineShape.LINE;
    private Vector2 curvePoint = new Vector2(0, 0);
    
    // Constructors
    public KeyLine() {
        lineThickness = 1;
        lineColor = Color.BLACK;
    }
    
    public KeyLine(Color color) {
        lineThickness = 1;
        lineColor = color;
    }
    
    public KeyLine(double thickness, Color color) {
        lineThickness = thickness;
        lineColor = color;
    }
    
    // Methods
    
    public void setColor(Color newColor) {
        lineColor = newColor;
    }
    
    public void setThickness(double thickness) {
        lineThickness = thickness;
    }
    
    public Color getColor() {
        return lineColor;
    }
    
    public double getThickness() {
        return lineThickness;
    }
    
    //Curve Managment, currently not used
    public Vector2 getCurve() {
        return this.curvePoint;
    }
    
    public void setCurve(Vector2 curvePoint) {
        this.curvePoint = curvePoint;
    }
    
    public void setShape(LineShape shape, Vector2 p1, Vector2 p2) {
        this.shape = shape;
        //Reset curve point
        this.curvePoint = new Vector2((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
    }
    
    public LineShape getShape() {
        return this.shape;
    }
}

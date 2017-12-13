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

import java.awt.Color;
import KeyUtils.KeyLine;
import KeyUtils.Vector2;
import Manager.GaudrophoneController;
import java.awt.geom.Line2D;

public class DrawableLine {
    private KeyLine keyLine;
    private int lineThickness;
    private Line2D.Double line;
    private Vector2 curvePoint;
    
    public void DrawableLine() { }
    
    public Vector2 handlePosition() {
        return this.curvePoint;
    }
    public Color getColor() { return this.keyLine.getColor(); }
    public int getThickness() { return this.lineThickness; }
    public Line2D.Double getLine() { return this.line; }
    
    public void setKeyLine(KeyLine p_keyLine) {
        this.keyLine = p_keyLine;
        this.curvePoint = GaudrophoneController.getController().getCanvasManager().convertWorldToPixel(this.keyLine.getCurve());
    }
    public void setThickness(int p_thickness) { this.lineThickness = p_thickness; }
    public void setLine(Line2D.Double p_line) { this.line = p_line; }
}

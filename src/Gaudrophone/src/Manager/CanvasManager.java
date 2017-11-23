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
package Manager;

import UI.DrawableShape;
import UI.DrawableLine;
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;
import java.util.List;
import Instrument.Key;
import Instrument.Key;
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;
import UI.DrawableShape;
import java.util.LinkedList;
import java.util.List;

public class CanvasManager {
    private GaudrophoneController controller;
    private List<DrawableShape> shapes;
    private State state;
    private KeyShapeGenerator storedKeyShape;
    private Vector2 windowSize = new Vector2(0, 0);
    
    public CanvasManager(GaudrophoneController p_controller) {
        controller = p_controller;
    }
    
    public Vector2 convertPixelToWorld(int x, int y) {
       return null;
    }
    
    public Vector2 convertWorldToPixel(Vector2 vector) {
        return vector;
    }
    
    public int convertThicknessToPixel(double thickness) {
        return 10;
    }
    
    public void drawKeys(List<Key> keyList) {
        this.shapes = new LinkedList<>();
        keyList.forEach((key) -> {
            this.shapes.add(new DrawableShape(key));
        });
    }
    
    public void clicked(Key key) {
        
    }
    
    public void clicked(int x, int y) {
        System.out.println("Click");
    }
    
    public void released(int x, int y) {
        System.out.println("Release");
    }
    
    public void dragged(int x, int y) {
        System.out.println("Drag");
    }
    
    public List<DrawableShape> getDrawableShapes() {
        return shapes;
    }
    
    public State getState() { return state; }
    public void setState(State value) { state = value; }
    
    public void setWindowSize(int x, int y) {
        this.windowSize = new Vector2(x, y);
    }
}
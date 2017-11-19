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

/**
 *
 * @author frigon
 */

import Instrument.Key;
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;
import UI.Shape;
import java.util.List;

public class CanvasManager {
    private GaudrophoneController controller;
    private List<Shape> shapes;
    private State state;
    private KeyShapeGenerator storedKeyShape;
    
    public CanvasManager(GaudrophoneController p_controller) {
        controller = p_controller;
    }
    
    public Vector2 convertPixelToWorld(int x, int y) {
        return (new Vector2());
    }
    
    public int convertThickness(double thickness) {
        return (0);
    }
    
    public Vector2 convertWorldToPixel(Vector2 vector) {
        return (new Vector2());
    }
    
    public void drawKeys(List<Key> keyList) {
        
    }
    
    public void clicked(Key key) {
        
    }
    
    public void clicked(/*Key key, HandleType handleType, int linkNumber*/) {
        
    }
    
    public void clicked(int x, int y) {
        
    }
    
    public List<Shape> getShapes() {
        return (shapes);
    }
}

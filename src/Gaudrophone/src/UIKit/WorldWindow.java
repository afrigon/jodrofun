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
package UIKit;

import KeyUtils.Vector2;

/**
 *
 * @author Olivier
 */
public class WorldWindow {
    private Vector2 origin; // position in the world of the pixel (0, 0)
    private double scale; // ratio world scale / 100px
    private final int PIXEL_RATIO = 100;
    
    public WorldWindow() {
        origin = new Vector2(0, 0);
        scale = 1;
    }
    
    public Vector2 convertPixelToWorld(int x, int y) {
        return origin.add(new Vector2(((double)x) * scale / PIXEL_RATIO, -((double)y) * scale / PIXEL_RATIO));
    }
    
    public Vector2 convertPixelToWorld(Vector2 pixels) {
        return origin.add(new Vector2(pixels.getX() * scale / PIXEL_RATIO, -pixels.getY() * scale / PIXEL_RATIO));
    }
    
    public Vector2 convertWorldToPixel(Vector2 vector) {
        Vector2 displacement = vector.sub(origin);
        
        return new Vector2(displacement.getX() * 100 / scale, -displacement.getY() * 100 / scale);
    }
    
    public int convertThicknessToPixel(double thickness) {
        return (int)(thickness * PIXEL_RATIO / scale);
    }
    
    public void move(Vector2 translation) {
        origin = origin.add(translation);
    }
    
    public void zoomIn() {
        scale = scale * 0.1;
    }
    
    public void zoomIn(Vector2 focusPixel) {
        Vector2 focusWorld = convertPixelToWorld(focusPixel);
        zoomIn();
        Vector2 displacement = convertPixelToWorld(focusPixel);
        move(focusWorld.sub(displacement));
    }
    
    public void zoomOut() {
        scale = scale * 1.1;
    }
    
    public void zoomOut(Vector2 focusPixel) {
        Vector2 focusWorld = convertPixelToWorld(focusPixel);
        zoomOut();
        Vector2 displacement = convertPixelToWorld(focusPixel);
        move(focusWorld.sub(displacement));
    }
    
    
}

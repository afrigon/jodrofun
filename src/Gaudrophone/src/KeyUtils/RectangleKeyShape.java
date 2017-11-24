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

import Manager.GaudrophoneController;
import java.util.ArrayList;
import java.util.List;

public class RectangleKeyShape implements KeyShapeGenerator {

    @Override
    public KeyShape generate() {
        return generateSquare(15);
    }
    
    public KeyShape generateSquare(int squareSize) {
        double size = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld(squareSize, squareSize).getX();
        List<Vector2> pointList = new ArrayList<>();
        pointList.add(new Vector2(0, 0));
        pointList.add(new Vector2(size, 0));
        pointList.add(new Vector2(size, size));
        pointList.add(new Vector2(0, size));
        
        return new KeyShape(pointList);
    }
    
    public KeyShape generateSquare(int squareSize, Vector2 position) {
        position = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld((int)position.getX(), (int)position.getY());
        double size = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld(squareSize, squareSize).getX();
        List<Vector2> pointList = new ArrayList<>();
        pointList.add(new Vector2(position.getX(), position.getY()));
        pointList.add(new Vector2(position.getX()+ size, position.getY()));
        pointList.add(new Vector2(position.getX()+ size, position.getY() + size));
        pointList.add(new Vector2(position.getX(), position.getY() + size));
        
        return new KeyShape(pointList);
    }
    
    public KeyShape generateRectangle(int sizeX, int sizeY) {
        Vector2 sizes = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld(sizeX, sizeY);
        List<Vector2> pointList = new ArrayList<>();
        pointList.add(new Vector2(0, 0));
        pointList.add(new Vector2(sizes.getX(), 0));
        pointList.add(new Vector2(sizes.getX(), sizes.getY()));
        pointList.add(new Vector2(0, sizes.getY()));
        
        return new KeyShape(pointList);
    }
    
    public KeyShape generateRectangle(int sizeX, int sizeY, Vector2 position) {
        position = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld((int)position.getX(), (int)position.getY());
        Vector2 sizes = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld(sizeX, sizeY);
        List<Vector2> pointList = new ArrayList<>();
        pointList.add(new Vector2(position.getX(), position.getY()));
        pointList.add(new Vector2(position.getX() + sizes.getX(), position.getY()));
        pointList.add(new Vector2(position.getX() + sizes.getX(), position.getY() + sizes.getY()));
        pointList.add(new Vector2(position.getX(), position.getY() + sizes.getY()));
        
        return new KeyShape(pointList);
    }
    
}

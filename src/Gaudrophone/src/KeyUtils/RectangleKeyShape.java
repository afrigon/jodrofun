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

import java.util.ArrayList;
import java.util.List;

public class RectangleKeyShape implements KeyShapeGenerator {

    @Override
    public KeyShape generate() {
        return generateSquare(15);
    }
    
    public KeyShape generateSquare(double squareSize) {
        List<Vector2> pointList = new ArrayList<>();
        pointList.add(new Vector2(0, 0));
        pointList.add(new Vector2(squareSize, 0));
        pointList.add(new Vector2(squareSize, squareSize));
        pointList.add(new Vector2(0, squareSize));
        
        return new KeyShape(pointList);
    }
    
    public KeyShape generateSquare(double squareSize, Vector2 position) {
        KeyShape key = generateSquare(squareSize);
        key.setPosition(position, KeyShape.Corner.Center);
        return key;
    }
    
    public KeyShape generateRectangle(double sizeX, double sizeY) {
        List<Vector2> pointList = new ArrayList<>();
        pointList.add(new Vector2(10, 10));
        pointList.add(new Vector2(sizeX, 0));
        pointList.add(new Vector2(sizeX, sizeY));
        pointList.add(new Vector2(0, sizeY));
        
        return new KeyShape(pointList);
    }
    
    public KeyShape generateRectangle(double sizeX, double sizeY, Vector2 position) {
        KeyShape key = generateRectangle(sizeX, sizeY);
        key.setPosition(position, KeyShape.Corner.Center);
        return key;
    }
    
}

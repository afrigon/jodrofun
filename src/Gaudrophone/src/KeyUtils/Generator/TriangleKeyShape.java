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
package KeyUtils.Generator;

import KeyUtils.KeyShape;
import KeyUtils.Vector2;
import Manager.GaudrophoneController;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TriangleKeyShape extends KeyShapeGenerator {
    private Color defaultColor = new Color(0x5a98fc);
    
    @Override
    public String getName() {
        return "Triangle";
    }
    
    @Override
    public KeyShape generate(Vector2 startClickPosition, Vector2 endClickPosition) {
        int x = (int)Math.abs(endClickPosition.getX() - startClickPosition.getX());
        int y = (int)Math.abs(endClickPosition.getY() - startClickPosition.getY());
        
        int leftX = (int)Math.min(startClickPosition.getX(), endClickPosition.getX());
        int leftY = (int)Math.min(startClickPosition.getY(), endClickPosition.getY());
        
        return this.generate(x, y, new Vector2(leftX, leftY), endClickPosition.getY() < startClickPosition.getY());
    }
    
    @Override
    public KeyShape generate(int size, Vector2 clickPosition) {
        return this.generate(size, size, clickPosition, false);
    }
    
    public KeyShape generate(int sizeX, int sizeY, Vector2 position, Boolean flipY) {
        Vector2 sizes = new Vector2(sizeX, sizeY);
        if (this.shouldConvertToRelative) {
            position = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld((int)position.getX(), (int)position.getY());
            sizes = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld(sizeX, sizeY);
        }
        
        List<Vector2> pointList = new ArrayList<>();
        if (!flipY) {
            pointList.add(new Vector2(position.getX(), position.getY() + sizes.getY()));
            pointList.add(new Vector2(position.getX() + sizes.getX()/2, position.getY()));
            pointList.add(new Vector2(position.getX() + sizes.getX(), position.getY() + sizes.getY()));
        } else {
            pointList.add(new Vector2(position.getX(), position.getY()));
            pointList.add(new Vector2(position.getX() + sizes.getX()/2, position.getY() + sizes.getY()));
            pointList.add(new Vector2(position.getX() + sizes.getX(), position.getY()));
        }
        
        return new KeyShape(pointList, this.defaultColor);
    }
}

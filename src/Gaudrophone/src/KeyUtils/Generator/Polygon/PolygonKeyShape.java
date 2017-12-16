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
package KeyUtils.Generator.Polygon;

import KeyUtils.Generator.KeyShapeGenerator;
import KeyUtils.KeyShape;
import KeyUtils.Vector2;
import Manager.GaudrophoneController;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PolygonKeyShape extends KeyShapeGenerator {
    protected Color defaultColor = new Color(0x5a98fc);
    protected int sides = 5;
    
    @Override
    public String getName() {
        return "Polygon";
    }
    
    @Override
    public KeyShape generate(Vector2 startClickPosition, Vector2 endClickPosition) {
        int x = (int)Math.abs(endClickPosition.getX() - startClickPosition.getX());
        int y = (int)Math.abs(endClickPosition.getY() - startClickPosition.getY());
        
        int leftX = (int)Math.min(startClickPosition.getX(), endClickPosition.getX());
        int leftY = (int)Math.min(startClickPosition.getY(), endClickPosition.getY());
        
        return this.generateShape(x, y, new Vector2(leftX, leftY));
    }
    
    @Override
    public KeyShape generate(int size, Vector2 clickPosition) {
        return this.generateShape(size, size, clickPosition);
    }
    
    public KeyShape generateShape(int sizeX, int sizeY, Vector2 position) {
        Vector2 sizes = new Vector2(sizeX, sizeY);
        if (this.shouldConvertToRelative) {
            position = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld((int)position.getX(), (int)position.getY());
            sizes = GaudrophoneController.getController().getCanvasManager().convertPixelToWorld(sizeX, sizeY);
        }
        
        List<Vector2> pointList = new ArrayList<>();
        Vector2 center = position.add(new Vector2(sizes.getX()/2, sizes.getY()/2));
        for (int i = 0; i < this.sides; i++) {
            double x = center.getX() - (sizes.getX()/2) * Math.sin(2 * Math.PI * i / this.sides);
            double y = center.getY() - (sizes.getY()/2) * Math.cos(2 * Math.PI * i / this.sides);
            pointList.add(new Vector2(x, y));
        }
        
        return new KeyShape(pointList, this.defaultColor);
    }
}

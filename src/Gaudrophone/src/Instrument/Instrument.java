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
package Instrument;

import KeyUtils.KeyShape;
import java.util.ArrayList;
import java.util.List;
import KeyUtils.Vector2;

public class Instrument implements java.io.Serializable {
    private String name;
    private ArrayList<Key> keys;
    private Vector2 boundingBox = new Vector2(0, 0);
    
    public Instrument() {
        this.name = "New Instrument";
        this.keys = new ArrayList<Key>();
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<Key> getKeys() {
        return keys;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public Vector2 getBoundingBox() {
        double lowestX = 0.0;
        double lowestY = 0.0;
        for (Key key : keys) {
            //System.out.println("lowestCorner : " + lowestCorner.getX() + ", " + lowestCorner.getY());
            if (key.getShape().getCorner(KeyShape.Corner.BottomRight).getX() > lowestX)
                lowestX = key.getShape().getCorner(KeyShape.Corner.BottomRight).getX();
            if (key.getShape().getCorner(KeyShape.Corner.BottomRight).getY() > lowestY)
                lowestY = key.getShape().getCorner(KeyShape.Corner.BottomRight).getY();
        }
        boundingBox = new Vector2(lowestX, lowestY);
        return boundingBox;
    }
    
    public void setRatio(Vector2 ratio) {
        System.out.println("setRatio");
        for (Key key : keys) {
            List<Vector2> newPoints = new ArrayList<Vector2>();
            for (Vector2 point : key.getShape().getPoints()) {
                System.out.println("Point Before : " + point.getX() + ", " + point.getY());
                newPoints.add(new Vector2(point.getX() + ratio.getX(), point.getY() + ratio.getY()));
                System.out.println("Point After : " + point.getX() + ", " + point.getY());
            }
            key.getShape().setPoints(newPoints);
            //key.getShape().stretch(ratio);
            System.out.println("Key Corner : " + key.getShape().getCorner(KeyShape.Corner.BottomRight).getX() + ", " + key.getShape().getCorner(KeyShape.Corner.BottomRight).getY());
        }
    }
    
    public void addKey(Key newKey) {
        keys.add(newKey);
        
        System.out.println("Key corner : " + newKey.getShape().getCorner(KeyShape.Corner.BottomRight).getX() + ", " + newKey.getShape().getCorner(KeyShape.Corner.BottomRight).getY());
        
        if (newKey.getShape().getCorner(KeyShape.Corner.BottomRight).getX() > boundingBox.getX()
                || newKey.getShape().getCorner(KeyShape.Corner.BottomRight).getY() > boundingBox.getY())
            boundingBox = newKey.getShape().getCorner(KeyShape.Corner.BottomRight);
    }
    
    public void removeKey(Key keyToRemove) {
        keys.remove(keyToRemove);
    }
}

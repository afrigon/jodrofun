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

import KeyUtils.Corner;
import java.util.ArrayList;
import KeyUtils.Vector2;

public class Instrument implements java.io.Serializable {
    private String name = "Instrument vide";
    private final ArrayList<Key> keys = new ArrayList<>();
    
    public String getName() {
        return this.name;
    }
    
    public ArrayList<Key> getKeys() {
        return this.keys;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public Vector2 getBoundingBox() {
        double width = 0;
        double height = 0;
        for(Key key : this.keys) {
            width = Math.max(width, key.getShape().getCorner(Corner.bottomRight).getX());
            height = Math.max(height, key.getShape().getCorner(Corner.bottomRight).getY());
        }
        return new Vector2(width, height);
    }
    
    public void addKey(Key newKey) {
        keys.add(newKey);
    }
    
    public void removeKey(Key keyToRemove) {
        this.keys.remove(keyToRemove);
    }
}

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

import Manager.Delegate.SelectionManagerDelegate;
import Instrument.Key;
import Instrument.KeyState;
import KeyUtils.KeyLine;
import java.awt.Color;

public class SelectionManager {
    private Key selectedKey = null;
    private int selectedLine = -5;
    private int selectedPoint = -1;
    
    private SelectionManagerDelegate delegate;
    public void setDelegate(SelectionManagerDelegate delegate) { this.delegate = delegate; }
    public SelectionManagerDelegate getDelegate() {
        if (this.delegate != null) {
            return this.delegate;
        } else {
            return new SelectionManagerDelegate() {
                @Override public void didSelectKey(Key key) {}
                @Override public void didDeselectKey() {}
                @Override public void didSelectLine(Color color, double thickness) {}
            };
        }
    }
    
    public void setKey(Key key) {
        if (this.selectedKey != null) {
            this.selectedKey.removeState(KeyState.selected);
        }
        this.selectedPoint = -1;
        this.selectedLine = -5;
        this.selectedKey = key;
        if (key != null) { key.addState(KeyState.selected); }
        if (key != null) {   
            this.getDelegate().didSelectKey(key);
        } else {
            this.getDelegate().didDeselectKey();
        }
    }
    
    public void setLine(int line) {
        this.selectedLine = line;
        if (line != -5) {
            if (line >= 0) {
                KeyLine keyLine = this.selectedKey.getShape().getLines().get(line);
                this.getDelegate().didSelectLine(keyLine.getColor(), keyLine.getThickness());
            } else {
                KeyLine keyLine = this.selectedKey.getShape().getCrossLines()[Math.abs(line)-1];
                this.getDelegate().didSelectLine(keyLine.getColor(), keyLine.getThickness());
            }
        }
    }
    
    public void setPoint(int point) {
        this.selectedPoint = point;
    }
    
    public Key getSelectedKey() {
        return this.selectedKey;
    }
    
    public int getSelectedLine() {
        return this.selectedLine;
    }
    
    public int getSelectedPoint() {
        return this.selectedPoint;
    }
}

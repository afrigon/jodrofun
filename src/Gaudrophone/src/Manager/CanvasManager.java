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

import Instrument.Key;
import Instrument.KeyState;
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;
import Music.SynthesizedSound;
import UI.DrawableShape;
import java.util.LinkedList;
import java.util.List;

public class CanvasManager {
    private List<DrawableShape> shapes;
    private State state = State.Play;
    private KeyShapeGenerator storedKeyShape;
    private Vector2 canvasSize = new Vector2(1, 1);
    public CanvasManagerDelegate delegate;
    
    private Key lastKey;
    private Vector2 clickPosition;
    
    public Vector2 convertPixelToWorld(int x, int y) {
       return new Vector2(x*100/this.canvasSize.getX()/100, y*100/this.canvasSize.getY()/100);
    }
    
    public Vector2 convertWorldToPixel(Vector2 vector) {
        return new Vector2(vector.getX()*this.canvasSize.getX(), vector.getY()*this.canvasSize.getY());
    }
    
    public int convertThicknessToPixel(double thickness) {
        return (int)thickness;
    }
    
    public void drawKeys(List<Key> keyList) {
        this.shapes = new LinkedList<>();
        keyList.forEach((key) -> {
            this.shapes.add(new DrawableShape(key));
        });
        if (this.delegate != null) {
            this.delegate.shouldRedraw();
        }
    }
    
    public void clicked(Key key) {
        switch (this.state) {
            case Play:
                GaudrophoneController.getController().getSoundService().play(key.getSound());
                key.addState(KeyState.clicked);
                this.lastKey = key;
                break;
            case EditKey:
                
                break;
        }
    }
    
    public void clicked(int x, int y) {
        this.clickPosition = new Vector2(x, y);
        DrawableShape ds = this.clickedShape(x, y);
        if (ds != null) {
            this.clicked(ds.getKey());
            
        }
    }
    
    public void released(Key key) {
        switch (this.state) {
            case Play:
                if (key != null) {
                    GaudrophoneController.getController().getSoundService().release(key.getSound());
                    key.removeState(KeyState.clicked);
                }
                break;
            case EditKey:
                GaudrophoneController.getController().getSelectionManager().setKey(key);
                if (key != null) {
                    for(Key k : GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys()) {
                        k.removeState(KeyState.selected);
                    }
                    key.addState(KeyState.selected);
                    this.lastKey = key;
                } else {
                    if (this.lastKey != null) {
                        this.lastKey.removeState(KeyState.selected);
                        this.lastKey = null;
                    }
                }
                break;
        }
    }
    
    public void released(int x, int y) {
        if (this.state == State.CreatingShape) {
            if (this.clickPosition != new Vector2(x, y)) {
                Key key = new Key(new SynthesizedSound(440), this.storedKeyShape.generate(this.clickPosition, new Vector2(x, y)), this.storedKeyShape.getName());
                GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().add(key);
                this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
            } else {
                Key key = new Key(new SynthesizedSound(440), this.storedKeyShape.generate(10, this.clickPosition), this.storedKeyShape.getName());
                GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().add(key);
                this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
            }
            
        }
        
        DrawableShape ds = this.clickedShape(x, y);
        if (ds != null) {
            this.released(ds.getKey());
        } else {
            this.released(null);
        }
    }
    
    public void dragged(int x, int y) {
        switch (this.state) {
            case Play:
                DrawableShape ds = this.clickedShape(x, y);
                if (ds != null) {
                    if (this.lastKey == null) {
                        GaudrophoneController.getController().getSoundService().play(ds.getKey().getSound());
                        ds.getKey().addState(KeyState.clicked);
                        if (this.delegate != null) { this.delegate.shouldRedraw(); }
                        this.lastKey = ds.getKey();
                    } else {
                        if (this.lastKey != ds.getKey()) {
                            GaudrophoneController.getController().getSoundService().release(this.lastKey.getSound());
                            GaudrophoneController.getController().getSoundService().play(ds.getKey().getSound());
                            ds.getKey().addState(KeyState.clicked);
                            this.lastKey.removeState(KeyState.clicked);
                            if (this.delegate != null) { this.delegate.shouldRedraw(); }
                            this.lastKey = ds.getKey();
                        }
                    }
                } else {
                    if (this.lastKey != null) {
                        GaudrophoneController.getController().getSoundService().release(this.lastKey.getSound());
                        this.lastKey.removeState(KeyState.clicked);
                        if (this.delegate != null) { this.delegate.shouldRedraw(); }
                        this.lastKey = null;
                    }
                }
                break;
            case CreatingShape:
                Key key = new Key(new SynthesizedSound(), this.storedKeyShape.generate(this.clickPosition, new Vector2(x, y)), this.storedKeyShape.getName());
                GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().add(key);
                this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
                GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().remove(key);
                break;
        }
    }
    
    private DrawableShape clickedShape(int x, int y) {
        for (int i = this.shapes.size() - 1; i >= 0; --i) {
            if (shapes.get(i).checkClick(x, y)) {
                return shapes.get(i);
            }
        }
        return null;
    }
    
    public List<DrawableShape> getDrawableShapes() {
        return shapes;
    }
    
    public State getState() { return state; }
    
    public void setState(State value) { state = value; }
    
    public void setCanvasSize(int x, int y) {
        this.canvasSize = new Vector2(x, y);
    }
    
    public void setStoredKeyGenerator(KeyShapeGenerator generator) {
        this.storedKeyShape = generator;
    }
}
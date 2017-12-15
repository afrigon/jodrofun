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

import Manager.Delegate.CanvasManagerDelegate;
import Instrument.Key;
import Instrument.KeyState;
import KeyUtils.Generator.KeyShapeGenerator;
import KeyUtils.Vector2;
import Music.PlayableNote;
import Music.SongPlayer;
import Music.SynthesizedSound;
import UI.DrawableShape;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.MouseEvent;

public class CanvasManager {
    private List<DrawableShape> shapes;
    private State state = State.Play;
    private KeyShapeGenerator storedKeyShape;
    
    private DrawableShape draggedShape = null;
    
    private double ratio = Double.POSITIVE_INFINITY;
    private Vector2 canvasSize2 = new Vector2(0, 0);
    
    public CanvasManagerDelegate delegate;
    
    private Key lastKey;
    private Vector2 clickPosition;
    
    public Vector2 convertPixelToWorld(int x, int y) {
        if(ratio != Double.POSITIVE_INFINITY)
            return new Vector2(x / ratio, y / ratio);
        else {
            this.ratio = 1;
            return new Vector2(x, y);
        }
    }
    
    public Vector2 convertWorldToPixel(Vector2 vector) {
        if(ratio != Double.POSITIVE_INFINITY)
            return new Vector2(vector.getX() * ratio, vector.getY() * ratio);
        else {
            this.ratio = 1;
            return canvasSize2;
        }
    }
    
    public int convertThicknessToPixel(double thickness) {
        if(ratio != Double.POSITIVE_INFINITY) {
            return (int)(thickness * ratio);
        } else {
            return (int)thickness;
        }
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
                key.play();
                this.lastKey = key;
                break;
            case AutoPlay:
                SongPlayer sequencer = GaudrophoneController.getController().getSequencerManager().getSequencer();
                if (sequencer.isMuted()) {
                    key.play(sequencer.hasNearNote(key.getSound().getPlayableNote().getFrequency()));
                } else {
                    key.play();
                }
                this.lastKey = key;
                break;
        }
    }
    
    public void clicked(int x, int y, int button) {
        this.clickPosition = new Vector2(x, y);
        if(this.state == State.EditKey) {
            int dot = clickedDot(x, y);
            if(dot != -1) {
                if (button == MouseEvent.BUTTON1) /* Left click */ {
                    GaudrophoneController.getController().getSelectionManager().setPoint(dot);
                    this.state = State.EditPoint;
                } else if (button == MouseEvent.BUTTON3) /* Right click */ {
                    GaudrophoneController.getController().deletePoint(dot);
                }
                return;
            }
        }
        DrawableShape ds = this.clickedShape(x, y);
        if (ds != null) {
            if(this.state == State.EditKey && (ds.getKey().getStates() & KeyState.selected.getValue()) != 0) {
                this.draggedShape = ds;
            }
            this.clicked(ds.getKey());
        }
    }
    
    public void released(Key key) {
        switch (this.state) {
            case AutoPlay:
            case Play:
                if (key != null) {
                    key.release();
                }
                break;
            case EditKey:
                GaudrophoneController.getController().getSelectionManager().setKey(key);
                if (key != null) {
                    this.lastKey = key;
                } else {
                    if (this.lastKey != null) {
                        this.lastKey = null;
                    }
                }
                break;
        }
    }
    
    public void released(int x, int y, int button) {
        switch (this.state) {
            case AutoPlay:
            case Play:
                this.released(this.lastKey);
                this.lastKey = null;
                break;
            case CreatingShape :
                if (this.storedKeyShape != null) {
                    if (!this.clickPosition.equals(new Vector2(x, y))) {
                        Key key = new Key(new SynthesizedSound(new PlayableNote()), this.storedKeyShape.generate(this.clickPosition, new Vector2(x, y)), this.storedKeyShape.getName());
                        GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().add(key);
                        this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
                    } else {
                        Key key = new Key(new SynthesizedSound(new PlayableNote()), this.storedKeyShape.generate(100, this.clickPosition), this.storedKeyShape.getName());
                        GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().add(key);
                        this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
                    }
                }
                break;
            case EditKey :
                if(this.draggedShape != null) {
                    this.draggedShape = null;
                }
                break;
            case EditPoint :
                GaudrophoneController.getController().getSelectionManager().setPoint(-1);
                this.state = State.EditKey;
                return; // Skip the key release to prevent deselection
            }
        
        DrawableShape ds = this.clickedShape(x, y);
        if (ds != null) {
            this.released(ds.getKey());
            ds.setDots();
        } else {
            this.released(null);
        }
    }
    
    public void dragged(int x, int y, int button) {
        switch (this.state) {
            case Play:
                DrawableShape ds = this.clickedShape(x, y);
                //If the drag is on a key
                if (ds != null) {
                    //If the user clicked the canvas and drag onto a key
                    if (this.lastKey == null) {
                        ds.getKey().play();
                        if (this.delegate != null) { this.delegate.shouldRedraw(); }
                        this.lastKey = ds.getKey();
                    } else {
                        //If the playing key is not the same as the key being drag right now
                        if (this.lastKey != ds.getKey()) {
                            this.lastKey.release();
                            ds.getKey().play();
                            if (this.delegate != null) { this.delegate.shouldRedraw(); }
                            this.lastKey = ds.getKey();
                        }
                    }
                } else {
                    if (this.lastKey != null) {
                        this.lastKey.release();
                        if (this.delegate != null) { this.delegate.shouldRedraw(); }
                        this.lastKey = null;
                    }
                }
                break;
            case CreatingShape:
                if (this.storedKeyShape != null) {
                    Key key = new Key(new SynthesizedSound(new PlayableNote()), this.storedKeyShape.generate(this.clickPosition, new Vector2(x, y)), this.storedKeyShape.getName());
                    GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().add(key);
                    this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
                    GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys().remove(key);
                }
                break;
            case EditKey :
                if(this.draggedShape != null) {
                    GaudrophoneController.getController().moveKey(
                            this.convertPixelToWorld(
                            (int)(x - this.clickPosition.getX()),
                            (int)(y - this.clickPosition.getY())));
                    
                    this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
                    this.clickPosition = new Vector2(x, y);
                }
                break;
            case EditPoint : 
                GaudrophoneController.getController().movePoint(this.convertPixelToWorld(
                        (int)(x - this.clickPosition.getX()),
                        (int)(y - this.clickPosition.getY())));
                this.drawKeys(GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys());
                this.clickPosition = new Vector2(x, y);
                break;
        }
    }
    
    private DrawableShape clickedShape(int x, int y) {
        for (int i = this.shapes.size()-1; i >= 0; --i) {
            if (this.shapes.get(i).checkClick(x, y)) {
                return this.shapes.get(i);
            }
        }
        return null;
    }
    
    private int clickedDot(int x, int y) {
        if(GaudrophoneController.getController().getSelectionManager().getSelectedKey() != null)  {
            for(int i = 0; i < DrawableShape.getDot().size(); ++i) {
                if(DrawableShape.getDot().get(i).contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public List<DrawableShape> getDrawableShapes() {
        return shapes;
    }
    
    public State getState() { return state; }
    
    public void setState(State state) {
        if (this.state == State.EditKey) {
            GaudrophoneController.getController().getSelectionManager().setKey(null);
        }
        this.state = state;
        this.delegate.didChangeState(state);
    }
    
    public void setCanvasSize(int x, int y) {
        this.canvasSize2 = new Vector2(x - 10, y - 10);
        if(GaudrophoneController.getController().getInstrumentManager().getInstrument() != null)
            this.findNewRatio(GaudrophoneController.getController().getInstrumentManager().getInstrument().getBoundingBox());
    }
    
    public void findNewRatio(Vector2 instrumentCorner) {
        if(instrumentCorner == null
                || instrumentCorner.getX() == Double.POSITIVE_INFINITY || instrumentCorner.getY() == Double.POSITIVE_INFINITY
                || instrumentCorner.getX() == Double.NEGATIVE_INFINITY || instrumentCorner.getY() == Double.NEGATIVE_INFINITY
                || instrumentCorner.getX() == Double.NaN || instrumentCorner.getY() == Double.NaN)
            ratio = Double.POSITIVE_INFINITY;
        else
            this.ratio = Math.min(
                    this.canvasSize2.getX() / instrumentCorner.getX(),
                    this.canvasSize2.getY() / instrumentCorner.getY());
    }
    
    public void setStoredKeyGenerator(KeyShapeGenerator generator) {
        this.storedKeyShape = generator;
    }
}
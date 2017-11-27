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
import Music.SoundService;
import KeyUtils.KeyShape;
import KeyUtils.Vector2;
import KeyUtils.KeyLine;
import Music.AudioClip;
import Music.SynthesizedSound;
import Instrument.Note;
import Instrument.Alteration;
import Music.NoteTranslator;
import Music.SoundType;
import Music.WaveFormType;
import java.awt.Color;
import java.util.List;

public class GaudrophoneController {
    private final InstrumentManager instrumentManager = new InstrumentManager();
    private final CanvasManager canvasManager = new CanvasManager();
    private final SoundService soundService = new SoundService();
    private final SelectionManager selectionManager = new SelectionManager();
    public GaudrophoneControllerDelegate delegate;
    
    private static GaudrophoneController controller = null;
    
    public static GaudrophoneController getController() {
        if (controller == null) {
            controller = new GaudrophoneController();
        }
        return controller;
    }
    
    public InstrumentManager getInstrumentManager() {
        return instrumentManager;
    }
    
    public CanvasManager getCanvasManager() {
        return canvasManager;
    }
    
    public SoundService getSoundService() {
        return soundService;
    }
    
    public SelectionManager getSelectionManager() {
        return selectionManager;
    }
    
    public void duplicateKey() {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            Key k = new Key(key);
            Vector2 translation = this.canvasManager.convertPixelToWorld(10, 10);
            k.getShape().translate(translation);
            instrumentManager.getInstrument().addKey(k);
            this.selectionManager.setKey(k);
            this.canvasManager.drawKeys(this.instrumentManager.getInstrument().getKeys());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void deleteKey() {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            instrumentManager.getInstrument().removeKey(key);
            selectionManager.setKey(null);
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeyPosition(Vector2 position) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            position = this.canvasManager.convertPixelToWorld((int)position.getX(), (int)position.getY());
            key.getShape().translate(position.sub(key.getShape().getCorner(KeyShape.Corner.TopLeft)));
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
        }
    }
    
    public void moveKey(Vector2 translation) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().translate(translation);
            this.delegate.didMoveKey(key);
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySize(Vector2 size) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            size = this.canvasManager.convertPixelToWorld((int)size.getX(), (int)size.getY());
            Vector2 origin = key.getShape().getCorner(KeyShape.Corner.TopLeft);
            Vector2 bottomRightOrigin = key.getShape().getCorner(KeyShape.Corner.BottomRight);
            Vector2 oldSize = new Vector2(bottomRightOrigin.getX() - origin.getX(), bottomRightOrigin.getY() - origin.getY());
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());

            if (size.getX() != 0 && size.getY() != 0) {
                key.getShape().setSize(size, KeyShape.Corner.Center);
            }        
        }
    }
    
    public void resizeKey(KeyShape.Corner corner, Vector2 delta) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().stretch(delta);
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void createPoint() {
        
    }
    
    public void deletePoint() {
        
    }
    
    public void movePoint(Vector2 translation) {
        Key key = selectionManager.getSelectedKey();
        int point = selectionManager.getSelectedPoint();
        if (key != null && point != -1) {
            key.getShape().getPoints().set(point, key.getShape().getPoints().get(point).add(translation));
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.delegate.didMovePoint(key);
        }
    }
    
    public void curveLine(Vector2 translation) {
        
    }
    
    public void setKeyDepth(int index) {
        Key key = selectionManager.getSelectedKey();
        if (key != null && index >= 0 && index < this.instrumentManager.getInstrument().getKeys().size()) {
            this.instrumentManager.getInstrument().getKeys().remove(key);
            this.instrumentManager.getInstrument().getKeys().add(index, key);
        }
    }
    
    public void setKeyColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getIdleAppearance().setColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySunkenColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getSunkenAppearance().setColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeyTextColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getIdleAppearance().setTextColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySunkenTextColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getSunkenAppearance().setTextColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeyImage(String path) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            if (path == null) {
                key.getShape().getIdleAppearance().removeImage();
            } else {
                key.getShape().getIdleAppearance().setImage(path);
            }
            
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySunkenImage(String path) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            if (path == null) {
                key.getShape().getSunkenAppearance().removeImage();
            } else {
                key.getShape().getSunkenAppearance().setImage(path);
            }
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setLineColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        int line = selectionManager.getSelectedLine();
        if (key != null) {
            if(line == -5)
                this.setAllLineColor(newColor);
            else if(line == -1 || line == -2 || line == -3 || line == -4)
                key.getShape().setCrossLineColor(newColor, Math.abs(line) - 1);
            else {
                List<KeyLine> shapeLines = key.getShape().getLines();
                shapeLines.get(line).setColor(newColor);
                key.getShape().setLines(shapeLines);
            }
            this.canvasManager.delegate.shouldRedraw();
        }
        
    }
    
    public void setAllLineColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            List<KeyLine> shapeLines = key.getShape().getLines();
            for(KeyLine line : shapeLines) {
                if(line != null)
                    line.setColor(newColor);
            }
            for(KeyLine line : key.getShape().getCrossLines()) {
                if(line != null)
                    line.setColor(newColor);
            }
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setAllLineThickness(double newThickness) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            List<KeyLine> shapeLines = key.getShape().getLines();
            for(KeyLine line : shapeLines) {
                if(line != null)
                    line.setThickness(newThickness);
            }
            for(KeyLine line : key.getShape().getCrossLines()) {
                if(line != null)
                    line.setThickness(newThickness);
            }
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setLineThickness(double newThickness) {
        Key key = selectionManager.getSelectedKey();
        int line = selectionManager.getSelectedLine();
        if (key != null) {
            if(line == -5)
                this.setAllLineThickness(newThickness);
            else if(line == -1 || line == -2 || line == -3 || line == -4)
                key.getShape().setCrossLineThickness(newThickness, Math.abs(line) - 1);
            else {
                List<KeyLine> shapeLines = key.getShape().getLines();
                shapeLines.get(line).setThickness(newThickness);
                key.getShape().setLines(shapeLines);
            }
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setName(String newName) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.setName(newName);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public Boolean setAudioClip(String path) {
        Key key = selectionManager.getSelectedKey();
        if (key != null && AudioClip.class.isInstance(key.getSound())/*key.getSound().getType() == SoundType.audioClip*/) {
            AudioClip clip = (AudioClip) key.getSound();
            if (!clip.setPath(path)) {
                return false;
            }
            
            key.setSound(clip);
        }
        return true;
    }
    
    private void setFrequency(Key key) {
        if (key.getSound().getType() == SoundType.synthesizedSound) {
            SynthesizedSound sound = (SynthesizedSound)key.getSound();
            sound.setFrequency(NoteTranslator.getFrequencyFromKey(key.getNote(), key.getAlteration(), key.getOctave(), sound.getTuning()));
            this.delegate.shouldUpdateProprietyPannelFor(key);
        }
    }
    
    public void setNote(Note newNote) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.setNote(newNote);
            this.setFrequency(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setAlteration(Alteration alteration) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.setAlteration(alteration);
            this.setFrequency(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setOctave(int newOctave) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.setOctave(newOctave);
            this.setFrequency(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setTuning (int newTuning) {
        Key key = selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.synthesizedSound) {
            ((SynthesizedSound)key.getSound()).setTuning(newTuning);
            this.setFrequency(key);
        }
    }
    
    public void setAttack (double newAttack) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setAttack(newAttack);
        }
    }
        
    public void setDecay (double newDecay) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setDecay(newDecay);
        }
    }
        
    public void setSustain (double newSustain) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setSustain((double)newSustain/100);
        }
    }
        
    public void setRelease (double newRelease) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setRelease(newRelease);
        }
    }
        
    public void setVolume (double newVolume) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().setVolume(newVolume);
        }
    }
    
    public void setPitch(double pitch) {
        Key key = selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.audioClip) {
            ((AudioClip)key.getSound()).setSpeed(pitch);
        }
    }

    public void setWaveform(WaveFormType waveFormType) {
        Key key = selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.synthesizedSound) {
            ((SynthesizedSound)key.getSound()).setWaveForm(waveFormType.getWaveForm());
        }
    }

    public void createSynth() {
        Key key = selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.audioClip) {
            key.setSound(new SynthesizedSound());
        }
    }

    public void createAudioClip() {
        Key key = selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.synthesizedSound) {
            key.setSound(new AudioClip());
        }
    }
}

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
import Music.AudioClip;
import Music.SynthesizedSound;
import Instrument.Note;
import Instrument.Alteration;
import Music.NoteTranslator;
import Music.WaveFormType;
import java.awt.Color;
import java.util.List;

public class GaudrophoneController {
    private InstrumentManager instrumentManager;
    private CanvasManager canvasManager;
    private SoundService soundService;
    private SelectionManager selectionManager;
    public GaudrophoneControllerDelegate delegate;
    
    private static GaudrophoneController controller = null;
    
    public GaudrophoneController() {
        instrumentManager = new InstrumentManager();
        canvasManager = new CanvasManager();
        soundService = new SoundService();
        selectionManager = new SelectionManager();
    }
    
    public static GaudrophoneController getController() {
        if (controller == null)
            controller = new GaudrophoneController();
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
            instrumentManager.getInstrument().addKey(new Key(key));
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void deleteKey() {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            instrumentManager.getInstrument().removeKey(key);
            selectionManager.setKey(null);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void moveKey(Vector2 translation) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().translate(translation);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void resizeKey(KeyShape.Corner corner, Vector2 delta) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().stretch(delta);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void createPoint() {
        
    }
    
    public void deletePoint() {
        
    }
    
    public void movePoint(Vector2 oldMousePosition, Vector2 newMousePosition) {

    }
    
    public void curveLine(Vector2 oldMousePosition, Vector2 newMousePosition) {
        
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
        if (key != null) {
            List<KeyUtils.KeyLine> shapeLines = key.getShape().getLines();
            shapeLines.get(selectionManager.getSelectedLine()).setColor(newColor);
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
        
    }
    
    public void setAllLineColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            List<KeyUtils.KeyLine> shapeLines = key.getShape().getLines();
            for(KeyUtils.KeyLine line : shapeLines) {
                line.setColor(newColor);
            }
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
        
    }
    
    public void setLineThickness(double newThickness) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            List<KeyUtils.KeyLine> shapeLines = key.getShape().getLines();
            shapeLines.get(selectionManager.getSelectedLine()).setThickness(newThickness);
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setCrossLineColor(Color newColor) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().setCrossLineColor(newColor, Math.abs(selectionManager.getSelectedLine()) - 1);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setCrossLineThickness(double newThickness) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().setCrossLineThickness(newThickness, Math.abs(selectionManager.getSelectedLine()) - 1);
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
    
    public void setAudioClip(String path) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            AudioClip clip = new AudioClip(path);
            
            key.setSound(clip);
            key.setNote(Note.A);
            key.setOctave(4);
            key.setAlteration(Alteration.Natural);
        }
    }
    
    public void removeAudioClip() {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            SynthesizedSound sound = new SynthesizedSound();
            
            key.setSound(sound);
            key.setNote(Note.A);
            key.setOctave(4);
            key.setAlteration(Alteration.Natural);
        }
    }
    
    public void setAudioClipReadSpeed(double newSpeed) {
        Key key = selectionManager.getSelectedKey();
        if (key != null) {
            ((AudioClip)key.getSound()).setSpeed(newSpeed);
        }
    }
    
    private void setFrequency(Key key) {
        if ("synth".equals(key.getSound().getType())) {
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
        if (key != null && "synth".equals(key.getSound().getType())) {
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
            key.getSound().getEnvelope().setAttack(newDecay);
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

    public void setWaveform(WaveFormType waveFormType) {
        Key key = selectionManager.getSelectedKey();
        if (key != null && "synth".equals(key.getSound().getType())) {
            ((SynthesizedSound)key.getSound()).setWaveForm(waveFormType.getWaveForm());
        }
    }
}

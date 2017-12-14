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
import KeyUtils.Corner;
import KeyUtils.CrossLine;
import Music.SoundService;
import KeyUtils.Vector2;
import KeyUtils.KeyLine;
import Music.AudioClip;
import Music.SynthesizedSound;
import Music.Note;
import Music.Alteration;
import Music.LiveLoop;
import Music.PlayableNote;
import Music.SongIO;
import Music.Sound;
import Music.SoundType;
import Music.WaveFormType;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class GaudrophoneController {
    private final InstrumentManager instrumentManager = new InstrumentManager();
    private final CanvasManager canvasManager = new CanvasManager();
    private final SoundService soundService = SoundService.getSoundService();
    private final SelectionManager selectionManager = new SelectionManager();
    private final Sequencer sequencer = new Sequencer();
    private final DeviceManager deviceManager = new DeviceManager();
    private final LiveLoop[] liveLoops = new LiveLoop[9];
    public GaudrophoneControllerDelegate delegate;
    
    private static GaudrophoneController controller = null;
    
    private GaudrophoneController()
      {
        for (int i = 0; i < 9; i++)
            liveLoops[i] = new LiveLoop();
      }
    
    public static GaudrophoneController getController() {
        if (controller == null) {
            controller = new GaudrophoneController();
        }
        return controller;
    }
    
    public InstrumentManager getInstrumentManager() {
        return this.instrumentManager;
    }
    
    public CanvasManager getCanvasManager() {
        return this.canvasManager;
    }
    
    public SoundService getSoundService() {
        return this.soundService;
    }
    
    public SelectionManager getSelectionManager() {
        return this.selectionManager;
    }
    
    public Sequencer getSequencer() {
        return this.sequencer;
    }
    
    public DeviceManager getDeviceManager() {
        return this.deviceManager;
    }
    
    public void duplicateKey() {
        Key key = this.selectionManager.getSelectedKey();
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
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            int index = this.instrumentManager.getInstrument().getKeys().indexOf(key);
            this.instrumentManager.getInstrument().removeKey(key);
            if (!this.instrumentManager.getInstrument().getKeys().isEmpty()) {
                this.selectionManager.setKey(this.instrumentManager.getInstrument().getKeys().get(Math.max(0, index-1)));
            } else {
                this.selectionManager.setKey(null);
            }
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.canvasManager.drawKeys(this.instrumentManager.getInstrument().getKeys());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeyPosition(Vector2 position) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            position = this.canvasManager.convertPixelToWorld((int)position.getX(), (int)position.getY());
            key.getShape().translate(position.sub(key.getShape().getCorner(Corner.topLeft)));
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
        }
    }
    
    public void moveKey(Vector2 translation) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().translate(translation);
            this.delegate.didMoveKey(key);
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.canvasManager.drawKeys(this.instrumentManager.getInstrument().getKeys());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySize(Vector2 size) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            size = this.canvasManager.convertPixelToWorld((int)size.getX(), (int)size.getY());
            Vector2 origin = key.getShape().getCorner(Corner.topLeft);
            Vector2 bottomRightOrigin = key.getShape().getCorner(Corner.bottomRight);
            Vector2 oldSize = new Vector2(bottomRightOrigin.getX() - origin.getX(), bottomRightOrigin.getY() - origin.getY());
            //this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());

            if (size.getX() != 0 && size.getY() != 0) {
                key.getShape().setSize(size, Corner.topLeft);
            }        
        }
    }
    
    public void resizeKey(Corner corner, Vector2 delta) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().stretch(delta);
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void createPoint(int index) {
        Key key = this.selectionManager.getSelectedKey();
        if(key != null) {
            key.getShape().addPoint(index);
            this.delegate.didMovePoint(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void deletePoint(int index) {
        Key key = this.selectionManager.getSelectedKey();
        //Must remain at least 3 points after removing
        if(key != null && key.getShape().getPoints().size() > 3) {
            key.getShape().getPoints().remove(index);
            key.getShape().getLines().remove(index);
            this.delegate.didMovePoint(key);
            this.delegate.shouldUpdateProprietyPannelFor(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void movePoint(Vector2 translation) {
        Key key = this.selectionManager.getSelectedKey();
        int point = this.selectionManager.getSelectedPoint();
        if (key != null && point != -1) {
            key.getShape().getPoints().set(point, key.getShape().getPoints().get(point).add(translation));
            this.canvasManager.findNewRatio(this.instrumentManager.getInstrument().getBoundingBox());
            this.delegate.didMovePoint(key);
        }
    }
    
    //Curves won't be out for a while, since border algo need to be redone (previously linear)
    public void curveLine(Vector2 translation) {
        Key key = this.selectionManager.getSelectedKey();
        int point = this.selectionManager.getSelectedPoint();
        if (key != null && point != -1) {
            key.getShape().getLines().get(point).setCurve(
                    key.getShape().getLines().get(point).getCurve().add(translation));
            this.delegate.didMovePoint(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setLineShape(KeyUtils.LineShape pointShape) {
        Key key = this.selectionManager.getSelectedKey();
        int line = this.selectionManager.getSelectedLine();
        if (key != null && line != -1) {
            Vector2 p2, p1 = key.getShape().getPoints().get(line);
            if(line + 1 == key.getShape().getPoints().size()) {
                p2 = key.getShape().getPoints().get(0);
            } else {
                p2 = key.getShape().getPoints().get(line);
            }
            key.getShape().getLines().get(line).setShape(pointShape, p1, p2);
            this.delegate.didMovePoint(key);
        }
    }
    
    public void setKeyDepth(int index) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null && index >= 0 && index < this.instrumentManager.getInstrument().getKeys().size()) {
            this.instrumentManager.getInstrument().getKeys().remove(key);
            this.instrumentManager.getInstrument().getKeys().add(index, key);
        }
    }
    
    public void setKeyColor(Color newColor) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getIdleAppearance().setColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySunkenColor(Color newColor) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getSunkenAppearance().setColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeyTextColor(Color newColor) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getIdleAppearance().setTextColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeySunkenTextColor(Color newColor) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getShape().getSunkenAppearance().setTextColor(newColor);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setKeyImage(String path) {
        Key key = this.selectionManager.getSelectedKey();
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
        Key key = this.selectionManager.getSelectedKey();
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
        Key key = this.selectionManager.getSelectedKey();
        int line = this.selectionManager.getSelectedLine();
        if (key != null) {
            if (line == -5)
                this.setAllLineColor(newColor);
            else if(line == -1 || line == -2 || line == -3 || line == -4)
                key.getShape().setCrossLineColor(newColor, CrossLine.getCrossLineForIndex(Math.abs(line) - 1));
            else {
                List<KeyLine> shapeLines = key.getShape().getLines();
                shapeLines.get(line).setColor(newColor);
                key.getShape().setLines(shapeLines);
            }
            this.canvasManager.delegate.shouldRedraw();
        }
        
    }
    
    public void setAllLineColor(Color newColor) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            List<KeyLine> shapeLines = key.getShape().getLines();
            for(KeyLine line : shapeLines) {
                if(line != null)
                    line.setColor(newColor);
            }
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setAllLineThickness(double newThickness) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            List<KeyLine> shapeLines = key.getShape().getLines();
            for(KeyLine line : shapeLines) {
                if(line != null)
                    line.setThickness(newThickness);
            }
            key.getShape().setLines(shapeLines);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setLineThickness(double newThickness) {
        Key key = this.selectionManager.getSelectedKey();
        int line = this.selectionManager.getSelectedLine();
        if (key != null) {
            if (line == -5)
                this.setAllLineThickness(newThickness);
            else if(line == -1 || line == -2 || line == -3 || line == -4)
                key.getShape().setCrossLineThickness(newThickness, CrossLine.getCrossLineForIndex(Math.abs(line) - 1));
            else {
                List<KeyLine> shapeLines = key.getShape().getLines();
                shapeLines.get(line).setThickness(newThickness);
                key.getShape().setLines(shapeLines);
            }
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setName(String newName) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.setName(newName);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public Boolean setAudioClip(String path) {
        Key key = this.selectionManager.getSelectedKey();
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
            this.delegate.shouldUpdateProprietyPannelFor(key);
        }
    }
    
    public void setNote(Note newNote) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.setNote(newNote);
            this.setFrequency(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setAlteration(Alteration alteration) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.setAlteration(alteration);
            this.setFrequency(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setOctave(int newOctave) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.setOctave(newOctave);
            this.setFrequency(key);
            this.canvasManager.delegate.shouldRedraw();
        }
    }
    
    public void setTuning (int newTuning) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.setTuning(newTuning);
            this.setFrequency(key);
        }
    }
    
    public void setAttack (double newAttack) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setAttack(newAttack);
            if(key.getSound().getType() == SoundType.synthesizedSound) {
                //((SynthesizedSound)key.getSound()).refreshBuffer();
            }
        }
    }
        
    public void setDecay (double newDecay) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setDecay(newDecay);
            if(key.getSound().getType() == SoundType.synthesizedSound) {
                //((SynthesizedSound)key.getSound()).refreshBuffer();
            }
        }
    }
        
    public void setSustain (double newSustain) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setSustain((double)newSustain/100);
            if(key.getSound().getType() == SoundType.synthesizedSound) {
                //((SynthesizedSound)key.getSound()).refreshBuffer();
            }
        }
    }
        
    public void setRelease (double newRelease) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().getEnvelope().setRelease(newRelease);
            if(key.getSound().getType() == SoundType.synthesizedSound) {
                //((SynthesizedSound)key.getSound()).refreshBuffer();
            }
        }
    }
        
    public void setVolume (double newVolume) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            key.getSound().setVolume(newVolume);
        }
    }
    
    public void setPitch(double pitch) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.audioClip) {
            ((AudioClip)key.getSound()).setSpeed(pitch);
        }
    }

    public void setWaveform(WaveFormType waveFormType) {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.synthesizedSound) {
            SynthesizedSound sound = ((SynthesizedSound)key.getSound());
            sound.setWaveForm(waveFormType.getWaveForm());
            //sound.refreshBuffer();
        }
    }

    public void setMasterWaveform(WaveFormType waveFormType) {
        if (this.instrumentManager.getInstrument() != null) {
            this.instrumentManager.getInstrument().setMasterWaveForm(waveFormType.getWaveForm());
        }
    }

    public void createSynth() {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.audioClip) {
            key.setSound(new SynthesizedSound(new PlayableNote()));
        }
    }

    public void createAudioClip() {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null && key.getSound().getType() == SoundType.synthesizedSound) {
            key.setSound(new AudioClip());
        }
    }

    public void nextKey() {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            int index = this.instrumentManager.getInstrument().getKeys().indexOf(key);
            Key newKey = this.instrumentManager.getInstrument().getKeys().get(index+1 >= this.instrumentManager.getInstrument().getKeys().size() ? 0 : index+1);
            this.selectionManager.setKey(newKey);
            this.canvasManager.delegate.shouldRedraw();
        } else {
            if (this.instrumentManager.getInstrument().getKeys().size() > 0) {
                this.selectionManager.setKey(this.instrumentManager.getInstrument().getKeys().get(0));
                this.canvasManager.delegate.shouldRedraw();
            }
        }
    }

    public void previousKey() {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            int index = this.instrumentManager.getInstrument().getKeys().indexOf(key);
            Key newKey = this.instrumentManager.getInstrument().getKeys().get(index-1 >= 0 ? index-1 : this.instrumentManager.getInstrument().getKeys().size()-1);
            this.selectionManager.setKey(newKey);
            this.canvasManager.delegate.shouldRedraw();
        } else {
            if (this.instrumentManager.getInstrument().getKeys().size() > 0) {
                this.selectionManager.setKey(this.instrumentManager.getInstrument().getKeys().get(0));
                this.canvasManager.delegate.shouldRedraw();
            }
        }
    }

    public void search(String text) {
        if ("".equals(text) || text == null) {
            for (Key key: this.instrumentManager.getInstrument().getKeys()) {
                key.removeState(KeyState.searched);
            }
        } else {
            Search search = new SearchAll(this.instrumentManager.getInstrument().getKeys());
            search.search(text);
        }
        this.canvasManager.delegate.shouldRedraw();
    }

    public void loadSong(String path) {
        this.sequencer.setSong(new SongIO().Load(path));
        this.delegate.didSetBPM(this.sequencer.getBPM());
    }
    
    public void setBPM(int bpm) {
        this.sequencer.setBPM(bpm);
        this.delegate.didSetBPM(this.sequencer.getBPM());
    }
    
    public boolean toggleMetronome() {
        return this.sequencer.toogleMetronome();
    }
    
    public boolean playNote(PlayableNote note) {
        for (Key key: this.instrumentManager.getInstrument().getKeys()) {
            if (key.getSound().getPlayableNote().getFrequency() == note.getFrequency()) {
                key.addState(KeyState.clicked);
                if (!sequencer.isMuted()) {
                    this.soundService.play(key.getSound());
                }
                this.canvasManager.delegate.shouldRedraw();
                return true;
            }
        }
        return sequencer.isMuted(); // so if it is muted the sound will not be played by the Sequencer
    }
    
    public boolean releaseNote(PlayableNote note) {
        for (Key key: this.instrumentManager.getInstrument().getKeys()) {
            if (key.getSound().getPlayableNote().getFrequency() == note.getFrequency()) {
                key.removeState(KeyState.clicked);
                this.soundService.release(key.getSound());
                this.canvasManager.delegate.shouldRedraw();
                return true;
            }
        }
        return sequencer.isMuted(); // so if it is muted the sound will not be played by the Sequencer
    }
    
    public void toggleLiveLoop(int index) {
        
        System.out.println("Toggleing LiveLoop " + index);
        
        if (liveLoops[index].isRecording())
            liveLoops[index].stopRecording();
        else
            liveLoops[index].startRecording();
    }
    
        public void addToLiveLoop(Sound sound)
      {
        for (LiveLoop ll: this.liveLoops) {
            if (ll.isRecording())
                ll.addSound(sound);
        }
      }
    
    public void stopSound() {
        for (LiveLoop ll: this.liveLoops) {
            if (ll.isRecording())
                ll.stopSound();
        }
    }
    
    public boolean toggleMute() {
        return this.sequencer.toggleMute();
    }

    public void closeAutoPlay() {
        this.sequencer.stopAll();
    }

    public void togglePlay() {
        this.sequencer.togglePlay();
    }

    public LinkedList<Key> getLinkedKeys(int channel, int midiNum) {
        LinkedList<Key> linkedKeys = new LinkedList<>();
        for (Key key: this.instrumentManager.getInstrument().getKeys()) {
            if (key.isLinked(channel, midiNum)) {
                linkedKeys.add(key);
            }
        }
        return linkedKeys;
    }
    
    public String midiAction() {
        Key key = this.selectionManager.getSelectedKey();
        if (key != null) {
            if (key.isLinked()) {
                key.unlink();
                return "midi_detected";
            } else {
                if (this.deviceManager.isLinking()) {
                    this.deviceManager.cancelLink();
                    return "midi_detected";    
                } else {
                    this.deviceManager.linkMidiToKey(key);
                    return "midi_linking";
                }
            }
        }
        return "midi_detected";
    }
}

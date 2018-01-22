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
package Instrument;

import Music.Note;
import Music.Alteration;
import KeyUtils.KeyShape;
import Manager.GaudrophoneController;
import Music.Sound;
import Music.SynthesizedSound;
import Music.AudioClip;
import Music.PlayableNote;
import Music.SoundType;
import java.awt.Color;

public class Key implements java.io.Serializable {
    private KeyShape shape = null;
    private Sound sound = null;
    private String name = null;
    private int states = 144;
    private boolean linked = false;
    private int linkedChannel = 0;
    private int linkedMidiNumber = 0;
    
    // Constructors
    public Key(Sound keySound, KeyShape keyShape, String keyName) {
        this.sound = keySound;
        this.shape = keyShape;
        this.name = keyName;
    }
    
    //Everything need to be brand new, otherwise it will use a referenced pointer
    //Exemple, moving one key will move any duplicated because the points are the same pointers
    public Key(Key key) {
        
        //Create new Sound, synthesized or audioclip
        if (key.getSound().getType() == SoundType.synthesizedSound) {
            SynthesizedSound s = (SynthesizedSound)key.getSound();
            this.sound = new SynthesizedSound(new PlayableNote());
            ((SynthesizedSound)this.sound).setWaveForm(s.getWaveform());
            this.sound.setEnvelope(new Music.Envelope(s.getEnvelope().getAttack(), s.getEnvelope().getDecay(), s.getEnvelope().getSustain(), s.getEnvelope().getRelease()));
            this.sound.setVolume(s.getVolume());
        } else if(key.getSound().getType() == SoundType.audioClip) {
            this.sound = new AudioClip(((AudioClip)key.getSound()).getPath());
        }
        
        //Create new KeyShape
        java.util.List<KeyUtils.Vector2> v = new java.util.ArrayList<>();
        key.getShape().getPoints().forEach((point)->{v.add(new KeyUtils.Vector2(point.getX(), point.getY()));});
        this.shape = new KeyShape(v, key.getShape().getIdleAppearance().getColor(), key.getShape().getSunkenAppearance().getColor());
        this.shape.getIdleAppearance().setImage(key.getShape().getIdleAppearance().getImagePath());
        this.shape.getSunkenAppearance().setImage(key.getShape().getSunkenAppearance().getImagePath());
        //Set lines borders and color using iterator because swag! (hope this work, first time)
        java.util.Iterator<KeyUtils.KeyLine> itline = key.getShape().getLines().iterator();
        for(int i = 0; itline.hasNext(); ++i) {
            KeyUtils.KeyLine line = itline.next();
            this.shape.getLines().get(i).setColor(line.getColor());
            this.shape.getLines().get(i).setThickness(line.getThickness());
        }
        //Same for cross-lines
        for(int i = 0; i < key.getShape().getCrossLines().length; ++i) {
            KeyUtils.KeyLine line = key.getShape().getCrossLines()[i];
            this.shape.getCrossLines()[i].setColor(line.getColor());
            this.shape.getCrossLines()[i].setThickness(line.getThickness());
        }
        
        //Set Others
        this.name = key.name;
        this.sound.getPlayableNote().setNote(key.sound.getPlayableNote().getNote());
        this.sound.getPlayableNote().setOctave(key.sound.getPlayableNote().getOctave());
        this.sound.getPlayableNote().setAlteration(key.sound.getPlayableNote().getAlteration());
        this.sound.getPlayableNote().setTuning(key.sound.getPlayableNote().getTuning());
        
        //Flags
        this.states = 0;
        if((key.getStates() & KeyState.displayAlteration.getValue()) != 0) {
            this.states += KeyState.displayAlteration.getValue();
        }
        if((key.getStates() & KeyState.displayName.getValue()) != 0) {
            this.states += KeyState.displayName.getValue();
        }
        if((key.getStates() & KeyState.displayNote.getValue()) != 0) {
            this.states += KeyState.displayNote.getValue();
        }
        if((key.getStates() & KeyState.displayOctave.getValue()) != 0) {
            this.states += KeyState.displayOctave.getValue();
        }
    }
    
    // Setters
    public void setSound(Sound sound) {
        this.sound = sound;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setNote(Note note) {
        this.sound.getPlayableNote().setNote(note);
    }
    
    public void setOctave(int octave) {
        this.sound.getPlayableNote().setOctave(octave);
    }
        
    public void setAlteration(Alteration alteration) {
        this.sound.getPlayableNote().setAlteration(alteration);
    }
    
    public void setTuning(int tuning) {
        this.sound.getPlayableNote().setTuning(tuning);
    }
    
    // Getters
    public Sound getSound() {
        return this.sound;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Note getNote() {
        return this.sound.getPlayableNote().getNote();
    }
    
    public Alteration getAlteration() {
        return this.sound.getPlayableNote().getAlteration();
    }
    
    public int getOctave() {
        return this.sound.getPlayableNote().getOctave();
    }
    
    public int getTuning() {
        return this.sound.getPlayableNote().getTuning();
    }
    
    public double getFrequency() {
        return this.sound.getPlayableNote().getFrequency();
    }
    
    public KeyShape getShape() {
        return this.shape;
    }
    
    public int getStates() {
        return this.states;
    }
    
    public void setStates(int states) {
        this.states = states;
    }
    
    public void addState(KeyState state) {
        this.states |= state.getValue();
    }
    
    public void addStates(int states) {
        this.states |= states;
    }
    
    public void removeState(KeyState state) {
        this.states &= ~state.getValue();
    }
    
    public void removeStates(int states) {
        this.states &= ~states;
    }
    
    public void link(int channel, int midiNum) {
        this.linkedChannel = channel;
        this.linkedMidiNumber = midiNum;
        this.linked = true;
        GaudrophoneController.getController().getDelegate().midiDidLink(this);
    }
    
    public boolean isLinked(int channel, int midiNum) {
        return (this.linked && this.linkedChannel == channel && this.linkedMidiNumber == midiNum);
    }
    
    public boolean isLinked() {
        return this.linked;
    }
    
    public void unlink() {
        this.linked = false;
    }
    
    public void play(boolean playSound) {
        if (playSound) {
            GaudrophoneController.getController().getSoundService().play(this.sound);
        }
        this.addState(KeyState.clicked);
    }
    
    public void play() {
        this.play(true);
    }
    
    public void release() {
        GaudrophoneController.getController().getSoundService().release(this.sound);
        this.removeState(KeyState.clicked);
    }
}

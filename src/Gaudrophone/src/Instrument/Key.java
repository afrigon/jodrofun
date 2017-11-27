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
import Music.Sound;
import Music.SynthesizedSound;
import Music.AudioClip;
import Music.SoundType;

public class Key implements java.io.Serializable {
    private KeyShape shape = null;
    private Sound sound = null;
    private String name = null;
    private Alteration alteration = Alteration.Natural;
    private Note note = Note.A;
    private int octave = 4;
    private int states = 144;
    
    // Constructors
    public Key(Sound keySound, KeyShape keyShape, String keyName) {
        this.sound = keySound;
        this.shape = keyShape;
        this.name = keyName;
    }
    
//    public Key(KeyShape keyShape) {
//        java.util.List<KeyUtils.Vector2> v = new java.util.ArrayList<>();
//        keyShape.getPoints().forEach((point)->{v.add(new KeyUtils.Vector2(point.getX(), point.getY()));});
//        this.shape = new KeyShape(v, keyShape.getIdleAppearance().getColor(), keyShape.getSunkenAppearance().getColor());
//        this.shape.getIdleAppearance().setImage(keyShape.getIdleAppearance().getImagePath());
//        this.shape.getSunkenAppearance().setImage(keyShape.getSunkenAppearance().getImagePath());
//        
//        this.sound = new SynthesizedSound();
//        this.name = "Nouvelle touche";
//    }
    
    //Everything need to be brand new, otherwise it will use a referenced pointer
    //Exemple, moving one key will move any duplicated because the points are the same pointers
    public Key(Key key) {
        //Create new Sound, synthesized or audioclip
        
        if (key.getSound().getType() == SoundType.synthesizedSound) {
            SynthesizedSound s = (SynthesizedSound)key.getSound();
            this.sound = new SynthesizedSound(s.getFrequency());
            ((SynthesizedSound)this.sound).setTuning(s.getTuning());
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
        
        //Set name
        this.name = key.name;
        this.note = key.note;
        this.alteration = key.alteration;
        this.octave = key.octave;
    }
    
    // Setters
    public void setSound(Sound newSound) {
        this.sound = newSound;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public void setNote(Note newNote) {
        this.note = newNote;
    }
    
    public void setAlteration(Alteration newAlteration) {
        this.alteration = newAlteration;
    }
    
    public void setOctave(int newOctave) {
        this.octave = newOctave;
    }
    
    public void changeState(int keyState) {
        this.states = this.states ^ keyState;
    }
    
    // Getters
    public Sound getSound() {
        return this.sound;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Note getNote() {
        return this.note;
    }
    
    public Alteration getAlteration() {
        return this.alteration;
    }
    
    public int getOctave() {
        return this.octave;
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
        this.states -= (this.states & state.getValue());
    }
    
    public void removeStates(int states) {
        this.states -= (this.states & states);
    }
}

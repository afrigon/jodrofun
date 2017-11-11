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

import KeyUtils.KeyShape;
import Music.Sound;
/**
 *
 * @author frigon
 */
public class Key {
    private KeyShape shape = null;
    private Sound sound = null;
    private String name = null;
    private Alteration displayAlteration = Alteration.Natural;
    private Note displayNote = Note.A;
    private int displayOctave = 4;
    
    public static enum Alteration {
        Flat, Natural, Sharp
    }
    public static enum Note {
        A, B, C, D, E, F, G
    } 
    
    // Constructors
    public Key(Sound keySound, KeyShape keyShape, String keyName) {
        sound = keySound;
        shape = keyShape;
        name = keyName;
    }
    
    // Setters
    public void setSound(Sound newSound) {
        sound = newSound;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public void setNote(Note newNote) {
        displayNote = newNote;
    }
    
    public void setAlteration(Alteration newAlteration) {
        displayAlteration = newAlteration;
    }
    
    public void setOctave(int newOctave) {
        displayOctave = newOctave;
    }
    
    // Getters
    public Sound getSound() {
        return sound;
    }
    
    public String getName() {
        return name;
    }
    
    public Note getNote() {
        return displayNote;
    }
    
    public Alteration getAlteration() {
        return displayAlteration;
    }
    
    public int getOctave() {
        return displayOctave;
    }
}

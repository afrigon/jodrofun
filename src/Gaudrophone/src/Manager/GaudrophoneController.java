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

/**
 *
 * @author frigon
 */

import Music.SoundService;
import Music.Envelope;
//import Music.Note;
import Instrument.Key;
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;

public class GaudrophoneController {
    private CanvasManager canvas;
    private SoundService soundService;
    
    public GaudrophoneController() {
        canvas = new CanvasManager();
        soundService = new SoundService();
    }
    
    public void createKey(KeyShapeGenerator key, Vector2 position) {
        
    }
    
    public void duplicateKey() {
        
    }
    
    public void deleteKey() {
        
    }
    
    public void moveKey(Vector2 translation) {
        
    }
    
    public void resizeKey(Vector2 delta) {
        
    }
    
    public void pressKey(Key key) {
        
    }
    
    public void releaseKey(Key key) {
        
    }
    
    public void createPoint() {
        
    }
    
    public void deletePoint() {
        
    }
    
    public void movePoint(Vector2 oldMousePosition, Vector2 newMousePosition) {
        
    }
    
    public void curveLine(Vector2 oldMousePosition, Vector2 newMousePosition) {
        
    }
    
    public void setKeyColor(/*Color newColor*/) {
        
    }
    
    public void setKeySunkenColor(/*Color newColor*/) {
    
    }
    
    public void setKeyImage(String path) {
        
    }
    
    public void setKeySunkenImage(String path) {
        
    }
    
    public void setLineColor(/*Color newColor*/) {
        
    }
    
    public void setLineThickness(int newThickness) {
        
    }
    
    public void setSoundName(String newName) {
        
    }
    
    public void setAudioClip(String path) {
        
    }
    
    public void removeAudioClip() {
        
    }
    
    public void setAudioClipReadSpeed(double newSpeed) {
        
    }
    
    public void setDisplayNote(/*Note newNote, Alteration newAlteration*/) {
        
    }
    
    public void setDisplayOctave(int newOctave) {
        
    }
    
    public void setTuning (double newTuning) {
        
    }
    
    public void setAttack (double newAttack) {
        
    }
        
    public void setDecay (double newDecay) {
        
    }
        
    public void setSustain (double newSustain) {
        
    }
        
    public void setRelease (double newRelease) {
        
    }
        
    public void setVolume (double newVolume) {
        
    }
}

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
import Instrument.Instrument;
import KeyUtils.KeyShape;
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;
import java.awt.Color;

public class GaudrophoneController {
    private Instrument instrument;
    private CanvasManager canvas;
    private SoundService soundService;
    private SelectionManager selection;
    
    public GaudrophoneController() {
        canvas = new CanvasManager(this);
        soundService = new SoundService();
    }
    
    public void createKey(KeyShapeGenerator key, Vector2 position) {
        KeyShape newShape = key.generate();
        
        newShape.setPosition(position, KeyShape.Corner.Center);
        instrument.addKey(new Key(newShape));
    }
    
    public void duplicateKey() {
        instrument.addKey(new Key(selection.getSelectedKey()));
    }
    
    public void deleteKey() {
        instrument.removeKey(selection.getSelectedKey());
    }
    
    public void moveKey(Vector2 translation) {
        selection.getSelectedKey().getShape().translate(translation);
    }
    
    public void resizeKey(KeyShape.Corner corner, Vector2 delta) {
        selection.getSelectedKey().getShape().stretch(delta);
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
    
    public void setKeyColor(Color newColor) {
        selection.getSelectedKey().getShape();
    }
    
    public void setKeySunkenColor(Color newColor) {
        selection.getSelectedKey().getShape();
    }
    
    public void setKeyImage(String path) {
        selection.getSelectedKey().getShape();
    }
    
    public void setKeySunkenImage(String path) {
        selection.getSelectedKey().getShape();
    }
    
    public void setLineColor(Color newColor) {
        selection.getSelectedKey().getShape();
    }
    
    public void setLineThickness(int newThickness) {
        selection.getSelectedKey().getShape();
    }
    
    public void setSoundName(String newName) {
        selection.getSelectedKey().getSound();
    }
    
    public void setAudioClip(String path) {
     //   selection.getSelectedKey().setNote(path);
    }
    
    public void removeAudioClip() {
        selection.getSelectedKey();
    }
    
    public void setAudioClipReadSpeed(double newSpeed) {
        
    }
    
    public void setDisplayNote(/*Note newNote, Alteration newAlteration*/) {
        
    }
    
    public void setDisplayOctave(int newOctave) {
        selection.getSelectedKey().setOctave(newOctave);
    }
    
    public void setTuning (double newTuning) {
        
    }
    
    public void setAttack (double newAttack) {
        selection.getSelectedKey().getSound().getEnvelope().setAttack(newAttack);
    }
        
    public void setDecay (double newDecay) {
        selection.getSelectedKey().getSound().getEnvelope().setAttack(newDecay);
    }
        
    public void setSustain (double newSustain) {
        selection.getSelectedKey().getSound().getEnvelope().setAttack(newSustain);
    }
        
    public void setRelease (double newRelease) {
        selection.getSelectedKey().getSound().getEnvelope().setAttack(newRelease);
    }
        
    public void setVolume (double newVolume) {
        selection.getSelectedKey().getSound().setVolume(newVolume);
    }
}

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
import KeyUtils.KeyShapeGenerator;
import KeyUtils.Vector2;
import java.awt.Color;

public class GaudrophoneController {
    private InstrumentManager instrumentManager;
    private CanvasManager canvasManager;
    private SoundService soundService;
    private SelectionManager selectionManager;
    
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
    
    public void createKey(KeyShapeGenerator key, Vector2 position) {
        KeyShape newShape = key.generate();
        
        newShape.setPosition(position, KeyShape.Corner.Center);
        instrumentManager.getInstrument().addKey(new Key(newShape));
    }
    
    public void duplicateKey() {
        instrumentManager.getInstrument().addKey(new Key(selectionManager.getSelectedKey()));
    }
    
    public void deleteKey() {
        instrumentManager.getInstrument().removeKey(selectionManager.getSelectedKey());
    }
    
    public void moveKey(Vector2 translation) {
        selectionManager.getSelectedKey().getShape().translate(translation);
    }
    
    public void resizeKey(KeyShape.Corner corner, Vector2 delta) {
        selectionManager.getSelectedKey().getShape().stretch(delta);
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
        selectionManager.getSelectedKey().getShape();
    }
    
    public void setKeySunkenColor(Color newColor) {
        selectionManager.getSelectedKey().getShape();
    }
    
    public void setKeyImage(String path) {
        selectionManager.getSelectedKey().getShape();
    }
    
    public void setKeySunkenImage(String path) {
        selectionManager.getSelectedKey().getShape();
    }
    
    public void setLineColor(Color newColor) {
        selectionManager.getSelectedKey().getShape();
    }
    
    public void setLineThickness(int newThickness) {
        selectionManager.getSelectedKey().getShape();
    }
    
    public void setSoundName(String newName) {
        selectionManager.getSelectedKey().getSound();
    }
    
    public void setAudioClip(String path) {
     //   selectionManager.getSelectedKey().setNote(path);
    }
    
    public void removeAudioClip() {
        selectionManager.getSelectedKey();
    }
    
    public void setAudioClipReadSpeed(double newSpeed) {
        
    }
    
    public void setDisplayNote(/*Note newNote, Alteration newAlteration*/) {
        
    }
    
    public void setDisplayOctave(int newOctave) {
        selectionManager.getSelectedKey().setOctave(newOctave);
    }
    
    public void setTuning (double newTuning) {
        
    }
    
    public void setAttack (double newAttack) {
        selectionManager.getSelectedKey().getSound().getEnvelope().setAttack(newAttack);
    }
        
    public void setDecay (double newDecay) {
        selectionManager.getSelectedKey().getSound().getEnvelope().setAttack(newDecay);
    }
        
    public void setSustain (double newSustain) {
        selectionManager.getSelectedKey().getSound().getEnvelope().setAttack(newSustain);
    }
        
    public void setRelease (double newRelease) {
        selectionManager.getSelectedKey().getSound().getEnvelope().setAttack(newRelease);
    }
        
    public void setVolume (double newVolume) {
        selectionManager.getSelectedKey().getSound().setVolume(newVolume);
    }
}

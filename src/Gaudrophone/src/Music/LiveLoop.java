/*
 * The MIT License
 *
 * Copyright 2017 Olivier.
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
package Music;

import Manager.GaudrophoneController;

public class LiveLoop {
    Song sequence = new Song();
    PlayableChord currentChord = new PlayableChord();
    boolean recording;
    long baseTime;
    
    
    public LiveLoop() {
        recording = false;
    }
    
    public boolean isRecording() {
        return recording;
    }
    
    public void startRecording() {
        baseTime = System.nanoTime() / 1000000000;
        recording = true;
        
         System.out.println("Start recording. Base Time : " + baseTime);
    }
    
    public void stopRecording() {
        recording = false;
    }
    
    public void addSound(Sound sound) {
        PlayableNote note = sound.getPlayableNote();
        
        System.out.println("Start Sound");

        currentChord.addNote(note);
        currentChord.setRelativeSteps(baseTime / (60 * GaudrophoneController.getController().getSequencer().getBPM()));
    }
    
    public void stopSound() {
        baseTime = System.nanoTime() / 1000000000;
        currentChord.setLength(baseTime / (60 * GaudrophoneController.getController().getSequencer().getBPM()));
        sequence.addChord(currentChord);
        currentChord = new PlayableChord();
        
        System.out.println("Stop Sound. Base Time : " + baseTime);
    }
}

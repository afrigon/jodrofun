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
        
        System.out.println("Stop recording.\nRecorded notes : " + sequence.getChords().size());
        GaudrophoneController.getController().getSequencer().setSong(sequence);
         GaudrophoneController.getController().getSequencer().play();
    }
    
    public void addSound(Sound sound) {
        PlayableNote note = sound.getPlayableNote();
        
        System.out.println("Start Sound");

        currentChord.addNote(note);
        currentChord.setRelativeSteps(baseTime / (60 * GaudrophoneController.getController().getSequencer().getBPM()));
    }
    
    public void stopSound() {
        long newBaseTime = System.nanoTime() / 1000000000;
        double newSteps = newBaseTime / (60 * GaudrophoneController.getController().getSequencer().getBPM());
        currentChord.setLength(newSteps);
        sequence.addChord(currentChord);
                       
        System.out.println("Stop Sound. Base Time : " + baseTime);
        System.out.println("BPM : " + GaudrophoneController.getController().getSequencer().getBPM());
        System.out.println("Steps : " + currentChord.getRelativeSteps());
        System.out.println("Length : " + newSteps);
        
        baseTime = newBaseTime;
        
        currentChord = new PlayableChord();
    }
}

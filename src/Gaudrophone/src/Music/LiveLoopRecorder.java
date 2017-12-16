/*
 * The MIT License
 *
 * Copyright 2017 LethalShade.
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

/**
 *
 * @author LethalShade
 */

import Manager.GaudrophoneController;

public class LiveLoopRecorder
  {
    private long baseTime;
    private Song currentSong;
    private PlayableChord currentChord;
    private boolean waiting = false;
    private boolean idle = true;
    
    public void startRecording() {
        idle = false;
        waiting = true;
        currentSong = new Song();
        currentChord = new PlayableChord();
    }
    
    public Song stopRecording() {
        idle = true;
        return currentSong;
    }
    
     public void addSound(Sound sound) {
        waiting = false;
        PlayableNote note = sound.getPlayableNote();
        
        System.out.println("Start Sound");

        currentChord.addNote(note);
        currentChord.setRelativeSteps(baseTime / (60 * GaudrophoneController.getController().getSequencerManager().getBPM()));
    }
    
    public void stopSound() {
        long newBaseTime = System.nanoTime() / 1000000000;
        double length = 0;
        currentChord.setLength(length); // Dunno what to put there.
        currentSong.addChord(currentChord);
                       
        System.out.println("Stop Sound. Base Time : " + baseTime);
        System.out.println("BPM : " + GaudrophoneController.getController().getSequencerManager().getBPM());
        System.out.println("Steps : " + currentChord.getRelativeSteps());
        System.out.println("Length : " + length);
        
        baseTime = newBaseTime;
        
        currentChord = new PlayableChord();
    }
    
    public boolean isIdle() {
        return idle;
    }
    
    public boolean isWaiting() {
        return waiting;
    }
  }

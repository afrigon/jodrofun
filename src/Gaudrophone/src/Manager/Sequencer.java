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

import Music.*;

public class Sequencer implements Runnable {
    private final Metronome metronome = new Metronome();
    private Song song = null;
    private int bpm = 120;
    private boolean isPlaying = false;
    private long lastTimeUpdate = 0;
    private double currentStep = 0;
    
    public int getBPM() {
        return this.bpm;
    }
    
    public void setBPM(int bpm) {
        this.bpm = Math.max(1, Math.min(bpm, 600));
        this.metronome.adjustBPM(this.bpm);
    }
    
    public void setSong(Song song) {
        this.song = song;
        this.setBPM(song.getBPM());
    }
    
    //return state
    public boolean toogleMetronome() {
        if (!this.metronome.isRunning) {
            this.metronome.start(this.bpm);
        } else {
            this.metronome.close();
        }
        return this.metronome.isRunning;
    }
    
    private double getElapsedTime() {
        long now = System.nanoTime();
        long delta = now - lastTimeUpdate;
        lastTimeUpdate = now;
        return ((double) delta) / 1000000000.0;
    }
    
//    private void generateMissingSound() {
//        missingSounds.clear();
//        for (PlayableChord chord : this.song.getChords()) {
//            for (PlayableNote note : chord.getNotes()) {
//                
//            }
//        }
//    }
    
    public void play() {
        isPlaying = true;
        new Thread(this).start();
    }
    
    public void pause() {
        isPlaying = false;
    }
    
    public void stop() {
        isPlaying = false;
        currentStep = 0;
    }
    
    @Override
    public void run() {
        getElapsedTime(); // call the method to init lastTimeUpdate
        while (isPlaying) {
            double previousStep = currentStep;
            currentStep += getElapsedTime() * ((double) bpm) / 60.0; // calculate elapsed steps
            
            double chordPlayStep = 0;
            for (PlayableChord chord : this.song.getChords()) {
                chordPlayStep += chord.getRelativeSteps();
                double chordEndStep = chordPlayStep + chord.getLength();
                
                if ((chordPlayStep > previousStep) && (chordPlayStep <= currentStep)) {
                    // get a sound or create a default one
                    // store it somewhere to stop it
                    //SOUND_SERVICE.play(sound);
                }
                
                if ((chordEndStep > previousStep) && (chordEndStep <= currentStep)) {
                    // get the searched sound or the created one and release it
                    //SOUND_SERVICE.release(sound);
                    isPlaying = false;
                }
            }
        }
    }
}

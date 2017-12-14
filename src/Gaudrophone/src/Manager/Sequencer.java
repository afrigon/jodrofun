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
import java.util.ArrayList;

public class Sequencer implements Runnable {
    private final double PRESSED_THRESHOLD = 0.5;
    private final Metronome metronome = new Metronome();
    private Song song = null;
    private int bpm = 120;
    private boolean isPlaying = false;
    private long lastTimeUpdate = 0;
    private double currentStep = 0;
    private final ArrayList<Sound> missingSounds = new ArrayList<>();
    private boolean muted = false;
    private double totalSteps = 0;
    
    public int getBPM() {
        return this.bpm;
    }
    
    public void setBPM(int bpm) {
        this.bpm = Math.max(1, Math.min(bpm, 600));
        this.metronome.adjustBPM(this.bpm);
        // need to change the time left label
    }
    
    public void setSong(Song song) {
        this.song = song;
        this.setBPM(song.getBPM());
        for (PlayableChord chord : song.getChords()) {
            totalSteps += chord.getRelativeSteps();
        }
        totalSteps += song.getChords().getLast().getLength();
    }
    
    public void setMuted(boolean active) {
        muted = active;
    }
    
    public void setPosition(double step) {
        currentStep = step;
    }
    
    public String getTimeLeft() {
        double time = totalSteps * 60.0 / bpm;
        return String.format("%d:$%02d", (int) Math.floor(time / 60.0), (int) Math.floor(time % 60.0));
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
    
    public boolean isMuted() {
        return this.muted;
    }
    
    public boolean toggleMute() {
        this.muted = !this.muted;
        return this.muted;
    }
    
    private double getElapsedTime() {
        long now = System.nanoTime();
        long delta = now - this.lastTimeUpdate;
        this.lastTimeUpdate = now;
        return ((double) delta) / 1000000000.0;
    }
    
    public void play() {
        this.isPlaying = true;
        new Thread(this).start();
        GaudrophoneController.getController().delegate.didStartPlayingSong();
    }
    
    public void pause() {
        this.isPlaying = false;
        GaudrophoneController.getController().getSoundService().closeAll();
        GaudrophoneController.getController().delegate.didPauseSong();
    }
    
    public void stop() {
        this.isPlaying = false;
        this.currentStep = 0;
        GaudrophoneController.getController().getSoundService().closeAll();
        GaudrophoneController.getController().delegate.didStopPlayingSong();
    }
    
    public boolean hasNearNote(double frequency) {
        // get note(s) that are supposed to be played very soon (or has been played)
        double chordPlayStep = 0;
        for (PlayableChord chord : this.song.getChords()) {
            chordPlayStep += chord.getRelativeSteps();
            
            double deltaStepDifference = Math.abs(this.currentStep - chordPlayStep);
            double deltaTimeDiff = deltaStepDifference * 60.0 / ((double)this.bpm);
            
            if (deltaTimeDiff < this.PRESSED_THRESHOLD) {
                for (PlayableNote note : chord.getNotes()) {
                    if (note.getFrequency() == frequency) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void createMissingSound(PlayableNote note) {
        Sound sound = new SynthesizedSound(note);
        this.missingSounds.add(sound);
        GaudrophoneController.getController().getSoundService().play(sound);
    }
    
    private void playMissingSound(PlayableNote note) {
        for (Sound sound : this.missingSounds) {
            if (sound.getPlayableNote() == note) {
                GaudrophoneController.getController().getSoundService().play(sound);
                return;
            }
        }
        createMissingSound(note);
    }
    
    @Override
    public void run() {
        missingSounds.clear();
        getElapsedTime(); // call the method to init lastTimeUpdate
        while (isPlaying) {
            double previousStep = currentStep;
            currentStep += getElapsedTime() * ((double) bpm) / 60.0; // calculate elapsed steps
            
            double chordPlayStep = 0;
            double chordEndStep = 0;
            
            for (PlayableChord chord : this.song.getChords()) {
                chordPlayStep += chord.getRelativeSteps();
                chordEndStep = chordPlayStep + chord.getLength();
                
                if ((chordPlayStep > previousStep) && (chordPlayStep <= currentStep)) {
                    for (PlayableNote note : chord.getNotes()) {
                        if (!GaudrophoneController.getController().playNote(note)) {
                            playMissingSound(note);
                        }
                    }
                }
                
                if ((chordEndStep > previousStep) && (chordEndStep <= currentStep)) {
                    for (PlayableNote note : chord.getNotes()) {
                        if (!GaudrophoneController.getController().releaseNote(note)) {
                            for (Sound sound : missingSounds) {
                                if (sound.getPlayableNote() == note) {
                                    GaudrophoneController.getController().getSoundService().release(sound);
                                }
                            }
                        }
                    }
                }
            }
            
            double roundPreviousStep = Math.floor(chordEndStep);
            double roundCurrentStep = Math.floor(currentStep);
            if (roundPreviousStep != roundCurrentStep) {
                // FRIGON HELPZ
            }
            
            if (this.currentStep > chordEndStep) {
                this.stop();
            }
        }
    }

    public void stopAll() {
        this.stop();
        this.metronome.close();
        this.muted = false;
    }

    public void togglePlay() {
        if (this.isPlaying) {
            this.pause();
        } else {
            this.play();
        }
        
    }
}

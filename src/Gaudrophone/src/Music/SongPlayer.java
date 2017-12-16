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
package Music;

import Instrument.KeyState;
import Manager.GaudrophoneController;
import Manager.SequencerManager;
import java.util.ArrayList;

public class SongPlayer extends Sequencer {
    private boolean muted = false;
    private final ArrayList<Sound> missingSounds = new ArrayList<>();
    private double playbackSpeed = 1;
    
    public SongPlayer(SequencerManager manager) {
        super(manager);
    }
    
    public double getPlaybackSpeed() {
        return this.playbackSpeed;
    }
    
    public void setPlaybackSpeed(double speed) {
        this.playbackSpeed = speed;
    }
    
    public double getAlteredBPM() {
        return this.manager.getBPM() * this.playbackSpeed;
    }
    
    public Song getSong() {
        return this.song;
    }
    
    @Override
    public void setSong(Song song) {
        super.setSong(song);
        for(PlayableChord chord : song.getChords()){
            for(PlayableNote note : chord.getNotes()) {
                Instrument.Key key = GaudrophoneController.getController().getKeyFromPlayableNote(note);
                if(key != null) {
                    key.addState(KeyState.presentInSong);
                }
            }
        }
        GaudrophoneController.getController().getCanvasManager().getDelegate().shouldRedraw();
    }
    
    public boolean isMuted() {
        return this.muted;
    }
    
    public void setMuted(boolean active) {
        this.muted = active;
    }
    
    public void setPosition(int percent) {
        if (!this.isPlaying) {
            this.currentStep = (double)percent*this.totalSteps/100;
            GaudrophoneController.getController().getDelegate().updateMediaPlayerSlider(percent);
        }
    }
    
    public String getTimeLeft() {
        double time = (this.totalSteps-this.currentStep) * 60.0 / this.getAlteredBPM();
        return String.format("%d:%02d", (int) Math.floor(time / 60.0), (int) Math.ceil(time % 60.0));
    }
    
    public boolean toggleMute() {
        this.muted = !this.muted;
        if (this.muted) {
            GaudrophoneController.getController().getSoundService().closeAll();
        }
        return this.muted;
    }
    
    public void togglePlay() {
        if (this.isPlaying) {
            this.pause();
        } else {
            this.play();
        }
    }
    
    @Override
    public void play() {
        this.isPlaying = true;
        new Thread(this).start();
        GaudrophoneController.getController().getDelegate().didStartPlayingSong();
    }
    
    @Override
    public void pause() {
        this.isPlaying = false;
        GaudrophoneController.getController().getSoundService().closeAll();
        GaudrophoneController.getController().getDelegate().didPauseSong();
    }
    
    @Override
    public void stop() {
        this.isPlaying = false;
        this.currentStep = 0;
        GaudrophoneController.getController().getSoundService().closeAll();
        GaudrophoneController.getController().getDelegate().didStopPlayingSong();
        GaudrophoneController.getController().getDelegate().updateMediaPlayerSlider(0);
    }
    
    private double getElapsedTime() {
        long now = System.nanoTime();
        long delta = now - this.lastTimeUpdate;
        this.lastTimeUpdate = now;
        return ((double) delta) / 1000000000.0;
    }
    
    public boolean hasNearNote(double frequency) {
        // get note(s) that are supposed to be played very soon (or has been played)
        double chordPlayStep = 0;
        for (PlayableChord chord : this.song.getChords()) {
            chordPlayStep += chord.getRelativeSteps();
            
            double deltaStepDifference = Math.abs(this.currentStep - chordPlayStep);
            double deltaTimeDiff = deltaStepDifference * 60.0 / ((double)this.getAlteredBPM());
            
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
        this.createMissingSound(note);
    }
    
    @Override
    public void run() {
        this.missingSounds.clear();
        this.getElapsedTime(); // call the method to init lastTimeUpdate
        while (isPlaying) {
            double previousStep = this.currentStep;
            this.currentStep += this.getElapsedTime() * ((double) this.getAlteredBPM()) / 60.0; // calculate elapsed steps
            
            double chordPlayStep = 0;
            double chordEndStep = 0;
            
            for (PlayableChord chord : this.song.getChords()) {
                chordPlayStep += chord.getRelativeSteps();
                chordEndStep = chordPlayStep + chord.getLength();
                
                if ((chordPlayStep > previousStep) && (chordPlayStep <= currentStep)) {
                    for (PlayableNote note : chord.getNotes()) {
                        if (!GaudrophoneController.getController().playNote(note)) {
                            this.playMissingSound(note);
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
                GaudrophoneController.getController().getDelegate().updateMediaPlayerSlider(this.currentStep*100/this.totalSteps);
            }
            
            if (this.currentStep > chordEndStep) {
                this.stop();
            }
        }
    }
}

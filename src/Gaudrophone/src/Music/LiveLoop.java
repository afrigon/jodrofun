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

import Manager.GaudrophoneController;
import Manager.SequencerManager;

public class LiveLoop extends Sequencer {
    private int loopIndex = -1;
    public LiveLoop(SequencerManager manager, int index) {
        super(manager);
        this.loopIndex = index;
    }
/*import Manager.GaudrophoneController;

        @Override
    public void play() {
        this.isPlaying = true;
        new Thread(this).start();
        GaudrophoneController.getController().getDelegate().didStartPlayingSong();
    }
    
    @Override
    public void pause() {
        this.stop();
    }
    }*/
    
    @Override
    public void stop() {
        this.isPlaying = false;
        this.currentStep = 0;
        GaudrophoneController.getController().getDelegate().liveLoopDidStop(this.loopIndex);
    }
    
    private void restart() {
        this.currentStep = 0;
    }
    
    private double getElapsedTime() {
        long now = System.nanoTime();
        long delta = now - this.lastTimeUpdate;
        this.lastTimeUpdate = now;
        return ((double) delta) / 1000000000.0;
    }
    
    @Override
    public void run() {
        getElapsedTime(); // call the method to init lastTimeUpdate
        while (isPlaying) {
            double previousStep = currentStep;
            currentStep += getElapsedTime() * ((double) this.song.getBPM()) / 60.0; // calculate elapsed steps
            
            double chordPlayStep = 0;
            double chordEndStep = 0;
            
            for (PlayableChord chord : this.song.getChords()) {
                chordPlayStep += chord.getRelativeSteps();
                chordEndStep = chordPlayStep + chord.getLength();
                
                if ((chordPlayStep > previousStep) && (chordPlayStep <= currentStep)) {
                    for (PlayableNote note : chord.getNotes()) {
                        GaudrophoneController.getController().playNote(note);
                    }
                }
                
                if ((chordEndStep > previousStep) && (chordEndStep <= currentStep)) {
                    for (PlayableNote note : chord.getNotes()) {
                        GaudrophoneController.getController().releaseNote(note);
                    }
                }
            }
            
            if (this.currentStep > chordEndStep) {
                this.restart();
            }
        }
    }
/*    public void stopRecording() {
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
    }*/
}

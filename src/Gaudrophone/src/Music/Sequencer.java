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

import Manager.SequencerManager;

public abstract class Sequencer implements Runnable {
    protected SequencerManager manager;
    protected final double PRESSED_THRESHOLD = 0.5;
    protected boolean isPlaying = false;
    protected Song song = null;
    protected long lastTimeUpdate = 0;
    protected double currentStep = 0;
    protected double totalSteps = 0;
    
    public Sequencer(SequencerManager manager) {
        this.manager = manager;
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    
    public boolean hasSong() {
        return this.song != null;
    }
    
    public void setSong(Song song) {
        this.song = song;
        this.manager.setBPM(song.getBPM());
        this.totalSteps = 0;
        this.currentStep = 0;
        for (PlayableChord chord : song.getChords()) {
            this.totalSteps += chord.getRelativeSteps();
        }
        this.totalSteps += song.getChords().getLast().getLength();
    }
    
    public abstract void play();
    public abstract void pause();
    public abstract void stop();
}

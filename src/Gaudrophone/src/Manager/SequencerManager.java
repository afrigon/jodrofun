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
import java.util.LinkedList;

public class SequencerManager {
    private final Metronome metronome = new Metronome();
    private final LinkedList<Sequencer> liveloops = new LinkedList<>();
    private final SongPlayer sequencer;
    private int bpm = 120;
    
    public SequencerManager() {
        this.sequencer = new SongPlayer(this);
    }
    
    public void addLiveLoop(LiveLoop ll) {
        this.liveloops.add(ll);
    }
    
    public void removeLiveLoop(int index) {
        this.liveloops.remove(index);
    }
    
    public SongPlayer getSequencer() {
        return this.sequencer;
    }
    
    public int getBPM() {
        return this.bpm;
    }
    
    public void setBPM(int bpm) {
        this.bpm = Math.max(1, Math.min(bpm, 600));
        this.metronome.adjustBPM(this.bpm);
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
    
    public void setMetronomeState(boolean state) {
        if (state) {
            this.metronome.start(this.bpm);
        } else {
            this.metronome.close();
        }
    }
}

/*
 * The MIT License
 *
 * Copyright 2017 Studio.
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
import java.util.HashMap;

class ChordData {
    public PlayableChord chord;
    public double startTime;
    public ChordData(PlayableChord chord, double startTime) {
        this.chord = chord;
        this.startTime = startTime;
    }
}

public class LiveLoopRecorder {
    private double lastStartTime = 0;
    private Song song;
    private final HashMap<Sound, ChordData> playingSounds = new HashMap<>();
    private int liveLoopIndex = -1;
    private LiveLoopRecorderState state = LiveLoopRecorderState.idle;
    
    public LiveLoopRecorderState getState() {
        return this.state;
    }

    public void setState(LiveLoopRecorderState state) {
        this.state = state;
    }
    
    public LiveLoopRecorderState getStateForIndex(int index) {
        if (index == this.liveLoopIndex) {
            return this.state;
        }
        return LiveLoopRecorderState.idle;
    }
    
    public void startRecording(int index) {
        this.state = LiveLoopRecorderState.waiting;
        this.song = new Song();
        this.song.setBPM(GaudrophoneController.getController().getSequencerManager().getBPM());
        this.liveLoopIndex = index;
        this.lastStartTime = 0;
    }
    
    public Song stopRecording() {
        this.state = LiveLoopRecorderState.idle;
        this.liveLoopIndex = -1;
        this.playingSounds.clear();
        return this.song;
    }
    
    public void addSound(Sound sound) {
        if (this.state == LiveLoopRecorderState.waiting || this.state == LiveLoopRecorderState.recording) {
            this.state = LiveLoopRecorderState.recording;
            GaudrophoneController.getController().getDelegate().liveLoopDidStartRecording(this.liveLoopIndex);
            PlayableNote note = sound.getPlayableNote();
            PlayableChord chord = new PlayableChord();
            chord.addNote(note);
            double time = System.currentTimeMillis();
            chord.setRelativeSteps(this.lastStartTime == 0 ? this.lastStartTime : (time-this.lastStartTime)*this.song.getBPM()/60000);
            this.playingSounds.put(sound, new ChordData(chord, time));
            this.lastStartTime = time;
        }
    }
    
    public void stopSound(Sound sound) {
        if (this.state == LiveLoopRecorderState.recording) {
            ChordData data = this.playingSounds.get(sound);
            if (data != null) {
                double time = System.currentTimeMillis();
                data.chord.setLength((time-data.startTime) * this.song.getBPM()/60000);
                this.song.addChord(data.chord);
                this.playingSounds.remove(sound);
            }
        }
    }
}
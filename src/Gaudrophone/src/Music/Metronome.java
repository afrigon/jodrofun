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

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Metronome {
    private int bpm = 120;
    public boolean isRunning = false;
    private AudioInputStream stream_high;
    private AudioInputStream stream_low;
    private Clip clip_high;
    private Clip clip_low;
    private boolean state = false;
    
    public void start(int bpm) {
        this.bpm = bpm;
        this.isRunning = true;
        
        try {
            this.clip_high = AudioSystem.getClip();
            this.clip_low = AudioSystem.getClip();
            this.stream_high = AudioSystem.getAudioInputStream(getClass().getResource("/resources/metronome_high.wav"));
            this.stream_low = AudioSystem.getAudioInputStream(getClass().getResource("/resources/metronome_low.wav"));
            this.clip_high.open(this.stream_high);
            this.clip_low.open(this.stream_low);
            this.cosmos();        
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {}
    }
    
    public void adjustBPM(int bpm) {
        this.bpm = bpm;
    }
    
    //does things
    public void cosmos() {
        if (this.isRunning) {
            this.state = !this.state;
            ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
            es.schedule(new Work(this.state ? this.clip_high : this.clip_low, this), (int)((double)60000/this.bpm), TimeUnit.MILLISECONDS);
        } else {
            this.clip_high.stop();
            this.clip_low.stop();
            this.clip_high.close();
            this.clip_low.close();
        }
    }

    public void close() {
        this.isRunning = false;
    }
}

class Work implements Runnable {
    private final Clip clip;
    private final Metronome metronome;
    
    public Work(Clip clip, Metronome metronome) {
        this.clip = clip;
        this.metronome = metronome;
    }
    
    @Override
    public void run() {
        clip.setMicrosecondPosition(0);
        clip.start();
        this.metronome.cosmos();
    }
}
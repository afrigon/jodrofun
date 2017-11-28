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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public abstract class Sound implements Runnable, java.io.Serializable {
    private static final SoundService SOUNDSERVICE = SoundService.get();
    
    protected double volume;
    protected PlayableNote playableNote = new PlayableNote();
    protected Envelope envelope = null;
    protected SoundType type = null;
    protected SourceDataLine line = null;
    
    protected boolean playing = false;
    protected boolean released = false;
    
    public void play(SourceDataLine newLine) {
        released = false;
        playing = true;
        line = newLine;
        try {
            line.open(getAudioFormat());
            line.start();
            
            // start thread
            new Thread(this).start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void release() {
        released = true;
    }
    
    public void kill() {
        playing = false;
        line.close();
        SOUNDSERVICE.killed();
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    public abstract AudioFormat getAudioFormat();
    
    @Override
    public abstract void run();
    
    // Constructors
    public Sound() {
        volume = 1;
        envelope = new Envelope();
    }
    
    // Setters
    public void setVolume(double newVolume) {
        volume = newVolume;
    }
    
    public void setEnvelope(Envelope newEnvelope) {
        envelope = newEnvelope;
    }
    
    // Getters
    public double getVolume() {
        return volume;
    }
    
    public Envelope getEnvelope() {
        return envelope;
    }
    
    public SoundType getType() {
        return type;
    }
    
    public PlayableNote getPlayableNote() {
        return this.playableNote;
    }
}

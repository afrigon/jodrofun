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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;

public abstract class Sound implements java.io.Serializable {
    protected double volume;
    protected PlayableNote playableNote = new PlayableNote();
    protected Envelope envelope = null;
    protected SoundType type = null;
    protected Clip clip;
    
    boolean playing = false;
    
    // Constructors
    public Sound() {
        try {
            clip = AudioSystem.getClip();
//            clip.addLineListener((LineEvent le) -> {
//                if (le.getType() == LineEvent.Type.STOP) {
//                    if (playing) {
//                        clip.setFramePosition(getLoopFrame());
//                        clip.start();
//                    }
//                }
//            });
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        volume = 1;
        envelope = new Envelope();
    }
    
    protected void refreshClip() {
        try {
            if (clip.isOpen())
                clip.close();
            
            clip.open(getPlayingStream());
            clip.setFramePosition(0);
            
            int loopFrame = getLoopFrame();
            if (loopFrame > 0) {
                clip.setLoopPoints(loopFrame, getLastLoopFrame());
            }
            
            
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void playClip() {
        playing = true;
        clip.setFramePosition(0);
        if (getLoopFrame() > 0) {
            //clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }
    
    public void releaseClip() {
        playing = false;
    }
    
    public void stopClip() {
        
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
    
    public abstract AudioInputStream getPlayingStream();
    public abstract AudioInputStream getReleasedStream(double timePlayed);
    public abstract int getLoopFrame();
    public abstract int getLastLoopFrame();
}

/*
 * The MIT License
 *
 * Copyright 2017 Olivier.
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
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Olivier
 */
public class EnvelopedClip extends Thread {
    private Clip clip = null;
    private Envelope envelope = null;
    private FloatControl volumeControl = null;
    private long startInstant = 0;
    private long releaseInstant = 0;
    private boolean released = false;
    
    public EnvelopedClip(Clip newClip, AudioInputStream audioInputStream, Envelope newEnvelope) {
        try {
            clip = newClip;
            envelope = newEnvelope;
            
            
            clip.open(audioInputStream);
            
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            
            clip.addLineListener((LineEvent le) -> {
                if (le.getType() == LineEvent.Type.STOP) {
                    System.out.println("clip close");
                    end();
                }
            });
            
            // set a loop
            //clip.setLoopPoints(0, 20000);
            
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(-80.0f);
            
            
        } catch (LineUnavailableException ex) {
            Logger.getLogger(EnvelopedClip.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnvelopedClip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        released = false;
        startInstant = System.nanoTime();
        
        
        setVolume(-80.0f);
        volumeControl.shift(-80.0f, 0f, 4000000);
        
        clip.start();
        
//        while (clip != null && clip.isOpen()) {
//            if (released) { // if key is released
//                double timePassed = ((double) System.nanoTime() - releaseInstant) / 1000000;
//                float newVolume = envelope.getReleaseVolume(timePassed); 
//                if (newVolume <= -80.0f) {
//                    end();
//                } else {
//                    setVolume(newVolume);
//                }
//                System.out.println("new volume : " + newVolume);
//
//            } else { // while key is played
//                if (startInstant != 0) {
//                    double timePassed = ((double) System.nanoTime() - startInstant) / 1000000;
//                    System.out.println("timePassed : " + timePassed);
//                    setVolume(envelope.getPlayingVolume(timePassed));
//                }
//            }
//            
//        }
        
        System.out.println("while loop done");
    }
    
    public void release() {
        releaseInstant = System.nanoTime();
        released = true;
        System.out.println("eclip release");
    }
    
    public void end() {
        System.out.println("clip stop");
        clip.stop();
        startInstant = 0;
        releaseInstant = 0;
        released = false;
        clip = null;
    }
    
    private void setVolume(float vol) {
        volumeControl.setValue(vol);
    }
    
    // convert the alpha (0 to 1) given by the amplitude to decibels
    private static float convertAmplitudeToDecibels(double alpha) { 
        return 10.0f * (float) (Math.log10(alpha));
    }
}


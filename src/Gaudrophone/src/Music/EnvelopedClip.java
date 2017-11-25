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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Olivier
 */
public class EnvelopedClip {
    private Clip clip = null;
    private long startInstant = 0;
    private long releaseInstant = 0;
    private boolean released = false;
    
    public EnvelopedClip(Clip newClip, AudioInputStream audioInputStream, int loopFrame) throws LineUnavailableException, IOException {
        clip = newClip;

        clip.open(audioInputStream);
        if (loopFrame > 0) {
            clip.setLoopPoints(loopFrame, -1);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void start() {
        released = false;
        startInstant = System.nanoTime();
        
        clip.start();
    }
    
    public double getTimePlayed() {
        return ((double)(System.nanoTime() - startInstant))/1000000000;
    }
    
    public void release(Clip newClip, AudioInputStream audioInputStream) throws LineUnavailableException, IOException {
        newClip.open(audioInputStream);
            
        releaseInstant = System.nanoTime();
        double timePlayed = ((double)(releaseInstant - startInstant))/1000000000;
        newClip.start();

        clip.close();

        clip = newClip;
        released = true;
        System.out.println("clip release");
    }
    
    public void end() {
        System.out.println("clip stop");
        clip.close();
        startInstant = 0;
        releaseInstant = 0;
        released = false;
        clip = null;
    }
}


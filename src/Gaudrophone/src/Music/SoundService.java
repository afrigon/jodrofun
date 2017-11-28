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


import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;

public class SoundService {
    private static final SoundService SHARED = new SoundService();
    private final LinkedList<Sound> playingSounds;
    private final int polyphony = 32;
    private int soundPlayingQty = 0;
    
    private SoundService() {
        playingSounds = new LinkedList<>();
        
        if (!AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            System.out.println("OUPUT IS NOT SUPPORTED");
        }
    }
    
    public static SoundService get() {
        return SHARED;
    }
    
    public void play(Sound sound) {
        if (soundPlayingQty == polyphony) // kill the least recent sound
            closeLastSound();
        
        if (sound.isPlaying())
            sound.kill();
        
        soundPlayingQty++;
        SourceDataLine line;
        try {
            line = AudioSystem.getSourceDataLine(sound.getAudioFormat());
            sound.play(line);
            playingSounds.add(sound);

        } catch (LineUnavailableException ex) {
            Logger.getLogger(SoundService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void release(Sound sound) {
        if (sound.isPlaying())
            sound.release();
    }
    
    public void killed() {
        soundPlayingQty--;
    }
    
    private void closeLastSound() {
        if (playingSounds.size() > 0) {
            Sound sound = playingSounds.remove();
            if (sound != null)
                sound.kill();
        }
    }
    
    public void closeAll() {
        for (Sound sound : playingSounds) {
            sound.kill();
        }
    }
}

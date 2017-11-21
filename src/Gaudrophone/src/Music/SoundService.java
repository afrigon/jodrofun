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

import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

public class SoundService {
    public static SoundService shared = new SoundService();
    private final LinkedHashMap<Sound, EnvelopedClip> clips = new LinkedHashMap();
    private final int polyphony = 32;
    
    private SoundService() {
        if (!AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            System.out.println("OUPUT IS NOT SUPPORTED");
        }
    }
    
    public void play(Sound sound) {
        if (clips.size() < polyphony) {
            try {
                EnvelopedClip clip = new EnvelopedClip(AudioSystem.getClip(), sound.getAudioInputStream(), sound.getEnvelope());
                
                clips.put(sound, clip);
                clip.start();
                
            } catch (LineUnavailableException ex) {
                Logger.getLogger(SoundService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Sound firstSound = clips.keySet().iterator().next();
            EnvelopedClip firstClip = clips.remove(firstSound);
            firstClip.end();
            play(sound);
        }
    }
    
    public void release(Sound sound) {
        EnvelopedClip clip = clips.get(sound);
        clip.release();
    }
    
    public void close() {
        while (clips.size() > 0) {
            Sound firstSound = clips.keySet().iterator().next();
            EnvelopedClip firstClip = clips.remove(firstSound);
            firstClip.end();
        }
    }
}

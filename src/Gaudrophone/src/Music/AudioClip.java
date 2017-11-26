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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioClip extends Sound {
    private String path;
    private double speed;
    
    private transient AudioInputStream audioInputStream = null;
    
    // Constructors
    public AudioClip() {
        type = SoundType.audioClip;
        speed = 1;
    }
    
    @Override
    public AudioInputStream getPlayingStream() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AudioInputStream getReleasedStream(double timePlayed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLoopFrame() {
        return -1;
    }
    
    // Getters
    public String getPath() {
        return path;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    // Setters
    public Boolean setPath(String newPath) {
        path = newPath;
                
        try {
            File file = new File(newPath);
            
            AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
            AudioFormat audioFormat = format.getFormat();
            
            InputStream inputStream = new FileInputStream(file);
            audioInputStream = new AudioInputStream(inputStream, audioFormat, file.length());
            return true;
        } catch (UnsupportedAudioFileException | IOException ex) {
            return false;
        } 
    }
    
    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }
}

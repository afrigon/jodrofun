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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioClip extends Sound {
    private String path;
    private double speed;
    private AudioFormat audioFormat;
    private byte[] buffer;
    
    //private AudioInputStream audioInputStream = null;
    
    // Constructors
    public AudioClip() {
        type = SoundType.audioClip;
        speed = 1;
        
        try {
            File file = new File(newPath);
            
            AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
            audioFormat = format.getFormat();
            
            InputStream inputStream = new FileInputStream(file);
            
            AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFormat, file.length());
            
            int byteRead = 0;
            int offset = 0;
            int fileLength = (int) file.length();
            buffer =  new byte[(int) file.length()];
            System.out.println("file length : " + fileLength);
            
            while (byteRead <= 0) {
                //System.out.println("reading byte : " + byteRead);
                //System.out.println("offset byte : " + offset);
                byteRead = audioInputStream.read(buffer, offset, fileLength - offset);
                offset += byteRead;
            }
            
            
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("error clip");
            Logger.getLogger(AudioClip.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("error clip");
            Logger.getLogger(AudioClip.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public AudioInputStream getPlayingStream() {
        
        int channels = audioFormat.getChannels();
        int frameSize = audioFormat.getFrameSize();
        int sampleSizeInBits = audioFormat.getSampleSizeInBits();
        double sampleRate = audioFormat.getSampleRate();
        
        int sampleSizeInByte = frameSize/channels;
        boolean isBigEndian = audioFormat.isBigEndian();
        
        System.out.println("AUDIO FORMAT ///// Channels : " + channels + " //// Frame size : " + frameSize + " //// Sample size in bits : " + sampleSizeInBits);
        System.out.println("//// Sample size in bytes : " + sampleSizeInByte + " //// Big Endian : " + isBigEndian);
        
        byte[] envelopedBuffer = new byte[buffer.length];
        
        for (int i = 0; i < buffer.length/frameSize; i++) {
            double time = ((double) i)/sampleRate;
            
            double amp = volume * envelope.getPlayingAmplitude(time * 1000.0);
            
            for (int j = 0; j < channels; j++) {
                if (sampleSizeInByte == 1) {
                    envelopedBuffer[frameSize * i + j] = (byte) (((double) buffer[frameSize * i + j]) * amp);
                    
                } else if (sampleSizeInByte == 2) {
                    int firstBytePosition = frameSize * i + j * channels;
                    short value = (short) ((buffer[firstBytePosition + 1] << 8) + (buffer[firstBytePosition] & 0xff));
                    
                    value = (short) (amp * ((double) value));
                    
                    envelopedBuffer[firstBytePosition] = (byte) (value & 0xff);
                    envelopedBuffer[firstBytePosition + 1] = (byte) ((value >> 8) & 0xff);
                }
            }
            
            
            //buffer[frameSize * i] = (byte) (volume * envelope.getPlayingAmplitude(time * 1000.0));
            //buffer[frameSize * i + 1] = buffer[channels * i];
        }
        
        System.out.println("sending stream");
        return new AudioInputStream(new ByteArrayInputStream(envelopedBuffer, 0, envelopedBuffer.length), audioFormat, envelopedBuffer.length);
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
    public Boolean setPath(String path) {
        this.path = path;
        
        if (path == null) {
            this.audioInputStream = null;
            return true;
        }
        
        try {
            File file = new File(path);
            
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

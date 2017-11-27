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
    private String path = null;
    private double speed = 1;
    private AudioFormat audioFormat = null;
    private byte[] buffer = null;
    private double clipTime = 0;
    
    // Constructors
    public AudioClip() {
        type = SoundType.audioClip;
        speed = 1;
    }
    
    public AudioClip(String path) {
        type = SoundType.audioClip;
        speed = 1;
        this.setPath(path);
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
                    envelopedBuffer[i * frameSize + j] = (byte) (((double) buffer[i * frameSize + j]) * amp);
                    
                } else if (sampleSizeInByte == 2) {
                    int firstBytePosition = i * frameSize + j * channels;
                    if (isBigEndian) {
                        System.out.println("Big Endian file might not be supported.");
                        short value = (short) ((buffer[firstBytePosition] << 8) + (buffer[firstBytePosition + 1] & 0xff));

                        value = (short) (amp * ((double) value));

                        envelopedBuffer[firstBytePosition + 1] = (byte) (value & 0xff);
                        envelopedBuffer[firstBytePosition] = (byte) ((value >> 8) & 0xff);
                        
                    } else {
                        short value = (short) ((buffer[firstBytePosition + 1] << 8) + (buffer[firstBytePosition] & 0xff));

                        value = (short) (amp * ((double) value));

                        envelopedBuffer[firstBytePosition] = (byte) (value & 0xff);
                        envelopedBuffer[firstBytePosition + 1] = (byte) ((value >> 8) & 0xff);
                    }
                }
            }
        }
        
        return new AudioInputStream(new ByteArrayInputStream(envelopedBuffer, 0, envelopedBuffer.length), audioFormat, envelopedBuffer.length);
    }

    @Override
    public AudioInputStream getReleasedStream(double timePlayed) {
        int playingFrame = (int) (timePlayed * this.audioFormat.getSampleRate());
        int remainingFrames = ((int) (this.buffer.length / this.audioFormat.getFrameSize())) - playingFrame;
        
        if (timePlayed < this.clipTime) {
            
            int channels = this.audioFormat.getChannels();
            int frameSize = this.audioFormat.getFrameSize();
            double sampleRate = this.audioFormat.getSampleRate();
            
            int sampleSizeInByte = frameSize/channels;
            boolean isBigEndian = this.audioFormat.isBigEndian();
        
            double milliTimePlayed = timePlayed * 1000;
            
            byte[] envelopedBuffer = new byte[remainingFrames * sampleSizeInByte * channels];
            
            for (int i = 0; i < remainingFrames; i++) {
                double time = ((double) i)/sampleRate;
                
                double amp = this.volume * this.envelope.getReleasedAmplitude(time * 1000.0, milliTimePlayed);
                
                for (int j = 0; j < channels; j++) {
                    if (sampleSizeInByte == 1) {
                        envelopedBuffer[frameSize * i + j] = (byte) (((double) this.buffer[frameSize * i + j]) * amp);

                    } else if (sampleSizeInByte == 2) {
                        int firstByteOriginalPosition = (playingFrame + i) * frameSize + j * channels;
                        int firstBytePosition = frameSize * i + j * channels;
                        if (isBigEndian) {
                            System.out.println("Big Endian file might not be supported.");
                            short value = (short) ((this.buffer[firstByteOriginalPosition] << 8) + (this.buffer[firstByteOriginalPosition + 1] & 0xff));

                            value = (short) (amp * ((double) value));

                            envelopedBuffer[firstBytePosition + 1] = (byte) (value & 0xff);
                            envelopedBuffer[firstBytePosition] = (byte) ((value >> 8) & 0xff);

                        } else {
                            short value = (short) ((this.buffer[firstByteOriginalPosition + 1] << 8) + (this.buffer[firstByteOriginalPosition] & 0xff));

                            value = (short) (amp * ((double) value));

                            envelopedBuffer[firstBytePosition] = (byte) (value & 0xff);
                            envelopedBuffer[firstBytePosition + 1] = (byte) ((value >> 8) & 0xff);
                        }
                    }
                }
            }
            return new AudioInputStream(new ByteArrayInputStream(envelopedBuffer, 0, envelopedBuffer.length), this.audioFormat, envelopedBuffer.length);
        }
        return new AudioInputStream(new ByteArrayInputStream(new byte[2], 0, 2), this.audioFormat, 2);
    }

    @Override
    public int getLoopFrame() {
        return -1;
    }
    
    // Getters
    public String getPath() {
        return this.path;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    // Setters
    public Boolean setPath(String path) {
        this.path = path;
        
        if (path == null) {
            this.buffer = null;
            this.audioFormat = null;
            this.clipTime = 0;
            return true;
        }
        
        try {
            File file = new File(path);
            
            AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
            audioFormat = format.getFormat();
            
            InputStream inputStream = new FileInputStream(file);
            
            AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFormat, file.length());
            
            int byteRead = 0;
            int offset = 0;
            int fileLength = (int) file.length();
            buffer =  new byte[(int) file.length()];
            
            while (byteRead <= 0) {
                byteRead = audioInputStream.read(buffer, offset, fileLength - offset);
                offset += byteRead;
            }
            
            clipTime = ((double)buffer.length / (double)audioFormat.getFrameSize()) / (double)audioFormat.getSampleRate();
            return true;
        } catch (UnsupportedAudioFileException | IOException ex) {
            return false;
        }
    }
    
    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }
}
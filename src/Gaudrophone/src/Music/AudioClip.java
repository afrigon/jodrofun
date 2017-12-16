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

import Music.Waveform.WaveForm;
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
    private String path = null;
    private double speed = 1;
    private transient AudioFormat audioFormat = null;
    private byte[] originalBuffer = null;
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        try {
            AudioFileFormat format = AudioSystem.getAudioFileFormat(new File(path));
            audioFormat = format.getFormat();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioClip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Constructors
    public AudioClip() {
        type = SoundType.audioClip;
    }
    
    public AudioClip(String path) {
        type = SoundType.audioClip;
        setPath(path);
    }
    
    public AudioClip(PlayableNote note) {
        type = SoundType.audioClip;
        playableNote = note;
    }
    
    @Override
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    @Override
    public void run() {
        int channels = audioFormat.getChannels();
        int frameSize = audioFormat.getFrameSize();
        double sampleRate = audioFormat.getSampleRate();
        
        int sampleSizeInByte = frameSize/channels;
        boolean isBigEndian = audioFormat.isBigEndian();
        
        int bufferSize = (int) (sampleRate / 20);
        int sampleCount = 0;
        
        // play the attack, decay and sustain part until sound is killed or released
        while (playing && (!released)) {
            byte[] buffer = new byte[frameSize * bufferSize];
            
            for (int i = 0; i < bufferSize; i++) {
                int isampleCount = (i + sampleCount);
                
                double time = (((double) (isampleCount))/WaveForm.SAMPLE_RATE);
                
                double amp = volume * envelope.getPlayingAmplitude(time * 1000.0);
                
                if (isampleCount >= originalBuffer.length) {
                    for (int j = 0; j < channels; j++) {
                        if (sampleSizeInByte == 1) {
                            buffer[isampleCount * frameSize + j] = 0;

                        } else if (sampleSizeInByte == 2) {
                            buffer[isampleCount * frameSize + j * channels + 1] = 0;
                            buffer[isampleCount * frameSize + j * channels] = 0;
                        }
                    }
                } else {
                    for (int j = 0; j < channels; j++) {
                        if (sampleSizeInByte == 1) {
                            buffer[i * frameSize + j] = (byte) (((double) originalBuffer[isampleCount * frameSize + j]) * amp);

                        } else if (sampleSizeInByte == 2) {
                            int firstBytePosition = i * frameSize + j * channels;
                            int ifirstBytePosition = isampleCount * frameSize + j * channels;
                            if (isBigEndian) {
                                System.out.println("Big Endian file might not be supported.");
                                short value = (short) ((originalBuffer[ifirstBytePosition] << 8) + (originalBuffer[ifirstBytePosition + 1] & 0xff));

                                value = (short) (amp * ((double) value));

                                buffer[firstBytePosition + 1] = (byte) (value & 0xff);
                                buffer[firstBytePosition] = (byte) ((value >> 8) & 0xff);

                            } else {
                                short value = (short) ((originalBuffer[ifirstBytePosition + 1] << 8) + (originalBuffer[ifirstBytePosition] & 0xff));

                                value = (short) (amp * ((double) value));

                                buffer[firstBytePosition] = (byte) (value & 0xff);
                                buffer[firstBytePosition + 1] = (byte) ((value >> 8) & 0xff);
                            }
                        }
                    }
                }
            }
            sampleCount += bufferSize;
            
            line.write(buffer, 0, buffer.length);
        }
        
        int sampleCountReleased = 0; // keeps the total number of samples played
        double milliTimePlayed = (double) sampleCount / (double) WaveForm.SAMPLE_RATE * 1000.0;
        double releasedAmplitude = envelope.getPlayingAmplitude(milliTimePlayed);
        
        // Play the release tail of a sound
        while (playing && released) {
            byte[] buffer = new byte[frameSize * bufferSize];
            
            for (int i = 0; i < bufferSize; i++) {
                int isampleCount = (i + sampleCount + sampleCountReleased);
                
                double time = (((double) (isampleCount))/WaveForm.SAMPLE_RATE);
                
                double amp = volume * releasedAmplitude * envelope.getReleasedAmplitude(time * 1000.0);
                
                if (amp == 0) {
                    kill();
                    return;
                }
                
                if (isampleCount * frameSize >= originalBuffer.length) {
                    for (int j = 0; j < channels; j++) {
                        if (sampleSizeInByte == 1) {
                            buffer[isampleCount * frameSize + j] = 0;

                        } else if (sampleSizeInByte == 2) {
                            buffer[isampleCount * frameSize + j * channels + 1] = 0;
                            buffer[isampleCount * frameSize + j * channels] = 0;
                        }
                    }
                } else {
                    for (int j = 0; j < channels; j++) {
                        if (sampleSizeInByte == 1) {
                            buffer[i * frameSize + j] = (byte) (((double) originalBuffer[isampleCount * frameSize + j]) * amp);

                        } else if (sampleSizeInByte == 2) {
                            int firstBytePosition = i * frameSize + j * channels;
                            int ifirstBytePosition = isampleCount * frameSize + j * channels;
                            if (isBigEndian) {
                                System.out.println("Big Endian file might not be supported.");
                                short value = (short) ((originalBuffer[ifirstBytePosition] << 8) + (originalBuffer[ifirstBytePosition + 1] & 0xff));

                                value = (short) (amp * ((double) value));

                                buffer[firstBytePosition + 1] = (byte) (value & 0xff);
                                buffer[firstBytePosition] = (byte) ((value >> 8) & 0xff);

                            } else {
                                short value = (short) ((originalBuffer[ifirstBytePosition + 1] << 8) + (originalBuffer[ifirstBytePosition] & 0xff));

                                value = (short) (amp * ((double) value));

                                buffer[firstBytePosition] = (byte) (value & 0xff);
                                buffer[firstBytePosition + 1] = (byte) ((value >> 8) & 0xff);
                            }
                        }
                    }
                }
            }
            sampleCountReleased += bufferSize;
            
            line.write(buffer, 0, buffer.length);
        }
    }
    
    // Getters
    public String getPath() {
        return path;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    // Setters
    public final Boolean setPath(String newPath) {
        path = newPath;
        
        if (path == null) {
            originalBuffer = null;
            audioFormat = null;
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
            originalBuffer =  new byte[(int) file.length()];
            
            while (byteRead <= 0) {
                byteRead = audioInputStream.read(originalBuffer, offset, fileLength - offset);
                offset += byteRead;
            }
            
            //clipTime = ((double)buffer.length / (double)audioFormat.getFrameSize()) / (double)audioFormat.getSampleRate();
            return true;
        } catch (UnsupportedAudioFileException | IOException ex) {
            return false;
        }
    }
    
    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }
}
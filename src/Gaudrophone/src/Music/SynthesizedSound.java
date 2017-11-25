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
import javax.sound.sampled.AudioInputStream;

public class SynthesizedSound extends Sound {
    private double frequency;
    private int tuning;
    private WaveForm waveForm = null;
    
    // Constructors
    public SynthesizedSound() {
        typeString = "synth";
        frequency = 440.0;
        tuning = 0;
        waveForm = new SineWaveForm();
//        waveForm.updateBuffer(frequency);
    }
    
    public SynthesizedSound(double freq) {
        typeString = "synth";
        frequency = freq;
        tuning = 0;
        waveForm = new SineWaveForm();
//        waveForm.updateBuffer(frequency);
    }
    
    // Implement abstract methods
//    @Override
//    public AudioInputStream getAudioInputStream() {
//        return waveForm.getAudioInputStream();
//    }

    @Override
    public AudioInputStream getPlayingStream() {
        double timeLength = envelope.getPlayingTimeLength(); // in milliseconds
        
        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
        
        byte[] buffer = new byte[2 * frames];
        
        for (int i = 0; i < frames; i++) {
            double time = ((double) i)/WaveForm.SAMPLE_RATE;
            buffer[2 * i] = (byte) (120.0 * volume * envelope.getPlayingAmplitude(time * 1000.0) * waveForm.getAmplitude(frequency, time));
            buffer[2 * i + 1] = buffer[2 * i];
        }
        
        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
    }
    
    @Override
    public int getLoopFrame() {
        double timeLength = envelope.getPlayingTimeLength(); // in milliseconds
        return (int) (WaveForm.SAMPLE_RATE * ((timeLength - Envelope.SUSTAIN_TIME/2) / 1000.0));
    }

    @Override
    public AudioInputStream getReleasedStream(double timePlayed) { // timePlayed must be in seconds
        double timeLength = envelope.getReleaseTime(); // in milliseconds
        
        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
        byte[] buffer = new byte[2 * frames];
        
        double milliTimePlayed = timePlayed * 1000;
        
        for (int i = 0; i < frames; i++) {
            double time = ((double) i)/WaveForm.SAMPLE_RATE;
            buffer[2 * i] = (byte) (120.0 * volume * envelope.getReleasedAmplitude(time * 1000.0, milliTimePlayed) * waveForm.getAmplitude(frequency, timePlayed + time));
            buffer[2 * i + 1] = buffer[2 * i];
        }
        
        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
    }
    
    // Setters
    public void setFrequency(double newFrequency) {
        this.frequency = newFrequency;
        // update tuning
//        waveForm.updateBuffer(frequency);
    }
    
    public void setTuning(int newTuning) {
        this.tuning = newTuning;
    }
    
    public void setWaveForm(WaveForm waveform) {
        this.waveForm = waveform;
    }
    
    // Getters
    public double getFrequency() {
        return this.frequency;
    }
    
    public int getTuning() {
        return this.tuning;
    }
    
    public WaveForm getWaveform() {
        return this.waveForm;
    }
}

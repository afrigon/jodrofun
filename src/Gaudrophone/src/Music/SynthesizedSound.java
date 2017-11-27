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
    private WaveForm waveForm = new SineWaveForm();
    private short[] synthesizedBuffer;
    
    public SynthesizedSound() {
        this.type = SoundType.synthesizedSound;
        this.refreshBuffer();
    }
    
    public SynthesizedSound(PlayableNote playableNote) {
        this.type = SoundType.synthesizedSound;
        this.playableNote = playableNote;
        this.refreshBuffer();
    }
    
    public final void refreshBuffer() {
        double timeLength = envelope.getPlayingTimeLength(); // in milliseconds
        
        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
        
        synthesizedBuffer = new short[frames];
        for (int i = 0; i < frames; i++) {
            double time = ((double) i)/WaveForm.SAMPLE_RATE;
            synthesizedBuffer[i] = (short) (32767.0 * this.volume * this.envelope.getPlayingAmplitude(time * 1000.0) * this.waveForm.getAmplitude(this.playableNote.getFrequency(), time));
            
        }
    }

    @Override
    public AudioInputStream getPlayingStream() {
        double timeLength = envelope.getPlayingTimeLength(); // in milliseconds
        
        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
        
        byte[] buffer = new byte[4 * frames];
        
        for (int i = 0; i < frames; i++) {
            //double time = ((double) i)/WaveForm.SAMPLE_RATE;
            //short amplitude = (short) (32767.0 * this.volume * this.envelope.getPlayingAmplitude(time * 1000.0) * this.waveForm.getAmplitude(this.playableNote.getFrequency(), time));
            short amplitude = synthesizedBuffer[i];
            buffer[4 * i] = (byte) (amplitude & 0xff);
            buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
            buffer[4 * i + 2] = buffer[4 * i];
            buffer[4 * i + 3] = buffer[4 * i + 1];
        }
        
        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
    }
    
    @Override
    public int getLoopFrame() {
        double timeLength = envelope.getPlayingTimeLength(); // in milliseconds
        return (int) (WaveForm.SAMPLE_RATE * ((timeLength - Envelope.SUSTAIN_TIME) / 1000.0));
    }

    @Override
    public AudioInputStream getReleasedStream(double timePlayed) { // timePlayed must be in seconds
        double timeLength = envelope.getReleaseTime(); // in milliseconds
        
        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
        byte[] buffer = new byte[4 * frames];
        
        double milliTimePlayed = timePlayed * 1000;
        
        for (int i = 0; i < frames; i++) {
            double time = ((double) i)/WaveForm.SAMPLE_RATE;
            short amplitude = (short) (32767.0 * this.volume * this.envelope.getReleasedAmplitude(time * 1000.0, milliTimePlayed) * this.waveForm.getAmplitude(this.getPlayableNote().getFrequency(), timePlayed + time));
            buffer[4 * i] = (byte) (amplitude & 0xff);
            buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
            buffer[4 * i + 2] = buffer[4 * i];
            buffer[4 * i + 3] = buffer[4 * i + 1];
        }
        
        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
    }
    
    // Setters
    public void setWaveForm(WaveForm waveform) {
        this.waveForm = waveform;
    }
    
    public WaveForm getWaveform() {
        return this.waveForm;
    }
}

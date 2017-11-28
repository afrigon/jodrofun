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

import javax.sound.sampled.AudioFormat;


public class SynthesizedSound extends Sound {
    private WaveForm waveForm = new SineWaveForm();
    
    private static final int BUFFER_SIZE = (int) (WaveForm.SAMPLE_RATE / 20);
    
    public SynthesizedSound() {
        
    }
    
    public SynthesizedSound(PlayableNote playableNote) {
        this.type = SoundType.synthesizedSound;
        this.playableNote = playableNote;
    }
    
    @Override
    public void run() {
        
        int sampleCount = 0;
        
        // play the attack, decay and sustain part until sound is killed or released
        while (playing && (!released)) {
            byte[] buffer = new byte[4 * BUFFER_SIZE];
            
            for (int i = 0; i < BUFFER_SIZE; i++) {
                double time = (((double) (i + sampleCount))/WaveForm.SAMPLE_RATE);
                
                short amplitude = (short) (32767.0 * envelope.getPlayingAmplitude(time * 1000.0) * volume * waveForm.getAmplitude(playableNote.getFrequency(), time));
            
                buffer[4 * i] = (byte) (amplitude & 0xff);
                buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
                buffer[4 * i + 2] = buffer[4 * i];
                buffer[4 * i + 3] = buffer[4 * i + 1];
            }
            sampleCount += BUFFER_SIZE;
            
            line.write(buffer, 0, buffer.length);
        }
        
        int sampleCountReleased = 0; // keeps the total number of samples played
        double milliTimePlayed = (double) sampleCount / (double) WaveForm.SAMPLE_RATE * 1000.0;
        
        // Play the release tail of a sound
        while (playing && released) {
            byte[] buffer = new byte[4 * BUFFER_SIZE];
            
            for (int i = 0; i < BUFFER_SIZE; i++) {
                double time = (((double) (i + sampleCountReleased))/WaveForm.SAMPLE_RATE);
                
                double envelopeAmplitude = envelope.getReleasedAmplitude(time * 1000.0, milliTimePlayed);
                
                if (envelopeAmplitude == 0) {
                    kill();
                    return;
                }
                
                short amplitude = (short) (32767.0 * envelopeAmplitude * volume * waveForm.getAmplitude(playableNote.getFrequency(), time));
            
                buffer[4 * i] = (byte) (amplitude & 0xff);
                buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
                buffer[4 * i + 2] = buffer[4 * i];
                buffer[4 * i + 3] = buffer[4 * i + 1];
                
                
                if (envelopeAmplitude == 0) {
                    kill();
                    return;
                }
            }
            
            sampleCountReleased += BUFFER_SIZE;
            line.write(buffer, 0, buffer.length);
        }
    }
    
    @Override
    public AudioFormat getAudioFormat() {
        return WaveForm.AUDIO_FORMAT;
    }
    
    // Setters
    public void setWaveForm(WaveForm waveform) {
        this.waveForm = waveform;
    }
    
    public WaveForm getWaveform() {
        return this.waveForm;
    }
}

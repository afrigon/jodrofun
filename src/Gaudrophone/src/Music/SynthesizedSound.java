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

import Manager.GaudrophoneController;
import Music.Waveform.WaveForm;
import javax.sound.sampled.AudioFormat;


public class SynthesizedSound extends Sound {
    private WaveForm waveForm = null;
    
    private static final int BUFFER_SIZE = (int) (WaveForm.SAMPLE_RATE / 20);
    
    public SynthesizedSound(PlayableNote playableNote) {
        this.type = SoundType.synthesizedSound;
        this.playableNote = playableNote;
        this.waveForm = GaudrophoneController.getController().getInstrumentManager().getInstrument().getMasterWaveForm();
    }
    
    @Override
    public void run() {
        
        int sampleCount = 0;
        
        double timePlayed = -1;
        double releasedAmplitude = 0;
        while (playing) {
            byte[] buffer = new byte[4 * BUFFER_SIZE];
            
            for (int i = 0; i < BUFFER_SIZE; i++) {
                double time = (((double) (i + sampleCount))/WaveForm.SAMPLE_RATE);
                
                short amplitude = 0;
                if (released) {
                    if (timePlayed == -1) {
                        timePlayed = time;
                        releasedAmplitude = envelope.getPlayingAmplitude(time * 1000.0);
                    }
                    amplitude = (short) (32767.0 * releasedAmplitude * envelope.getReleasedAmplitude((time - timePlayed) * 1000.0) * volume * waveForm.getAmplitude(playableNote.getFrequency(), time));
                } else {
                    amplitude = (short) (32767.0 * envelope.getPlayingAmplitude(time * 1000.0) * volume * waveForm.getAmplitude(playableNote.getFrequency(), time));
                }
                buffer[4 * i] = (byte) (amplitude & 0xff);
                buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
                buffer[4 * i + 2] = buffer[4 * i];
                buffer[4 * i + 3] = buffer[4 * i + 1];
            }
            sampleCount += BUFFER_SIZE;
            
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

    @Override
    public void setVolume(double newVolume) {
        super.setVolume(newVolume);
    }
    
    public WaveForm getWaveform() {
        return this.waveForm;
    }
}

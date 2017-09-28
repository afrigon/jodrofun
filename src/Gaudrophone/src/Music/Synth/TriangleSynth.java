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
package Music.Synth;

import Music.Note;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author frigon
 */
public class TriangleSynth extends Synth {
    public TriangleSynth(Note note, double amplitude) {
        this.note = note;
        this.amplitude = amplitude;
    }
    
    @Override
    public void play() {
        try {
            AudioFormat audioFormat = new AudioFormat(this.SAMPLE_RATE, 16, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(audioFormat);
            sdl.open(audioFormat, 4096*2);
            sdl.start();

            byte[] buffer = new byte[2];
            double fadedAmplitude = amplitude;
            for(int i = 0; i < this.LENGTH * this.SAMPLE_RATE/1000; i++) {
//                if (i < FADE_IN * SAMPLE_RATE/1000) {
//                    fadedAmplitude += (this.amplitude/(this.FADE_IN * this.SAMPLE_RATE/1000));
//                } else if (i > (this.LENGTH - this.FADE_OUT) * this.SAMPLE_RATE/1000) {
//                    fadedAmplitude -= (this.amplitude/(this.FADE_OUT * this.SAMPLE_RATE/1000));
//                }
                double period = this.SAMPLE_RATE/this.note.getFrequency();
                double time = i / period;
                buffer[0] = (byte)(fadedAmplitude * 2*Math.abs(time - Math.floor(time))-1);
                buffer[1] = (byte)(fadedAmplitude * 2*Math.abs(time+1 - Math.floor(time))-1);
                sdl.write(buffer, 0, 2);
            }

            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException ex) {   
        }
    }
}

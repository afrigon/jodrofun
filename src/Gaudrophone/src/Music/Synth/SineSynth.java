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
public class SineSynth extends Synth {
    public SineSynth(Note note, double amplitude) {
        this.note = note;
        this.amplitude = amplitude;
    }
    
    public double getY(double time, double frequency) {
        return this.getY(time, frequency, 0);
    }
    
    public double getY(double time, double frequency, double phase) {
        double angularFrequency = 2 * Math.PI * frequency ;
        return Math.sin(angularFrequency * (time+phase));
    } 
    
    @Override
    public void play() {
        try {
            AudioFormat audioFormat = new AudioFormat(this.SAMPLE_RATE, 16, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(audioFormat);
            sdl.open(audioFormat, 4096*2);
            sdl.start();

            double reducedSampleRate = this.SAMPLE_RATE/1000;
            byte[] buffer = new byte[2];
            for(int i = 0; i < this.LENGTH * reducedSampleRate; i++) {
                buffer[0] = (byte)(amplitude * this.getY(i/reducedSampleRate, note.getFrequency()) + amplitude * this.getY(i/reducedSampleRate, note.getFrequency()+500));
                buffer[1] = (byte)(amplitude * this.getY(i/reducedSampleRate, note.getFrequency(), 1) + amplitude * this.getY(i/reducedSampleRate, note.getFrequency()+500, 1));
                sdl.write(buffer, 0, 2);
            }

            sdl.drain();
            sdl.stop();
        } catch (LineUnavailableException ex) {   
        }
    }
}

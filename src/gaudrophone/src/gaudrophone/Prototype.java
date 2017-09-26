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
package gaudrophone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Prototype {
    private static float SAMPLE_RATE = 44100;
    
    public static void play(float length, float frequency, float amplitude) {
        try {
            AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(audioFormat);
            sdl.open(audioFormat, 4096*2);
            sdl.start();

            byte[] buffer = new byte[1];
            for(int i = 0; i < length * SAMPLE_RATE / 1000; i++) {
                double angle = i / (SAMPLE_RATE/frequency) * 2.0 * Math.PI;
                buffer[0] = (byte)(Math.sin(angle) * amplitude);
                sdl.write(buffer, 0, 1);
            }

            sdl.drain();
            sdl.stop();
        } catch (LineUnavailableException ex) {   
        }
    }
}

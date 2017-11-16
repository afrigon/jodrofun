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

public class SynthesizedSound extends Sound {
    private double frequency;
    private int tuning;
    private WaveForm waveForm = null;
    
    // Constructors
    public SynthesizedSound() {
        frequency = 440.0;
        tuning = 0;
        waveForm = new SineWaveForm();
    }
    
    public SynthesizedSound(double freq) {
        frequency = freq;
        tuning = 0;
        waveForm = new SineWaveForm();
    }
    
    // Implement abstract methods
    @Override
    public byte[] getBuffer() {
        return waveForm.getBuffer(frequency);
    }
    
    // Setters
    public void setFrequency(double newFrequency) {
        this.frequency = newFrequency;
        // update tuning
    }
    
    public void setTuning(int newTuning) {
        this.tuning = newTuning;
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

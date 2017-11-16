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

public class Envelope implements java.io.Serializable {
    private double attack; // milliseconds
    private double decay; // milliseconds
    private double sustain; // between 0 and 1
    private double release; // milliseconds
    private double attackAndDecay;
    
    public Envelope() {
        attack = 100;
        decay = 100;
        sustain = 0.8;
        release = 400;
    }
    
    public Envelope(double newAttack, double newDecay, double newSustain, double newRelease) {
        attack = newAttack;
        decay = newDecay;
        sustain = newSustain;
        release = newRelease;
        attackAndDecay = newAttack + newDecay;
    }
    
    public void setAttack(double newAttack) {
        attack = newAttack;
        attackAndDecay = attack + decay;
    }
    
    public void setDecay(double newDecay) {
        decay = newDecay;
        attackAndDecay = attack + decay;
    }
    
    public void setSustain(double newSustain) {
        sustain = newSustain;
    }
    
    public void setRelease(double newRelease) {
        release = newRelease;
    }
    
    public double getPlayingAmplitude(double time) {
        if (time <= attack) {
            return time / attack;
        } else {
            if (time < attackAndDecay) {
                return (time - attack) * (sustain - 1) / decay + 1;
            } else {
                return sustain;
            }
        }
    }
    
    public double getReleaseAmplitude(double time) {
        if (time < release) {
            double powThis = time / release - 1;
            return sustain * powThis * powThis;
        } else {
            return 0;
        } 
    }
}

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

public class Envelope {
    private double attack; // milliseconds
    private double decay; // milliseconds
    private float sustain; // between -80 and 0
    private double release; // milliseconds
    private double attackAndDecay;
    
    public Envelope() {
        attack = 3000;
        decay = 500;
        sustain = -15f;
        release = 3000;
        attackAndDecay = attack + decay;
    }
    
    public Envelope(double newAttack, double newDecay, float newSustain, double newRelease) {
        attack = newAttack;
        decay = newDecay;
        sustain = newSustain;
        release = newRelease;
        attackAndDecay = attack + decay;
    }
    
    public void setAttack(double newAttack) {
        attack = newAttack;
        attackAndDecay = attack + decay;
    }
    
    public void setDecay(double newDecay) {
        decay = newDecay;
        attackAndDecay = attack + decay;
    }
    
    public void setSustain(float newSustain) {
        sustain = newSustain;
    }
    
    public void setRelease(double newRelease) {
        release = newRelease;
    }
    
    public float getPlayingVolume(double time) {
        if (time <= attack) {
            return getAttackVolume(time);
        } else {
            if (time < attackAndDecay) {
                return getDecayVolume(time - attack);
            } else {
                return sustain;
            }
        }
    }
    
    public float getReleaseVolume(double time) {
        if (time < release) {
            return sustain + (- sustain - 80.0f) * (float) (time/release);
        } else {
            return -80.0f;
        } 
    }
    
    private float getAttackVolume(double time) {
        return 80.0f * (float) (time/attack - 1);
    }
    
    private float getDecayVolume(double time) {
        return sustain * (float) (time/decay);
    }
}

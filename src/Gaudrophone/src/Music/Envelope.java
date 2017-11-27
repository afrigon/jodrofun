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
    private double attack = 100; // milliseconds
    private double decay = 100; // milliseconds
    private double sustain = 0.8; // between 0 and 1
    private double release = 100; // milliseconds
    
    public static final double SUSTAIN_TIME = 60000;
    
    public Envelope() {}
    public Envelope(double newAttack, double newDecay, double newSustain, double newRelease) {
        attack = newAttack;
        decay = newDecay;
        sustain = newSustain;
        release = newRelease;
    }
    
    public int getAttack() {
        return (int)this.attack;
    }
    
    public int getDecay() {
        return (int)this.decay;
    }
    
    public int getSustain() {
        return (int)(this.sustain*100);
    }
    
    public int getRelease() {
        return (int)this.release;
    }
    
    // setters
    public void setAttack(double newAttack) {
        attack = newAttack;
    }
    
    public void setDecay(double newDecay) {
        decay = newDecay;
    }
    
    public void setSustain(double newSustain) {
        sustain = newSustain;
    }
    
    public void setRelease(double newRelease) {
        release = newRelease;
    }
    
    // methods
    private double getAttackAmplitude(double time) {
        return time / attack;
    }
    
    private double getDecayAmplitude(double time) {
        return time * (sustain - 1) / decay + 1;
    }
    
    public double getPlayingAmplitude(double time) {
        if (time <= attack) {
            return getAttackAmplitude(time);
        } else {
            if (time < attack + decay) {
                return getDecayAmplitude(time - attack);
            } else {
                return sustain;
            }
        }
    }
    public double getReleasedAmplitude(double time, double timePlayed) {
        if (time < release) {
            double powThis = time / release - 1;
            return getPlayingAmplitude(timePlayed) * powThis * powThis;
        } else {
            return 0;
        } 
    }
    
    public double getPlayingTimeLength() {
        return attack + decay + SUSTAIN_TIME;
    }
    
    public double getReleaseTime() {
        return release;
    }

    
//    public float getPlayingVolume(double time) {
//        if (time <= attack) {
//            return getAttackVolume(time);
//        } else {
//            if (time < attackAndDecay) {
//                return getDecayVolume(time - attack);
//            } else {
//                return sustain;
//            }
//        }
//    }
//    
//    public float getReleaseVolume(double time) {
//        if (time < release) {
//            return sustain + (- sustain - 80.0f) * (float) (time/release);
//        } else {
//            return -80.0f;
//        } 
//    }
//    
//    private float getAttackVolume(double time) {
//        return 80.0f * (float) (time/attack - 1);
//    }
//    
//    private float getDecayVolume(double time) {
//        return sustain * (float) (time/decay);
//    }
}

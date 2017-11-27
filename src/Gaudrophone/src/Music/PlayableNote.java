/*
 * The MIT License
 *
 * Copyright 2017 Alexandre Frigon.
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

public class PlayableNote {
    //Clamping contants
    private final int MIN_OCTAVE = -2;
    private final int MAX_OCTAVE = 8;
    private final int MIN_TUNING= -100;
    private final int MAX_TUNING = 100;
    
    //Frequency calculation constants
    private final int BASE_OCTAVE = 4;
    private final double REFERENCE_FREQUENCY = 440.0;
    private final double FREQUENCY_CONSTANT = Math.pow(2, ((double)1/12));
    
    private Note note;
    private int octave;
    private Alteration alteration = Alteration.Natural;
    private int tuning = 0;
    private double cachedFrequency = -1;
    
    public PlayableNote() {
        this.note = Note.A;
        this.octave = 4;
        this.calculateFrequency();
    }
    
    public PlayableNote(Note note, int octave) {
        this.note = note;
        this.setOctave(octave);
        this.calculateFrequency();
    }
    
    public PlayableNote(Note note, int octave, Alteration alteration) {
        this.note = note;
        this.setOctave(octave);
        this.alteration = alteration;
        this.calculateFrequency();
    }
    
    public PlayableNote(Note note, int octave, int tuning) {
        this.note = note;
        this.setOctave(octave);
        this.setTuning(tuning);
        this.calculateFrequency();
    }
    
    public PlayableNote(Note note, int octave, Alteration alteration, int tuning) {
        this.note = note;
        this.setOctave(octave);
        this.alteration = alteration;
        this.setTuning(tuning);
        this.calculateFrequency();
    }
    
    public Note getNote() {
        return this.note;
    }
    
    public int getOctave() {
        return this.octave;
    }
    
    public Alteration getAlteration() {
        return this.alteration;
    }
    
    public int getTuning() {
        return this.tuning;
    }
    
    public final void setNote(Note note) {
        this.note = note;
        this.invalidateFrequency();
    }
    
    public final void setOctave(int octave) {
        this.octave = Math.min(this.MAX_OCTAVE, Math.max(octave, this.MIN_OCTAVE));
        this.invalidateFrequency();
    }
    
    public final void setAlteration(Alteration alteration) {
        this.alteration = alteration;
        this.invalidateFrequency();
    }
    
    public final void setTuning(int tuning) {
        this.tuning = Math.min(this.MAX_TUNING, Math.max(tuning, this.MIN_TUNING));
        this.invalidateFrequency();
    }
    
    public double getFrequency() {
        if (this.cachedFrequency != -1) {
            return this.cachedFrequency;
        } else {
            return this.calculateFrequency();
        }
    }
    
    //Calculate and set the frequency cache
    private double calculateFrequency() {
        int halfStepsFromReference = ((this.octave-this.BASE_OCTAVE) * 12) + this.note.getValue() + this.alteration.getValue();
        double frequency = Math.round(this.REFERENCE_FREQUENCY * Math.pow(this.FREQUENCY_CONSTANT, halfStepsFromReference) * 100.0) / 100.0;
        frequency *= Math.pow(2, this.tuning/1200.0);
        this.cachedFrequency = frequency;
        return frequency;
    }
    
    //Invalidate the cache. Is fire upon modification of instance attributes
    private void invalidateFrequency() {
        this.cachedFrequency = -1;
    }
    
    //Return the next Note acording to the chromatic scale. Exemple: A4 returns A#4
    //Always prefers sharp over flat alterations.
    public PlayableNote getNextPlayableNote() {
        switch (this.alteration) {
            case Flat: return new PlayableNote(this.note, this.octave, Alteration.Natural, this.tuning);
            case Natural:
                if (this.note != Note.E && this.note != Note.B) {
                    return new PlayableNote(this.note, this.octave, Alteration.Sharp, this.tuning);
                } else {
                    if (this.note == Note.E) {
                        return new PlayableNote(Note.F, this.octave, Alteration.Natural, this.tuning);
                    } else {
                        return new PlayableNote(Note.C, this.octave+1, Alteration.Natural, this.tuning);
                    }
                }
            case Sharp: return new PlayableNote(this.note.getNext(), this.octave, Alteration.Natural, this.tuning);
            default: return null;
        }
    }
    
    //Return the previous Note acording to the chromatic scale. Exemple: A4 returns G#4
    //Always prefers sharp over flat alterations.
    public PlayableNote getPreviousPlayableNote() {
        switch (this.alteration) {
            case Sharp: return new PlayableNote(this.note, this.octave, Alteration.Natural, this.tuning);
            case Natural:
                if (this.note != Note.F && this.note != Note.C) {
                    return new PlayableNote(this.note, this.octave, Alteration.Flat, this.tuning);
                } else {
                    if (this.note == Note.F) {
                        return new PlayableNote(Note.E, this.octave, Alteration.Natural, this.tuning);
                    } else {
                        return new PlayableNote(Note.B, this.octave-1, Alteration.Natural, this.tuning);
                    }
                }
            case Flat: return new PlayableNote(this.note.getPrevious(), this.octave, Alteration.Natural, this.tuning);
            default: return null;
        }
    }
}

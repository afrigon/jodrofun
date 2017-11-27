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
package Music;

public class PlayableNote {
    private final int BASE_OCTAVE = 4;
    private final double REFERENCE_FREQUENCY = 440.0;
    private final double FREQUENCY_CONSTANT = Math.pow(2, ((double)1/12));
    
    private Note note;
    private int octave;
    private Alteration alteration = Alteration.Natural;
    private int tuning = 0;
    
    public PlayableNote(Note note, int octave) {
        this.note = note;
        this.octave = octave;
    }
    
    public PlayableNote(Note note, int octave, Alteration alteration) {
        this.note = note;
        this.octave = octave;
        this.alteration = alteration;
    }
    
    public PlayableNote(Note note, int octave, int tuning) {
        this.note = note;
        this.octave = octave;
        this.tuning = tuning;
    }
    
    public PlayableNote(Note note, int octave, Alteration alteration, int tuning) {
        this.note = note;
        this.octave = octave;
        this.alteration = alteration;
        this.tuning = tuning;
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
    
    public void setNote(Note note) {
        this.note = note;
    }
    
    public void setOctave(int octave) {
        this.octave = octave;
    }
    
    public void setAlteration(Alteration alteration) {
        this.alteration = alteration;
    }
    
    public void setTuning(int tuning) {
        this.tuning = tuning;
    }
    
    public double getFrequency() {
        int halfStepsFromReference = ((this.octave-this.BASE_OCTAVE) * 12) + this.note.getValue() + this.alteration.getValue();
        double frequency = Math.round(this.REFERENCE_FREQUENCY * Math.pow(this.FREQUENCY_CONSTANT, halfStepsFromReference) * 100.0) / 100.0;
        frequency *= Math.pow(2, this.tuning/1200.0);
        return frequency;
    }
    
    public PlayableNote getNextPlayableNote() {
        switch (this.alteration) {
            case Flat: return new PlayableNote(this.note, this.octave, Alteration.Natural, this.tuning);
            case Natural:
                if (this.note != Note.E || this.note != Note.B) {
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
    
    public PlayableNote getPreviousPlayableNote() {
        switch (this.alteration) {
            case Sharp: return new PlayableNote(this.note, this.octave, Alteration.Natural, this.tuning);
            case Natural:
                if (this.note != Note.F || this.note != Note.C) {
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

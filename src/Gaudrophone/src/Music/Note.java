///*
// * The MIT License
// *
// * Copyright 2017 frigon.
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//package Music;
//
//public class Note {
//    private static final double REFERENCE_FREQUENCY = 440;
//    private static final int HALF_PER_OCTAVE = 12;
//    private static final int BASE_OCTAVE = 4;
//    private final double frequency;
//    
//    public double getFrequency() {
//        return this.frequency;
//    }
//    
//    public Note(double frequency) {
//        this.frequency = frequency;
//    }
//    
//    public Note(int note, int octave) {
//        int halfStepsAway = Note.getIntervalFromReference(note, octave);
//        this.frequency = Note.getFrequencyFromInterval(halfStepsAway);
//    }
//    
//    public Note(int note, int octave, Alteration alteration) {
//        int halfStepsAway = Note.getIntervalFromReference(note, octave);
//        halfStepsAway += alteration == Alteration.SHARP ? 1 : -1;
//        this.frequency = Note.getFrequencyFromInterval(halfStepsAway);
//    }
//    
//    protected static int getIntervalFromReference(int note, int octave) {
//        int interval = ((octave-Note.BASE_OCTAVE) * Note.HALF_PER_OCTAVE) + note;
//        return interval;
//    }
//    
//    protected static double getFrequencyFromInterval(int interval) {
//        double a = Math.pow(2, ((double)1/12));
//        return (double)Math.round(Note.REFERENCE_FREQUENCY * Math.pow(a, interval) * 100) / 100;
//    }
//}

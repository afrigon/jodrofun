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

public class NoteTranslator {
    
    public double getFrequencyFromMIDI(int n) {
        return 440.0 * Math.pow(2.0, ((double)(n - NOTE_REFERENCE))/12);
    }
    
    public double getFrequencyFromMIDI(int n, int tuning) {
        return 440.0 * Math.pow(2.0, ((double)(n - NOTE_REFERENCE) + 0.01 * (double)tuning)/12);
    }
    
    public double getFrequencyFromName(String name) {
        return getFrequencyFromMIDI(getMIDIFromName(name));
    }
    
    public double getFrequencyFromName(String name, int tuning) {
        return getFrequencyFromMIDI(getMIDIFromName(name), tuning);
    }
    
    public int getMIDIFromName(String name) {
        if (name.length() > 1) {
            int i = 0;
            for (String noteName : NOTE_NAMES) {
                System.out.println(name + " : compare " + noteName);
                if (name.length() > noteName.length() && name.startsWith(noteName) && name.charAt(noteName.length()) != '#' && name.charAt(noteName.length()) != 'b') {
                    return i + 12 * (Integer.parseInt(name.substring(noteName.length())) + 2);
                }
                i++;
            }
        }
        throw new java.lang.Error("Invalid note name.");
    }
    
    public int getMIDIFromFrequency(double frequency) {
        return (int)Math.ceil(NOTE_REFERENCE + CALCULUS_CONSTANT * Math.log(frequency / FREQUENCY_REFERENCE));
    }
    
    public int getTuningFromFrequency(double frequency) {
        return (int)(((NOTE_REFERENCE + CALCULUS_CONSTANT * Math.log(frequency / FREQUENCY_REFERENCE)) % 1) * 100);
    }
    
    public String getNameFromMIDI(int n) {
        return NOTE_NAMES[n % 12] + String.valueOf((int)Math.ceil(n / 12) - 2);
    }
    
    private final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "Ab", "A", "Bb", "B"};
    private final int NOTE_REFERENCE = 69; // A3
    private final double FREQUENCY_REFERENCE = 440.0;
    private final double CALCULUS_CONSTANT = 12 / Math.log(2);
       
}

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

import java.util.LinkedList;

public class Song {
    private String raw = "";
    private final LinkedList<PlayableChord> chords = new LinkedList<>();
    private int bpm = -1;
    
    public LinkedList<PlayableChord> getChords() {
        return this.chords;
    }
    
    public int getBPM() {
        return this.bpm;
    }
    
    public String getRaw() {
        return this.raw;
    }
    
    public void addChord(PlayableChord chord) {
        this.chords.add(chord);
    }
    
    public void addChords(LinkedList<PlayableChord> chords) {
        this.chords.addAll(chords);
    }
    
    public void setBPM(int bpm) {
        this.bpm = Math.max(1, Math.min(bpm, 600));
    }
    
    public void setRaw(String raw) {
        this.raw = raw;
    }
}

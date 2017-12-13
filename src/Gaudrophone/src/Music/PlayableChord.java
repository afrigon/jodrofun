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

public class PlayableChord {
    private final LinkedList<PlayableNote> notes = new LinkedList<>();
    private double relativeSteps = 0;
    private double length = 1;
    
    public LinkedList<PlayableNote> getNotes() {
        return this.notes;
    }
    
    public double getRelativeSteps() {
        return this.relativeSteps;
    }
    
    public double getLength() {
        return this.length;
    }
    
    public void addNote(PlayableNote note) {
        this.notes.add(note);
    }
    
    public void setRelativeSteps(double steps) {
        this.relativeSteps = steps;
    }
    
    public void setLength(double steps) {
        this.length = steps;
    }
    
    public boolean isEmpty() {
        return this.notes.isEmpty();
    }
}
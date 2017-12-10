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

public enum Note {
    C(-9), D(-7), E(-5), F(-4), G(-2), A(0), B(2);

    private final int value;
    private Note(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public String getFrenchName() {
        switch (this) {
            case A: return "La";
            case B: return "Si";
            case C: return "Do";
            case D: return "Ré";
            case E: return "Mi";
            case F: return "Fa";
            case G: return "Sol";
            default: return "";
        }
    }
    
    public static Note getNoteFromName(String str) {
        switch (str.toUpperCase()) {
            case "A": return Note.A;
            case "B": return Note.B;
            case "C": return Note.C;
            case "D": return Note.D;
            case "E": return Note.E;
            case "F": return Note.F;
            case "G": return Note.G;
            default: return null;
        }
    }

    public Note getNext() {
        switch (this) {
            case A: return Note.B;
            case B: return Note.C;
            case C: return Note.D;
            case D: return Note.E;
            case E: return Note.F;
            case F: return Note.G;
            case G: return Note.A;
            default: return null;
        }
    }

    public Note getPrevious() {
        switch (this) {
            case A: return Note.G;
            case B: return Note.A;
            case C: return Note.B;
            case D: return Note.C;
            case E: return Note.D;
            case F: return Note.E;
            case G: return Note.F;
            default: return null;
        }
    }
} 

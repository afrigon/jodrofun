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
package Instrument;

import Music.Note;
import KeyUtils.CrossLine;
import KeyUtils.KeyLine;
import KeyUtils.KeyShape;
import KeyUtils.RectangleKeyShape;
import KeyUtils.Vector2;
import Music.AudioClip;
import Music.PlayableNote;
import java.awt.Color;
import java.util.LinkedList;

public class Guitar implements InstrumentPattern {
    private Instrument guitar;
    
    @Override
    public Instrument generate() {
        this.guitar = new Instrument();
        guitar.setName("Guitare");
        
        this.addString(Note.E, 4, 0); //high
        this.addString(Note.B, 3, 1);
        this.addString(Note.G, 3, 2);
        this.addString(Note.D, 3, 3);
        this.addString(Note.A, 2, 4);
        this.addString(Note.E, 2, 5); //low
        
        return guitar;
    }
    
    private void addString(Note note, int octave, int stringIndex) {
        PlayableNote playableNote = new PlayableNote(note, octave);
        for (int i = 0; i < 12; i++) {
            KeyShape shape = new RectangleKeyShape().generateRectangle(100, 100, new Vector2(i*100, stringIndex*100));
            LinkedList<KeyLine> lines = new LinkedList<>();
            for (int j = 0; j < 4; j++) {
                lines.add(new KeyLine((j == 1 || (i == 0 && j == 3)) ? 1 : 0, new Color(0x5f7684)));
            }
            shape.setLines(lines);
            shape.setCrossLineColor(new Color(0xf2bc52), CrossLine.horizontal.getValue());
            shape.setCrossLineThickness(2, CrossLine.horizontal.getValue());
            shape.getIdleAppearance().setColor(new Color(0x966F33));
            shape.getSunkenAppearance().setColor(new Color(0x725325));
            
            AudioClip sound = new AudioClip();
            sound.getEnvelope().setRelease(600);
            sound.setPath(getClass().getResource("/resources/guitar/" + "A3" + ".wav").getPath());
            
            Key key = new Key(sound, shape, "");
//          key.set
            key.setStates(0);
            guitar.addKey(key);
            playableNote = playableNote.getNextPlayableNote();
        }
    }
}

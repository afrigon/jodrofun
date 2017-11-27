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

import KeyUtils.CrossLine;
import KeyUtils.KeyLine;
import KeyUtils.KeyShape;
import KeyUtils.RectangleKeyShape;
import KeyUtils.ShapeAppearance;
import KeyUtils.Vector2;
import Music.AudioClip;
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
        for (int i = 0; i < 12; i++) {
            KeyShape shape = new RectangleKeyShape().generateRectangle(1, 1, new Vector2(i, stringIndex));
            LinkedList<KeyLine> lines = new LinkedList<>();
            for (int j = 0; j < 4; j++) {
                lines.add(new KeyLine(4, new Color(0x5f7684)));
            }
            shape.setLines(lines);
            shape.setCrossLineColor(new Color(0x5f7684), CrossLine.horizontal.getValue());
            shape.setCrossLineThickness(4, CrossLine.horizontal.getValue());
            shape.getIdleAppearance().setColor(new Color(0x966F33));
            shape.getSunkenAppearance().setColor(new Color(0x725325));
            
            AudioClip sound = new AudioClip();
            //sound.setPath();
            
            Key key = new Key(sound, shape, "");
//          key.set
            guitar.addKey(key);
        }
    }
}

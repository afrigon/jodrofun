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
package Instrument.Pattern;

import Instrument.Instrument;
import Instrument.Key;
import Music.Note;
import KeyUtils.CrossLine;
import KeyUtils.KeyLine;
import KeyUtils.KeyShape;
import KeyUtils.Generator.RectangleKeyShape;
import KeyUtils.Vector2;
import Music.AudioClip;
import Music.PlayableNote;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public class Guitar implements InstrumentPattern {
    private Instrument guitar;
    private String fullPathJar;
    
    public Guitar() { 
        this.fullPathJar = new java.io.File(".").getAbsolutePath().replace("/.", "");
    }
    
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
            playableNote = playableNote.getNextPlayableNote();
            String keyName = playableNote.getNote().toString() + playableNote.getAlteration().getString() + playableNote.getOctave();
            
            AudioClip sound = new AudioClip(playableNote);
            sound.getEnvelope().setRelease(600);
            try {
                new java.io.File(".").getAbsolutePath();
                sound.setPath(java.net.URLDecoder.decode(this.fullPathJar + "/resources/guitar/" + keyName + ".wav", "UTF-8"));
            } catch (UnsupportedEncodingException | NullPointerException ex) {
                continue;
            }
            
            Key key = new Key(sound, this.getVisual(i, stringIndex), keyName);
            key.setStates(0);
            guitar.addKey(key);
        }
    }
    
    private KeyShape getVisual(int x, int y) {
        KeyShape shape = new RectangleKeyShape().generateShape(100, 30, new Vector2(x*100, y*30+60));
        LinkedList<KeyLine> lines = new LinkedList<>();
        for (int j = 0; j < 4; j++) {
            lines.add(new KeyLine((j % 2 == 0) ? 0 : 1, new Color(0x5f7684)));
        }
        shape.setLines(lines);
        shape.setCrossLineColor(y > 2 ? new Color(0xb87333) : new Color(0xa7a7a7), CrossLine.horizontal);
        shape.setCrossLineThickness(y+1, CrossLine.horizontal);
        shape.getIdleAppearance().setColor(new Color(0x775732));
        shape.getSunkenAppearance().setColor(new Color(0x543c21));
        return shape;
    }
}

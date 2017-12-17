/*
 * The MIT License
 *
 * Copyright 2017 Frigon.
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
import Music.Alteration;
import KeyUtils.KeyLine;
import KeyUtils.KeyShape;
import KeyUtils.Generator.RectangleKeyShape;
import KeyUtils.Vector2;
import Music.Envelope;
import Music.PlayableNote;
import Music.SynthesizedSound;
import java.awt.Color;
import java.util.LinkedList;

public class ElectricPiano implements InstrumentPattern {
    private Instrument piano;
    private final double KEY_WIDTH = 60;
    private final double KEY_HEIGHT = 300;
    private double x = 0;
    
    @Override
    public Instrument generate() {
        this.piano = new Instrument();
        this.piano.setName("Piano");
        
        Note note = Note.A;
        int octave = 0;
        
        for(int i = 0; i < 7*7+3; i++) {
            this.piano.addKey(this.generateKey(note, octave, Alteration.Natural));
            note = note.getNext();
            if (note == Note.C) {
                octave++;
            }
            this.x += this.KEY_WIDTH;
        }
        
        this.x = this.KEY_WIDTH*0.75;
        this.piano.addKey(this.generateKey(Note.A, -1, Alteration.Sharp));
        
        for(int i = 1; i < 8; i++) {
            this.x += 2*this.KEY_WIDTH;
            this.piano.addKey(this.generateKey(Note.C, i, Alteration.Sharp));
            this.x += this.KEY_WIDTH;
            this.piano.addKey(this.generateKey(Note.D, i, Alteration.Sharp));
            this.x += 2*this.KEY_WIDTH;
            this.piano.addKey(this.generateKey(Note.F, i, Alteration.Sharp));
            this.x += this.KEY_WIDTH;
            this.piano.addKey(this.generateKey(Note.G, i, Alteration.Sharp));
            this.x += this.KEY_WIDTH;
            this.piano.addKey(this.generateKey(Note.A, i, Alteration.Sharp));
        }
        
        return this.piano;
    }
    
    private Key generateKey(Note note, int octave, Alteration alteration) {
        SynthesizedSound sound = new SynthesizedSound(new PlayableNote(note, octave, alteration, 0));
        sound.setWaveForm(new Music.Waveform.SineWaveForm());
        sound.setEnvelope(new Envelope(100, 1000, 0, 1000));
        KeyShape shape = alteration == Alteration.Natural ? this.getWhiteKey() : this.getBlackKey();
        String noteName = note.toString() + alteration.getString() + octave;
        Key key = new Key(sound, shape, noteName);
        key.setNote(note);
        key.setOctave(octave);
        key.setAlteration(alteration);
        key.setStates(0);
        return key;
    }
    
    private KeyShape getWhiteKey() {
        KeyShape shape = new RectangleKeyShape().generateShape((int)this.KEY_WIDTH, (int)this.KEY_HEIGHT, new Vector2(this.x, 0));
        LinkedList<KeyLine> lines = new LinkedList<>();
        for (int j = 0; j < 4; j++) {
            lines.add(new KeyLine(j % 2 == 0 ? 0 : 1, new Color(0x858585)));
        }
        shape.setLines(lines);
        shape.getIdleAppearance().setColor(new Color(0xffffff));
        shape.getSunkenAppearance().setColor(new Color(0xaaaaaa));
        return shape;
    }
    
    private KeyShape getBlackKey() {
        KeyShape shape = new RectangleKeyShape().generateShape((int)this.KEY_WIDTH/2, (int)(this.KEY_HEIGHT*0.66), new Vector2(this.x, 0));
        shape.getIdleAppearance().setColor(new Color(0x000000));
        shape.getSunkenAppearance().setColor(new Color(0x555555));
        return shape;
    }
}

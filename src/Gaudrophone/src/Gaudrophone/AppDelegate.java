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
package Gaudrophone;
import Music.*;
import Music.Synth.*;
import UIKit.UIWindow;
import UIKit.MainWindow;

public class AppDelegate {
    public static void main(String[] args) {
        Note note = new Note(NoteName.C, 4);
        Key sin = new Key(new Sound(new SinusSynth(note, 100)), null);
        Key square = new Key(new Sound(new SquareSynth(note, 100)), null);
        Key saw = new Key(new Sound(new SawSynth(note, 100)), null);
        Key random = new Key(new Sound(new RandomSynth(100)), null);
        int index = Instrument.shared.addKey(sin);
        int index1 = Instrument.shared.addKey(square);
        int index2 = Instrument.shared.addKey(saw);
        int index4 = Instrument.shared.addKey(random);
        MainWindow window = new MainWindow();
        window.setVisible();
    }
}

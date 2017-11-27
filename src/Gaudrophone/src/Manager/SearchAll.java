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
package Manager;

import Instrument.Key;
import Instrument.KeyState;
import java.util.ArrayList;

public class SearchAll extends Search {
    public SearchAll(ArrayList<Key> keys) {
        super(keys);
    }
    
    @Override
    public void search(String value) {
        value = value.toLowerCase();
        for (Key key: this.keys) {
            if (key.getName().toLowerCase().contains(value) ||
                key.getNote().toString().toLowerCase().contains(value) ||
                key.getNote().getFrenchName().toLowerCase().contains(value) ||
                key.getAlteration().toString().toLowerCase().contains(value) ||
                key.getAlteration().getString().contains(value) ||
                key.getAlteration().getFrenchName().toLowerCase().contains(value) ||
                String.valueOf(key.getFrequency()).contains(value) ||
                String.valueOf(key.getOctave()).contains(value) ||
                String.valueOf(key.getTuning()).contains(value)) {
                key.addState(KeyState.searched);
            } else {
                key.removeState(KeyState.searched);
            }
        }
    }
}

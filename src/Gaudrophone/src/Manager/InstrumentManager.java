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

import Instrument.*;

public class InstrumentManager {
    private Instrument currentInstrument;
    private String currentInstrumentPath;
    
    public Instrument getInstrument() {
        return this.currentInstrument;
    }
    
    public String getPath() {
        return this.currentInstrumentPath;
    }
    
    public void newInstrument() {
        this.currentInstrument = new Instrument();
    }
    
    public void newInstrument(InstrumentPattern newInstrument) {
        this.currentInstrument = newInstrument.generate();
    }
    
    public void openInstrument(String path) {
        this.currentInstrumentPath = path;
        InstrumentFileIO instrumentIO = new InstrumentFileIO(this.currentInstrumentPath);
        Instrument temp = instrumentIO.load();
        if (temp == null) {
            //show error loading instrument
        } else {
            this.currentInstrument = temp;
        }
    }
    
    public boolean saveInstrument() {
        if (this.currentInstrumentPath == null) {
            return false;
        }
        
        InstrumentFileIO instrumentIO = new InstrumentFileIO(this.currentInstrumentPath);
        return instrumentIO.save(this.currentInstrument);
    }
    
    public boolean saveInstrument(String path) {
        this.currentInstrumentPath = path;
        return this.saveInstrument();
    }
}

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
package Instrument;

import java.io.*;

public class InstrumentFileIO {
    private String path = null;
    
    public InstrumentFileIO(String path) {
        this.path = path;
    }
    
    public boolean save(Instrument instrument) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instrument);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            System.out.println("Could not save instrument.");
            return false;
        }
    }
    
    public Instrument load() {
        Instrument instrument = null;
        try {
            FileInputStream fileIn = new FileInputStream(this.path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instrument = (Instrument) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Could not load instrument.");
        }

        return instrument;
    }
}

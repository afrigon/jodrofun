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
package Music;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Transmitter;

/**
 *
 * @author Olivier
 */
public class DeviceManager {
    
    DeviceManager() {
        System.out.println("Gettting devices");
        
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice device = null;
        MidiDevice nanoKey = null;
        
        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);
            } catch (javax.sound.midi.MidiUnavailableException e) {
                System.out.println("error");
                  // Handle or throw exception...
            }
            //System.out.println("INFO : ");
            System.out.println(infos[i].getName());
            //System.out.println(infos[i].getDescription());
            System.out.println("getMaxTransmitters()");
            System.out.println(device.getMaxTransmitters());
            System.out.println("device.getMaxReceivers()");
            System.out.println(device.getMaxReceivers());
            
            if (device.getMaxTransmitters() == -1 && "nanoKEY2".equals(infos[i].getName())) {
                nanoKey = device;
            }
        }
        
        
        MidiReceiver r = new MidiReceiver();
        
        try {
            nanoKey.open();
            
            Transmitter trans = nanoKey.getTransmitter();
            trans.setReceiver(r);
            
            
        } catch (MidiUnavailableException e) {
            System.out.println("MidiUnavailableException : " + e.getMessage());
        }  catch (NullPointerException e) {
            System.out.println("Nano key not found. " + e.getMessage());
        }
        
    }
}

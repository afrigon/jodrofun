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
package Manager;

import Instrument.Key;
import java.util.LinkedList;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Transmitter;

public final class DeviceManager {
    private final LinkedList<MidiDevice> devices = new LinkedList<>();
    private boolean learning = false;
    private Key learningKey = null;
    private boolean autoLinking = false;
    
    public DeviceManager() {
        this.refresh();
    }
    
    public final void clearDevices() {
        for (MidiDevice device : devices) {
            device.close();
        }
        this.devices.clear();
    }
    
    private void addDevice(MidiDevice device) {
        try {
            MidiReceiver receiver = new MidiReceiver();
            device.open();
            Transmitter trans = device.getTransmitter();
            trans.setReceiver(receiver);
            this.devices.add(device);
        } catch (MidiUnavailableException ex) {
            System.out.println("MidiException : " + ex.toString());
        }
    }
    
    public final void sendInput(int channel, int midiNum, boolean noteOn) {
        if (this.learning) {
            if (noteOn) {
                this.learningKey.link(channel, midiNum);
                this.learning = false;
            }
        } else if (autoLinking) {
            this.autoLinkKeys(channel);
            GaudrophoneController.getController().getDelegate().didAutoBindMidi();
        } else {
            if (GaudrophoneController.getController().getCanvasManager().getState() == State.Play || GaudrophoneController.getController().getCanvasManager().getState() == State.AutoPlay) {
                LinkedList<Key> linkedKeys = GaudrophoneController.getController().getLinkedKeys(channel, midiNum);
                for (Key linkedKey : linkedKeys) {
                    if (noteOn) {
                        GaudrophoneController.getController().getCanvasManager().clicked(linkedKey);
                    } else {
                        GaudrophoneController.getController().getCanvasManager().released(linkedKey);
                    }
                    GaudrophoneController.getController().getCanvasManager().getDelegate().shouldRedraw();
                }
            }
        }
    }
    
    public final void linkMidiToKey(Key key) {
        this.learningKey = key;
        this.learning = true;
    }
    
    public final void autoLink() {
        this.autoLinking = true;
    }
    
    public final void cancelLink() {
        this.learning = false;
    }
    
    public final void cancelAutoLink() {
        this.autoLinking = false;
    }
    
    public final boolean hasDevice() {
        return this.devices.size() > 0;
    }
    
    public final boolean isLinking(Key key) {
        return this.learning && key == this.learningKey;
    }
    
    public final boolean isLinking() {
        return this.learning;
    }
    
    public final boolean isAutoLinking() {
        return this.autoLinking;
    }
    
    public final void refresh() {
        this.clearDevices();
        
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getMaxReceivers() == 0) {
                    this.addDevice(device);
                }
            } catch (javax.sound.midi.MidiUnavailableException e) {}
        }
    }
    
    public void autoLinkKeys(int channel) {
        for (Key key: GaudrophoneController.getController().getInstrumentManager().getInstrument().getKeys()) {
            int midiNum = (int)Math.round(69.0 + (12.0 * Math.log(key.getFrequency() / 440.0) / Math.log(2.0)));
            key.link(channel, midiNum);
        }
    }
}

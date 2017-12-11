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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

/**
 *
 * @author Olivier
 */
public class MidiPlayer {
    private Sequencer sequencer = null;
    
    public MidiPlayer(Sequence sequence) {
        try {
            sequencer = MidiSystem.getSequencer();
            
            sequencer.setSequence(sequence);
//            int[] controllers = new int[128];
//            for (int i = 0; i < 128; i++) {
//                controllers[i] = i;
//            }
            //sequencer.addControllerEventListener(new MidiEventListener(), controllers); // checker le tableau de int
               
            //sequencer.addMetaEventListener(new MidiEventListener());
                    
            sequencer.open();
            
            MidiReceiver receiver = new MidiReceiver();
            Transmitter trans = sequencer.getTransmitter();
            trans.setReceiver(receiver);            
            
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            System.out.println("Sequencer is going to start.");
            sequencer.start();
            System.out.println("Sequencer has started.");
            
        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            System.out.println(ex.toString());
        } finally {
//            System.out.println("Sequencer is going to close.");
//            sequencer.stop();
//            sequencer.close();
//            System.out.println("Sequencer has closed.");
        }
    }
    
    public void setTempo(float bpm) {
        if (sequencer != null) {
            sequencer.setTempoInBPM(bpm);
        } else {
            System.out.println("Sequencer should not be null ?!??!?!?!!.");
        }
    }
    
    public float getTempo() {
        if (sequencer != null) {
            return sequencer.getTempoInBPM();
        } else {
            System.out.println("Sequencer should not be null ?!??!?!?!!.");
        }
        return 0;
    }
}

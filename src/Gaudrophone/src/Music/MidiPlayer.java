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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 *
 * @author Olivier
 */
public class MidiPlayer {
    private Sequencer sequencer = null;
    
    
    public void MidiPlayer(Sequence sequence) {
        try {
            sequencer = MidiSystem.getSequencer();
            
            sequencer.setSequence(sequence);
            sequencer.open();
            sequencer.addControllerEventListener(new MidiEventListener(), new int[1]); // checker le tableau de int
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            
            
        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            System.out.println(ex.toString());
        } finally {
            sequencer.stop();
            sequencer.close();
        }
    }
    
    public void setTempo(float bpm) {
        if (sequencer != null) {
            sequencer.setTempoInBPM(bpm);
        } else {
            System.out.println("Sequencer should not be null ?!??!?!?!!.");
        }
    }
}

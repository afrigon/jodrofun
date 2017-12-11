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

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class MidiReceiver implements javax.sound.midi.Receiver {
    //private final NoteTranslator translator = new NoteTranslator();
    
    @Override
    public void send(MidiMessage message, long l) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            int command = shortMessage.getCommand();
            if (command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF) {
                int midiNum = shortMessage.getData1();
                int octave = (midiNum / 12) - 1;
                int note = midiNum % 12;
                int velocity = shortMessage.getData2();
                System.out.println("Note ON/OFF: " + note + " - " + octave + " : vel " + velocity);
            } else {
                System.out.println("Other command " + command);
            }
        }
        
//        System.out.println("midi message : ");
//        System.out.println("     length : " + message.getLength());
//        System.out.println("     message : " + String.valueOf(message.getMessage()));
//        System.out.println("     status : " + message.getStatus());
//        
//        byte[] test = message.getMessage();
//        System.out.println("     byte 1 : " + String.valueOf(test[0]));
//        System.out.println("     byte 2 : " + String.valueOf(test[1]));
//        System.out.println("     byte 3 : " + String.valueOf(test[2]));
//        String noteName = translator.getNameFromMIDI(test[1]);
//        System.out.println("     note : " + noteName);
//        System.out.println("     note midi : " + translator.getMIDIFromName(noteName));
//        System.out.println("     frequency : " + translator.getFrequencyFromMIDI(test[1]));
//        System.out.println("     frequency : " + translator.getFrequencyFromMIDI(test[1], 100));
//        System.out.println("     frequency : " + translator.getFrequencyFromMIDI(test[1]+1));
//        System.out.println("     frequency : " + translator.getFrequencyFromMIDI(test[1], 50));
        // call sound service
        
    }

    @Override
    public void close() {
        System.out.println("close midi receiver");
    }
    
}

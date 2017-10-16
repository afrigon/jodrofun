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
import Music.InstrumentType.Piano;
import static Music.Test.NUM_PRODUCERS;
import UIKit.UIWindow;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class AppDelegate {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        System.out.print("Press return to exit...");
//    Test.AudioConsumer consumer = new Test.AudioConsumer();
//    consumer.start();
//    for (int i = 0; i < NUM_PRODUCERS; i++)
//        new Test.AudioProducer(consumer).start();
//    System.console().readLine();
//    consumer.stop();
    
//        Note note = new Note(NoteName.C, 4);
//        World.shared.instrument = new Piano();
//        UIWindow window = new UIWindow();
//        window.makeKeyAndVisible();



        int playBackSpeed = 2;

        URL url = new URL("http://static1.grsites.com/archive/sounds/cartoon/cartoon001.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        AudioFormat af = ais.getFormat();

        int frameSize = af.getFrameSize();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2^16];
        int read = 1;
        while( read>-1 ) {
            read = ais.read(b);
            if (read>0) {
                baos.write(b, 0, read);
            }
        }

        byte[] b1 = baos.toByteArray();
        byte[] b2 = new byte[b1.length / playBackSpeed];
        for (int i=0; i < (b2.length / frameSize); i++) {
            for (int j=0; j < frameSize; j++) {
                double value = b1[i*frameSize*playBackSpeed + j];
                double low = b1[i*frameSize*playBackSpeed - j];
                double high = b1[i*frameSize*playBackSpeed + j];
                b2[i*frameSize + j] = b1[i*frameSize*playBackSpeed + j];
            }
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(b2);
        AudioInputStream aisAccelerated = new AudioInputStream(bais, af, b2.length);
        Clip clip = AudioSystem.getClip();
        clip.open(aisAccelerated);
        clip.start();
        JOptionPane.showMessageDialog(null, "Exit?");
    }
}

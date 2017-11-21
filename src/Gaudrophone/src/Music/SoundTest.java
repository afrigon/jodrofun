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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
/**
 *
 * @author Olivier
 */
public class SoundTest {
    
    
    
    public SoundTest() {
        
        if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            System.out.println("OUPUT IS SUPPORTED");
            
            File testFile = new File(".");
            File[] filesList = testFile.listFiles();
            for(File f : filesList){
                if(f.isDirectory())
                    System.out.println("Dir " + f.getName());
                if(f.isFile()){
                    System.out.println("Filename " + f.getName());
                }
            }
            
            File file = new File("Audio001.wav");
            
            
            try {
                AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
                AudioFormat af = format.getFormat();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);
                
                try {
                    SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                    sdl.open(af);
                    
                    int bufferLen = (int) ais.getFrameLength() * af.getFrameSize();
                    
                    System.out.println("getFrameLength : " + ais.getFrameLength());
                    System.out.println("getFrameSize : " + af.getFrameSize());
                    System.out.println("getSampleSizeInBits : " + af.getSampleSizeInBits());
                    
                    byte[] clipBuffer = new byte[bufferLen];
                    byte[] newBuffer = new byte[bufferLen];
                    
                    Clip clip = AudioSystem.getClip();
                    ais.read(clipBuffer, 0, bufferLen);
                    
                    /*for (int i = 0; i < bufferLen; i++) {
                        byte result = (byte) (clipBuffer[i] * 0.1);
                        newBuffer[i] = result;
                    }*/
                    
                    clip.open(af, clipBuffer, 0, bufferLen);
                    
                    Control controls[] = clip.getControls();
                    for (Control control : controls) {
                        System.out.println("control : " + control.toString());
                    }
                    
                    FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    vol.setValue(0.0f);
                    
                    clip.addLineListener((LineEvent le) -> {
                        if (le.getType() == LineEvent.Type.STOP) {
                            System.out.println("clip close");
                            clip.close();
                        }
                    });
                    
                    clip.start();
                    float volume = 0.0f;
                    
                    for (int i = 0; i < 100; i++) {
                        try {
                            Thread.sleep(5);
                            volume -= 0.2f;
                            vol.setValue(volume);
                        } catch (InterruptedException e) {
                            System.out.println("interrupted");
                            Thread.currentThread().interrupt();
                        }
                    }
                    
                    System.out.println("finish");
                    
                    
//                    sdl.start();
//                    
//                    
//                    byte[] buffer = new byte[bufferLen];
//                    long frameLength = ais.getFrameLength();
//                    double length = frameLength;
//                    
//                    System.out.println("buffer : " + bufferLen);
//                    
//                    
//                    int read = ais.read(buffer);
//                    
//                    while (read != -1) {
//                        sdl.write(buffer, 0, bufferLen);
//                        read = ais.read(buffer);
//                    }
//                    
//                    System.out.println("close line");
//                    sdl.close();
                    
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(SoundTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                
//                try {
//                    SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
//                    sdl.open(af, 4096*2);
//                    sdl.start();
//                    
//                    int bufferLen = sdl.getBufferSize();
//                    byte[] buffer = new byte[bufferLen];
//                    long frameLength = ais.getFrameLength();
//                    double length = frameLength;
//                    
//                    System.out.println("buffer : " + bufferLen);
//                    FloatControl vol = (FloatControl)sdl.getControl(FloatControl.Type.MASTER_GAIN);
//                    
//                    
//                    int read = ais.read(buffer);
//                    float volume = 0.1f;
//                    vol.setValue(volume);
//                    while (read != -1) {
//                        byte[] newBuffer = new byte[bufferLen];
//                        for (int i = 0; i < bufferLen; i++) {
//                            newBuffer[i] = (byte) (buffer[i]/10);
//                        }
//                        
//                        sdl.write(newBuffer, 0, bufferLen);
//                        
//                       // sdl.write(buffer, 0, bufferLen);
//                        read = ais.read(buffer);
//                       // vol.setValue(volume);
//                       // volume = volume * 0.5f;
//                        
//                       // System.out.println("volume : " + volume);
//                    }
//                    
//                    System.out.println("close line");
//                    sdl.close();
//                    
//                } catch (LineUnavailableException ex) {
//                    Logger.getLogger(SoundTest.class.getName()).log(Level.SEVERE, null, ex);
//                }
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(SoundTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        } else {
            System.out.println("OUPUT IS NOT SUPPORTED");
        }
        
//        Mixer.Info infos[] = AudioSystem.getMixerInfo();
//        
//        for (Mixer.Info info : infos) {
//            System.out.println("Info : ");
//            System.out.println("    " + info.getName());
//            System.out.println("    " + info.getDescription());
//            
//            Mixer mix = AudioSystem.getMixer(info);
//            Line.Info[] lineInfos = mix.getSourceLineInfo();
//            
//            System.out.println("Lines infos : ");
//            for (Line.Info lineInfo : lineInfos) {
//                System.out.println("     " + lineInfo.toString());
//            }
//        }
//        
        
        
    }
    
}

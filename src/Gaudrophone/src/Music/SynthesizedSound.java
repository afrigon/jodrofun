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

import javax.sound.sampled.AudioFormat;


public class SynthesizedSound extends Sound {
    private WaveForm waveForm = new SineWaveForm();
    
    private static final int BUFFER_SIZE = (int) (WaveForm.SAMPLE_RATE / 20);
    
    public SynthesizedSound() {
        
    }
    
    public SynthesizedSound(PlayableNote playableNote) {
        this.type = SoundType.synthesizedSound;
        this.playableNote = playableNote;
    }
    
    @Override
    public void run() {
        
        int sampleCount = 0;
        while (playing && (!released)) {
            byte[] buffer = new byte[4 * BUFFER_SIZE];
            
            for (int i = 0; i < BUFFER_SIZE; i++) {
                double time = (((double) (i + sampleCount))/WaveForm.SAMPLE_RATE);
                
                
                short amplitude = (short) (32767.0 * envelope.getPlayingAmplitude(time * 1000.0) * volume * waveForm.getAmplitude(playableNote.getFrequency(), time));
            
                buffer[4 * i] = (byte) (amplitude & 0xff);
                buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
                buffer[4 * i + 2] = buffer[4 * i];
                buffer[4 * i + 3] = buffer[4 * i + 1];
            }
            sampleCount += BUFFER_SIZE;
            
            line.write(buffer, 0, buffer.length);
        }
        
        int sampleCountReleased = 0;
        double milliTimePlayed = (double) sampleCount / (double) WaveForm.SAMPLE_RATE * 1000.0;
        System.out.println("Time played : " + milliTimePlayed);
        while (playing && released) {
            byte[] buffer = new byte[4 * BUFFER_SIZE];
            
            for (int i = 0; i < BUFFER_SIZE; i++) {
                double time = (((double) (i + sampleCountReleased))/WaveForm.SAMPLE_RATE);
                
                double envelopeAmplitude = envelope.getReleasedAmplitude(time * 1000.0, milliTimePlayed);
                
                if (envelopeAmplitude == 0) {
                    kill();
                    return;
                }
                
                short amplitude = (short) (32767.0 * envelopeAmplitude * volume * waveForm.getAmplitude(playableNote.getFrequency(), time));
            
                buffer[4 * i] = (byte) (amplitude & 0xff);
                buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
                buffer[4 * i + 2] = buffer[4 * i];
                buffer[4 * i + 3] = buffer[4 * i + 1];
                
                
                if (envelopeAmplitude == 0) {
                    kill();
                    return;
                }
            }
            sampleCountReleased += BUFFER_SIZE;
            line.write(buffer, 0, buffer.length);
        }
    }
    
    @Override
    public AudioFormat getAudioFormat() {
        return WaveForm.AUDIO_FORMAT;
    }
    
//    
//    public final void refreshBuffer() {
//        
//        //double frequency = playableNote.getFrequency();
//        //int waves = (int) ((Envelope.SUSTAIN_TIME / 1000.0) * frequency); // number of waves during approx SUSTAIN_TIME
//        //System.out.println("Waves : " + waves);
//        //double preciseSustainTime = ((double) waves) / frequency;
//        
//        //int frames = (int) (WaveForm.SAMPLE_RATE * (timeLength / 1000.0 + preciseSustainTime));
//        
//        double timeLength = envelope.getAttackAndDecay(); // in milliseconds
//        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
//        
//        int loopFrames = (int) (WaveForm.SAMPLE_RATE * LOOP_LENGTH / 1000.0);
//        int loopFadeFrames = (int) (WaveForm.SAMPLE_RATE * LOOP_FADE / 1000.0);
//        
//        // init buffer sizes
//        synthesizedBuffer = new short[frames + loopFadeFrames];
//        fadingBuffer2 = new short[frames + 2 * loopFrames - loopFadeFrames];
//        fadingBuffer = new short[fadingBuffer2.length - loopFadeFrames];
//        
//        // start filling buffers
//        for (int i = 0; i < synthesizedBuffer.length; i++) {
//            double time = ((double) i)/WaveForm.SAMPLE_RATE;
//            synthesizedBuffer[i] = (short) (32767.0 * envelope.getPlayingAmplitude(time * 1000.0) * volume * waveForm.getAmplitude(this.playableNote.getFrequency(), time));
//            if (i >= frames) {
//                double alpha = ((double) (i - frames)) / ((double) loopFadeFrames);
//                fadingBuffer[i] = (short) (synthesizedBuffer[i] * alpha);
//                synthesizedBuffer[i] = (short) (synthesizedBuffer[i] * (1.0 - alpha));
//            } else {
//                fadingBuffer[i] = 0;
//                fadingBuffer2[i] = 0;
//            }
//        }
//        
//        // continue for looping buffers 
//        for (int i = synthesizedBuffer.length; i < fadingBuffer.length; i++) {
//            double time = ((double) i)/WaveForm.SAMPLE_RATE;
//            double waveAmplitude = 32767.0 * envelope.getPlayingAmplitude(time * 1000.0) * volume * waveForm.getAmplitude(this.playableNote.getFrequency(), time);
//            
//            if (i >= fadingBuffer.length - loopFrames + 2 * loopFadeFrames) { 
//                fadingBuffer[i] = 0;
//                fadingBuffer2[i] = (short) waveAmplitude;
//            } else if (i >= frames + loopFrames - loopFadeFrames) {
//                double alpha = ((double) (i - (frames + loopFrames - loopFadeFrames))) / ((double) loopFadeFrames);
//                fadingBuffer[i] = (short) (waveAmplitude * (1.0 - alpha));
//                fadingBuffer2[i] = (short) (waveAmplitude * alpha);
//            } else {
//                fadingBuffer[i] = (short) waveAmplitude;
//                fadingBuffer2[i] = 0;
//            }
//        }
//        
//        // continue for the last looping buffer
//        for (int i = fadingBuffer.length; i < fadingBuffer2.length; i++) {
//            double time = ((double) i)/WaveForm.SAMPLE_RATE;
//            double waveAmplitude = 32767.0 * envelope.getPlayingAmplitude(time * 1000.0) * volume * waveForm.getAmplitude(this.playableNote.getFrequency(), time);
//            
//            double alpha = ((double) (i - fadingBuffer.length)) / ((double) loopFadeFrames);
//            fadingBuffer2[i] = (short) (waveAmplitude * (1.0 - alpha));
//        }
//        
//        fadingClipLoopPoint = frames;
//        fadingClipLoopPoint2 = frames + loopFadeFrames;
//        
////        loopFrame = 0;
////        // find out where is the first next zero point to loop frame
////        int startFrame = (int) (WaveForm.SAMPLE_RATE * (envelope.getAttackAndDecay() / 1000.0));
////        while (loopFrame == 0) {
////            if (synthesizedBuffer[startFrame] > -LOOP_THRESHOLD && synthesizedBuffer[startFrame] < LOOP_THRESHOLD) {
////                loopFrame = startFrame * 4;
////            } else {
////                startFrame++;
////            }
////        }
////        
////        lastLoopFrame = 0;
////        // find out the last zero to set final loop point
////        int lastFrame = synthesizedBuffer.length - 1;
////        while (lastLoopFrame == 0) {
////            if (synthesizedBuffer[lastFrame] > -LOOP_THRESHOLD && synthesizedBuffer[lastFrame] < LOOP_THRESHOLD) {
////                lastLoopFrame = lastFrame * 4;
////            } else {
////                lastFrame--;
////            }
////        }
////        
////        System.out.println("loop from : " + loopFrame);
////        System.out.println("loop end : " + lastLoopFrame);
//        refreshClip();
//    }
//
//    @Override
//    public AudioInputStream getPlayingStream() {
//        
//        byte[] buffer = new byte[4 * synthesizedBuffer.length];
//        
//        for (int i = 0; i < synthesizedBuffer.length; i++) {
//            short amplitude = synthesizedBuffer[i];
//            buffer[4 * i] = (byte) (amplitude & 0xff);
//            buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
//            buffer[4 * i + 2] = buffer[4 * i];
//            buffer[4 * i + 3] = buffer[4 * i + 1];
//        }
//        
//        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
//    }
//    
//    public AudioInputStream getStream(short[] shortBuffer) {
//        
//        byte[] buffer = new byte[4 * shortBuffer.length];
//        
//        for (int i = 0; i < shortBuffer.length; i++) {
//            short amplitude = shortBuffer[i];
//            buffer[4 * i] = (byte) (amplitude & 0xff);
//            buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
//            buffer[4 * i + 2] = buffer[4 * i];
//            buffer[4 * i + 3] = buffer[4 * i + 1];
//        }
//        
//        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
//    }
//    
//    @Override
//    public int getLoopFrame() {
//        return loopFrame;
//    }
//    
//    @Override
//    public int getLastLoopFrame() {
//        return lastLoopFrame;
//    }
//    
//    @Override
//    public AudioInputStream getReleasedStream(double timePlayed) { // timePlayed must be in seconds
//        double timeLength = envelope.getReleaseTime(); // in milliseconds
//        
//        int frames = (int) (WaveForm.SAMPLE_RATE * timeLength / 1000.0);
//        byte[] buffer = new byte[4 * frames];
//        
//        double milliTimePlayed = timePlayed * 1000;
//        
//        for (int i = 0; i < frames; i++) {
//            double time = ((double) i)/WaveForm.SAMPLE_RATE;
//            short amplitude = (short) (32767.0 * this.volume * this.envelope.getReleasedAmplitude(time * 1000.0, milliTimePlayed) * this.waveForm.getAmplitude(this.getPlayableNote().getFrequency(), timePlayed + time));
//            buffer[4 * i] = (byte) (amplitude & 0xff);
//            buffer[4 * i + 1] = (byte) ((amplitude >> 8) & 0xff);
//            buffer[4 * i + 2] = buffer[4 * i];
//            buffer[4 * i + 3] = buffer[4 * i + 1];
//        }
//        
//        return new AudioInputStream(new ByteArrayInputStream(buffer, 0, buffer.length), WaveForm.AUDIO_FORMAT, buffer.length);
//    }
//    
//    @Override
//    public void playClip() {
//        playing = true;
//        clip.setFramePosition(0);
//        fadingClip.setFramePosition(0);
//        fadingClip2.setFramePosition(0);
//        
//        clip.start();
//        fadingClip.loop(Clip.LOOP_CONTINUOUSLY);
//        fadingClip2.loop(Clip.LOOP_CONTINUOUSLY);
////        if (getLoopFrame() > 0) {
////            clip.start();
////            //clip.loop(1);//Clip.LOOP_CONTINUOUSLY);
////        } else {
////            clip.start();
////        }
//    }
//    
//    @Override
//    protected void refreshClip() {
//        try {
//            if (clip.isOpen())
//                clip.close();
//            
//            if (fadingClip.isOpen())
//                fadingClip.close();
//            
//            if (fadingClip2.isOpen())
//                fadingClip2.close();
//            
//            clip.open(getStream(synthesizedBuffer));
//            clip.setFramePosition(0);
//            
//            fadingClip.open(getStream(fadingBuffer));
//            fadingClip.setFramePosition(0);
//            fadingClip.setLoopPoints(fadingClipLoopPoint, -1);
//            
//            fadingClip2.open(getStream(fadingBuffer2));
//            fadingClip2.setFramePosition(0);
//            fadingClip2.setLoopPoints(fadingClipLoopPoint2, -1);
//            
//        } catch (LineUnavailableException ex) {
//            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    // Setters
    public void setWaveForm(WaveForm waveform) {
        this.waveForm = waveform;
    }
    
    public WaveForm getWaveform() {
        return this.waveForm;
    }
}

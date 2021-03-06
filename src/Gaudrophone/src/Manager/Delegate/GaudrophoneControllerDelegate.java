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
package Manager.Delegate;

import Instrument.Key;
import Music.Song;

public interface GaudrophoneControllerDelegate {
    public void shouldUpdateProprietyPannelFor(Key key);
    public void didMoveKey(Key key);
    public void didMovePoint(Key key);
    public void didSetBPM(int bpm);
    public void didStopPlayingSong();
    public void didStartPlayingSong();
    public void didPauseSong();
    public void midiDidLink(Key key);
    public void updateMediaPlayerSlider(double percent);
    public void didLoadSong(Song song);
    public void liveLoopDidStartRecording(int index);
    public void liveLoopDidCancelRecording(int index);
    public void liveLoopDidStop(int index);
    public void didAutoBindMidi();
}

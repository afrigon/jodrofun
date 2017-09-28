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
package Music;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frigon
 */
public class NoteTest {
    @Test
    public void SameNoteInterval() {
        assertEquals(0, Note.getIntervalFromReference(NoteName.A, 4), 0);
    }
    
    @Test
    public void HigherOctaveInterval() {
        assertEquals(12, Note.getIntervalFromReference(NoteName.A, 5), 0);
    }
    
    @Test
    public void LowerOctaveInterval() {
        assertEquals(-12, Note.getIntervalFromReference(NoteName.A, 3), 0);
    }
    
    @Test
    public void HigherNoteInterval() {
        assertEquals(2, Note.getIntervalFromReference(NoteName.B, 4), 0);
    }
    
    @Test
    public void LowerNoteInterval() {
        assertEquals(-5, Note.getIntervalFromReference(NoteName.E, 4), 0);
    }
    
    @Test
    public void HigherNoteHigherOctaveInterval() {
        assertEquals(14, Note.getIntervalFromReference(NoteName.B, 5), 0);
    }
    
    @Test
    public void HigherNoteLowerOctaveInterval() {
        assertEquals(-10, Note.getIntervalFromReference(NoteName.B, 3), 0);
    }
    
    @Test
    public void LowerNoteHigherOctaveInterval() {
        assertEquals(7, Note.getIntervalFromReference(NoteName.E, 5), 0);
    }
    
    @Test
    public void LowerNoteLowerOctaveInterval() {
        assertEquals(-17, Note.getIntervalFromReference(NoteName.E, 3), 0);
    }
    
    @Test
    public void HzA4() {
        assertEquals((double)440, Note.getFrequencyFromInterval(Note.getIntervalFromReference(NoteName.A, 4)), 0);
    }
    
    @Test
    public void HzA5() {
        assertEquals((double)880, Note.getFrequencyFromInterval(Note.getIntervalFromReference(NoteName.A, 5)), 0);
    }
    
    @Test
    public void HzC4() {
        assertEquals((double)261.63, Note.getFrequencyFromInterval(Note.getIntervalFromReference(NoteName.C, 4)), 0);
    }
}

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
package UI;

import Music.Alteration;
import Music.PlayableChord;
import Music.PlayableNote;
import Music.Song;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;

public class JodrofunHero extends javax.swing.JPanel {
    private final int margin = 15;
    private final int hitMargin = 10;
    private final int keySize = 35;
    private final int keySpacing = 20;
    private final int cornerRadius = 12;
    private final Color keyColor = new Color(110, 110, 110);
    private final float thinWidth = 0.2f;
    private final float normalWidth = 0.6f;
    private final float hitWidth = 2f;
    private final Color textColor = Color.white;
    private final Font font = new Font("Verdana", Font.BOLD, 13);
    private int marginTop = 15;
    private int maxYCount = 0;
    private int songLength = 0;
    private double currentPercent = 0;
    private Song song;
    
    public void setSong(Song song) {
        this.song = song;
        this.maxYCount = 0;
        this.songLength = 0;
        this.currentPercent = 0;
        for (PlayableChord chord: song.getChords()) {
            this.maxYCount = Math.max(chord.getNotes().size(), this.maxYCount);
            this.songLength += chord.getLength()*4;
        }
        this.repaint();
    }
    
    public void setProgress(double percent) {
        this.currentPercent = percent;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.song == null) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        int w = this.getWidth();
        int h = this.getHeight();
        this.marginTop = (int)Math.floor((h - this.maxYCount*this.keySize - (this.maxYCount-1)*this.keySpacing) / 2);
                
        //vertical step line
        int beforeMargin = this.margin + this.hitMargin*2 + this.keySize;
        int quarterStep = (int)Math.ceil(w - beforeMargin) / (this.keySize+this.keySpacing)+1;
        g2.setColor(this.keyColor.brighter());
        for (int i = 0; i < quarterStep; i++) {
            if (i % 4 == 0) {
                g2.setStroke(new BasicStroke(this.normalWidth));
            } else {
                g2.setStroke(new BasicStroke(this.thinWidth));
            }
            
            int leftMargin = beforeMargin + i*(this.keySize+this.keySpacing);
            g2.drawLine(leftMargin, 0, leftMargin, h);
        }
        
        int keyLength = this.keySize+this.keySpacing;
        int currentX = (int)Math.floor(beforeMargin - this.currentPercent*this.songLength/100*keyLength);
        for (PlayableChord chord: this.song.getChords()) {
            LinkedList<PlayableNote> notes = chord.getNotes();
            for (int i = 0; i < notes.size(); i++) {
                if (currentX + keyLength*chord.getLength()*4 < 0) {
                    break;
                }
                
                if (currentX > w) {
                    return;
                }
                
                PlayableNote note = notes.get(i);
                switch(note.getNote()) {
                    case A: g2.setColor(HSL.getRGB(0f, 65f, 65f)); break;
                    case B: g2.setColor(HSL.getRGB(45f, 65f, 65f)); break;
                    case C: g2.setColor(HSL.getRGB(75f, 65f, 65f)); break;
                    case D: g2.setColor(HSL.getRGB(135f, 65f, 65f)); break;
                    case E: g2.setColor(HSL.getRGB(180f, 65f, 65f)); break;
                    case F: g2.setColor(HSL.getRGB(225f, 65f, 65f)); break;
                    case G: g2.setColor(HSL.getRGB(270f, 65f, 65f)); break;
                }
                
                if (currentX < this.margin + this.hitMargin) {
                    g2.setColor(g2.getColor().brighter());
                }
                
                g2.fillRoundRect(currentX + this.keySpacing/2,
                                 this.marginTop + i*(this.keySpacing+this.keySize), 
                                 (int)Math.floor(this.keySize*chord.getLength()*4 + this.keySpacing*(chord.getLength()*4 - 1)), 
                                 this.keySize, 
                                 this.cornerRadius, 
                                 this.cornerRadius);
                
                g2.setColor(this.textColor);
                g2.setFont(this.font);
                String text = note.getNote().toString() + note.getOctave() + (note.getAlteration() == Alteration.Sharp ? "#" : "");
                g2.drawString(text, 
                              currentX + this.keySpacing/2 + ((int)Math.floor(this.keySize*chord.getLength()*4 + this.keySpacing*(chord.getLength()*4 - 1)))/2 - g2.getFontMetrics().stringWidth(text)/2, 
                              this.marginTop + i*(this.keySpacing+this.keySize) + this.keySize/2 + g2.getFontMetrics().getHeight()/4);
            }
            currentX += keyLength*chord.getLength()*4;
            
            //hit keys
            for (int i = 0; i < this.maxYCount; i++) {
                g2.setStroke(new BasicStroke(this.hitWidth));
                g2.setColor(this.keyColor);
                g2.drawRoundRect(this.margin+this.hitMargin, this.marginTop + i*(this.keySpacing+this.keySize), this.keySize, this.keySize, this.cornerRadius, this.cornerRadius);

                g2.setStroke(new BasicStroke(this.thinWidth));
                g2.setColor(this.keyColor.brighter());
                g2.drawLine(0, this.marginTop + i*(this.keySpacing+this.keySize) - this.keySpacing/2, w, this.marginTop + i*(this.keySpacing+this.keySize) - this.keySpacing/2);
            }
            g2.drawLine(0, this.marginTop + this.maxYCount*(this.keySpacing+this.keySize) - this.keySpacing/2, w, this.marginTop + this.maxYCount*(this.keySpacing+this.keySize) - this.keySpacing/2);
        }
    }
}

/*
 * The MIT License
 *
 * Copyright 2017 Alexandre.
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class EnvelopeGraph extends javax.swing.JPanel {
    private double attack = 100;
    private double decay = 100;
    private double sustain = 0.8;
    private double release = 100;
    
    private final Color foreColor = new Color(0x388e3c);
    private final Color fillColor = new Color(0x1a388e3c, true);
    private final Color backgroundColor = Color.DARK_GRAY;
    private final float strokeSize = 2.0f;
    
    private final double maxValue = 5000;
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double width = this.getWidth();
        double height = this.getHeight();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setBackground(this.backgroundColor);
        g2.setStroke(new BasicStroke(strokeSize));
        
        double attackPoint = this.getCurve(this.attack) * width / 3;
        
        Path2D p = new Path2D.Double();
        
        p.moveTo(0, height+strokeSize);
        p.lineTo(attackPoint, 0+strokeSize);
        p.lineTo(attackPoint + this.getCurve(this.decay) * width / 3, (1 - this.sustain) * height+strokeSize);
        p.lineTo(2 * width / 3, (1 - this.sustain) * height+strokeSize);
        p.lineTo(2 * width / 3 + this.getCurve(this.release) * width / 3, height+strokeSize);
        p.closePath();
        
        g2.setColor(this.fillColor);
        g2.fill(p);
        g2.setColor(this.foreColor);
        g2.draw(p);
    }
    
    private double getCurve(double a) {
        return 1 + (0.5 * Math.log10(a / this.maxValue + 0.01));
    }
    
    public void setAttack(double p_attack) {
        this.attack = p_attack;
    }
    
    public void setDecay(double p_decay) {
        this.decay = p_decay;
    }
    
    public void setSustain(double p_sustain) {
        this.sustain = p_sustain / 100;
    }
    
    public void setRelease(double p_release) {
        this.release = p_release;
    }
}

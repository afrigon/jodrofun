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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;

public class GradientPanel extends javax.swing.JPanel {
    private Color[] colors = new Color[2];
    public GradientPanel(int hue) {
        Color color = this.getColor((float)hue);
        this.colors[0] = color.brighter();
        this.colors[1] = color;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        float[] dist = {0, 0.9f};
        RadialGradientPaint gp = new RadialGradientPaint((float)w/2, (float)h/2, (float)Math.max(h, w)/2, dist, this.colors);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w, h, 20, 20);
    }
    
    private Color getColor(float h) {
        h = h % 360.0f;
        h /= 360f;
        float s = 0.65f;
        float l = 0.65f;

        float q;
        if (l < 0.5) { q = l * (1 + s); } else { q = (l + s) - (s * l); }
        float p = 2 * l - q;
        float r = Math.min(1.0f, Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f))));
        float g = Math.min(1.0f, Math.max(0, HueToRGB(p, q, h)));
        float b = Math.min(1.0f, Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f))));
        return new Color(r, g, b, 1);
    }

    private static float HueToRGB(float p, float q, float h) {
        if (h < 0) h += 1;
        if (h > 1 ) h -= 1;
        if (6 * h < 1) { return p + ((q - p) * 6 * h); }
        if (2 * h < 1 ) { return  q; }
        if (3 * h < 2) { return p + ( (q - p) * 6 * ((2.0f / 3.0f) - h) ); }
        return p;
    }
}

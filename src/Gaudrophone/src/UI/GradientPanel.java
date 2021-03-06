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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GradientPanel extends javax.swing.JPanel {
    private Color[] colors = new Color[2];
    private Color borderColor = new Color(0x00000000, true);
    private boolean drawImage = false;
    private BufferedImage image;
    
    public void setDrawImage(boolean value) {
        this.drawImage = value;
        this.repaint();
    }
    
    public void setBorderColor(Color color) {
        this.borderColor = color;
        this.repaint();
    }
    
   public GradientPanel(int hue) {
        Color color = HSL.getRGB((float)hue, 65f, 65f);
        this.colors[0] = color.brighter();
        this.colors[1] = color;
        try {
            this.image = ImageIO.read(this.getClass().getResource("/resources/liveloop_playing.png"));
        } catch (IOException ex) {}
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
        int w = this.getWidth();
        int h = this.getHeight();
        float[] dist = {0, 1f};
        RadialGradientPaint gp = new RadialGradientPaint((float)w/2, (float)h/2, (float)Math.max(h, w)/2, dist, this.colors);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w, h, 20, 20);
        
        //border
        g2.setPaint(this.borderColor);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(2, 2, w-5, h-5, 16, 16);
        
        //image
        if (this.drawImage) {
            g2.drawImage(this.image, null, (w-48)/2, (h-48)/2);
        }
    }
}

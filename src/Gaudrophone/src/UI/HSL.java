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

public class HSL {
    public static Color getRGB(float h, float s, float l) {
        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

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
    
    public static float[] rgbToHSL(Color color)
	{
            float[] rgb = color.getRGBColorComponents( null );
            float r = rgb[0];
            float g = rgb[1];
            float b = rgb[2];
            
            float min = Math.min(r, Math.min(g, b));
            float max = Math.max(r, Math.max(g, b));

            float h = 0;
            if (max == min)
                h = 0;
            else if (max == r)
                h = ((60 * (g - b) / (max - min)) + 360) % 360;
            else if (max == g)
                h = (60 * (b - r) / (max - min)) + 120;
            else if (max == b)
                h = (60 * (r - g) / (max - min)) + 240;

            float l = (max + min) / 2;

            float s = 0;
            if (max == min)
                s = 0;
            else if (l <= .5f)
                s = (max - min) / (max + min);
            else
                s = (max - min) / (2 - max - min);

            return new float[] {h, s * 100, l * 100};
	}
}

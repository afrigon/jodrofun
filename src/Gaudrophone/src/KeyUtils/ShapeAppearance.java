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
package KeyUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ShapeAppearance implements java.io.Serializable {
    private Color backgroundColor;
    private String backgroundImagePath;
    private transient BufferedImage backgroundImage = null;
    
    public ShapeAppearance() {
        backgroundColor = Color.GRAY;
        backgroundImagePath = null;
    }
    
    private void readObject(java.io.ObjectInputStream in) {
        try {
            in.defaultReadObject();
            setImage();
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    private void setImage() {
        try {
            if (backgroundImagePath != null && !"".equals(backgroundImagePath)) {
                if (java.nio.file.Files.notExists(java.nio.file.Paths.get(backgroundImagePath)))
                    throw new java.io.FileNotFoundException("ShapeAppearance.setImage : Not file found according to backgroundImagePath.");

                backgroundImage = javax.imageio.ImageIO.read(new java.io.File(backgroundImagePath));
            }
        }
        catch (java.io.IOException ex) {
            System.out.println(ex);
            backgroundImage = null;
            backgroundImagePath = null;
        }
    }
    
    public void setColor(Color color) {
        backgroundColor = color;
    }
    
    public void setImage(String pathToImage) {
        backgroundImagePath = pathToImage;
        setImage();
    }
    
    public void removeImage() {
        backgroundImagePath = null;
        backgroundImage = null;
    }
    
    public Color getColor() {
        return backgroundColor;
    }
    
    public String getImagePath() {
        return backgroundImagePath;
    }
    
    public BufferedImage getImage() {
        return backgroundImage;
    }
}

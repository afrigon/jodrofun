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
import KeyUtils.KeyShape;
import KeyUtils.Generator.KeyShapeGenerator;
import KeyUtils.Vector2;
import java.awt.geom.Line2D;
import java.awt.Polygon;
import java.awt.Color;
import javax.swing.BorderFactory;

public class ShapePreview extends javax.swing.JPanel {
    private KeyShapeGenerator shapeGen = null;
    private KeyShape shape = null;
    private static java.util.LinkedList<ShapePreview> previews = new java.util.LinkedList<>();
    private static ShapePreview selected = null;
    
    public ShapePreview() {
        ShapePreview.previews.add(this);
        this.setBackground(new Color(51,51,51));
    }
    
    public ShapePreview(KeyShapeGenerator p_shape) {
        ShapePreview.previews.add(this);
        this.setBackground(new Color(51,51,51));
        this.shapeGen = p_shape;
        this.shapeGen.setShouldConvertToRelative(false);
    }
    
    public static void deselect() {
        ShapePreview.selected = null;        
        for (ShapePreview preview: ShapePreview.previews) {
            preview.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 65), 10, true));
        }
    }
    
    public static void repaintAll() {
        ShapePreview.previews.forEach(ShapePreview::repaint);
    }
    
    public void select() {
        ShapePreview.selected = this;
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xf19335), 3, true), BorderFactory.createLineBorder(new Color(65, 65, 65), 7, true)));
    }
    
    public void setShapeGenerator(KeyShapeGenerator gen) {
        this.shapeGen = gen;
        this.shapeGen.setShouldConvertToRelative(false);
        this.repaint();
    }
    
    public KeyShapeGenerator getShapeGenerator() {
        try {
            return this.shapeGen.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            return this.shapeGen;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if(shapeGen != null) {
            this.shape = this.shapeGen.generate(Math.min(this.getWidth() - 20, this.getHeight() - 20),
                                                new Vector2(Math.max(10, (this.getWidth() - this.getHeight() + 20) / 2),
                                                            Math.max(10, (this.getHeight() - this.getWidth() + 20) / 2)));
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

            Polygon p = new Polygon();
            Vector2 prev = null;
            java.util.ArrayList<Line2D> lines = new java.util.ArrayList<>();
            java.util.ArrayList<Color> colors = new java.util.ArrayList<>();
            for(int i = 0; i < shape.getPoints().size(); ++i) {
                Vector2 v = shape.getPoints().get(i);
                p.addPoint((int)v.getX(), (int)v.getY());
                if(prev != null) {
                    lines.add(new Line2D.Double(prev.getX(), prev.getY(), v.getX(), v.getY()));
                    colors.add(shape.getLines().get(i).getColor());
                }
                prev = v;
            }
            if(prev != null) {
                lines.add(new Line2D.Double(prev.getX(), prev.getY(), shape.getPoints().get(0).getX(), shape.getPoints().get(0).getY()));
                    colors.add(shape.getLines().get(shape.getLines().size() - 1).getColor());
            }
            
            g2.setColor(shape.getIdleAppearance().getColor());
            g2.fill(p);
            g2.setStroke(new java.awt.BasicStroke(2));
            for(int i = 0; i < lines.size(); ++i) {
                g2.setColor(colors.get(i));
                g2.draw(lines.get(i));
            }
        }
    }
}

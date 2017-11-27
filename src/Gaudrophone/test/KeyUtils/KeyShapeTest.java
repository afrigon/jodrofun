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
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Olivier
 */
public class KeyShapeTest {
    
    @Test
    public void constructors() {
        List<Vector2> pointList = new ArrayList<>();
        
        Vector2 position = new Vector2(1, 1);
        Vector2 size = new Vector2(100, 100);
        
        pointList.add(new Vector2(position.getX(), position.getY() + size.getY()));
        pointList.add(new Vector2(position.getX() + size.getX()/2, position.getY()));
        pointList.add(new Vector2(position.getX() + size.getX(), position.getY() + size.getY()));
        
        KeyShape triangle = new KeyShape(pointList, new Color(0x979899));
        
        assertEquals(triangle.getSize().getX(), size.getX(), 0);
        assertEquals(triangle.getSize().getY(), size.getY(), 0);
        
    }
}

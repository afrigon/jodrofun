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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olivier
 */
public class Vector2Test {
    
    Vector2 vector0 = new Vector2();
    Vector2 vector1 = new Vector2(1, 0);
    Vector2 vector2 = new Vector2(1, 4);
    Vector2 vector3 = new Vector2(-5, -1);
    Vector2 vector4 = Vector2.fromPolarCoord(4, Math.PI);
    
    @Test
    public void constructors() {
        // Constructor without argument should initialize to zero
        assertEquals(0, vector0.getX(), 0);
        assertEquals(0, vector0.getY(), 0);
        // Constructors with 2 argument
        assertEquals(1, vector1.getX(), 0);
        assertEquals(0, vector1.getY(), 0);
        assertEquals(1, vector2.getX(), 0);
        assertEquals(4, vector2.getY(), 0);
        assertEquals(-5, vector3.getX(), 0);
        assertEquals(-1, vector3.getY(), 0);
        // Constructor fromPolarCoord(double ray, double angle)
        assertEquals(-4, vector4.getX(), 0);
        assertEquals(0, vector4.getY(), 0.000001);
    }
    
    @Test
    public void length() {
        assertEquals(0, vector0.length(), 0);
        assertEquals(1, vector1.length(), 0);
        assertEquals(4.123106, vector2.length(), 0.000001);
        assertEquals(5.099020, vector3.length(), 0.000001);
    }
    
    // operators
    
    @Test
    public void equals() {
        assertEquals(true, vector0.equals(vector0));
        assertEquals(false, vector0.equals(vector1));
        assertEquals(false, vector0.equals(vector1));
        assertEquals(true, vector4.equals(vector4));
    }
    
    @Test
    public void addition() {
        Vector2 vector5 = vector0.add(vector0);
        assertEquals(0, vector5.getX(), 0);
        assertEquals(0, vector5.getY(), 0);
        
        Vector2 vector6 = vector0.add(vector1);
        assertEquals(1, vector6.getX(), 0);
        assertEquals(0, vector6.getY(), 0);
        
        Vector2 vector7 = vector2.add(vector1);
        assertEquals(2, vector7.getX(), 0);
        assertEquals(4, vector7.getY(), 0);
    }
    
    @Test
    public void substraction() {
        Vector2 vector5 = vector0.sub(vector0);
        assertEquals(0, vector5.getX(), 0);
        assertEquals(0, vector5.getY(), 0);
        
        Vector2 vector6 = vector0.sub(vector1);
        assertEquals(-1, vector6.getX(), 0);
        assertEquals(0, vector6.getY(), 0);
        
        Vector2 vector7 = vector2.sub(vector1);
        assertEquals(0, vector7.getX(), 0);
        assertEquals(4, vector7.getY(), 0);
    }
    
    @Test
    public void multiply() {
        Vector2 vector5 = vector0.multiply(45);
        assertEquals(0, vector5.getX(), 0);
        assertEquals(0, vector5.getY(), 0);
        
        Vector2 vector6 = vector1.multiply(2);
        assertEquals(2, vector6.getX(), 0);
        assertEquals(0, vector6.getY(), 0);
        
        Vector2 vector7 = vector2.multiply(-2);
        assertEquals(-2, vector7.getX(), 0);
        assertEquals(-8, vector7.getY(), 0);
    }
    
    @Test
    public void negate() {
        Vector2 vector5 = vector0.negate();
        assertEquals(0, vector5.getX(), 0);
        assertEquals(0, vector5.getY(), 0);
        
        Vector2 vector6 = vector1.negate();
        assertEquals(-1, vector6.getX(), 0);
        assertEquals(0, vector6.getY(), 0);
        
        Vector2 vector7 = vector2.negate();
        assertEquals(-1, vector7.getX(), 0);
        assertEquals(-4, vector7.getY(), 0);
    }
    
    @Test
    public void rotate() {
        Vector2 vector5 = vector0.rotate(vector0, Math.PI/4);
        assertEquals(0, vector5.getX(), 0);
        assertEquals(0, vector5.getY(), 0);
        
        Vector2 vector6 = vector1.rotate(vector0, Math.PI);
        assertEquals(-1, vector6.getX(), 0);
        assertEquals(0, vector6.getY(), 0.0000001);
        
        Vector2 vector7 = vector2.rotate(vector1, -Math.PI);
        assertEquals(1, vector7.getX(), 0.000000001);
        assertEquals(-4, vector7.getY(), 0);
        
        Vector2 vector8 = vector2.rotate(vector3, Math.PI/6);
        assertEquals(-2.303848, vector8.getX(), 0.000001);
        assertEquals(6.330127, vector8.getY(), 0.000001);
        
        Vector2 vector9 = vector2.rotate(vector3, - 11 * Math.PI/6);
        assertEquals(-2.303848, vector9.getX(), 0.000001);
        assertEquals(6.330127, vector9.getY(), 0.000001);
    }
    
    @Test
    public void unit() {
        Vector2 vector5 = vector1.unit();
        assertEquals(1, vector5.getX(), 0);
        assertEquals(0, vector5.getY(), 0);
        
        Vector2 vector6 = vector2.unit();
        assertEquals(0.242536, vector6.getX(), 0.000001);
        assertEquals(0.9701425, vector6.getY(), 0.000001);
        
        Vector2 vector7 = vector3.unit();
        assertEquals(-0.980581, vector7.getX(), 0.000001);
        assertEquals(-0.196116, vector7.getY(), 0.000001);
    }
    
    @Test(expected = ArithmeticException.class)
    public void unitException() {
        Vector2 vector5 = vector0.unit();
    }
    
    @Test
    public void dotProduct() {
        double result0 = vector0.dotProduct(vector0);
        assertEquals(0, result0, 0);
        
        double result1 = vector0.dotProduct(vector1);
        assertEquals(0, result1, 0);
        
        double result2 = vector1.dotProduct(vector1);
        assertEquals(1, result2, 0);
        
        double result3 = vector1.dotProduct(vector2);
        assertEquals(1, result3, 0);
        
        double result4 = vector2.dotProduct(vector2);
        assertEquals(17, result4, 0);
        
        double result5 = vector2.dotProduct(vector3);
        assertEquals(-9, result5, 0);
        
        double result6 = vector3.dotProduct(vector3);
        assertEquals(26, result6, 0);
    }
    
}

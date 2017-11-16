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
public class ComplexTest {
    
    Complex number1 = new Complex(1);
    Complex number2 = new Complex(-4, 2.5);
    Complex number3 = Complex.fromPolarCoord(3.34, 3 * Math.PI / 2);
    
    @Test
    public void constructors() {
        // Constructor with 1 argument
        assertEquals(1, number1.getReal(), 0);
        assertEquals(0, number1.getImaginary(), 0);
        // Constructors with 2 argument
        assertEquals(-4, number2.getReal(), 0);
        assertEquals(2.5, number2.getImaginary(), 0);
        // Constructor fromPolarCoord(double ray, double angle)
        assertEquals(0, number3.getReal(), 0.00000001);
        assertEquals(-3.34, number3.getImaginary(), 0);
    }
    
    @Test
    public void length() {
        assertEquals(1, number1.length(), 0);
        assertEquals(4.717, number2.length(), 0.001);
        assertEquals(3.34, number3.length(), 0);
    }
    
    @Test
    public void angle() {
        assertEquals(0, number1.getAngle(), 0);
        assertEquals(2.583, number2.getAngle(), 0.001);
        assertEquals(Math.atan2(-3.34, 0), number3.getAngle(), 0.0000001);
    }
    
    // Tests operators
    
    @Test
    public void addition() {
        Complex number4 = number1.add(number1);
        assertEquals(2, number4.getReal(), 0);
        assertEquals(0, number4.getImaginary(), 0);
        
        Complex number5 = number2.add(number1);
        assertEquals(-3, number5.getReal(), 0);
        assertEquals(2.5, number5.getImaginary(), 0);
        
        Complex number6 = number5.add(number5);
        assertEquals(-6, number6.getReal(), 0);
        assertEquals(5, number6.getImaginary(), 0);
    }
    
    @Test
    public void substraction() {
        Complex number4 = number1.sub(number1);
        assertEquals(0, number4.getReal(), 0);
        assertEquals(0, number4.getImaginary(), 0);
        
        Complex number5 = number2.sub(number1);
        assertEquals(-5, number5.getReal(), 0);
        assertEquals(2.5, number5.getImaginary(), 0);
        
        Complex number6 = number1.sub(number2);
        assertEquals(5, number6.getReal(), 0);
        assertEquals(-2.5, number6.getImaginary(), 0);
    }
    
    @Test
    public void conjugate() {
        Complex number4 = number1.conjugate();
        assertEquals(1, number4.getReal(), 0);
        assertEquals(0, number4.getImaginary(), 0);
        
        Complex number5 = number2.conjugate();
        assertEquals(-4, number5.getReal(), 0);
        assertEquals(-2.5, number5.getImaginary(), 0);
    }
    
    @Test
    public void multiply() {
        Complex number4 = number1.multiply(number1);
        assertEquals(1, number4.getReal(), 0);
        assertEquals(0, number4.getImaginary(), 0);
        
        Complex number5 = number2.multiply(number1);
        assertEquals(-4, number5.getReal(), 0);
        assertEquals(2.5, number5.getImaginary(), 0);
        
        Complex number6 = number2.multiply(number2);
        assertEquals(9.75, number6.getReal(), 0);
        assertEquals(-20, number6.getImaginary(), 0);
    }
    
    @Test
    public void divide() {
        Complex number4 = number1.divide(number1);
        assertEquals(1, number4.getReal(), 0);
        assertEquals(0, number4.getImaginary(), 0);
        
        Complex number5 = number2.divide(number1);
        assertEquals(-4, number5.getReal(), 0);
        assertEquals(2.5, number5.getImaginary(), 0);
        
        Complex number6 = number1.divide(number2);
        assertEquals(-0.17978, number6.getReal(), 0.00001);
        assertEquals(-0.112360, number6.getImaginary(), 0.00001);
    }
    
    
}

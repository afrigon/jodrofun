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

/**
 *
 * @author Olivier
 */
public class Vector2 {
    private final double x;
    private final double y;
    
    
    // Constructors
    public Vector2() {
        x = 0;
        y = 0;
    }
    public Vector2(double coordX, double coordY) {
        x = coordX;
        y = coordY;
    }
    
    // Methods
    public double getX() {  return x; }
    
    public double getY() { return y; }
    
    public double length() { return Math.sqrt(x * x + y * y); }
    
    public static Vector2 fromPolarCoord(double ray, double angle) {
        return new Vector2(ray * Math.cos(angle), ray * Math.sin(angle));
    }
    
    // Operators
    public boolean equals(Vector2 otherVector) {
        return x == otherVector.getX() && y == otherVector.getY();
    }
    
    public Vector2 add(Vector2 otherVector) {
        return new Vector2(x + otherVector.getX(), y + otherVector.getY());
    }
    
    public Vector2 sub(Vector2 otherVector) {
        return new Vector2(x - otherVector.getX(), y - otherVector.getY());
    }
    
    public Vector2 multiply(double scalar) {
        return new Vector2(scalar * x, scalar * y);
    }
    
    public Vector2 negate() {
        return new Vector2(-x, -y);
    }
    
    public Vector2 rotate(Vector2 anchor, double angle) {
        Complex complex = new Complex(x - anchor.getX(), y - anchor.getY());
        Complex rotated = complex.multiply(Complex.fromPolarCoord(1, angle));
        return new Vector2(anchor.getX() + rotated.getReal(), anchor.getY() + rotated.getImaginary());
    }
    
    public Vector2 unit() {
        double magnitude = length();
        if (magnitude > 0) {
            return new Vector2(x / length(), y / length());
        } else {
            throw new java.lang.ArithmeticException("Can not normalized a null vector.");
        }
    }
    
    public double dotProduct(Vector2 otherVector) {
        return x * otherVector.getX() + y * otherVector.getY();
    }
    
}

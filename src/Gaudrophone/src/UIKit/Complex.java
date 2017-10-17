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
package UIKit;

/**
 *
 * @author Olivier
 */
public class Complex {
    private double real;
    private double imaginary;
    
    // Constructors
    public Complex() {
        real = 0;
        imaginary = 0;
    }
    
    public Complex(double realPart) {
        real = realPart;
    }
    
    public Complex(double realPart, double imaginaryPart) {
        real = realPart;
        imaginary = imaginaryPart;
    }
    
    // Methods
    public double getReal() { return real; }
    public double getImaginary() { return imaginary; }
    public double length() { return Math.sqrt(real * real + imaginary * imaginary); }
    public double getAngle() { return Math.atan2(imaginary, real); }
    
    static Complex fromPolarCoord(double ray, double angle) {
        return new Complex(ray * Math.cos(angle), ray * Math.sin(angle));
    }
    
    // Operators
    public Complex add(Complex otherComplex) {
        return new Complex(real + otherComplex.getReal(), imaginary + otherComplex.getImaginary());
    }
    
    public Complex sub(Complex otherComplex) {
        return new Complex(real - otherComplex.getReal(), imaginary - otherComplex.getImaginary());
    }
    
    public Complex conjugate() { return new Complex(real, -imaginary); }
    
    public Complex multiply(Complex otherComplex) {
        return new Complex(real * otherComplex.getReal() - imaginary * otherComplex.getImaginary(), real * otherComplex.getImaginary() + imaginary * otherComplex.getReal());
    }
    
    public Complex divide(Complex otherComplex) {
        double otherReal = otherComplex.getReal();
        double otherImaginary = otherComplex.getImaginary();
        double denom = otherReal * otherReal + otherImaginary * otherImaginary;
        return new Complex((real * otherReal + imaginary * otherImaginary) / denom, (imaginary * otherReal - real * otherImaginary) / denom);
    }
}

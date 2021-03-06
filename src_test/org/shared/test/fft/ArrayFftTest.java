/**
 * <p>
 * Copyright (c) 2007 The Regents of the University of California<br>
 * All rights reserved.
 * </p>
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 * </p>
 * <ul>
 * <li>Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.</li>
 * <li>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.</li>
 * <li>Neither the name of the author nor the names of any contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.</li>
 * </ul>
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * </p>
 */

package org.shared.test.fft;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.shared.array.Array;
import org.shared.array.ComplexArray;
import org.shared.array.RealArray;
import org.shared.fft.JavaFftService;
import org.shared.test.Tests;
import org.shared.util.Arithmetic;

/**
 * A class of unit tests of FFT functionality for all sorts of {@link Array} instances.
 * 
 * @author Roy Liu
 */
public class ArrayFftTest {

    /**
     * Default constructor.
     */
    public ArrayFftTest() {
    }

    /**
     * Tests {@link ComplexArray#fft()} and {@link RealArray#rfft()} in one dimension.
     */
    @Test
    public void testOneDimensionalFft() {

        ComplexArray a = new ComplexArray(new double[] {
                //
                1, 1, 2, 2, 3, 3 //
                } //
        );

        ComplexArray b = new ComplexArray(new double[] {
                //
                3, 3, 2, 2, 1, 1 //
                } //
        );

        ComplexArray expected = new ComplexArray(new double[] {
                //
                0, 22, 0, 22, 0, 28 //
                } //
        );

        Assert.assertTrue(Tests.equals( //
                a.fft().eMul(b.fft()).ifft().values(), expected.values()));

        RealArray aReal = new RealArray(new double[] {
                //
                1, 2, 3, 4, 5, 6, 7, 8, 9, //
                10, 11, 12, 13, 14, 15, 16, 17, 18, //
                19, 20, 21, 22, 23, 24, 25, 26, 27 //
                } //
        );

        RealArray bReal = new RealArray(new double[] {
                //
                1, 2, 3, 0, 0, 0, 0, 0, 0, //
                0, 0, 0, 0, 0, 0, 0, 0, 0, //
                0, 0, 0, 0, 0, 0, 0, 0, 0 //
                } //
        );

        RealArray expectedReal = new RealArray(new double[] {
                //
                133, 85, 10, 16, 22, 28, 34, 40, 46, //
                52, 58, 64, 70, 76, 82, 88, 94, 100, //
                106, 112, 118, 124, 130, 136, 142, 148, 154 //
                } //
        );

        Assert.assertTrue(Tests.equals( //
                aReal.rfft().eMul(bReal.rfft()).rifft().values(), expectedReal.values()));
    }

    /**
     * Tests {@link ComplexArray#fft()} and {@link RealArray#rfft()} in two dimensions.
     */
    @Test
    public void testTwoDimensionalFft() {

        RealArray a = new RealArray(new double[] {
                //
                0, 1, 2, 3, 4, 5, 6, //
                7, 8, 9, 10, 11, 12, 13, //
                14, 15, 16, 17, 18, 19, 20, //
                21, 22, 23, 24, 25, 26, 27, //
                28, 29, 30, 31, 32, 33, 34, //
                35, 36, 37, 38, 39, 40, 41, //
                42, 43, 44, 45, 46, 47, 48 //
                }, //
                7, 7 //
        );

        RealArray b = new RealArray(new double[] {
                //
                -1, 2, 2, -1, 0, 0, 0, //
                0, 1, 1, -4, 0, 0, 0, //
                0, 0, 1, -2, 0, 0, 0, //
                0, 0, 0, 1, 0, 0, 0, //
                0, 0, 0, 0, 0, 0, 0, //
                0, 0, 0, 0, 0, 0, 0, //
                0, 0, 0, 0, 0, 0, 0 //
                }, //
                7, 7 //
        );

        RealArray expected = new RealArray(new double[] {
                //
                -14, -14, -14, -14, 28, 0, -21, //
                -14, -14, -14, -14, 28, 0, -21, //
                -14, -14, -14, -14, 28, 0, -21, //
                -14, -14, -14, -14, 28, 0, -21, //
                -63, -63, -63, -63, -21, -49, -70, //
                -14, -14, -14, -14, 28, 0, -21, //
                84, 84, 84, 84, 126, 98, 77 //
                }, //
                7, 7 //
        );

        Assert.assertTrue(Tests.equals( //
                ((a.rfft()).eMul(b.rfft().uConj())).rifft().values(), expected.values()));

        Assert.assertTrue(Tests.equals( //
                ((a.tocRe().fft()).eMul(b.tocRe().fft().uConj())).ifft().torRe().values(), //
                expected.values()));
    }

    /**
     * Tests {@link ComplexArray#fft()} and {@link RealArray#rfft()} in three dimensions.
     */
    @Test
    public void testThreeDimensionalFft() {

        RealArray a = new RealArray(new double[] {
                //
                1, 4, 7, //
                2, 5, 8, //
                3, 6, 9, //
                //
                10, 13, 16, //
                11, 14, 17, //
                12, 15, 18, //
                //
                19, 22, 25, //
                20, 23, 26, //
                21, 24, 27 //
                }, //
                3, 3, 3 //
        );

        RealArray b = new RealArray(new double[] {
                //
                -2, -2, 1, //
                1, 1, 1, //
                0, 0, 0, //
                //
                1, 0, 1, //
                0, -2, 0, //
                0, 0, 0, //
                //
                0, 0, 0, //
                0, 0, 0, //
                0, 0, 0 //
                }, //
                3, 3, 3 //
        );

        RealArray expected = new RealArray(new double[] {
                //
                -7, 20, -7, //
                -10, 17, -10, //
                -10, 17, -10, //
                //
                -7, 20, -7, //
                -10, 17, -10, //
                -10, 17, -10, //
                //
                -7, 20, -7, //
                -10, 17, -10, //
                -10, 17, -10 //
                }, //
                3, 3, 3 //
        );

        Assert.assertTrue(Tests.equals( //
                a.tocRe().fft().eMul(b.tocRe().fft()).ifft().torRe().values(), expected.values()));

        Assert.assertTrue(Tests.equals( //
                a.rfft().eMul(b.rfft()).rifft().values(), expected.values()));
    }

    /**
     * Tests {@link ComplexArray#fftShift()}.
     */
    @Test
    public void testFftShift() {

        ComplexArray a = new ComplexArray(new double[] {
                //
                1, 0, 4, 0, 7, 0, 10, 0, //
                2, 0, 5, 0, 8, 0, 11, 0, //
                3, 0, 6, 0, 9, 0, 12, 0, //
                //
                13, 0, 16, 0, 19, 0, 22, 0, //
                14, 0, 17, 0, 20, 0, 23, 0, //
                15, 0, 18, 0, 21, 0, 24, 0, //
                //
                25, 0, 28, 0, 31, 0, 34, 0, //
                26, 0, 29, 0, 32, 0, 35, 0, //
                27, 0, 30, 0, 33, 0, 36, 0, //
                //
                37, 0, 40, 0, 43, 0, 46, 0, //
                38, 0, 41, 0, 44, 0, 47, 0, //
                39, 0, 42, 0, 45, 0, 48, 0, //
                //
                49, 0, 52, 0, 55, 0, 58, 0, //
                50, 0, 53, 0, 56, 0, 59, 0, //
                51, 0, 54, 0, 57, 0, 60, 0 //
                }, //
                5, 3, 4, 2 //
        );

        ComplexArray expected = new ComplexArray(new double[] {
                //
                45, 0, 48, 0, 39, 0, 42, 0, //
                43, 0, 46, 0, 37, 0, 40, 0, //
                44, 0, 47, 0, 38, 0, 41, 0, //
                //
                57, 0, 60, 0, 51, 0, 54, 0, //
                55, 0, 58, 0, 49, 0, 52, 0, //
                56, 0, 59, 0, 50, 0, 53, 0, //
                //
                9, 0, 12, 0, 3, 0, 6, 0, //
                7, 0, 10, 0, 1, 0, 4, 0, //
                8, 0, 11, 0, 2, 0, 5, 0, //
                //
                21, 0, 24, 0, 15, 0, 18, 0, //
                19, 0, 22, 0, 13, 0, 16, 0, //
                20, 0, 23, 0, 14, 0, 17, 0, //
                //
                33, 0, 36, 0, 27, 0, 30, 0, //
                31, 0, 34, 0, 25, 0, 28, 0, //
                32, 0, 35, 0, 26, 0, 29, 0 //
                }, //
                5, 3, 4, 2 //
        );

        Assert.assertTrue(Arrays.equals(a.fftShift().values(), expected.values()));
        Assert.assertTrue(Arrays.equals(a.fftShift().ifftShift().values(), a.values()));
    }

    /**
     * Tests {@link JavaFftService#reducedToFull(ComplexArray, int[])}.
     */
    @Test
    public void testReducedToFull() {

        JavaFftService jfs = new JavaFftService();

        int baseSize = 5;
        int nDims = 5;

        int[] dims = new int[nDims];

        for (int i = 0, n = 1 << nDims; i < n; i++) {

            for (int dim = 0; dim < nDims; dim++) {
                dims[dim] = baseSize + ((i >>> dim) & 0x1);
            }

            RealArray arr = new RealArray(dims);
            double[] values = arr.values();

            for (int j = 0, m = values.length; j < m; j++) {
                values[j] = Arithmetic.nextInt(2);
            }

            Tests.equals( //
                    arr.tocRe().fft().values(), //
                    jfs.reducedToFull(arr.rfft(), dims).values());
        }
    }
}

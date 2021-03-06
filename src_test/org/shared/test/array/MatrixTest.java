/**
 * <p>
 * Copyright (c) 2009 Roy Liu<br>
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

package org.shared.test.array;

import static org.shared.array.ArrayBase.opKernel;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shared.array.ComplexArray;
import org.shared.array.Matrix;
import org.shared.array.RealArray;
import org.shared.test.Tests;
import org.shared.util.Control;

/**
 * A class of unit tests for {@link Matrix}.
 * 
 * @author Roy Liu
 */
public class MatrixTest {

    /**
     * Default constructor.
     */
    public MatrixTest() {
    }

    /**
     * Seeds random sources so that results are predictable.
     */
    @BeforeClass
    final public static void initClass() {
        opKernel.derandomize();
    }

    /**
     * Tests {@link Matrix#mMul(Matrix)}.
     */
    @Test
    public void testMMul() {

        RealArray a = new RealArray(new double[] {
                //
                0, 1, 2, //
                3, 4, 5, //
                6, 7, 8, //
                9, 10, 11 //
                }, //
                4, 3 //
        );

        RealArray expected = new RealArray(new double[] {
                //
                5, 14, 23, 32, //
                14, 50, 86, 122, //
                23, 86, 149, 212, //
                32, 122, 212, 302 //
                }, //
                4, 4 //
        );

        Assert.assertTrue(Tests.equals( //
                a.mMul(a.transpose(1, 0)).values(), expected.values()));

        ComplexArray c1 = new ComplexArray(new double[] {
                //
                1, 1, 4, 4, 7, 7, 10, 10, 13, 13, //
                2, 2, 5, 5, 8, 8, 11, 11, 14, 14, //
                3, 3, 6, 6, 9, 9, 12, 12, 15, 15 //
                }, //
                3, 5, 2 //
        );

        ComplexArray c2 = new ComplexArray(new double[] {
                //
                1, 1, 2, 2, 3, 3, //
                4, 4, 5, 5, 6, 6, //
                7, 7, 8, 8, 9, 9, //
                10, 10, 11, 11, 12, 12, //
                13, 13, 14, 14, 15, 15 //
                }, //
                5, 3, 2 //
        );

        ComplexArray cExpected = new ComplexArray(new double[] {
                //
                0, 670, 0, 740, 0, 810, //
                0, 740, 0, 820, 0, 900, //
                0, 810, 0, 900, 0, 990 //
                }, //
                3, 3, 2 //
        );

        Assert.assertTrue(Tests.equals( //
                c1.mMul(c2).values(), cExpected.values()));
    }

    /**
     * Tests {@link Matrix#mDiag()}.
     */
    @Test
    public void testMDiag() {

        RealArray a = new RealArray(new double[] {
                //
                0, 1, 2, 3, 4, //
                5, 6, 7, 8, 9, //
                10, 11, 12, 13, 14, //
                15, 16, 17, 18, 19, //
                20, 21, 22, 23, 24 //
                }, //
                5, 5 //
        );

        RealArray expected = new RealArray(new double[] {
                //
                0, //
                6, //
                12, //
                18, //
                24 //
                }, //
                5, 1 //
        );

        Assert.assertTrue(Tests.equals( //
                a.mDiag().values(), expected.values()));

        ComplexArray cA = new ComplexArray(new double[] {
                //
                0, 0, 1, 0, 2, 0, 3, 0, 4, 0, //
                5, 0, 6, 0, 7, 0, 8, 0, 9, 0, //
                10, 0, 11, 0, 12, 0, 13, 0, 14, 0, //
                15, 0, 16, 0, 17, 0, 18, 0, 19, 0, //
                20, 0, 21, 0, 22, 0, 23, 0, 24, 0 //
                }, //
                5, 5, 2 //
        );

        ComplexArray cExpected = new ComplexArray(new double[] {
                //
                0, 0, //
                6, 0, //
                12, 0, //
                18, 0, //
                24, 0 //
                }, //
                5, 1, 2 //
        );

        Assert.assertTrue(Tests.equals( //
                cA.mDiag().values(), cExpected.values()));
    }

    /**
     * Tests {@link Matrix#mSvd()}.
     */
    @Test
    public void testMSvd() {

        int size = 120;

        loop: for (int i = 1; i <= size; i++) {

            if (size % i != 0) {
                continue loop;
            }

            RealArray r = new RealArray(size / i, i).uRnd(1.0);
            RealArray[] svds = r.mSvd();

            Control.checkTrue(r.eSub(svds[0].mMul(svds[1]).mMul(svds[2].mTranspose())) //
                    .uAbs().aSum() < 1e-8);
        }
    }

    /**
     * Tests {@link Matrix#mEigs()}.
     */
    @Test
    public void testMEigs() {

        int size = 128;
        int nTrials = 16;

        for (int i = 0; i < nTrials; i++) {

            RealArray r = new RealArray(size, size).uRnd(1.0);
            RealArray[] eigs = r.mEigs();

            Assert.assertTrue(r.mMul(eigs[0]).lSub(eigs[0].mMul(eigs[1])) //
                    .uAbs().aSum() < 1e-8);
        }
    }

    /**
     * Tests {@link Matrix#mInvert()}.
     */
    @Test
    public void testMInvert() {

        int size = 128;
        int nTrials = 16;

        RealArray eye = RealArray.eye(size, 2);

        for (int i = 0; i < nTrials; i++) {

            RealArray r = new RealArray(size, size).uRnd(1.0).lAdd(eye);

            Assert.assertTrue(r.mMul(r.mInvert()).lSub(eye) //
                    .uAbs().aSum() < 1e-8);
        }
    }
}

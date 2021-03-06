/**
 * <p>
 * Copyright (c) 2007 Roy Liu<br>
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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.shared.array.Array;
import org.shared.array.Array.IndexingOrder;
import org.shared.array.IntegerArray;

/**
 * A class of unit tests for {@link IntegerArray}.
 * 
 * @author Roy Liu
 */
public class IntegerArrayTest {

    /**
     * Default constructor.
     */
    public IntegerArrayTest() {
    }

    /**
     * Tests {@link Array#map(Array, int...)}.
     */
    @Test
    public void testMap() {

        IntegerArray a = new IntegerArray(new int[] {
                //
                0, 1, 2, 3, //
                4, 5, 6, 7, //
                8, 9, 10, 11, //
                //
                12, 13, 14, 15, //
                16, 17, 18, 19, //
                20, 21, 22, 23, //
                //
                24, 25, 26, 27, //
                28, 29, 30, 31, //
                32, 33, 34, 35 //
                }, //
                IndexingOrder.FAR, //
                3, 3, 4 //
        );

        IntegerArray expected = a.map( //
                new IntegerArray(IndexingOrder.NEAR, 3, 3, 4), //
                1, 0, 2, //
                0, 1, 2, //
                1, 0, 3);

        a = a.map(new IntegerArray(IndexingOrder.FAR, 3, 3, 4), //
                1, 0, 2, 0, 1, 2, 1, 0, 3);

        Assert.assertTrue(Arrays.equals(a.reverseOrder().values(), expected.values()));
    }

    /**
     * Tests {@link Array#splice(Array, int...)}.
     */
    @Test
    public void testSlice() {

        IntegerArray a = new IntegerArray(new int[] {
                //
                0, 1, 2, 3, 4, //
                5, 6, 7, 8, 9, //
                10, 11, 12, 13, 14, //
                15, 16, 17, 18, 19, //
                //
                20, 21, 22, 23, 24, //
                25, 26, 27, 28, 29, //
                30, 31, 32, 33, 34, //
                35, 36, 37, 38, 39, //
                //
                40, 41, 42, 43, 44, //
                45, 46, 47, 48, 49, //
                50, 51, 52, 53, 54, //
                55, 56, 57, 58, 59 //
                }, //
                IndexingOrder.FAR, //
                3, 4, 5 //
        );

        a = a.splice(new IntegerArray(IndexingOrder.NEAR, 2, 3, 4), //
                //
                1, 0, 0, //
                1, 1, 0, //
                //
                1, 0, 1, //
                1, 1, 1, //
                //
                1, 0, 2, //
                2, 1, 2, //
                2, 2, 2, //
                3, 3, 2);

        IntegerArray expected = new IntegerArray(new int[] {
                //
                26, 27, 27, 28, //
                26, 27, 27, 28, //
                0, 0, 0, 0, //
                //
                26, 27, 27, 28, //
                26, 27, 27, 28, //
                0, 0, 0, 0 //
                }, //
                IndexingOrder.FAR, //
                2, 3, 4 //
        );

        Assert.assertTrue(Arrays.equals(a.reverseOrder().values(), expected.values()));

        a = new IntegerArray(new int[] {
                //
                0, 1, 2, 3, 4, //
                5, 6, 7, 8, 9, //
                10, 11, 12, 13, 14, //
                15, 16, 17, 18, 19, //
                20, 21, 22, 23, 24 //
                }, //
                IndexingOrder.FAR, //
                5, 5 //
        );

        a = a.splice(new IntegerArray(IndexingOrder.FAR, 3, 3), //
                //
                1, 0, 0, //
                2, 1, 0, //
                3, 2, 0, //
                //
                0, 0, 1, //
                2, 1, 1, //
                4, 2, 1);

        expected = new IntegerArray(new int[] {
                //
                5, 7, 9, //
                10, 12, 14, //
                15, 17, 19 //
                }, //
                IndexingOrder.FAR, //
                3, 3 //
        );

        Assert.assertTrue(Arrays.equals(a.values(), expected.values()));
    }

    /**
     * Tests {@link IntegerArray#find(int...)}.
     */
    @Test
    public void testFind() {

        IntegerArray a = new IntegerArray(new int[] {
                //
                0, 1, 2, 3, -1, //
                5, 6, 7, -1, -1, //
                10, 11, -1, -1, -1, //
                15, -1, -1, -1, -1, //
                //
                20, 21, 22, 23, -1, //
                25, 26, 27, -1, -1, //
                30, 31, -1, -1, -1, //
                35, -1, -1, -1, -1, //
                //
                40, 41, 42, 43, -1, //
                45, 46, 47, -1, -1, //
                50, 51, -1, -1, -1, //
                55, -1, -1, -1, -1 //
                }, //
                IndexingOrder.FAR, //
                3, 4, 5 //
        );

        Assert.assertTrue(Arrays.equals(a.find(0, 0, -1), new int[] { 0, 1, 2, 3 }));
        Assert.assertTrue(Arrays.equals(a.find(0, 1, -1), new int[] { 5, 6, 7 }));
        Assert.assertTrue(Arrays.equals(a.find(0, 2, -1), new int[] { 10, 11 }));
        Assert.assertTrue(Arrays.equals(a.find(0, 3, -1), new int[] { 15 }));
        Assert.assertTrue(Arrays.equals(a.find(1, 0, -1), new int[] { 20, 21, 22, 23 }));
        Assert.assertTrue(Arrays.equals(a.find(1, 1, -1), new int[] { 25, 26, 27 }));
        Assert.assertTrue(Arrays.equals(a.find(1, 2, -1), new int[] { 30, 31 }));
        Assert.assertTrue(Arrays.equals(a.find(1, 3, -1), new int[] { 35 }));
        Assert.assertTrue(Arrays.equals(a.find(2, 0, -1), new int[] { 40, 41, 42, 43 }));
        Assert.assertTrue(Arrays.equals(a.find(2, 1, -1), new int[] { 45, 46, 47 }));
        Assert.assertTrue(Arrays.equals(a.find(2, 2, -1), new int[] { 50, 51 }));
        Assert.assertTrue(Arrays.equals(a.find(2, 3, -1), new int[] { 55 }));
    }

    /**
     * Tests {@link IntegerArray#ndgrid(int...)}.
     */
    @Test
    public void testNdGrid() {

        IntegerArray[] arrays = IntegerArray.ndgrid(-3, 0, 1, 3, 0, -1, 6, 0, -2);

        Assert.assertTrue(Arrays.equals(arrays[0].values(), new int[] {
                //
                -3, -3, -3, //
                -3, -3, -3, //
                -3, -3, -3, //
                //
                -2, -2, -2, //
                -2, -2, -2, //
                -2, -2, -2, //
                //
                -1, -1, -1, //
                -1, -1, -1, //
                -1, -1, -1 //
                }) //
        );

        Assert.assertTrue(Arrays.equals(arrays[1].values(), new int[] {
                //
                3, 3, 3, //
                2, 2, 2, //
                1, 1, 1, //
                //
                3, 3, 3, //
                2, 2, 2, //
                1, 1, 1, //
                //
                3, 3, 3, //
                2, 2, 2, //
                1, 1, 1 //
                }) //
        );

        Assert.assertTrue(Arrays.equals(arrays[2].values(), new int[] {
                //
                6, 4, 2, //
                6, 4, 2, //
                6, 4, 2, //
                //
                6, 4, 2, //
                6, 4, 2, //
                6, 4, 2, //
                //
                6, 4, 2, //
                6, 4, 2, //
                6, 4, 2 //
                }) //
        );
    }
}

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

package org.shared.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.shared.array.Array.IndexingOrder;
import org.shared.array.RealArray;
import org.shared.util.Control;

/**
 * A static utility class for reading and writing intensity images represented as {@link RealArray}s.
 * 
 * @author Roy Liu
 */
public class IntensityImages {

    /**
     * A mapping of color names to interpolating colormaps.
     */
    final protected static Map<String, int[]> colormaps;

    static {

        colormaps = new HashMap<String, int[]>();

        colormaps.put("gray", createInterpolatingColormap( //
                0, 0xFF000000, //
                255, 0xFFFFFFFF));

        colormaps.put("jet", createInterpolatingColormap( //
                0, 0xFF000080, //
                31, 0xFF0000FF, //
                95, 0xFF00FFFF, //
                160, 0xFFFFFF00, //
                223, 0xFFFF0000, //
                255, 0xFF800000));
    }

    /**
     * Converts a {@link RealArray} to a {@link BufferedImage}.
     * 
     * @param m
     *            the intensity image.
     * @param cmName
     *            the name of the colormap to display with.
     * @param rangeMin
     *            the minimum intensity.
     * @param rangeMax
     *            the maximum intensity.
     * @return an image representation.
     */
    final public static BufferedImage createImage(RealArray m, String cmName, double rangeMin, double rangeMax) {

        Control.checkTrue(m.nDims() == 2, //
                "Number of array dimensions must equal two");

        Control.checkTrue(rangeMax > rangeMin, //
                "Invalid intensity range");

        int[] colormap = colormaps.get(cmName);

        Control.checkTrue(colormap != null, //
                "Invalid colormap name");

        m = m.transpose(1, 0).reverseOrder().uAdd(-rangeMin) //
                .uMul(1.0 / (rangeMax - rangeMin));

        double[] backing = m.values();
        int[] arr = new int[backing.length];

        for (int i = 0, n = backing.length; i < n; i++) {
            arr[i] = colormap[(int) (backing[i] * 255)];
        }

        BufferedImage bi = new BufferedImage(m.size(0), m.size(1), BufferedImage.TYPE_INT_RGB);
        bi.getRaster().setDataElements(0, 0, m.size(0), m.size(1), arr);

        return bi;
    }

    /**
     * Converts a {@link BufferedImage} to a {@link RealArray}.
     * 
     * @param image
     *            the original image.
     * @return an intensity image representation.
     */
    final public static RealArray createMatrix(BufferedImage image) {

        Raster raster = ensureGrayscale(image).getRaster();

        RealArray res = new RealArray(IndexingOrder.NEAR, image.getWidth(), image.getHeight());

        raster.getSamples(0, 0, image.getWidth(), image.getHeight(), 0, res.values());

        return res.reverseOrder().transpose(1, 0).uMul(1.0d / 256);
    }

    /**
     * Creates a {@link RealArray} from an image file.
     * 
     * @param file
     *            the image file.
     * @return the intensity image.
     * @throws IOException
     *             when the load operation goes awry.
     */
    final public static RealArray createMatrix(File file) throws IOException {
        return createMatrix(ImageIO.read(file));
    }

    /**
     * Creates a {@link RealArray} from a {@code byte} array.
     * 
     * @param data
     *            the binary data.
     * @return the intensity image.
     * @throws IOException
     *             when the load operation goes awry.
     */
    final public static RealArray createMatrix(byte[] data) throws IOException {
        return createMatrix(bytesToJpeg(data));
    }

    /**
     * Converts a {@link BufferedImage} to a {@code byte} array.
     * 
     * @param bi
     *            the image.
     * @return the binary data.
     * @throws IOException
     *             when the encoding operation goes awry.
     */
    final public static byte[] jpegToBytes(BufferedImage bi) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(ensureGrayscale(bi), "jpeg", out);

        return out.toByteArray();
    }

    /**
     * Converts a {@code byte} array to a {@link BufferedImage}.
     * 
     * @param data
     *            the binary data.
     * @return the image.
     * @throws IOException
     *             when the decoding operation goes awry.
     */
    final public static BufferedImage bytesToJpeg(byte[] data) throws IOException {

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        BufferedImage bi = ImageIO.read(in);

        return bi;
    }

    /**
     * Ensures that an image is in grayscale, converting it if necessary.
     */
    final protected static BufferedImage ensureGrayscale(BufferedImage src) {

        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), //
                BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g2 = dst.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();

        return dst;
    }

    /**
     * Creates a colormap by interpolating between start, end, and intermediate colors.
     */
    final protected static int[] createInterpolatingColormap(int... args) {

        Control.checkTrue(args.length < 4 //
                || args.length % 2 == 0 //
                || args[0] != 0 //
                || args[args.length - 2] != 255, //
                "Invalid arguments");

        int[] indices = new int[args.length / 2];
        int[] values = new int[args.length / 2];

        for (int i = 0, n = indices.length; i < n; i++) {

            indices[i] = args[2 * i];
            values[i] = args[2 * i + 1];
        }

        int[] colormap = new int[256];

        for (int i = 1, n = indices.length; i < n; i++) {

            int lower = indices[i - 1];
            int upper = indices[i];
            int diff = upper - lower;

            Control.checkTrue(diff > 0, //
                    "Invalid arguments");

            int srcArgb = values[i - 1];
            int dstArgb = values[i];

            int aSrc = (srcArgb >>> 24) & 0xFF;
            int rSrc = (srcArgb >>> 16) & 0xFF;
            int gSrc = (srcArgb >>> 8) & 0xFF;
            int bSrc = srcArgb & 0xFF;

            double aIncr = (((dstArgb >>> 24) & 0xFF) - aSrc) / (double) diff;
            double rIncr = (((dstArgb >>> 16) & 0xFF) - rSrc) / (double) diff;
            double gIncr = (((dstArgb >>> 8) & 0xFF) - gSrc) / (double) diff;
            double bIncr = ((dstArgb & 0xFF) - bSrc) / (double) diff;

            for (int j = lower; j <= upper; j++) {

                int a = ((((int) Math.round(aSrc + (j - lower) * aIncr)) & 0xFF) << 24);
                int r = ((((int) Math.round(rSrc + (j - lower) * rIncr)) & 0xFF) << 16);
                int g = ((((int) Math.round(gSrc + (j - lower) * gIncr)) & 0xFF) << 8);
                int b = (((int) Math.round(bSrc + (j - lower) * bIncr)) & 0xFF);

                colormap[j] = a | r | g | b;
            }
        }

        return colormap;
    }

    // Dummy constructor.
    IntensityImages() {
    }
}

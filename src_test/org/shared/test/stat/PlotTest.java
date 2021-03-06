/**
 * <p>
 * Copyright (c) 2009 The Regents of the University of California<br>
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

package org.shared.test.stat;

import static org.shared.test.Demo.demoDir;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shared.array.AbstractRealArray.RealMap;
import org.shared.array.RealArray;
import org.shared.stat.plot.DataStyle;
import org.shared.stat.plot.DataStyle.DataStyleType;
import org.shared.stat.plot.GnuplotContext;
import org.shared.stat.plot.Histogram;
import org.shared.stat.plot.Plot.AxisScaleType;
import org.shared.stat.plot.Plot.AxisType;
import org.shared.stat.plot.PrecisionRecall;
import org.shared.stat.plot.ProbabilityDistribution;
import org.shared.stat.plot.ProbabilityDistribution.Mode;
import org.shared.stat.plot.Roc;
import org.shared.stat.plot.Scatter;
import org.shared.test.Demo;
import org.shared.util.Arithmetic;
import org.shared.util.IoBase;

/**
 * A plotting demo.
 * 
 * @author Roy Liu
 */
public class PlotTest {

    /**
     * Creates the demo directory if it doesn't already exist in the file system.
     */
    @BeforeClass
    final public static void initClass() {
        Demo.createDemoDir();
    }

    /**
     * Default constructor.
     */
    public PlotTest() {
    }

    /**
     * Tests plotting operations.
     * 
     * @throws IOException
     *             when something goes awry.
     */
    @Test
    public void testPlot() throws IOException {

        final int size = 31;

        RealArray a2D = new RealArray(384, 2);
        RealArray a3D = new RealArray(size * size, 3);

        GnuplotContext gpc = new GnuplotContext();

        // 2D random scatter plot that highlights logarithmic scaling for the x-axis.

        Arithmetic.derandomize();

        gpc.addPlot(a2D.clone().uRnd(1.5).uAdd(0.5), a2D.clone().uRnd(1.5).uAdd(0.5)) //
                .setAxis(AxisType.X, "log(X)", 0.5, 2.0, AxisScaleType.LOG) //
                .setAxis(AxisType.Y, "Y", 0.5, 2.0, AxisScaleType.NORMAL) //
                .setDataStyles( //
                        new DataStyle(DataStyleType.POINTS) //
                                .setLineColor("blue") //
                                .setPointStyle("4"), //
                        new DataStyle(DataStyleType.POINTS) //
                                .setLineColor("gold") //
                                .setPointStyle("4")) //
                .setTitle("2D Scatter Plot") //
                .setPanelLocation(0, 0);

        // 2D random line graph.

        Arithmetic.derandomize();

        RealArray a2D1 = new RealArray(32, 2).uRnd(4.0).uAdd(-2.0);
        RealArray a2D2 = new RealArray(32, 2).uRnd(4.0).uAdd(-2.0);

        RealArray a2D1Sorted = a2D1.clone();
        RealArray a2D2Sorted = a2D2.clone();

        a2D1Sorted.iSort(0);
        a2D2Sorted.iSort(0);

        a2D1Sorted.map(a2D1, 0, 0, a2D1.size(0), 0, 0, 1);
        a2D2Sorted.map(a2D2, 0, 0, a2D2.size(0), 0, 0, 1);

        gpc.addPlot(new Scatter(a2D1, a2D2)) //
                .setAxisRange(AxisType.X, -2.0, 2.0) //
                .setAxisRange(AxisType.Y, -2.0, 2.0) //
                .setPropertyEnabled("grid", true) //
                .setDataStyles( //
                        new DataStyle(DataStyleType.LINESPOINTS) //
                                .setLineColor("blue") //
                                .setLineStyle("2") //
                                .setLineSize(4.0) //
                                .setPointStyle("2") //
                                .setPointSize(1.0), //
                        new DataStyle(DataStyleType.POINTS) //
                                .setLineColor("gold") //
                                .setLineStyle("1") //
                                .setLineSize(1.0) //
                                .setPointStyle("1") //
                                .setPointSize(2.0)) //
                .setTitle("Line Plot") //
                .setPanelLocation(0, 1);

        // 3D mesh plot.

        a3D.map(new RealMap() {

            @Override
            public double apply(double value, int[] logical) {

                int xcoord = (size >>> 1) - (logical[0] / size);
                int ycoord = (size >>> 1) - (logical[0] % size);

                switch (logical[1]) {

                case 0:
                    return xcoord;

                case 1:
                    return ycoord;

                case 2:
                    return (xcoord * ycoord) / Math.sqrt(Math.abs(xcoord * ycoord) + 1);

                default:
                    throw new IllegalArgumentException("Invalid logical index");
                }
            }
        });

        gpc.addPlot(new Scatter(a3D)) //
                .setAxisRange(AxisType.X, -(size >>> 1), (size >>> 1)) //
                .setAxisRange(AxisType.Y, -(size >>> 1), (size >>> 1)) //
                .setAxisRange(AxisType.Z, 1.5 * -(size >>> 1), 1.5 * (size >>> 1)) //
                .setPropertyEnabled("mesh", true) //
                .setPropertyEnabled("colormap", true) //
                .setPropertyEnabled("legend", false) //
                .setDataStyles(DataStyle.surface) //
                .setTitle("3D Mesh Plot") //
                .setViewport(60.0, 15.0) //
                .setPanelLocation(1, 0) //
                .setPanelSize(1, 2);

        int nValues = 1024;

        double[] values = new double[nValues];

        for (int i = 0; i < nValues; i++) {
            values[i] = Arithmetic.nextGaussian(2.0);
        }

        // Random histogram.

        gpc.addPlot(new Histogram(-6.0, 6.0, 24, values)) //
                .setPanelLocation(0, 2);

        // Random probability distribution.

        gpc.addPlot(new ProbabilityDistribution(Mode.PDF, -6.0, 6.0, 24, values)) //
                .setPanelLocation(1, 2);
        gpc.addPlot(new ProbabilityDistribution(Mode.CDF, -6.0, 6.0, 24, values)) //
                .setPanelLocation(2, 2);

        // Random ROC curve.

        Arithmetic.derandomize();

        boolean[] outcomes = new boolean[nValues];

        for (int i = 0; i < nValues; i++) {

            values[i] = Arithmetic.nextDouble(1.0);
            outcomes[i] = (Arithmetic.nextDouble(1.0) < (1.0 - values[i]) * 0.25) ? false : true;
        }

        Roc roc = new Roc(values, outcomes);

        gpc.addPlot(roc) //
                .setTitle(String.format("%s (auc = %.4f)", roc.getTitle(), roc.getAucs()[0])) //
                .setPanelLocation(2, 0);

        // Random precision-recall curve.

        PrecisionRecall pr = new PrecisionRecall(values, outcomes);

        gpc.addPlot(pr) //
                .setTitle(String.format("%s (auc = %.4f)", pr.getTitle(), pr.getAucs()[0])) //
                .setPanelLocation(2, 1);

        //

        File epsFile = new File(demoDir, "gnuplot.eps");
        File pngFile = new File(demoDir, "gnuplot.png");
        File svgFile = new File(demoDir, "gnuplot.svg");

        IoBase.delete(epsFile);
        IoBase.delete(pngFile);
        IoBase.delete(svgFile);

        gpc.setOutputFormat("eps").setFont("Helvetica", 8).toFile(epsFile);

        //

        URL url = Thread.currentThread().getContextClassLoader().getResource("font/Vera.ttf");

        File file = new File(demoDir, "Vera.ttf");

        IoBase.transfer(url.openStream(), file);

        IoBase.beginEnvironment().put("GDFONTPATH", file.getParent());

        try {

            gpc.setOutputFormat("png").setFont("Vera", 8).toFile(pngFile);

        } finally {

            IoBase.endEnvironment();
        }

        IoBase.delete(file);

        //

        gpc.setOutputFormat("svg").setFont("Helvetica", 8).toFile(svgFile);

        Assert.assertTrue(epsFile.exists());
        Assert.assertTrue(pngFile.exists());
        Assert.assertTrue(svgFile.exists());
    }
}

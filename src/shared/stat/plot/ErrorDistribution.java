/**
 * <p>
 * Copyright (C) 2007 The Regents of the University of California<br />
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

package shared.stat.plot;

import shared.array.RealArray;
import shared.stat.plot.Plot.AxisType;
import shared.util.Arrays;
import shared.util.Control;

/**
 * A base class for {@link PrecisionRecall} and {@link ROC}.
 * 
 * @author Roy Liu
 */
abstract public class ErrorDistribution implements Plottable {

    /**
     * The datasets.
     */
    final protected RealArray[] datasets;

    /**
     * The data titles.
     */
    final protected String[] dataTitles;

    /**
     * The {@link DataStyle}s.
     */
    final protected DataStyle[] dataStyles;

    /**
     * Default constructor.
     * 
     * @param confidencesArray
     *            the array of prediction confidences.
     * @param outcomesArray
     *            the array of prediction outcomes.
     */
    public ErrorDistribution(double[][] confidencesArray, boolean[][] outcomesArray) {

        int nClasses = Control.checkEquals(confidencesArray.length, outcomesArray.length);

        this.datasets = new RealArray[nClasses];

        for (int i = 0; i < nClasses; i++) {

            int nExamples = Control.checkEquals(confidencesArray[i].length, outcomesArray[i].length);

            double[] confidences = new double[nExamples];
            boolean[] outcomes = new boolean[nExamples];

            RealArray dataset = new RealArray(nExamples, 2);

            int[] perm = new RealArray(confidencesArray[i]).clone().iSort(0).reverse(0).values();

            for (int j = 0; j < nExamples; j++) {

                confidences[j] = confidencesArray[i][perm[j]];
                outcomes[j] = outcomesArray[i][perm[j]];
            }

            initDataset(dataset, outcomes);

            this.datasets[i] = dataset;
        }

        this.dataTitles = PlotBase.createDefaultTitles(nClasses);
        this.dataStyles = Arrays.newArray(DataStyle.class, nClasses, DataStyle.Lines);
    }

    /**
     * Gets the AUC's (area under curves).
     */
    public double[] getAUCs() {

        double[] aucs = new double[this.datasets.length];

        for (int i = 0, n = this.datasets.length; i < n; i++) {

            RealArray dataset = this.datasets[i];

            RealArray xarr = dataset.subarray(0, dataset.size(0), 0, 1);
            RealArray yarr = dataset.subarray(0, dataset.size(0), 1, 2);

            aucs[i] = xarr.eSub(xarr.shift(1, 0)).eMul(yarr.eAdd(yarr.shift(1, 0))).uMul(0.5) //
                    .subarray(1, dataset.size(0), 0, dataset.size(1)).rSum(0).get(0, 0);
        }

        return aucs;
    }

    public RealArray[] getDatasets() {
        return this.datasets;
    }

    public String[] getDataTitles() {
        return this.dataTitles;
    }

    public DataStyle[] getDataStyles() {
        return this.dataStyles;
    }

    public boolean getPropertyEnabled(String property) {
        return (property.equals("legend") || property.equals("grid"));
    }

    public String getAxisTitle(AxisType axisType) {

        switch (axisType) {

        case X:
            return getXAxisTitle();

        case Y:
            return getYAxisTitle();

        default:
            throw new IllegalArgumentException("Invalid axis type");
        }
    }

    public double[] getAxisRange(AxisType axisType) {

        switch (axisType) {

        case X:
            return getXAxisRange();

        case Y:
            return getYAxisRange();

        default:
            throw new IllegalArgumentException("Invalid axis type");
        }
    }

    /**
     * Initializes the given dataset.
     * 
     * @param dataset
     *            the dataset.
     * @param outcomes
     *            the prediction outcomes.
     */
    abstract protected void initDataset(RealArray dataset, boolean[] outcomes);

    /**
     * Gets the <tt>x</tt>-axis title.
     */
    abstract protected String getXAxisTitle();

    /**
     * Gets the <tt>y</tt>-axis title.
     */
    abstract protected String getYAxisTitle();

    /**
     * Gets the <tt>x</tt>-axis range.
     */
    abstract protected double[] getXAxisRange();

    /**
     * Gets the <tt>y</tt>-axis range.
     */
    abstract protected double[] getYAxisRange();
}

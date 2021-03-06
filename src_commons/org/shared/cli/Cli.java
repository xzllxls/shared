/**
 * <p>
 * Copyright (c) 2008 Roy Liu<br>
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

package org.shared.cli;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.shared.cli.CliOptions.CliOption;
import org.shared.util.Control;

/**
 * An interpreter for {@link CliOptions} annotations, from which <a href="http://commons.apache.org/cli/">Apache Commons
 * CLI</a> {@link Options} objects are derived.
 * 
 * @apiviz.has org.shared.cli.CliOptions - - - argument
 * @author Roy Liu
 */
public class Cli {

    /**
     * Creates a {@link CommandLine}.
     * 
     * @param clazz
     *            the class annotated with {@link CliOptions}.
     * @throws ParseException
     *             when the command-line arguments couldn't be parsed.
     */
    public static CommandLine createCommandLine(Class<?> clazz, String[] args) throws ParseException {
        return new PosixParser().parse(createOptions(clazz), args);
    }

    /**
     * Creates a help string.
     * 
     * @param clazz
     *            the class annotated with {@link CliOptions}.
     */
    public static String createHelp(Class<?> clazz) {

        StringWriter sw = new StringWriter();
        new HelpFormatter().printHelp(new PrintWriter(sw), //
                80, clazz.getName(), //
                "", createOptions(clazz), 4, 4, "", true);

        return sw.toString();
    }

    /**
     * Creates a set of {@link Option}s.
     * 
     * @param clazz
     *            the class annotated with {@link CliOptions}.
     */
    protected static Options createOptions(Class<?> clazz) {

        CliOptions cliOptions = clazz.getAnnotation(CliOptions.class);

        Control.checkTrue(cliOptions != null, //
                "Please provide command-line argument specifications");

        Options options = new Options();

        for (CliOption cliOption : cliOptions.options()) {

            int nArgs = cliOption.nArgs();

            if (!cliOption.hasOptionalArgs()) {

                if (nArgs >= 0) {

                    OptionBuilder.hasArgs(nArgs);

                } else {

                    Control.checkTrue(nArgs == -1, //
                            "Invalid number of arguments");

                    OptionBuilder.hasArgs();
                }

            } else {

                if (nArgs >= 0) {

                    OptionBuilder.hasOptionalArgs(nArgs);

                } else {

                    Control.checkTrue(nArgs == -1, //
                            "Invalid number of arguments");

                    OptionBuilder.hasOptionalArgs();
                }
            }

            OptionBuilder.isRequired(cliOption.required());

            String longOpt = cliOption.longOpt();
            OptionBuilder.withLongOpt(longOpt.length() > 0 ? longOpt : null);

            String argName = cliOption.argName();
            OptionBuilder.withArgName(argName.length() > 0 ? argName : null);

            String description = cliOption.description();
            OptionBuilder.withDescription(description.length() > 0 ? description : null);

            char valueSeparator = cliOption.valueSeparator();

            if (valueSeparator != '\u0000') {
                OptionBuilder.withValueSeparator(valueSeparator);
            }

            //

            String shortOpt = cliOption.opt();

            if (shortOpt.length() > 0) {

                options.addOption(OptionBuilder.create(shortOpt));

            } else {

                options.addOption(OptionBuilder.create());
            }
        }

        return options;
    }

    // Dummy constructor.
    Cli() {
    }
}

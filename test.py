#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright (c) 2010 Roy Liu
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
#   * Redistributions of source code must retain the above copyright notice,
#     this list of conditions and the following disclaimer.
#   * Redistributions in binary form must reproduce the above copyright notice,
#     this list of conditions and the following disclaimer in the documentation
#     and/or other materials provided with the distribution.
#   * Neither the name of the author nor the names of any contributors may be
#     used to endorse or promote products derived from this software without
#     specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

"""A script for running SST JUnit tests.
"""

__author__ = "Roy Liu <carsomyr@gmail.com>"

import platform
import subprocess
import sys

os_options = {"Darwin": ["-XX:+AggressiveHeap"],
              "Linux": ["-XX:+AggressiveHeap", "-XX:+AllowUserSignalHandlers", "-Xcheck:jni"],
              "Windows": ["-XX:+AggressiveHeap", "-Xcheck:jni"]}

def main():
    """The main method body.
    """

    subprocess.call(["make", "--", "jar"])

    java_cmd = ["java", "-ea"] + os_options[platform.system()] + ["-cp", "sst.jar"]

    subprocess.call(java_cmd + ["org.shared.test.All"])
    subprocess.call(java_cmd + ["org.shared.test.Demo"])
    subprocess.call(java_cmd + ["org.shared.test.AllNative"])
    subprocess.call(java_cmd + ["org.sharedx.test.AllX"])

#

if __name__ == "__main__":
    sys.exit(main())

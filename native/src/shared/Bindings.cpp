/*
 * Copyright (c) 2008 Roy Liu
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 *     disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *     following disclaimer in the documentation and/or other materials provided with the distribution.
 *   * Neither the name of the author nor the names of any contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include <Bindings.hpp>

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_randomize(JNIEnv *env, jobject thisObj) {
    srand((jlong) time(NULL));
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_derandomize(JNIEnv *env, jobject thisObj) {
    srand(0);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_map(JNIEnv *env, jobject thisObj, //
        jintArray bounds, //
        jobject srcV, jintArray srcD, jintArray srcS, //
        jobject dstV, jintArray dstD, jintArray dstS) {
    MappingOps::map(env, thisObj, //
            bounds, //
            srcV, srcD, srcS, //
            dstV, dstD, dstS);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_slice(JNIEnv *env, jobject thisObj, //
        jintArray slices, //
        jobject srcV, jintArray srcD, jintArray srcS, //
        jobject dstV, jintArray dstD, jintArray dstS) {
    MappingOps::slice(env, thisObj, //
            slices, //
            srcV, srcD, srcS, //
            dstV, dstD, dstS);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_rrOp(JNIEnv *env, jobject thisObj, jint type, //
        jdoubleArray srcV, jintArray srcD, jintArray srcS, //
        jdoubleArray dstV, jintArray dstD, jintArray dstS, //
        jintArray opDims) {
    DimensionOps::rrOp(env, thisObj, type, //
            srcV, srcD, srcS, //
            dstV, dstD, dstS, //
            opDims);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_riOp(JNIEnv *env, jobject thisObj, jint type, //
        jdoubleArray srcV, jintArray srcD, jintArray srcS, //
        jintArray dstV, //
        jint dim) {
    DimensionOps::riOp(env, thisObj, type, //
            srcV, srcD, srcS, //
            dstV, //
            dim);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_rdOp(JNIEnv *env, jobject thisObj, jint type, //
        jdoubleArray srcV, jintArray srcD, jintArray srcS, jdoubleArray dstV, //
        jintArray opDims) {
    DimensionOps::rdOp(env, thisObj, type, //
            srcV, srcD, srcS, dstV, //
            opDims);
}

JNIEXPORT jdouble JNICALL Java_org_shared_array_jni_NativeArrayKernel_raOp(JNIEnv *env, jobject thisObj, jint type, //
        jdoubleArray srcV) {
    return ElementOps::raOp(env, thisObj, type, srcV);
}

JNIEXPORT jdoubleArray JNICALL Java_org_shared_array_jni_NativeArrayKernel_caOp(JNIEnv *env, jobject thisObj, //
        jint type, jdoubleArray srcV) {
    return ElementOps::caOp(env, thisObj, type, srcV);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_ruOp(JNIEnv *env, jobject thisObj, jint type, //
        jdouble a, jdoubleArray srcV) {
    ElementOps::ruOp(env, thisObj, type, a, srcV);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_cuOp(JNIEnv *env, jobject thisObj, jint type, //
        jdouble aRe, jdouble aIm, jdoubleArray srcV) {
    ElementOps::cuOp(env, thisObj, type, aRe, aIm, srcV);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_iuOp(JNIEnv *env, jobject thisObj, jint type, //
        jint a, jintArray srcV) {
    ElementOps::iuOp(env, thisObj, type, a, srcV);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_eOp(JNIEnv *env, jobject thisObj, jint type, //
        jobject lhsV, jobject rhsV, jobject dstV, jboolean complex) {
    ElementOps::eOp(env, thisObj, type, lhsV, rhsV, dstV, complex);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_convert(JNIEnv *env, jobject thisObj, jint type, //
        jobject srcV, jboolean isSrcComplex, jobject dstV, jboolean isDstComplex) {
    ElementOps::convert(env, thisObj, type, srcV, isSrcComplex, dstV, isDstComplex);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_mul(JNIEnv *env, jobject thisObj, //
        jdoubleArray lhsV, jdoubleArray rhsV, jint lhsR, jint rhsC, jdoubleArray dstV, jboolean complex) {
    MatrixOps::mul(env, thisObj, lhsV, rhsV, lhsR, rhsC, dstV, complex);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_diag(JNIEnv *env, jobject thisObj, //
        jdoubleArray srcV, jdoubleArray dstV, jint size, jboolean complex) {
    MatrixOps::diag(env, thisObj, srcV, dstV, size, complex);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_svd(JNIEnv *env, jobject thisObj, //
        jdoubleArray srcV, jint srcStrideRow, jint srcStrideCol, //
        jdoubleArray uV, jdoubleArray sV, jdoubleArray vV, //
        jint nRows, jint nCols) {
    LinearAlgebraOps::svd(env, thisObj, srcV, srcStrideRow, srcStrideCol, uV, sV, vV, nRows, nCols);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_eigs(JNIEnv *env, jobject thisObj, //
        jdoubleArray srcV, jdoubleArray vecV, jdoubleArray valV, jint size) {
    LinearAlgebraOps::eigs(env, thisObj, srcV, vecV, valV, size);
}

JNIEXPORT void JNICALL Java_org_shared_array_jni_NativeArrayKernel_invert(JNIEnv *env, jobject thisObj, //
        jdoubleArray srcV, jdoubleArray dstV, jint size) {
    LinearAlgebraOps::invert(env, thisObj, srcV, dstV, size);
}

JNIEXPORT void JNICALL Java_org_shared_image_jni_NativeImageKernel_createIntegralImage(JNIEnv *env, jobject thisObj, //
        jdoubleArray srcV, jintArray srcD, jintArray srcS, //
        jdoubleArray dstV, jintArray dstD, jintArray dstS) {
    NativeImageKernel::createIntegralImage(env, thisObj, //
            srcV, srcD, srcS, //
            dstV, dstD, dstS);
}

JNIEXPORT void JNICALL Java_org_shared_image_jni_NativeImageKernel_createIntegralHistogram(JNIEnv *env, jobject thisObj, //
        jdoubleArray srcV, jintArray srcD, jintArray srcS, jintArray memV, //
        jdoubleArray dstV, jintArray dstD, jintArray dstS) {
    NativeImageKernel::createIntegralHistogram(env, thisObj, //
            srcV, srcD, srcS, memV, //
            dstV, dstD, dstS);
}

JNIEXPORT jintArray JNICALL Java_org_shared_array_jni_NativeArrayKernel_find(JNIEnv *env, jobject thisObj, //
        jintArray srcV, jintArray srcD, jintArray srcS, jintArray logical) {
    return IndexOps::find(env, thisObj, srcV, srcD, srcS, logical);
}

JNIEXPORT jobject JNICALL Java_org_shared_array_jni_NativeArrayKernel_insertSparse(JNIEnv *env, jobject thisObj, //
        jobject oldV, jintArray oldD, jintArray oldS, jintArray oldDo, jintArray oldI, //
        jobject newV, jintArray newLi) {
    return SparseOps::insert(env, thisObj, oldV, oldD, oldS, oldDo, oldI, newV, newLi);
}

JNIEXPORT jobject JNICALL Java_org_shared_array_jni_NativeArrayKernel_sliceSparse(JNIEnv *env, jobject thisObj, //
        jintArray slices, //
        jobject srcV, jintArray srcD, jintArray srcS, jintArray srcDo, //
        jintArray srcI, jintArray srcIo, jintArray srcIi, //
        jobject dstV, jintArray dstD, jintArray dstS, jintArray dstDo, //
        jintArray dstI, jintArray dstIo, jintArray dstIi) {
    return SparseOps::slice(env, thisObj, slices, //
            srcV, srcD, srcS, srcDo, //
            srcI, srcIo, srcIi, //
            dstV, dstD, dstS, dstDo, //
            dstI, dstIo, dstIi);
}

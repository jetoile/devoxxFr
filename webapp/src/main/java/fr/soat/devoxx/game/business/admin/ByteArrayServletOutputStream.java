/*
 * Copyright (c) 2011 Khanh Tuong Maudoux <kmx.petals@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.soat.devoxx.game.business.admin;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * User: khanh
 * Date: 23/01/12
 * Time: 23:42
 */
public class ByteArrayServletOutputStream extends ServletOutputStream {

    protected byte buf[];

    protected int count;

    public ByteArrayServletOutputStream() {
        this(32);
    }

    public ByteArrayServletOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: " + size);
        }
        buf = new byte[size];
    }

    public synchronized byte toByteArray()[] {
        return copyOf(buf, count);
    }

    public synchronized void reset() {
        count = 0;
    }

    public synchronized int size() {
        return count;
    }

    public void enlarge(int size) {
        if (size > buf.length) {
            buf = copyOf(buf, Math.max(buf.length << 1, size));
        }
    }

    @Override
    public synchronized void write(int b) throws IOException {
        int newcount = count + 1;
        enlarge(newcount);
        buf[count] = (byte) b;
        count = newcount;
    }

    /**
     * copy from: jdk1.6, java.util.Arrays.copyOf(byte[] original, int
     * newLength)
     */
    private static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length,
                newLength));
        return copy;
    }
}
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * User: khanh
 * Date: 23/01/12
 * Time: 23:41
 */
public class HttpServletResponseContentWrapper extends
        HttpServletResponseWrapper {

    protected ByteArrayServletOutputStream buffer;
    protected PrintWriter bufferWriter;
    protected boolean committed = false;

    public HttpServletResponseContentWrapper(HttpServletResponse response) {
        super(response);
        buffer = new ByteArrayServletOutputStream();
    }

    public void flushWrapper() throws IOException {
        if (bufferWriter != null)
            bufferWriter.close();
        if (buffer != null)
            buffer.close();
        byte[] content = wrap(buffer.toByteArray());
        getResponse().setContentLength(content.length);
        getResponse().getOutputStream().write(content);
        getResponse().flushBuffer();
        committed = true;
    }

    public byte[] wrap(byte[] content) throws IOException {
        return content;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return buffer;
    }

    /**
     * The default behavior of this method is to return getWriter() on the
     * wrapped response object.
     */

    @Override
    public PrintWriter getWriter() throws IOException {
        if (bufferWriter == null) {
            bufferWriter = new PrintWriter(new OutputStreamWriter(buffer, this
                    .getCharacterEncoding()));
        }
        return bufferWriter;
    }

    @Override
    public void setBufferSize(int size) {
        buffer.enlarge(size);
    }

    @Override
    public int getBufferSize() {
        return buffer.size();
    }

    @Override
    public void flushBuffer() throws IOException {
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    @Override
    public void reset() {
        getResponse().reset();
        buffer.reset();
    }

    @Override
    public void resetBuffer() {
        getResponse().resetBuffer();
        buffer.reset();
    }

}

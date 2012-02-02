/*
 * Copyright (c) 2012 Aurélien VIALE
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
package fr.soat.devoxx.game.webmvc.delegate;


public interface IRest {    
    /**
     * Invoke the GET method.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException.
     */
    <T> T get(Class<T> c) throws HttpRestException;        
    
    /**
     * Invoke the PUT method with no request entity or response.
     * <p>
     * If the status code is less than 300 and a representation is present
     * then that representation is ignored.
     * 
     * @throws HttpRestException.
     */
    void put() throws HttpRestException;
    
    /**
     * Invoke the PUT method with a request entity but no response.
     * <p>
     * If the status code is less than 300 and a representation is present
     * then that representation is ignored.
     * 
     * @param requestEntity the request entity.
     * @throws HttpRestException.
     */
    void put(Object requestEntity) throws HttpRestException;

    /**
     * Invoke the PUT method with no request entity that returns a response.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException.
     */
    <T> T put(Class<T> c) throws HttpRestException;
    
    /**
     * Invoke the PUT method with a request entity that returns a response.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @param requestEntity the request entity.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException.
     */
    <T> T put(Class<T> c, Object requestEntity) 
            throws HttpRestException;
    
    /**
     * Invoke the POST method with no request entity or response.
     * <p>
     * If the status code is less than 300 and a representation is present
     * then that representation is ignored.
     * 
     * @throws HttpRestException.
     */
    void post() throws HttpRestException;
    
    /**
     * Invoke the POST method with a request entity but no response.
     * <p>
     * If the status code is less than 300 and a representation is present
     * then that representation is ignored.
     * 
     * @param requestEntity the request entity.
     * @throws HttpRestException.
     */
    void post(Object requestEntity) throws HttpRestException;
    
    /**
     * Invoke the POST method with no request entity that returns a response.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException.
     */
    <T> T post(Class<T> c) throws HttpRestException;
    
    /**
     * Invoke the POST method with a request entity that returns a response.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @param requestEntity the request entity.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException.
     */
    <T> T post(Class<T> c, Object requestEntity) 
            throws HttpRestException;
    
    /**
     * Invoke the DELETE method with no request entity or response.
     * <p>
     * If the status code is less than 300 and a representation is present
     * then that representation is ignored.
     * 
     * @throws HttpRestException.
     */
    void delete() throws HttpRestException;
    
    /**
     * Invoke the DELETE method with a request entity but no response.
     * <p>
     * If the status code is less than 300 and a representation is present
     * then that representation is ignored.
     * 
     * @param requestEntity the request entity.
     * @throws HttpRestException.
     */
    void delete(Object requestEntity) throws HttpRestException;
    
    /**
     * Invoke the DELETE method with no request entity that returns a response.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException.
     */
    <T> T delete(Class<T> c) throws HttpRestException;
    
    /**
     * Invoke the DELETE method with a request entity that returns a response.
     * 
     * @param <T> the type of the response.
     * @param c the type of the returned response.
     * @param requestEntity the request entity.
     * @return an instance of type <code>c</code>.
     * @throws HttpRestException
     */
    <T> T delete(Class<T> c, Object requestEntity) 
            throws HttpRestException;
}

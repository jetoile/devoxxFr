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

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RequesterDelegate implements IRest {
	private final String BASE_URI = "http://devoxxfrjee.aure77.cloudbees.net/services";

	private String servicePath;
	private WebResource service;
	private String type = MediaType.APPLICATION_JSON;

	public RequesterDelegate() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		this.service = client.resource(BASE_URI);
	}
	
	public RequesterDelegate(String servicePath) {
		this();
		this.servicePath = servicePath;		
	}
	
	public RequesterDelegate(String servicePath, String type) {
		this(servicePath);
		this.type = type;		
	}

	/**
	 * @return the servicePath
	 */
	public String getServicePath() {
		return servicePath;
	}

	/**
	 * @param servicePath the servicePath to set
	 */
	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public <T> T get(Class<T> c) throws HttpRestException {
		try {
			 return service.path(servicePath).type(type).get(c);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public void put() throws HttpRestException {
		try {
			service.path(servicePath).type(type).put();
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public void put(Object requestEntity) throws HttpRestException {
		try {
			service.path(servicePath).type(type).put(requestEntity);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}		
	}

	@Override
	public <T> T put(Class<T> c) throws HttpRestException {
		try {
			return service.path(servicePath).type(type).put(c);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public <T> T put(Class<T> c, Object requestEntity) throws HttpRestException {
		try {
			return service.path(servicePath).type(type).put(c, requestEntity);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public void post() throws HttpRestException {
		try {
			service.path(servicePath).type(type).post();
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}		
	}

	@Override
	public void post(Object requestEntity) throws HttpRestException {
		try {
			service.path(servicePath).type(type).post(requestEntity);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public <T> T post(Class<T> c) throws HttpRestException {
		try {
			return service.path(servicePath).type(type).post(c);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public <T> T post(Class<T> c, Object requestEntity)	throws HttpRestException {
		try {
			return service.path(servicePath).type(type).post(c, requestEntity);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public void delete() throws HttpRestException {
		try {
			service.path(servicePath).type(type).delete();
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}		
	}

	@Override
	public void delete(Object requestEntity) throws HttpRestException {
		try {
			service.path(servicePath).type(type).delete(requestEntity);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}		
	}

	@Override
	public <T> T delete(Class<T> c) throws HttpRestException {
		try {
			return service.path(servicePath).type(type).delete(c);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}

	@Override
	public <T> T delete(Class<T> c, Object requestEntity) throws HttpRestException {
		try {
			return service.path(servicePath).type(type).delete(c, requestEntity);
		} catch (UniformInterfaceException e) {
			throw new HttpRestException(e);
		}
	}
}

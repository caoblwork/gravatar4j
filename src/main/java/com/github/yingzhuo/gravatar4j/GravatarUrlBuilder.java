/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.gravatar4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A gravatar is a dynamic image resource that is requested from the
 * gravatar.com server. This class calculates the gravatar url and fetches
 * gravatar images. See http://en.gravatar.com/site/implement/url .
 * 
 * @author YING Zhuo
 *
 */
public class GravatarUrlBuilder {
	
    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

	private final static int DEFAULT_SIZE = 80;
	private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
	
	private int size = DEFAULT_SIZE;
	private String email = null;
	
    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/
	
	/**
	 * Create a builder
	 */
	public GravatarUrlBuilder() {
		super();
	}
	
	/**
	 * Create a builder and init email
	 */
	public GravatarUrlBuilder(String email) {
		this();
		email(email);
	}

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
	
	/**
	 * Set email.
	 */
	public GravatarUrlBuilder email(String email) {
		_validate(email);
		this.email = email.trim();
		return this;
	}

	/**
	 * Set size of image. 
	 */
	public GravatarUrlBuilder size(int sizeInPixels) {
		_validate(size);
		this.size = sizeInPixels;
		return this;
	}

	/**
	 * build the url string
	 */
	public String build() {
		_validate(email);
		return GRAVATAR_URL + _md5(email.toLowerCase()) + ".jpg?s=" + size;
	}
	
    /*--------------------------------------------
    |            H E L P - M E T H O D S        |
    ============================================*/

	private void _validate(String email) {
		if (email == null) {
			throw new IllegalArgumentException("'email' should NOT be null.");
		}
		int length = email.trim().length();
		if (length == 0) {
			throw new IllegalArgumentException("'email' should NOT be blank.");
		}
	}

	private void _validate(int sizeInPixels) {
		boolean b = sizeInPixels >= 1 && sizeInPixels <= 512;
		if (b == false) {
			throw new IllegalArgumentException(
					"sizeInPixels needs to be between 1 and 512");
		}
	}

	private String _md5(String string) {
		MessageDigest md = null;
		try {md = MessageDigest.getInstance("MD5");} catch (NoSuchAlgorithmException e) {}
		md.update(string.getBytes());
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

}

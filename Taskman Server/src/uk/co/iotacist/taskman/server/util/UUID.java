/*
 * Copyright 2020 Iotacist <iotacist@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.iotacist.taskman.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import uk.co.iotacist.logman.Logger;

/**
 * The <code>UUID</code> class is used generate universally unique identifier
 * (UUID) numbers.
 * 
 * A guaranteed UUID contains a reference to a timestamp (a record of the
 * precise time of a transaction), and a randomly generated component.
 * 
 * @since 1.0
 * @version 1.0
 * @author Iotacist <iotacist@gmail.com>
 */
public final class UUID {
	/**
	 * Return the MessageDigest implementation to be used when creating session
	 * identifiers.
	 */
	private MessageDigest digest = null;

	/**
	 * A random number generator to use when generating session identifiers.
	 */
	private Random random = new SecureRandom();

	/**
	 * The message digest algorithm to be used when generating session identifiers.
	 * This must be an algorithm supported by the
	 * <code>java.security.MessageDigest</code> class on your platform.
	 */
	private String algorithm = DEFAULT_ALGORITHM;

	/**
	 * Class logger.
	 */
	private Logger log;

	/**
	 * The default message digest algorithm to use if we cannot use the requested
	 * one.
	 */
	protected static final String DEFAULT_ALGORITHM = "MD5";

	/**
	 * Creates a <code>UUID</code> instance.
	 */
	public UUID() {
		// Start with the current system time as a seed
		long seed = System.currentTimeMillis();
		// Also throw in the system identifier for 'this' from toString
		char[] entropy = toString().toCharArray();
		for (int i = 0; i < entropy.length; i++) {
			long update = ((byte) entropy[i]) << ((i % 8) * 8);
			seed ^= update;
		}
		random.setSeed(seed);
		log = Logger.getLogger(UUID.class);
	}

	/**
	 * Generate and return a new session identifier.
	 * 
	 * @param length The number of bytes to generate
	 * 
	 * @return A new page id string
	 */
	public synchronized String generateId(int length) {
		// Create byte buffer with fixed length.
		byte[] buffer = new byte[length];
		// Render the result as a String of hexadecimal digits
		StringBuffer reply = new StringBuffer();
		int resultLenBytes = 0;
		while (resultLenBytes < length) {
			random.nextBytes(buffer);
			buffer = getDigest().digest(buffer);
			for (int j = 0; j < buffer.length && resultLenBytes < length; j++) {
				byte b1 = (byte) ((buffer[j] & 0xf0) >> 4);
				if (b1 < 10) {
					reply.append((char) ('0' + b1));
				} else {
					reply.append((char) ('A' + (b1 - 10)));
				}
				byte b2 = (byte) (buffer[j] & 0x0f);
				if (b2 < 10) {
					reply.append((char) ('0' + b2));
				} else {
					reply.append((char) ('A' + (b2 - 10)));
				}
				resultLenBytes++;
			}
		}
		return reply.toString();
	}

	/**
	 * @return the algorithm
	 */
	public synchronized String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public synchronized void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
		digest = null;
	}

	/**
	 * Return the MessageDigest object to be used for calculating session
	 * identifiers. If none has been created yet, initialize one the first time this
	 * method is called.
	 * 
	 * @return The hashing algorithm
	 */
	private MessageDigest getDigest() {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException ex0) {
				try {
					log.severe("Unable to get instance of [" + algorithm + "], reverting back to [" + DEFAULT_ALGORITHM
							+ "].", ex0);
					digest = MessageDigest.getInstance(DEFAULT_ALGORITHM);
				} catch (NoSuchAlgorithmException ex1) {
					digest = null;
					log.severe("Unable to get instance of [" + DEFAULT_ALGORITHM + "], no algorithms for UUID.", ex1);
					throw new IllegalStateException("No algorithms for IdGenerator");
				}
			}
			log.debug("Using MessageDigest: " + digest.getAlgorithm());
		}
		return digest;
	}
}
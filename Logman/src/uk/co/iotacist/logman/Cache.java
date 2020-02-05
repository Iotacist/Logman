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
package uk.co.iotacist.logman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import uk.co.iotacist.logman.appender.Appender;

/**
 * The <code>LoggerCache</code> class hold all of the registered loggers, the
 * logger cache list is thread safe and can be accessed through the methods
 * within this class.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public final class Cache {
	/*
	 * The logger cache list.
	 */
	private static final List<Logger> LOGGER_CACHE = Collections.synchronizedList(new ArrayList<Logger>());

	/*
	 * Instances of this class may not be made.
	 */
	private Cache() {
	}

	/**
	 * Adds a logger to the cache list, if the logger is nulled or already in the
	 * list it will not be added.
	 * 
	 * @param logger - Logger instance.
	 * 
	 * @return <code>true</code> if the logger was added to the cache list,
	 *         <code>false</code> otherwise.
	 */
	protected static boolean addLogger(Logger logger) {
		if (logger != null) {
			if (!containsLogger(logger.getName())) {
				synchronized(LOGGER_CACHE) {
					return LOGGER_CACHE.add(logger);
				}
			}
		}
		return false;
	}

	/**
	 * Removes a logger from the cache list, if the logger is nulled or not in the
	 * list it will not be removed.
	 * 
	 * @param logger - Logger instance.
	 * 
	 * @return <code>true</code> if the logger was removed from the cache list,
	 *         <code>false</code> otherwise.
	 */
	protected static boolean removeLogger(Logger logger) {
		if (logger != null) {
			if (containsLogger(logger.getName())) {
				synchronized(LOGGER_CACHE) {
					return LOGGER_CACHE.remove(logger);
				}
			}
		}
		return false;
	}

	/**
	 * Performs a search of the logger cache to see if a logger instance with the
	 * same name exists.
	 * 
	 * @param name - Logger name.
	 * 
	 * @return A logger instance with the same name or <code>null</code> if a logger
	 *         instance was not found.
	 */
	protected static Logger getLogger(String name) {
		synchronized (LOGGER_CACHE) {
			for (Logger logger : LOGGER_CACHE) {
				if (logger.getName().equals(name)) {
					return logger;
				}
			}
		}
		return null;
	}

	/**
	 * Performs a search of the logger cache to see if the specified logger instance
	 * is within the cache list.
	 * 
	 * @param name - Logger instance.
	 * 
	 * @return <code>true</code> if the specified logger was found within the cache
	 *         list, <code>false</code> otherwise.
	 */
	protected static boolean containsLogger(String name) {
		synchronized (LOGGER_CACHE) {
			for (Logger log : LOGGER_CACHE) {
				if (log.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
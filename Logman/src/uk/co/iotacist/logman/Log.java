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

/**
 * The <code>Log</code> class holds information about a logger event, this
 * information gets passed through the internal <code>Logman</code> event
 * system.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class Log {
	/*
	 * The logger.
	 */
	private final Logger LOGGER;

	/*
	 * The level.
	 */
	private final Level LEVEL;

	/*
	 * The message.
	 */
	private final Object MESSAGE;

	/*
	 * The formatted message.
	 */
	private Object formattedMessage;

	/*
	 * The error.
	 */
	private final Throwable THROWN;

	/**
	 * Creates a new Log instance with the specified variables.
	 * 
	 * @param logger  - Event source.
	 * 
	 * @param level   - Event level.
	 * 
	 * @param message - Event message.
	 * 
	 * @param thrown  - Event error.
	 */
	protected Log(Logger logger, Level level, Object message, Throwable thrown) {
		LOGGER = logger;
		LEVEL = level;
		MESSAGE = message;
		THROWN = thrown;
	}

	/**
	 * Returns the logger that created this Log instance.
	 * 
	 * @return Event logger.
	 */
	public final Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Returns the level of this Log instance.
	 * 
	 * @return Log level.
	 */
	public final Level getLevel() {
		return LEVEL;
	}

	/**
	 * Returns the message of this log instance.
	 * 
	 * @return Log message.
	 */
	public final Object getMessage() {
		return MESSAGE;
	}

	/**
	 * Returns the formatted message of this log instance.
	 * 
	 * @return Log formatted message.
	 */
	public Object getFormattedMessage() {
		return formattedMessage;
	}

	/**
	 * Sets the formatted message for this Log instance, the new message cannot be
	 * null.
	 * 
	 * @param message - Formatted message.
	 * 
	 * @return <code>true</code> if the formatted message was set,
	 *         <code>false</code> if it was null.
	 */
	protected boolean setFormattedMessage(Object message) {
		if (message != null) {
			formattedMessage = message;
			return true;
		}
		return false;
	}

	/**
	 * Returns the error of this log instance.
	 * 
	 * @return Log error.
	 */
	public Throwable getThrown() {
		return THROWN;
	}
}
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
 * The <code>Level</code> enum defines a set of standard logging levels that can
 * be used to control logging output.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public enum Level {
	/**
	 * The <code>OFF</code> level has the highest rank and is intended to turn of
	 * all logging.
	 */
	OFF("OFF", 5000),

	/**
	 * The <code>SEVERE</code> level designates very severe error events that will
	 * presumably lead the application to abort.
	 */
	SEVERE("SEVERE", 4000),

	/**
	 * The <code>WARN</code> level designates potentially harmful situations.
	 */
	WARN("WARN", 3000),

	/**
	 * The <code>INFO</code> level designates informational messages that highlight
	 * the progress of the application at coarse-grained level.
	 */
	INFO("INFO", 2000),

	/**
	 * The <code>DEBUG</code> Level designates fine-grained informational events
	 * that are most useful to debug an application.
	 */
	DEBUG("DEBUG", 1000),

	/**
	 * The <code>ALL</code> has the lowest possible rank and is intended to turn on
	 * all logging.
	 */
	ALL("ALL", 0);

	/*
	 * The level name.
	 */
	private final String LEVEL_NAME;

	/*
	 * The level number.
	 */
	private final int LEVEL_NUMBER;

	/**
	 * Constructs a new <code>Level</code> instance with the specified name and
	 * number.
	 * 
	 * @param name   - Level name.
	 * 
	 * @param number - Level number.
	 */
	private Level(String name, int number) {
		this.LEVEL_NAME = name;
		this.LEVEL_NUMBER = number;
	}

	/**
	 * Returns the name of this <code>Level</code> instance.
	 * 
	 * @return Level name.
	 */
	public final String getName() {
		return this.LEVEL_NAME;
	}

	/**
	 * Returns the number of this <code>Level</code> instance.
	 * 
	 * @return Level number.
	 */
	public final int getNumber() {
		return this.LEVEL_NUMBER;
	}
}
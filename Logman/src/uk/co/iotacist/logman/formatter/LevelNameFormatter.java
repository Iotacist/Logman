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
package uk.co.iotacist.logman.formatter;

import uk.co.iotacist.logman.Log;

/**
 * The <code>LevelNameFormatter</code> class adds information about the specific
 * <code>LoggerEvent</code>, the <code>LoggerEvent</code> level and the
 * <code>Logger</code> name are appended to the <code>LoggerEvent</code>
 * message.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class LevelNameFormatter implements Formatter {
	/**
	 * Open bracket character.
	 */
	private final char OPEN_BRACKET = '[';
	
	/**
	 * Close bracket character.
	 */
	private final char CLOSE_BRACKET = ']';
	
	/**
	 * Space bar character.
	 */
	private final char SPACE_BAR = ' ';
	
	/**
	 * Constructs a new <code>LevelNameFormatter</code> instance.
	 */
	public LevelNameFormatter() {
	}

	/**
	 * Formats the <code>LoggerEvent</code> message and appends the
	 * <code>LoggerEvent</code> level and the <code>Logger</code> name to the
	 * message.
	 * 
	 * @param event
	 *            - LoggerEvent instance.
	 * 
	 * @return Formatted message.
	 */
	@Override
	public String format(Log log) {
		String name = OPEN_BRACKET + log.getLogger().getName() + CLOSE_BRACKET;
		String level = OPEN_BRACKET + log.getLevel().getName() + CLOSE_BRACKET;
		String message = SPACE_BAR + log.getMessage().toString();
		return name + level + message;
	}

}
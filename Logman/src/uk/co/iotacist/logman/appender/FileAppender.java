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
package uk.co.iotacist.logman.appender;

import java.io.File;

import uk.co.iotacist.logman.Log;

/**
 * The <code>FileAppender</code> class takes <code>LoggerEvent</code> messages
 * from the logger and exports them to the specified file.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class FileAppender implements Appender {
	/**
	 * Constructs a new <code>FileHandler</code> instance.
	 */
	public FileAppender(File output) {
	}

	/**
	 * Exports the specified <code>Log</code> to the specified file.
	 * 
	 * @param log
	 *            - Log instance.
	 */
	@Override
	public void append(Log log) {

	}
}
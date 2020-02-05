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

import uk.co.iotacist.logman.Level;
import uk.co.iotacist.logman.Log;

/**
 * The <code>StdOutAppender</code> class takes <code>Log</code> messages from
 * the logger and exports them to the System.out and System.err print streams.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class StdOutAppender implements Appender {
	/**
	 * Constructs a new <code>StdOutAppender</code> instance.
	 */
	public StdOutAppender() {
	}

	/**
	 * Exports the specified <code>Log</code> to the system streams, if the level of
	 * this <code>Log</code> is either <code>WARN</code> or <code>SEVERE</code> the
	 * formatted message will be printed to the <code>System.err</code> output
	 * stream, otherwise the formatted message will be printed to the
	 * <code>System.out</code> output stream.
	 * 
	 * @param event - Log instance.
	 */
	@Override
	public void append(Log log) {
		if (log.getLevel() == Level.WARN || log.getLevel() == Level.SEVERE) {
			System.err.println(log.getFormattedMessage());
		} else {
			System.out.println(log.getFormattedMessage());
		}
	}
}
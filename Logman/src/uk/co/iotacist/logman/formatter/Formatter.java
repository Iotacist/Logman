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
 * The <code>Formatter</code> class formats the <code>LoggerEvent</code> message
 * to a more organised and readable format, additional information may be added
 * or removed from the <code>LoggerEvent</code> at this point.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public interface Formatter {
	/**
	 * Formats the specified log and adds/removes any information from the
	 * original <code>Log</code> message.
	 * 
	 * @param log
	 *            - Log instance.
	 * 
	 * @return Formatted message.
	 */
	public String format(Log log);
}
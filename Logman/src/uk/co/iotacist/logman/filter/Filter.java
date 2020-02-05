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
package uk.co.iotacist.logman.filter;

import uk.co.iotacist.logman.Log;

/**
 * The <code>Filter</code> class performs a check to see if the Log should
 * proceed any further through the system.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public interface Filter {
	/**
	 * Performs a check on the <code>Log</code> parameter to see if it should be
	 * logged by the system.
	 * 
	 * @param event - LoggerEvent instance.
	 * 
	 * @return <code>true</code> if the event should be logged, <code>false</code>
	 *         otherwise.
	 */
	public boolean filter(Log log);
}
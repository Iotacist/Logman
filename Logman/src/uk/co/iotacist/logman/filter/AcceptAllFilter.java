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
 * The <code>AcceptAllFilter</code> class does not perform any filtering, the
 * <code>filter</code> method always returns <code>true</code>.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class AcceptAllFilter implements Filter {
	/**
	 * Constructs a new <code>AcceptAllFilter</code> instance.
	 */
	public AcceptAllFilter() {
	}

	/**
	 * Performs no filtering on the <code>Log</code> parameter, the method always
	 * returns true.
	 * 
	 * @param event - LoggerEvent instance.
	 * 
	 * @return <code>true</code>.
	 */
	@Override
	public boolean filter(Log log) {
		return true;
	}
}
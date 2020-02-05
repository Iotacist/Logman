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

import uk.co.iotacist.logman.Level;
import uk.co.iotacist.logman.Log;

/**
 * The <code>LevelRangeFilter</code> class performs a check to see if the level
 * of the <code>Log</code> is between or equal to a set minimum and maximum
 * level.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class LevelRangeFilter implements Filter {
	/*
	 * Minimum level.
	 */
	private Level minimumLevel;

	/*
	 * Maximum level.
	 */
	private Level maximumLevel;

	/**
	 * Constructs a new <code>LevelRangeFilter</code> instance with the specified
	 * minimum and maximum level.
	 * 
	 * @param min - Minimum level.
	 * 
	 * @param max - Maximum level.
	 */
	public LevelRangeFilter(Level min, Level max) {
		minimumLevel = min;
		maximumLevel = max;
	}

	/**
	 * Returns the current minimum level.
	 * 
	 * @return Minimum level.
	 */
	public Level getMinimumLevel() {
		return minimumLevel;
	}

	/**
	 * Sets the minimum level.
	 * 
	 * @param min - Minimum level.
	 */
	public void setMinimumLevel(Level min) {
		minimumLevel = min;
	}

	/**
	 * Returns the current maximum level.
	 * 
	 * @return Maximum level.
	 */
	public Level setMaximumLevel() {
		return maximumLevel;
	}

	/**
	 * Sets the maximum level.
	 * 
	 * @param max - Maximum level.
	 */
	public void setMaximumLevel(Level max) {
		maximumLevel = max;
	}

	/**
	 * Performs a check to see if the level of the <code>Log</code> is between or
	 * equal to the set minimum and maximum level.
	 * 
	 * @param event - Log instance.
	 * 
	 * @return <code>true</code> if the <code>Log</code> level is between or equal
	 *         to the set minimum and maximum level, <code>false</code> otherwise.
	 */
	@Override
	public boolean filter(Log event) {
		int eventLevel = event.getLevel().getNumber();
		int minLevel = minimumLevel.getNumber();
		int maxLevel = maximumLevel.getNumber();
		/*
		 * Check error level is between min and max.
		 */
		if (eventLevel >= minLevel && eventLevel <= maxLevel) {
			return true;
		}
		return false;
	}
}
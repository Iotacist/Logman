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
import uk.co.iotacist.logman.appender.StdOutAppender;
import uk.co.iotacist.logman.filter.AcceptAllFilter;
import uk.co.iotacist.logman.filter.Filter;
import uk.co.iotacist.logman.formatter.Formatter;
import uk.co.iotacist.logman.formatter.LevelNameFormatter;

/**
 * The <code>Logger</code> class is used to log messages for a specific system
 * or application component.
 * 
 * Each Logger keeps track of a "parent" Logger, which is its nearest existing
 * ancestor in the Logger namespace.
 * 
 * @since 1.0
 * 
 * @version 1.1
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class Logger {
	/*
	 * The logger name.
	 */
	private String name;

	/*
	 * The logger parent.
	 */
	private Logger parent;

	/*
	 * The logger level.
	 */
	private Level level;

	/*
	 * The logger parent level flag.
	 */
	private boolean useParentLevel;

	/*
	 * The logger filter.
	 */
	private Filter filter;

	/*
	 * The logger parent filter flag.
	 */
	private boolean useParentFilter;

	/*
	 * The logger formatter.
	 */
	private Formatter formatter;

	/*
	 * The logger parent formatter flag.
	 */
	private boolean useParentFormatter;

	/**
	 * The logger appenders.
	 */
	private List<Appender> appenders;

	/*
	 * The logger parent appender flag.
	 */
	private boolean useParentAppenders;
	
	/**
	 * The global logger.
	 */
	public static final Logger GLOBAL = Logger.getLogger(Logger.class);

	/**
	 * Constructs a new <code>Logger</code> instance with the specified name.
	 * 
	 * @param name - Logger name.
	 */
	private Logger(String name) {
		this.name = name;
		this.level = Level.ALL;
		this.filter = new AcceptAllFilter();
		this.formatter = new LevelNameFormatter();
		this.appenders = new ArrayList<Appender>();
	}

	/**
	 * Logs a message object with the <code>DEBUG</code> level.
	 * 
	 * @param message - Message object.
	 */
	public void debug(Object message) {
		log(Level.DEBUG, message);
	}

	/**
	 * Logs a message object and a stack trace with the <code>DEBUG</code> level.
	 * 
	 * @param message   - Message object.
	 * 
	 * @param throwable - Throwable stack trace.
	 */
	public void debug(Object message, Throwable throwable) {
		log(Level.DEBUG, message, throwable);
	}

	/**
	 * Logs a message object with the <code>INFO</code> level.
	 * 
	 * @param message - Message object.
	 */
	public void info(Object message) {
		log(Level.INFO, message);
	}

	/**
	 * Logs a message object and a stack trace with the <code>INFO</code> level.
	 * 
	 * @param message   - Message object.
	 * 
	 * @param throwable - Throwable stack trace.
	 */
	public void info(Object message, Throwable throwable) {
		log(Level.INFO, message, throwable);
	}

	/**
	 * Logs a message object with the <code>WARN</code> level.
	 * 
	 * @param message - Message object.
	 */
	public void warn(Object message) {
		log(Level.WARN, message);
	}

	/**
	 * Logs a message object and a stack trace with the <code>WARN</code> level.
	 * 
	 * @param message   - Message object.
	 * 
	 * @param throwable - Throwable stack trace.
	 */
	public void warn(Object message, Throwable throwable) {
		log(Level.WARN, message, throwable);
	}

	/**
	 * Logs a message object with the <code>SEVERE</code> level.
	 * 
	 * @param message - Message object.
	 */
	public void severe(Object message) {
		log(Level.SEVERE, message);
	}

	/**
	 * Logs a message object and a stack trace with the <code>SEVERE</code> level.
	 * 
	 * @param message   - Message object.
	 * 
	 * @param throwable - Throwable stack trace.
	 */
	public void severe(Object message, Throwable throwable) {
		log(Level.SEVERE, message, throwable);
	}

	/**
	 * Logs a message object with the specified level.
	 * 
	 * @param level   - Log level.
	 * 
	 * @param message - Message object.
	 */
	public void log(Level level, Object message) {
		log(level, message, null);
	}

	/**
	 * Logs a message object and a stack trace with the specified level.
	 * 
	 * @param level     - Log level.
	 * 
	 * @param message   - Message object.
	 * 
	 * @param throwable - Throwable stack trace.
	 */
	public void log(Level level, Object message, Throwable throwable) {
		Level loggerLevel = useParentLevel ? parent.getLevel() : getLevel();

		if (level.getNumber() >= loggerLevel.getNumber()) {
			log(new Log(this, level, message, throwable));
		}
	}

	/**
	 * Logs a <code>LoggerEvent</code> object.
	 * 
	 * @param event - Log object.
	 */
	private void log(Log event) {
		/*
		 * Pass log to filter, only progress if the filter returns true.
		 */
		if (getFilter().filter(event)) {
			/*
			 * Pass log to formatter and set the formatted message.
			 */
			event.setFormattedMessage(getFormatter().format(event));
			/*
			 * Cycle through all set appenders and pass log file.
			 */
			for (Appender appender : getAppenders()) {
				appender.append(event);
			}
		}
	}

	/**
	 * Returns the current logger name.
	 * 
	 * @return Logger name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the current logger parent.
	 * 
	 * @return Logger parent.
	 */
	public Logger getParent() {
		return parent;
	}

	/**
	 * Sets the logger parent.
	 * 
	 * @param parent - Logger parent.
	 */
	public void setParent(Logger parent) {
		this.parent = parent;
	}

	/**
	 * Returns the current logger level.
	 * 
	 * @return Logger level.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Sets the logger level.
	 * 
	 * @param level - Logger level.
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Returns the use parent level flag.
	 * 
	 * @return Parent level flag.
	 */
	public boolean getUseParentLevel() {
		return useParentLevel;
	}

	/**
	 * Sets the use parent level flag, if this is set to <code>true</code> the
	 * logger will use the set parent level.
	 * 
	 * @param useParentLevel - Parent level flag.
	 */
	public void setUseParentLevel(boolean useParentLevel) {
		this.useParentLevel = useParentLevel;
	}

	/**
	 * Returns the current logger filter.
	 * 
	 * @return Logger filter.
	 */
	public Filter getFilter() {
		if (useParentFilter) {
			if (getParent() != null) {
				return parent.getFilter();
			}
		}
		return filter;
	}

	/**
	 * Sets the logger filter.
	 * 
	 * @param filter - Filter instance.
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	/**
	 * Returns the use parent filter flag.
	 * 
	 * @return Parent filter flag.
	 */
	public boolean getUseParentFilter() {
		return useParentFilter;
	}

	/**
	 * Sets the use parent filter flag, if this is set to <code>true</code> the
	 * logger will use the set parent level.
	 * 
	 * @param useParentLevel - Parent level flag.
	 */
	public void setUseParentFilter(boolean useParentFilter) {
		this.useParentFilter = useParentFilter;
	}

	/**
	 * Returns the current logger formatter.
	 * 
	 * @return Logger formatter.
	 */
	public Formatter getFormatter() {
		if (useParentFormatter) {
			if (getParent() != null) {
				return parent.getFormatter();
			}
		}
		return formatter;
	}

	/**
	 * Sets the logger formatter.
	 * 
	 * @param formatter - Logger formatter.
	 */
	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * Returns the use parent formatter flag.
	 * 
	 * @return Parent formatter flag.
	 */
	public boolean getUseParentFormatter() {
		return useParentFormatter;
	}

	/**
	 * Sets the use parent formatter flag, if this is set to <code>true</code> the
	 * logger will use the set parent level.
	 * 
	 * @param useParentLevel - Parent formatter flag.
	 */
	public void setUseParentFormatter(boolean useParentFormatter) {
		this.useParentFormatter = useParentFormatter;
	}

	/**
	 * Adds the specified appender to the end of the list, if the specified appender
	 * is equal to null it will be ignored.
	 * 
	 * @param appender - Appender to be added.
	 * 
	 * @return <code>true</code> if the appender was added, <code>false</code>
	 *         otherwise.
	 */
	public boolean addAppender(Appender appender) {
		if (!(appender == null)) {
			return appenders.add(appender);
		}
		return false;
	}

	/**
	 * Removes the first instance of the specified appender from the appenders list,
	 * the appender is only removed if it exists in the list.
	 * 
	 * @param appender - Appender to be removed.
	 * 
	 * @return <code>true</code> if the appender was removed, <code>false</code>
	 *         otherwise.
	 */
	public boolean removeAppender(Appender appender) {
		if (!(appender == null)) {
			return appenders.remove(appender);
		}
		return false;
	}

	/**
	 * Returns the current logger appenders.
	 * 
	 * @return Logger appenders.
	 */
	public Collection<Appender> getAppenders() {
		if (useParentAppenders) {
			if (getParent() != null) {
				return getParent().getAppenders();
			}
		}
		return Collections.unmodifiableCollection(appenders);
	}

	/**
	 * Clears the appenders list.
	 */
	public void clearAppenders() {
		appenders.clear();
	}

	/**
	 * Returns the use appenders formatter flag.
	 * 
	 * @return Parent formatter flag.
	 */
	public boolean getUseParentAppenders() {
		return useParentAppenders;
	}

	/**
	 * Sets the use parent appenders flag, if this is set to <code>true</code> the
	 * logger will use the set parent level.
	 * 
	 * @param useParentLevel - Parent formatter flag.
	 */
	public void setUseParentAppenders(boolean useParentAppenders) {
		this.useParentAppenders = useParentAppenders;
	}

	/**
	 * Constructs a new <code>Logger</code> instance with
	 * <code>clazz.getCanonicalName()</code> as the specified name, if a
	 * <code>Logger</code> instance already exists within the cache it will be
	 * returned, otherwise a new instance will be constructed and added to the
	 * cache.
	 * 
	 * @param name - Logger name.
	 * 
	 * @return <code>Logger</code> instance.
	 */
	public static final Logger getLogger(Class<?> clazz) {
		return getLogger(clazz, false);
	}
	
	/**
	 * Constructs a new <code>Logger</code> instance with
	 * <code>clazz.getCanonicalName()</code> as the specified name, if a
	 * <code>Logger</code> instance already exists within the cache it will be
	 * returned, otherwise a new instance will be constructed and added to the
	 * cache.
	 * 
	 * @param name - Logger name.
	 * 
	 * @return <code>Logger</code> instance.
	 */
	public static final Logger getLogger(Class<?> clazz, boolean simpleName) {
		if (simpleName) {
			return getLogger(clazz.getSimpleName());
		}
		return getLogger(clazz.getCanonicalName());
	}

	/**
	 * Constructs a new <code>Logger</code> instance with the specified name, if a
	 * <code>Logger</code> instance already exists within the cache it will be
	 * returned, otherwise a new instance will be constructed and added to the
	 * cache.
	 * 
	 * @param name - Logger name.
	 * 
	 * @return <code>Logger</code> instance.
	 */
	public static final Logger getLogger(String name) {
		if (Cache.containsLogger(name)) {
			return Cache.getLogger(name);
		} else {
			Logger logger = new Logger(name);
			if (name.equals("GLOBAL")) {
				logger.setParent(null);
				logger.setUseParentAppenders(false);
				logger.setUseParentFilter(false);
				logger.setUseParentFormatter(false);
				logger.setUseParentLevel(false);
			} else {
				logger.setParent(GLOBAL);
			}
			logger.addAppender(new StdOutAppender());
			Cache.addLogger(logger);
			return logger;
		}
	}
}
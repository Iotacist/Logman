package uk.co.iotacist.logman.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.iotacist.logman.Log;

public class TimeNameLevelFormatter implements Formatter {
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
	 * Formats the timestamp to [dd/MM/yyyy HH:mm:ss].
	 */
	private SimpleDateFormat formatter;
	
	/**
	 * Constructs a new <code>TimeNameLevelFormatter</code> instance.
	 */
	public TimeNameLevelFormatter() {
		this.formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Override
	public String format(Log log) {
		String time = OPEN_BRACKET + formatter.format(new Date()) + CLOSE_BRACKET;
		String name = OPEN_BRACKET + log.getLogger().getName() + CLOSE_BRACKET;
		String level = OPEN_BRACKET + log.getLevel().getName() + CLOSE_BRACKET;
		String message = SPACE_BAR + log.getMessage().toString();
		return time + name + level + message;
	}
}
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
package uk.co.iotacist.logman.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import uk.co.iotacist.logman.Level;
import uk.co.iotacist.logman.Log;
import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.logman.appender.Appender;

/**
 * The <code>Console</code> class takes <code>LoggerEvent</code> messages from
 * the logger and exports them to a console window.
 * 
 * @since 1.0
 * 
 * @version 1.0
 * 
 * @author Iotacist <iotacist@gmail.com>
 */
public class Console extends JPanel implements Appender {
	/**
	 * Log list model.
	 */
	protected final DefaultListModel<Log> CONSOLE_LOG_MODEL;

	/**
	 * Log list.
	 */
	protected final JList<Log> CONSOLE_LOG;

	/**
	 * Log list renderer.
	 */
	protected final ConsoleCellRenderer CONSOLE_LOG_RENDERER;

	/**
	 * Log list scroll bar.
	 */
	protected final JScrollPane CONSOLE_LOG_SCROLLBAR;

	/**
	 * Log list scroll bar handler.
	 */
	protected final SmartScroller CONSOLE_LOG_SMART_CONTROLLER;

	/**
	 * Severe log text foreground color.
	 * 
	 * Boston University Red.
	 */
	protected final Color consoleSevereColor = new Color(204, 0, 0);

	/**
	 * Warn log text foreground color.
	 * 
	 * Tangelo.
	 */
	protected Color consoleWarnColor = new Color(230, 76, 0);

	/**
	 * Info log text foreground color.
	 * 
	 * Electric Green.
	 */
	protected Color consoleInfoColor = new Color(0, 230, 0);

	/**
	 * Debug log text foreground color.
	 * 
	 * Duke Blue.
	 */
	protected Color consoleDebugColor = new Color(0, 0, 153);

	/**
	 * Default log text foreground color.
	 * 
	 * Default Yellow.
	 */
	protected Color consoleLogColor = new Color(255, 255, 0);

	/**
	 * Default panel background.
	 * 
	 * Default White.
	 */
	protected Color consoleBackgroundColor = new Color(255, 255, 255);

	/**
	 * Default selected item background.
	 * 
	 * Light Grey.
	 */
	protected Color consoleSelectedColor = new Color(211, 211, 211);

	/**
	 * The serialization runtime associates with each serializable class a version
	 * number, called a serialVersionUID, which is used during deserialization to
	 * verify that the sender and receiver of a serialized object have loaded
	 * classes for that object that are compatible with respect to serialization.
	 */
	private static final long serialVersionUID = -6592993870163868178L;

	public Console() {
		this(450, 200);
	}

	public Console(int width, int height) {
		this(width, height, Color.WHITE);
	}

	public Console(int width, int height, Color background) {
		this(width, height, background, Color.LIGHT_GRAY);
	}

	public Console(int width, int height, Color background, Color selected) {
		this(new Dimension(width, height), background, selected, new Font("Tahoma", Font.BOLD, 14));
	}

	private Console(Dimension dim, Color background, Color selected, Font font) {
		/*
		 * 
		 */
		CONSOLE_LOG_RENDERER = new ConsoleCellRenderer(consoleSevereColor, consoleWarnColor, consoleInfoColor,
				consoleDebugColor, consoleLogColor, background, selected);
		/*
		 * Setup logger list.
		 */
		CONSOLE_LOG_MODEL = new DefaultListModel<Log>();
		CONSOLE_LOG = new JList<Log>(CONSOLE_LOG_MODEL);
		CONSOLE_LOG.setValueIsAdjusting(true);
		CONSOLE_LOG.setFont(font);
		CONSOLE_LOG.setCellRenderer(CONSOLE_LOG_RENDERER);
		CONSOLE_LOG.setLayoutOrientation(JList.VERTICAL);
		/*
		 * Setup scroll bar.
		 */
		CONSOLE_LOG_SCROLLBAR = new JScrollPane(CONSOLE_LOG);
		CONSOLE_LOG_SCROLLBAR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		CONSOLE_LOG_SCROLLBAR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		CONSOLE_LOG_SCROLLBAR.setViewportView(CONSOLE_LOG);
		CONSOLE_LOG_SCROLLBAR.setAutoscrolls(true);
		CONSOLE_LOG_SCROLLBAR.setVisible(true);
		CONSOLE_LOG_SMART_CONTROLLER = new SmartScroller(CONSOLE_LOG_SCROLLBAR, SmartScroller.VERTICAL,
				SmartScroller.END);
		/*
		 * Init JPanel.
		 */
		setLayout(new BorderLayout());
		add(CONSOLE_LOG_SCROLLBAR, BorderLayout.CENTER);
		setPreferredSize(dim);
		setBackground(background);
		/*
		 * Configure global logger to redirect all logs.
		 */
		Logger.GLOBAL.addAppender(this);
	}

	@Override
	public void setBackground(Color background) {
		if (background != null) {
			consoleBackgroundColor = background;
			super.setBackground(background);
		}
	}

	public void setSelectedBackground(Color selected) {
		if (selected != null) {
			consoleSelectedColor = selected;
		}
	}

	@Override
	public void append(Log log) {
		CONSOLE_LOG_MODEL.addElement(log);
		CONSOLE_LOG.ensureIndexIsVisible(CONSOLE_LOG_MODEL.getSize());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.dispose();
	}

	protected static class ConsoleCellRenderer extends DefaultListCellRenderer {
		/**
		 * Severe log text foreground color.
		 * 
		 * Boston University Red.
		 */
		protected Color severeColor = new Color(204, 0, 0);
		/**
		 * Warn log text foreground color.
		 * 
		 * Tangelo.
		 */
		protected Color warnColor = new Color(230, 76, 0);
		/**
		 * Info log text foreground color.
		 * 
		 * Electric Green.
		 */
		protected Color infoColor = new Color(0, 230, 0);
		/**
		 * Debug log text foreground color.
		 * 
		 * Ultramarine Blue.
		 */
		protected Color debugColor = new Color(51, 85, 255);
		/**
		 * Log text foreground color.
		 * 
		 * Default White.
		 */
		protected Color logColor = new Color(255, 255, 255);
		/**
		 * Background color for JPanel.
		 */
		protected Color backgroundColor;
		/**
		 * Selected item background.
		 */
		protected Color selectedColor;
		/**
		 * The serialization runtime associates with each serializable class a version
		 * number, called a serialVersionUID, which is used during deserialization to
		 * verify that the sender and receiver of a serialized object have loaded
		 * classes for that object that are compatible with respect to serialization.
		 */
		private static final long serialVersionUID = -3059877184559013726L;

		/**
		 * Creates a new <code>ConsoleCellRenderer</code> with the specified colours.
		 * 
		 * @param severe     - Text colour of <code>Level.SEVERE</code> log events.
		 * @param warn       - Text colour of <code>Level.WARN</code> log events.
		 * @param info       - Text colour of <code>Level.INFO</code> log events.
		 * @param debug      - Text colour of <code>Level.DEBUG</code> log events.
		 * @param log        - Text colour of base log events.
		 * @param background - Colour of <code>Console</code> background.
		 * @param selected   - Colour of <code>Console</code> selected items background.
		 */
		protected ConsoleCellRenderer(Color severe, Color warn, Color info, Color debug, Color log, Color background,
				Color selected) {
			/** Checking severe color is not null **/
			if (severe != null) {
				severeColor = severe;
				/** Checking warn color is not null **/
			} else if (warn != null) {
				warnColor = warn;
				/** Checking info color is not null **/
			} else if (info != null) {
				infoColor = info;
				/** Checking debug color is not null **/
			} else if (debug != null) {
				debugColor = debug;
				/** Checking log color is not null **/
			} else if (log != null) {
				logColor = log;
				/** Checking background color is not null **/
			} else if (background != null) {
				backgroundColor = background;
				/** Checking selected color is not null **/
			} else if (selected != null) {
				selectedColor = selected;
			}
			/**
			 * Set the ConsoleCellRenderer to opaque.
			 **/
			setOpaque(true);
		}

		protected Color lookup(Level level) {
			if (level.equals(Level.SEVERE)) {
				return severeColor;
			}
			if (level.equals(Level.WARN)) {
				return warnColor;
			}
			if (level.equals(Level.INFO)) {
				return infoColor;
			}
			if (level.equals(Level.DEBUG)) {
				return debugColor;
			}
			return logColor;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Log log = (Log) value;
			value = log.getFormattedMessage();

			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			c.setForeground(lookup(log.getLevel()));
			c.setBackground(isSelected ? selectedColor : backgroundColor);
			return c;
		}
	}

	/**
	 * The SmartScroller will attempt to keep the viewport positioned based on the
	 * users interaction with the scrollbar. The normal behaviour is to keep the
	 * viewport positioned to see new data as it is dynamically added.
	 *
	 * Assuming vertical scrolling and data is added to the bottom:
	 *
	 * - when the viewport is at the bottom and new data is added, then
	 * automatically scroll the viewport to the bottom - when the viewport is not at
	 * the bottom and new data is added, then do nothing with the viewport
	 *
	 * Assuming vertical scrolling and data is added to the top:
	 *
	 * - when the viewport is at the top and new data is added, then do nothing with
	 * the viewport - when the viewport is not at the top and new data is added,
	 * then adjust the viewport to the relative position it was at before the data
	 * was added
	 *
	 * Similiar logic would apply for horizontal scrolling.
	 */
	public class SmartScroller implements AdjustmentListener {
		public final static int HORIZONTAL = 0;
		public final static int VERTICAL = 1;

		public final static int START = 0;
		public final static int END = 1;

		private int viewportPosition;

		private JScrollBar scrollBar;
		private boolean adjustScrollBar = true;

		private int previousValue = -1;
		private int previousMaximum = -1;

		/**
		 * Convenience constructor. Scroll direction is VERTICAL and viewport position
		 * is at the END.
		 *
		 * @param scrollPane the scroll pane to monitor
		 */
		public SmartScroller(JScrollPane scrollPane) {
			this(scrollPane, VERTICAL, END);
		}

		/**
		 * Convenience constructor. Scroll direction is VERTICAL.
		 *
		 * @param scrollPane       the scroll pane to monitor
		 * @param viewportPosition valid values are START and END
		 */
		public SmartScroller(JScrollPane scrollPane, int viewportPosition) {
			this(scrollPane, VERTICAL, viewportPosition);
		}

		/**
		 * Specify how the SmartScroller will function.
		 *
		 * @param scrollPane       the scroll pane to monitor
		 * @param scrollDirection  indicates which JScrollBar to monitor. Valid values
		 *                         are HORIZONTAL and VERTICAL.
		 * @param viewportPosition indicates where the viewport will normally be
		 *                         positioned as data is added. Valid values are START
		 *                         and END
		 */
		public SmartScroller(JScrollPane scrollPane, int scrollDirection, int viewportPosition) {
			if (scrollDirection != HORIZONTAL && scrollDirection != VERTICAL)
				throw new IllegalArgumentException("invalid scroll direction specified");

			if (viewportPosition != START && viewportPosition != END)
				throw new IllegalArgumentException("invalid viewport position specified");

			this.viewportPosition = viewportPosition;

			if (scrollDirection == HORIZONTAL)
				scrollBar = scrollPane.getHorizontalScrollBar();
			else
				scrollBar = scrollPane.getVerticalScrollBar();

			scrollBar.addAdjustmentListener(this);

			// Turn off automatic scrolling for text components

			Component view = scrollPane.getViewport().getView();

			if (view instanceof JTextComponent) {
				JTextComponent textComponent = (JTextComponent) view;
				DefaultCaret caret = (DefaultCaret) textComponent.getCaret();
				caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
			}
		}

		@Override
		public void adjustmentValueChanged(final AdjustmentEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					checkScrollBar(e);
				}
			});
		}

		/*
		 * Analyze every adjustment event to determine when the viewport needs to be
		 * repositioned.
		 */
		private void checkScrollBar(AdjustmentEvent e) {
			// The scroll bar listModel contains information needed to determine
			// whether the viewport should be repositioned or not.

			JScrollBar scrollBar = (JScrollBar) e.getSource();
			BoundedRangeModel listModel = scrollBar.getModel();
			int value = listModel.getValue();
			int extent = listModel.getExtent();
			int maximum = listModel.getMaximum();

			boolean valueChanged = previousValue != value;
			boolean maximumChanged = previousMaximum != maximum;

			// Check if the user has manually repositioned the scrollbar

			if (valueChanged && !maximumChanged) {
				if (viewportPosition == START)
					adjustScrollBar = value != 0;
				else
					adjustScrollBar = value + extent >= maximum;
			}

			// Reset the "value" so we can reposition the viewport and
			// distinguish between a user scroll and a program scroll.
			// (ie. valueChanged will be false on a program scroll)

			if (adjustScrollBar && viewportPosition == END) {
				// Scroll the viewport to the end.
				scrollBar.removeAdjustmentListener(this);
				value = maximum - extent;
				scrollBar.setValue(value);
				scrollBar.addAdjustmentListener(this);
			}

			if (adjustScrollBar && viewportPosition == START) {
				// Keep the viewport at the same relative viewportPosition
				scrollBar.removeAdjustmentListener(this);
				value = value + maximum - previousMaximum;
				scrollBar.setValue(value);
				scrollBar.addAdjustmentListener(this);
			}

			previousValue = value;
			previousMaximum = maximum;
		}
	}

}
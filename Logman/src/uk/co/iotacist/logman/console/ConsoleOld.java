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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.co.iotacist.logman.Level;
import uk.co.iotacist.logman.Log;
import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.logman.appender.Appender;

public class ConsoleOld {
	/**
	 * Console view instance.
	 */
	private final ConsoleView CONSOLE_VIEW;

	/**
	 * Console controller instance.
	 */
	private final ConsoleController CONSOLE_CONTROLLER;

	/**
	 * Console logger instance.
	 */
	private final Logger CONSOLE_LOGGER;

	/**
	 * Creates a new <code>Console</code> instance.
	 */
	public ConsoleOld() {
		/*
		 * Setup MVC model variables.
		 */
		CONSOLE_VIEW = new ConsoleView(this);
		CONSOLE_CONTROLLER = new ConsoleController(this, CONSOLE_VIEW);
		/*
		 * Init console logger.
		 */
		CONSOLE_LOGGER = Logger.getLogger(ConsoleOld.class);
		CONSOLE_LOGGER.setUseParentAppenders(true);
		CONSOLE_LOGGER.setUseParentFilter(true);
		CONSOLE_LOGGER.setUseParentFormatter(true);
		CONSOLE_LOGGER.setUseParentLevel(true);
		/*
		 * Configure global logger to redirect all logs.
		 */
		Logger.GLOBAL.addAppender(CONSOLE_CONTROLLER);
	}

	public void init() {
		CONSOLE_VIEW.init();
	}

	public JScrollPane getPane() {
		return CONSOLE_VIEW.scroll;
	}

	/**
	 * 
	 * 
	 * @author Iotacist <iotacist@gmail.com>
	 * @version 1.1
	 * @since 1.0
	 */
	protected class ConsoleView {
		/**
		 * Console model instance.
		 */
		protected final ConsoleOld CONSOLE_MODEL;

		/**
		 * Console cell renderer instance.
		 */
		protected final ConsoleCellRenderer CONSOLE_RENDERER;

		/**
		 * Log list model.
		 */
		protected DefaultListModel<Log> listModel;

		/**
		 * Log list.
		 */
		protected JList<Log> list;

		/**
		 * Panel scroll bar.
		 */
		protected JScrollPane scroll;

		protected ConsoleView(ConsoleOld console) {
			this.CONSOLE_MODEL = console;
			this.CONSOLE_RENDERER = new ConsoleCellRenderer();
		}

		protected void init() {
			/*
			 * Setup logger list.
			 */
			listModel = new DefaultListModel<Log>();
			list = new JList<Log>(listModel);
			list.setVisibleRowCount(10);
			list.setValueIsAdjusting(true);
			list.setFont(new Font("Tahoma", Font.PLAIN, 14));
			list.setCellRenderer(CONSOLE_RENDERER);
			/*
			 * Setup scroll bar.
			 */
			scroll = new JScrollPane();
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setViewportView(list);
			scroll.setAutoscrolls(true);
		}

		public void add(Log log) {

		}
	}

	/**
	 * 
	 * 
	 * @author Iotacist <iotacist@gmail.com>
	 * @version 1.1
	 * @since 1.0
	 */
	protected class ConsoleController implements Appender {
		/**
		 * Console model instance.
		 */
		protected final ConsoleOld CONSOLE_MODEL;

		/**
		 * Console view instance.
		 */
		protected final ConsoleView CONSOLE_VIEW;

		protected ConsoleController(ConsoleOld console, ConsoleView view) {
			CONSOLE_MODEL = console;
			CONSOLE_VIEW = view;
		}

		@Override
		public void append(Log log) {
			CONSOLE_VIEW.listModel.addElement(log);
			CONSOLE_VIEW.list.ensureIndexIsVisible(CONSOLE_VIEW.list.getLastVisibleIndex());
		}
	}

	protected class ConsoleCellRenderer extends DefaultListCellRenderer {
		/**
		 * The serialization runtime associates with each serializable class a version
		 * number, called a serialVersionUID, which is used during deserialization to
		 * verify that the sender and receiver of a serialized object have loaded
		 * classes for that object that are compatible with respect to serialization.
		 */
		private static final long serialVersionUID = -3059877184559013726L;

		protected ConsoleCellRenderer() {
			setOpaque(true);
		}

		protected Color lookup(Level level) {
			if (level.equals(Level.SEVERE)) {
				return Color.RED;
			}
			if (level.equals(Level.WARN)) {
				return Color.ORANGE;
			}
			if (level.equals(Level.INFO)) {
				return Color.BLUE;
			}
			if (level.equals(Level.DEBUG)) {
				return Color.GREEN;
			}
			return Color.BLACK;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Log log = (Log) value;
			value = log.getMessage();

			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			c.setForeground(lookup(log.getLevel()));
			c.setBackground(isSelected ? Color.lightGray : Color.WHITE);
			return c;
		}
	}
}
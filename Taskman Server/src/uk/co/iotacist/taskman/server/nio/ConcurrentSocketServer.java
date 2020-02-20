/*
 * Copyright 2020 Iotacist.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.co.iotacist.taskman.server.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.taskman.common.net.NetworkUtils;
import uk.co.iotacist.taskman.server.util.UUID;

/**
 * 
 * @author Iotacist <iotacist@gmail.com>
 * 
 * @version 1.0.0
 */
public class ConcurrentSocketServer implements Runnable {
	/*
	 * 
	 */
	protected ServerSocket serverSocket;
	protected InetAddress serverAddress;
	protected int serverPort;
	protected Thread serverThread;
	protected int serverThreads;
	protected ExecutorService serverThreadPool;
	protected boolean isRunning = false;
	protected UUID uuid;
	protected Logger log;

	public ConcurrentSocketServer(int port) {
		this(port, 10);
	}

	public ConcurrentSocketServer(int port, int threads) {
		this(NetworkUtils.getLocalHost(), port, threads);
	}

	/**
	 * Constructs a new <code>AsyncSocketServer</code> instance with the specified
	 * address, port and thread pool size.
	 * 
	 * @param bindAddr - the local InetAddress the server will bind to.
	 * @param port     - the port number, or 0 to use a port number that is
	 *                 automatically allocated.
	 * @param threads  - the number of threads in the pool.
	 */
	public ConcurrentSocketServer(InetAddress bindAddr, int port, int threads) {
		if (bindAddr != null) {
			this.serverAddress = bindAddr;
		}
		if (port > 0 && port < 25565) {
			this.serverPort = port;
		}
		if (threads > 0) {
			this.serverThreads = threads;
			this.serverThreadPool = Executors.newFixedThreadPool(threads);
		}
		uuid = new UUID();
		log = Logger.getLogger(ConcurrentSocketServer.class);
		log.setUseParentAppenders(true);
		log.setUseParentFilter(true);
		log.setUseParentFormatter(true);
		log.setUseParentLevel(true);
	}

	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(serverPort);
			log.info("Socket server started, accepting traffic on port " + serverPort);
		} catch (IOException e) {
			log.severe("Cannot open port: " + serverPort, e);
			System.exit(1);
		}
	}

	private synchronized boolean isRunning() {
		return isRunning;
	}

	public synchronized void start() {
		synchronized (this) {
			isRunning = true;
			serverThread = new Thread(this);
			serverThread.start();
		}
	}

	public synchronized void stop() {
		synchronized (this) {
			isRunning = false;
		}
	}

	@Override
	public void run() {
		log.debug("Attempting to open the server socket.");
		// Create socket server to handle traffic.
		openServerSocket();
		log.debug("Socket has been opened successfully.");
		// Loop while server is running.
		while (isRunning()) {
			// Create null Socket so we can execute in thread pool.
			Socket clientSocket = null;
			String id = null;
			try {
				// Accept traffic on socket.
				clientSocket = this.serverSocket.accept();
				id = uuid.generateId(128);
				log.info(uuid + " connected from " + clientSocket.toString());
			} catch (IOException e) {
				// Break from for loop if isRunning = false.
				if (!isRunning()) {
					break;
				}
				log.warn("Error accepting client connection" + serverPort, e);
			}
			// Execute socket handler in the thread pool.
			serverThreadPool.execute(new ConcurrentSocketHandler(clientSocket, id));
		}
		log.info("Socket server is shutting down.");
		try {
			serverThreadPool.shutdown();
			serverSocket.close();
		} catch (IOException e) {
			log.severe("Error closing server: " + serverPort, e);
			System.exit(1);
		}
		log.info("Socket server has been shutdown.");
	}
}
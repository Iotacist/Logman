package uk.co.iotacist.taskman.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.logman.console.Console;

public class Main extends WindowAdapter implements Runnable {
	
	private JFrame frame;
	
	private Console console;
	
	private ConcurrentSocketServer socketServer;
	
	private Logger log;
	
	public Main(int width, int height, int port) {
		/* Setup Console instance. */
		console = new Console(width, height, Color.DARK_GRAY, Color.LIGHT_GRAY);
		/* Setup JFrame so we can attach Console */
		frame = new JFrame();
		frame.setTitle("Taskman Server Console");
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(console);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		/* Setup socket server instance. */
		socketServer = new ConcurrentSocketServer(port);
		/* Setup Logger instance. */
		log = Logger.getLogger(Main.class);
		log.setUseParentAppenders(true);
		log.setUseParentFilter(true);
		log.setUseParentFormatter(true);
		log.setUseParentLevel(true);
		/* Start the socket server and the main class thread. */
		socketServer.start();
		new Thread(this).start();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		socketServer.stop();
		super.windowClosing(e);
	}
	
	@Override
	public void run() {
		
	}

	public static void main(String[] args) {
		new Main(800, 400, 9999);
	}
}
package uk.co.iotacist.taskman.common.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {
	public static final InetAddress getLocalHost() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr;
	}
}
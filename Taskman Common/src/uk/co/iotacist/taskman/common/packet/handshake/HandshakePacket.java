package uk.co.iotacist.taskman.common.packet.handshake;

import uk.co.iotacist.taskman.common.packet.Packet;

public class HandshakePacket extends Packet {
	
	
	/**
	 * Handshake packet state. 
	 */
	protected HandshakePacketState handshakeState;
	
	/**
	 * The serialization runtime associates with each serializable class a version
	 * number, called a serialVersionUID, which is used during deserialization to
	 * verify that the sender and receiver of a serialized object have loaded
	 * classes for that object that are compatible with respect to serialization.
	 */
	private static final long serialVersionUID = -1274361813213810141L;

	public HandshakePacket() {
		
	}
}
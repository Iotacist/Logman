package uk.co.iotacist.taskman.common.packet;

import java.io.Serializable;

public abstract class Packet implements Serializable {
	/**
	 * Session universally unique identifier (UUID) number.
	 */
	protected String sessionID;

	/**
	 * The serialization runtime associates with each serializable class a version
	 * number, called a serialVersionUID, which is used during deserialization to
	 * verify that the sender and receiver of a serialized object have loaded
	 * classes for that object that are compatible with respect to serialization.
	 */
	private static final long serialVersionUID = 8145405743555806565L;

	/**
	 * Creates a <code>Packet</code> instance.
	 */
	public Packet() {
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		if (sessionID != null && !sessionID.isBlank() && !sessionID.isEmpty()) {
			this.sessionID = sessionID;
		}
	}
}
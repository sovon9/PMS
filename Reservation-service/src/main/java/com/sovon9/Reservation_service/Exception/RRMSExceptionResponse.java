package com.sovon9.Reservation_service.Exception;

/**
 * provides exception structure for Exception
 * @author Sovon Singha
 *
 */
public class RRMSExceptionResponse {

	private int status;
	private String message;
	private long timestamp;
	public RRMSExceptionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "RRMSExceptionResponse [status=" + status + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
	
}

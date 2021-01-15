package exception;

public class BatchException extends Exception {

	private static final long serialVersionUID = 1L;

	private String messageID;
	
	public BatchException(String messageID) {
		
		this.messageID = messageID;
	}

	public String getMessageID() {
		return messageID;
	}
}

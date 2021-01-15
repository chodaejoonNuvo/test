package exception;

public class ApplicationException extends BatchException {

	private static final long serialVersionUID = 1L;
	
	public ApplicationException(String messageID) {
		super(messageID);
	}
}

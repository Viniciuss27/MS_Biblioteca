package vinix.services.exception;

public class DatabaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DatabaseException(String name) {	super(name);}
}
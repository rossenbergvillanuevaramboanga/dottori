package it.prova.dottori.web.api.exception;

public class DottoreNotInServizioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DottoreNotInServizioException(String string) {
		super(string);
	}

}

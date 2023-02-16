package com.stonewashedpc.cocktailmakerng.exceptions;

/**
 * Exception for when some element cannot be found.
 * @author Joel
 *
 */
public class ElementNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6008287316158841712L;

	public ElementNotFoundException(String msg) {
		super(msg);
	}

	public ElementNotFoundException() {
		super();
	}
	
}

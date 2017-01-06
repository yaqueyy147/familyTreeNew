/**
 * 
 * @author Qiuxj create 2012-11-8
 */
package com.witkey.familyTree.exception;

/**
 * @author Qiuxj
 * 
 */
public class UnLoginException extends RuntimeException {

	private static final long serialVersionUID = -2045724314336478607L;

	public UnLoginException(String message) {
		super(message);
	}

	public UnLoginException(String message, Throwable cause) {
		super(message, cause);
	}

}

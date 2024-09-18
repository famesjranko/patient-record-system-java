
/*	
 *	InvalidPatientRecordSystemException.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 15.05.2018
 *	Version 1.0
 *
 *	---Class outline---
 *	Extends the Exception class.
 *	
 *	Really isn't any need for this as it is effectively the same as
 *	as just using the Exception class. However, we were shown this in 
 *	IOO lab04 and wasn't sure if this is what was wanted from us for 
 *	task3 in the assignment.
 *	
 *	---End---
 *
 */

public class InvalidPatientRecordSystemException extends Exception {

	private static final long serialVersionUID = 1L;

	// constructors
	public InvalidPatientRecordSystemException() {
		super();
	}
	
	public InvalidPatientRecordSystemException(String message) {
		super(message);
	}
}

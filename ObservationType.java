/*	
 *	ObservationType.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.0	
 *
 *	---Class outline---
 *	The ObservationType class is the base class for Observation Types. It is extended
 *	by two derived classes: 1)MeasurementObservationType; 2) CategoryObservationType.
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */	
 
public abstract class ObservationType {
	private String name;
	private String code;

	// constructor
	public ObservationType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	// public getters
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	// toString method
	public String toString() {
		return "\tName: " + name + "\n\tCode: " + code;
	}
}
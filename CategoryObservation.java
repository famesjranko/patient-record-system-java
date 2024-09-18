/*	
 *	CategoryObservation.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.0	
 *
 *	---Class outline---
 *	The CategoryObservation class is a derived class from Observation. It holds
 *	the information for recorded patient observation of type CategoryObservationType. 	 
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */	

public class CategoryObservation extends Observation {
	private String value;

	// constructor
	public CategoryObservation(ObservationType observationType, String value) {
		super(observationType);
		this.value = value;
	}
	
	// public getter
	public String getValue() {
		return value;
	}
	
	// public setter
	public void setValue(String value) {
		this.value = value;
	}

	// toString method
	public String toString() {
		return super.toString() + "\n\tValue: " + value;
	}
}
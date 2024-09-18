/*	
 *	MeasurementObservationType.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.0	
 *
 *	---Class outline---
 *	The MeasurementObservationType class is a derived class from ObservationType.
 *	It holds the information for ObservationTypes of type MeasurementObservationType. 
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */	

public class MeasurementObservationType extends ObservationType {
	private String unit;

	// constructor
	public MeasurementObservationType(String code, String name, String unit) {
		super(code, name);
		this.unit = unit;
	}

	// public getter
	public String getUnit() {
		return unit;
	}

	// toString method
	public String toString() {
		return super.toString() + "\n\tUnit: " + unit;
	}
}
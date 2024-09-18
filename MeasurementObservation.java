/*	
 *	MeasurementObservation.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.0	
 *
 *	---Class outline---
 *	The MeasurementObservation class is a derived class from Observation. It holds
 *	the information for recorded patient observation of type MeasurementObservationType. 	 
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */	

public class MeasurementObservation extends Observation {
	// value set to double as presumed that Measurement Observations may have
	// decimal values: eg. height, 175.4cm.
	private double value;

	// constructor
	public MeasurementObservation(ObservationType observationType, double value) {
		super(observationType);
		this.value = value;
	}

	// public getter
	public double getValue() {
		return value;
	}

	// public setter
	public void setValue(double value) {
		this.value = value;
	}

	// toString method
	public String toString() {
		return super.toString() + "\n\tValue: " + value;
	}
}
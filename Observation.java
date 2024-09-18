/*	
 *	Observation.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.0	
 *
 *	---Class outline---
 *	The Observation class is the base class for Observation Types. It is extended
 *	by two derived classes: 1)MeasurementObservation; 2) CategoryObservation.
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */	

public class Observation {
	private ObservationType observationType;

	// constructor
	public Observation(ObservationType observationType) {
		this.observationType = observationType;
	}

	// public getters
	public ObservationType getObservationType() {
		return observationType;
	}

	// toString method
	public String toString() {
		return "" + observationType;
	}
}

/*	
 *	Patient.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.1	
 *
 *	---Class outline---
 *	The Patient class represents the records for a single patient individual and 
 *	their relative medical observations, which are divided into two sub-categories 
 *	of observation Types: 1) Measurement; 2) Category.
 *	
 *	Both types of Observations can be added to a patient's record, however, a patient 
 *	may only have one Observation added for any one specific Observation Type. If 
 *	adding a new Observation and the Patient already has one of the Type being added, 
 *	the program will throw an exception of Type Exception.
 *	
 *	Moreover, patients may have a total maximum of 20 individual observations - this
 *	can be changed depending on systems requirements, as assignment1 didn't stipulate
 *	a specific max for Patient Observations. If a new observation is attempted to be 
 *	added when this maximum has been reached, the program will throw an exception of 
 *	Type Exception.
 *
 *	Regarding display output, no explicit requirements were made regarding display 
 *	output information, so have chosen to include all CategoryObservationType acceptable 
 *	values, along with the patients specific recorded value. This can be changed in 
 *	future versions if warranted.
 * 
 *	Additional: this program, in its current form, is written for use on Unix based 
 *	operating systems as it implements the interface OutputColours, which holds ansi 
 *	values for console output colours. While this will not break functionality on other 
 *	systems, colours may not output correctly and display extra non-standard characters 
 *	in their place.
 *	  
 *	To correct output on non Unix based operating systems, in the OutputColours interface 
 *	simply change ansi String values to empty, "".
 *
 *	Ansi colours were added to the output to aid in assignment output readability. 
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Patient implements OutputColours {
	private String id;
	private String name;
	private final List<Observation> observations;
	private final int MAX_OBSERVATIONS = 20; // arbitrarily set

	// constructor
	public Patient(String id, String name) {
		this.name = name;
		this.id = id;
		observations = new ArrayList<Observation>();
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW PATIENT", success);
	}

	// public add methods
	public void addMeasurementObservation(ObservationType observationType, String observationCode, double value) throws InvalidPatientRecordSystemException {
		// input error: cannot be full
		if (observations.size() >= MAX_OBSERVATIONS) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation, max observations has been reached");
		}

		// returns -1 if not found and array index value if found
		boolean hasObservationtype = (hasMeasurementObservationType(observationCode) != -1);
		if (hasObservationtype) {
			// input error: cannot have observation twice
			throw new InvalidPatientRecordSystemException("patient already has observation of that type");
		}

		observations.add(new MeasurementObservation(observationType, value));
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION", success);
	}

	public void addcategoryObservation(ObservationType observationType, String observationCode, String categoryValue) throws InvalidPatientRecordSystemException {
		// input error: cannot be full
		if (observations.size() >= MAX_OBSERVATIONS) {
			throw new InvalidPatientRecordSystemException("cannot add new category observation, max observations has been reached");
		}

		// returns -1 if not found, array index value if found
		boolean hasObservationtype = (hasCategoryObservationType(observationCode) != -1);
		if (hasObservationtype) {
			// input error: cannot have observation twice
			throw new InvalidPatientRecordSystemException("patient already has observation of that type");
		}

		observations.add(new CategoryObservation(observationType, categoryValue));
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION", success);
	}

	// public modify methods
	public void modifyMeasurementObservation(String observationCode, double value) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (observations.isEmpty()) {
			throw new InvalidPatientRecordSystemException("patient does not have any observations yet");
		}

		boolean patientHasObservation = (hasMeasurementObservationType(observationCode) != -1);
		if (!patientHasObservation) {
			// input error: must exist
			throw new InvalidPatientRecordSystemException("patient does not have a measurement observation matching that code");
		}

		int observationIndex = hasMeasurementObservationType(observationCode);
		((MeasurementObservation) (observations.get(observationIndex))).setValue(value);
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "MODIFY MEASUREMENT OBSERVATION VALUE", success);
	}

	public void modifyCategoryObservation(String observationCode, String value) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (observations.isEmpty()) {
			throw new InvalidPatientRecordSystemException("patient does not have any observations yet");
		}

		// returns -1 if not found, array index value if found
		boolean patientHasObservation = (hasCategoryObservationType(observationCode) != -1);
		if (!patientHasObservation) {
			// input error: must exist
			throw new InvalidPatientRecordSystemException("patient does not have a category observation matching that code");
		}

		int observationIndex = hasCategoryObservationType(observationCode);
		((CategoryObservation) (observations.get(observationIndex))).setValue(value);
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "MODIFY CATEGORY OBSERVATION VALUE", success);
	}

	// public delete methods
	public void deleteObservation(String observationCode) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (observations.isEmpty()) {
			throw new InvalidPatientRecordSystemException("patient does not have any observations yet");
		}

		// returns -1 if not found, array index value if found
		boolean patientHasObservation = (hasObservation(observationCode) != -1);
		if (!patientHasObservation) {
			// input error: must exist
			throw new InvalidPatientRecordSystemException("patient does not have observation of that type");
		}

		observations.remove(hasObservation(observationCode));
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "REMOVE OBSERVATION", success);
	}
	
	// private delete/clear data method
	private void clearAllObservations() {
		// remove all observations
		observations.clear();
	}

	// public getters
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public int getNumberOfObservations() {
		return observations.size();
	}
	
	// protected getter, to prevent unauthorised access to arrayList objects
	protected List<Observation> getObservations() {
		return observations;
	}

	// public internal conditions methods.
	public boolean hasCategoryObservationTypes() {
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i) instanceof CategoryObservation) {
				return true;
			}
		}
		return false;
	}

	public boolean hasMeasurementObservationTypes() {
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i) instanceof MeasurementObservation) {
				return true;
			}
		}
		return false;
	}

	// private internal condition check methods. 
	// set private as there isn't a need for outside access. 
	// however, technically no risk is posed by having set public.
	private int hasMeasurementObservationType(String observationCode) {
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i).getObservationType().getCode().equalsIgnoreCase(observationCode)
					&& observations.get(i).getObservationType() instanceof MeasurementObservationType) {
				return i;
			}
		}
		return -1;
	}

	private int hasCategoryObservationType(String observationCode) {
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i).getObservationType().getCode().equalsIgnoreCase(observationCode)
					&& observations.get(i).getObservationType() instanceof CategoryObservationType) {
				return i;
			}
		}
		return -1;
	}

	private int hasObservation(String observationCode) {
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i).getObservationType().getCode().equalsIgnoreCase(observationCode)) {
				return i;
			}
		}
		return -1;
	}

	// Display methods
	public String displayMeasurementObservations() {
		String measurementObservationsDisplay = "Measurement Observations:";

		// set measurement observation count start
		int measurementObvservationCount = 1;

		// collect info
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i) instanceof MeasurementObservation) {
				measurementObservationsDisplay += "\n[" + measurementObvservationCount++ + "]" + observations.get(i).toString() + "\n";
			}
		}
		return measurementObservationsDisplay;
	}

	public String displayCategoryObservations() {
		String categoryObservationsDisplay = "Category Observations:";

		// set category observation count start
		int categoryObservationCount = 1;

		// collect info
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i) instanceof CategoryObservation) {
				categoryObservationsDisplay += "\n[" + categoryObservationCount++ + "]" + observations.get(i).toString() + "\n";
			}
		}
		return categoryObservationsDisplay;
	}

	public String displayObservations() {
		// display logic
		String observationsDisplay = "";
		if (hasCategoryObservationTypes()) {
			observationsDisplay += displayCategoryObservations();
		}
		if (hasMeasurementObservationTypes()) {
			if (hasCategoryObservationTypes()) {
				observationsDisplay += "\n" + displayMeasurementObservations();
			} else
				observationsDisplay += displayMeasurementObservations();
		}
		return observationsDisplay;
	}

	public String toString() {
		// display logic
		String totalString = "";
		if (observations.size() > 0) {
			totalString = "Name: " + name + "\nId: " + id + "\n\n" 
						+ displayObservations();
		} else
			totalString = "Name: " + name + "\nId: " + id + "\n\n" 
						+ ANSI_FGBLACK_BGWHITE + " - No observations recorded - " + ANSI_RESET + "\n";
		return totalString;
	}
}

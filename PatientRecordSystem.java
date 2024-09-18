
/*	
 *	PatientRecordSystem.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.9
 *
 *	---Program outline---
 *	This program collects and holds information about patients and their relative
 *	medical observations, which are divided into two sub-categories of Observation 
 *	Types: 1) Measurement; 2) Category.
 *	
 *	Both types of Observation Types can be added to the system, however, they must 
 *	have a unique code identifier. Patients can also be added, but they too must 
 *	have a unique code identifier.
 *
 *	If adding a new Observation Type and the identifier is already in use, the 
 *	program will throw an exception of type Exception.  If adding a new patient and 
 *	the identifier is already in use, the program will throw an exception of type 
 *	Exception.
 *	
 *	Patients may only have one observation of a given Observation Type 
 *	added to their record. If adding a new observation to a Patient that already has 
 *	an Observation of that Type, the program will throw an exception of Type 
 *	Exception.
 *	
 *  There is a maximum amount of both Observation Types and Patients that can 
 *	be added to the system: Patients max = 50; Observation Types max = 100. If 
 *	attempting to add more than max allowed of either, the program will throw and 
 *	exception of Type Exception.
 *
 *  System data is able to be loaded from a text file. The loadData() method takes 
 *  in no parameters and file paths are currently hard-coded. The method tests for 
 *  errors, such as if the path is valid (both exists and is a file) and test for 
 *  correct formatting (in this case, formatting restrictions are only limited to 
 *  either being a String, or in some cases a double, and of x amount of parameters). 
 *  This could be expanded on and better limits imposed in future updates where 
 *  necessary. Note: Any records currently in the system when loadData() is called
 *  are "zero'd out" (deleted) before new data is loaded to the system. This can be 
 *  easily be changed by commenting out line 933 if system requires data to be added 
 *  on top of existing data records. 
 *  
 *  System data may also be saved to a text file. The SaveData() method takes in no
 *  parameters and file paths are currently hard-coded. The method overwrites any 
 *  pre-existing saved data if save files already exist, and creates files before 
 *  writing if no previous saves have occurred.
 *  
 *  Modifying and deleting functionality has also been implemented.
 * 
 *	Additional: this program, in its current form, is written for use on Unix based 
 *	operating systems as it implements the interface OutputColours, which holds ansi 
 *	values for console output colours. While this will not break functionality on 
 *	other systems, colours may not output correctly and display extra non-standard 
 *	characters in their place.
 *	  
 *	To correct output on non Unix based operating systems, in the OutputColours 
 *	interface simply change ansi String values to empty, "".
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientRecordSystem implements OutputColours {
	private final int MAX_PATIENTS = 100;
	private final int MAX_OBSERVATION_TYPES = 50;
	private final List<Patient> patients;
	private final List<ObservationType> observationTypes;

	// constructor
	public PatientRecordSystem() {
		patients = new ArrayList<Patient>();
		observationTypes = new ArrayList<ObservationType>();
	}

	// public add methods
	public void addPatient(String patientId, String patientName) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if(patientId.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new patient, patientId cannot be empty");
		}
		
		// input error: cannot be empty
		if(patientName.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new patient, patientName cannot be empty");
		}
		
		// input error: cannot be full
		if (patients.size() >= MAX_PATIENTS) {
			throw new InvalidPatientRecordSystemException("cannot add new patient as max patients has been reached");
		}

		// returns -1 if not found, array index number if found
		boolean foundPatient = (findPatient(patientId) != -1);
		if (foundPatient) {
			// input error: id cannot already be used
			throw new InvalidPatientRecordSystemException("cannot add new patient as id already exists");
		}

		/*
		 * It wasn't stated whether or not input names needed to conform to a form
		 * style, so has not been implemented. However, it would be very easy to
		 * strictly format any input name if needed. For example, patientName input
		 * formatted to capital first letter and lower case trailing letters.
		 *
		 * String formatPatientName = patientName.toUpperCase().substring(0,1) +
		 * patientName.toLowerCase().substring(1);
		 *
		 */
		
		patients.add(new Patient(patientId, patientName));
	}

	public void addMeasurementObservationType(String observationCode, String observationName, String unit) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if(observationCode.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation, observationCode cannot be empty");
		}
		
		// input error: cannot be empty
		if(observationName.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation, observationName cannot be empty");
		}
		
		// input error: cannot be empty
		if(unit.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation, unit cannot be empty");
		}
		
		// input error: cannot be full
		if (observationTypes.size() >= MAX_OBSERVATION_TYPES) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation type, maximum types has been reached");
		}
		
		// input error: code cannot already be used
		if (hasObservationType(observationCode)) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation type, code already exists");
		}

		/*
		 * measurement observations have not been set with value range limits, so is
		 * possible to send 'weird' values in all cases. eg. height -10. This is a
		 * fundamental problem within this program predicated by the outline and method
		 * headers provided
		 */

		observationTypes.add(new MeasurementObservationType(observationCode, observationName, unit));
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION TYPE", success);
	}

	public void addCategoryObservationType(String observationCode, String observationName, String[] categories) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if(observationCode.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation, observationCode cannot be empty");
		}
		
		// input error: cannot be empty
		if(observationName.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add new measurement observation, observationName cannot be empty");
		}
		
		// input error: cannot be full
		if (observationTypes.size() >= MAX_OBSERVATION_TYPES) {
			throw new InvalidPatientRecordSystemException("cannot add new category observation type, maximum types has been reached");
		}

		// input error: code cannot already be used
		if (hasObservationType(observationCode)) {
			throw new InvalidPatientRecordSystemException("cannot add new category observation type, code already exists");
		}

		// converts parameter String[] to ArrayList<String> for constructor
		List<String> categoriesList = new ArrayList<String>();
		for (int i = 0; i < categories.length; i++) {
			if(categories[i].length() < 1) {
				// input error: cannot be empty
				throw new InvalidPatientRecordSystemException("cannot add new measurement observation, categories cannot have empty values");
			} else
				categoriesList.add(i, categories[i]);
		}

		observationTypes.add(new CategoryObservationType(observationCode, observationName, categoriesList));
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION TYPE", success);
	}

	public void addMeasurementObservation(String patientId, String observationCode, double value) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if(patientId.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add measurement observation, patientId cannot be empty");
		}
		
		// input error: cannot be empty
		if(observationCode.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add measurement observation, observationCode cannot be empty");
		}
		
		// input error: cannot be empty
		if (patients.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no patients have been added yet");
		}

		// input error: patient must exist
		// returns -1 if not found, array index number if found
		boolean foundPatient = (findPatient(patientId) != -1);
		if (!foundPatient) {
			throw new InvalidPatientRecordSystemException("patient id, not found");
		}

		// create an object reference of the ObservationType
		ObservationType observationType = null;
		if (hasMeasurementObservationType(observationCode)) {
			observationType = getObservationTypeObject(observationCode);
		} else
			// input error: observation type must exist
			throw new InvalidPatientRecordSystemException("observation code, not found");

		/*
		 * measurement observations have not been set with value range limits, so is
		 * possible to send 'weird' values in all cases. eg. height -10. This is a
		 * fundamental problem within this program predicated by the outline and method
		 * headers provided
		 */

		int patientIndex = findPatient(patientId);
		// throws possible Exceptions from Patient Class
		patients.get(patientIndex).addMeasurementObservation(observationType, observationCode, value);
	}

	public void addCategoryObservation(String patientId, String observationCode, String categoryValue) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if(patientId.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add category observation, patientId cannot be empty");
		}
		
		// input error: cannot be empty
		if(observationCode.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add category observation, observationCode cannot be empty");
		}
		
		// input error: cannot be empty
		if(categoryValue.length() < 1) {
			throw new InvalidPatientRecordSystemException("cannot add category observation, categoryValue cannot be empty");
		}
		
		// input error: cannot be empty
		if (patients.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no patients have been added yet");
		}

		// input error: patient must exist
		// returns -1 if not found, array index number if found.
		boolean foundPatient = (findPatient(patientId) != -1);
		if (!foundPatient) {
			throw new InvalidPatientRecordSystemException("patient id, not found");
		}

		// create an object reference of the ObservationType
		ObservationType observationType = null;
		if (hasCategoryObservationType(observationCode)) {
			if (categoryValueExists(observationCode, categoryValue)) {
				observationType = getObservationTypeObject(observationCode);
			} else
				// input error: category value must exist
				throw new InvalidPatientRecordSystemException("category value, not found");
		} else
			// input error: observation type must exist
			throw new InvalidPatientRecordSystemException("observation code, not found");

		int patientIndex = findPatient(patientId);
		// throws possible Exceptions from Patient class
		patients.get(patientIndex).addcategoryObservation(observationType, observationCode, categoryValue);
	}

	// public modify methods
	public void modifyMeasurementObservation(String patientId, String observationCode, double value) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (patients.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no patients have been added yet");
		}

		// input error: patient must exist
		// returns -1 if not found, array index number if found.
		boolean foundPatient = (findPatient(patientId) != -1);
		if (!foundPatient) {
			throw new InvalidPatientRecordSystemException("patient id, not found");
		}

		// input error: observation type must exist
		if (!hasMeasurementObservationType(observationCode)) {
			throw new InvalidPatientRecordSystemException("observation code, not found");
		}

		/*
		 * measurement observations have not been set with value range limits, so is
		 * possible to send 'weird' values in all cases. eg. height -10. This is a
		 * fundamental problem within this program predicated by the outline and method
		 * headers provided
		 */

		int patientIndex = findPatient(patientId);
		// throws possible Exceptions from Patient Class
		patients.get(patientIndex).modifyMeasurementObservation(observationCode, value);
	}

	public void modifyCategoryObservation(String patientId, String observationCode, String value) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (patients.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no patients have been added yet");
		}

		// input error: patient must exist
		// returns -1 if not found, array index number if found.
		boolean foundPatient = (findPatient(patientId) != -1);
		if (!foundPatient) {
			throw new InvalidPatientRecordSystemException("patient id, not found");
		}

		// input error: observation type must exist
		if (!hasCategoryObservationType(observationCode)) {
			throw new InvalidPatientRecordSystemException("observation code, not found");
		}

		// input error: category value must exist
		if (!categoryValueExists(observationCode, value)) {
			throw new InvalidPatientRecordSystemException("category value, not found");
		}

		int patientIndex = findPatient(patientId);
		// throws possible Exceptions from Patient Class
		patients.get(patientIndex).modifyCategoryObservation(observationCode, value);
	}

	// public delete methods
	public void deletePatient(String patientId) throws Exception {
		// input error: cannot be empty
		if (patients.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no patients have been added yet");
		}

		// input error: patient must exist
		// if not found returns -1, and index value if found
		boolean patientFound = (findPatient(patientId) != -1);
		if (!patientFound) {
			throw new InvalidPatientRecordSystemException("patient id, not found");
		}

		int patientIndex = findPatient(patientId);
		// returns copy of deleted object
		patients.remove(patientIndex);
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "REMOVE PATIENT", completed);
	}

	public void deleteObservationType(String observationCode) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (observationTypes.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no observation types have been added yet");
		}

		// input error: observation type code must exist
		if (!hasObservationType(observationCode)) {
			throw new InvalidPatientRecordSystemException("observation code, not found");
		}

		// input error: cannot have observaions
		if (observationTypeHasObservations(observationCode)) {
			throw new InvalidPatientRecordSystemException("cannot remove, observation type has observations");
		}

		int observationTypeIndex = observationTypeIndex(observationCode);
		// returns copy of deleted object
		observationTypes.remove(observationTypeIndex);
		System.out.printf("%-8s %-40s %s \n", "STATUS:", "REMOVE OBSERVATION TYPE", completed);
	}

	public void deleteObservation(String patientId, String observationCode) throws InvalidPatientRecordSystemException {
		// input error: cannot be empty
		if (patients.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no patients have been added yet");
		}

		// input error: patient must exist
		// returns -1 if not found, and index value if found
		boolean patientIdFound = (findPatient(patientId) != -1);
		if (!patientIdFound) {
			throw new InvalidPatientRecordSystemException("patient id, not found");
		}

		// input error: cannot be empty
		if (observationTypes.isEmpty()) {
			throw new InvalidPatientRecordSystemException("no observations types have been added yet");
		}

		// input error: observation type must exist
		if (!hasObservationType(observationCode)) {
			throw new InvalidPatientRecordSystemException("observation type, not found");
		}

		int patientIndex = findPatient(patientId);
		// throws possible Exceptions from Patient Class
		patients.get(patientIndex).deleteObservation(observationCode);
	}

	// public i/o methods
	// all error checks completed within this method, any found displays message to console
	// also, if wanted, could move these two i/o methods to the menu class and change the private
	// save/load methods to public, which would allow for single files to be saved/loaded if necessary.
	public void saveData() {
		System.out.printf("%-8s %s \n", "ACTION:", saveSystem);
		
		// set scope of scanner object
		PrintWriter file = null;
		String fileName = null;
		
		// save measurement observation types
		fileName = "PRS-MeasurementObservationTypes.txt";
		if(getMeasurementObservationTypeSaveData() != null) {
			System.out.printf("%-49s %s \n", "STATUS:  Measurement Observation Types", found);
			try {
				file = new PrintWriter(fileName);
				System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, created);

				// save data to file
				saveMeasurementObservationTypes(file);
				System.out.printf("%-49s %s \n", "STATUS:", saved);
			} catch (FileNotFoundException e) {
				System.out.printf("%-49s %s \n", "STATUS:", notSaved);
				e.printStackTrace();
			} finally {
				if (file != null)
					file.close();
			}
		} else {
			System.out.printf("%-49s %s \n", "STATUS:  Measurement Observation Types", notFound);
			System.out.printf("%-49s %s \n", "STATUS:", notSaved);
		}
		
		// save category observation types
		fileName = "PRS-CategoryObservationTypes.txt";
		if(getCategoryObservationTypeSaveData() != null) {
			System.out.printf("%-49s %s \n", "STATUS:  Category Observation Types", found);
			try {
				file = new PrintWriter(fileName);
				System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, created);

				// save data to file
				saveCategoryObservationTypes(file);
				System.out.printf("%-49s %s \n", "STATUS:", saved);
			} catch (FileNotFoundException e) {
				System.out.printf("%-49s %s \n", "STATUS:", notSaved);
				e.printStackTrace();
			} finally {
				if (file != null)
					file.close();
			}
		} else {
			System.out.printf("%-49s %s \n", "STATUS:  Category Observation Types", notFound);
			System.out.printf("%-49s %s \n", "STATUS:", notSaved);
		}
		
		// save patients
		fileName = "PRS-Patients.txt";
		if(getPatientSaveData() != null) {
			System.out.printf("%-49s %s \n", "STATUS:  Patients", found);
			fileName = "PRS-Patients.txt";
			try {
				file = new PrintWriter(fileName);
				System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, created);

				// save data to file
				savePatients(file);
				System.out.printf("%-49s %s \n", "STATUS:", saved);
			} catch (FileNotFoundException e) {
				System.out.printf("%-49s %s \n", "STATUS:", notSaved);
				e.printStackTrace();
			} finally {
				if (file != null)
					file.close();
			}
		} else {
			System.out.printf("%-49s %s \n", "STATUS:  Patients", notFound);
			System.out.printf("%-49s %s \n", "STATUS:", notSaved);
		}

		// save measurement observations 
		fileName = "PRS-MeasurementObservations.txt";
		if(getMeasurementObservationSaveData() != null) {
			System.out.printf("%-49s %s \n", "STATUS:  Measurement Observations", found);
			fileName = "PRS-MeasurementObservations.txt";
			try {
				file = new PrintWriter(fileName);
				System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, created);

				// save data to file
				saveMeasurementObservations(file);
				System.out.printf("%-49s %s \n", "STATUS:", saved);
			} catch (FileNotFoundException e) {
				System.out.printf("%-49s %s \n", "STATUS:", notSaved);
				e.printStackTrace();
			} finally {
				if (file != null)
					file.close();
			}
		} else {
			System.out.printf("%-49s %s \n", "STATUS:  Measurement Observations", notFound);
			System.out.printf("%-49s %s \n", "STATUS:", notSaved);
		}

		// save category observations
		fileName = "PRS-CategoryObservations.txt";
		if(getCategoryObservationSaveData() != null) {
			System.out.printf("%-49s %s \n", "STATUS:  Category Observations", found);
			try {
				file = new PrintWriter(fileName);
				System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, created);

				// save data to file
				saveCategoryObservations(file);
				System.out.printf("%-49s %s \n", "STATUS:", saved);
			} catch (FileNotFoundException e) {
				System.out.printf("%-49s %s \n", "STATUS:", notSaved);
				e.printStackTrace();
			} finally {
				if (file != null)
					file.close();
			}
		} else {
			System.out.printf("%-49s %s \n", "STATUS:  Category Observations", notFound);
			System.out.printf("%-49s %s \n", "STATUS:", notSaved);
		}
	}
	
	public void loadData() {
		// zero out PRS records data.
		// this may not be suitable if data is needed to be added to existing records.
		// option 1: current system data should be overwritten
		// option 2: current system data should be added to
		
		//clearRecords(); // <------------ if option 2, comment out this line
		
		System.out.printf("%-8s %s \n", "ACTION:", loadSystem);
		
		// set scope of scanner object
		Scanner file = null;
		String fileName = null;
	
		// load measurement observation types
		fileName = "PRS-MeasurementObservationTypes.txt";
		try {
			file = new Scanner(new File(fileName));
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, found);

			// load data to records
			loadMeasurementObservationTypes(file);
			System.out.printf("%-49s %s \n", "STATUS:", completed);
		} catch (FileNotFoundException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, notFound);
			System.out.println(e.toString().substring(8,31) + e.toString().substring(67));
		} finally {
			if (file != null)
				file.close();
		}
		
		// load category observation types
		fileName = "PRS-CategoryObservationTypes.txt";
		try {
			file = new Scanner(new File(fileName));
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, found);

			// load data to records
			loadCategoryObservationTypes(file);
			System.out.printf("%-49s %s \n", "STATUS:", completed);
		} catch (FileNotFoundException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, notFound);
			System.out.println(e.toString().substring(8,31) + e.toString().substring(64));
			if (file != null)
				file.close();
		}

		// load patients
		fileName = "PRS-Patients.txt";
		try {
			file = new Scanner(new File(fileName));
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, found);

			// load data to records
			loadPatients(file);
			System.out.printf("%-49s %s \n", "STATUS:", completed);
		} catch (FileNotFoundException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, notFound);
			System.out.println(e.toString().substring(8,31) + e.toString().substring(48));
		} finally {
			if (file != null)
				file.close();
		}

		// load measurement observations
		fileName = "PRS-MeasurementObservations.txt";
		try {
			file = new Scanner(new File(fileName));
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, found);

			// load data to records
			loadMeasurementObservations(file);
			System.out.printf("%-49s %s \n", "STATUS:", completed);
		} catch (FileNotFoundException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, notFound);
			System.out.println(e.toString().substring(8,31) + e.toString().substring(63));
			} finally {
			if (file != null)
				file.close();
		}

		// load category observations
		fileName = "PRS-CategoryObservations.txt";
		try {
			file = new Scanner(new File(fileName));
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, found);

			// load data to records
			loadCategoryObservations(file);
			System.out.printf("%-49s %s \n", "STATUS:", completed);
		} catch (FileNotFoundException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", fileName, notFound);
			System.out.println(e.toString().substring(8,31) + e.toString().substring(60));
		} finally {
			if (file != null)
				file.close();
		}
	}
	
	// private save methods
	private void saveMeasurementObservationTypes(PrintWriter file) {
		// write to file
		System.out.printf("%-49s %s \n", "STATUS:", writing);
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i) instanceof MeasurementObservationType) {
				file.println(observationTypes.get(i).getCode() + "; " + observationTypes.get(i).getName() + "; "
						+ ((MeasurementObservationType) observationTypes.get(i)).getUnit());
				
			}
		}
		file.close();
	}

	private void saveCategoryObservationTypes(PrintWriter file) {
		// write to file
		System.out.printf("%-49s %s \n", "STATUS:", writing);
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i) instanceof CategoryObservationType) {
				file.print(observationTypes.get(i).getCode() + "; " + observationTypes.get(i).getName() + "; ");
				
				int categoriesSize = ((CategoryObservationType) observationTypes.get(i)).getCategories().size();
				for (int j = 0; j < categoriesSize; j++) {
					file.print(((CategoryObservationType) observationTypes.get(i)).getCategories().get(j));
					if (!(j == categoriesSize - 1)) {
						file.print(", ");
					}
				}
				file.print("\r\n");
			}
		}
		file.close();
	}

	private void savePatients(PrintWriter file) {
		// write to file
		System.out.printf("%-49s %s \n", "STATUS:", writing);
		for (int i = 0; i < patients.size(); i++) {
			file.println(patients.get(i).getId() + "; " + patients.get(i).getName());
			//patientsFound = true;
		}
		file.close();
	}

	private void saveMeasurementObservations(PrintWriter file) {
		// write to file
		System.out.printf("%-49s %s \n", "STATUS:", writing);
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).hasMeasurementObservationTypes()) {
				for (int j = 0; j < patients.get(i).getObservations().size(); j++) {
					if (patients.get(i).getObservations().get(j) instanceof MeasurementObservation) {
						file.println(patients.get(i).getId() + "; " + patients.get(i).getObservations().get(j).getObservationType().getCode() + "; "
								+ ((MeasurementObservation) patients.get(i).getObservations().get(j)).getValue());
						
					}
				}
			}
		}
		file.close();
	}

	private void saveCategoryObservations(PrintWriter file) {
		// write to file
		System.out.printf("%-49s %s \n", "STATUS:", writing);
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).hasCategoryObservationTypes()) {
				for (int j = 0; j < patients.get(i).getObservations().size(); j++) {
					if (patients.get(i).getObservations().get(j) instanceof CategoryObservation) {
						file.println(patients.get(i).getId() + "; " + patients.get(i).getObservations().get(j).getObservationType().getCode() + "; "
								+ ((CategoryObservation) patients.get(i).getObservations().get(j)).getValue());
					}
				}
			}
		}
		file.close();
	}

	// private get data records methods for saving to file methods,
	// returns null when no data found
	private String[] getMeasurementObservationTypeSaveData() {
		// check for data in records and return data if found, return null if not found
		String[] saveData = new String[observationTypes.size()];
		
		boolean found = false;
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i) instanceof MeasurementObservationType) {
				saveData[i] = (observationTypes.get(i).getCode() + "; " + observationTypes.get(i).getName() + "; "
						+ ((MeasurementObservationType) observationTypes.get(i)).getUnit());
				found = true;
			}
		}
		return found? saveData: null;
	}
	
	private String[] getCategoryObservationTypeSaveData() {
		// check for data in records and return data if found, return null if not found
		String[] saveData = new String[observationTypes.size()];
		
		boolean found = false;
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i) instanceof CategoryObservationType) {
				saveData[i] = (observationTypes.get(i).getCode() + "; " + observationTypes.get(i).getName() + "; ");
				found = true;

				int categoriesSize = ((CategoryObservationType) observationTypes.get(i)).getCategories().size();
				for (int j = 0; j < categoriesSize; j++) {
					saveData[i] += ((CategoryObservationType) observationTypes.get(i)).getCategories().get(j);
					if (!(j == categoriesSize - 1)) {
						saveData[i] += ", ";
					}
				}
			}
		}
		return found? saveData: null;
	}
	
	private String[] getPatientSaveData() {
		// check for data in records and return data if found, return null if not found
		String [] saveData =  new String[patients.size()];
		
		boolean found = false;
		for (int i = 0; i < patients.size(); i++) {
			saveData[i] = patients.get(i).getId() + "; " + patients.get(i).getName();
			found = true;
		}
		return found? saveData: null;
	}
	
	private String[] getMeasurementObservationSaveData() {
		// check for data in records and return data if found, return null if not found
		String[] saveData = new String[patients.size()];
				
		boolean found = false;
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).hasMeasurementObservationTypes()) {
				for (int j = 0; j < patients.get(i).getObservations().size(); j++) {
					if (patients.get(i).getObservations().get(j) instanceof MeasurementObservation) {
						saveData[i] = patients.get(i).getId() + "; " + patients.get(i).getObservations().get(j).getObservationType().getCode() + "; "
								+ ((MeasurementObservation) patients.get(i).getObservations().get(j)).getValue();
						found = true;
					}
				}
			}
		}
		return found? saveData: null;
	}
	
	private String[] getCategoryObservationSaveData() {
		// check for data in records and return data if found, return null if not found
		String[] saveData = new String[patients.size()];
		
		boolean found = false;

		// test for records and write to file if found
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).hasCategoryObservationTypes()) {
				for (int j = 0; j < patients.get(i).getObservations().size(); j++) {
					if (patients.get(i).getObservations().get(j) instanceof CategoryObservation) {
						saveData[i] = patients.get(i).getId() + "; " + patients.get(i).getObservations().get(j).getObservationType().getCode() + "; "
								+ ((CategoryObservation) patients.get(i).getObservations().get(j)).getValue();
						found = true;
					}
				}
			}
		}
		return found? saveData: null;
	}
	
	// private load methods
	private void loadMeasurementObservationTypes(Scanner file) {
		// load data from file and check for format errors
		System.out.printf("%-49s %s \n", "STATUS:", loading);

		int count = 1;
		while (file.hasNextLine()) {
			String line = file.nextLine();
			String[] lineTokens = line.split(";");

			// set error output message
			String invalidMessage = "line [" + count + "] is not valid: " + "\"" + line + "\"";

			// test for empty lines, continue if found
			if (line.length() == 0) {
				System.out.printf("%16s %s %s \n",  error, "", "line [" + count + "] is not valid: is empty");
				count++;
				continue;
			}
			
			// test for correct format
			boolean formatIssue = false;
			if (lineTokens.length != 3 || lineTokens[0].trim().length() == 0 || lineTokens[1].trim().length() == 0 || lineTokens[2].trim().length() == 0) {
				formatIssue = true;
			}

			// check format and send to prs records
			if (!formatIssue) {
				try {
					addMeasurementObservationType(lineTokens[0].trim(), lineTokens[1].trim(), lineTokens[2].trim());
				} catch (Exception e) {
					System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION TYPE", setBack);
					System.out.printf("%16s %s %s \n",  error, "", e.toString().substring(7));
				}
			} else {
				System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION TYPE", setBack);
				System.out.printf("%16s %s %s \n",  error, "", invalidMessage);
			}
			count++;
		}
	}

	private void loadCategoryObservationTypes(Scanner file) {
		// load data from file and check for format errors
		System.out.printf("%-49s %s \n", "STATUS:", loading);

		int count = 1;
		while (file.hasNextLine()) {
			String line = file.nextLine();
			String[] lineTokens = line.split(";");

			// set error output message
			String invalidMessage = "line [" + count + "] is not valid: " + "\"" + line + "\"";

			// test for empty lines, continue if found
			if (line.length() == 0) {
				System.out.printf("%16s %s %s \n",  error, "", "line [" + count + "] is not valid: is empty");
				count++;
				continue;
			}
			
			// build categories array, and check for correct format
			if (lineTokens.length == 3) {
				boolean formatIssue = false;
				if (lineTokens[0].trim().length() == 0 || lineTokens[1].trim().length() == 0) {
					formatIssue = true;
				}
				
				String[] categories = lineTokens[2].split(",");
				
				for (int i = 0; i < categories.length; i++) {
					if (categories[i].trim().length() != 0)
						categories[i] = categories[i].trim();
					else {
						formatIssue = true;
						break;
					}
				}
				
				// check format and send to prs records
				if (!formatIssue) {
					try {
						addCategoryObservationType(lineTokens[0].trim(), lineTokens[1].trim(), categories);
					} catch (InvalidPatientRecordSystemException e) {
						System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION TYPE", setBack);
						System.out.printf("%16s %s %s \n",  error, "", e.toString().substring(7));
					}
				} else {
					System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION TYPE", setBack);
					System.out.printf("%16s %s %s \n",  error, "", invalidMessage);
				}
			} else {
				System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION TYPE", setBack);
				System.out.printf("%16s %s %s \n",  error, "", invalidMessage);
			}
			count++;
		}
	}

	private void loadPatients(Scanner file) {
		// load data from file and check for format errors
		System.out.printf("%-49s %s \n", "STATUS:", loading);
		int count = 1;

		while (file.hasNextLine()) {
			String line = file.nextLine();
			String[] lineTokens = line.split(";");
			
			// set error output message
			String invalidMessage = "line [" + count + "] is not valid: " + "\"" + line + "\"";

			// test for empty lines, continue if found
			if (line.length() == 0) {
				System.out.printf("%16s %s %s \n",  error, "", "line [" + count + "] is not valid: is empty");
				count++;
				continue;
			}

			// test for correct format
			boolean formatIssue = false;
			if (lineTokens.length != 2 || (lineTokens[0].trim()).length() == 0 || lineTokens[1].trim().length() == 0) {
				formatIssue = true;
			}

			// check format and send to prs records
			if (!formatIssue) {
				try {
					addPatient(lineTokens[0].trim(), lineTokens[1].trim());
				} catch (Exception e) {
					System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW PATIENT", setBack);
					System.out.printf("%16s %s %s \n",  error, "", e.toString().substring(7));
				}
			} else {
				System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW PATIENT", setBack);
				System.out.printf("%16s %s %s \n",  error, "", invalidMessage);
			}
			count++;
		}
	}

	private void loadMeasurementObservations(Scanner file) {
		// load data from file and check for format errors
		System.out.printf("%-49s %s \n", "STATUS:", loading);
		int count = 1;

		while (file.hasNextLine()) {
			String line = file.nextLine();
			String[] lineTokens = line.split(";");
			
			// set error output message
			String invalidMessage = "line [" + count + "] is not valid: " + "\"" + line + "\"";

			// test for empty lines, continue if found
			if (line.length() == 0) {
				System.out.printf("%16s %s %s \n",  error, "", "line [" + count + "] is not valid: is empty");
				count++;
				continue;
			}

			// test for correct format
			boolean formatIssue = false;
			if (lineTokens.length != 3 || lineTokens[0].trim().length() == 0 || lineTokens[1].trim().length() == 0 || lineTokens[2].trim().length() == 0) {
				formatIssue = true;
			}

			// test that measurement observation value is double
			Double value = null;
			if (!formatIssue) {
				try {
					value = Double.parseDouble(lineTokens[2].trim());
				} catch (NumberFormatException e) {
					System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION", setBack);
					System.out.printf("%16s %s %s %s \n",  error, "", invalidMessage, "("+e.toString().substring(10)+")");
					count++;
					continue;
				}
			}

			// check format and send to prs records
			if (!formatIssue) {
				try {
					addMeasurementObservation(lineTokens[0].trim(), lineTokens[1].trim(), value);
				} catch (Exception e) {
					System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION", setBack);
					System.out.printf("%16s %s %s \n",  error, "", e.toString().substring(7));
				}
			} else {
				System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION", setBack);
				System.out.printf("%16s %s %s \n",  error, "", invalidMessage);
			}
			count++;
		}
	}

	private void loadCategoryObservations(Scanner file) {
		// load data from file and check for format errors
		System.out.printf("%-49s %s \n", "STATUS:", loading);
		int count = 1;

		while (file.hasNextLine()) {
			String line = file.nextLine();
			String[] lineTokens = line.split(";");
			
			// set error output message
			String invalidMessage = "line [" + count + "] is not valid: " + "\"" + line + "\"";

			// test for empty lines, continue if found
			if (line.length() == 0) {
				System.out.printf("%16s %s %s \n",  error, "", "line [" + count + "] is not valid: is empty");
				count++;
				continue;
			}

			// test for correct format
			boolean formatIssue = false;
			if (lineTokens.length != 3 || lineTokens[0].trim().length() == 0 || lineTokens[1].trim().length() == 0 || lineTokens[2].trim().length() == 0) {
				formatIssue = true;
			}

			// check format and send to prs records
			if (!formatIssue) {
				try {
					addCategoryObservation(lineTokens[0].trim(), lineTokens[1].trim(), lineTokens[2].trim());
				} catch (Exception e) {
					System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION", setBack);
					System.out.printf("%16s %s %s \n",  error, "", e.toString().substring(7));
				}
			} else{
				System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION", setBack);
				System.out.printf("%16s %s %s \n",  error, "", invalidMessage);
			}
			count++;
		}
	}
	
	// public getters
	public int getMaxPatients() {
		return MAX_PATIENTS;
	}

	public int getMaxObservationTypes() {
		return MAX_OBSERVATION_TYPES;
	}

	public int getNumberOfPatients() {
		return patients.size();
	}

	public int getNumberOfObservationTypes() {
		return observationTypes.size();
	}
	
	// private getters, to prevent unauthorised access to arrayList objects 
	private ObservationType getObservationTypeObject(String observationCode) {
		// returns null if not found
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i).getCode().equalsIgnoreCase(observationCode)) {
				return observationTypes.get(i);
			}
		}
		return null;
	}

	private List<Patient> getPatients() { return patients; }	
	
	private List<ObservationType> getObservationTypes() { return observationTypes; }
	
	// private delete/clear data method
	private void clearRecords() {
		// zero out PRS records data
		patients.clear();
		observationTypes.clear(); 
	}
	
	// private internal condition check methods. 
	// set private as there isn't a need for outside access. 
	// however, technically no risk is posed by having set public.
	private int findPatient(String patientId) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getId().equalsIgnoreCase(patientId)) {
				return i;
			}
		}
		return -1;
	}

	private boolean hasMeasurementObservationTypes() {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i) instanceof MeasurementObservationType) {
				return true;
			}
		}
		return false;
	}

	private boolean hasCategoryObservationTypes() {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i) instanceof CategoryObservationType) {
				return true;
			}
		}
		return false;
	}

	private boolean hasMeasurementObservationType(String observationCode) {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i).getCode().equalsIgnoreCase(observationCode)
					&& observationTypes.get(i) instanceof MeasurementObservationType) {
				return true;
			}
		}
		return false;
	}

	private boolean hasCategoryObservationType(String observationCode) {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i).getCode().equalsIgnoreCase(observationCode)
					&& observationTypes.get(i) instanceof CategoryObservationType) {
				return true;
			}
		}
		return false;
	}

	private boolean categoryValueExists(String observationCode, String categoryValue) {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i).getCode().equalsIgnoreCase(observationCode)
					&& observationTypes.get(i) instanceof CategoryObservationType) {
				for (int j = 0; j < ((CategoryObservationType) observationTypes.get(i)).getCategories().size(); j++) {
					if (((CategoryObservationType) observationTypes.get(i)).getCategories().get(j)
							.equalsIgnoreCase(categoryValue)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean hasObservationType(String observationCode) {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i).getCode().equalsIgnoreCase(observationCode)) {
				return true;
			}
		}
		return false;
	}

	// public internal condition check methods.
	public boolean observationTypeHasObservations(String observationCode) {
		for (int i = 0; i < patients.size(); i++) {
			for (int j = 0; j < patients.get(i).getObservations().size(); j++) {
				if (patients.get(i).getObservations().get(j).getObservationType().getCode()
						.equalsIgnoreCase(observationCode)) {
					return true;
				}
			}
		}
		return false;
	}

	public int observationTypeIndex(String observationCode) {
		for (int i = 0; i < observationTypes.size(); i++) {
			if (observationTypes.get(i).getCode().equalsIgnoreCase(observationCode)) {
				return i;
			}
		}
		return -1;
	}
	
	// Private display methods implemented in toString() method
	private String displayCategoryObservationTypes() {
		String categoryTypeDisplay = "Category Observation Types:\n";

		// Collect info and set boolean
		int categoryNumber = 1;
		String categoryTypeInfo = "";
		boolean categoryTypesFound = false;
		for (int i = 0; i < observationTypes.size(); i++) {
			if (this.observationTypes.get(i) instanceof CategoryObservationType) {
				categoryTypeInfo += "[" + categoryNumber++ + "]" + observationTypes.get(i).toString() + "\n";
				categoryTypesFound = true;
			}
		}

		// Display logic
		if (categoryTypesFound) {
			categoryTypeDisplay += categoryTypeInfo;
		} else {
			categoryTypeDisplay += "\n" + noCOTypesDisplay + "\n";
		}
		return categoryTypeDisplay;
	}

	private String displayMeasurementObservationTypes() {
		String measurementTypesDisplay = "Measurement Observation Types:\n";

		// Measurement observation type start number
		int measurementNumber = 1;

		// Collect info and set boolean
		String measurementTypeInfo = "";
		boolean measurementTypesFound = false;
		for (int i = 0; i < observationTypes.size(); i++) {
			if (this.observationTypes.get(i) instanceof MeasurementObservationType) {
				measurementTypeInfo += "[" + measurementNumber++ + "]" + observationTypes.get(i).toString() + "\n";
				measurementTypesFound = true;
			}
		}

		// Display logic
		if (measurementTypesFound) {
			measurementTypesDisplay += measurementTypeInfo;
		} else {
			measurementTypesDisplay += "\n" + noMOTypesDisplay + "\n\n";
		}
		return measurementTypesDisplay;
	}

	private String displayObservationTypes() {
		// Header
		String observationTypesDisplay = "+-------------------------+\n" 
									   + "|    Observation Types    |\n"
									   + "+-------------------------+\n";

		// Display logic
		if (hasCategoryObservationTypes()) {
			observationTypesDisplay += displayCategoryObservationTypes();
		} else
			observationTypesDisplay += "\n" + noCOTypesDisplay + "\n\n";
		if (hasMeasurementObservationTypes()) {
			if (hasCategoryObservationTypes()) {
				observationTypesDisplay += "\n" + displayMeasurementObservationTypes();
			} else
				observationTypesDisplay += displayMeasurementObservationTypes();
		} else {
			if (hasCategoryObservationTypes()) {
				observationTypesDisplay += "\n" + noMOTypesDisplay + "\n\n";
			} else
				observationTypesDisplay += noMOTypesDisplay + "\n\n";
		}
		return observationTypesDisplay;
	}

	private String displayPatients() {
		// Header
		String patientsDisplay = "+-------------------------+\n" 
							   + "|        Patients         |\n"
							   + "+-------------------------+\n";

		// Collect info and set boolean
		int patientNumber = 1;
		boolean patientsFound = false;
		for (int i = 0; i < patients.size(); i++) {
			patientsDisplay += "Patient[" + patientNumber++ + "]" + "\n" + patients.get(i).toString() + "\n";
			patientsFound = true;
		}

		if (!patientsFound) {
			patientsDisplay += "\n" + noPatientDisplay + "\n\n";
		}
		return patientsDisplay;
	}

	// Public display methods
	public String displayObservationType(String code) {
		// Header
		String observationTypeDisplay = "+-------------------------+\n" 
									  + "|    Observation Type     |\n"
									  + "+-------------------------+\n";

		for (int i = 0; i < observationTypes.size(); i++) {
			if (this.observationTypes.get(i).getCode().equalsIgnoreCase(code)) {
				if (this.observationTypes.get(i) instanceof CategoryObservationType) {
					observationTypeDisplay += "Category Type Observation:\n" + observationTypes.get(i).toString() + "\n";
					return observationTypeDisplay;
				} else if (this.observationTypes.get(i) instanceof MeasurementObservationType) {
					observationTypeDisplay += "Measurement Type Observation:\n" + observationTypes.get(i).toString() + "\n";
					return observationTypeDisplay;
				}
			}
		}
		return "observation code [" + code + "] not found";
	}

	public String displayPatient(String patientId) {
		// Header
		String patientDisplay = "+-------------------------+\n" 
							  + "|         Patient         |\n"
							  + "+-------------------------+\n";

		for (int i = 0; i < patients.size(); i++) {
			if (this.patients.get(i).getId().equalsIgnoreCase(patientId)) {
				return patientDisplay += patients.get(i).toString();
			}
		}
		return "patient Id [" + patientId + "] not found";
	}
	
	public String toString() {
		// Header
		String displayOutput = "+-----------------------------------------------+\n" 
							 + "| - - - - - -  " + getClass().getName() + "  - - - - - - |\n" 
							 + "+-----------------------------------------------+\n";

		// Collect info and concat
		displayOutput += displayObservationTypes();
		displayOutput += displayPatients();

		// Display output and concat end header
		return displayOutput + "+-----------------------------------------------+\n"
							 + "| - - - - - - - - -  END  - - - - - - - - - - - |\n"
							 + "+-----------------------------------------------+";
	}
}
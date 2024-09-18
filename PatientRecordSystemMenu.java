
/*	
 *	PatientRecordSystemMenu.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 17.05.2018
 *	Version 1.2
 *
 *	---class outline---
 *	This class implements a front-end menu for the PatientRecordSystem
 *	program. Allowing the user to cycle through basic functions of the
 *	PatientRecordSystem such as: 
 *		1) adding:	observation types, 
 *					patients,
 *					patient observations.
 *		2) displaying: specific observation type, 
 *					   specific patient record.
 *		3) i/o: loading from file,
 *				saving to from.
 * 	
 *	The menu uses loops and data checks to ensure that user inputs
 *	relevant data for the menu to function correctly and to prevent
 *	unintended system crashes. The menu loops until user chooses to exit 
 *	the program
 *
 *	---user experience---
 *	Once the menu has been displayed the user, the program runs an input
 * 	validation method to confirm that any choice the user makes conforms
 *	to the menu choice restrictions. Once the user has input a valid choice
 *	the program runs the process method, which uses a switch statement to
 *	pipe the control flow to the correct method respective to the users
 *	choice. If the method chosen requires the user to input further data,
 *	then a relevant input validation method is called depending on the 
 *	required data type restriction. Once any other necessary user data
 *	input has been collected (via validation methods) then the program 
 *	calls the relevant method for the PatientRecordSystem, and utilising
 *	Exception checking where necessary. Once the call to the 
 *	PatientRecordSystem has been completed, the screen pauses to allow time 
 *	for any output status messages that may have been printed by the 
 *	PatientRecordSystem to be read, and the user can return to the main 
 *	menu display by pressing the ENTER key. The menu will loop indefinitely, 
 *	or until the user chooses to exit by pressing the exit option in the menu.
 *	
 *	---further functionality not utilised---
 *  The PatientRecordSystem class has more functionality than is currently 
 *  represented by the menu. Functions such as deleting, and modifying have 
 *  been omitted from the menu as the assignment outline did not include them, 
 *  however they have been written into the base system and can be implemented 
 *  at any time.
 *
 */

import java.util.Scanner;

public class PatientRecordSystemMenu implements OutputColours {
	private PatientRecordSystem prs;
	private Scanner kb;
	private char choice = ' ';
	
	// menu choice attributes
	private final char ADD_MEASUREMENT_TYPE = '1';
	private final char ADD_CATEGORY_TYPE = '2';
	private final char ADD_PATIENT = '3';
	private final char ADD_MEASUREMENT_OBSERVATION = '4';
	private final char ADD_CATEGORY_OBSERVATION = '5';
	private final char RETRIEVE_OBSERVATION_TYPE = '6';
	private final char RETRIEVE_PATIENT_RECORD = '7';
	private final char SAVE_TO_FILE = '8';
	private final char LOAD_FROM_FILE = '9';
	private final char DISPLAY = 'd';
	private final char EXIT = 'x';

	// constructor
	public PatientRecordSystemMenu() {
		this.kb = new Scanner(System.in);
		this.prs = new PatientRecordSystem();
	}
	
	// main menu display and operation method
	private void run() {
		System.out.println("==========================\n" 
						 + "Patient Record System Menu\n" 
						 + "==========================\n");
		System.out.println(ADD_MEASUREMENT_TYPE + 			". Add a measurement observation type");
		System.out.println(ADD_CATEGORY_TYPE + 				". Add a category observation type");
		System.out.println(ADD_PATIENT + 					". Add a patient");
		System.out.println(ADD_MEASUREMENT_OBSERVATION +	". Add a measurement observation");
		System.out.println(ADD_CATEGORY_OBSERVATION + 		". Add a category observation");
		System.out.println(RETRIEVE_OBSERVATION_TYPE + 		". Display details of an observation type");
		System.out.println(RETRIEVE_PATIENT_RECORD + 		". Display a patient record by the patient id");
		System.out.println(SAVE_TO_FILE + 					". Save data");
		System.out.println(LOAD_FROM_FILE + 				". Load data");
		System.out.println(DISPLAY + 						". Display all data for inspection");
		System.out.println(EXIT + 							". Exit");
		System.out.print("Please enter an option (1-9 or d or x): ");
		
		userMenuInput();
	}

	// user input validation methods
	private void userMenuInput() {			
			while (choice != 'x') {
			String input = kb.nextLine().toLowerCase().trim();
			if (input.length() == 1) {
				choice = input.charAt(0);
				
				if (choice == 'x') {continue;}
				
				process(choice);
				run();
			} else {
				System.out.println("Input is out of menu range. Try again.");
				System.out.print("Please enter an option (1-9 or d or x): ");
			}
		}
	}
	
	private String userInputString(String message) {
		while (true) {
			System.out.print(message);
			String input = kb.nextLine().toLowerCase().trim();

			if (input.isEmpty()) {
				System.out.println("Invalid input. Try again.");
			} else
				return input;
		}
	}

	private int userInputInt(String message) {
		while (true) {
			System.out.print(message);
			String input = kb.nextLine().toLowerCase().trim();

			try {
				int number = Integer.parseInt(input);
				return number;
			} catch (NumberFormatException e) {
				System.out.println(e.toString().substring(10));
				System.out.println("Invalid input. Try again.");
			}
		}
	}

	private double userInputDouble(String message) {
		while (true) {
			System.out.print(message);
			String input = kb.nextLine().toLowerCase().trim();

			try {
				double number = Double.parseDouble(input);
				return number;
			} catch (NumberFormatException e) {
				System.out.println(e.toString().substring(10));
				System.out.println("Invalid input. Try again.");
			}
		}
	}

	private String[] userInputStringArray(int size) {
		String[] categories = new String[size];

		for (int i = 0; i < categories.length; i++) {
			categories[i] = userInputString("Enter category value [" + (i + 1) + "]: ");
		}
		return categories;
	}
	
	// menu methods switch selector 
	private void process(char choice) {
		switch (choice) {
		case ADD_MEASUREMENT_TYPE:
			addMeasurementType();
			break;
		case ADD_CATEGORY_TYPE:
			addCategoryType();
			break;
		case ADD_PATIENT:
			addPatient();
			break;
		case ADD_MEASUREMENT_OBSERVATION:
			addMeasurementObservation();
			break;
		case ADD_CATEGORY_OBSERVATION:
			addCategoryObservation();
			break;
		case RETRIEVE_OBSERVATION_TYPE:
			retrieveObservationType();
			break;
		case RETRIEVE_PATIENT_RECORD:
			retrivePatientRecord();
			break;
		case SAVE_TO_FILE:
			saveToFile();
			break;
		case LOAD_FROM_FILE:
			loadFromFile();
			break;
		case DISPLAY:
			displayAll();
			break;
		case EXIT:
			// END PROGRAM
			System.out.println("exiting. . .");
			break;
		default:
			System.out.println("Input is out of menu range. Try again.");
			break;
		}
	}

	// menu methods
	private void addMeasurementType() {
		// collect and test user input values
		String code = userInputString("Enter observation type code: ");
		String name = userInputString("Enter observation type name: ");
		String unit = userInputString("Enter observation type unit: ");

		// user input tested and validated, now sending to PRS method
		try {
			prs.addMeasurementObservationType(code, name, unit);
		} catch (InvalidPatientRecordSystemException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION TYPE", setBack);
			System.out.println(e.toString().substring(7));
		} finally {
			System.out.println("Press ENTER to continue. . .");
			kb.nextLine();
		}
	}

	private void addCategoryType() {
		// collect and test user input values
		String code = userInputString("Enter observation type code: ");
		String name = userInputString("Enter observation type name: ");
		int numOfValues = userInputInt("Enter the number of categories: ");
		String[] categories = userInputStringArray(numOfValues);

		// user input tested and validated, now sending to PRS method
		try {
			prs.addCategoryObservationType(code, name, categories);
		} catch (InvalidPatientRecordSystemException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION TYPE", setBack);
			System.out.println(e.toString().substring(7));
		} finally {
			System.out.println("Press ENTER to continue. . .");
			kb.nextLine();
		}
	}

	private void addPatient() {
		// collect and test user input values
		String patientId = userInputString("Enter patient ID: ");
		String name = userInputString("Enter patient Name: ");

		// user input tested and validated, now sending to PRS method
		try {
			prs.addPatient(patientId, name);
		} catch (InvalidPatientRecordSystemException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW PATIENT", setBack);
			System.out.println(e.toString().substring(7));
		} finally {
			System.out.println("Press ENTER to continue. . .");
			kb.nextLine();
		}
	}

	private void addMeasurementObservation() {
		// collect and test user input values
		String patientId = userInputString("Enter patient ID: ");
		String observationCode = userInputString("Enter observation type code: ");
		double value = userInputDouble("Enter observation type value: ");

		// user input tested and validated, now sending to PRS method
		try {
			prs.addMeasurementObservation(patientId, observationCode, value);
		} catch (InvalidPatientRecordSystemException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW MEASUREMENT OBSERVATION", setBack);
			System.out.println(e.toString().substring(7));
		} finally {
			System.out.println("Press ENTER to continue. . .");
			kb.nextLine();
		}
	}

	private void addCategoryObservation() {
		// collect and test user input values
		String patientId = userInputString("Enter patient ID: ");
		String observationCode = userInputString("Enter observation type code: ");
		String categoryValue = userInputString("Enter observation type value: ");

		// user input tested and validated, now sending to PRS method
		try {
			prs.addCategoryObservation(patientId, observationCode, categoryValue);
		} catch (InvalidPatientRecordSystemException e) {
			System.out.printf("%-8s %-40s %s \n", "STATUS:", "ADD NEW CATEGORY OBSERVATION", setBack);
			System.out.println(e.toString().substring(7));
		} finally {
			System.out.println("Press ENTER to continue. . .");
			kb.nextLine();
		}
	}

	private void retrieveObservationType() {
		// collect and test user input values
		String observationCode = userInputString("Enter observation type code: ");

		// user input tested and validated, now sending to PRS method
		System.out.println(prs.displayObservationType(observationCode));
		System.out.println("Press ENTER to continue. . .");
		kb.nextLine();
	}

	private void retrivePatientRecord() {
		// collect and test user input values
		String patientId = userInputString("Enter patient ID: ");

		// user input tested and validated, now sending to PRS method
		System.out.println(prs.displayPatient(patientId));
		System.out.println("Press ENTER to continue. . .");
		kb.nextLine();
	}

	private void saveToFile() {
		prs.saveData();	
		System.out.println("Press ENTER to continue. . .");
		kb.nextLine();
	}

	private void loadFromFile() {
		prs.loadData();
		System.out.println("Press ENTER to continue. . .");
		kb.nextLine();
	}

	private void displayAll() {
		System.out.println(prs);
		System.out.println("Press ENTER to continue. . .");
		kb.nextLine();
	} 
	
	// main method
	public static void main(String[] args) {
		PatientRecordSystemMenu menu = new PatientRecordSystemMenu();
		menu.run();
	}
}
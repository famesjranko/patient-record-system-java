# Patient Record System

This project is a Java-based application designed to manage a patient record system. It allows users to create, manage, and save patient records with various observation types, such as measurements and category-based observations. The system provides a menu-based interface to navigate through various functions and supports saving and loading data for persistence.

## Project Structure

- **CategoryObservation.java**: Handles category-based observations for patients.
- **CategoryObservationType.java**: Manages the types of category-based observations.
- **InvalidPatientRecordSystemException.java**: Defines a custom exception class used in the system.
- **MeasurementObservation.java**: Stores patient observations based on measurable data (e.g., height, weight).
- **MeasurementObservationType.java**: Defines the types for measurement-based observations.
- **Observation.java**: Represents a general observation for a patient.
- **ObservationType.java**: Manages the types of observations (both category-based and measurement-based).
- **OutputColours.java**: Contains utilities for displaying output in different colors in the terminal.
- **Patient.java**: Manages patient information such as ID and personal data.
- **PatientRecordSystem.java**: Implements the core functionality of the system, including patient and observation management.
- **PatientRecordSystemMenu.java**: Displays the text-based menu and handles user interaction.
- **PatientRecordSystemTester.java**: Provides basic testing and verification for the patient record system.
- **SaveLoadTester.java**: Implements testing for saving and loading functionality.
- **UnitTester.java**: Tests specific parts of the system.

## Features

- **Add Observation Types**: The system allows adding different types of observations, either measurement-based or category-based.
- **Manage Patient Records**: Create new patient records, add observations to patients, and view patient records by ID.
- **Save/Load Data**: The system supports saving and loading patient data to/from a file, ensuring persistence between sessions.
- **Display Data**: View all current data in the system for easy inspection.

## How to Run

### Prerequisites

- You will need **Java** installed to run the program. You can install Java in a WSL environment with the following commands:
  ```bash
  sudo apt update
  sudo apt install default-jdk
  ```
- Ensure all the Java files are compiled:
  ```bash
  javac *.java
  ```

### Running the Program

To start the Patient Record System:

1. Navigate to the directory containing the Java files.
2. Run the `PatientRecordSystemMenu` class:
   ```bash
   java PatientRecordSystemMenu
   ```

You will be presented with the following menu:
```
==========================
Patient Record System Menu
==========================

1. Add a measurement observation type
2. Add a category observation type
3. Add a patient
4. Add a measurement observation
5. Add a category observation
6. Display details of an observation type
7. Display a patient record by the patient id
8. Save data
9. Load data
d. Display all data for inspection
x. Exit
```

Select an option by entering the corresponding number or letter.

### Menu Options

- **1, 2**: Add a new observation type (either measurement-based or category-based).
- **3**: Add a new patient.
- **4, 5**: Add an observation to a patient (either measurement-based or category-based).
- **6**: Display details about an observation type.
- **7**: View a patient's record by entering their patient ID.
- **8**: Save the current patient records and observation types to a file.
- **9**: Load previously saved patient records and observation types from a file.
- **d**: Display all saved data (for debugging or review).
- **x**: Exit the system.

### Data Persistence

- **Save data**: When option 8 is selected, the program saves all patient data and observation types to a file.
- **Load data**: Option 9 restores data from a previously saved state, ensuring that no data is lost between sessions.

## Testing

You can run the following testers to verify specific parts of the system:

1. **UnitTester.java**: Tests individual units or functionalities within the system.
   ```bash
   java UnitTester
   ```

2. **SaveLoadTester.java**: Tests the save/load functionality to ensure persistence works correctly.
   ```bash
   java SaveLoadTester
   ```

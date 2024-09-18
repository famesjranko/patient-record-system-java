import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class UnitTester {
	// set array limits
	private int observationTypeMax = 50;
	private int patientMax = 100;
	private int observationMax = 20;
	
	// global test result
	private String test1 = null;
	private String test2 = null;
	private String test3 = null;
	private String test4 = null;
	private String test5 = null;
	private String test6 = null;
	private String test7 = null;
	private String test8 = null;
	private String test9 = null;
	private String test10 = null;
	private String test11 = null;
	private String test12 = null;
	private String test13 = null;
	private String test14 = null;

	public UnitTester() {
	}

	public static void main(String[] args) {
		UnitTester test = new UnitTester();
		
		// create patient record system object
		test.testInit();
		// test saving and loading conditions
		test.loadSaveTest();
		// test file formatting
		test.loadTestFormat();
		// test both add observation methods
		test.testAddObservationTypes();
		// test the add patient method, incl empty values
		test.testAddPatients();
		// tests both add observation methods, incl empty values
		test.testAddObservations();
		// checks all methods for case sensitivity
		test.caseSensitivity();
		/*
		 * test array boundaries
		 * method 1 tests all three boundaries, but if any errors you can test each
		 * independently by commenting out 1, and uncomment 2 and 3, to test 2 and 3 individually
		 * 
		 */
		// 1
		test.testObservationArrayBoundary();
		// 2
		//test.testObservationTypeArrayBoundary();
		// 3
		//test.testPatientArrayBoundary();
	}

	public void testInit() {
		// System.out.println("[action: create patient record system object]");
		try {
			PatientRecordSystem prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}
		
		// print results
		System.out.println("[testInit(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object\n");
		System.out.println();
	}

	public void testObservationTypeArrayBoundary() {
		// create patient record system object
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// check observation type array boundary
		System.out.println();
		System.out.println("[action: fill observation type array fully with valids and test boundary]");
		System.out.println("observationTypeMax array currently set at: " + observationTypeMax);

		String id = null;
		String name = null;
		String unit = null;
		int i = 0;

		try {
			for (;;) {
				id = String.format("%s%d", "obs", i);
				name = String.format("%s%d", "name", i);
				unit = String.format("%s%d", "unit", i);
				prs.addMeasurementObservationType(id, name, unit);
				i++;
			}
		} catch (Exception e) {
			if (observationTypeMax == i) {
				test2 = "SUCCESS";
				System.out.println("exited at index: " + i);
			} else {
				test2 = "FAIL";
				System.out.println("failed at index: " + i);
				e.printStackTrace();
			}
		}

		// print results
		System.out.println("\n[testObservationTypeArrayBoundary(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object\n");
		System.out.printf("%-8s %s", test2, " ---- fill observation type array fully with valids and test boundary\n");
		System.out.println();
	}

	public void testPatientArrayBoundary() {
		// create patient record system object
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// check patient array boundary
		System.out.println();
		System.out.println("[action: fill patient array fully with valids and test boundary]");
		System.out.println("patientMax array currently set at index: " + patientMax);

		String id = null;
		String name = null;
		int i = 0;

		try {
			for (;;) {
				id = String.format("%c%d", 'p', i);
				name = String.format("%s%d", "name", i);
				prs.addPatient(id, name);
				i++;
			}
		} catch (Exception e) {
			if (patientMax == i) {
				test2 = "SUCCESS";
				System.out.println("exited at index: " + i);
			} else {
				test2 = "FAIL";
				System.out.println("failed at index: " + i);
				e.printStackTrace();
			}
		}

		// print results
		System.out.println("\n[testPatientArrayBoundary(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object\n");
		System.out.printf("%-8s %s", test2, " ---- fill patient array fully with valids and test boundary\n");
		System.out.println();
	}

	public void testObservationArrayBoundary() {
		// create patient record system object
		// System.out.println("[action: create patient record system object]");
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// fill observation type array and check boundary
		System.out.println();
		System.out.println("[action: fill observation type array fully with valids and test boundary]");
		System.out.println("observationTypeMax array currently set at: " + observationTypeMax);

		int i = 0;
		try {
			String observationTypeId = null;
			String name = null;
			String unit = null;

			for (;;) {
				observationTypeId = String.format("%s%d", "obs", i);
				name = String.format("%s%d", "name", i);
				unit = String.format("%s%d", "unit", i);
				prs.addMeasurementObservationType(observationTypeId, name, unit);
				i++;
			}
		} catch (Exception e) {
			if (observationTypeMax == i) {
				test2 = "SUCCESS";
				System.out.println("exited at index: " + i);
			} else {
				test2 = "FAIL";
				System.out.println("failed at index: " + i);
				e.printStackTrace();
			}
		}

		// fill patient array and check boundary
		System.out.println();
		System.out.println("[action: fill patient array fully with valids and test boundary]");
		System.out.println("patientMax array currently set at index: " + patientMax);

		String patientId = null;
		String patientName = null;
		i = 0;

		try {
			for (;;) {
				patientId = String.format("%c%d", 'p', i);
				patientName = String.format("%s%d", "name", i);
				prs.addPatient(patientId, patientName);
				i++;
			}
		} catch (Exception e) {
			if (patientMax == i) {
				test3 = "SUCCESS";
				System.out.println("exited at index: " + i);
			} else {
				test3 = "FAIL";
				System.out.println("failed at index: " + i);
				e.printStackTrace();
			}
		}

		// check observation array boundary
		System.out.println();
		System.out.println("[action: fill observation array fully with valids and test boundary]");
		System.out.println("observationMax array currently set at index: " + observationMax);

		i = 0;
		try {
			patientId = null;
			String observationTypeId = null;

			for (;;) {
				patientId = String.format("%c%d", 'p', i);
				observationTypeId = String.format("%s%d", "obs", i);
				prs.addMeasurementObservation("p1", observationTypeId, i);
				i++;
			}
		} catch (Exception e) {
			if (observationMax == i) {
				test4 = "SUCCESS";
				System.out.println("exited at index: " + i);
			} else {
				test4 = "FAIL";
				System.out.println("failed at index: " + i);
				e.printStackTrace();
			}
		}

		// print results
		System.out.println("\n[testObservationArrayBoundary(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object\n");
		System.out.printf("%-8s %s", test2, " ---- fill observation type array fully with valids and test boundary\n");
		System.out.printf("%-8s %s", test3, " ---- fill patient array fully with valids and test boundary\n");
		System.out.printf("%-8s %s", test4, " ---- fill observation array fully with valids and test boundary\n");
	}

	public void testAddObservationTypes() {
		// create patient record system object
		PatientRecordSystem prs = null;

		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// add a measurement observation type with valids
		try {
			prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
			test2 = "SUCCESS";
		} catch (Exception e) {
			test2 = "FAIL";
			e.printStackTrace();
		}

		// invalid request: id already used
		try {
			prs.addMeasurementObservationType("T100", "Height", "cm");
			test3 = "FAIL";
		} catch (Exception e) {
			test3 = "SUCCESS";
		}

		// add a category observation type with valids
		try {
			String[] categories = { "Group A", "Group B1", "Group B2" };
			prs.addCategoryObservationType("T200", "blood type", categories);
			test4 = "SUCCESS";
		} catch (Exception e) {
			test4 = "FAIL";
			e.printStackTrace();
		}

		// invalid request: id already used
		try {
			String[] categories = { "Group A", "Group B1", "Group B2" };
			prs.addCategoryObservationType("T200", "blood type", categories);
			test5 = "FAIL";
		} catch (Exception e) {
			test5 = "SUCCESS";
		}

		// invalid request: id empty
		try {
			String[] categories = { "Group A", "Group B1", "Group B2" };
			prs.addCategoryObservationType("", "blood type", categories);
			test6 = "FAIL";
		} catch (Exception e) {
			test6 = "SUCCESS";
		}

		// invalid request: name empty
		try {
			String[] categories = { "Group A", "Group B1", "Group B2" };
			prs.addCategoryObservationType("T600", "", categories);
			test7 = "FAIL";
		} catch (Exception e) {
			test7 = "SUCCESS";
		}

		// invalid request: categories empty String[]
		try {
			String[] categories = { "", "" };
			prs.addCategoryObservationType("T700", "blood type", categories);
			test8 = "FAIL";
		} catch (Exception e) {
			test8 = "SUCCESS";
		}

		// print results
		System.out.println("\n[testAddObservationTypes(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object\n");
		System.out.printf("%-8s %s", test2, " ---- add a measurement observation type with valids\n");
		System.out.printf("%-8s %s", test3, " ---- add a measurement observation type with invalids: id already used\n");
		System.out.printf("%-8s %s", test4, " ---- add a category observation type with valids\n");
		System.out.printf("%-8s %s", test5, " ---- add a category observation type with invalids: id already used\n");
		System.out.printf("%-8s %s", test6, " ---- add a category observation type with invalids: id is empty String\n");
		System.out.printf("%-8s %s", test7, " ---- add a category observation type with invalids: name is empty String\n");
		System.out.printf("%-8s %s", test8, " ---- add a category observation type with invalids: categories array has empty Strings\n");
		System.out.println();
	}

	public void testAddPatients() {
		// create patient record system object
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// add a new patient with valids
		try {
			prs.addPatient("P100", "Smith");
			test2 = "SUCCESS";
		} catch (Exception e) {
			test2 = "FAIL";
			e.printStackTrace();
		}

		// add another patient with valids
		try {
			prs.addPatient("P200", "Adams");
			test3 = "SUCCESS";
		} catch (Exception e) {
			test3 = "FAIL";
			e.printStackTrace();
		}

		// invalid request: id already used
		try {
			prs.addPatient("P200", "Blake");
			test4 = "FAIL";
		} catch (Exception e) {
			test4 = "SUCCESS";
		}

		// invalid request: empty id
		try {
			prs.addPatient("", "Blake");
			test5 = "FAIL";
		} catch (Exception e) {
			test5 = "SUCCESS";
		}

		// invalid request: empty name
		try {
			prs.addPatient("P300", "");
			test6 = "FAIL";
		} catch (Exception e) {
			test6 = "SUCCESS";
		}

		// print results
		System.out.println("\n[ testAddPatients(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object\n");
		System.out.printf("%-8s %s", test2, " ---- add a patient with valids\n");
		System.out.printf("%-8s %s", test3, " ---- add a patient with valids\n");
		System.out.printf("%-8s %s", test4, " ---- add a patient with invalids: id already used\n");
		System.out.printf("%-8s %s", test5, " ---- add a patient with invalids: empty id\n");
		System.out.printf("%-8s %s", test6, " ---- add a patient with invalids: empty name\n");
		System.out.println();
	}

	public void testAddObservations() {
		// create patient record system object
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// add 2 observation types with valids
		try {
			prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
			String[] categories = { "Group A", "Group B1", "Group B2" };
			prs.addCategoryObservationType("T200", "blood type", categories);
			test2 = "SUCCESS";
		} catch (Exception e) {
			test2 = "FAIL";
			e.printStackTrace();
		}

		// add 4 new patient with valids
		try {
			prs.addPatient("P100", "Smith");
			prs.addPatient("P200", "West");
			prs.addPatient("P300", "Davis");
			prs.addPatient("P400", "Wyat");
			test3 = "SUCCESS";
		} catch (Exception e) {
			test3 = "FAIL";
			e.printStackTrace();
		}

		// add a measurement observation with valids
		try {
			prs.addMeasurementObservation("P100", "T100", 120);
			test4 = "SUCCESS";
		} catch (Exception e) {
			test4 = "FAIL";
			e.printStackTrace();
		}

		// add a category observation with valids
		try {
			prs.addCategoryObservation("P100", "T200", "Group A");
			test5 = "SUCCESS";
		} catch (Exception e) {
			test5 = "FAIL";
			e.printStackTrace();
		}

		// invalid request: patient already has observation of the type
		try {
			prs.addMeasurementObservation("P100", "T100", 140);
			test6 = "FAIL";
		} catch (Exception e) {
			test6 = "SUCCESS";
		}

		// add a category observation with invalids: invalid category value
		try {
			prs.addCategoryObservation("P200", "T200", "Group D");
			test7 = "FAIL";
		} catch (Exception e) {
			test7 = "SUCCESS";
		}

		// invalid request: patient already has observation of the type
		try {
			prs.addMeasurementObservation("none", "T100", 140);
			test8 = "FAIL";
		} catch (Exception e) {
			test8 = "SUCCESS";
		}

		// invalid request: invalid category value
		try {
			prs.addCategoryObservation("none", "T200", "Group A");
			test9 = "FAIL";
		} catch (Exception e) {
			test9 = "SUCCESS";
		}

		// invalid request: category observation empty entries
		try {
			prs.addCategoryObservation("", "T200", "Group A");
			test10 = "FAIL";
		} catch (Exception e) {
			test10 = "SUCCESS";
		}
		// invalid request: invalid category value
		try {
			prs.addCategoryObservation("P300", "", "Group A");
			test11 = "FAIL";
		} catch (Exception e) {
			test11 = "SUCCESS";
		}

		// invalid request: invalid category value
		try {
			prs.addCategoryObservation("P300", "T200", "");
			test12 = "FAIL";
		} catch (Exception e) {
			test12 = "SUCCESS";
		}

		// invalid request: measurement observation empty entries
		try {
			prs.addMeasurementObservation("", "T100", 120);
			test13 = "FAIL";
		} catch (Exception e) {
			test13 = "SUCCESS";
		}

		// invalid request: invalid category value
		try {
			prs.addMeasurementObservation("P400", "", 120);
			test14 = "FAIL";
		} catch (Exception e) {
			test14 = "SUCCESS";
		}

		// print results
		System.out.println("\n[testAddObservations(): test results ]");
		System.out.printf("%-8s %s", test1,  " ---- create patient record system object\n");
		System.out.printf("%-8s %s", test2,  " ---- add 2 observation type with valids\n");
		System.out.printf("%-8s %s", test3,  " ---- add 4 patient with valids\n");
		System.out.printf("%-8s %s", test4,  " ---- add measurement observation with valids\n");
		System.out.printf("%-8s %s", test5,  " ---- add category observation with valids\n");
		System.out.printf("%-8s %s", test6,  " ---- add measurement observation with invalids:  patient already has observation of the type\n");
		System.out.printf("%-8s %s", test8,  " ---- add measurement observation with invalids: patient doesn't exist\n");
		System.out.printf("%-8s %s", test13, " ---- add measurement observation with invalids: empty patient id\n");
		System.out.printf("%-8s %s", test14, " ---- add measurement observation with invalids: empty category observation id\n");
		System.out.printf("%-8s %s", test7,  " ---- add category observation with invalids: invalid category value\n");
		System.out.printf("%-8s %s", test9,  " ---- add category observation with invalids: patient doesn't exist\n");
		System.out.printf("%-8s %s", test10, " ---- add category observation with invalids: empty patient id\n");
		System.out.printf("%-8s %s", test11, " ---- add category observation with invalids: empty category observation id\n");
		System.out.printf("%-8s %s", test12, " ---- add category observation with invalids: empty category value\n");
		System.out.println();
	}

	public void caseSensitivity() {
		// create patient record system object
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// add valid measurement observation type with mixed case entries
		try {
			prs.addMeasurementObservationType("tT100", "bLood PrEssure", "pSi");	
			test2 = "SUCCESS";
		} catch (Exception e) {
			test2 = "FAIL";
			e.printStackTrace();
		}

		// add valid category observation type with mixed case entries
		try {
			String[] categories = { "gRoup a", "GrouP b1", "grOUp B2" };
			prs.addCategoryObservationType("tT200", "bLood tYpe", categories);
			test3 = "SUCCESS";
		} catch (Exception e) {
			test3 = "FAIL";
			e.printStackTrace();
		}

		// add 2 patients with mixed case entries
		try {
			prs.addPatient("pP100", "sMith");
			prs.addPatient("pP200", "wEst");
			test4 = "SUCCESS";
		} catch (Exception e) {
			test4 = "FAIL";
			e.printStackTrace();
		}

		// add valid measurement observation with mixed case entries
		try {
			prs.addMeasurementObservation("Pp100", "Tt100", 120);
			test5 = "SUCCESS";
		} catch (Exception e) {
			test5 = "FAIL";
			e.printStackTrace();
		}

		// add valid category observation with mixed case entries
		try {
			prs.addCategoryObservation("PP200", "TT200", "GROUP A");
			test6 = "SUCCESS";
		} catch (Exception e) {
			test6 = "FAIL";
			e.printStackTrace();
		}

		// print results
		System.out.println("\n[caseSensitivity(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object.\n");
		System.out.printf("%-8s %s", test2, " ---- add valid measurement observation type with mixed case entires\n");
		System.out.printf("%-8s %s", test3, " ---- add valid category observation type with mixed case entires\n");
		System.out.printf("%-8s %s", test4, " ---- add valid patient with mixed case entries\n");
		System.out.printf("%-8s %s", test5, " ---- add valid observation with mixed case entries\n");
		System.out.printf("%-8s %s", test6, " ---- add valid observation with mixed case entries\n");
		System.out.println();
	}

	public void loadSaveTest() {
		// delete files if exist
		deleteFiles();
		
		// valid create patient record system object
		PatientRecordSystem prs = null;
		try {
			prs = new PatientRecordSystem();
			test1 = "SUCCESS";
		} catch (Exception e) {
			test1 = "FAIL";
			e.printStackTrace();
		}

		// invalid request: load prs without having saved first
		try {
			prs.loadData();
			test2 = "FAIL";
		} catch (Exception e) {
			test2 = "SUCCESS";
		}

		// invalid request: save prs without data
		try {
			prs.saveData();
			test3 = "FAIL";
		} catch (Exception e) {
			test3 = "SUCCESS";
		}

		// add valid records to prs
		try {
			prs.addMeasurementObservationType("T700", "test", "psi");
			String[] categories = { "Groupbb", "Groupcc", "Groupdd" };
			prs.addCategoryObservationType("T800", "test2", categories);
			String[] temp = { "l", "M", "h" };
			categories = temp;
			prs.addCategoryObservationType("T900", "test3", categories);
			prs.addMeasurementObservationType("T1100", "test4", "cm");
			prs.addPatient("P900", "Smith");
			prs.addPatient("P800", "Adams");
			prs.addMeasurementObservation("P900", "T700", 120);
			prs.addCategoryObservation("P900", "T900", "l");
			prs.addCategoryObservation("P800", "T800", "Groupdd");
			test4 = "SUCCESS";
		} catch (Exception e) {
			test4 = "FAIL";
			e.printStackTrace();
		}

		// valid save prs
		try {
			prs.saveData();
			test5 = "SUCCESS";
		} catch (Exception e) {
			test5 = "FAIL";
			e.printStackTrace();
		}

		// invalid request: load prs data that is already in system
		try {
			prs.loadData();
			test6 = "FAIL";
		} catch (Exception e) {
			test6 = "SUCCESS";
		}

		// print results
		System.out.println("\n[loadSaveTest(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- create patient record system object.\n");
		System.out.printf("%-8s %s", test2, " ---- invalid request - load patient record system without having saved first. (this is set to fail if can load.)\n");
		System.out.printf("%-8s %s", test3, " ---- invalid request - load patient record system without having saved first. (this is set to fail if no data is added and can save.)\n");
		System.out.printf("%-8s %s", test4, " ---- valid - add records to patient record system.\n");
		System.out.printf("%-8s %s", test5, " ---- valid - save prs.\n");
		System.out.printf("%-8s %s", test6, " ---- invalid request - load records from saved file that already exist in prs.\n");
		System.out.println();
	}

	public void loadTestFormat() {
		// valid create patient record system object
		PatientRecordSystem prs = new PatientRecordSystem();

		PrintWriter outFile = null;

		// create file with wrong formatting
		System.out.println("[action: create files with wrong formatting]");
		try {
			outFile = new PrintWriter(new File("PRS-MeasurementObservationTypes.txt"));
			outFile.println(";asdad;asdasd;;;; ;");
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println("test1;test2;test3");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			outFile.close();
		}

		try {
			outFile = new PrintWriter(new File("PRS-CategoryObservationTypes.txt"));
			outFile.println(";asdad;asdasd;;;; ;");
			outFile.println("test2;test3;test4");
			outFile.println();
			outFile.println("T300; Stress Level; Low,, High");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			outFile.close();
		}

		try {
			outFile = new PrintWriter(new File("PRS-Patients.txt"));
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println(";asdad;asdasd;;;; ;");
			outFile.println("patient1;patient2");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			outFile.close();
		}

		try {
			outFile = new PrintWriter(new File("PRS-MeasurementObservations.txt"));
			outFile.println(";asdad;asdasd;;;; ;");
			outFile.println();
			outFile.println();
			outFile.println("patient1;test1;apple");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			outFile.close();
		}

		try {
			outFile = new PrintWriter(new File("PRS-CategoryObservations.txt"));
			outFile.println(";asdad;asdasd;;;; ;");
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println();
			outFile.println("patient1;test2;;;;");
			outFile.println("patient1;test2;120");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			outFile.close();
		}

		String test1 = null;

		// test loading data with wrong formatting
		System.out.println("[action: load data with wrong formatting]");
		try {
			prs.loadData();
			test1 = "FAIL";
		} catch (Exception e) {
			test1 = "SUCCESS";
		}

		// print results
		System.out.println("\n[loadTestFormat(): test results ]");
		System.out.printf("%-8s %s", test1, " ---- load data with wrong formatting\n");
		System.out.println();
	}
	
	public void deleteFiles() {
		// delete saved files if they exist
		System.out.println("[action: delete files if exist]");
		File outFile = null;
		
		outFile = new File("PRS-MeasurementObservationTypes.txt");
		if(outFile.exists()) {
			outFile.delete();
		}
	
		outFile = new File("PRS-CategoryObservationTypes.txt");
		if(outFile.exists()) {
			outFile.delete();
		}
		
		outFile = new File("PRS-Patients.txt");
		if(outFile.exists()) {
			outFile.delete();
		}
		
		outFile = new File("PRS-MeasurementObservations.txt");
		if(outFile.exists()) {
			outFile.delete();
		}
		
		outFile = new File("PRS-CategoryObservations.txt");
		if(outFile.exists()) {
			outFile.delete();
		}
	}
}

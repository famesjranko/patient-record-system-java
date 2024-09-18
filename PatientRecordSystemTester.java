import java.io.*;
import java.util.*;
public class PatientRecordSystemTester
{
	public static void main(String [] args) throws Exception
	{
		testSaveData();
		testLoadData(); // this method calls testSaveData ()
	}
	
	public static void testSaveData() throws Exception
	{
		// Create PatientRecordSystem
		// Add observation types , patients and observations
		PatientRecordSystem prs = new PatientRecordSystem();
		prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
		String [] categories = {"Group A", "Group B1", "Group B2"};
		prs.addCategoryObservationType("T200", "blood type", categories);
		String [] temp = {"low", "Medium", "high"};
		categories = temp;
		prs.addCategoryObservationType("T300", "stress level", categories);
		prs.addMeasurementObservationType("T400", "height", "cm");
		prs.addPatient("P100", "Smith");
		prs.addPatient("P200", "Adams");
		prs.addMeasurementObservation("P100", "T100", 120);
		prs.addCategoryObservation("P100", "T200", "Group A");
		// save data to file
		prs.saveData();
	}
	public static void testLoadData() throws Exception
	{
		testSaveData();
		PatientRecordSystem prs = new PatientRecordSystem();
		prs.loadData();
		System.out.println(prs);
	}
}

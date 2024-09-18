
/*	
 *	OutputColours.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 17.05.2018
 *	Version 1.0	
 *
 *	---Interface outline---
 * 	This interface holds the ansi colour variables for both the PatientRecordSystem
 * 	class and the Patient class. 
 * 
 * 	Ansi colours were added to the output to aid in assignment output readability. 
 * 
 *  As this program, in its current form, is written primarily for use on Unix based 
 *  operating systems colours may not output correctly on non-Unix based operating 
 *  systems. While this won't break functionality on non-Unix operating systems, colours 
 *  may not be shown correctly and instead display extra non-standard characters in their 
 *  place. To correct output on non-Unix based systems simply change ansi String values 
 *  to empty, "".
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */

public interface OutputColours {
	// ansi colour codes
	public final String ANSI_GREEN = "\u001B[32m";
	public final String ANSI_RED = "\u001B[31m";
	public final String ANSI_FGBLACK_BGWHITE = "\u001B[30;47m";
	public final String ANSI_RESET = "\u001B[0m";
	
	// coloured status output messages
	public final String created = 	"[" + ANSI_GREEN + "  CREATED  " + ANSI_RESET + "]";
	public final String loading = 	"[" + ANSI_GREEN + "  LOADING  " + ANSI_RESET + "]";
	public final String writing = 	"[" + ANSI_GREEN + "  WRITING  " + ANSI_RESET + "]";
	public final String saved = 	"[" + ANSI_GREEN + "   SAVED   " + ANSI_RESET + "]";
	public final String notSaved = 	"[" + ANSI_RED +   " NOT SAVED " + ANSI_RESET + "]";
	public final String found = 	"[" + ANSI_GREEN + "   FOUND   " + ANSI_RESET + "]";
	public final String notFound = 	"[" + ANSI_RED +   " NOT FOUND " + ANSI_RESET + "]";
	public final String completed = "[" + ANSI_GREEN + " COMPLETED " + ANSI_RESET + "]";
	public final String success = 	"[" + ANSI_GREEN + "  SUCCESS  " + ANSI_RESET + "]";
	public final String setBack = 	"[" + ANSI_RED +   "  SETBACK  " + ANSI_RESET + "]";
	
	// i/o method header output messages
	public final String saveSystem = ANSI_GREEN +  "SAVING PATIENT RECORD SYSTEM" 	+ ANSI_RESET;
	public final String loadSystem = ANSI_GREEN +  "LOADING PATIENT RECORD SYSTEM" 	+ ANSI_RESET;
	public final String error = 	 ANSI_RED 	+  "ERROR:"  						+ ANSI_RESET;

	
	// coloured display method output messages
	public final String noMOTypesDisplay = ANSI_FGBLACK_BGWHITE + " - No measurement observation types added - " + ANSI_RESET;
	public final String noCOTypesDisplay = ANSI_FGBLACK_BGWHITE + " - No category observation types added - "	 + ANSI_RESET;
	public final String noPatientDisplay = ANSI_FGBLACK_BGWHITE + " - No patients added - " 					 + ANSI_RESET;
}

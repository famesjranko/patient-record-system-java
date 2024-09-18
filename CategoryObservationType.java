/*	
 *	CategoryObservationType.java
 *	
 *	Written by: Andrew McDonald, 17961040
 *	Written for: CSE1IOO
 *	Date: 01.05.2018
 *	Version 1.0	
 *
 *	---Class outline---
 *	The CategoryObservationType class is a derived class from ObservationType.
 *	It holds the information for ObservationTypes of type CategoryObservationType. 
 *	
 *	Also, all arrays from previous version have been replaced with ArrayList in 
 *	aid of potential future functionality addons in assignment part two, such as 
 *	modifying, deleting, swapping specific array members.
 *	
 *	The first version converted constructor parameter String[] into ArrayList<String> 
 *	with a for loop, but this responsibility has since been delegated to 
 *	PatientRecordSystem.java.
 *
 *		//Have kept in-code comments to a minimum as the code structure and naming 
 *		//practices should speak for themselves, and unnecessary comments can cause 
 *		//extra problems in future updates if not managed properly. 
 *	
 *	---End---
 *
 */	

import java.util.List;
import java.util.ArrayList;

public class CategoryObservationType extends ObservationType {
	private final List<String> categories;

	// constructor
	public CategoryObservationType(String code, String name, List<String> categoriesList) {
		super(code, name);
		this.categories = new ArrayList<String>(categoriesList);
	}

	// protected getter
	protected List<String> getCategories(){
		return categories;
	}
	
	// public getter
	public int getNumberOfCategories() {
		return categories.size();
	}

	// toString method
	public String toString() {
		String categoriesList = " [ ";
		for (int i = 0; i < categories.size(); i++) {
			categoriesList += categories.get(i).toString();
			if (i != categories.size() - 1) {
				categoriesList += ", ";
			}
		}
		categoriesList += " ]";
		return super.toString() + "\n\tCategories: " + categoriesList;
	}
}
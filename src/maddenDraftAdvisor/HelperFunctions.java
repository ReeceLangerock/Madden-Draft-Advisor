package maddenDraftAdvisor;

import java.util.ArrayList;

public class HelperFunctions {

	// validate entries when no optional information is added. Makes sure names
	// are correct; 40, 3 cone, shuttle, bench and vert are numeric and in right
	// range; and that duplicate attributes aren't selected
	public ArrayList<String> validateEntry(String forty, String threeCone, String shuttle, String bench, String vert,
			String fName, String lName, String att1, String att2, String att3) {
		ArrayList<String> badEntries = new ArrayList<String>();
		if (!fName.matches("[a-zA-z]+")) {
			badEntries.add("First Name");
		}

		if (!lName.matches("[a-zA-z]+")) {
			badEntries.add("Last Name");
		}

		try {
			float temp = Float.parseFloat(forty);
			if (temp < 4 || temp > 6 || temp != 0) {
				badEntries.add("Forty Time");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Forty Time");
		}
		try {
			float temp = Float.parseFloat(threeCone);
			if (temp < 6.45 || temp > 8.5) {
				badEntries.add("Three Cone Time");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Three Cone Time");
		}
		try {
			float temp = Float.parseFloat(shuttle);
			if (temp < 3.8 || temp > 5.5) {
				badEntries.add("Shuttle Time");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Shuttle Time");
		}
		try {
			int temp = Integer.parseInt(bench);
			if (temp < 1 || temp > 42) {
				badEntries.add("Bench Reps");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Bench Reps");
		}
		try {
			float temp = Float.parseFloat(vert);
			if (temp < 1 || temp > 43.8 ) {
				badEntries.add("Vertical Jump");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Vertical Jump");
		}

		if (att1 == att2 && att1 == att3 && att2 == att3) {
			if (att1 != "Unscouted")
				badEntries.add("All Attributes");
		} else {

			if (att1 == att2) {
				if (att1 != "Unscouted") {
					badEntries.add("First and Second Attribute");
				}
			}

			if (att1 == att3) {
				if (att1 != "Unscouted") {
					badEntries.add("First and Third Attribute");
					;
				}
			}

			if (att2 == att3) {
				if (att2 != "Unscouted") {
					badEntries.add("Second and Third Attribute");
				}
			}
		}

		return badEntries;
	}

	// validate entries when no optional information is added. Makes sure names
	// are correct; 40, 3 cone, shuttle, bench and vert are numeric and in right
	// range; that draft round and pick are between 1-7 and 1-32; that duplicate
	// attributes aren't selected
	public ArrayList<String> validateEntry(String forty, String threeCone, String shuttle, String bench, String vert,
			String fName, String lName, String dRound, String dPick, String att1, String att2, String att3) {
		ArrayList<String> badEntries = new ArrayList<String>();
		if (!fName.matches("[a-zA-z]+")) {
			badEntries.add("First Name");
		}

		if (!lName.matches("[a-zA-z]+")) {
			badEntries.add("Last Name");
		}

		try {
			float temp = Float.parseFloat(forty);
			if (temp < 4.2 || temp > 6) {
				badEntries.add("Forty Time");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Forty Time");
		}
		try {
			float temp = Float.parseFloat(threeCone);
			if (temp < 6.45 || temp > 8.5) {
				badEntries.add("Three Cone Time");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Three Cone Time");
		}
		try {
			float temp = Float.parseFloat(shuttle);
			if (temp < 3.8 || temp > 5.5) {
				badEntries.add("Shuttle Time");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Shuttle Time");
		}
		try {
			int temp = Integer.parseInt(bench);
			if (temp < 1 || temp > 42) {
				badEntries.add("Bench Reps");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Bench Reps");
		}
		try {
			float temp = Float.parseFloat(vert);
			if (temp < 1 || temp > 43.8) {
				badEntries.add("Vertical Jump");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Vertical Jump");
		}

		try {
			int temp = Integer.parseInt(dRound);
			if (temp < 1 || temp > 7) {
				badEntries.add("Draft Round");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Draft Round");
		}
		try {
			int temp = Integer.parseInt(dPick);
			if (temp < 1 || temp > 32) {
				badEntries.add("Draft Pick");
			}
		} catch (NumberFormatException e) {
			badEntries.add("Draft Pick");
		}

		if (att1 == att2) {
			if (att1 != "Unscouted") {
				badEntries.add(att1);
				badEntries.add(att2);
			}
		}

		if (att1 == att3) {
			if (att1 != "Unscouted") {
				badEntries.add(att1);
				badEntries.add(att3);
			}
		}

		if (att2 == att3) {
			if (att1 != "Unscouted") {
				badEntries.add(att2);
				badEntries.add(att3);
			}
		}

		return badEntries;

	}

	// makes sure input in textfield is a integer
	public int checkIntInput(String input) {

		try {
			int temp = Integer.parseInt(input);
			return temp;
		} catch (NumberFormatException e) {
			return 0;
		}

	}

	// makes sure input in textfield is a float
	public float checkFloatInput(String input) {

		try {
			float temp = Float.parseFloat(input);
			return temp;
		} catch (NumberFormatException e) {
			return 0;
		}

	}

	// makes sure input in textfield is a String
	public boolean checkStringInput(String input) {

		try {
			String temp = (String) input;

			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public ArrayList<String> validateEntry(String fName, String lName, String att1, String att2, String att3) {
		ArrayList<String> badEntries = new ArrayList<String>();
		if (!fName.matches("[a-zA-z]+")) {
			badEntries.add("First Name");
		}

		if (!lName.matches("[a-zA-z]+")) {
			badEntries.add("Last Name");
		}	


		if (att1 == att2) {
			if (att1 != "Unscouted") {
				badEntries.add(att1);
				badEntries.add(att2);
			}
		}

		if (att1 == att3) {
			if (att1 != "Unscouted") {
				badEntries.add(att1);
				badEntries.add(att3);
			}
		}

		if (att2 == att3) {
			if (att1 != "Unscouted") {
				badEntries.add(att2);
				badEntries.add(att3);
			}
		}

		return badEntries;

	}

}

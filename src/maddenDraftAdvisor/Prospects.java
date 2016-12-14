package maddenDraftAdvisor;

import java.io.Serializable;

public class Prospects implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814343370056272446L;
	// combine attributes and grade in scoutable attributes
	private float fortyTime, shuttle, threeCone, vert, firstGrade, secondGrade, thirdGrade;
	private byte bench, speed, agility, acceleration, jumping, strength;
	private byte assumedMinimum = 45;
	// optional attributes
	private byte draftRound, draftPick; // draftpick is now age
	// offensive scoutable attributes
	private byte awareness, catching, carrying, throwPower, passBlock, runBlock, trucking, elusiveness, bcVision,
			stiffArm, spinMove, jukeMove, impactBlocking, specCatch, catchInTraffic, routeRunning, release, shortAcc,
			midAcc, deepAcc, throwOnRun, playAction, toughness;
	// defensive scoutable attributes
	private float tackle, powerMoves, finesseMoves, blockShedding, pursuit, playRec, manCover, zoneCover, hitPower,
			press;
	// hold prospect name and name of which attributes are scouted
	private String firstName, lastName, position, firstAttribute = "", secondAttribute = "", thirdAttribute = "",
			firstLetterGrade, secondLetterGrade, thirdLetterGrade, firstABV = "uns", secondABV = "uns",
			thirdABV = "uns";
	double overall;
	private boolean drafted = false;

	// String array of position names
	public static final String[] positionChoices = { "Quarterback", "Halfback", "Fullback", "Wide Receiver",
			"Tight End", "Left Tackle", "Left Guard", "Center", "Right Guard", "Right Tackle", "Left End",
			"Defensive Tackle", "Right End", "Left Outside Linebacker", "Middle Linebacker", "Right Outside Linebacker",
			"Cornerback", "Free Safety", "Strong Safety" };

	// String array of possible attributes
	public static final String[] attributeChoices = { "Unscouted", "Awareness", "Ball Carrier Vision", "Block Shedding",
			"Catching", "Carrying", "Catch in Traffic", "Deep Accuracy", "Elusiveness", "Finesse Moves", "Hit Power",
			"Impact Blocking", "Juke Move", "Man Coverage", "Mid Accuracy", "Pass Block", "Play Action",
			"Play Recognition", "Power Moves", "Press", "Pursuit", "Release", "Route Running", "Run Block",
			"Short Accuracy", "Spectacular Catch", "Spin Move", "Stiff Arm", "Tackle", "Toughness", "Throw Power",
			"Throw on Run", "Trucking", "Zone Coverage" };

	// String array of possible scouting grades
	public static final String[] attributeGrade = { "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D",
			"D-" };

	// constructor with name only
	public Prospects(String fn, String ln, String p, String fAtt,
			String fGrade, String sAtt, String sGrade, String tAtt, String tGrade){
		fortyTime = 0;
		shuttle = 0;
		threeCone = 0;
		bench = 0;
		vert = 0;
		draftRound = -1;
		draftPick = -1;
		firstLetterGrade = fGrade;
		secondLetterGrade = sGrade;
		thirdLetterGrade = tGrade;
		if (fAtt == "Unscouted") {

		} else {
			firstAttribute = fAtt;
			firstGrade = (float) registerScoutingGrades(fGrade);
		}

		if (sAtt == "Unscouted") {

		} else {
			secondAttribute = sAtt;
			secondGrade = (float) registerScoutingGrades(sGrade);
		}

		if (tAtt == "Unscouted") {

		} else {
			thirdAttribute = tAtt;
			thirdGrade = (float) registerScoutingGrades(tGrade);
		}
		calculateAttributes();
		assignScoutingGrade();
		calculateOverall();

	}
	
	// constructor without draft round and pick info
	public Prospects(float ft, float tc, float s, byte b, float v, String fn, String ln, String p, String fAtt,
			String fGrade, String sAtt, String sGrade, String tAtt, String tGrade) {
		fortyTime = ft;
		shuttle = s;
		threeCone = tc;
		bench = b;
		vert = v;
		firstName = fn;
		lastName = ln;
		position = p;
		draftRound = -1;
		draftPick = -1;
		firstLetterGrade = fGrade;
		secondLetterGrade = sGrade;
		thirdLetterGrade = tGrade;
		if (fAtt == "Unscouted") {

		} else {
			firstAttribute = fAtt;
			firstGrade = (float) registerScoutingGrades(fGrade);
		}

		if (sAtt == "Unscouted") {

		} else {
			secondAttribute = sAtt;
			secondGrade = (float) registerScoutingGrades(sGrade);
		}

		if (tAtt == "Unscouted") {

		} else {
			thirdAttribute = tAtt;
			thirdGrade = (float) registerScoutingGrades(tGrade);
		}
		calculateAttributes();
		assignScoutingGrade();
		calculateOverall();

	}

	// constructor with draft round and pick info
	public Prospects(float ft, float tc, float s, byte b, float v, String fn, String ln, String p, byte dr, byte dp,
			String fAtt, String fGrade, String sAtt, String sGrade, String tAtt, String tGrade) {
		fortyTime = ft;
		shuttle = s;
		threeCone = tc;
		bench = b;
		vert = v;
		firstName = fn;
		lastName = ln;
		position = p;
		draftRound = dr;
		draftPick = dp;
		firstLetterGrade = fGrade;
		secondLetterGrade = sGrade;
		thirdLetterGrade = tGrade;
		if (fAtt == "Unscouted") {
			firstABV = "uns";
		} else {
			firstAttribute = fAtt;
			firstGrade = (float) registerScoutingGrades(fGrade);
		}

		if (sAtt == "Unscouted") {
			secondABV = "uns";

		} else {
			secondAttribute = sAtt;
			secondGrade = (float) registerScoutingGrades(sGrade);
		}

		if (tAtt == "Unscouted") {
			thirdABV = "uns";

		} else {
			thirdAttribute = tAtt;
			thirdGrade = (float) registerScoutingGrades(tGrade);
			;
		}

		calculateAttributes();
		assignScoutingGrade();
		calculateOverall();

	}

	// calculates attributes associated with combine info
	// slope/intercept last updated 8/26/2016 with data from 110 players
	public void calculateAttributes() {
		speed = (byte) ((fortyTime * -35.42348729) + 248.6380549);
		double adjShuttle = shuttle - (.5 * threeCone);
		agility = (byte) ((adjShuttle * -83.56252342) + 146.6944999);
		if (agility > 99)
			agility = 99;
		if (agility < 1)
			agility = 1;
		double adjThree = threeCone - (shuttle * .85);
		acceleration = (byte) ((adjThree * -59.21119085) + 290.3543785);
		if (acceleration > 99)
			acceleration = 99;
		if (acceleration < 1)
			acceleration = 1;
		jumping = (byte) ((vert * 2.070900708) + 11.38461396);
		strength = (byte) ((bench * 1.179848085) + 47.61870019);
	}

	// takes in the letter grade selected for an attribute and returns the
	// Associated numerical value
	public double registerScoutingGrades(String grade) {
		double rating = 0;
		switch (grade) {
		case "A+":
			rating = 97.5;
			break;
		case "A":
			rating = 94;
			break;
		case "A-":
			rating = 91;
			break;
		case "B+":
			rating = 87.5;
			break;
		case "B":
			rating = 84;
			break;
		case "B-":
			rating = 81;
			break;
		case "C+":
			rating = 77.5;
			break;
		case "C":
			rating = 74;
			break;
		case "C-":
			rating = 71;
			break;
		case "D+":
			rating = 67.5;
			break;
		case "D":
			rating = 64;
			break;
		case "D-":
			rating = 61;
			break;
		}

		return rating;

	}

	// iterates through the 3 selected attributes from the add prospect screen,
	// uses a switch to find the matching attribute and assigns the grade to it
	public void assignScoutingGrade() {
		String skill = "";
		String abv = "";
		byte grade = 0;

		awareness = catching = carrying = throwPower = passBlock = runBlock = trucking = elusiveness = bcVision = stiffArm = spinMove = jukeMove = impactBlocking = specCatch = catchInTraffic = routeRunning = release = shortAcc = midAcc = deepAcc = throwOnRun = playAction = toughness = assumedMinimum;
		tackle = powerMoves = finesseMoves = blockShedding = pursuit = playRec = manCover = zoneCover = hitPower = press = assumedMinimum;

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				skill = firstAttribute;
				grade = (byte) firstGrade;
				abv = firstABV;
			}
			if (i == 1) {
				skill = secondAttribute;
				grade = (byte) secondGrade;
				abv = secondABV;
			}
			if (i == 2) {
				skill = thirdAttribute;
				grade = (byte) thirdGrade;
				abv = thirdABV;
			}

			switch (skill) {
			case "Unscouted":
				abv = "uns";
				break;
			case "Awareness":
				awareness = grade;
				abv = "awr";
				break;
			case "Catching":
				catching = grade;
				abv = "cth";
				break;
			case "Carrying":
				carrying = grade;
				abv = "car";
				break;
			case "Throw Power":
				throwPower = grade;
				abv = "thp";
				break;
			case "Pass Block":
				passBlock = grade;
				abv = "pbk";
				break;
			case "Run Block":
				runBlock = grade;
				abv = "rbk";
				break;
			case "Trucking":
				trucking = grade;
				abv = "trk";
				break;
			case "Elusiveness":
				elusiveness = grade;
				abv = "elu";
				break;
			case "Ball Carrier Vision":
				bcVision = grade;
				abv = "bcv";
				break;
			case "Stiff Arm":
				stiffArm = grade;
				abv = "stf";
				break;
			case "Spin Move":
				spinMove = grade;
				abv = "spn";
				break;
			case "Juke Move":
				jukeMove = grade;
				abv = "jkm";
				break;
			case "Impact Blocking":
				impactBlocking = grade;
				abv = "ibk";
				break;
			case "Spectacular Catch":
				specCatch = grade;
				abv = "spc";
				break;
			case "Catch in Traffic":
				catchInTraffic = grade;
				abv = "cit";
				break;
			case "Route Running":
				routeRunning = grade;
				abv = "rte";
				break;
			case "Release":
				release = grade;
				abv = "rel";
				break;
			case "Short Accuracy":
				shortAcc = grade;
				abv = "sac";
				break;
			case "Mid Accuracy":
				midAcc = grade;
				abv = "mac";
				break;
			case "Deep Accuracy":
				deepAcc = grade;
				abv = "dac";
				break;
			case "Throw On Run":
				throwOnRun = grade;
				abv = "tor";
				break;
			case "Play Action":
				playAction = grade;
				abv = "pla";
				break;
			case "Toughness":
				toughness = grade;
				abv = "tgh";
				break;
			case "Tackle":
				tackle = grade;
				abv = "tak";
				break;
			case "Power Moves":
				powerMoves = grade;
				abv = "pmv";
				break;
			case "Finesse Moves":
				finesseMoves = grade;
				abv = "fmv";
				break;
			case "Block Shedding":
				blockShedding = grade;
				abv = "bks";
				break;
			case "Pursuit":
				pursuit = grade;
				abv = "pur";
				break;
			case "Play Recognition":
				playRec = grade;
				abv = "prc";
				break;
			case "Man Coverage":
				manCover = grade;
				abv = "mcv";
				break;
			case "Zone Coverage":
				zoneCover = grade;
				abv = "zcv";
				break;
			case "Hit Power":
				hitPower = grade;
				abv = "hpw";
				break;
			case "Press":
				press = grade;
				abv = "prs";
				break;
			}

			if (i == 0)
				firstABV = abv;
			if (i == 1)
				secondABV = abv;
			if (i == 2)
				thirdABV = abv;

		}
	}

	// uses a switch to cycle through all possible positions, and calculates an
	// estimated overall

	public void calculateOverall() {
		switch (position) {
		case "Quarterback":
			overall = ((agility * .02) + (.05 * speed) + (.01 * acceleration) + (.12 * awareness) + (.26 * throwPower)
					+ (.18 * shortAcc) + (.18 * midAcc) + (.12 * deepAcc) + (.02 * throwOnRun) + (.04 * playAction));
			break;

		case "Halfback":
			overall = ((strength * .03) + (agility * .08) + (.13 * speed) + (.08 * acceleration) + (.08 * awareness)
					+ (.04 * catching) + (.13 * carrying) + (.08 * trucking) + (.08 * elusiveness) + (.13 * bcVision)
					+ (.02 * stiffArm) + (.04 * spinMove) + (.04 * jukeMove) + (catchInTraffic * .01)
					+ (routeRunning * .03));
			break;
		case "Fullback":
			overall = ((strength * .03) + (agility * .03) + (.05 * speed) + (.05 * acceleration) + (.15 * awareness)
					+ (.04 * catching) + (.07 * carrying) + (.07 * passBlock) + (.16 * runBlock) + (.05 * trucking)
					+ (.02 * elusiveness) + (.03 * bcVision) + (.03 * stiffArm) + (.22 * impactBlocking));
			break;
		case "Wide Receiver":
			overall = ((strength * .02) + (agility * .05) + (.12 * speed) + (.08 * acceleration) + (.12 * awareness)
					+ (.12 * catching) + (.04 * carrying) + (.04 * jumping) + (.01 * trucking) + (.04 * elusiveness)
					+ (.01 * bcVision) + (.01 * stiffArm) + (.01 * spinMove) + (.01 * jukeMove) + (catchInTraffic * .08)
					+ (routeRunning * .12) + (.04 * specCatch) + (.08 * release));
			break;
		case "Tight End":
			overall = ((strength * .06) + (agility * .04) + (.07 * speed) + (.04 * acceleration) + (.12 * awareness)
					+ (.09 * catching) + (.02 * carrying) + (.04 * passBlock) + (.10 * runBlock) + (.02 * jumping)
					+ (.01 * trucking) + (.01 * elusiveness) + (.02 * bcVision) + (.01 * stiffArm) + (.01 * spinMove)
					+ (.01 * jukeMove) + (catchInTraffic * .04) + (routeRunning * .08) + (.04 * specCatch)
					+ (.02 * release) + (.06 * impactBlocking));
			break;
		case "Left Tackle":
			overall = ((strength * .13) + (agility * .01) + (.03 * speed) + (.03 * acceleration) + (.16 * awareness)
					+ (.36 * passBlock) + (.20 * runBlock) + (.08 * impactBlocking));
			break;
		case "Left Guard":
			overall = ((strength * .12) + (agility * .02) + (.03 * speed) + (.06 * acceleration) + (.18 * awareness)
					+ (.26 * passBlock) + (.23 * runBlock) + (.10 * impactBlocking));
			break;
		case "Center":
			overall = ((strength * .12) + (agility * .01) + (.05 * speed) + (.05 * acceleration) + (.18 * awareness)
					+ (.25 * passBlock) + (.25 * runBlock) + (.09 * impactBlocking));
			break;
		case "Right Guard":
			overall = ((strength * .12) + (agility * .02) + (.03 * speed) + (.06 * acceleration) + (.18 * awareness)
					+ (.26 * passBlock) + (.25 * runBlock) + (.08 * impactBlocking));
			break;
		case "Right Tackle":
			overall = ((strength * .12) + (agility * .01) + (.03 * speed) + (.03 * acceleration) + (.19 * awareness)
					+ (.24 * passBlock) + (.29 * runBlock) + (.09 * impactBlocking));
			break;
		case "Left End":
			overall = ((strength * .05) + (agility * .05) + (.1 * speed) + (.1 * acceleration) + (.11 * awareness)
					+ (.1 * tackle) + (.12 * powerMoves) + (.12 * finesseMoves) + (.1 * blockShedding) + (.03 * pursuit)
					+ (.11 * playRec) + (.01 * hitPower));
			break;
		case "Right End":
			overall = ((strength * .05) + (agility * .05) + (.1 * speed) + (.1 * acceleration) + (.11 * awareness)
					+ (.1 * tackle) + (.12 * powerMoves) + (.12 * finesseMoves) + (.1 * blockShedding) + (.03 * pursuit)
					+ (.11 * playRec) + (.01 * hitPower));
			break;
		case "Defensive Tackle":
			overall = ((strength * .17) + (agility * .03) + (.06 * speed) + (.06 * acceleration) + (.11 * awareness)
					+ (.08 * tackle) + (.14 * powerMoves) + (.10 * finesseMoves) + (.12 * blockShedding)
					+ (.02 * pursuit) + (.11 * playRec));
			break;
		case "Left Outside Linebacker":
			overall = ((strength * .06) + (agility * .02) + (.09 * speed) + (.06 * acceleration) + (.13 * awareness)
					+ (.1 * tackle) + (.10 * powerMoves) + (.06 * finesseMoves) + (.09 * blockShedding)
					+ (.06 * pursuit) + (.13 * playRec) + (.03 * manCover) + (.05 * zoneCover) + .02 * hitPower);
			break;
		case "Middle Linebacker":
			overall = ((strength * .04) + (agility * .04) + (.08 * speed) + (.04 * acceleration) + (.14 * awareness)
					+ (.15 * tackle) + (.02 * powerMoves) + (.02 * finesseMoves) + (.13 * blockShedding)
					+ (.09 * pursuit) + (.14 * playRec) + (.03 * manCover) + (.05 * zoneCover) + .04 * hitPower);
			break;
		case "Right Outside Linebacker":
			overall = ((strength * .06) + (agility * .02) + (.1 * speed) + (.06 * acceleration) + (.13 * awareness)
					+ (.13 * tackle) + (.06 * powerMoves) + (.10 * finesseMoves) + (.07 * blockShedding)
					+ (.06 * pursuit) + (.13 * playRec) + (.03 * manCover) + (.05 * zoneCover) + .03 * hitPower);
			break;
		case "Cornerback":
			overall = ((strength * .01) + (agility * .04) + (.14 * speed) + (.14 * acceleration) + (.10 * awareness)
					+ (.01 * catching) + (.03 * tackle) + (.04 * jumping) + (.14 * playRec) + (.18 * manCover)
					+ (.14 * zoneCover) + .03 * press);
			break;
		case "Strong Safety":
			overall = ((strength * .06) + (agility * .04) + (.09 * speed) + (.04 * acceleration) + (.15 * awareness)
					+ (.11 * tackle) + (.03 * jumping) + (.02 * blockShedding) + (pursuit * .09) + (.15 * playRec)
					+ (.05 * manCover) + (.13 * zoneCover) + (.04 * hitPower));
			break;
		case "Free Safety":
			overall = ((strength * .03) + (agility * .05) + (.11 * speed) + (.05 * acceleration) + (.14 * awareness)
					+ (.10 * tackle) + (.05 * jumping) + (pursuit * .05) + (.14 * playRec) + (.04 * manCover)
					+ (.17 * zoneCover) + (.03 * hitPower));
			break;

		}

	}

	// getter and setter to indicate if a player is drafted or not on the view
	// prospect screen
	public void setDrafted() {
		if (drafted == true) {
			drafted = false;
		} else
			drafted = true;
	}

	// getters and setters

	public void setPosition(String pos) {
		position = pos;
	}

	public String getFirstAttribute() {
		return firstAttribute;
	}

	public String getSecondAttribute() {
		return secondAttribute;
	}

	public String getThirdAttribute() {
		return thirdAttribute;
	}

	public void setFirstAttribute(String fa) {
		firstAttribute = fa;
	}

	public void setSecondAttribute(String sa) {
		secondAttribute = sa;
	}

	public void setThirdAttribute(String ta) {
		thirdAttribute = ta;
	}

	public byte getDraftRound() {
		return draftRound;
	}

	public byte getDraftPick() {
		return draftPick;
	}

	public double getOverall() {
		return overall;
	}

	public String getPosition() {
		return position;
	}

	public void setFortyTime(float time) {
		fortyTime = time;
	}

	public void setShuttle(float time) {
		shuttle = time;
	}

	public void setThreeCone(float time) {
		threeCone = time;
	}

	public void setBench(byte reps) {
		bench = reps;
	}

	public void setVert(float height) {
		vert = height;
	}

	public void setFirstName(String fn) {
		firstName = fn;
	}

	public void setLastName(String ln) {
		lastName = ln;
	}

	public float getFortyTime() {
		return fortyTime;
	}

	public float getShuttle() {
		return shuttle;
	}

	public boolean getDrafted() {
		return drafted;
	}

	public float getThreeCone() {
		return threeCone;
	}

	public byte getBench() {
		return bench;
	}

	public float getVert() {
		return vert;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public byte getSpeed() {
		return speed;
	}

	public byte getAgility() {
		return agility;
	}

	public byte getAcceleration() {
		return acceleration;
	}

	public byte getStrength() {
		return strength;
	}

	public byte getJumping() {
		return jumping;
	}

	public String getFirstABV() {
		return firstABV;
	}

	public String getSecondABV() {
		return secondABV;
	}

	public String getThirdABV() {
		return thirdABV;
	}

	public void setDraftRound(byte d) {
		draftRound = d;
	}

	public void setDraftPick(byte d) {
		draftPick = d;
	}

	public String getFirstLetterGrade() {
		return firstLetterGrade;
	}

	public String getSecondLetterGrade() {
		return secondLetterGrade;
	}

	public String getThirdLetterGrade() {
		return thirdLetterGrade;
	}

	public void setFirstConvertedGrade(double grade) {
		firstGrade = (float) grade;
	}

	public void setSecondConvertedGrade(double grade) {
		secondGrade = (float) grade;
	}

	public void setThirdConvertedGrade(double grade) {
		thirdGrade = (float) grade;
	}

	public float getFirstConvertedGrade() {
		return firstGrade;
	}

	public float getSecondConvertedGrade() {
		return secondGrade;
	}

	public float getThirdConvertedGrade() {
		return thirdGrade;
	}

	public void setFirstLetterGrade(String flg) {
		firstLetterGrade = flg;
	}

	public void setSecondLetterGrade(String slg) {
		secondLetterGrade = slg;
	}

	public void setThirdLetterGrade(String tlg) {
		thirdLetterGrade = tlg;
	}
}

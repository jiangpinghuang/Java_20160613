package org.hjp.corpus.ctb.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Evaluation {

	public static int correct = 0;
	public static int overall = 0;
	public static int predict = 0;

	public static int clB = 0;
	public static int olB = 0;
	public static int plB = 0;
	public static int crI = 0;
	public static int orI = 0;
	public static int prI = 0;
	
	public static int gt = 0;
	public static int et = 0;
	public static int ct = 0;

	public static void main(String[] args) {
		String goldFile = "/Users/hjp/Workshop/Model/jcsue/lstmcrfs/ten.txt";
		String evalFile = "/Users/hjp/Workshop/Model/jcsue/lstmcrfs/tens.txt";
		readFiles(goldFile, evalFile);
		System.out.println("Segmented measure: ");
		System.out.println("Correct: " + correct);
		System.out.println("Overall: " + overall);
		System.out.println("Predict: " + predict);
		resultDisplay(correct, predict, overall);
		System.out.println("Left boundary measure: ");
		System.out.println("Correct: " + clB);
		System.out.println("Overall: " + olB);
		System.out.println("Predict: " + plB);
		resultDisplay(clB, plB, olB);
		System.out.println("Right boundary measure: ");
		System.out.println("Correct: " + crI);
		System.out.println("Overall: " + orI);
		System.out.println("Predict: " + prI);
		resultDisplay(crI, prI, orI);
		System.out.println("Term segmented measure: ");
		System.out.println("Correct: " + ct);
		System.out.println("Overall: " + gt);
		System.out.println("Predict: " + et);
		resultDisplay(ct, et, gt);
	}

	public static void resultDisplay(int corr, int pred, int over) {
		double precision = (double) corr / (double) pred;
		double recall = (double) corr / (double) over;
		double fScore = 2 * precision * recall / (precision + recall);
		System.out.println("precision: " + precision);
		System.out.println("recall: " + recall);
		System.out.println("fScore: " + fScore);
	}

	public static void readFiles(String goldFile, String evalFile) {
		File gfile = new File(goldFile);
		File efile = new File(evalFile);
		BufferedReader greader = null;
		BufferedReader ereader = null;
		String gsent = "";
		String esent = "";
		String gterm = "";
		String eterm = "";
		try {
			greader = new BufferedReader(new FileReader(gfile));
			ereader = new BufferedReader(new FileReader(efile));
			String gline;
			String eline;
			while ((gline = greader.readLine()) != null && (eline = ereader.readLine()) != null) {
				if (gline.length() > 0 && eline.length() > 0) {
					String[] gArr = gline.split("\t");
					String[] eArr = eline.split(" ");
					System.out.println(gline + "  " + eline);
					if (gsent.length() == 0 && esent.length() == 0) {
						gsent = gArr[2];
						esent = eArr[1];
						gterm = gArr[0];
						eterm = eArr[0];
					} else {
						gsent = gsent + "\t" + gArr[2];
						esent = esent + "\t" + eArr[1];
						gterm = gterm + gArr[0];
						eterm = eterm + eArr[0];
					}
				} else {
					evalResult(gsent.split("\t"), esent.split("\t"));
					evalTerm(gterm, eterm);
					gsent = "";
					esent = "";
					gterm = "";
					eterm = "";
				}
			}
			greader.close();
			ereader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void evalTerm(String gterm, String eterm) {
		String[] gArr = gterm.split("、");
		String[] eArr = eterm.split("、");
		gt = gt + gArr.length;
		et = et + eArr.length;
		for (int i = 0; i < gArr.length; i++) {
			for(int j = 0; j < eArr.length; j++) {
				if(gArr[i].equals(eArr[j])) {
					ct++;
				}
			}
		}
	}

	public static void evalResult(String[] gArr, String[] eArr) {
		String sgArr = "";
		String seArr = "";
		boolean flag = false;
		boolean gflag = false;
		boolean eflag = false;
		boolean geflag = false;
		for (int i = 0; i < gArr.length; i++) {
			if (gArr[i].equals(eArr[i]) && gArr[i].equals("B") && !flag) {
				seArr = eArr[i];
				sgArr = gArr[i];
				flag = true;
			}
			if (gArr[i].equals(eArr[i]) && gArr[i].equals("I") && flag) {
				seArr = seArr + eArr[i];
				sgArr = sgArr + gArr[i];
				continue;
			}
			if (gArr[i].equals(eArr[i]) && gArr[i].equals("O") && flag) {
				correct++;
				System.out.println(seArr);
				System.out.println(sgArr);
				seArr = "";
				sgArr = "";
				flag = false;
			}
			if (gArr[i].equals(eArr[i]) && gArr[i].equals("B")) {
				clB++;
			}
			if (gArr[i].equals(eArr[i]) && gArr[i].equals("I")) {
				geflag = true;
			}
			if (gArr[i].equals(eArr[i]) && gArr[i].equals("O") && geflag) {
				crI++;
				geflag = false;
			}
			if (gArr[i].equals("B")) {
				overall++;
				olB++;
			}
			if (eArr[i].equals("B")) {
				predict++;
				plB++;
			}
			if (gArr[i].equals("I") && !gflag) {
				orI++;
				System.out.println(orI);
				gflag = true;
			}
			if (eArr[i].equals("I") && !eflag) {
				prI++;
				System.out.println(prI);
				eflag = true;
			}
			if (gArr[i].equals("O") && gflag) {
				gflag = false;
			}
			if (eArr[i].equals("O") && eflag) {
				eflag = false;
			}
		}
	}

}

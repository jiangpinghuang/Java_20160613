package org.hjp.corpus.ctb.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CountTerms {

	public static int count = 0;

	public static void main(String[] args) {
		String filePath = "/Users/hjp/Workshop/Model/jcsue/data/train.txt";
		countTerms(filePath);
		System.out.println("Words and phrases is: " + count);
	}

	public static void countTerms(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			String strline = "";
			boolean flag = false;
			while ((line = reader.readLine()) != null) {
				if (line.length() > 0) {
					String[] terms = line.split("\t");
					if (terms[2].equals("B")) {
						strline = terms[0];
						flag = true;
					}
					if (terms[2].equals("I") && flag) {
						strline = strline + terms[0];
					}
					if (terms[2].equals("O") && flag) {
						flag = false;
						String[] wps = strline.split("、");
						count = count + wps.length;
						System.out.println(strline + "   " + wps.length);
						strline = "";
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

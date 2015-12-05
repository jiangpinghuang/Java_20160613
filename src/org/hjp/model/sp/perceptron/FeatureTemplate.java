package org.hjp.model.sp.perceptron;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FeatureTemplate {

	public static void main(String[] args) {
		int uLength = 2;
		int bLength = 2;
		int tLength = 2;
		boolean word = true;
		boolean pos = true;

		unigramFeature(uLength, word, pos);
		bigramFeature(bLength, word, pos);
		trigramFeature(tLength, word, pos);

	}

	public static void unigramFeature(int length, boolean word, boolean pos) {
		if (word) {
			for (int i = -length; i <= length; i++) {
				System.out.println("U:[" + i + ",0]");
				writeFile("U:[" + i + ",0]");
			}
		}
		if (pos) {
			for (int i = -length; i <= length; i++) {
				System.out.println("U:[" + i + ",1]");
				writeFile("U:[" + i + ",1]");
			}
		}

	}

	public static void bigramFeature(int length, boolean word, boolean pos) {
		if (word) {
			for (int i = -length; i < length; i++) {
				System.out.println("B:[" + i + ",0]%[" + (i + 1) + ",0]");
				writeFile("B:[" + i + ",0]%[" + (i + 1) + ",0]");
			}
		}

		if (pos) {
			for (int i = -length; i < length; i++) {
				System.out.println("B:[" + i + ",1]%[" + (i + 1) + ",1]");
				writeFile("B:[" + i + ",1]%[" + (i + 1) + ",1]");
			}
		}

	}

	public static void trigramFeature(int length, boolean word, boolean pos) {
		if (word) {
			for (int i = -length; i < length - 1; i++) {
				System.out.println("T:[" + i + ",0]%[" + (i + 1) + ",0]%[" + (i + 2) + ",0]");
				writeFile("T:[" + i + ",0]%[" + (i + 1) + ",0]%[" + (i + 2) + ",0]");
			}
		}

		if (pos) {
			for (int i = -length; i < length - 1; i++) {
				System.out.println("T:[" + i + ",1]%[" + (i + 1) + ",1]%[" + (i + 2) + ",1]");
				writeFile("T:[" + i + ",1]%[" + (i + 1) + ",1]%[" + (i + 2) + ",1]");
			}
		}
	}

	public static void writeFile(String content) {
		String filePath = "/Users/hjp/Workshop/Model/perceptron/trial/template";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

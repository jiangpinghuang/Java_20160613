package org.hjp.conference.ijcai2016.corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SentencePair {

	public static int trainPara = 0;
	public static int trainNPara = 0;
	public static int trainTotal = 0;

	public static void main(String[] args) {
		String fileTrainPath = "/Users/hjp/Workshop/Model/ijcai/data/train.data";
		String fileDevPath = "/Users/hjp/Workshop/Model/ijcai/data/dev.data";
		String fileTestPath = "/Users/hjp/Workshop/Model/ijcai/data/test.data.txt";
		String fileTestLabel = "/Users/hjp/Workshop/Model/ijcai/data/test.label";
		readTestFile(fileTestPath, fileTestLabel);
		System.out.println("Para is: " + trainPara + ", No Para is: " + trainNPara + ", debate is: "
				+ (trainTotal - trainPara - trainNPara) + ", trainTotal is: " + trainTotal);

	}

	public static void readTestFile(String filePath, String fileTestLabel) {
		File file = new File(filePath);
		File filel = new File(fileTestLabel);
		BufferedReader reader = null;
		BufferedReader readerl = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			readerl = new BufferedReader(new FileReader(filel));
			String linel = null;
			String line = null;
			while ((line = reader.readLine()) != null && (linel = readerl.readLine()) != null) {
				trainTotal = trainTotal + 1;
				System.out.println(line);
				System.out.println(linel);
				String[] sents = line.split("\t");
				String[] sentl = linel.split("\t");
				System.out.println(sents[0]);
				System.out.println(sentl[0]);
				if (sentl[0].equals("true")) {
					trainPara = trainPara + 1;
					System.out.println("1");
					writeLabelFile("1");
					writeFile(sents[2] + "\r\n" + sents[3]);
				}
				if (sentl[0].equals("false")) {
					trainNPara = trainNPara + 1;
					System.out.println("0");
					writeLabelFile("0");
					writeFile(sents[2] + "\r\n" + sents[3]);
				}
				System.out.println(sents[2] + "\r\n" + sents[3]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (readerl != null) {
				try {
					readerl.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void readFile(String filePath, String fileTestLabel) {
		File file = new File(filePath);
		File filel = new File(fileTestLabel);
		BufferedReader reader = null;
		BufferedReader readerl = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			readerl = new BufferedReader(new FileReader(filel));
			String linel = null;
			String line = null;
			while ((line = reader.readLine()) != null && (linel = readerl.readLine()) != null) {
				trainTotal = trainTotal + 1;
				System.out.println(line);
				System.out.println(linel);
				String[] sents = line.split("\t");
				String[] sentl = linel.split("\t");
				System.out.println(sents[0]);
				System.out.println(sentl[0]);
				if (sents[4].equals("(3, 2)") || sents[4].equals("(4, 1)") || sents[4].equals("(5, 0)")) {
					trainPara = trainPara + 1;
					System.out.println("1");
					writeLabelFile("1");
					writeFile(sents[2] + "\r\n" + sents[3]);
				}
				if (sents[4].equals("(1, 4)") || sents[4].equals("(0, 5)")) {
					trainNPara = trainNPara + 1;
					System.out.println("0");
					writeLabelFile("0");
					writeFile(sents[2] + "\r\n" + sents[3]);
				}
				System.out.println(sents[2] + "\r\n" + sents[3]);
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

	public static void writeFile(String content) {
		String filePath = "/Users/hjp/Workshop/Model/ijcai/data/test.txt";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeLabelFile(String content) {
		String filePath = "/Users/hjp/Workshop/Model/ijcai/data/testlab.txt";
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

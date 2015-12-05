package org.hjp.model.sp.perceptron.pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class POSTagger {
	
	public static ArrayList alpha = new ArrayList();
	public static ArrayList sentVec = new ArrayList();
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String trainFile = "/Users/hjp/Workshop/Model/perceptron/trial/train.txt";
		POSFeatureVector posfv = new POSFeatureVector();
		POSFeatureVector.featureExtraction(trainFile);
		//initialAlpha(posfv.featureList.size());
		System.out.println(posfv.featureList.size());
		System.out.println(posfv.posList);
		trainModel(trainFile);
		
		
		
	}
	
	public static void trainModel(String trainFile) {
		if (trainFile.endsWith(".txt")) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(trainFile));

				String line;
				String wordStr = "";
				String posStr = "";
				while ((line = in.readLine()) != null) {
					if (line.length() != 0) {
						String[] terms = line.split(" ");
						wordStr = wordStr + "\t" + terms[0];
						posStr = posStr + "\t" + terms[1];
					} else {
						String[] words = wordStr.split("\t");
						String[] goldtag = posStr.split("\t");
						updateAlpha(words, goldtag);
						wordStr = "";
						posStr = "";
						System.out.println();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void updateAlpha(String[] words, String[] goldtag) {
		Hashtable<String, Double> sequence = new Hashtable<String, Double>();
		for(int i = 1; i< words.length; i++)
			System.out.println(words[i] + "\t" + goldtag[i]);
		System.out.println("0:" + words[0]);
		
		for(int i = 1; i < words.length; i++) {
			
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static void initialAlpha(int dim) {
		for(int i = 0; i < dim; i++) {
			alpha.add(0);
			sentVec.add(0);
		}
	}

}

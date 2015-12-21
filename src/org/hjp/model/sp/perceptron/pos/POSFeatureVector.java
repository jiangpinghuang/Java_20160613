package org.hjp.model.sp.perceptron.pos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class POSFeatureVector {

	public static ArrayList<String> posList = new ArrayList<String>();
	public static ArrayList<String> featureList = new ArrayList<String>();
	public static Hashtable<String, Integer> posSet = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> wordSet = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> featureSet = new Hashtable<String, Integer>();

	public static void featureExtraction(String trainFile) {
		long start = System.currentTimeMillis();
		readFile(trainFile);
		displayFeature();
		displayFeatureList();

		long end = System.currentTimeMillis();
		System.out.println("The cost time is: " + (end - start) / 1000 + " s!");

	}

	public static void readFile(String filePath) {
		if (filePath.endsWith(".txt")) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(filePath));

				String line;
				while ((line = in.readLine()) != null) {
					if (line.length() != 0) {
						String[] terms = line.split(" ");
						addWordSet(terms[0]);
						addPosSet(terms[1]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void addPosSet(String pos) {
		if (!posSet.containsKey(pos)) {
			posSet.put(pos, 1);
		} else {
			int value = posSet.get(pos);
			posSet.put(pos, value + 1);
		}
	}

	public static void addWordSet(String word) {
		if (!wordSet.containsKey(word)) {
			wordSet.put(word, 1);
		} else {
			int value = wordSet.get(word);
			wordSet.put(word, value + 1);
		}
	}

	public static void addFeatureSet(String feature) {
		if (!featureSet.containsKey(feature)) {
			featureSet.put(feature, 1);
		} else {
			int value = featureSet.get(feature);
			featureSet.put(feature, value + 1);
		}
	}

	@SuppressWarnings("unchecked")
	public static void addFeatureList(String feature) {
		featureList.add(feature);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map.Entry[] sortHashtableByValue(Hashtable<String, Integer> ht) {
		Set set = ht.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				int key1 = Integer.parseInt(((Map.Entry) arg0).getValue().toString());
				int key2 = Integer.parseInt(((Map.Entry) arg1).getValue().toString());
				return ((Comparable<Integer>) key1).compareTo(key2);
			}
		});

		return entries;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map.Entry[] sortHashtableByKey(Hashtable<String, Integer> ht) {
		Set set = ht.entrySet();

		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable<Object>) key1).compareTo(key2);
			}

		});

		return entries;
	}

	public static void displayFeatureList() {
		for (int i = 0; i < featureList.size(); i++) {
			System.out.println(featureList.get(i));
		}
		System.out.println("featureList size is: " + featureList.size());
		System.out.println("Index of zero#NN is: " + featureList.indexOf("zero#NN"));
		System.out.println("Index of zones#NN is: " + featureList.indexOf("zones#NN"));
		System.out.println(featureList.get(0));
		// writeFile(featureList.toString());
	}

	public static void displayFeature() {
		@SuppressWarnings("rawtypes")
		Iterator iterPos = posSet.entrySet().iterator();
		while (iterPos.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iterPos.next();
			// System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		@SuppressWarnings("rawtypes")
		Map.Entry[] setPos = sortHashtableByValue(posSet);
		String[] posArray = new String[setPos.length];
		for (int i = 0; i < setPos.length; i++) {
			// System.out.println(setPos[i].getKey() + "\t" +
			// setPos[i].getValue());
			posArray[i] = setPos[i].getKey().toString();
			posList.add(posArray[i]);
		}

		@SuppressWarnings("rawtypes")
		Iterator iterWordPos = wordSet.entrySet().iterator();
		while (iterWordPos.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iterWordPos.next();
			// System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		@SuppressWarnings("rawtypes")
		Map.Entry[] setWordPos = sortHashtableByKey(wordSet);
		String[] wordArray = new String[setWordPos.length];
		for (int i = 0; i < setWordPos.length; i++) {
			// System.out.println(setWordPos[i].getKey() + "\t" +
			// setWordPos[i].getValue());
			wordArray[i] = setWordPos[i].getKey().toString();
		}

		// System.out.println("setWordPos size is: " + setWordPos.length);
		// System.out.println("posSet size is: " + setPos.length);
		for (int i = 0; i < posArray.length; i++) {
			for (int j = 0; j < posArray.length; j++) {
				for (int k = 0; k < posArray.length; k++) {
					// System.out.println(posArray[i] + "#" + posArray[j] + "#"
					// + posArray[k]);
					addFeatureSet(posArray[i] + "#" + posArray[j] + "#" + posArray[k]);
					addFeatureList(posArray[i] + "#" + posArray[j] + "#" + posArray[k]);
				}
			}

			for (int j = 0; j < wordArray.length; j++) {
				// System.out.println(wordArray[j] + "#" + posArray[i]);
				addFeatureSet(wordArray[j] + "#" + posArray[i]);
				addFeatureList(wordArray[j] + "#" + posArray[i]);
			}
		}
	}

	public static void writeFile(String content) {
		String filePath = "/Users/hjp/Workshop/Model/perceptron/trial/model";
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

package org.hjp.model.sp.perceptron;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FeatureExtraction {
	public static Hashtable<String, Integer> featureSet = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> allFeatureSet = new Hashtable<String, Integer>();
	public static int sentNum = 0;
	public static int sentCount = 0;
	public static int indexNum = 0;
	
	public static int value = 3;

	public static String featVec = "";
	
	public static String featureFilePath = "/Users/hjp/Workshop/Model/perceptron/data/ftrain" + value + ".txt";
	public static String tagFilePath = "/Users/hjp/Workshop/Model/perceptron/data/gtrain" + value + ".txt";
	
	public static String featureTestFilePath = "/Users/hjp/Workshop/Model/perceptron/data/ftest" + value + ".txt";
	public static String tagTestFilePath = "/Users/hjp/Workshop/Model/perceptron/data/gtest" + value + ".txt";

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String filePath = "/Users/hjp/Workshop/Model/perceptron/data/train.txt";
		writeTagFile("3\r\n");
		readFile(filePath);

		Iterator iter = featureSet.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if ((Integer) entry.getValue() > value) {
				if (!allFeatureSet.containsKey(entry.getKey())) {
					allFeatureSet.put(entry.getKey().toString(), indexNum++);
				}
			}
		}

		System.out.println("The type of total: " + allFeatureSet.size());
		writeFeatureFile(allFeatureSet.size() + "\r\n");

		indexFeature(filePath);
		System.out.println("Sent: " + sentCount);
		
		
		String testFile = "/Users/hjp/Workshop/Model/perceptron/data/test.txt";
		indexTestFeature(testFile);
		long end = System.currentTimeMillis();
		
		System.out.println("The time cost is: " + (end - start) / 1000 + " s!");

	}
	////////////////////////////////////////////////////////////////////////////
	public static void indexTestFeature(String testFile) {
		if (testFile.endsWith(".txt")) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(testFile));

				String line;
				String sent = "";
				while ((line = in.readLine()) != null) {
					if (line.length() > 0) {
						sent = sent + line + "#@#@#";
					} else {
						extractTestFeature(sent);
						sent = "";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void extractTestFeature(String sent) {
		System.out.println(sent);
		String tag = "";
		String[] terms = sent.split("#@#@#");
		String[] words = new String[terms.length];
		String[] postag = new String[terms.length];
		String[] tagger = new String[terms.length];
		for (int i = 0; i < terms.length; i++) {
			String[] tokens = terms[i].split(" ");
			words[i] = tokens[0];
			postag[i] = tokens[1];
			tagger[i] = tokens[2];
			if (tokens[2].equals("B-NP")) {
				if (tag.length() == 0) {
					tag = "2";
				} else {
					tag = tag + ",2";
				}
			} else {
				if (tokens[2].equals("I-NP")) {
					if (tag.length() == 0) {
						tag = "1";
					} else {
						tag = tag + ",1";
					}
				} else {
					if (tag.length() == 0) {
						tag = "0";
					} else {
						tag = tag + ",0";
					}
				}
			}
		}
		writeTestTagFile(tag + "\r\n");
		wpTestFeature(words, postag, tagger);
	}
	
	public static void writeTestFeatureFile(String content) {
		String filePath = featureTestFilePath;
		System.out.println("Writing feature index: " + content);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeTestTagFile(String content) {
		String filePath = tagTestFilePath;
		System.out.println("Writing tag index: " + content);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void wpTestFeature(String[] words, String[] postag, String[] tagger) {
		if (words.length == 1) {
			featTestVector("w_2", "w_1", words[0], "w1", "w2", "p_2", "p_1", postag[0], "p1", "p2", tagger[0]);
		}
		if (words.length == 2) {
			featTestVector("w_2", "w_1", words[0], words[1], "w1", "p_2", "p_1", postag[0], postag[1], "p1", tagger[0]);
			featTestVector("w_1", words[0], words[1], "w1", "w2", "p_1", postag[0], postag[1], "p1", "p2", tagger[1]);
		}
		if (words.length == 3) {
			featTestVector("w_2", "w_1", words[0], words[1], words[2], "p_2", "p_1", postag[0], postag[1], postag[2],
					tagger[0]);
			featTestVector("w_1", words[0], words[1], words[2], "w1", "p_1", postag[0], postag[1], postag[2], "p1",
					tagger[1]);
			featTestVector(words[0], words[1], words[2], "w1", "w2", postag[0], postag[1], postag[2], "p1", "p2",
					tagger[2]);
		}
		if (words.length == 4) {
			featTestVector("w_2", "w_1", words[0], words[1], words[2], "p_2", "p_1", postag[0], postag[1], postag[2],
					tagger[0]);
			featTestVector("w_1", words[0], words[1], words[2], words[3], "p_1", postag[0], postag[1], postag[2], postag[3],
					tagger[1]);
			featTestVector(words[0], words[1], words[2], words[3], "w1", postag[0], postag[1], postag[2], postag[3], "p1",
					tagger[2]);
			featTestVector(words[1], words[2], words[3], "w1", "w2", postag[1], postag[2], postag[3], "p1", "p2",
					tagger[3]);
		}
		if (words.length > 4) {
			for (int i = 0; i < words.length; i++) {
				if (i == 0) {
					featTestVector("w_2", "w_1", words[i], words[i + 1], words[i + 2], "p_2", "p_1", postag[i],
							postag[i + 1], postag[i + 2], tagger[i]);
				}
				if (i == 1) {
					featTestVector("w_1", words[i - 1], words[i], words[i + 1], words[i + 2], "p_1", postag[i - 1],
							postag[i], postag[i + 1], postag[i + 2], tagger[i]);
				}
				if (i == words.length - 2) {
					featTestVector(words[i - 2], words[i - 1], words[i], words[i + 1], "w1", postag[i - 2], postag[i - 1],
							postag[i], postag[i + 1], "p1", tagger[i]);
				}
				if (i == words.length - 1) {
					featTestVector(words[i - 2], words[i - 1], words[i], "w1", "w2", postag[i - 2], postag[i - 1],
							postag[i], "p1", "p2", tagger[i]);
				}
				if (i < words.length - 2 && i >= 2) {
					featTestVector(words[i - 2], words[i - 1], words[i], words[i + 1], words[i + 2], postag[i - 2],
							postag[i - 1], postag[i], postag[i + 1], postag[i + 2], tagger[i]);
				}
			}
		}
	}
	
	public static void featTestVector(String w_2, String w_1, String w, String w1, String w2, String p_2, String p_1,
			String p, String p1, String p2, String tag) {
		featVec = "";

		// for word
		// unigram
		featTestSequence(w_2 + "#" + tag);
		featTestSequence(w_1 + "#" + tag);
		featTestSequence(w + "#" + tag);
		featTestSequence(w1 + "#" + tag);
		featTestSequence(w2 + "#" + tag);

		// bigram
		featTestSequence(w_2 + "#" + w_1 + "#" + tag);
		featTestSequence(w_1 + "#" + w + "#" + tag);
		featTestSequence(w + "#" + w1 + "#" + tag);
		featTestSequence(w1 + "#" + w2 + "#" + tag);

		// for pos
		// unigram
		featTestSequence(p_2 + "#" + tag);
		featTestSequence(p_1 + "#" + tag);
		featTestSequence(p + "#" + tag);
		featTestSequence(p1 + "#" + tag);
		featTestSequence(p2 + "#" + tag);

		// bigram
		featTestSequence(p_2 + "#" + p_1 + "#" + tag);
		featTestSequence(p_1 + "#" + p + "#" + tag);
		featTestSequence(p + "#" + p1 + "#" + tag);
		featTestSequence(p1 + "#" + p2 + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p_2 + "#" + p_1 + "#" +
		// tag);
		// featVec = featVec + "," + findIndexValue(p_1 + "#" + p + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p + "#" + p1 + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p1 + "#" + p2 + "#" + tag);

		// trigarm
		featTestSequence(p_2 + "#" + p_1 + "#" + p + tag);
		featTestSequence(p_1 + "#" + p + "#" + p1 + "#" + tag);
		featTestSequence(p + "#" + p1 + "#" + p2 + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p_2 + "#" + p_1 + "#" + p +
		// tag);
		// featVec = featVec + "," + findIndexValue(p_1 + "#" + p + "#" + p1 +
		// "#" + tag);
		// featVec = featVec + "," + findIndexValue(p + "#" + p1 + "#" + p2 +
		// "#" + tag);
		if(featVec.length() < 1) {
			featVec = testIndex(w + "#" + tag) + featVec;
			writeTestFeatureFile(featVec);
		} else {
			writeTestFeatureFile(featVec);
		}
		
	}
	
	public static void featTestSequence(String feature) {
		if(featureSet.containsKey(feature)) {
			if(featureSet.get(feature) > value) {
				if(featVec.length() < 1) {
					featVec = findTestIndexValue(feature) + featVec;
				} else {
					featVec = featVec + "," + findTestIndexValue(feature);
				}
			}
			
		} 
	}

	public static int testTestIndex(String feature) {
		int index = 0;
		if(featureSet.containsKey(feature)) {
			index = featureSet.get(feature);
		} 
		return index;
	}

	public static int findTestIndexValue(String feature) {
		int index;

		index = allFeatureSet.get(feature);
		System.out.println("Find: " + feature + "'index is: " + index);

		return index;
	}

	
	////////////////////////////////////////////////////////////////////////////
	
	public static void readFile(String filePath) {
		if (filePath.endsWith(".txt")) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(filePath));

				String line;
				String sent = "";
				while ((line = in.readLine()) != null) {
					if (line.length() > 0) {
						sent = sent + line + "#@#@#";
					} else {
						extractFeature(sent);
						sent = "";
						sentNum++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void extractFeature(String sent) {
		System.out.println(sent);
		String tag = "";
		String[] terms = sent.split("#@#@#");
		String[] words = new String[terms.length];
		String[] postag = new String[terms.length];
		String[] tagger = new String[terms.length];
		for (int i = 0; i < terms.length; i++) {
			String[] tokens = terms[i].split(" ");
			words[i] = tokens[0];
			postag[i] = tokens[1];
			tagger[i] = tokens[2];
			if (tokens[2].equals("B-NP")) {
				if (tag.length() == 0) {
					tag = "2";
				} else {
					tag = tag + ",2";
				}
			} else {
				if (tokens[2].equals("I-NP")) {
					if (tag.length() == 0) {
						tag = "1";
					} else {
						tag = tag + ",1";
					}
				} else {
					if (tag.length() == 0) {
						tag = "0";
					} else {
						tag = tag + ",0";
					}
				}
			}
			
		}
		writeTagFile(tag + "\r\n");

		wpFeature(words, postag, tagger);

	}

	public static void featVector(String w_2, String w_1, String w, String w1, String w2, String p_2, String p_1,
			String p, String p1, String p2, String tag) {
		// for word
		// unigram
		addFeature(w_2 + "#" + tag);
		addFeature(w_1 + "#" + tag);
		addFeature(w + "#" + tag);
		addFeature(w1 + "#" + tag);
		addFeature(w2 + "#" + tag);

		// bigram
		addFeature(w_2 + "#" + w_1 + "#" + tag);
		addFeature(w_1 + "#" + w + "#" + tag);
		addFeature(w + "#" + w1 + "#" + tag);
		addFeature(w1 + "#" + w2 + "#" + tag);

		// for pos
		// unigram
		addFeature(p_2 + "#" + tag);
		addFeature(p_1 + "#" + tag);
		addFeature(p + "#" + tag);
		addFeature(p1 + "#" + tag);
		addFeature(p2 + "#" + tag);

		// bigram
		addFeature(p_2 + "#" + p_1 + "#" + tag);
		addFeature(p_1 + "#" + p + "#" + tag);
		addFeature(p + "#" + p1 + "#" + tag);
		addFeature(p1 + "#" + p2 + "#" + tag);

		// trigarm
		// bigram
		addFeature(p_2 + "#" + p_1 + "#" + p + tag);
		addFeature(p_1 + "#" + p + "#" + p1 + "#" + tag);
		addFeature(p + "#" + p1 + "#" + p2 + "#" + tag);
	}

	public static void wpFeature(String[] words, String[] postag, String[] tagger) {
		if (words.length == 1) {
			featVector("w_2", "w_1", words[0], "w1", "w2", "p_2", "p_1", postag[0], "p1", "p2", tagger[0]);
		}
		if (words.length == 2) {
			featVector("w_2", "w_1", words[0], words[1], "w1", "p_2", "p_1", postag[0], postag[1], "p1", tagger[0]);
			featVector("w_1", words[0], words[1], "w1", "w2", "p_1", postag[0], postag[1], "p1", "p2", tagger[1]);
		}
		if (words.length == 3) {
			featVector("w_2", "w_1", words[0], words[1], words[2], "p_2", "p_1", postag[0], postag[1], postag[2],
					tagger[0]);
			featVector("w_1", words[0], words[1], words[2], "w1", "p_1", postag[0], postag[1], postag[2], "p1",
					tagger[1]);
			featVector(words[0], words[1], words[2], "w1", "w2", postag[0], postag[1], postag[2], "p1", "p2",
					tagger[2]);
		}
		if (words.length == 4) {
			featVector("w_2", "w_1", words[0], words[1], words[2], "p_2", "p_1", postag[0], postag[1], postag[2],
					tagger[0]);
			featVector("w_1", words[0], words[1], words[2], words[3], "p_1", postag[0], postag[1], postag[2], postag[3],
					tagger[1]);
			featVector(words[0], words[1], words[2], words[3], "w1", postag[0], postag[1], postag[2], postag[3], "p1",
					tagger[2]);
			featVector(words[1], words[2], words[3], "w1", "w2", postag[1], postag[2], postag[3], "p1", "p2",
					tagger[3]);
		}
		if (words.length > 4) {
			for (int i = 0; i < words.length; i++) {
				if (i == 0) {
					featVector("w_2", "w_1", words[i], words[i + 1], words[i + 2], "p_2", "p_1", postag[i],
							postag[i + 1], postag[i + 2], tagger[i]);
				}
				if (i == 1) {
					featVector("w_1", words[i - 1], words[i], words[i + 1], words[i + 2], "p_1", postag[i - 1],
							postag[i], postag[i + 1], postag[i + 2], tagger[i]);
				}
				if (i == words.length - 2) {
					featVector(words[i - 2], words[i - 1], words[i], words[i + 1], "w1", postag[i - 2], postag[i - 1],
							postag[i], postag[i + 1], "p1", tagger[i]);
				}
				if (i == words.length - 1) {
					featVector(words[i - 2], words[i - 1], words[i], "w1", "w2", postag[i - 2], postag[i - 1],
							postag[i], "p1", "p2", tagger[i]);
				}
				if (i < words.length - 2 && i >= 2) {
					featVector(words[i - 2], words[i - 1], words[i], words[i + 1], words[i + 2], postag[i - 2],
							postag[i - 1], postag[i], postag[i + 1], postag[i + 2], tagger[i]);
				}
			}
		}
	}

	public static void addFeature(String feature) {
		if (!featureSet.containsKey(feature)) {
			featureSet.put(feature, 1);
		} else {
			int value = featureSet.get(feature);
			featureSet.put(feature, value + 1);
		}

	}

	public static Map.Entry[] sortHashtableByValue(Hashtable ht) {
		Set set = ht.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				int key1 = Integer.parseInt(((Map.Entry) arg0).getValue().toString());
				int key2 = Integer.parseInt(((Map.Entry) arg1).getValue().toString());
				return ((Comparable) key1).compareTo(key2);
			}
		});

		return entries;
	}

	public static void indexFeature(String filePath) {
		if (filePath.endsWith(".txt")) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(filePath));

				String line;
				String sent = "";
				while ((line = in.readLine()) != null) {
					if (line.length() > 0) {
						sent = sent + line + "#@#@#";
					} else {
						extractTrainFeature(sent);
						sent = "";
						sentNum++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void extractTrainFeature(String sent) {

		System.out.println(sent);
		String[] terms = sent.split("#@#@#");
		String[] words = new String[terms.length];
		String[] postag = new String[terms.length];
		String[] tagger = new String[terms.length];
		for (int i = 0; i < terms.length; i++) {
			System.out.println(terms[i]);
			String[] tokens = terms[i].split(" ");
			words[i] = tokens[0];
			postag[i] = tokens[1];
			tagger[i] = tokens[2];
		}

		searchFeatureIndex(words, postag, tagger);
		writeFeatureFile("");
		sentCount++;
	}

	public static void featIndex(String w_2, String w_1, String w, String w1, String w2, String p_2, String p_1,
			String p, String p1, String p2, String tag) {
		featVec = "";

		// for word
		// unigram
		featSequence(w_2 + "#" + tag);
		featSequence(w_1 + "#" + tag);
		featSequence(w + "#" + tag);
		featSequence(w1 + "#" + tag);
		featSequence(w2 + "#" + tag);

		// bigram
		featSequence(w_2 + "#" + w_1 + "#" + tag);
		featSequence(w_1 + "#" + w + "#" + tag);
		featSequence(w + "#" + w1 + "#" + tag);
		featSequence(w1 + "#" + w2 + "#" + tag);

		// for pos
		// unigram
		featSequence(p_2 + "#" + tag);
		featSequence(p_1 + "#" + tag);
		featSequence(p + "#" + tag);
		featSequence(p1 + "#" + tag);
		featSequence(p2 + "#" + tag);

		// bigram
		featSequence(p_2 + "#" + p_1 + "#" + tag);
		featSequence(p_1 + "#" + p + "#" + tag);
		featSequence(p + "#" + p1 + "#" + tag);
		featSequence(p1 + "#" + p2 + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p_2 + "#" + p_1 + "#" +
		// tag);
		// featVec = featVec + "," + findIndexValue(p_1 + "#" + p + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p + "#" + p1 + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p1 + "#" + p2 + "#" + tag);

		// trigarm
		featSequence(p_2 + "#" + p_1 + "#" + p + tag);
		featSequence(p_1 + "#" + p + "#" + p1 + "#" + tag);
		featSequence(p + "#" + p1 + "#" + p2 + "#" + tag);
		// featVec = featVec + "," + findIndexValue(p_2 + "#" + p_1 + "#" + p +
		// tag);
		// featVec = featVec + "," + findIndexValue(p_1 + "#" + p + "#" + p1 +
		// "#" + tag);
		// featVec = featVec + "," + findIndexValue(p + "#" + p1 + "#" + p2 +
		// "#" + tag);
		if(featVec.length() < 1) {
			featVec = testIndex(w + "#" + tag) + featVec;
			writeFeatureFile(featVec);
		} else {
			writeFeatureFile(featVec);
		}
	}

	public static void featSequence(String feature) {

		if (testIndex(feature) > value) {
			if (featVec.length() < 1) {
				featVec = findIndexValue(feature) + featVec;
			} else {
				featVec = featVec + "," + findIndexValue(feature);
			}
		}
	}

	public static int testIndex(String feature) {
		int index;
		index = featureSet.get(feature);
		return index;
	}

	public static int findIndexValue(String feature) {
		int index;

		index = allFeatureSet.get(feature);
		System.out.println("Find: " + feature + "'index is: " + index);

		return index;
	}

	public static void searchFeatureIndex(String[] words, String[] postag, String[] tagger) {
		if (words.length == 1) {
			featIndex("w_2", "w_1", words[0], "w1", "w2", "p_2", "p_1", postag[0], "p1", "p2", tagger[0]);
		}
		if (words.length == 2) {
			featIndex("w_2", "w_1", words[0], words[1], "w1", "p_2", "p_1", postag[0], postag[1], "p1", tagger[0]);
			featIndex("w_1", words[0], words[1], "w1", "w2", "p_1", postag[0], postag[1], "p1", "p2", tagger[1]);
		}
		if (words.length == 3) {
			featIndex("w_2", "w_1", words[0], words[1], words[2], "p_2", "p_1", postag[0], postag[1], postag[2],
					tagger[0]);
			featIndex("w_1", words[0], words[1], words[2], "w1", "p_1", postag[0], postag[1], postag[2], "p1",
					tagger[1]);
			featIndex(words[0], words[1], words[2], "w1", "w2", postag[0], postag[1], postag[2], "p1", "p2", tagger[2]);
		}
		if (words.length == 4) {
			featIndex("w_2", "w_1", words[0], words[1], words[2], "p_2", "p_1", postag[0], postag[1], postag[2],
					tagger[0]);
			featIndex("w_1", words[0], words[1], words[2], words[3], "p_1", postag[0], postag[1], postag[2], postag[3],
					tagger[1]);
			featIndex(words[0], words[1], words[2], words[3], "w1", postag[0], postag[1], postag[2], postag[3], "p1",
					tagger[2]);
			featIndex(words[1], words[2], words[3], "w1", "w2", postag[1], postag[2], postag[3], "p1", "p2", tagger[3]);
		}
		if (words.length > 4) {
			for (int i = 0; i < words.length; i++) {
				if (i == 0) {
					featIndex("w_2", "w_1", words[i], words[i + 1], words[i + 2], "p_2", "p_1", postag[i],
							postag[i + 1], postag[i + 2], tagger[i]);
				}
				if (i == 1) {
					featIndex("w_1", words[i - 1], words[i], words[i + 1], words[i + 2], "p_1", postag[i - 1],
							postag[i], postag[i + 1], postag[i + 2], tagger[i]);
				}
				if (i == words.length - 2) {
					featIndex(words[i - 2], words[i - 1], words[i], words[i + 1], "w1", postag[i - 2], postag[i - 1],
							postag[i], postag[i + 1], "p1", tagger[i]);
				}
				if (i == words.length - 1) {
					featIndex(words[i - 2], words[i - 1], words[i], "w1", "w2", postag[i - 2], postag[i - 1], postag[i],
							"p1", "p2", tagger[i]);
				}
				if (i < words.length - 2 && i >= 2) {
					featIndex(words[i - 2], words[i - 1], words[i], words[i + 1], words[i + 2], postag[i - 2],
							postag[i - 1], postag[i], postag[i + 1], postag[i + 2], tagger[i]);
				}
			}
		}
	}

	public static void writeFeatureFile(String content) {
		String filePath = featureFilePath;
		System.out.println("Writing feature index: " + content);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeTagFile(String content) {
		String filePath = tagFilePath;
		System.out.println("Writing tag index: " + content);
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

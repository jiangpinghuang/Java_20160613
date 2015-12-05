/**
 * Project Name: StructuredPerceptron.
 * File Name: FeatureVector.java.
 * Date: Nov 14, 2015.
 * Copyright (c) 2015 hjp@whu.edu.cn All Rights Reserved.
 */

package org.hjp.model.sp.perceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class: FeatureVector.
 * 
 * @author: hjp.
 * @version: v1.0.
 * @since: JDK 1.8.
 */

public class FeatureVector {

	public static Hashtable<String, Integer> featureSet = new Hashtable<String, Integer>();
	public static int sentNum = 0;

	public static void maintest(String args) {
		//if (args.length == 0)
		//	args = new String[] { "/Users/hjp/Workshop/Model/perceptron/train/" };
		
		long start = System.currentTimeMillis();
		listFile(args);

		/// Map map = new HashMap();
		Iterator iter = featureSet.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if ((Integer) entry.getValue() > 5)
				System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		Map.Entry[] set = sortHashtableByValue(featureSet);
		for (int i = 0; i < set.length; i++) {
			System.out.println(set[i].getKey() + "\t" + set[i].getValue());

		}

		System.out.println("The type of pos: " + featureSet.size());
		long end = System.currentTimeMillis();
		System.out.println("The time cost is: " + (end - start) / 1000 + " s!");

	}

	public static void listFile(String dirName) {
		try {
			File pathName = new File(dirName);
			String[] fileNames = pathName.list();

			for (int i = 0; i < fileNames.length; i++) {
				File f = new File(pathName.getPath(), fileNames[i]);

				if (f.isDirectory()) {
					System.out.println(f.getCanonicalPath());
					listFile(f.getPath());
				} else {
					// System.out.println(f.getAbsolutePath());
					readFile(f.getAbsolutePath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readFile(String filePath) {
		// The data must be prepared with .txt format.
		if (filePath.endsWith(".txt")) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(filePath));

				String line;
				String sent = "";
				while ((line = in.readLine()) != null) {
					if (line.length() != 0) {
						sent = sent + line + "\t";
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
		String[] terms = sent.split("\t");
		String[] words = new String[terms.length];
		String[] postag = new String[terms.length];
		String[] tagger = new String[terms.length];
		for (int i = 0; i < terms.length; i++) {
			System.out.println(terms[i]);
			String[] tokens = terms[i].split(" ");
			for (int j = 0; j < tokens.length - 1; j++) {
				System.out.println(tokens[j]);
				words[i] = tokens[0];
				postag[i] = tokens[1];
				tagger[i] = tokens[2];
			}

		}

		wordFeature(words, tagger);
		posFeature(postag, tagger);

	}

	public static void wordFeature(String[] words, String[] tagger) {
		for (int i = 0; i < words.length; i++) {
			if (i == 0 && i < words.length - 2) {
				// unigram feature.
				addFeature("pre2w#" + tagger[i]);
				addFeature("pre1w#" + tagger[i]);
				addFeature(words[i] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 2] + "#" + tagger[i]);

				// bigram feature
				addFeature("pre2w#pre1w#" + tagger[i]);
				addFeature("pre1w#" + words[i] + "#" + tagger[i]);
				addFeature(words[i] + "#" + words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + words[i + 2] + "#" + tagger[i]);
			}

			if (i == 1 && i < words.length - 2) {
				// unigram feature
				addFeature("pre1w#" + tagger[i]);
				addFeature(words[i - 1] + "#" + tagger[i]);
				addFeature(words[i] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 2] + "#" + tagger[i]);

				// bigram feature
				addFeature("pre1w#" + words[i - 1] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + words[i] + "#" + tagger[i]);
				addFeature(words[i] + "#" + words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + words[i + 2] + "#" + tagger[i]);
			}

			if (i >= 2 && i < words.length - 2) {
				// unigram feature
				addFeature(words[i - 2] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + tagger[i]);
				addFeature(words[i] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 2] + "#" + tagger[i]);

				// bigram feature
				addFeature(words[i - 2] + "#" + words[i - 1] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + words[i] + "#" + tagger[i]);
				addFeature(words[i] + "#" + words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + words[i + 2] + "#" + tagger[i]);
			}

			if (i >= 2 && i == words.length - 2) {
				// unigram feature
				addFeature(words[i - 2] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + tagger[i]);
				addFeature(words[i] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#" + tagger[i]);
				addFeature("suff1w#" + tagger[i]);

				// bigram feature
				addFeature(words[i - 2] + "#" + words[i - 1] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + words[i] + "#" + tagger[i]);
				addFeature(words[i] + "#" + words[i + 1] + "#" + tagger[i]);
				addFeature(words[i + 1] + "#suff1w#" + tagger[i]);
			}

			if (i >= 2 && i == words.length - 1) {
				// unigram feature
				addFeature(words[i - 2] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + tagger[i]);
				addFeature(words[i] + "#" + tagger[i]);
				addFeature("suff1w#" + tagger[i]);
				addFeature("suff2w#" + tagger[i]);

				// bigram feature
				addFeature(words[i - 2] + "#" + words[i - 1] + "#" + tagger[i]);
				addFeature(words[i - 1] + "#" + words[i] + "#" + tagger[i]);
				addFeature(words[i] + "#suff1w#" + tagger[i]);
				addFeature("suff1w#suff1w#" + tagger[i]);
			}
		}
	}

	public static void posFeature(String[] postag, String[] tagger) {
		for (int i = 0; i < postag.length; i++) {
			// the first pos feature.
			if (i == 0 && i < postag.length - 2) {
				// unigram feature.
				addFeature("pre2p#" + tagger[i]);
				addFeature("pre1p#" + tagger[i]);
				addFeature(postag[i] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 2] + "#" + tagger[i]);

				// bigram feature
				addFeature("pre2p#pre1p#" + tagger[i]);
				addFeature("pre1p#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + postag[i + 2] + "#" + tagger[i]);

				// trigram feature
				addFeature("pre2p#pre1p#" + postag[i] + "#" + tagger[i]);
				addFeature("pre1p#" + postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + postag[i + 2] + "#" + tagger[i]);
			}

			// the second pos feature.
			if (i == 1 && i < postag.length - 2) {
				// unigram feature
				addFeature("pre1p#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 2] + "#" + tagger[i]);

				// bigram feature
				addFeature("pre1p#" + postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + postag[i + 2] + "#" + tagger[i]);

				// trigram feature
				addFeature("pre1p#" + postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + postag[i + 2] + "#" + tagger[i]);

			}

			if (i >= 2 && i < postag.length - 2) {
				// unigram feature
				addFeature(postag[i - 2] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 2] + "#" + tagger[i]);

				// bigram feature
				addFeature(postag[i - 2] + "#" + postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + postag[i + 2] + "#" + tagger[i]);

				// trigram feature
				addFeature(postag[i - 2] + "#" + postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + postag[i + 2] + "#" + tagger[i]);
			}

			// the previous of last pos feature.
			if (i >= 2 && i == postag.length - 2) {
				// unigram feature
				addFeature(postag[i - 2] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#" + tagger[i]);
				addFeature("suff1p#" + tagger[i]);

				// bigram feature
				addFeature(postag[i - 2] + "#" + postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i + 1] + "#suff1p#" + tagger[i]);

				// trigram feature
				addFeature(postag[i - 2] + "#" + postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + postag[i + 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + postag[i + 1] + "#suff1p#" + tagger[i]);
			}

			// last pos feature.
			if (i >= 2 && i == postag.length - 1) {
				// unigram feature
				addFeature(postag[i - 2] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i] + "#" + tagger[i]);
				addFeature("suff1p#" + tagger[i]);
				addFeature("suff2p#" + tagger[i]);

				// bigram feature
				addFeature(postag[i - 2] + "#" + postag[i - 1] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i] + "#suff1p#" + tagger[i]);
				addFeature("suff1p#suff2p#" + tagger[i]);

				// trigram feature
				addFeature(postag[i - 2] + "#" + postag[i - 1] + "#" + postag[i] + "#" + tagger[i]);
				addFeature(postag[i - 1] + "#" + postag[i] + "#suff1p#" + tagger[i]);
				addFeature(postag[i] + "#suff1p#suff2p#" + tagger[i]);
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

}

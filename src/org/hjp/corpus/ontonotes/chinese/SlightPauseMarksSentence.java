package org.hjp.corpus.ontonotes.chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlightPauseMarksSentence {
	
	public static int sent = 0;
	
	public static void main(String[] args) {
		String dirPath = "/Users/hjp/Workshop/Corpus/OntoNotes/data";
		listFile(dirPath);
		System.out.println("sent: " + sent);
	}
	
	public static void listFile(String dirPath) {
		File file = new File(dirPath);
		File[] files = file.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : files) {
			if (f.isDirectory()) {
				listFile(f.getAbsolutePath());
			} else {
				list.add(f);
			}
		}
		for (File f : files) {
			if (f.isFile()) {
				if (f.getAbsolutePath().endsWith(".onf")) {
					//System.out.println(f.getAbsolutePath());
					readFile(f.getAbsolutePath());
				}
			}
		}
	}
	
	public static void readFile(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			boolean flag = false;
			while ((line = reader.readLine()) != null) {
				if(line.equals("---------------")) {
					flag = true;
					continue;
				}
				if(flag) {
					//System.out.println(line);
					flag = false;
					if(line.contains("、")) {
						System.out.println(line);
						String[] terms = line.split("、");
						
						sent = sent + terms.length - 1;
					}
					//sent++;
				}
				//if(line.contains("、")) {
				//	System.out.println(line);
				//	sent++;
				//}
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

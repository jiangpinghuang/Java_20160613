package org.hjp.java.toolkit.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

	public static void main(String[] args) {
		String dirPath = "G:\\Downloads\\MyStudio\\WorkShops\\Studios\\treebank";
		listFile(dirPath);

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
				System.out.println(f.getAbsolutePath());
				try {
					readFile(f.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void readFile(String filePath) throws IOException {
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
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
		String filePath = "G:\\Downloads\\MyStudio\\WorkShops\\coling\\corpus\\dev.data";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

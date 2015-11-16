/**
 * Project Name: Java.
 * File Name: FindDirectories.java.
 * Date: Nov 14, 2015.
 * Copyright (c) 2015 hjp@whu.edu.cn All Rights Reserved.
 */

package org.hjp.java.core.II.ch01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class: FindDirectories.
 * 
 * @author: hjp.
 * @version: v1.0.
 * @since: JDK 1.8.
 */

public class FindDirectories {

	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "/Users/hjp/Workshop/Model/perceptron/trial/" };

		try {
			File pathName = new File(args[0]);
			String[] fileNames = pathName.list();

			for (int i = 0; i < fileNames.length; i++) {
				File f = new File(pathName.getPath(), fileNames[i]);

				if (f.isDirectory()) {
					System.out.println(f.getCanonicalPath());
					main(new String[] { f.getPath() });
				} else {
					if (f.getAbsolutePath().endsWith(".txt")) {
						System.out.println(f.getAbsolutePath());
						@SuppressWarnings("resource")
						BufferedReader in = new BufferedReader(new FileReader(f.getAbsolutePath()));
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

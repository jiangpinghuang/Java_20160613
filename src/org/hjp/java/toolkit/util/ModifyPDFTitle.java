package org.hjp.java.toolkit.util;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class ModifyPDFTitle {

	public static void main(String[] args) {
		String dirPath = "G:\\Downloads\\MyThunder\\Thunder\\pdf";
		listFile(dirPath);

	}

	public static void listFile(String dirPath) {
		File file = new File(dirPath);

		if (!file.exists()) {
			System.out.println(dirPath + " is not existed!");
			return;
		} else {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					listFile(files[i].getAbsolutePath());
				} else {
					if (files[i].getAbsolutePath().endsWith(".pdf"))
						modifyPDFName(files[i].getAbsolutePath());
				}
			}
		}

	}

	public static void modifyPDFName(String filePath) {
		System.out.println(filePath);
		PDDocument doc = null;
		try {
			doc = PDDocument.load(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PDDocumentInformation info = doc.getDocumentInformation();
		String title = info.getTitle();
		if (title != null) {
			title = title.replace("?", "").replace("\"", "").replace("*", "")
					.replace(":", "").replace("  ", "").replace("/", " ");
			try {
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			title = title + ".pdf";
			renFile(filePath, title);
		}

	}

	public static void renFile(String oldName, String newName) {
		File oldFile = new File(oldName);
		if (!oldFile.exists()) {
			System.out.println(oldName + " is not existed!");
		} else {
			String path = oldFile.getParent();
			File newFile = new File(path + File.separator + newName);
			if (oldFile.renameTo(newFile)) {
				System.out.println("Rename " + newName + " success!");
			} else {
				System.out.println("Rename " + newName + " fail!");
			}
		}

	}

}

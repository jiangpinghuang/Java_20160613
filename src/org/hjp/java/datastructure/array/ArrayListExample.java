package org.hjp.java.datastructure.array;

import java.util.ArrayList;

public class ArrayListExample {

	public static void main(String[] args) {
		ArrayList al = new ArrayList();
		ArrayList bl = new ArrayList();
		System.out.println("Initial size of al: " + al.size());

		al.add("C");
		al.add("A");
		al.add("E");
		al.add("B");
		al.add("D");
		al.add("F");
		al.add(1, "A2");
		System.out.println("Size of al after additions: " + al.size());

		// display the array list
		System.out.println("Contents of al: " + al);
		// Remove elements from the array list
		al.remove("F");
		al.remove(2);
		System.out.println("Size of al after deletions: " + al.size());
		System.out.println("Contents of al: " + al);

		for (int i = 0; i < 486369; i++)
			bl.add(0);
		System.out.println("size of bl: " + bl.size());
		System.out.println(bl);
	}

}

/**
 * Project Name: Java.
 * File Name: RandomFileTest.java.
 * Date: Nov 13, 2015.
 * Copyright (c) 2015 hjp@whu.edu.cn All Rights Reserved.
 */

package org.hjp.java.core.II.ch01;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class: RandomFileTest.
 * 
 * @author: hjp.
 * @version: v1.0.
 * @since: JDK 1.8.
 */

public class RandomFileTest {

	public static void main(String[] args) {
		Employee2[] staff = new Employee2[3];

		staff[0] = new Employee2("Carl Cracker", 75000, 1987, 12, 15);
		staff[1] = new Employee2("Harry Hacker", 50000, 1989, 10, 1);
		staff[2] = new Employee2("Tony Tester", 4000, 1990, 3, 15);

		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream("/Users/hjp/Downloads/employee2.dat"));
			for (Employee2 e : staff)
				e.writeData(out);
			out.close();

			RandomAccessFile in = new RandomAccessFile("/Users/hjp/Downloads/employee2.dat", "r");
			int n = (int) (in.length() / Employee2.RECORD_SIZE);
			Employee2[] newStaff = new Employee2[n];

			for (int i = n - 1; i >= 0; i--) {
				newStaff[i] = new Employee2();
				in.seek(i * Employee2.RECORD_SIZE);
				newStaff[i].readData(in);
			}
			in.close();
			for (Employee2 e : newStaff)
				System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Employee2 {
	public Employee2() {
	}

	public Employee2(String n, double s, int year, int month, int day) {
		name = n;
		salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		hireDay = calendar.getTime();
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public Date getHireDay() {
		return hireDay;
	}

	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

	public String toString() {
		return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
	}

	public void writeData(DataOutput out) throws IOException {
		DataIO.writeFixedString(name, NAME_SIZE, out);
		out.writeDouble(salary);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(hireDay);
		out.writeInt(calendar.get(Calendar.YEAR));
		out.writeInt(calendar.get(Calendar.MONTH) + 1);
		out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
	}

	public void readData(DataInput in) throws IOException {
		name = DataIO.readFixedString(NAME_SIZE, in);
		salary = in.readDouble();
		int y = in.readInt();
		int m = in.readInt();
		int d = in.readInt();
		GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
		hireDay = calendar.getTime();
	}

	public static final int NAME_SIZE = 40;
	public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

	private String name;
	private double salary;
	private Date hireDay;
}

class DataIO {
	public static String readFixedString(int size, DataInput in) throws IOException {
		StringBuilder b = new StringBuilder(size);
		int i = 0;
		boolean more = true;
		while (more && i < size) {
			char ch = in.readChar();
			i++;
			if (ch == 0)
				more = false;
			else
				b.append(ch);
		}
		in.skipBytes(2 * (size - i));
		return b.toString();
	}

	public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
		for (int i = 0; i < size; i++) {
			char ch = 0;
			if (i < s.length())
				ch = s.charAt(i);
			out.writeChar(ch);

		}
	}
}

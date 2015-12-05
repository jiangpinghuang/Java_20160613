/**
 * Project Name: StructuredPerceptron.
 * File Name: Perceptron.java.
 * Date: Nov 10, 2015.
 * Copyright (c) 2015 hjp@whu.edu.cn All Rights Reserved.
 */

package org.hjp.model.sp.perceptron;

import java.util.ArrayList;

import org.hjp.model.sp.perceptron.FeatureVector;

/**
 * Class: Perceptron.
 * 
 * @author: hjp.
 * @version: v1.0.
 * @since: JDK 1.8.
 */

public class Perceptron {
	
	public static ArrayList alpha = new ArrayList();
	public static ArrayList phi_xy = new ArrayList();
	public static ArrayList phi_xz = new ArrayList();
	
	public static void main(String[] args) {
		String fileDir = "/Users/hjp/Workshop/Model/perceptron/train/";
		FeatureVector fv = new FeatureVector();
		fv.maintest(fileDir);
		System.out.println(fv.featureSet.size());
		System.out.println("Sentence: " + fv.sentNum);
		// the number of interations over the training set.
		int interNum = 10;
		
		setParameter(fv.featureSet.size());
	}
	
	public static void setParameter(int dim) {
		for(int i = 0; i < dim; i++) {
			alpha.add(0);
		}
		//System.out.println(alpha);
	}
	
	public static void train(String trainFile) {
		
	}

}

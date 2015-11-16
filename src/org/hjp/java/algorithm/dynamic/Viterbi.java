package org.hjp.java.algorithm.dynamic;

import static org.hjp.java.algorithm.dynamic.Viterbi.Weather.*;
import static org.hjp.java.algorithm.dynamic.Viterbi.Activity.*;

public class Viterbi {

	public static int[] compute(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p) {
		double[][] V = new double[obs.length][states.length];
		int[][] path = new int[states.length][obs.length];

		for (int y : states) {
			V[0][y] = start_p[y] * emit_p[y][obs[0]];
			path[y][0] = y;
		}

		for (int t = 1; t < obs.length; ++t) {
			int[][] newpath = new int[states.length][obs.length];

			for (int y : states) {
				double prob = -1;
				int state;
				for (int y0 : states) {
					double nprob = V[t - 1][y0] * trans_p[y0][y] * emit_p[y][obs[t]];
					if (nprob > prob) {
						prob = nprob;
						state = y0;
						V[t][y] = prob;
						System.arraycopy(path[state], 0, newpath[y], 0, t);
						newpath[y][t] = y;
					}
				}
			}

			path = newpath;
		}

		double prob = -1;
		int state = 0;
		for (int y : states) {
			if (V[obs.length - 1][y] > prob) {
				prob = V[obs.length - 1][y];
				state = y;
			}
		}

		return path[state];
	}

	static enum Weather {
		Rainy, Sunny,
	}

	static enum Activity {
		walk, shop, clean,
	}

	static int[] states = new int[] { Rainy.ordinal(), Sunny.ordinal() };
	static int[] observations = new int[] { walk.ordinal(), shop.ordinal(), clean.ordinal() };
	static double[] start_probability = new double[] { 0.6, 0.4 };
	static double[][] transititon_probability = new double[][] { { 0.7, 0.3 }, { 0.4, 0.6 }, };
	static double[][] emission_probability = new double[][] { { 0.1, 0.4, 0.5 }, { 0.6, 0.3, 0.1 }, };

	public static void main(String[] args) {
		int[] result = Viterbi.compute(observations, states, start_probability, transititon_probability,
				emission_probability);
		for (int r : result) {
			System.out.print(Weather.values()[r] + " ");
		}
		System.out.println();
	}

}

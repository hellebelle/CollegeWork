import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/* @author: Anna Maliakal 17324430
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Floyd-Warshall algorithm
 */

public class CompetitionFloydWarshall {
	private int slowest;
	private int intersections;
	private int streets;
	private double maxDist;

	private double[][] weights;

	private boolean isValid = false;

	/**
	 * @param filename: A filename containing the details of the city road network
	 * @param sA, sB, sC: speeds for 3 contestants
	 */
	CompetitionFloydWarshall(String filename, int sA, int sB, int sC) {
		if((sA>=50 && sA <=100)&&(sB>=50 && sB <=100)&&(sC>=50 && sC <=100))
        {
            isValid = true;
        }
		if (sA < sB) {
			slowest = sA;
		} else {
			slowest = sB;
		}
		if (sC < slowest) {
			slowest = sC;
		}

		try {
			FileReader input = new FileReader(filename);
			Scanner sc = new Scanner(input);

			intersections = sc.nextInt();
			streets = sc.nextInt();
			maxDist = -1;

			weights = new double[intersections][intersections];

			for (int i = 0; i < intersections; i++) {
				for (int j = 0; j < intersections; j++) {
					weights[i][j] = Double.MAX_VALUE;
				}
			}

			for (int i = 0; i < streets; i++) {
				int source = sc.nextInt();
				int dest = sc.nextInt();
				double dist = sc.nextDouble();
				weights[source][dest] = dist;
			}

			for (int i = 0; i < intersections; i++) {
				weights[i][i] = 0;
			}

			sc.close();
		}
		catch (FileNotFoundException e) {
			isValid = false;
		}
		catch (NullPointerException e){
			isValid = false;
		}
		if (slowest <= 0 || weights == null || weights.length < 2 || slowest < 50 || slowest > 100) {
			isValid = false;
		}
	}

	/**
	 * @return int: minimum minutes that will pass before the three contestants can
	 *         meet
	 */
	public int timeRequiredforCompetition() {
		if (isValid == false) {
			return -1;
		}

		for (int k = 0; k < intersections; k++) {
			for (int i = 0; i < intersections; i++) {
				for (int j = 0; j < intersections; j++) {
					double dist = weights[i][j];
					double newDistance = weights[i][k] + weights[k][j];
					if (dist > newDistance) {
						weights[i][j] = newDistance;
					}
				}
			}
		}

		for (int i = 0; i < intersections; i++) {
			for (int j = 0; j < intersections; j++) {
				double dist = weights[i][j];
				if (dist == Double.MAX_VALUE) {
					return -1;
				}
				if (dist > maxDist) {
					maxDist = dist;
				}
			}
		}


		double dist = maxDist * 1000;
		return (int) Math.ceil(dist / slowest);
	}
}
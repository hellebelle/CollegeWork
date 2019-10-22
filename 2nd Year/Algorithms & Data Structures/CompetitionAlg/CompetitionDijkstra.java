import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * @author: Anna Maliakal 17324430
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
 * This class implements the competition using Dijkstra's algorithm
 */

public class CompetitionDijkstra {
    int intersections;
	int streets;
	int slowest; //the slowest walker
	double maxDist;
	DijkstraVertex[] vertices;
    boolean isValid = false;

	/**
	 * @param filename: A filename containing the details of the city road network
	 * @param sA, sB, sC: speeds for 3 contestants
	 */
	CompetitionDijkstra(String filename, int sA, int sB, int sC) {
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

			vertices = new DijkstraVertex[intersections];

			for (int i = 0; i < streets; i++) {
				int vertex = sc.nextInt();
				int dest = sc.nextInt();
				double dist = sc.nextDouble();
				if (vertices[vertex] != null) {
					vertices[vertex].addNeighbour(dest, dist);
				}
				else {
					ArrayList<Integer> neighbours = new ArrayList<Integer>();
					ArrayList<Double> distances = new ArrayList<Double>();
					neighbours.add(dest);
					distances.add(dist);
					vertices[vertex] = new DijkstraVertex(false, -1, neighbours, distances);
				}
			}

			sc.close();
		} 
		catch (FileNotFoundException e) {
			isValid = false;
		}
		catch (NullPointerException e){
			isValid = false;
		}

		if (slowest < 0 || vertices == null || vertices.length < 2 || slowest < 50 || slowest > 100) {
			isValid = false;
		}

		for(int i = 0; i < intersections; i++){
			if(vertices[i] == null){
				isValid = false;
			}
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
	
		for (int i = 0; i < intersections; i++) {
			for (int j = 0; j < intersections; j++) {
				vertices[j].setrouteDist(Double.MAX_VALUE);
				vertices[j].unsettle();
			}

			vertices[i].setrouteDist(0);
			for (int j = 0; j < intersections; j++) {
				int vertex = -1;
				for (int k = 0; k < intersections; k++) {
					if (!vertices[k].isSettled()) {
						if (vertex == -1 || vertices[k].getrouteDist() < vertices[vertex].getrouteDist()) {
							vertex = k;
						}
					}
				}

				double routeDist = vertices[vertex].getrouteDist();
				ArrayList<Integer> neighbours = vertices[vertex].getNeighbours();
				ArrayList<Double> distances = vertices[vertex].getDistances();

				vertices[vertex].settle();

				for (int k = 0; k < neighbours.size(); k++) {
					int dest = neighbours.get(k);
					if (!vertices[dest].isSettled()) {
						double dist = routeDist + distances.get(k);
						if (vertices[dest].getrouteDist() > dist) {
							vertices[dest].setrouteDist(dist);
						}
					}
				}
			}
			for (int k = 0; k < intersections; k++) {
				double dist = vertices[k].getrouteDist();
				if (dist > maxDist) {
					maxDist = dist;
				}
			}
		}
		double dist = maxDist * 1000;
		return (int) Math.ceil(dist / slowest);
	}

	private class DijkstraVertex {
		boolean settled;
		double routeDist;
		ArrayList<Integer> neighbours;
		ArrayList<Double> distances;

		DijkstraVertex(boolean settled, double routeDist, ArrayList<Integer> neighbours, ArrayList<Double> distances) {
			this.settled = settled;
			this.routeDist = routeDist;
			this.neighbours = neighbours;
			this.distances = distances;
		}

		boolean isSettled() {
			return this.settled;
		}

		void settle() {
			this.settled = true;
		}

		void unsettle() {
			this.settled = false;
		}

		double getrouteDist() {
			return this.routeDist;
		}

		void setrouteDist(double routeDist) {
			this.routeDist = routeDist;
		}

		ArrayList<Integer> getNeighbours() {
			return this.neighbours;
		}

		ArrayList<Double> getDistances() {
			return this.distances;
		}

		void addNeighbour(int dest, double distance) {
			this.neighbours.add(dest);
			this.distances.add(distance);
		}
	}
}